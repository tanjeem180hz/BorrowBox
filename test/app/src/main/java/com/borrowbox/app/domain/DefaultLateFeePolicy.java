package com.borrowbox.app.domain;

/**
 * Standard Late Fee Policy: ৳10 for every started 20 minutes overdue.
 */
public class DefaultLateFeePolicy implements LateFeePolicy {

    public static final int FEE_PER_BLOCK = 10;
    public static final long BLOCK_MILLIS = 20 * 60 * 1000L; // 20 minutes

    @Override
    public int calculateLateFee(long overdueMillis) {
        if (overdueMillis <= 0) {
            return 0;
        }
        long blocks = (overdueMillis + BLOCK_MILLIS - 1) / BLOCK_MILLIS;
        return (int) (blocks * FEE_PER_BLOCK);
    }
}
