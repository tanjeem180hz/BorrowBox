package com.borrowbox.app.data.repository;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.borrowbox.app.data.local.AppDatabase;
import com.borrowbox.app.data.local.NotificationDao;
import com.borrowbox.app.model.NotificationModel;
import com.borrowbox.app.util.AppExecutors;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Repository for managing in-app & push notification history.
 * Integrates Room DB with Firestore `notifications` collection.
 */
public class NotificationRepository {

    private static volatile NotificationRepository instance;
    private final NotificationDao notificationDao;
    private final FirebaseFirestore firestore;

    private NotificationRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.notificationDao = db.notificationDao();
        this.firestore = FirebaseFirestore.getInstance();
    }

    public static NotificationRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (NotificationRepository.class) {
                if (instance == null) {
                    instance = new NotificationRepository(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public LiveData<List<NotificationModel>> getNotificationsForUserLive(long userId) {
        return notificationDao.getNotificationsForUserLive(userId);
    }

    public LiveData<Integer> getUnreadCountLive(long userId) {
        return notificationDao.getUnreadCountLive(userId);
    }

    public void sendNotification(long recipientId, String title, String body, String deepLinkScreen) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            String notifId = UUID.randomUUID().toString();
            NotificationModel notif = new NotificationModel(
                    notifId, recipientId, title, body, deepLinkScreen, false, System.currentTimeMillis()
            );

            // 1. Store in Room DB
            notificationDao.insert(notif);

            // 2. Store in Firestore
            Map<String, Object> data = new HashMap<>();
            data.put("notifId", notifId);
            data.put("recipientId", recipientId);
            data.put("title", title);
            data.put("body", body);
            data.put("deepLinkScreen", deepLinkScreen);
            data.put("isRead", false);
            data.put("sentAt", notif.getSentAt());

            try {
                firestore.collection("notifications").document(notifId).set(data);
            } catch (Exception ignored) {}
        });
    }

    public void markAsRead(String notifId) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            notificationDao.markAsRead(notifId);
            try {
                firestore.collection("notifications").document(notifId).update("isRead", true);
            } catch (Exception ignored) {}
        });
    }

    public void markAllAsRead(long userId) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            notificationDao.markAllAsRead(userId);
        });
    }

    public void deleteNotification(String notifId) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            notificationDao.deleteNotification(notifId);
            try {
                firestore.collection("notifications").document(notifId).delete();
            } catch (Exception ignored) {}
        });
    }
}
