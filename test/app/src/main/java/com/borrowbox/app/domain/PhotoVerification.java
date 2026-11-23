package com.borrowbox.app.domain;

import com.borrowbox.app.model.ProductCondition;

/**
 * Concrete implementation of {@link Verifiable} interface for photo & timestamp verification.
 */
public class PhotoVerification implements Verifiable {

    @Override
    public boolean verifyCondition(ProductCondition condition) {
        if (condition == null) {
            return false;
        }

        // Must have at least 1 pre-rental photo
        if (condition.getPrePhotoUrl() == null || condition.getPrePhotoUrl().isEmpty()) {
            return false;
        }

        // Must have at least 1 post-rental photo
        if (condition.getPostPhotoUrl() == null || condition.getPostPhotoUrl().isEmpty()) {
            return false;
        }

        // Post timestamp must be after or equal to pre timestamp
        if (condition.getPostTimestamp() < condition.getPreTimestamp()) {
            return false;
        }

        // Check if post-rental notes explicitly mention damage
        String postNotes = condition.getPostNotes().toLowerCase();
        if (postNotes.contains("damage") || postNotes.contains("broken") || postNotes.contains("scratched") || postNotes.contains("faulty")) {
            return false;
        }

        return true;
    }

    @Override
    public String getVerificationNotes(ProductCondition condition) {
        if (condition == null) {
            return "No condition check record available.";
        }

        boolean preOk = condition.getPrePhotoUrl() != null && !condition.getPrePhotoUrl().isEmpty();
        boolean postOk = condition.getPostPhotoUrl() != null && !condition.getPostPhotoUrl().isEmpty();

        StringBuilder sb = new StringBuilder();
        sb.append("Pre-Rental Photos: ").append(preOk ? condition.getPrePhotoUrl().size() + " photo(s)" : "Missing").append("\n");
        sb.append("Post-Rental Photos: ").append(postOk ? condition.getPostPhotoUrl().size() + " photo(s)" : "Missing").append("\n");

        if (preOk && postOk) {
            if (verifyCondition(condition)) {
                sb.append("Verification Status: PASS - Clean Return Confirmed.");
            } else {
                sb.append("Verification Status: ATTENTION REQUIRED - Discrepancy or Potential Damage Reported.");
                sb.append("\nEstimated Severity Index: ").append(String.format(java.util.Locale.US, "%.0f%%", calculateDamageSeverity(condition) * 100));
            }
        } else {
            sb.append("Verification Status: INCOMPLETE EVIDENCE.");
        }

        return sb.toString();
    }

    @Override
    public double calculateDamageSeverity(ProductCondition condition) {
        if (condition == null) return 0.0;

        double severity = 0.0;
        String notes = condition.getPostNotes().toLowerCase();

        if (notes.contains("broken") || notes.contains("shattered") || notes.contains("crack")) {
            severity += 0.6;
        } else if (notes.contains("scratch") || notes.contains("dent") || notes.contains("stain")) {
            severity += 0.3;
        }

        if (notes.contains("heavy") || notes.contains("severe") || notes.contains("major")) {
            severity += 0.3;
        }

        if (condition.getPostPhotoUrl() == null || condition.getPostPhotoUrl().isEmpty()) {
            severity += 0.2;
        }

        return Math.min(1.0, severity);
    }
}
