package com.borrowbox.app.model;

import java.util.List;

/**
 * Concrete domain model for Damaged Gear reports filed during or after rental handover.
 * Extends abstract {@link Report}.
 */
public class DamageReport extends Report {

    private String prePhotoUrl = "";
    private String postPhotoUrl = "";
    private double estimatedLoss = 0.0;
    private String ownerDeclaration = "";

    public DamageReport() {
        super();
        setIssueType("Damaged Gear");
    }

    public DamageReport(String reportId, long reporterId, long rentalId, String description,
                        List<String> evidenceUrls, String status, long createdAt,
                        String prePhotoUrl, String postPhotoUrl, double estimatedLoss,
                        String ownerDeclaration) {
        super(reportId, reporterId, rentalId, "Damaged Gear", description, evidenceUrls, status, createdAt);
        setPrePhotoUrl(prePhotoUrl);
        setPostPhotoUrl(postPhotoUrl);
        setEstimatedLoss(estimatedLoss);
        setOwnerDeclaration(ownerDeclaration);
    }

    public String getPrePhotoUrl() {
        return prePhotoUrl;
    }

    public void setPrePhotoUrl(String prePhotoUrl) {
        this.prePhotoUrl = prePhotoUrl != null ? prePhotoUrl : "";
    }

    public String getPostPhotoUrl() {
        return postPhotoUrl;
    }

    public void setPostPhotoUrl(String postPhotoUrl) {
        this.postPhotoUrl = postPhotoUrl != null ? postPhotoUrl : "";
    }

    public double getEstimatedLoss() {
        return estimatedLoss;
    }

    public void setEstimatedLoss(double estimatedLoss) {
        this.estimatedLoss = Math.max(0.0, estimatedLoss);
    }

    public String getOwnerDeclaration() {
        return ownerDeclaration;
    }

    public void setOwnerDeclaration(String ownerDeclaration) {
        this.ownerDeclaration = ownerDeclaration != null ? ownerDeclaration : "";
    }
}
