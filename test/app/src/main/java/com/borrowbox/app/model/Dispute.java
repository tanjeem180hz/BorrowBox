package com.borrowbox.app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Dispute entity as specified in SRS Module 5 (FR-41, FR-42, FR-43).
 * Represents reports filed by students regarding damaged items, lost items, or conduct,
 * resolved by Administrators with deposit adjustment, warnings, or account suspension.
 */
@Entity(tableName = "disputes")
public class Dispute {

    public static final String TYPE_DAMAGED = "DAMAGED";
    public static final String TYPE_LOST = "LOST";
    public static final String TYPE_LATE_RETURN = "LATE_RETURN";
    public static final String TYPE_MISCONDUCT = "MISCONDUCT";
    public static final String TYPE_FAKE_LISTING = "FAKE_LISTING";

    public static final String STATUS_SUBMITTED = "SUBMITTED";
    public static final String STATUS_UNDER_REVIEW = "UNDER_REVIEW";
    public static final String STATUS_RESOLVED = "RESOLVED";
    public static final String STATUS_DISMISSED = "DISMISSED";

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long rentalId;
    private long reporterId;
    @NonNull private String reporterName = "";
    private long reportedStudentId;
    @NonNull private String reportedStudentName = "";
    private long productId;
    @NonNull private String productTitle = "";

    @NonNull private String disputeType = TYPE_DAMAGED;
    @NonNull private String description = "";
    @NonNull private String evidenceUri = "";
    private double depositAmount;
    @NonNull private String status = STATUS_SUBMITTED;
    @NonNull private String adminVerdict = "";
    private int depositRefundPercentage = 100; // 100 = full refund to borrower, 0 = forfeit to owner
    private long createdAt;
    private long resolvedAt;

    public Dispute() {
    }

    @Ignore
    public Dispute(long id, long rentalId, long reporterId, @NonNull String reporterName,
                   long reportedStudentId, @NonNull String reportedStudentName,
                   long productId, @NonNull String productTitle,
                   @NonNull String disputeType, @NonNull String description,
                   @NonNull String evidenceUri, double depositAmount,
                   @NonNull String status, @NonNull String adminVerdict,
                   int depositRefundPercentage, long createdAt, long resolvedAt) {
        this.id = id;
        this.rentalId = rentalId;
        this.reporterId = reporterId;
        this.reporterName = reporterName;
        this.reportedStudentId = reportedStudentId;
        this.reportedStudentName = reportedStudentName;
        this.productId = productId;
        this.productTitle = productTitle;
        this.disputeType = disputeType;
        this.description = description;
        this.evidenceUri = evidenceUri;
        this.depositAmount = depositAmount;
        this.status = status;
        this.adminVerdict = adminVerdict;
        this.depositRefundPercentage = depositRefundPercentage;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getRentalId() { return rentalId; }
    public void setRentalId(long rentalId) { this.rentalId = rentalId; }

    public long getReporterId() { return reporterId; }
    public void setReporterId(long reporterId) { this.reporterId = reporterId; }

    @NonNull public String getReporterName() { return reporterName; }
    public void setReporterName(@NonNull String reporterName) { this.reporterName = reporterName; }

    public long getReportedStudentId() { return reportedStudentId; }
    public void setReportedStudentId(long reportedStudentId) { this.reportedStudentId = reportedStudentId; }

    @NonNull public String getReportedStudentName() { return reportedStudentName; }
    public void setReportedStudentName(@NonNull String reportedStudentName) { this.reportedStudentName = reportedStudentName; }

    public long getProductId() { return productId; }
    public void setProductId(long productId) { this.productId = productId; }

    @NonNull public String getProductTitle() { return productTitle; }
    public void setProductTitle(@NonNull String productTitle) { this.productTitle = productTitle; }

    @NonNull public String getDisputeType() { return disputeType; }
    public void setDisputeType(@NonNull String disputeType) { this.disputeType = disputeType; }

    @NonNull public String getDescription() { return description; }
    public void setDescription(@NonNull String description) { this.description = description; }

    @NonNull public String getEvidenceUri() { return evidenceUri; }
    public void setEvidenceUri(@NonNull String evidenceUri) { this.evidenceUri = evidenceUri; }

    public double getDepositAmount() { return depositAmount; }
    public void setDepositAmount(double depositAmount) { this.depositAmount = depositAmount; }

    @NonNull public String getStatus() { return status; }
    public void setStatus(@NonNull String status) { this.status = status; }

    @NonNull public String getAdminVerdict() { return adminVerdict; }
    public void setAdminVerdict(@NonNull String adminVerdict) { this.adminVerdict = adminVerdict; }

    public int getDepositRefundPercentage() { return depositRefundPercentage; }
    public void setDepositRefundPercentage(int depositRefundPercentage) { this.depositRefundPercentage = depositRefundPercentage; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(long resolvedAt) { this.resolvedAt = resolvedAt; }
}
