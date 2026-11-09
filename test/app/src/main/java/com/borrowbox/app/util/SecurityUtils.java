package com.borrowbox.app.util;

import android.util.Base64;
import androidx.annotation.NonNull;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Utility for hashing student passwords with salted SHA-256 (Android API 24+ compatible).
 */
public final class SecurityUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    private SecurityUtils() {
    }

    @NonNull
    public static String generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.encodeToString(salt, Base64.NO_WRAP);
    }

    @NonNull
    public static String hashPassword(@NonNull String password, @NonNull String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(hash, Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    public static boolean verifyPassword(@NonNull String password, @NonNull String salt, @NonNull String expectedHash) {
        String calculated = hashPassword(password, salt);
        return calculated.equals(expectedHash);
    }
}
