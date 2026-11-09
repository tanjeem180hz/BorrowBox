package com.borrowbox.app.util;

import androidx.annotation.NonNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateUtils {

    private static final SimpleDateFormat DATE_TIME_FORMAT = 
            new SimpleDateFormat("dd MMM, hh:mm a", Locale.US);

    private DateUtils() {
    }

    @NonNull
    public static String formatDateTime(long timestamp) {
        if (timestamp <= 0) return "N/A";
        return DATE_TIME_FORMAT.format(new Date(timestamp));
    }

    @NonNull
    public static String formatDuration(long millis) {
        if (millis <= 0) return "00:00:00";
        long seconds = (millis / 1000) % 60;
        long minutes = (millis / (1000 * 60)) % 60;
        long hours = millis / (1000 * 60 * 60);
        return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
    }
}
