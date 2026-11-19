package com.borrowbox.app.ui.dispute;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.borrowbox.app.R;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.DisputeRepository;
import com.borrowbox.app.data.repository.NotificationRepository;
import com.borrowbox.app.model.DamageReport;
import com.borrowbox.app.model.MisconductReport;
import com.borrowbox.app.model.NotificationModel;
import com.borrowbox.app.model.Report;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;

public class ReportIncidentActivity extends AppCompatActivity {

    public static final String EXTRA_RENTAL_ID = "extra_rental_id";
    public static final String EXTRA_INITIAL_CATEGORY = "extra_initial_category";

    private long rentalId = 1L;
    private String selectedCategory = "Damaged Gear";
    private final List<String> evidencePhotos = new ArrayList<>();

    private ChipGroup chipGroupCategory;
    private Chip chipDamagedGear;
    private Chip chipLostItem;
    private Chip chipFakeListing;
    private Chip chipRudeBehaviour;
    private TextInputLayout tilEstimatedLoss;
    private TextInputEditText etEstimatedLoss;
    private TextInputEditText etDescription;
    private MaterialButton btnAddEvidencePhoto;
    private MaterialButton btnSubmitReport;
    private ProgressBar progressBarReport;

    private DisputeRepository disputeRepository;
    private NotificationRepository notificationRepository;
    private SessionManager sessionManager;

    public static Intent createIntent(Context context, long rentalId, String category) {
        Intent intent = new Intent(context, ReportIncidentActivity.class);
        intent.putExtra(EXTRA_RENTAL_ID, rentalId);
        intent.putExtra(EXTRA_INITIAL_CATEGORY, category);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);

        rentalId = getIntent().getLongExtra(EXTRA_RENTAL_ID, 1L);
        String initialCat = getIntent().getStringExtra(EXTRA_INITIAL_CATEGORY);

        disputeRepository = DisputeRepository.getInstance(this);
        notificationRepository = NotificationRepository.getInstance(this);
        sessionManager = new SessionManager(this);

        initViews();
        setupToolbar();

        if (initialCat != null && !initialCat.isEmpty()) {
            selectedCategory = initialCat;
            selectCategoryChip(initialCat);
        }
    }

    private void initViews() {
        chipGroupCategory = findViewById(R.id.chipGroupCategory);
        chipDamagedGear = findViewById(R.id.chipDamagedGear);
        chipLostItem = findViewById(R.id.chipLostItem);
        chipFakeListing = findViewById(R.id.chipFakeListing);
        chipRudeBehaviour = findViewById(R.id.chipRudeBehaviour);
        tilEstimatedLoss = findViewById(R.id.tilEstimatedLoss);
        etEstimatedLoss = findViewById(R.id.etEstimatedLoss);
        etDescription = findViewById(R.id.etDescription);
        btnAddEvidencePhoto = findViewById(R.id.btnAddEvidencePhoto);
        btnSubmitReport = findViewById(R.id.btnSubmitReport);
        progressBarReport = findViewById(R.id.progressBarReport);

        chipGroupCategory.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.contains(R.id.chipDamagedGear)) {
                selectedCategory = "Damaged Gear";
                tilEstimatedLoss.setVisibility(View.VISIBLE);
            } else if (checkedIds.contains(R.id.chipLostItem)) {
                selectedCategory = "Lost / Unreturned Item";
                tilEstimatedLoss.setVisibility(View.VISIBLE);
            } else if (checkedIds.contains(R.id.chipFakeListing)) {
                selectedCategory = "Fake Listing";
                tilEstimatedLoss.setVisibility(View.GONE);
            } else if (checkedIds.contains(R.id.chipRudeBehaviour)) {
                selectedCategory = "Rude or Threatening Behaviour";
                tilEstimatedLoss.setVisibility(View.GONE);
            }
        });

        btnAddEvidencePhoto.setOnClickListener(v -> {
            evidencePhotos.add("evidence_photo_" + (evidencePhotos.size() + 1) + ".jpg");
            Toast.makeText(this, "Evidence photo attached (" + evidencePhotos.size() + "/3)", Toast.LENGTH_SHORT).show();
        });

        btnSubmitReport.setOnClickListener(v -> submitIncidentReport());
    }

    private void selectCategoryChip(String category) {
        if ("Damaged Gear".equalsIgnoreCase(category)) {
            chipDamagedGear.setChecked(true);
        } else if (category.contains("Lost")) {
            chipLostItem.setChecked(true);
        } else if (category.contains("Fake")) {
            chipFakeListing.setChecked(true);
        } else {
            chipRudeBehaviour.setChecked(true);
        }
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void submitIncidentReport() {
        String description = etDescription.getText() != null ? etDescription.getText().toString().trim() : "";

        if (description.isEmpty()) {
            etDescription.setError("Please describe the incident");
            return;
        }

        double lossAmount = 0.0;
        if (tilEstimatedLoss.getVisibility() == View.VISIBLE && etEstimatedLoss.getText() != null) {
            try {
                lossAmount = Double.parseDouble(etEstimatedLoss.getText().toString());
            } catch (NumberFormatException ignored) {}
        }

        long reporterId = sessionManager.getLoggedInUserId();
        Report report;

        // Polymorphic creation of OOP domain models
        if ("Damaged Gear".equals(selectedCategory)) {
            DamageReport dr = new DamageReport();
            dr.setReporterId(reporterId);
            dr.setRentalId(rentalId);
            dr.setDescription(description);
            dr.setEvidenceUrls(evidencePhotos);
            dr.setEstimatedLoss(lossAmount);
            dr.setOwnerDeclaration("Owner reported item damaged during rental period");
            report = dr;
        } else {
            MisconductReport mr = new MisconductReport();
            mr.setReporterId(reporterId);
            mr.setRentalId(rentalId);
            mr.setDescription(description);
            mr.setEvidenceUrls(evidencePhotos);
            mr.setCategory(selectedCategory);
            mr.setReportedUserId(2L); // Default counterparty for demo
            report = mr;
        }

        progressBarReport.setVisibility(View.VISIBLE);
        btnSubmitReport.setEnabled(false);

        disputeRepository.fileReport(report, new DisputeRepository.DisputeCallback() {
            @Override
            public void onSuccess(long disputeId) {
                progressBarReport.setVisibility(View.GONE);
                Toast.makeText(ReportIncidentActivity.this, "Incident Report Submitted!", Toast.LENGTH_SHORT).show();

                // Dispatch Notification to user & admin
                notificationRepository.sendNotification(
                        reporterId,
                        "Dispute Report Filed",
                        "Report #" + disputeId + " (" + selectedCategory + ") is now Under Review.",
                        NotificationModel.DEEP_LINK_REPORT_STATUS
                );

                // Open ReportStatusActivity
                Intent intent = ReportStatusActivity.createIntent(ReportIncidentActivity.this, disputeId);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Exception e) {
                progressBarReport.setVisibility(View.GONE);
                btnSubmitReport.setEnabled(true);
                Toast.makeText(ReportIncidentActivity.this, "Failed to submit report: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
