package com.borrowbox.app.ui.condition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.borrowbox.app.R;
import com.borrowbox.app.data.repository.ConditionRepository;
import com.borrowbox.app.data.repository.NotificationRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.domain.PhotoVerification;
import com.borrowbox.app.domain.Verifiable;
import com.borrowbox.app.model.NotificationModel;
import com.borrowbox.app.model.ProductCondition;
import com.borrowbox.app.ui.dispute.ReportIncidentActivity;
import com.borrowbox.app.util.AppExecutors;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import java.util.Date;

public class ConditionComparisonActivity extends AppCompatActivity {

    public static final String EXTRA_RENTAL_ID = "extra_rental_id";

    private long rentalId = 1L;

    private TextView tvVerificationBadge;
    private TextView tvVerificationNotes;
    private ImageView ivPrePhoto;
    private ImageView ivPostPhoto;
    private TextView tvPreMeta;
    private TextView tvPostMeta;
    private TextView tvPreNotes;
    private TextView tvPostNotes;
    private TextView tvDepositAmount;
    private MaterialButton btnConfirmCleanReturn;
    private MaterialButton btnFileDamageReport;
    private MaterialCardView cardVerificationStatus;

    private ConditionRepository conditionRepository;
    private NotificationRepository notificationRepository;
    private final Verifiable verifiable = new PhotoVerification();

    public static Intent createIntent(Context context, long rentalId) {
        Intent intent = new Intent(context, ConditionComparisonActivity.class);
        intent.putExtra(EXTRA_RENTAL_ID, rentalId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_comparison);

        rentalId = getIntent().getLongExtra(EXTRA_RENTAL_ID, 1L);
        conditionRepository = ConditionRepository.getInstance(this);
        notificationRepository = NotificationRepository.getInstance(this);

        initViews();
        setupToolbar();
        loadConditionData();
    }

    private void initViews() {
        tvVerificationBadge = findViewById(R.id.tvVerificationBadge);
        tvVerificationNotes = findViewById(R.id.tvVerificationNotes);
        ivPrePhoto = findViewById(R.id.ivPrePhoto);
        ivPostPhoto = findViewById(R.id.ivPostPhoto);
        tvPreMeta = findViewById(R.id.tvPreMeta);
        tvPostMeta = findViewById(R.id.tvPostMeta);
        tvPreNotes = findViewById(R.id.tvPreNotes);
        tvPostNotes = findViewById(R.id.tvPostNotes);
        tvDepositAmount = findViewById(R.id.tvDepositAmount);
        btnConfirmCleanReturn = findViewById(R.id.btnConfirmCleanReturn);
        btnFileDamageReport = findViewById(R.id.btnFileDamageReport);
        cardVerificationStatus = findViewById(R.id.cardVerificationStatus);

        btnConfirmCleanReturn.setOnClickListener(v -> confirmCleanReturn());
        btnFileDamageReport.setOnClickListener(v -> fileDamageReport());
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void loadConditionData() {
        conditionRepository.getConditionForRentalLive(rentalId).observe(this, condition -> {
            if (condition == null) {
                // Populate demo mock condition for testing
                condition = createMockCondition(rentalId);
            }
            bindConditionData(condition);
        });
    }

    private ProductCondition createMockCondition(long rentalId) {
        ProductCondition mock = new ProductCondition();
        mock.setCheckId("CHK-" + rentalId);
        mock.setRentalId(rentalId);
        mock.setPreNotes("Pre-pickup photo verified. No scratches.");
        mock.setPostNotes("Post-return photo captured. Clean condition.");
        long now = System.currentTimeMillis();
        mock.setPreTimestamp(now - 3600000L * 4);
        mock.setPostTimestamp(now);
        mock.setPreGpsLocation("23.8762,90.3201");
        mock.setPostGpsLocation("23.8762,90.3201");
        return mock;
    }

    private void bindConditionData(ProductCondition condition) {
        boolean isValid = verifiable.verifyCondition(condition);
        String verificationSummary = verifiable.getVerificationNotes(condition);

        if (isValid) {
            tvVerificationBadge.setText("🛡️ Verification Status: PASS - Clean Return");
            tvVerificationBadge.setTextColor(getColor(R.color.bottom_nav_color));
        } else {
            tvVerificationBadge.setText("⚠️ Verification Status: DISCREPANCY DETECTED");
            tvVerificationBadge.setTextColor(getColor(com.google.android.material.R.color.design_default_color_error));
        }

        tvVerificationNotes.setText(verificationSummary);

        // Pre-Rental Details
        String preTimeStr = condition.getPreTimestamp() > 0 ?
                DateFormat.format("yyyy-MM-dd HH:mm", new Date(condition.getPreTimestamp())).toString() : "Recorded";
        tvPreMeta.setText("🕒 " + preTimeStr + "\n📍 " + condition.getPreGpsLocation());
        tvPreNotes.setText("Notes: " + (condition.getPreNotes().isEmpty() ? "None" : condition.getPreNotes()));

        // Post-Rental Details
        String postTimeStr = condition.getPostTimestamp() > 0 ?
                DateFormat.format("yyyy-MM-dd HH:mm", new Date(condition.getPostTimestamp())).toString() : "Recorded";
        tvPostMeta.setText("🕒 " + postTimeStr + "\n📍 " + condition.getPostGpsLocation());
        tvPostNotes.setText("Notes: " + (condition.getPostNotes().isEmpty() ? "None" : condition.getPostNotes()));

        // Glide image loading
        if (!condition.getPrePhotoUrl().isEmpty()) {
            Glide.with(this)
                    .load(condition.getPrePhotoUrl().get(0))
                    .placeholder(R.drawable.sample_product_photo)
                    .error(R.drawable.sample_product_photo)
                    .into(ivPrePhoto);
        } else {
            ivPrePhoto.setImageResource(R.drawable.sample_product_photo);
        }

        if (!condition.getPostPhotoUrl().isEmpty()) {
            Glide.with(this)
                    .load(condition.getPostPhotoUrl().get(0))
                    .placeholder(R.drawable.sample_product_photo)
                    .error(R.drawable.sample_product_photo)
                    .into(ivPostPhoto);
        } else {
            ivPostPhoto.setImageResource(R.drawable.sample_product_photo);
        }
    }

    private void confirmCleanReturn() {
        btnConfirmCleanReturn.setEnabled(false);
        Toast.makeText(this, "Clean return confirmed! Security deposit released.", Toast.LENGTH_LONG).show();

        // Dispatch notification
        notificationRepository.sendNotification(
                1L,
                "Security Deposit Refunded",
                "Your deposit of ৳200 for Rental #" + rentalId + " has been fully refunded.",
                NotificationModel.DEEP_LINK_RENTAL_DETAILS
        );

        finish();
    }

    private void fileDamageReport() {
        // Open ReportIncidentActivity with pre-filled category
        Intent intent = ReportIncidentActivity.createIntent(this, rentalId, "Damaged Gear");
        startActivity(intent);
    }
}
