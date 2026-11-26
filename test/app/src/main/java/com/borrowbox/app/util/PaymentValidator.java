package com.borrowbox.app.util;

import java.util.Locale;

/**
 * Utility for validating bKash / Nagad transaction IDs, settlement amounts,
 * and formatting digital payment receipts.
 */
public final class PaymentValidator {

    private PaymentValidator() {
    }

    /**
     * Validates if a transaction ID or reference is non-empty and formatted.
     */
    public static boolean isValidTransactionId(String trnId) {
        if (trnId == null) {
            return false;
        }
        String trimmed = trnId.trim();
        return trimmed.length() >= 6 && trimmed.length() <= 30;
    }

    /**
     * Validates settlement payment amount.
     */
    public static boolean isValidAmount(double amount) {
        return amount > 0 && amount <= 100000;
    }

    /**
     * Generates a clean digital receipt summary string.
     */
    public static String formatReceiptSummary(long rentalId, String method, double amount) {
        String safeMethod = method != null ? method.trim() : "Cash";
        return String.format(Locale.ROOT, "Receipt #%d | Method: %s | Paid: ৳%.0f", rentalId, safeMethod, Math.max(0, amount));
    }
}
