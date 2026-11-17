package com.borrowbox.app.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.borrowbox.app.model.ProductCondition;
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
public final class ProductConditionDao_Impl implements ProductConditionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProductCondition> __insertionAdapterOfProductCondition;

  private final EntityDeletionOrUpdateAdapter<ProductCondition> __updateAdapterOfProductCondition;

  public ProductConditionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProductCondition = new EntityInsertionAdapter<ProductCondition>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `product_conditions` (`checkId`,`rentalId`,`prePhotoUrl`,`postPhotoUrl`,`preNotes`,`postNotes`,`preTimestamp`,`postTimestamp`,`preGpsLocation`,`postGpsLocation`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ProductCondition entity) {
        if (entity.getCheckId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getCheckId());
        }
        statement.bindLong(2, entity.getRentalId());
        final String _tmp = Converters.fromStringList(entity.getPrePhotoUrl());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        final String _tmp_1 = Converters.fromStringList(entity.getPostPhotoUrl());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp_1);
        }
        if (entity.getPreNotes() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPreNotes());
        }
        if (entity.getPostNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPostNotes());
        }
        statement.bindLong(7, entity.getPreTimestamp());
        statement.bindLong(8, entity.getPostTimestamp());
        if (entity.getPreGpsLocation() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getPreGpsLocation());
        }
        if (entity.getPostGpsLocation() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getPostGpsLocation());
        }
      }
    };
    this.__updateAdapterOfProductCondition = new EntityDeletionOrUpdateAdapter<ProductCondition>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `product_conditions` SET `checkId` = ?,`rentalId` = ?,`prePhotoUrl` = ?,`postPhotoUrl` = ?,`preNotes` = ?,`postNotes` = ?,`preTimestamp` = ?,`postTimestamp` = ?,`preGpsLocation` = ?,`postGpsLocation` = ? WHERE `checkId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ProductCondition entity) {
        if (entity.getCheckId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getCheckId());
        }
        statement.bindLong(2, entity.getRentalId());
        final String _tmp = Converters.fromStringList(entity.getPrePhotoUrl());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        final String _tmp_1 = Converters.fromStringList(entity.getPostPhotoUrl());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp_1);
        }
        if (entity.getPreNotes() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPreNotes());
        }
        if (entity.getPostNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPostNotes());
        }
        statement.bindLong(7, entity.getPreTimestamp());
        statement.bindLong(8, entity.getPostTimestamp());
        if (entity.getPreGpsLocation() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getPreGpsLocation());
        }
        if (entity.getPostGpsLocation() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getPostGpsLocation());
        }
        if (entity.getCheckId() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getCheckId());
        }
      }
    };
  }

  @Override
  public void insert(final ProductCondition condition) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfProductCondition.insert(condition);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<ProductCondition> conditions) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfProductCondition.insert(conditions);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final ProductCondition condition) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfProductCondition.handle(condition);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public ProductCondition getConditionById(final String checkId) {
    final String _sql = "SELECT * FROM product_conditions WHERE checkId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (checkId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, checkId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCheckId = CursorUtil.getColumnIndexOrThrow(_cursor, "checkId");
      final int _cursorIndexOfRentalId = CursorUtil.getColumnIndexOrThrow(_cursor, "rentalId");
      final int _cursorIndexOfPrePhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "prePhotoUrl");
      final int _cursorIndexOfPostPhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "postPhotoUrl");
      final int _cursorIndexOfPreNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "preNotes");
      final int _cursorIndexOfPostNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "postNotes");
      final int _cursorIndexOfPreTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "preTimestamp");
      final int _cursorIndexOfPostTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "postTimestamp");
      final int _cursorIndexOfPreGpsLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "preGpsLocation");
      final int _cursorIndexOfPostGpsLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "postGpsLocation");
      final ProductCondition _result;
      if (_cursor.moveToFirst()) {
        _result = new ProductCondition();
        final String _tmpCheckId;
        if (_cursor.isNull(_cursorIndexOfCheckId)) {
          _tmpCheckId = null;
        } else {
          _tmpCheckId = _cursor.getString(_cursorIndexOfCheckId);
        }
        _result.setCheckId(_tmpCheckId);
        final long _tmpRentalId;
        _tmpRentalId = _cursor.getLong(_cursorIndexOfRentalId);
        _result.setRentalId(_tmpRentalId);
        final List<String> _tmpPrePhotoUrl;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfPrePhotoUrl)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfPrePhotoUrl);
        }
        _tmpPrePhotoUrl = Converters.toStringList(_tmp);
        _result.setPrePhotoUrl(_tmpPrePhotoUrl);
        final List<String> _tmpPostPhotoUrl;
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfPostPhotoUrl)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfPostPhotoUrl);
        }
        _tmpPostPhotoUrl = Converters.toStringList(_tmp_1);
        _result.setPostPhotoUrl(_tmpPostPhotoUrl);
        final String _tmpPreNotes;
        if (_cursor.isNull(_cursorIndexOfPreNotes)) {
          _tmpPreNotes = null;
        } else {
          _tmpPreNotes = _cursor.getString(_cursorIndexOfPreNotes);
        }
        _result.setPreNotes(_tmpPreNotes);
        final String _tmpPostNotes;
        if (_cursor.isNull(_cursorIndexOfPostNotes)) {
          _tmpPostNotes = null;
        } else {
          _tmpPostNotes = _cursor.getString(_cursorIndexOfPostNotes);
        }
        _result.setPostNotes(_tmpPostNotes);
        final long _tmpPreTimestamp;
        _tmpPreTimestamp = _cursor.getLong(_cursorIndexOfPreTimestamp);
        _result.setPreTimestamp(_tmpPreTimestamp);
        final long _tmpPostTimestamp;
        _tmpPostTimestamp = _cursor.getLong(_cursorIndexOfPostTimestamp);
        _result.setPostTimestamp(_tmpPostTimestamp);
        final String _tmpPreGpsLocation;
        if (_cursor.isNull(_cursorIndexOfPreGpsLocation)) {
          _tmpPreGpsLocation = null;
        } else {
          _tmpPreGpsLocation = _cursor.getString(_cursorIndexOfPreGpsLocation);
        }
        _result.setPreGpsLocation(_tmpPreGpsLocation);
        final String _tmpPostGpsLocation;
        if (_cursor.isNull(_cursorIndexOfPostGpsLocation)) {
          _tmpPostGpsLocation = null;
        } else {
          _tmpPostGpsLocation = _cursor.getString(_cursorIndexOfPostGpsLocation);
        }
        _result.setPostGpsLocation(_tmpPostGpsLocation);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<ProductCondition> getConditionForRentalLive(final long rentalId) {
    final String _sql = "SELECT * FROM product_conditions WHERE rentalId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, rentalId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"product_conditions"}, false, new Callable<ProductCondition>() {
      @Override
      @Nullable
      public ProductCondition call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCheckId = CursorUtil.getColumnIndexOrThrow(_cursor, "checkId");
          final int _cursorIndexOfRentalId = CursorUtil.getColumnIndexOrThrow(_cursor, "rentalId");
          final int _cursorIndexOfPrePhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "prePhotoUrl");
          final int _cursorIndexOfPostPhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "postPhotoUrl");
          final int _cursorIndexOfPreNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "preNotes");
          final int _cursorIndexOfPostNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "postNotes");
          final int _cursorIndexOfPreTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "preTimestamp");
          final int _cursorIndexOfPostTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "postTimestamp");
          final int _cursorIndexOfPreGpsLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "preGpsLocation");
          final int _cursorIndexOfPostGpsLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "postGpsLocation");
          final ProductCondition _result;
          if (_cursor.moveToFirst()) {
            _result = new ProductCondition();
            final String _tmpCheckId;
            if (_cursor.isNull(_cursorIndexOfCheckId)) {
              _tmpCheckId = null;
            } else {
              _tmpCheckId = _cursor.getString(_cursorIndexOfCheckId);
            }
            _result.setCheckId(_tmpCheckId);
            final long _tmpRentalId;
            _tmpRentalId = _cursor.getLong(_cursorIndexOfRentalId);
            _result.setRentalId(_tmpRentalId);
            final List<String> _tmpPrePhotoUrl;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPrePhotoUrl)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPrePhotoUrl);
            }
            _tmpPrePhotoUrl = Converters.toStringList(_tmp);
            _result.setPrePhotoUrl(_tmpPrePhotoUrl);
            final List<String> _tmpPostPhotoUrl;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfPostPhotoUrl)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfPostPhotoUrl);
            }
            _tmpPostPhotoUrl = Converters.toStringList(_tmp_1);
            _result.setPostPhotoUrl(_tmpPostPhotoUrl);
            final String _tmpPreNotes;
            if (_cursor.isNull(_cursorIndexOfPreNotes)) {
              _tmpPreNotes = null;
            } else {
              _tmpPreNotes = _cursor.getString(_cursorIndexOfPreNotes);
            }
            _result.setPreNotes(_tmpPreNotes);
            final String _tmpPostNotes;
            if (_cursor.isNull(_cursorIndexOfPostNotes)) {
              _tmpPostNotes = null;
            } else {
              _tmpPostNotes = _cursor.getString(_cursorIndexOfPostNotes);
            }
            _result.setPostNotes(_tmpPostNotes);
            final long _tmpPreTimestamp;
            _tmpPreTimestamp = _cursor.getLong(_cursorIndexOfPreTimestamp);
            _result.setPreTimestamp(_tmpPreTimestamp);
            final long _tmpPostTimestamp;
            _tmpPostTimestamp = _cursor.getLong(_cursorIndexOfPostTimestamp);
            _result.setPostTimestamp(_tmpPostTimestamp);
            final String _tmpPreGpsLocation;
            if (_cursor.isNull(_cursorIndexOfPreGpsLocation)) {
              _tmpPreGpsLocation = null;
            } else {
              _tmpPreGpsLocation = _cursor.getString(_cursorIndexOfPreGpsLocation);
            }
            _result.setPreGpsLocation(_tmpPreGpsLocation);
            final String _tmpPostGpsLocation;
            if (_cursor.isNull(_cursorIndexOfPostGpsLocation)) {
              _tmpPostGpsLocation = null;
            } else {
              _tmpPostGpsLocation = _cursor.getString(_cursorIndexOfPostGpsLocation);
            }
            _result.setPostGpsLocation(_tmpPostGpsLocation);
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
  public ProductCondition getConditionForRental(final long rentalId) {
    final String _sql = "SELECT * FROM product_conditions WHERE rentalId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, rentalId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCheckId = CursorUtil.getColumnIndexOrThrow(_cursor, "checkId");
      final int _cursorIndexOfRentalId = CursorUtil.getColumnIndexOrThrow(_cursor, "rentalId");
      final int _cursorIndexOfPrePhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "prePhotoUrl");
      final int _cursorIndexOfPostPhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "postPhotoUrl");
      final int _cursorIndexOfPreNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "preNotes");
      final int _cursorIndexOfPostNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "postNotes");
      final int _cursorIndexOfPreTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "preTimestamp");
      final int _cursorIndexOfPostTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "postTimestamp");
      final int _cursorIndexOfPreGpsLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "preGpsLocation");
      final int _cursorIndexOfPostGpsLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "postGpsLocation");
      final ProductCondition _result;
      if (_cursor.moveToFirst()) {
        _result = new ProductCondition();
        final String _tmpCheckId;
        if (_cursor.isNull(_cursorIndexOfCheckId)) {
          _tmpCheckId = null;
        } else {
          _tmpCheckId = _cursor.getString(_cursorIndexOfCheckId);
        }
        _result.setCheckId(_tmpCheckId);
        final long _tmpRentalId;
        _tmpRentalId = _cursor.getLong(_cursorIndexOfRentalId);
        _result.setRentalId(_tmpRentalId);
        final List<String> _tmpPrePhotoUrl;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfPrePhotoUrl)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfPrePhotoUrl);
        }
        _tmpPrePhotoUrl = Converters.toStringList(_tmp);
        _result.setPrePhotoUrl(_tmpPrePhotoUrl);
        final List<String> _tmpPostPhotoUrl;
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfPostPhotoUrl)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfPostPhotoUrl);
        }
        _tmpPostPhotoUrl = Converters.toStringList(_tmp_1);
        _result.setPostPhotoUrl(_tmpPostPhotoUrl);
        final String _tmpPreNotes;
        if (_cursor.isNull(_cursorIndexOfPreNotes)) {
          _tmpPreNotes = null;
        } else {
          _tmpPreNotes = _cursor.getString(_cursorIndexOfPreNotes);
        }
        _result.setPreNotes(_tmpPreNotes);
        final String _tmpPostNotes;
        if (_cursor.isNull(_cursorIndexOfPostNotes)) {
          _tmpPostNotes = null;
        } else {
          _tmpPostNotes = _cursor.getString(_cursorIndexOfPostNotes);
        }
        _result.setPostNotes(_tmpPostNotes);
        final long _tmpPreTimestamp;
        _tmpPreTimestamp = _cursor.getLong(_cursorIndexOfPreTimestamp);
        _result.setPreTimestamp(_tmpPreTimestamp);
        final long _tmpPostTimestamp;
        _tmpPostTimestamp = _cursor.getLong(_cursorIndexOfPostTimestamp);
        _result.setPostTimestamp(_tmpPostTimestamp);
        final String _tmpPreGpsLocation;
        if (_cursor.isNull(_cursorIndexOfPreGpsLocation)) {
          _tmpPreGpsLocation = null;
        } else {
          _tmpPreGpsLocation = _cursor.getString(_cursorIndexOfPreGpsLocation);
        }
        _result.setPreGpsLocation(_tmpPreGpsLocation);
        final String _tmpPostGpsLocation;
        if (_cursor.isNull(_cursorIndexOfPostGpsLocation)) {
          _tmpPostGpsLocation = null;
        } else {
          _tmpPostGpsLocation = _cursor.getString(_cursorIndexOfPostGpsLocation);
        }
        _result.setPostGpsLocation(_tmpPostGpsLocation);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<ProductCondition>> getAllConditionsLive() {
    final String _sql = "SELECT * FROM product_conditions ORDER BY preTimestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"product_conditions"}, false, new Callable<List<ProductCondition>>() {
      @Override
      @Nullable
      public List<ProductCondition> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCheckId = CursorUtil.getColumnIndexOrThrow(_cursor, "checkId");
          final int _cursorIndexOfRentalId = CursorUtil.getColumnIndexOrThrow(_cursor, "rentalId");
          final int _cursorIndexOfPrePhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "prePhotoUrl");
          final int _cursorIndexOfPostPhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "postPhotoUrl");
          final int _cursorIndexOfPreNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "preNotes");
          final int _cursorIndexOfPostNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "postNotes");
          final int _cursorIndexOfPreTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "preTimestamp");
          final int _cursorIndexOfPostTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "postTimestamp");
          final int _cursorIndexOfPreGpsLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "preGpsLocation");
          final int _cursorIndexOfPostGpsLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "postGpsLocation");
          final List<ProductCondition> _result = new ArrayList<ProductCondition>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProductCondition _item;
            _item = new ProductCondition();
            final String _tmpCheckId;
            if (_cursor.isNull(_cursorIndexOfCheckId)) {
              _tmpCheckId = null;
            } else {
              _tmpCheckId = _cursor.getString(_cursorIndexOfCheckId);
            }
            _item.setCheckId(_tmpCheckId);
            final long _tmpRentalId;
            _tmpRentalId = _cursor.getLong(_cursorIndexOfRentalId);
            _item.setRentalId(_tmpRentalId);
            final List<String> _tmpPrePhotoUrl;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPrePhotoUrl)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPrePhotoUrl);
            }
            _tmpPrePhotoUrl = Converters.toStringList(_tmp);
            _item.setPrePhotoUrl(_tmpPrePhotoUrl);
            final List<String> _tmpPostPhotoUrl;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfPostPhotoUrl)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfPostPhotoUrl);
            }
            _tmpPostPhotoUrl = Converters.toStringList(_tmp_1);
            _item.setPostPhotoUrl(_tmpPostPhotoUrl);
            final String _tmpPreNotes;
            if (_cursor.isNull(_cursorIndexOfPreNotes)) {
              _tmpPreNotes = null;
            } else {
              _tmpPreNotes = _cursor.getString(_cursorIndexOfPreNotes);
            }
            _item.setPreNotes(_tmpPreNotes);
            final String _tmpPostNotes;
            if (_cursor.isNull(_cursorIndexOfPostNotes)) {
              _tmpPostNotes = null;
            } else {
              _tmpPostNotes = _cursor.getString(_cursorIndexOfPostNotes);
            }
            _item.setPostNotes(_tmpPostNotes);
            final long _tmpPreTimestamp;
            _tmpPreTimestamp = _cursor.getLong(_cursorIndexOfPreTimestamp);
            _item.setPreTimestamp(_tmpPreTimestamp);
            final long _tmpPostTimestamp;
            _tmpPostTimestamp = _cursor.getLong(_cursorIndexOfPostTimestamp);
            _item.setPostTimestamp(_tmpPostTimestamp);
            final String _tmpPreGpsLocation;
            if (_cursor.isNull(_cursorIndexOfPreGpsLocation)) {
              _tmpPreGpsLocation = null;
            } else {
              _tmpPreGpsLocation = _cursor.getString(_cursorIndexOfPreGpsLocation);
            }
            _item.setPreGpsLocation(_tmpPreGpsLocation);
            final String _tmpPostGpsLocation;
            if (_cursor.isNull(_cursorIndexOfPostGpsLocation)) {
              _tmpPostGpsLocation = null;
            } else {
              _tmpPostGpsLocation = _cursor.getString(_cursorIndexOfPostGpsLocation);
            }
            _item.setPostGpsLocation(_tmpPostGpsLocation);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
