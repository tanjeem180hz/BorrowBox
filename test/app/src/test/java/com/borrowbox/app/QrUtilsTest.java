package com.borrowbox.app;

import com.borrowbox.app.util.QrUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for QrUtils payload format and extraction.
 */
public class QrUtilsTest {

    @Test
    public void testGeneratePayload() {
        String payload = QrUtils.generatePayload(105L, "BB-105");
        assertEquals("BORROWBOX:RENTAL:105:TOKEN:BB-105", payload);
    }

    @Test
    public void testIsValidBorrowBoxPayload() {
        assertTrue(QrUtils.isValidBorrowBoxPayload("BORROWBOX:RENTAL:105:TOKEN:BB-105"));
        assertFalse(QrUtils.isValidBorrowBoxPayload("INVALID:PAYLOAD"));
        assertFalse(QrUtils.isValidBorrowBoxPayload(null));
    }

    @Test
    public void testExtractRentalId() {
        assertEquals(105L, QrUtils.extractRentalId("BORROWBOX:RENTAL:105:TOKEN:BB-105"));
        assertEquals(-1L, QrUtils.extractRentalId("INVALID:PAYLOAD"));
        assertEquals(-1L, QrUtils.extractRentalId(null));
    }
}
