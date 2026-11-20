package com.borrowbox.app.model;

import java.util.Arrays;
import java.util.List;

/**
 * Concrete domain model for User Misconduct, Fake Listings, and Lost/Unreturned Items.
 * Extends abstract {@link Report}.
 */
public class MisconductReport extends Report {

    public static final String CATEGORY_FAKE_LISTING = "Fake Listing";
    public static final String CATEGORY_RUDE_BEHAVIOUR = "Rude or Threatening Behaviour";
    public static final String CATEGORY_LOST_ITEM = "Lost / Unreturned Item";

    public static final List<String> VALID_CATEGORIES = Arrays.asList(
            CATEGORY_FAKE_LISTING, CATEGORY_RUDE_BEHAVIOUR, CATEGORY_LOST_ITEM
    );

    private long reportedUserId;
    private String category = CATEGORY_RUDE_BEHAVIOUR;
    private String adminNotes = "";

    public MisconductReport() {
        super();
        setIssueType("Misconduct");
    }

    public MisconductReport(String reportId, long reporterId, long rentalId, String description,
                            List<String> evidenceUrls, String status, long createdAt,
                            long reportedUserId, String category, String adminNotes) {
        super(reportId, reporterId, rentalId, "Misconduct", description, evidenceUrls, status, createdAt);
        setReportedUserId(reportedUserId);
        setCategory(category);
        setAdminNotes(adminNotes);
    }

    public long getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(long reportedUserId) {
        this.reportedUserId = Math.max(0, reportedUserId);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category != null && VALID_CATEGORIES.contains(category)) {
            this.category = category;
        } else {
            this.category = CATEGORY_RUDE_BEHAVIOUR;
        }
    }

    public String getAdminNotes() {
        return adminNotes;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes != null ? adminNotes : "";
    }
}
