package com.borrowbox.app.util;

import androidx.annotation.NonNull;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Utility for hashing student passwords with salted SHA-256 (Android API 24+ & JVM unit test compatible).
 */
public final class SecurityUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    private SecurityUtils() {
    }

    private static String encodeBase64(byte[] bytes) {
        try {
            return java.util.Base64.getEncoder().encodeToString(bytes);
        } catch (Throwable t) {
            return android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP);
        }
    }

    @NonNull
    public static String generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return encodeBase64(salt);
    }

    @NonNull
    public static String hashPassword(@NonNull String password, @NonNull String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return encodeBase64(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    public static boolean verifyPassword(@NonNull String password, @NonNull String salt, @NonNull String expectedHash) {
        String calculated = hashPassword(password, salt);
        return calculated.equals(expectedHash);
    }
}
