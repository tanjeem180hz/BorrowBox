package com.borrowbox.app.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.borrowbox.app.model.NotificationModel;
import java.util.List;

@Dao
public interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NotificationModel notification);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NotificationModel> notifications);

    @Update
    void update(NotificationModel notification);

    @Query("SELECT * FROM notifications WHERE recipientId = :userId ORDER BY sentAt DESC")
    LiveData<List<NotificationModel>> getNotificationsForUserLive(long userId);

    @Query("SELECT * FROM notifications WHERE recipientId = :userId ORDER BY sentAt DESC")
    List<NotificationModel> getNotificationsForUser(long userId);

    @Query("SELECT COUNT(*) FROM notifications WHERE recipientId = :userId AND isRead = 0")
    LiveData<Integer> getUnreadCountLive(long userId);

    @Query("SELECT COUNT(*) FROM notifications WHERE recipientId = :userId AND isRead = 0")
    int getUnreadCount(long userId);

    @Query("UPDATE notifications SET isRead = 1 WHERE notifId = :notifId")
    void markAsRead(String notifId);

    @Query("UPDATE notifications SET isRead = 1 WHERE recipientId = :userId")
    void markAllAsRead(long userId);

    @Query("DELETE FROM notifications WHERE notifId = :notifId")
    void deleteNotification(String notifId);

    @Query("DELETE FROM notifications WHERE recipientId = :userId")
    void clearNotificationsForUser(long userId);
}
