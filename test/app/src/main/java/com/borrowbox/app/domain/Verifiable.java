package com.borrowbox.app.domain;

import com.borrowbox.app.model.ProductCondition;

/**
 * Interface contract for condition verification and integrity validation.
 */
public interface Verifiable {

    /**
     * Verifies whether pre-rental and post-rental condition data is complete and clean.
     */
    boolean verifyCondition(ProductCondition condition);

    /**
     * Generates a descriptive verification report string.
     */
    String getVerificationNotes(ProductCondition condition);
}
