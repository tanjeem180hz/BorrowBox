package com.borrowbox.app.domain;

import com.borrowbox.app.model.PriceUnit;
import java.util.Locale;

/**
 * Domain service for calculating rental costs, overdue late fees,
 * and security deposit refunds according to BorrowBox campus policy.
 */
public class RentalFeeCalculator {

    public static final double LATE_FEE_PER_INTERVAL = 10.0; // ৳10
    public static final long LATE_FEE_INTERVAL_MILLIS = 20 * 60 * 1000L; // 20 minutes

    /**
     * Calculates base rental cost according to rate and duration.
     */
    public static double calculateBaseCost(double rate, PriceUnit priceUnit, int durationValue) {
        if (rate <= 0 || durationValue <= 0) {
            return 0.0;
        }
        return rate * durationValue;
    }

    /**
     * Calculates overdue late fee based on BorrowBox policy:
     * ৳10 for every started 20 minutes past the designated return time.
     */
    public static double calculateOverdueLateFee(long overdueMillis) {
        if (overdueMillis <= 0) {
            return 0.0;
        }
        long intervals = (long) Math.ceil((double) overdueMillis / LATE_FEE_INTERVAL_MILLIS);
        return intervals * LATE_FEE_PER_INTERVAL;
    }

    /**
     * Calculates the net deposit refund after deducting overdue late fees.
     */
    public static double calculateDepositRefund(double securityDeposit, double overdueLateFee) {
        double refund = securityDeposit - overdueLateFee;
        return Math.max(0.0, refund);
    }

    /**
     * Formats currency cleanly with the Bangladeshi Taka symbol (৳).
     */
    public static String formatTaka(double amount) {
        return String.format(Locale.ROOT, "৳%.0f", Math.max(0, amount));
    }
}
