package com.borrowbox.app.domain;

/**
 * Trust Score calculation engine for student accounts.
 * Bounded strictly between 0 and 100.
 */
public final class TrustScoreEngine {

    private TrustScoreEngine() {
    }

    /**
     * Calculates the Trust Score for a student.
     *
     * @param ratingAverage    Average star rating (0.0 to 5.0)
     * @param ratingCount      Number of ratings received
     * @param completedRentals Total successfully completed rentals
     * @param overdueCount     Count of overdue return infractions
     * @param isVerified       Whether student email/ID is verified
     * @return Trust score between 0 and 100
     */
    public static int calculateTrustScore(double ratingAverage, int ratingCount,
                                          int completedRentals, int overdueCount,
                                          boolean isVerified) {
        double score = 50.0; // Base score

        if (ratingCount > 0) {
            // Scale 0.0-5.0 rating to up to 30 points
            double ratingPoints = (Math.min(5.0, Math.max(0.0, ratingAverage)) / 5.0) * 30.0;
            score += ratingPoints;
        } else {
            score += 15.0; // Neutral rating allowance
        }

        // Up to 15 points for completed rentals (1 point per rental capped at 15)
        score += Math.min(15.0, completedRentals * 1.0);

        // Verified student bonus (+5 points)
        if (isVerified) {
            score += 5.0;
        }

        // Deductions for infractions
        score -= (overdueCount * 8.0);

        // Clamp between 0 and 100
        int finalScore = (int) Math.round(score);
        return Math.max(0, Math.min(100, finalScore));
    }
}
