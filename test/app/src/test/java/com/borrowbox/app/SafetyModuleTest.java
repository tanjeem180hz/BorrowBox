package com.borrowbox.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.borrowbox.app.domain.PhotoVerification;
import com.borrowbox.app.domain.Verifiable;
import com.borrowbox.app.model.DamageReport;
import com.borrowbox.app.model.MisconductReport;
import com.borrowbox.app.model.NotificationModel;
import com.borrowbox.app.model.ProductCondition;

import org.junit.Test;

import java.util.Collections;

public class SafetyModuleTest {

    @Test
    public void testPhotoVerificationCleanReturnPasses() {
        Verifiable verifiable = new PhotoVerification();
        ProductCondition condition = new ProductCondition();
        condition.setCheckId("CHK-100");
        condition.setRentalId(1L);
        condition.setPrePhotoUrl(Collections.singletonList("http://example.com/pre.jpg"));
        condition.setPostPhotoUrl(Collections.singletonList("http://example.com/post.jpg"));

        long now = System.currentTimeMillis();
        condition.setPreTimestamp(now - 3600000L);
        condition.setPostTimestamp(now);
        condition.setPostNotes("Item returned clean with no scratches");

        assertTrue(verifiable.verifyCondition(condition));
        assertTrue(verifiable.getVerificationNotes(condition).contains("PASS"));
    }

    @Test
    public void testPhotoVerificationFailsOnMissingPostPhotoOrDamageNote() {
        Verifiable verifiable = new PhotoVerification();
        ProductCondition condition = new ProductCondition();
        condition.setPrePhotoUrl(Collections.singletonList("http://example.com/pre.jpg"));
        condition.setPostNotes("Contains scratch and broken lens cap");

        // Missing post photo -> fails
        assertFalse(verifiable.verifyCondition(condition));

        condition.setPostPhotoUrl(Collections.singletonList("http://example.com/post.jpg"));
        // Has post photo but post notes mention damage -> fails clean verification
        assertFalse(verifiable.verifyCondition(condition));
    }

    @Test
    public void testReportInheritanceAndStatusValidation() {
        DamageReport dr = new DamageReport();
        dr.setReporterId(1L);
        dr.setRentalId(10L);
        dr.setEstimatedLoss(250.0);

        assertEquals("Damaged Gear", dr.getIssueType());
        assertEquals(250.0, dr.getEstimatedLoss(), 0.001);

        // Test status validation
        dr.setStatus("Resolved");
        assertEquals("Resolved", dr.getStatus());

        // Invalid status defaults to Submitted
        dr.setStatus("INVALID_STATUS");
        assertEquals("Submitted", dr.getStatus());
    }

    @Test
    public void testMisconductReportCategories() {
        MisconductReport mr = new MisconductReport();
        mr.setReportedUserId(3L);
        mr.setCategory(MisconductReport.CATEGORY_FAKE_LISTING);

        assertEquals(MisconductReport.CATEGORY_FAKE_LISTING, mr.getCategory());
        assertEquals(3L, mr.getReportedUserId());
    }

    @Test
    public void testNotificationModelDeepLinkAndEncapsulation() {
        NotificationModel notif = new NotificationModel();
        notif.setNotifId("NOTIF-1");
        notif.setRecipientId(1L);
        notif.setTitle("Return Reminder");
        notif.setDeepLinkScreen(NotificationModel.DEEP_LINK_CONDITION_COMPARISON);

        assertEquals("NOTIF-1", notif.getNotifId());
        assertEquals(NotificationModel.DEEP_LINK_CONDITION_COMPARISON, notif.getDeepLinkScreen());
        assertFalse(notif.isRead());

        notif.setRead(true);
        assertTrue(notif.isRead());
    }
}
