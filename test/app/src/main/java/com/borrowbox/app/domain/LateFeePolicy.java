package com.borrowbox.app.domain;

public interface LateFeePolicy {
    /**
     * Calculates late fee based on overdue duration in milliseconds.
     */
    int calculateLateFee(long overdueMillis);
}
