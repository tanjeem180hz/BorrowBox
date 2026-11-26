package com.borrowbox.app;

import com.borrowbox.app.util.PaymentValidator;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for PaymentValidator.
 */
public class PaymentValidatorTest {

    @Test
    public void testValidTransactionId() {
        assertTrue(PaymentValidator.isValidTransactionId("TRN-984210"));
        assertTrue(PaymentValidator.isValidTransactionId("BKASH123456"));
        assertFalse(PaymentValidator.isValidTransactionId("123"));
        assertFalse(PaymentValidator.isValidTransactionId(null));
    }

    @Test
    public void testValidAmount() {
        assertTrue(PaymentValidator.isValidAmount(250.0));
        assertFalse(PaymentValidator.isValidAmount(0.0));
        assertFalse(PaymentValidator.isValidAmount(-50.0));
    }

    @Test
    public void testFormatReceiptSummary() {
        String receipt = PaymentValidator.formatReceiptSummary(105L, "bKash", 240.0);
        assertEquals("Receipt #105 | Method: bKash | Paid: ৳240", receipt);
    }
}
