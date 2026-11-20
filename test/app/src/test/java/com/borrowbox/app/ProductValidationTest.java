package com.borrowbox.app;

import com.borrowbox.app.util.ProductValidationUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for ProductValidationUtils.
 */
public class ProductValidationTest {

    @Test
    public void testValidTitle() {
        assertTrue(ProductValidationUtils.isValidTitle("Scientific Calculator"));
        assertFalse(ProductValidationUtils.isValidTitle("Hi"));
        assertFalse(ProductValidationUtils.isValidTitle(null));
    }

    @Test
    public void testValidPrice() {
        assertTrue(ProductValidationUtils.isValidPrice(50.0));
        assertFalse(ProductValidationUtils.isValidPrice(0.0));
        assertFalse(ProductValidationUtils.isValidPrice(-10.0));
    }

    @Test
    public void testValidDeposit() {
        assertTrue(ProductValidationUtils.isValidDeposit(200.0));
        assertTrue(ProductValidationUtils.isValidDeposit(0.0));
        assertFalse(ProductValidationUtils.isValidDeposit(-50.0));
    }

    @Test
    public void testValidCategory() {
        assertTrue(ProductValidationUtils.isValidCategory("Electronics"));
        assertFalse(ProductValidationUtils.isValidCategory("Select Category"));
        assertFalse(ProductValidationUtils.isValidCategory(null));
        assertFalse(ProductValidationUtils.isValidCategory("   "));
    }

    @Test
    public void testSanitizeDescription() {
        assertEquals("Clean text", ProductValidationUtils.sanitizeDescription("  Clean text  "));
        assertEquals("", ProductValidationUtils.sanitizeDescription(null));
    }
}
