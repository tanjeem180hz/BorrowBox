package com.borrowbox.app.domain;

/**
 * Strategy pattern interface for computing rental cost.
 */
public interface PricingStrategy {
    int calculateTotalCost(int unitRate, int durationUnits);
}
