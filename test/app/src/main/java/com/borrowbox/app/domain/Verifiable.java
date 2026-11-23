package com.borrowbox.app.domain;

import com.borrowbox.app.model.ProductCondition;

/**
 * Interface contract for condition verification, integrity validation, and damage detection.
 */
public interface Verifiable {

    /**
     * Verifies whether the pre-rental and post-rental condition data is complete, valid,
     * and shows no clean-return violations.
     *
     * @param condition The {@link ProductCondition} instance to analyze.
     * @return true if pre & post evidence is valid and clean return confirmed; false if discrepancies exist.
     */
    boolean verifyCondition(ProductCondition condition);

    /**
     * Generates a descriptive verification report string detailing status, timestamps, and evidence integrity.
     *
     * @param condition The {@link ProductCondition} instance to analyze.
     * @return A human-readable verification summary.
     */
    String getVerificationNotes(ProductCondition condition);

    /**
     * Calculates an automated damage severity score ranging from 0.0 (Pristine) to 1.0 (Severe Damage).
     *
     * @param condition The {@link ProductCondition} instance to analyze.
     * @return A severity rating between 0.0 and 1.0.
     */
    double calculateDamageSeverity(ProductCondition condition);
}
