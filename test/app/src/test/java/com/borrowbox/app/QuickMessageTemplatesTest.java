package com.borrowbox.app;

import com.borrowbox.app.util.QuickMessageTemplates;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for QuickMessageTemplates.
 */
public class QuickMessageTemplatesTest {

    @Test
    public void testHandoverQuickRepliesNotNullAndNotEmpty() {
        List<String> replies = QuickMessageTemplates.getHandoverQuickReplies();
        assertNotNull(replies);
        assertEquals(5, replies.size());
        assertTrue(replies.contains(QuickMessageTemplates.TEMPLATE_ARRIVED));
        assertTrue(replies.contains(QuickMessageTemplates.TEMPLATE_LIBRARY));
    }
}
