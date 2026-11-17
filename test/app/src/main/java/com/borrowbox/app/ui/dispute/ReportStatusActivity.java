package com.borrowbox.app.ui.dispute;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.borrowbox.app.R;
import com.borrowbox.app.data.repository.DisputeRepository;
import com.borrowbox.app.model.Dispute;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import java.util.Date;

public class ReportStatusActivity extends AppCompatActivity {

    public static final String EXTRA_DISPUTE_ID = "extra_dispute_id";

    private long disputeId = 1L;

    private TextView tvReportId;
    private TextView tvReportStatusPill;
    private TextView tvCategory;
    private TextView tvSubmittedAt;
    private ImageView ivStep1Icon;
    private ImageView ivStep2Icon;
    private ImageView ivStep3Icon;
    private TextView tvStep1Text;
    private TextView tvStep2Text;
    private TextView tvStep3Text;
    private TextView tvDescription;
    private TextView tvAdminVerdict;

    private DisputeRepository disputeRepository;
    private FirebaseFirestore firestore;
    private ListenerRegistration firestoreListener;

    public static Intent createIntent(Context context, long disputeId) {
        Intent intent = new Intent(context, ReportStatusActivity.class);
        intent.putExtra(EXTRA_DISPUTE_ID, disputeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_status);

        disputeId = getIntent().getLongExtra(EXTRA_DISPUTE_ID, 1L);
        disputeRepository = DisputeRepository.getInstance(this);
        firestore = FirebaseFirestore.getInstance();

        initViews();
        setupToolbar();
        loadDisputeFromRoom();
        attachFirestoreRealtimeListener();
    }

    private void initViews() {
        tvReportId = findViewById(R.id.tvReportId);
        tvReportStatusPill = findViewById(R.id.tvReportStatusPill);
        tvCategory = findViewById(R.id.tvCategory);
        tvSubmittedAt = findViewById(R.id.tvSubmittedAt);
        ivStep1Icon = findViewById(R.id.ivStep1Icon);
        ivStep2Icon = findViewById(R.id.ivStep2Icon);
        ivStep3Icon = findViewById(R.id.ivStep3Icon);
        tvStep1Text = findViewById(R.id.tvStep1Text);
        tvStep2Text = findViewById(R.id.tvStep2Text);
        tvStep3Text = findViewById(R.id.tvStep3Text);
        tvDescription = findViewById(R.id.tvDescription);
        tvAdminVerdict = findViewById(R.id.tvAdminVerdict);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void loadDisputeFromRoom() {
        disputeRepository.getDisputeByIdLive(disputeId).observe(this, dispute -> {
            if (dispute != null) {
                bindDispute(dispute);
            }
        });
    }

    private void attachFirestoreRealtimeListener() {
        try {
            firestoreListener = firestore.collection("dispute_reports")
                    .document(String.valueOf(disputeId))
                    .addSnapshotListener((snapshot, e) -> {
                        if (e != null || snapshot == null || !snapshot.exists()) {
                            return;
                        }
                        updateFromSnapshot(snapshot);
                    });
        } catch (Exception ignored) {}
    }

    private void updateFromSnapshot(DocumentSnapshot snapshot) {
        String status = snapshot.getString("status");
        String verdict = snapshot.getString("adminVerdict");

        if (status != null) {
            tvReportStatusPill.setText(status);
            updateStepperUI(status);
        }
        if (verdict != null && !verdict.isEmpty()) {
            tvAdminVerdict.setText(verdict);
        }
    }

    private void bindDispute(Dispute dispute) {
        tvReportId.setText("Dispute Report #" + dispute.getId());
        tvReportStatusPill.setText(dispute.getStatus());
        tvCategory.setText("Type: " + dispute.getDisputeType());

        String dateStr = DateFormat.format("yyyy-MM-dd HH:mm", new Date(dispute.getCreatedAt())).toString();
        tvSubmittedAt.setText("Submitted: " + dateStr);
        tvDescription.setText(dispute.getDescription());

        if (!dispute.getAdminVerdict().isEmpty()) {
            tvAdminVerdict.setText(dispute.getAdminVerdict());
        } else {
            tvAdminVerdict.setText("Awaiting Administrator verdict...");
        }

        updateStepperUI(dispute.getStatus());
    }

    private void updateStepperUI(String status) {
        if (Dispute.STATUS_SUBMITTED.equalsIgnoreCase(status)) {
            ivStep1Icon.setImageResource(R.drawable.ic_check_circle);
            ivStep2Icon.setImageResource(R.drawable.dot_gray);
            ivStep3Icon.setImageResource(R.drawable.dot_gray);
        } else if (Dispute.STATUS_UNDER_REVIEW.equalsIgnoreCase(status)) {
            ivStep1Icon.setImageResource(R.drawable.ic_check_circle);
            ivStep2Icon.setImageResource(R.drawable.ic_check_circle);
            ivStep3Icon.setImageResource(R.drawable.dot_gray);
        } else if (Dispute.STATUS_RESOLVED.equalsIgnoreCase(status) || Dispute.STATUS_DISMISSED.equalsIgnoreCase(status)) {
            ivStep1Icon.setImageResource(R.drawable.ic_check_circle);
            ivStep2Icon.setImageResource(R.drawable.ic_check_circle);
            ivStep3Icon.setImageResource(R.drawable.ic_check_circle);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (firestoreListener != null) {
            firestoreListener.remove();
        }
    }
}
