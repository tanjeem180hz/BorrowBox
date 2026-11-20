package com.borrowbox.app.ui.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.borrowbox.app.R;
import com.borrowbox.app.data.repository.DisputeRepository;
import com.borrowbox.app.data.repository.NotificationRepository;
import com.borrowbox.app.model.Dispute;
import com.borrowbox.app.model.NotificationModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

public class AdminModerationActivity extends AppCompatActivity {

    public static final String EXTRA_DISPUTE_ID = "extra_dispute_id";

    private long disputeId = 1L;
    private Dispute currentDispute;

    private TextView tvAdminDisputeTitle;
    private TextView tvAdminReporter;
    private TextView tvAdminReportedUser;
    private TextView tvAdminDescription;
    private ChipGroup chipGroupVerdictAction;
    private TextView tvRefundPercentLabel;
    private Slider sliderRefundPercent;
    private TextView tvTrustPenaltyLabel;
    private Slider sliderTrustPenalty;
    private MaterialSwitch switchSuspendAccount;
    private TextInputEditText etAdminNotes;
    private MaterialButton btnExecuteVerdict;

    private DisputeRepository disputeRepository;
    private NotificationRepository notificationRepository;

    public static Intent createIntent(Context context, long disputeId) {
        Intent intent = new Intent(context, AdminModerationActivity.class);
        intent.putExtra(EXTRA_DISPUTE_ID, disputeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_moderation);

        disputeId = getIntent().getLongExtra(EXTRA_DISPUTE_ID, 1L);
        disputeRepository = DisputeRepository.getInstance(this);
        notificationRepository = NotificationRepository.getInstance(this);

        initViews();
        setupToolbar();
        loadDisputeData();
    }

    private void initViews() {
        tvAdminDisputeTitle = findViewById(R.id.tvAdminDisputeTitle);
        tvAdminReporter = findViewById(R.id.tvAdminReporter);
        tvAdminReportedUser = findViewById(R.id.tvAdminReportedUser);
        tvAdminDescription = findViewById(R.id.tvAdminDescription);
        chipGroupVerdictAction = findViewById(R.id.chipGroupVerdictAction);
        tvRefundPercentLabel = findViewById(R.id.tvRefundPercentLabel);
        sliderRefundPercent = findViewById(R.id.sliderRefundPercent);
        tvTrustPenaltyLabel = findViewById(R.id.tvTrustPenaltyLabel);
        sliderTrustPenalty = findViewById(R.id.sliderTrustPenalty);
        switchSuspendAccount = findViewById(R.id.switchSuspendAccount);
        etAdminNotes = findViewById(R.id.etAdminNotes);
        btnExecuteVerdict = findViewById(R.id.btnExecuteVerdict);

        sliderRefundPercent.addOnChangeListener((slider, value, fromUser) ->
                tvRefundPercentLabel.setText("Deposit Refund to Borrower: " + (int) value + "%"));

        sliderTrustPenalty.addOnChangeListener((slider, value, fromUser) ->
                tvTrustPenaltyLabel.setText("Trust Score Penalty Deduction: -" + (int) value + " pts"));

        btnExecuteVerdict.setOnClickListener(v -> executeAdminVerdict());
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void loadDisputeData() {
        disputeRepository.getDisputeByIdLive(disputeId).observe(this, dispute -> {
            if (dispute != null) {
                currentDispute = dispute;
                tvAdminDisputeTitle.setText("Dispute #" + dispute.getId() + ": " + dispute.getDisputeType());
                tvAdminReporter.setText("Reporter: " + dispute.getReporterName() + " (ID: " + dispute.getReporterId() + ")");
                tvAdminReportedUser.setText("Reported Student ID: " + (dispute.getReportedStudentId() > 0 ? dispute.getReportedStudentId() : "N/A"));
                tvAdminDescription.setText(dispute.getDescription());
            }
        });
    }

    private void executeAdminVerdict() {
        if (currentDispute == null) {
            Toast.makeText(this, "No dispute loaded", Toast.LENGTH_SHORT).show();
            return;
        }

        btnExecuteVerdict.setEnabled(false);

        boolean isDismiss = chipGroupVerdictAction.getCheckedChipId() == R.id.chipDismiss;
        String status = isDismiss ? Dispute.STATUS_DISMISSED : Dispute.STATUS_RESOLVED;
        int refundPercent = (int) sliderRefundPercent.getValue();
        int trustPenalty = (int) sliderTrustPenalty.getValue();
        boolean suspendUser = switchSuspendAccount.isChecked();
        String notes = etAdminNotes.getText() != null ? etAdminNotes.getText().toString().trim() : "";

        long reportedUserId = currentDispute.getReportedStudentId() > 0 ? currentDispute.getReportedStudentId() : 3L;

        disputeRepository.executeAdminVerdict(
                disputeId,
                status,
                notes,
                refundPercent,
                trustPenalty,
                suspendUser,
                reportedUserId,
                new DisputeRepository.AdminActionCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(AdminModerationActivity.this, "Admin Verdict Executed Successfully!", Toast.LENGTH_LONG).show();

                        // Dispatch notifications to reporter and reported student
                        notificationRepository.sendNotification(
                                currentDispute.getReporterId(),
                                "Admin Dispute Verdict Issued",
                                "Dispute #" + disputeId + " verdict: " + status + ". " + notes,
                                NotificationModel.DEEP_LINK_REPORT_STATUS
                        );

                        notificationRepository.sendNotification(
                                reportedUserId,
                                "Campus Safety Notice",
                                "Dispute #" + disputeId + " verdict finalized. Trust penalty: -" + trustPenalty + " pts.",
                                NotificationModel.DEEP_LINK_REPORT_STATUS
                        );

                        finish();
                    }

                    @Override
                    public void onError(Exception e) {
                        btnExecuteVerdict.setEnabled(true);
                        Toast.makeText(AdminModerationActivity.this, "Error executing verdict", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
