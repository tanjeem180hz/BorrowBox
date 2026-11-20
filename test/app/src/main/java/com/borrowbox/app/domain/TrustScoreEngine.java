package com.borrowbox.app.domain;

/**
 * Trust Score calculation engine for student accounts.
 * Provides both SRS Module 5 (FR-39) formula and heuristic weighted scoring,
 * bounded strictly between 0 and 100.
 */
public final class TrustScoreEngine {

    public static final int BASE_SRS_TRUST_SCORE = 80;
    public static final int TRUSTED_PEER_THRESHOLD = 90;
    public static final int TRUSTED_PEER_MIN_RENTALS = 10;

    private TrustScoreEngine() {
    }

    /**
     * SRS FR-39 Formula:
     * Trust Score = 80 + 1.5 × Completed + 2.0 × AvgRating − 4.0 × LateReturns − 10.0 × DisputePenalties
     * Clamped strictly between 0 and 100.
     */
    public static int calculateSrsTrustScore(int completedRentals, double avgRating,
                                             int lateReturns, int disputePenalties) {
        double score = BASE_SRS_TRUST_SCORE
                + (1.5 * completedRentals)
                + (2.0 * Math.min(5.0, Math.max(0.0, avgRating)))
                - (4.0 * lateReturns)
                - (10.0 * disputePenalties);

        int rounded = (int) Math.round(score);
        return Math.max(0, Math.min(100, rounded));
    }

    /**
     * Checks if the student qualifies for the "Trusted Campus Peer" badge (SRS FR-40).
     */
    public static boolean isTrustedCampusPeer(int trustScore, int completedRentals) {
        return trustScore >= TRUSTED_PEER_THRESHOLD && completedRentals >= TRUSTED_PEER_MIN_RENTALS;
    }

    /**
     * Calculates the Trust Score for a student using heuristic weighting.
     * Base 50 + up to 30 rating points + up to 15 completions + 5 verified bonus - 8 per overdue.
     */
    public static int calculateTrustScore(double ratingAverage, int ratingCount,
                                          int completedRentals, int overdueCount,
                                          boolean isVerified) {
        double score = 50.0;

        if (ratingCount > 0) {
            double ratingPoints = (Math.min(5.0, Math.max(0.0, ratingAverage)) / 5.0) * 30.0;
            score += ratingPoints;
        } else {
            score += 15.0;
        }

        score += Math.min(15.0, completedRentals * 1.0);

        if (isVerified) {
            score += 5.0;
        }

        score -= (overdueCount * 8.0);

        int finalScore = (int) Math.round(score);
        return Math.max(0, Math.min(100, finalScore));
    }
}
