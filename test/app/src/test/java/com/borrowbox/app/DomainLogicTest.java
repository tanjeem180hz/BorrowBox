package com.borrowbox.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.borrowbox.app.domain.DailyPricing;
import com.borrowbox.app.domain.DefaultLateFeePolicy;
import com.borrowbox.app.domain.HourlyPricing;
import com.borrowbox.app.domain.LateFeePolicy;
import com.borrowbox.app.domain.PricingStrategy;
import com.borrowbox.app.domain.RentalStateMachine;
import com.borrowbox.app.domain.TrustScoreEngine;
import com.borrowbox.app.model.RentalStatus;

import org.junit.Test;

public class DomainLogicTest {

    @Test
    public void testHourlyPricingCalculation() {
        PricingStrategy hourly = new HourlyPricing();
        // 2 hours at ৳20/hour = ৳40
        assertEquals(40, hourly.calculateTotalCost(20, 2));
        assertEquals(0, hourly.calculateTotalCost(20, 0));
        assertEquals(100, hourly.calculateTotalCost(25, 4));
    }

    @Test
    public void testDailyPricingCalculation() {
        PricingStrategy daily = new DailyPricing();
        // 3 days at ৳100/day = ৳300
        assertEquals(300, daily.calculateTotalCost(100, 3));
        assertEquals(150, daily.calculateTotalCost(150, 1));
    }

    @Test
    public void testLateFeePolicy() {
        LateFeePolicy policy = new DefaultLateFeePolicy();

        // On time -> ৳0
        assertEquals(0, policy.calculateLateFee(0));
        assertEquals(0, policy.calculateLateFee(-5000));

        // 10 minutes overdue -> 1 block -> ৳10
        assertEquals(10, policy.calculateLateFee(10 * 60 * 1000L));

        // Exactly 20 minutes overdue -> 1 block -> ৳10
        assertEquals(10, policy.calculateLateFee(20 * 60 * 1000L));

        // 21 minutes overdue -> starts 2nd block -> ৳20
        assertEquals(20, policy.calculateLateFee(21 * 60 * 1000L));

        // 45 minutes overdue -> 3 blocks (20+20+5) -> ৳30
        assertEquals(30, policy.calculateLateFee(45 * 60 * 1000L));
    }

    @Test
    public void testTrustScoreEngine() {
        // High performing verified student (e.g. 5.0 rating, 15 completions, 0 overdues, verified)
        int score = TrustScoreEngine.calculateTrustScore(5.0, 10, 15, 0, true);
        // Base 50 + 30 (rating) + 15 (completions) + 5 (verified) = 100
        assertEquals(100, score);

        // Typical active student (4.8 rating, 10 completions, verified)
        int midScore = TrustScoreEngine.calculateTrustScore(4.8, 10, 10, 0, true);
        assertTrue(midScore >= 90 && midScore <= 95);

        // Score is strictly clamped between 0 and 100
        int lowScore = TrustScoreEngine.calculateTrustScore(1.0, 5, 0, 10, false);
        assertTrue(lowScore >= 0);
    }

    @Test
    public void testRentalStateMachineValidTransitions() {
        // Legal path: REQUESTED -> CONFIRMED -> ACTIVE -> RETURNED
        assertTrue(RentalStateMachine.canTransition(RentalStatus.REQUESTED, RentalStatus.CONFIRMED));
        assertTrue(RentalStateMachine.canTransition(RentalStatus.CONFIRMED, RentalStatus.ACTIVE));
        assertTrue(RentalStateMachine.canTransition(RentalStatus.ACTIVE, RentalStatus.RETURNED));

        // Active to overdue and overdue to returned
        assertTrue(RentalStateMachine.canTransition(RentalStatus.ACTIVE, RentalStatus.OVERDUE));
        assertTrue(RentalStateMachine.canTransition(RentalStatus.OVERDUE, RentalStatus.RETURNED));

        // Rejection / cancellation
        assertTrue(RentalStateMachine.canTransition(RentalStatus.REQUESTED, RentalStatus.REJECTED));
        assertTrue(RentalStateMachine.canTransition(RentalStatus.REQUESTED, RentalStatus.CANCELLED));
        assertTrue(RentalStateMachine.canTransition(RentalStatus.CONFIRMED, RentalStatus.CANCELLED));
    }

    @Test(expected = IllegalStateException.class)
    public void testRentalStateMachineIllegalTransitionThrows() {
        // Illegal: Directly from REQUESTED to RETURNED
        RentalStateMachine.validateTransition(RentalStatus.REQUESTED, RentalStatus.RETURNED);
    }

    @Test
    public void testRentalStateMachineTerminalStates() {
        // Terminal states cannot transition to anything
        assertFalse(RentalStateMachine.canTransition(RentalStatus.RETURNED, RentalStatus.ACTIVE));
        assertFalse(RentalStateMachine.canTransition(RentalStatus.REJECTED, RentalStatus.CONFIRMED));
        assertFalse(RentalStateMachine.canTransition(RentalStatus.CANCELLED, RentalStatus.REQUESTED));
    }

    @Test
    public void testChatMessageAttributesAndLikeStatus() {
        com.borrowbox.app.model.ChatMessage msg = new com.borrowbox.app.model.ChatMessage(
                1L, 10L, 1L, 2L, "Hi, is this charger available?",
                System.currentTimeMillis(), true, "SENT", false
        );

        assertTrue(msg.isFromMe());
        assertEquals("SENT", msg.getStatus());
        assertFalse(msg.isLike());
        assertEquals("Hi, is this charger available?", msg.getText());

        msg.setStatus("DELIVERED");
        assertEquals("DELIVERED", msg.getStatus());

        msg.setStatus("SEEN");
        assertEquals("SEEN", msg.getStatus());

        // Test Like message
        com.borrowbox.app.model.ChatMessage likeMsg = new com.borrowbox.app.model.ChatMessage(
                2L, 10L, 1L, 2L, "👍",
                System.currentTimeMillis(), true, "SENT", true
        );
        assertTrue(likeMsg.isLike());
        assertEquals("👍", likeMsg.getText());
    }

    @Test
    public void testChatConversationModelAndUnreadCalculations() {
        long now = System.currentTimeMillis();
        com.borrowbox.app.model.ChatConversation conv = new com.borrowbox.app.model.ChatConversation(
                1L, "Laptop Charger (Type-C 65W)", 2L,
                "Md. Sabbir Hossain Shamim", "Yes, meet me at library",
                now, 2, false, false
        );

        assertEquals(1L, conv.getProductId());
        assertEquals("Laptop Charger (Type-C 65W)", conv.getProductName());
        assertEquals(2L, conv.getOtherUserId());
        assertEquals("Md. Sabbir Hossain Shamim", conv.getOtherUserName());
        assertEquals("Yes, meet me at library", conv.getLastMessage());
        assertEquals(2, conv.getUnreadCount());
        assertFalse(conv.isLastMessageFromMe());
        assertFalse(conv.isLike());
    }

    @Test
    public void testChatAttachmentTypes() {
        com.borrowbox.app.model.ChatMessage imgMsg = new com.borrowbox.app.model.ChatMessage(
                3L, 4L, 1L, 4L, "📷 Photo attached from Camera",
                System.currentTimeMillis(), true, "DELIVERED", false,
                "IMAGE", "content://media/photos/101"
        );
        assertEquals("IMAGE", imgMsg.getAttachmentType());
        assertEquals("content://media/photos/101", imgMsg.getAttachmentUri());
        assertFalse(imgMsg.isLike());

        com.borrowbox.app.model.ChatMessage audioMsg = new com.borrowbox.app.model.ChatMessage(
                4L, 4L, 1L, 4L, "🎙️ Voice message (0:08)",
                System.currentTimeMillis(), true, "SENT", false,
                "AUDIO", ""
        );
        assertEquals("AUDIO", audioMsg.getAttachmentType());
    }

    @Test
    public void testMultiUserConversationDirectionality() {
        long userTanjeem = 1L;
        long userTasnim = 4L;

        com.borrowbox.app.model.ChatMessage msgFromTanjeem = new com.borrowbox.app.model.ChatMessage(
                5L, 4L, userTanjeem, userTasnim, "Can I borrow your DSLR camera?",
                System.currentTimeMillis(), false, "SENT", false
        );

        // When Tanjeem is logged in:
        msgFromTanjeem.setFromMe(msgFromTanjeem.getSenderId() == userTanjeem);
        assertTrue(msgFromTanjeem.isFromMe());

        // When Tasnim is logged in:
        msgFromTanjeem.setFromMe(msgFromTanjeem.getSenderId() == userTasnim);
        assertFalse(msgFromTanjeem.isFromMe());
    }
}
