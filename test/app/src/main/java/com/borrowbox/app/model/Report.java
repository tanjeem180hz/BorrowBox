package com.borrowbox.app.model;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract base class for all incident and dispute reports.
 * Employs strict OOP encapsulation and input validation.
 */
public abstract class Report {

    public static final String STATUS_SUBMITTED = "Submitted";
    public static final String STATUS_UNDER_REVIEW = "Under Review";
    public static final String STATUS_RESOLVED = "Resolved";
    public static final String STATUS_DISMISSED = "Dismissed";

    public static final List<String> VALID_STATUSES = Arrays.asList(
            STATUS_SUBMITTED, STATUS_UNDER_REVIEW, STATUS_RESOLVED, STATUS_DISMISSED
    );

    private String reportId = "";
    private long reporterId;
    private long rentalId;
    private String issueType = "General";
    private String description = "";
    private List<String> evidenceUrls = new ArrayList<>();
    private String status = STATUS_SUBMITTED;
    private long createdAt = System.currentTimeMillis();

    public Report() {
    }

    public Report(String reportId, long reporterId, long rentalId, String issueType,
                  String description, List<String> evidenceUrls, String status, long createdAt) {
        setReportId(reportId);
        setReporterId(reporterId);
        setRentalId(rentalId);
        setIssueType(issueType);
        setDescription(description);
        setEvidenceUrls(evidenceUrls);
        setStatus(status);
        setCreatedAt(createdAt);
    }

    @NonNull
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId != null ? reportId : "";
    }

    public long getReporterId() {
        return reporterId;
    }

    public void setReporterId(long reporterId) {
        if (reporterId < 0) {
            throw new IllegalArgumentException("reporterId cannot be negative");
        }
        this.reporterId = reporterId;
    }

    public long getRentalId() {
        return rentalId;
    }

    public void setRentalId(long rentalId) {
        this.rentalId = rentalId;
    }

    @NonNull
    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType != null ? issueType : "General";
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description : "";
    }

    @NonNull
    public List<String> getEvidenceUrls() {
        return evidenceUrls != null ? evidenceUrls : new ArrayList<>();
    }

    public void setEvidenceUrls(List<String> evidenceUrls) {
        this.evidenceUrls = evidenceUrls != null ? new ArrayList<>(evidenceUrls) : new ArrayList<>();
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || !VALID_STATUSES.contains(status)) {
            this.status = STATUS_SUBMITTED;
        } else {
            this.status = status;
        }
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt > 0 ? createdAt : System.currentTimeMillis();
    }
}
