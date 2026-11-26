package com.borrowbox.app;

import com.borrowbox.app.model.Payment;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for Payment system models and method normalization.
 */
public class PaymentSystemTest {

    @Test
    public void testPaymentMethodValidationAndNormalization() {
        assertTrue(Payment.isValidMethod("bKash"));
        assertTrue(Payment.isValidMethod("Nagad"));
        assertTrue(Payment.isValidMethod("Cash"));
        assertFalse(Payment.isValidMethod(null));
        assertFalse(Payment.isValidMethod("Crypto"));

        assertEquals("bKash", Payment.normalizeMethod("bKash (Mock)"));
        assertEquals("Nagad", Payment.normalizeMethod("Nagad"));
        assertEquals("Cash", Payment.normalizeMethod("Cash Handover"));
    }

    @Test
    public void testPaymentCreationAndStatus() {
        long now = System.currentTimeMillis();
        Payment payment = new Payment(1L, 10L, 2L, 250, "RENTAL_SETTLEMENT", "bKash", "COMPLETED", now);

        assertEquals(1L, payment.getId());
        assertEquals(10L, payment.getRentalId());
        assertEquals(2L, payment.getUserId());
        assertEquals(250, payment.getAmount());
        assertEquals("RENTAL_SETTLEMENT", payment.getType());
        assertEquals("bKash", payment.getMethod());
        assertEquals("COMPLETED", payment.getStatus());
        assertTrue(payment.isCompleted());
        assertEquals(now, payment.getCreatedAt());
    }
}
