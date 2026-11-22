package com.borrowbox.app.util;

import java.util.Locale;

/**
 * Utility functions for geo-distance calculations, walking ETA estimation,
 * and location formatting for campus meeting maps.
 */
public final class MapUtils {

    public static final double AVERAGE_WALKING_SPEED_METERS_PER_MIN = 83.33; // ~5 km/h

    private MapUtils() {
    }

    /**
     * Calculates the great-circle distance between two GPS coordinates in meters
     * using the Haversine formula.
     */
    public static double calculateDistanceMeters(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_METERS = 6371000;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_METERS * c;
    }

    /**
     * Estimates walking time in minutes based on distance in meters.
     */
    public static int calculateWalkingEtaMinutes(double distanceMeters) {
        if (distanceMeters <= 0) {
            return 1;
        }
        int minutes = (int) Math.round(distanceMeters / AVERAGE_WALKING_SPEED_METERS_PER_MIN);
        return Math.max(1, minutes);
    }

    /**
     * Formats distance in meters as a readable string (e.g., "350 m" or "1.2 km").
     */
    public static String formatDistance(double distanceMeters) {
        if (distanceMeters < 1000) {
            return String.format(Locale.ROOT, "%d m", (int) Math.round(distanceMeters));
        } else {
            return String.format(Locale.ROOT, "%.1f km", distanceMeters / 1000.0);
        }
    }
}
