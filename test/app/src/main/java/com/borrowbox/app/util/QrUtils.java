package com.borrowbox.app.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public final class QrUtils {

    public static final String PAYLOAD_PREFIX = "BORROWBOX:RENTAL:";

    private QrUtils() {
    }

    /**
     * Formats a structured QR payload string for a rental handover.
     */
    @NonNull
    public static String generatePayload(long rentalId, @NonNull String token) {
        String safeToken = token != null ? token.trim() : "";
        return PAYLOAD_PREFIX + rentalId + ":TOKEN:" + safeToken;
    }

    /**
     * Validates if a QR code payload belongs to BorrowBox rental verification.
     */
    public static boolean isValidBorrowBoxPayload(@Nullable String payload) {
        return payload != null && payload.startsWith(PAYLOAD_PREFIX);
    }

    /**
     * Extracts rental ID from a valid payload, or returns -1 if invalid.
     */
    public static long extractRentalId(@Nullable String payload) {
        if (!isValidBorrowBoxPayload(payload)) {
            return -1L;
        }
        try {
            String[] parts = payload.split(":");
            if (parts.length >= 3) {
                return Long.parseLong(parts[2]);
            }
        } catch (NumberFormatException ignored) {
        }
        return -1L;
    }

    @Nullable
    public static Bitmap generateQrBitmap(@NonNull String content, int size) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;
        } catch (Exception e) {
            return null;
        }
    }
}
