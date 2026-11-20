package com.borrowbox.app.util;

/**
 * Utility methods for validating product listing inputs such as title, price,
 * deposit, and category.
 */
public class ProductValidationUtils {

    public static final int MIN_TITLE_LENGTH = 3;
    public static final int MAX_TITLE_LENGTH = 100;

    /**
     * Checks whether the product title is valid.
     */
    public static boolean isValidTitle(String title) {
        if (title == null) {
            return false;
        }
        String trimmed = title.trim();
        return trimmed.length() >= MIN_TITLE_LENGTH && trimmed.length() <= MAX_TITLE_LENGTH;
    }

    /**
     * Validates if the rental price is greater than zero and within reasonable bounds.
     */
    public static boolean isValidPrice(double price) {
        return price > 0 && price <= 50000;
    }

    /**
     * Validates security deposit amount.
     */
    public static boolean isValidDeposit(double deposit) {
        return deposit >= 0 && deposit <= 100000;
    }

    /**
     * Checks if the selected category is non-empty and non-null.
     */
    public static boolean isValidCategory(String category) {
        return category != null && !category.trim().isEmpty() && !"Select Category".equalsIgnoreCase(category.trim());
    }

    /**
     * Cleans up and trims description input.
     */
    public static String sanitizeDescription(String description) {
        if (description == null) {
            return "";
        }
        return description.trim();
    }
}
