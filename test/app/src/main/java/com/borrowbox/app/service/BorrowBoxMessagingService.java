package com.borrowbox.app.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.borrowbox.app.MainActivity;
import com.borrowbox.app.R;
import com.borrowbox.app.data.repository.NotificationRepository;
import com.borrowbox.app.model.NotificationModel;
import com.borrowbox.app.ui.condition.ConditionComparisonActivity;
import com.borrowbox.app.ui.dispute.ReportStatusActivity;
import com.borrowbox.app.ui.notifications.NotificationCentreActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Firebase Cloud Messaging (FCM) Service for push notifications & deep-link routing.
 * Handles system notifications for all rental and safety events.
 */
public class BorrowBoxMessagingService extends FirebaseMessagingService {

    public static final String CHANNEL_ID_SAFETY = "borrowbox_safety_channel";
    public static final String CHANNEL_NAME_SAFETY = "Safety & Rental Notifications";

    public static final String EVENT_RENTAL_REQUEST = "RENTAL_REQUEST";
    public static final String EVENT_RENTAL_ACCEPTED = "RENTAL_ACCEPTED";
    public static final String EVENT_RENTAL_DECLINED = "RENTAL_DECLINED";
    public static final String EVENT_EMERGENCY_SOS = "EMERGENCY_SOS";
    public static final String EVENT_REMINDER_15MIN = "RETURN_REMINDER_15MIN";
    public static final String EVENT_REMINDER_5MIN = "RETURN_REMINDER_5MIN";
    public static final String EVENT_OVERDUE_ALERT = "OVERDUE_ALERT";
    public static final String EVENT_DAMAGE_REPORT = "DAMAGE_REPORT_FILED";
    public static final String EVENT_ADMIN_VERDICT = "ADMIN_DISPUTE_VERDICT";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        String title = "BorrowBox Alert";
        String body = "You have a new update";
        String eventType = EVENT_RENTAL_REQUEST;
        long recipientId = 1L;

        if (message.getNotification() != null) {
            title = message.getNotification().getTitle() != null ? message.getNotification().getTitle() : title;
            body = message.getNotification().getBody() != null ? message.getNotification().getBody() : body;
        }

        if (message.getData().containsKey("title")) title = message.getData().get("title");
        if (message.getData().containsKey("body")) body = message.getData().get("body");
        if (message.getData().containsKey("eventType")) eventType = message.getData().get("eventType");

        // Map event type to deep link screen
        String deepLink = mapEventTypeToDeepLink(eventType);

        // Save to NotificationRepository / Room DB
        NotificationRepository.getInstance(this).sendNotification(recipientId, title, body, deepLink);

        // Show Android System Notification
        showSystemNotification(title, body, deepLink);
    }

    private String mapEventTypeToDeepLink(String eventType) {
        if (EVENT_DAMAGE_REPORT.equals(eventType) || EVENT_ADMIN_VERDICT.equals(eventType)) {
            return NotificationModel.DEEP_LINK_REPORT_STATUS;
        } else if (EVENT_REMINDER_15MIN.equals(eventType) || EVENT_REMINDER_5MIN.equals(eventType)) {
            return NotificationModel.DEEP_LINK_CONDITION_COMPARISON;
        } else if (EVENT_EMERGENCY_SOS.equals(eventType)) {
            return NotificationModel.DEEP_LINK_NOTIFICATION_CENTRE;
        }
        return NotificationModel.DEEP_LINK_MAIN;
    }

    private void showSystemNotification(String title, String body, String deepLink) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID_SAFETY,
                    CHANNEL_NAME_SAFETY,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("BorrowBox safety alerts, return reminders, and dispute verdicts");
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        Intent intent;
        if (NotificationModel.DEEP_LINK_REPORT_STATUS.equals(deepLink)) {
            intent = ReportStatusActivity.createIntent(this, 1L);
        } else if (NotificationModel.DEEP_LINK_CONDITION_COMPARISON.equals(deepLink)) {
            intent = ConditionComparisonActivity.createIntent(this, 1L);
        } else if (NotificationModel.DEEP_LINK_NOTIFICATION_CENTRE.equals(deepLink)) {
            intent = NotificationCentreActivity.createIntent(this);
        } else {
            intent = new Intent(this, MainActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                (int) System.currentTimeMillis(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_SAFETY)
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        if (manager != null) {
            manager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }
}
