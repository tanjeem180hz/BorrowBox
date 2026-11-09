package com.borrowbox.app.domain;

import androidx.annotation.NonNull;
import com.borrowbox.app.model.RentalStatus;

/**
 * Enforces legal state transitions for the rental lifecycle.
 */
public final class RentalStateMachine {

    private RentalStateMachine() {
    }

    public static boolean canTransition(@NonNull RentalStatus current, @NonNull RentalStatus target) {
        if (current == target) {
            return true;
        }

        switch (current) {
            case REQUESTED:
                return target == RentalStatus.CONFIRMED
                        || target == RentalStatus.REJECTED
                        || target == RentalStatus.CANCELLED;

            case CONFIRMED:
                return target == RentalStatus.ACTIVE
                        || target == RentalStatus.CANCELLED;

            case ACTIVE:
                return target == RentalStatus.OVERDUE
                        || target == RentalStatus.RETURNED;

            case OVERDUE:
                return target == RentalStatus.RETURNED;

            case RETURNED:
            case REJECTED:
            case CANCELLED:
            default:
                // Terminal states
                return false;
        }
    }

    public static void validateTransition(@NonNull RentalStatus current, @NonNull RentalStatus target) {
        if (!canTransition(current, target)) {
            throw new IllegalStateException("Illegal rental state transition from "
                    + current.name() + " to " + target.name());
        }
    }
}
