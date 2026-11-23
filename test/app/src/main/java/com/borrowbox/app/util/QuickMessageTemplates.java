package com.borrowbox.app.util;

import java.util.Arrays;
import java.util.List;

/**
 * Pre-defined quick chat template messages for campus peer rentals and handovers.
 */
public final class QuickMessageTemplates {

    private QuickMessageTemplates() {
    }

    public static final String TEMPLATE_ARRIVED = "I have arrived at the campus meeting point. Ready for handover!";
    public static final String TEMPLATE_LIBRARY = "I'm near Knowledge Tower Library. See you shortly.";
    public static final String TEMPLATE_RUNNING_LATE = "Running about 5 minutes late due to class. Thanks for waiting!";
    public static final String TEMPLATE_RETURN_READY = "I'm ready to return the item. Let me know when you're available.";
    public static final String TEMPLATE_THANK_YOU = "Item returned safely in excellent condition. Thanks!";

    /**
     * Returns a list of all campus handover quick reply messages.
     */
    public static List<String> getHandoverQuickReplies() {
        return Arrays.asList(
                TEMPLATE_ARRIVED,
                TEMPLATE_LIBRARY,
                TEMPLATE_RUNNING_LATE,
                TEMPLATE_RETURN_READY,
                TEMPLATE_THANK_YOU
        );
    }
}
