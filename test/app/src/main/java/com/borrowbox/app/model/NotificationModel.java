package com.borrowbox.app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Domain model and Room Entity representing in-app and push notifications.
 * Features strict validation, encapsulation, and deep-link screen target tracking.
 */
@Entity(tableName = "notifications")
public class NotificationModel {

    public static final String DEEP_LINK_RENTAL_DETAILS = "RENTAL_DETAILS";
    public static final String DEEP_LINK_CONDITION_COMPARISON = "CONDITION_COMPARISON";
    public static final String DEEP_LINK_REPORT_STATUS = "REPORT_STATUS";
    public static final String DEEP_LINK_ADMIN_MODERATION = "ADMIN_MODERATION";
    public static final String DEEP_LINK_NOTIFICATION_CENTRE = "NOTIFICATION_CENTRE";
    public static final String DEEP_LINK_MAIN = "MAIN";

    @PrimaryKey
    @NonNull
    private String notifId = "";

    private long recipientId;
    @NonNull
    private String title = "";
    @NonNull
    private String body = "";
    @NonNull
    private String deepLinkScreen = DEEP_LINK_MAIN;
    private boolean isRead = false;
    private long sentAt = System.currentTimeMillis();

    public NotificationModel() {
    }

    @Ignore
    public NotificationModel(@NonNull String notifId, long recipientId, @NonNull String title,
                             @NonNull String body, @NonNull String deepLinkScreen,
                             boolean isRead, long sentAt) {
        setNotifId(notifId);
        setRecipientId(recipientId);
        setTitle(title);
        setBody(body);
        setDeepLinkScreen(deepLinkScreen);
        setRead(isRead);
        setSentAt(sentAt);
    }

    @NonNull
    public String getNotifId() {
        return notifId;
    }

    public void setNotifId(@NonNull String notifId) {
        this.notifId = notifId != null ? notifId : "";
    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(long recipientId) {
        this.recipientId = Math.max(0, recipientId);
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title != null ? title : "";
    }

    @NonNull
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body != null ? body : "";
    }

    @NonNull
    public String getDeepLinkScreen() {
        return deepLinkScreen;
    }

    public void setDeepLinkScreen(String deepLinkScreen) {
        this.deepLinkScreen = deepLinkScreen != null ? deepLinkScreen : DEEP_LINK_MAIN;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public long getSentAt() {
        return sentAt;
    }

    public void setSentAt(long sentAt) {
        this.sentAt = sentAt > 0 ? sentAt : System.currentTimeMillis();
    }
}
