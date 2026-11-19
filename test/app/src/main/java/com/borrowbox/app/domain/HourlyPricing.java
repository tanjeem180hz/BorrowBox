package com.borrowbox.app.domain;

public class HourlyPricing implements PricingStrategy {
    @Override
    public int calculateTotalCost(int unitRate, int hours) {
        if (unitRate < 0 || hours < 0) {
            throw new IllegalArgumentException("Rate and hours must be non-negative");
        }
        return unitRate * hours;
    }
}
