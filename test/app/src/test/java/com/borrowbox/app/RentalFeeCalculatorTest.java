package com.borrowbox.app;

import com.borrowbox.app.domain.RentalFeeCalculator;
import com.borrowbox.app.model.PriceUnit;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for RentalFeeCalculator.
 */
public class RentalFeeCalculatorTest {

    @Test
    public void testBaseCostCalculation() {
        // 3 hours @ ৳20/hour = ৳60
        double hourlyCost = RentalFeeCalculator.calculateBaseCost(20.0, PriceUnit.HOUR, 3);
        assertEquals(60.0, hourlyCost, 0.001);

        // 2 days @ ৳100/day = ৳200
        double dailyCost = RentalFeeCalculator.calculateBaseCost(100.0, PriceUnit.DAY, 2);
        assertEquals(200.0, dailyCost, 0.001);

        // Zero duration or rate returns 0
        assertEquals(0.0, RentalFeeCalculator.calculateBaseCost(0, PriceUnit.DAY, 2), 0.001);
        assertEquals(0.0, RentalFeeCalculator.calculateBaseCost(50, PriceUnit.HOUR, 0), 0.001);
    }

    @Test
    public void testOverdueLateFeePolicy() {
        // No delay = ৳0
        assertEquals(0.0, RentalFeeCalculator.calculateOverdueLateFee(0), 0.001);

        // 10 minutes overdue (1 started 20-min interval) = ৳10
        long tenMinsMillis = 10 * 60 * 1000L;
        assertEquals(10.0, RentalFeeCalculator.calculateOverdueLateFee(tenMinsMillis), 0.001);

        // 25 minutes overdue (2 started 20-min intervals) = ৳20
        long twentyFiveMinsMillis = 25 * 60 * 1000L;
        assertEquals(20.0, RentalFeeCalculator.calculateOverdueLateFee(twentyFiveMinsMillis), 0.001);
    }

    @Test
    public void testDepositRefundCalculation() {
        double deposit = 500.0;
        double lateFee = 30.0;

        double refund = RentalFeeCalculator.calculateDepositRefund(deposit, lateFee);
        assertEquals(470.0, refund, 0.001);

        // Late fee exceeds deposit -> refund is capped at ৳0
        assertEquals(0.0, RentalFeeCalculator.calculateDepositRefund(100.0, 150.0), 0.001);
    }

    @Test
    public void testFormatTaka() {
        assertEquals("৳150", RentalFeeCalculator.formatTaka(150.0));
        assertEquals("৳0", RentalFeeCalculator.formatTaka(0.0));
        assertEquals("৳0", RentalFeeCalculator.formatTaka(-50.0));
    }
}
