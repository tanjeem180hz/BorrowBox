package com.borrowbox.app.data.repository;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.borrowbox.app.data.local.AppDatabase;
import com.borrowbox.app.data.local.DisputeDao;
import com.borrowbox.app.data.local.StudentDao;
import com.borrowbox.app.model.DamageReport;
import com.borrowbox.app.model.Dispute;
import com.borrowbox.app.model.MisconductReport;
import com.borrowbox.app.model.Report;
import com.borrowbox.app.model.Student;
import com.borrowbox.app.util.AppExecutors;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Repository handling dispute filing, incident reporting, and admin moderation actions.
 */
public class DisputeRepository {

    private static volatile DisputeRepository instance;
    private final DisputeDao disputeDao;
    private final StudentDao studentDao;
    private final FirebaseFirestore firestore;

    private DisputeRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.disputeDao = db.disputeDao();
        this.studentDao = db.studentDao();
        this.firestore = FirebaseFirestore.getInstance();
    }

    public static DisputeRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (DisputeRepository.class) {
                if (instance == null) {
                    instance = new DisputeRepository(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public LiveData<List<Dispute>> getAllDisputesLive() {
        return disputeDao.getAllDisputesLive();
    }

    public LiveData<Dispute> getDisputeByIdLive(long id) {
        return disputeDao.getDisputeByIdLive(id);
    }

    public void fileReport(Report report, DisputeCallback callback) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            Dispute dispute = new Dispute();
            dispute.setRentalId(report.getRentalId());
            dispute.setReporterId(report.getReporterId());
            dispute.setDisputeType(report.getIssueType());
            dispute.setDescription(report.getDescription());
            dispute.setStatus(Dispute.STATUS_SUBMITTED);
            dispute.setCreatedAt(report.getCreatedAt());

            if (!report.getEvidenceUrls().isEmpty()) {
                dispute.setEvidenceUri(report.getEvidenceUrls().get(0));
            }

            if (report instanceof DamageReport) {
                DamageReport dr = (DamageReport) report;
                dispute.setDepositAmount(dr.getEstimatedLoss());
            } else if (report instanceof MisconductReport) {
                MisconductReport mr = (MisconductReport) report;
                dispute.setReportedStudentId(mr.getReportedUserId());
            }

            // Fetch reporter name
            Student reporter = studentDao.getStudentById(report.getReporterId());
            if (reporter != null) {
                dispute.setReporterName(reporter.getName());
            }

            long id = disputeDao.insert(dispute);
            dispute.setId(id);

            // Sync to Firestore
            Map<String, Object> data = new HashMap<>();
            data.put("reportId", String.valueOf(id));
            data.put("rentalId", dispute.getRentalId());
            data.put("reporterId", dispute.getReporterId());
            data.put("reporterName", dispute.getReporterName());
            data.put("reportedStudentId", dispute.getReportedStudentId());
            data.put("issueType", dispute.getDisputeType());
            data.put("description", dispute.getDescription());
            data.put("evidenceUri", dispute.getEvidenceUri());
            data.put("depositAmount", dispute.getDepositAmount());
            data.put("status", Dispute.STATUS_SUBMITTED);
            data.put("createdAt", dispute.getCreatedAt());

            try {
                firestore.collection("dispute_reports")
                        .document(String.valueOf(id))
                        .set(data);
            } catch (Exception ignored) {}

            if (callback != null) {
                AppExecutors.getInstance().mainThread().execute(() -> callback.onSuccess(id));
            }
        });
    }

    public void executeAdminVerdict(long disputeId, String verdictStatus, String verdictNotes,
                                   int refundPercentage, int trustPenalty, boolean suspendUser,
                                   long reportedStudentId, AdminActionCallback callback) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            long now = System.currentTimeMillis();
            disputeDao.resolveDispute(disputeId, verdictStatus, verdictNotes, refundPercentage, now);

            // Update reported student's Trust Score & Suspension if applicable
            if (reportedStudentId > 0) {
                Student student = studentDao.getStudentById(reportedStudentId);
                if (student != null) {
                    int newTrustScore = Math.max(0, student.getTrustScore() - trustPenalty);
                    studentDao.updateTrustScore(reportedStudentId, newTrustScore);

                    if (suspendUser) {
                        studentDao.updateSuspension(reportedStudentId, true, "Suspended due to admin dispute verdict");
                    }
                }
            }

            // Sync to Firestore admin_actions collection
            String actionId = UUID.randomUUID().toString();
            Map<String, Object> actionData = new HashMap<>();
            actionData.put("actionId", actionId);
            actionData.put("disputeId", disputeId);
            actionData.put("verdictStatus", verdictStatus);
            actionData.put("verdictNotes", verdictNotes);
            actionData.put("refundPercentage", refundPercentage);
            actionData.put("trustPenalty", trustPenalty);
            actionData.put("suspendUser", suspendUser);
            actionData.put("timestamp", now);

            try {
                firestore.collection("admin_actions").document(actionId).set(actionData);
                firestore.collection("dispute_reports").document(String.valueOf(disputeId))
                        .update("status", verdictStatus, "adminVerdict", verdictNotes, "resolvedAt", now);
            } catch (Exception ignored) {}

            if (callback != null) {
                AppExecutors.getInstance().mainThread().execute(callback::onSuccess);
            }
        });
    }

    public interface DisputeCallback {
        void onSuccess(long disputeId);
        void onError(Exception e);
    }

    public interface AdminActionCallback {
        void onSuccess();
        void onError(Exception e);
    }
}
