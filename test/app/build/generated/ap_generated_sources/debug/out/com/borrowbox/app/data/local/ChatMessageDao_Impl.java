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
import com.borrowbox.app.model.ChatMessage;
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
public final class ChatMessageDao_Impl implements ChatMessageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ChatMessage> __insertionAdapterOfChatMessage;

  private final EntityDeletionOrUpdateAdapter<ChatMessage> __updateAdapterOfChatMessage;

  private final SharedSQLiteStatement __preparedStmtOfMarkMessagesAsSeen;

  private final SharedSQLiteStatement __preparedStmtOfDeleteConversation;

  public ChatMessageDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChatMessage = new EntityInsertionAdapter<ChatMessage>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `chat_messages` (`id`,`productId`,`senderId`,`receiverId`,`text`,`timestamp`,`isFromMe`,`status`,`isLike`,`attachmentType`,`attachmentUri`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ChatMessage entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        statement.bindLong(3, entity.getSenderId());
        statement.bindLong(4, entity.getReceiverId());
        if (entity.getText() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getText());
        }
        statement.bindLong(6, entity.getTimestamp());
        final int _tmp = entity.isFromMe() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getStatus() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getStatus());
        }
        final int _tmp_1 = entity.isLike() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        if (entity.getAttachmentType() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getAttachmentType());
        }
        if (entity.getAttachmentUri() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAttachmentUri());
        }
      }
    };
    this.__updateAdapterOfChatMessage = new EntityDeletionOrUpdateAdapter<ChatMessage>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `chat_messages` SET `id` = ?,`productId` = ?,`senderId` = ?,`receiverId` = ?,`text` = ?,`timestamp` = ?,`isFromMe` = ?,`status` = ?,`isLike` = ?,`attachmentType` = ?,`attachmentUri` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ChatMessage entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        statement.bindLong(3, entity.getSenderId());
        statement.bindLong(4, entity.getReceiverId());
        if (entity.getText() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getText());
        }
        statement.bindLong(6, entity.getTimestamp());
        final int _tmp = entity.isFromMe() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getStatus() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getStatus());
        }
        final int _tmp_1 = entity.isLike() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        if (entity.getAttachmentType() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getAttachmentType());
        }
        if (entity.getAttachmentUri() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAttachmentUri());
        }
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfMarkMessagesAsSeen = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE chat_messages SET status = 'SEEN' WHERE productId = ? AND receiverId = ? AND status != 'SEEN'";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteConversation = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM chat_messages WHERE productId = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final ChatMessage message) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfChatMessage.insertAndReturnId(message);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<ChatMessage> messages) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfChatMessage.insert(messages);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final ChatMessage message) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfChatMessage.handle(message);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void markMessagesAsSeen(final long productId, final long currentUserId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfMarkMessagesAsSeen.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, productId);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, currentUserId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfMarkMessagesAsSeen.release(_stmt);
    }
  }

  @Override
  public void deleteConversation(final long productId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteConversation.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, productId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteConversation.release(_stmt);
    }
  }

  @Override
  public LiveData<List<ChatMessage>> getMessagesForProductLive(final long productId) {
    final String _sql = "SELECT * FROM chat_messages WHERE productId = ? ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"chat_messages"}, false, new Callable<List<ChatMessage>>() {
      @Override
      @Nullable
      public List<ChatMessage> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfSenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "senderId");
          final int _cursorIndexOfReceiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "receiverId");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsFromMe = CursorUtil.getColumnIndexOrThrow(_cursor, "isFromMe");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsLike = CursorUtil.getColumnIndexOrThrow(_cursor, "isLike");
          final int _cursorIndexOfAttachmentType = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentType");
          final int _cursorIndexOfAttachmentUri = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentUri");
          final List<ChatMessage> _result = new ArrayList<ChatMessage>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChatMessage _item;
            _item = new ChatMessage();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            _item.setProductId(_tmpProductId);
            final long _tmpSenderId;
            _tmpSenderId = _cursor.getLong(_cursorIndexOfSenderId);
            _item.setSenderId(_tmpSenderId);
            final long _tmpReceiverId;
            _tmpReceiverId = _cursor.getLong(_cursorIndexOfReceiverId);
            _item.setReceiverId(_tmpReceiverId);
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            _item.setText(_tmpText);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item.setTimestamp(_tmpTimestamp);
            final boolean _tmpIsFromMe;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFromMe);
            _tmpIsFromMe = _tmp != 0;
            _item.setFromMe(_tmpIsFromMe);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item.setStatus(_tmpStatus);
            final boolean _tmpIsLike;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsLike);
            _tmpIsLike = _tmp_1 != 0;
            _item.setLike(_tmpIsLike);
            final String _tmpAttachmentType;
            if (_cursor.isNull(_cursorIndexOfAttachmentType)) {
              _tmpAttachmentType = null;
            } else {
              _tmpAttachmentType = _cursor.getString(_cursorIndexOfAttachmentType);
            }
            _item.setAttachmentType(_tmpAttachmentType);
            final String _tmpAttachmentUri;
            if (_cursor.isNull(_cursorIndexOfAttachmentUri)) {
              _tmpAttachmentUri = null;
            } else {
              _tmpAttachmentUri = _cursor.getString(_cursorIndexOfAttachmentUri);
            }
            _item.setAttachmentUri(_tmpAttachmentUri);
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
  public List<ChatMessage> getMessagesForProductSync(final long productId) {
    final String _sql = "SELECT * FROM chat_messages WHERE productId = ? ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
      final int _cursorIndexOfSenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "senderId");
      final int _cursorIndexOfReceiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "receiverId");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfIsFromMe = CursorUtil.getColumnIndexOrThrow(_cursor, "isFromMe");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfIsLike = CursorUtil.getColumnIndexOrThrow(_cursor, "isLike");
      final int _cursorIndexOfAttachmentType = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentType");
      final int _cursorIndexOfAttachmentUri = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentUri");
      final List<ChatMessage> _result = new ArrayList<ChatMessage>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ChatMessage _item;
        _item = new ChatMessage();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final long _tmpProductId;
        _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
        _item.setProductId(_tmpProductId);
        final long _tmpSenderId;
        _tmpSenderId = _cursor.getLong(_cursorIndexOfSenderId);
        _item.setSenderId(_tmpSenderId);
        final long _tmpReceiverId;
        _tmpReceiverId = _cursor.getLong(_cursorIndexOfReceiverId);
        _item.setReceiverId(_tmpReceiverId);
        final String _tmpText;
        if (_cursor.isNull(_cursorIndexOfText)) {
          _tmpText = null;
        } else {
          _tmpText = _cursor.getString(_cursorIndexOfText);
        }
        _item.setText(_tmpText);
        final long _tmpTimestamp;
        _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        _item.setTimestamp(_tmpTimestamp);
        final boolean _tmpIsFromMe;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsFromMe);
        _tmpIsFromMe = _tmp != 0;
        _item.setFromMe(_tmpIsFromMe);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        _item.setStatus(_tmpStatus);
        final boolean _tmpIsLike;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsLike);
        _tmpIsLike = _tmp_1 != 0;
        _item.setLike(_tmpIsLike);
        final String _tmpAttachmentType;
        if (_cursor.isNull(_cursorIndexOfAttachmentType)) {
          _tmpAttachmentType = null;
        } else {
          _tmpAttachmentType = _cursor.getString(_cursorIndexOfAttachmentType);
        }
        _item.setAttachmentType(_tmpAttachmentType);
        final String _tmpAttachmentUri;
        if (_cursor.isNull(_cursorIndexOfAttachmentUri)) {
          _tmpAttachmentUri = null;
        } else {
          _tmpAttachmentUri = _cursor.getString(_cursorIndexOfAttachmentUri);
        }
        _item.setAttachmentUri(_tmpAttachmentUri);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<ChatMessage>> getConversationLive(final long productId, final long user1,
      final long user2) {
    final String _sql = "SELECT * FROM chat_messages WHERE productId = ? AND ((senderId = ? AND receiverId = ?) OR (senderId = ? AND receiverId = ?)) ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 5);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, user1);
    _argIndex = 3;
    _statement.bindLong(_argIndex, user2);
    _argIndex = 4;
    _statement.bindLong(_argIndex, user2);
    _argIndex = 5;
    _statement.bindLong(_argIndex, user1);
    return __db.getInvalidationTracker().createLiveData(new String[] {"chat_messages"}, false, new Callable<List<ChatMessage>>() {
      @Override
      @Nullable
      public List<ChatMessage> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfSenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "senderId");
          final int _cursorIndexOfReceiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "receiverId");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsFromMe = CursorUtil.getColumnIndexOrThrow(_cursor, "isFromMe");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsLike = CursorUtil.getColumnIndexOrThrow(_cursor, "isLike");
          final int _cursorIndexOfAttachmentType = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentType");
          final int _cursorIndexOfAttachmentUri = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentUri");
          final List<ChatMessage> _result = new ArrayList<ChatMessage>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChatMessage _item;
            _item = new ChatMessage();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            _item.setProductId(_tmpProductId);
            final long _tmpSenderId;
            _tmpSenderId = _cursor.getLong(_cursorIndexOfSenderId);
            _item.setSenderId(_tmpSenderId);
            final long _tmpReceiverId;
            _tmpReceiverId = _cursor.getLong(_cursorIndexOfReceiverId);
            _item.setReceiverId(_tmpReceiverId);
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            _item.setText(_tmpText);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item.setTimestamp(_tmpTimestamp);
            final boolean _tmpIsFromMe;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFromMe);
            _tmpIsFromMe = _tmp != 0;
            _item.setFromMe(_tmpIsFromMe);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item.setStatus(_tmpStatus);
            final boolean _tmpIsLike;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsLike);
            _tmpIsLike = _tmp_1 != 0;
            _item.setLike(_tmpIsLike);
            final String _tmpAttachmentType;
            if (_cursor.isNull(_cursorIndexOfAttachmentType)) {
              _tmpAttachmentType = null;
            } else {
              _tmpAttachmentType = _cursor.getString(_cursorIndexOfAttachmentType);
            }
            _item.setAttachmentType(_tmpAttachmentType);
            final String _tmpAttachmentUri;
            if (_cursor.isNull(_cursorIndexOfAttachmentUri)) {
              _tmpAttachmentUri = null;
            } else {
              _tmpAttachmentUri = _cursor.getString(_cursorIndexOfAttachmentUri);
            }
            _item.setAttachmentUri(_tmpAttachmentUri);
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
  public List<ChatMessage> getConversationSync(final long productId, final long user1,
      final long user2) {
    final String _sql = "SELECT * FROM chat_messages WHERE productId = ? AND ((senderId = ? AND receiverId = ?) OR (senderId = ? AND receiverId = ?)) ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 5);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, user1);
    _argIndex = 3;
    _statement.bindLong(_argIndex, user2);
    _argIndex = 4;
    _statement.bindLong(_argIndex, user2);
    _argIndex = 5;
    _statement.bindLong(_argIndex, user1);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
      final int _cursorIndexOfSenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "senderId");
      final int _cursorIndexOfReceiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "receiverId");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfIsFromMe = CursorUtil.getColumnIndexOrThrow(_cursor, "isFromMe");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfIsLike = CursorUtil.getColumnIndexOrThrow(_cursor, "isLike");
      final int _cursorIndexOfAttachmentType = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentType");
      final int _cursorIndexOfAttachmentUri = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentUri");
      final List<ChatMessage> _result = new ArrayList<ChatMessage>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ChatMessage _item;
        _item = new ChatMessage();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final long _tmpProductId;
        _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
        _item.setProductId(_tmpProductId);
        final long _tmpSenderId;
        _tmpSenderId = _cursor.getLong(_cursorIndexOfSenderId);
        _item.setSenderId(_tmpSenderId);
        final long _tmpReceiverId;
        _tmpReceiverId = _cursor.getLong(_cursorIndexOfReceiverId);
        _item.setReceiverId(_tmpReceiverId);
        final String _tmpText;
        if (_cursor.isNull(_cursorIndexOfText)) {
          _tmpText = null;
        } else {
          _tmpText = _cursor.getString(_cursorIndexOfText);
        }
        _item.setText(_tmpText);
        final long _tmpTimestamp;
        _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        _item.setTimestamp(_tmpTimestamp);
        final boolean _tmpIsFromMe;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsFromMe);
        _tmpIsFromMe = _tmp != 0;
        _item.setFromMe(_tmpIsFromMe);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        _item.setStatus(_tmpStatus);
        final boolean _tmpIsLike;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsLike);
        _tmpIsLike = _tmp_1 != 0;
        _item.setLike(_tmpIsLike);
        final String _tmpAttachmentType;
        if (_cursor.isNull(_cursorIndexOfAttachmentType)) {
          _tmpAttachmentType = null;
        } else {
          _tmpAttachmentType = _cursor.getString(_cursorIndexOfAttachmentType);
        }
        _item.setAttachmentType(_tmpAttachmentType);
        final String _tmpAttachmentUri;
        if (_cursor.isNull(_cursorIndexOfAttachmentUri)) {
          _tmpAttachmentUri = null;
        } else {
          _tmpAttachmentUri = _cursor.getString(_cursorIndexOfAttachmentUri);
        }
        _item.setAttachmentUri(_tmpAttachmentUri);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<ChatMessage>> getAllMessagesForUserLive(final long userId) {
    final String _sql = "SELECT * FROM chat_messages WHERE senderId = ? OR receiverId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"chat_messages"}, false, new Callable<List<ChatMessage>>() {
      @Override
      @Nullable
      public List<ChatMessage> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfSenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "senderId");
          final int _cursorIndexOfReceiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "receiverId");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsFromMe = CursorUtil.getColumnIndexOrThrow(_cursor, "isFromMe");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsLike = CursorUtil.getColumnIndexOrThrow(_cursor, "isLike");
          final int _cursorIndexOfAttachmentType = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentType");
          final int _cursorIndexOfAttachmentUri = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentUri");
          final List<ChatMessage> _result = new ArrayList<ChatMessage>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChatMessage _item;
            _item = new ChatMessage();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            _item.setProductId(_tmpProductId);
            final long _tmpSenderId;
            _tmpSenderId = _cursor.getLong(_cursorIndexOfSenderId);
            _item.setSenderId(_tmpSenderId);
            final long _tmpReceiverId;
            _tmpReceiverId = _cursor.getLong(_cursorIndexOfReceiverId);
            _item.setReceiverId(_tmpReceiverId);
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            _item.setText(_tmpText);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item.setTimestamp(_tmpTimestamp);
            final boolean _tmpIsFromMe;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFromMe);
            _tmpIsFromMe = _tmp != 0;
            _item.setFromMe(_tmpIsFromMe);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item.setStatus(_tmpStatus);
            final boolean _tmpIsLike;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsLike);
            _tmpIsLike = _tmp_1 != 0;
            _item.setLike(_tmpIsLike);
            final String _tmpAttachmentType;
            if (_cursor.isNull(_cursorIndexOfAttachmentType)) {
              _tmpAttachmentType = null;
            } else {
              _tmpAttachmentType = _cursor.getString(_cursorIndexOfAttachmentType);
            }
            _item.setAttachmentType(_tmpAttachmentType);
            final String _tmpAttachmentUri;
            if (_cursor.isNull(_cursorIndexOfAttachmentUri)) {
              _tmpAttachmentUri = null;
            } else {
              _tmpAttachmentUri = _cursor.getString(_cursorIndexOfAttachmentUri);
            }
            _item.setAttachmentUri(_tmpAttachmentUri);
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
  public List<ChatMessage> getAllMessagesForUserSync(final long userId) {
    final String _sql = "SELECT * FROM chat_messages WHERE senderId = ? OR receiverId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, userId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
      final int _cursorIndexOfSenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "senderId");
      final int _cursorIndexOfReceiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "receiverId");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfIsFromMe = CursorUtil.getColumnIndexOrThrow(_cursor, "isFromMe");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfIsLike = CursorUtil.getColumnIndexOrThrow(_cursor, "isLike");
      final int _cursorIndexOfAttachmentType = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentType");
      final int _cursorIndexOfAttachmentUri = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentUri");
      final List<ChatMessage> _result = new ArrayList<ChatMessage>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ChatMessage _item;
        _item = new ChatMessage();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final long _tmpProductId;
        _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
        _item.setProductId(_tmpProductId);
        final long _tmpSenderId;
        _tmpSenderId = _cursor.getLong(_cursorIndexOfSenderId);
        _item.setSenderId(_tmpSenderId);
        final long _tmpReceiverId;
        _tmpReceiverId = _cursor.getLong(_cursorIndexOfReceiverId);
        _item.setReceiverId(_tmpReceiverId);
        final String _tmpText;
        if (_cursor.isNull(_cursorIndexOfText)) {
          _tmpText = null;
        } else {
          _tmpText = _cursor.getString(_cursorIndexOfText);
        }
        _item.setText(_tmpText);
        final long _tmpTimestamp;
        _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        _item.setTimestamp(_tmpTimestamp);
        final boolean _tmpIsFromMe;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsFromMe);
        _tmpIsFromMe = _tmp != 0;
        _item.setFromMe(_tmpIsFromMe);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        _item.setStatus(_tmpStatus);
        final boolean _tmpIsLike;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsLike);
        _tmpIsLike = _tmp_1 != 0;
        _item.setLike(_tmpIsLike);
        final String _tmpAttachmentType;
        if (_cursor.isNull(_cursorIndexOfAttachmentType)) {
          _tmpAttachmentType = null;
        } else {
          _tmpAttachmentType = _cursor.getString(_cursorIndexOfAttachmentType);
        }
        _item.setAttachmentType(_tmpAttachmentType);
        final String _tmpAttachmentUri;
        if (_cursor.isNull(_cursorIndexOfAttachmentUri)) {
          _tmpAttachmentUri = null;
        } else {
          _tmpAttachmentUri = _cursor.getString(_cursorIndexOfAttachmentUri);
        }
        _item.setAttachmentUri(_tmpAttachmentUri);
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
    final String _sql = "SELECT COUNT(*) FROM chat_messages WHERE receiverId = ? AND status != 'SEEN'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"chat_messages"}, false, new Callable<Integer>() {
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
  public ChatMessage getLatestMessageForProduct(final long productId) {
    final String _sql = "SELECT * FROM chat_messages WHERE productId = ? ORDER BY timestamp DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
      final int _cursorIndexOfSenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "senderId");
      final int _cursorIndexOfReceiverId = CursorUtil.getColumnIndexOrThrow(_cursor, "receiverId");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfIsFromMe = CursorUtil.getColumnIndexOrThrow(_cursor, "isFromMe");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfIsLike = CursorUtil.getColumnIndexOrThrow(_cursor, "isLike");
      final int _cursorIndexOfAttachmentType = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentType");
      final int _cursorIndexOfAttachmentUri = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentUri");
      final ChatMessage _result;
      if (_cursor.moveToFirst()) {
        _result = new ChatMessage();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final long _tmpProductId;
        _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
        _result.setProductId(_tmpProductId);
        final long _tmpSenderId;
        _tmpSenderId = _cursor.getLong(_cursorIndexOfSenderId);
        _result.setSenderId(_tmpSenderId);
        final long _tmpReceiverId;
        _tmpReceiverId = _cursor.getLong(_cursorIndexOfReceiverId);
        _result.setReceiverId(_tmpReceiverId);
        final String _tmpText;
        if (_cursor.isNull(_cursorIndexOfText)) {
          _tmpText = null;
        } else {
          _tmpText = _cursor.getString(_cursorIndexOfText);
        }
        _result.setText(_tmpText);
        final long _tmpTimestamp;
        _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        _result.setTimestamp(_tmpTimestamp);
        final boolean _tmpIsFromMe;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsFromMe);
        _tmpIsFromMe = _tmp != 0;
        _result.setFromMe(_tmpIsFromMe);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        _result.setStatus(_tmpStatus);
        final boolean _tmpIsLike;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsLike);
        _tmpIsLike = _tmp_1 != 0;
        _result.setLike(_tmpIsLike);
        final String _tmpAttachmentType;
        if (_cursor.isNull(_cursorIndexOfAttachmentType)) {
          _tmpAttachmentType = null;
        } else {
          _tmpAttachmentType = _cursor.getString(_cursorIndexOfAttachmentType);
        }
        _result.setAttachmentType(_tmpAttachmentType);
        final String _tmpAttachmentUri;
        if (_cursor.isNull(_cursorIndexOfAttachmentUri)) {
          _tmpAttachmentUri = null;
        } else {
          _tmpAttachmentUri = _cursor.getString(_cursorIndexOfAttachmentUri);
        }
        _result.setAttachmentUri(_tmpAttachmentUri);
      } else {
        _result = null;
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
