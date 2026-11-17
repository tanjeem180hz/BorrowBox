package com.borrowbox.app.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.borrowbox.app.model.NotificationModel;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class NotificationDao_Impl implements NotificationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NotificationModel> __insertionAdapterOfNotificationModel;

  private final EntityDeletionOrUpdateAdapter<NotificationModel> __updateAdapterOfNotificationModel;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsRead;

  private final SharedSQLiteStatement __preparedStmtOfMarkAllAsRead;

  private final SharedSQLiteStatement __preparedStmtOfDeleteNotification;

  private final SharedSQLiteStatement __preparedStmtOfClearNotificationsForUser;

  public NotificationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNotificationModel = new EntityInsertionAdapter<NotificationModel>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `notifications` (`notifId`,`recipientId`,`title`,`body`,`deepLinkScreen`,`isRead`,`sentAt`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final NotificationModel entity) {
        if (entity.getNotifId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getNotifId());
        }
        statement.bindLong(2, entity.getRecipientId());
        if (entity.getTitle() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitle());
        }
        if (entity.getBody() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getBody());
        }
        if (entity.getDeepLinkScreen() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDeepLinkScreen());
        }
        final int _tmp = entity.isRead() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getSentAt());
      }
    };
    this.__updateAdapterOfNotificationModel = new EntityDeletionOrUpdateAdapter<NotificationModel>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `notifications` SET `notifId` = ?,`recipientId` = ?,`title` = ?,`body` = ?,`deepLinkScreen` = ?,`isRead` = ?,`sentAt` = ? WHERE `notifId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final NotificationModel entity) {
        if (entity.getNotifId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getNotifId());
        }
        statement.bindLong(2, entity.getRecipientId());
        if (entity.getTitle() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitle());
        }
        if (entity.getBody() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getBody());
        }
        if (entity.getDeepLinkScreen() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDeepLinkScreen());
        }
        final int _tmp = entity.isRead() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getSentAt());
        if (entity.getNotifId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNotifId());
        }
      }
    };
    this.__preparedStmtOfMarkAsRead = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE notifications SET isRead = 1 WHERE notifId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAllAsRead = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE notifications SET isRead = 1 WHERE recipientId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteNotification = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM notifications WHERE notifId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearNotificationsForUser = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM notifications WHERE recipientId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final NotificationModel notification) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfNotificationModel.insert(notification);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<NotificationModel> notifications) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfNotificationModel.insert(notifications);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final NotificationModel notification) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfNotificationModel.handle(notification);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void markAsRead(final String notifId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsRead.acquire();
    int _argIndex = 1;
    if (notifId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, notifId);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfMarkAsRead.release(_stmt);
    }
  }

  @Override
  public void markAllAsRead(final long userId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAllAsRead.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, userId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfMarkAllAsRead.release(_stmt);
    }
  }

  @Override
  public void deleteNotification(final String notifId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteNotification.acquire();
    int _argIndex = 1;
    if (notifId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, notifId);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteNotification.release(_stmt);
    }
  }

  @Override
  public void clearNotificationsForUser(final long userId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearNotificationsForUser.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, userId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfClearNotificationsForUser.release(_stmt);
    }
  }

  @Override
  public LiveData<List<NotificationModel>> getNotificationsForUserLive(final long userId) {
    final String _sql = "SELECT * FROM notifications WHERE recipientId = ? ORDER BY sentAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"notifications"}, false, new Callable<List<NotificationModel>>() {
      @Override
      @Nullable
      public List<NotificationModel> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNotifId = CursorUtil.getColumnIndexOrThrow(_cursor, "notifId");
          final int _cursorIndexOfRecipientId = CursorUtil.getColumnIndexOrThrow(_cursor, "recipientId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
          final int _cursorIndexOfDeepLinkScreen = CursorUtil.getColumnIndexOrThrow(_cursor, "deepLinkScreen");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final int _cursorIndexOfSentAt = CursorUtil.getColumnIndexOrThrow(_cursor, "sentAt");
          final List<NotificationModel> _result = new ArrayList<NotificationModel>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NotificationModel _item;
            _item = new NotificationModel();
            final String _tmpNotifId;
            if (_cursor.isNull(_cursorIndexOfNotifId)) {
              _tmpNotifId = null;
            } else {
              _tmpNotifId = _cursor.getString(_cursorIndexOfNotifId);
            }
            _item.setNotifId(_tmpNotifId);
            final long _tmpRecipientId;
            _tmpRecipientId = _cursor.getLong(_cursorIndexOfRecipientId);
            _item.setRecipientId(_tmpRecipientId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpBody;
            if (_cursor.isNull(_cursorIndexOfBody)) {
              _tmpBody = null;
            } else {
              _tmpBody = _cursor.getString(_cursorIndexOfBody);
            }
            _item.setBody(_tmpBody);
            final String _tmpDeepLinkScreen;
            if (_cursor.isNull(_cursorIndexOfDeepLinkScreen)) {
              _tmpDeepLinkScreen = null;
            } else {
              _tmpDeepLinkScreen = _cursor.getString(_cursorIndexOfDeepLinkScreen);
            }
            _item.setDeepLinkScreen(_tmpDeepLinkScreen);
            final boolean _tmpIsRead;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp != 0;
            _item.setRead(_tmpIsRead);
            final long _tmpSentAt;
            _tmpSentAt = _cursor.getLong(_cursorIndexOfSentAt);
            _item.setSentAt(_tmpSentAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<NotificationModel> getNotificationsForUser(final long userId) {
    final String _sql = "SELECT * FROM notifications WHERE recipientId = ? ORDER BY sentAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfNotifId = CursorUtil.getColumnIndexOrThrow(_cursor, "notifId");
      final int _cursorIndexOfRecipientId = CursorUtil.getColumnIndexOrThrow(_cursor, "recipientId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
      final int _cursorIndexOfDeepLinkScreen = CursorUtil.getColumnIndexOrThrow(_cursor, "deepLinkScreen");
      final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
      final int _cursorIndexOfSentAt = CursorUtil.getColumnIndexOrThrow(_cursor, "sentAt");
      final List<NotificationModel> _result = new ArrayList<NotificationModel>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final NotificationModel _item;
        _item = new NotificationModel();
        final String _tmpNotifId;
        if (_cursor.isNull(_cursorIndexOfNotifId)) {
          _tmpNotifId = null;
        } else {
          _tmpNotifId = _cursor.getString(_cursorIndexOfNotifId);
        }
        _item.setNotifId(_tmpNotifId);
        final long _tmpRecipientId;
        _tmpRecipientId = _cursor.getLong(_cursorIndexOfRecipientId);
        _item.setRecipientId(_tmpRecipientId);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _item.setTitle(_tmpTitle);
        final String _tmpBody;
        if (_cursor.isNull(_cursorIndexOfBody)) {
          _tmpBody = null;
        } else {
          _tmpBody = _cursor.getString(_cursorIndexOfBody);
        }
        _item.setBody(_tmpBody);
        final String _tmpDeepLinkScreen;
        if (_cursor.isNull(_cursorIndexOfDeepLinkScreen)) {
          _tmpDeepLinkScreen = null;
        } else {
          _tmpDeepLinkScreen = _cursor.getString(_cursorIndexOfDeepLinkScreen);
        }
        _item.setDeepLinkScreen(_tmpDeepLinkScreen);
        final boolean _tmpIsRead;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsRead);
        _tmpIsRead = _tmp != 0;
        _item.setRead(_tmpIsRead);
        final long _tmpSentAt;
        _tmpSentAt = _cursor.getLong(_cursorIndexOfSentAt);
        _item.setSentAt(_tmpSentAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> getUnreadCountLive(final long userId) {
    final String _sql = "SELECT COUNT(*) FROM notifications WHERE recipientId = ? AND isRead = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"notifications"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public int getUnreadCount(final long userId) {
    final String _sql = "SELECT COUNT(*) FROM notifications WHERE recipientId = ? AND isRead = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
