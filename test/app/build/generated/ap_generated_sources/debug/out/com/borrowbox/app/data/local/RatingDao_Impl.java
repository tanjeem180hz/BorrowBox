package com.borrowbox.app.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.borrowbox.app.model.Rating;
import java.lang.Class;
import java.lang.Exception;
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
public final class RatingDao_Impl implements RatingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Rating> __insertionAdapterOfRating;

  public RatingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRating = new EntityInsertionAdapter<Rating>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `ratings` (`id`,`rentalId`,`fromUserId`,`toUserId`,`stars`,`comment`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Rating entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getRentalId());
        statement.bindLong(3, entity.getFromUserId());
        statement.bindLong(4, entity.getToUserId());
        statement.bindLong(5, entity.getStars());
        if (entity.getComment() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getComment());
        }
        statement.bindLong(7, entity.getCreatedAt());
      }
    };
  }

  @Override
  public long insert(final Rating rating) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfRating.insertAndReturnId(rating);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Rating>> getRatingsForUserLive(final long userId) {
    final String _sql = "SELECT * FROM ratings WHERE toUserId = ? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"ratings"}, false, new Callable<List<Rating>>() {
      @Override
      @Nullable
      public List<Rating> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRentalId = CursorUtil.getColumnIndexOrThrow(_cursor, "rentalId");
          final int _cursorIndexOfFromUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "fromUserId");
          final int _cursorIndexOfToUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "toUserId");
          final int _cursorIndexOfStars = CursorUtil.getColumnIndexOrThrow(_cursor, "stars");
          final int _cursorIndexOfComment = CursorUtil.getColumnIndexOrThrow(_cursor, "comment");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Rating> _result = new ArrayList<Rating>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Rating _item;
            _item = new Rating();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpRentalId;
            _tmpRentalId = _cursor.getLong(_cursorIndexOfRentalId);
            _item.setRentalId(_tmpRentalId);
            final long _tmpFromUserId;
            _tmpFromUserId = _cursor.getLong(_cursorIndexOfFromUserId);
            _item.setFromUserId(_tmpFromUserId);
            final long _tmpToUserId;
            _tmpToUserId = _cursor.getLong(_cursorIndexOfToUserId);
            _item.setToUserId(_tmpToUserId);
            final int _tmpStars;
            _tmpStars = _cursor.getInt(_cursorIndexOfStars);
            _item.setStars(_tmpStars);
            final String _tmpComment;
            if (_cursor.isNull(_cursorIndexOfComment)) {
              _tmpComment = null;
            } else {
              _tmpComment = _cursor.getString(_cursorIndexOfComment);
            }
            _item.setComment(_tmpComment);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
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
  public Rating getRatingByRentalId(final long rentalId) {
    final String _sql = "SELECT * FROM ratings WHERE rentalId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, rentalId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfRentalId = CursorUtil.getColumnIndexOrThrow(_cursor, "rentalId");
      final int _cursorIndexOfFromUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "fromUserId");
      final int _cursorIndexOfToUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "toUserId");
      final int _cursorIndexOfStars = CursorUtil.getColumnIndexOrThrow(_cursor, "stars");
      final int _cursorIndexOfComment = CursorUtil.getColumnIndexOrThrow(_cursor, "comment");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final Rating _result;
      if (_cursor.moveToFirst()) {
        _result = new Rating();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final long _tmpRentalId;
        _tmpRentalId = _cursor.getLong(_cursorIndexOfRentalId);
        _result.setRentalId(_tmpRentalId);
        final long _tmpFromUserId;
        _tmpFromUserId = _cursor.getLong(_cursorIndexOfFromUserId);
        _result.setFromUserId(_tmpFromUserId);
        final long _tmpToUserId;
        _tmpToUserId = _cursor.getLong(_cursorIndexOfToUserId);
        _result.setToUserId(_tmpToUserId);
        final int _tmpStars;
        _tmpStars = _cursor.getInt(_cursorIndexOfStars);
        _result.setStars(_tmpStars);
        final String _tmpComment;
        if (_cursor.isNull(_cursorIndexOfComment)) {
          _tmpComment = null;
        } else {
          _tmpComment = _cursor.getString(_cursorIndexOfComment);
        }
        _result.setComment(_tmpComment);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _result.setCreatedAt(_tmpCreatedAt);
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
