package com.borrowbox.app.domain;

public class DailyPricing implements PricingStrategy {
    @Override
    public int calculateTotalCost(int unitRate, int days) {
        if (unitRate < 0 || days < 0) {
            throw new IllegalArgumentException("Rate and days must be non-negative");
        }
        return unitRate * days;
    }
}
