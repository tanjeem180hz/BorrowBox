package com.borrowbox.app.util;

import androidx.annotation.NonNull;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility for formatting Bangladesh Taka currency throughout the BorrowBox application.
 */
public final class CurrencyUtils {

    private CurrencyUtils() {
        // Prevent instantiation
    }

    @NonNull
    public static String format(int amount) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        return "৳" + formatter.format(amount);
    }

    @NonNull
    public static String format(double amount) {
        return format((int) Math.round(amount));
    }
}
