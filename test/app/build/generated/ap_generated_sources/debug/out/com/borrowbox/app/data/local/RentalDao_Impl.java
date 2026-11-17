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
import com.borrowbox.app.model.PriceUnit;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.model.RentalStatus;
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
public final class RentalDao_Impl implements RentalDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Rental> __insertionAdapterOfRental;

  private final EntityDeletionOrUpdateAdapter<Rental> __updateAdapterOfRental;

  public RentalDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRental = new EntityInsertionAdapter<Rental>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `rentals` (`id`,`productId`,`ownerId`,`borrowerId`,`durationValue`,`durationUnit`,`totalCost`,`deposit`,`lateFee`,`status`,`requestedAt`,`confirmedAt`,`startAt`,`endAt`,`returnedAt`,`qrToken`,`beforePhotoPath`,`afterPhotoPath`,`urgent`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Rental entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        statement.bindLong(3, entity.getOwnerId());
        statement.bindLong(4, entity.getBorrowerId());
        statement.bindLong(5, entity.getDurationValue());
        final String _tmp = Converters.fromPriceUnit(entity.getDurationUnit());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp);
        }
        statement.bindLong(7, entity.getTotalCost());
        statement.bindLong(8, entity.getDeposit());
        statement.bindLong(9, entity.getLateFee());
        final String _tmp_1 = Converters.fromRentalStatus(entity.getStatus());
        if (_tmp_1 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_1);
        }
        statement.bindLong(11, entity.getRequestedAt());
        statement.bindLong(12, entity.getConfirmedAt());
        statement.bindLong(13, entity.getStartAt());
        statement.bindLong(14, entity.getEndAt());
        statement.bindLong(15, entity.getReturnedAt());
        if (entity.getQrToken() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getQrToken());
        }
        if (entity.getBeforePhotoPath() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getBeforePhotoPath());
        }
        if (entity.getAfterPhotoPath() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getAfterPhotoPath());
        }
        final int _tmp_2 = entity.isUrgent() ? 1 : 0;
        statement.bindLong(19, _tmp_2);
      }
    };
    this.__updateAdapterOfRental = new EntityDeletionOrUpdateAdapter<Rental>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `rentals` SET `id` = ?,`productId` = ?,`ownerId` = ?,`borrowerId` = ?,`durationValue` = ?,`durationUnit` = ?,`totalCost` = ?,`deposit` = ?,`lateFee` = ?,`status` = ?,`requestedAt` = ?,`confirmedAt` = ?,`startAt` = ?,`endAt` = ?,`returnedAt` = ?,`qrToken` = ?,`beforePhotoPath` = ?,`afterPhotoPath` = ?,`urgent` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Rental entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        statement.bindLong(3, entity.getOwnerId());
        statement.bindLong(4, entity.getBorrowerId());
        statement.bindLong(5, entity.getDurationValue());
        final String _tmp = Converters.fromPriceUnit(entity.getDurationUnit());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp);
        }
        statement.bindLong(7, entity.getTotalCost());
        statement.bindLong(8, entity.getDeposit());
        statement.bindLong(9, entity.getLateFee());
        final String _tmp_1 = Converters.fromRentalStatus(entity.getStatus());
        if (_tmp_1 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_1);
        }
        statement.bindLong(11, entity.getRequestedAt());
        statement.bindLong(12, entity.getConfirmedAt());
        statement.bindLong(13, entity.getStartAt());
        statement.bindLong(14, entity.getEndAt());
        statement.bindLong(15, entity.getReturnedAt());
        if (entity.getQrToken() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getQrToken());
        }
        if (entity.getBeforePhotoPath() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getBeforePhotoPath());
        }
        if (entity.getAfterPhotoPath() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getAfterPhotoPath());
        }
        final int _tmp_2 = entity.isUrgent() ? 1 : 0;
        statement.bindLong(19, _tmp_2);
        statement.bindLong(20, entity.getId());
      }
    };
  }

  @Override
  public long insert(final Rental rental) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfRental.insertAndReturnId(rental);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<Rental> rentals) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfRental.insert(rentals);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Rental rental) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfRental.handle(rental);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<Rental> getRentalByIdLive(final long id) {
    final String _sql = "SELECT * FROM rentals WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"rentals"}, false, new Callable<Rental>() {
      @Override
      @Nullable
      public Rental call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
          final int _cursorIndexOfBorrowerId = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowerId");
          final int _cursorIndexOfDurationValue = CursorUtil.getColumnIndexOrThrow(_cursor, "durationValue");
          final int _cursorIndexOfDurationUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "durationUnit");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "deposit");
          final int _cursorIndexOfLateFee = CursorUtil.getColumnIndexOrThrow(_cursor, "lateFee");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfRequestedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "requestedAt");
          final int _cursorIndexOfConfirmedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "confirmedAt");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfReturnedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "returnedAt");
          final int _cursorIndexOfQrToken = CursorUtil.getColumnIndexOrThrow(_cursor, "qrToken");
          final int _cursorIndexOfBeforePhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "beforePhotoPath");
          final int _cursorIndexOfAfterPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "afterPhotoPath");
          final int _cursorIndexOfUrgent = CursorUtil.getColumnIndexOrThrow(_cursor, "urgent");
          final Rental _result;
          if (_cursor.moveToFirst()) {
            _result = new Rental();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            _result.setProductId(_tmpProductId);
            final long _tmpOwnerId;
            _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
            _result.setOwnerId(_tmpOwnerId);
            final long _tmpBorrowerId;
            _tmpBorrowerId = _cursor.getLong(_cursorIndexOfBorrowerId);
            _result.setBorrowerId(_tmpBorrowerId);
            final int _tmpDurationValue;
            _tmpDurationValue = _cursor.getInt(_cursorIndexOfDurationValue);
            _result.setDurationValue(_tmpDurationValue);
            final PriceUnit _tmpDurationUnit;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDurationUnit)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDurationUnit);
            }
            _tmpDurationUnit = Converters.toPriceUnit(_tmp);
            _result.setDurationUnit(_tmpDurationUnit);
            final int _tmpTotalCost;
            _tmpTotalCost = _cursor.getInt(_cursorIndexOfTotalCost);
            _result.setTotalCost(_tmpTotalCost);
            final int _tmpDeposit;
            _tmpDeposit = _cursor.getInt(_cursorIndexOfDeposit);
            _result.setDeposit(_tmpDeposit);
            final int _tmpLateFee;
            _tmpLateFee = _cursor.getInt(_cursorIndexOfLateFee);
            _result.setLateFee(_tmpLateFee);
            final RentalStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = Converters.toRentalStatus(_tmp_1);
            _result.setStatus(_tmpStatus);
            final long _tmpRequestedAt;
            _tmpRequestedAt = _cursor.getLong(_cursorIndexOfRequestedAt);
            _result.setRequestedAt(_tmpRequestedAt);
            final long _tmpConfirmedAt;
            _tmpConfirmedAt = _cursor.getLong(_cursorIndexOfConfirmedAt);
            _result.setConfirmedAt(_tmpConfirmedAt);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            _result.setStartAt(_tmpStartAt);
            final long _tmpEndAt;
            _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            _result.setEndAt(_tmpEndAt);
            final long _tmpReturnedAt;
            _tmpReturnedAt = _cursor.getLong(_cursorIndexOfReturnedAt);
            _result.setReturnedAt(_tmpReturnedAt);
            final String _tmpQrToken;
            if (_cursor.isNull(_cursorIndexOfQrToken)) {
              _tmpQrToken = null;
            } else {
              _tmpQrToken = _cursor.getString(_cursorIndexOfQrToken);
            }
            _result.setQrToken(_tmpQrToken);
            final String _tmpBeforePhotoPath;
            if (_cursor.isNull(_cursorIndexOfBeforePhotoPath)) {
              _tmpBeforePhotoPath = null;
            } else {
              _tmpBeforePhotoPath = _cursor.getString(_cursorIndexOfBeforePhotoPath);
            }
            _result.setBeforePhotoPath(_tmpBeforePhotoPath);
            final String _tmpAfterPhotoPath;
            if (_cursor.isNull(_cursorIndexOfAfterPhotoPath)) {
              _tmpAfterPhotoPath = null;
            } else {
              _tmpAfterPhotoPath = _cursor.getString(_cursorIndexOfAfterPhotoPath);
            }
            _result.setAfterPhotoPath(_tmpAfterPhotoPath);
            final boolean _tmpUrgent;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfUrgent);
            _tmpUrgent = _tmp_2 != 0;
            _result.setUrgent(_tmpUrgent);
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
  public Rental getRentalById(final long id) {
    final String _sql = "SELECT * FROM rentals WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
      final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
      final int _cursorIndexOfBorrowerId = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowerId");
      final int _cursorIndexOfDurationValue = CursorUtil.getColumnIndexOrThrow(_cursor, "durationValue");
      final int _cursorIndexOfDurationUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "durationUnit");
      final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
      final int _cursorIndexOfDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "deposit");
      final int _cursorIndexOfLateFee = CursorUtil.getColumnIndexOrThrow(_cursor, "lateFee");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfRequestedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "requestedAt");
      final int _cursorIndexOfConfirmedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "confirmedAt");
      final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
      final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
      final int _cursorIndexOfReturnedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "returnedAt");
      final int _cursorIndexOfQrToken = CursorUtil.getColumnIndexOrThrow(_cursor, "qrToken");
      final int _cursorIndexOfBeforePhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "beforePhotoPath");
      final int _cursorIndexOfAfterPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "afterPhotoPath");
      final int _cursorIndexOfUrgent = CursorUtil.getColumnIndexOrThrow(_cursor, "urgent");
      final Rental _result;
      if (_cursor.moveToFirst()) {
        _result = new Rental();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final long _tmpProductId;
        _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
        _result.setProductId(_tmpProductId);
        final long _tmpOwnerId;
        _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
        _result.setOwnerId(_tmpOwnerId);
        final long _tmpBorrowerId;
        _tmpBorrowerId = _cursor.getLong(_cursorIndexOfBorrowerId);
        _result.setBorrowerId(_tmpBorrowerId);
        final int _tmpDurationValue;
        _tmpDurationValue = _cursor.getInt(_cursorIndexOfDurationValue);
        _result.setDurationValue(_tmpDurationValue);
        final PriceUnit _tmpDurationUnit;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfDurationUnit)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfDurationUnit);
        }
        _tmpDurationUnit = Converters.toPriceUnit(_tmp);
        _result.setDurationUnit(_tmpDurationUnit);
        final int _tmpTotalCost;
        _tmpTotalCost = _cursor.getInt(_cursorIndexOfTotalCost);
        _result.setTotalCost(_tmpTotalCost);
        final int _tmpDeposit;
        _tmpDeposit = _cursor.getInt(_cursorIndexOfDeposit);
        _result.setDeposit(_tmpDeposit);
        final int _tmpLateFee;
        _tmpLateFee = _cursor.getInt(_cursorIndexOfLateFee);
        _result.setLateFee(_tmpLateFee);
        final RentalStatus _tmpStatus;
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
        }
        _tmpStatus = Converters.toRentalStatus(_tmp_1);
        _result.setStatus(_tmpStatus);
        final long _tmpRequestedAt;
        _tmpRequestedAt = _cursor.getLong(_cursorIndexOfRequestedAt);
        _result.setRequestedAt(_tmpRequestedAt);
        final long _tmpConfirmedAt;
        _tmpConfirmedAt = _cursor.getLong(_cursorIndexOfConfirmedAt);
        _result.setConfirmedAt(_tmpConfirmedAt);
        final long _tmpStartAt;
        _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
        _result.setStartAt(_tmpStartAt);
        final long _tmpEndAt;
        _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
        _result.setEndAt(_tmpEndAt);
        final long _tmpReturnedAt;
        _tmpReturnedAt = _cursor.getLong(_cursorIndexOfReturnedAt);
        _result.setReturnedAt(_tmpReturnedAt);
        final String _tmpQrToken;
        if (_cursor.isNull(_cursorIndexOfQrToken)) {
          _tmpQrToken = null;
        } else {
          _tmpQrToken = _cursor.getString(_cursorIndexOfQrToken);
        }
        _result.setQrToken(_tmpQrToken);
        final String _tmpBeforePhotoPath;
        if (_cursor.isNull(_cursorIndexOfBeforePhotoPath)) {
          _tmpBeforePhotoPath = null;
        } else {
          _tmpBeforePhotoPath = _cursor.getString(_cursorIndexOfBeforePhotoPath);
        }
        _result.setBeforePhotoPath(_tmpBeforePhotoPath);
        final String _tmpAfterPhotoPath;
        if (_cursor.isNull(_cursorIndexOfAfterPhotoPath)) {
          _tmpAfterPhotoPath = null;
        } else {
          _tmpAfterPhotoPath = _cursor.getString(_cursorIndexOfAfterPhotoPath);
        }
        _result.setAfterPhotoPath(_tmpAfterPhotoPath);
        final boolean _tmpUrgent;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfUrgent);
        _tmpUrgent = _tmp_2 != 0;
        _result.setUrgent(_tmpUrgent);
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
  public LiveData<List<Rental>> getAllRentalsLive() {
    final String _sql = "SELECT * FROM rentals ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"rentals"}, false, new Callable<List<Rental>>() {
      @Override
      @Nullable
      public List<Rental> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
          final int _cursorIndexOfBorrowerId = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowerId");
          final int _cursorIndexOfDurationValue = CursorUtil.getColumnIndexOrThrow(_cursor, "durationValue");
          final int _cursorIndexOfDurationUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "durationUnit");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "deposit");
          final int _cursorIndexOfLateFee = CursorUtil.getColumnIndexOrThrow(_cursor, "lateFee");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfRequestedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "requestedAt");
          final int _cursorIndexOfConfirmedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "confirmedAt");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfReturnedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "returnedAt");
          final int _cursorIndexOfQrToken = CursorUtil.getColumnIndexOrThrow(_cursor, "qrToken");
          final int _cursorIndexOfBeforePhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "beforePhotoPath");
          final int _cursorIndexOfAfterPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "afterPhotoPath");
          final int _cursorIndexOfUrgent = CursorUtil.getColumnIndexOrThrow(_cursor, "urgent");
          final List<Rental> _result = new ArrayList<Rental>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Rental _item;
            _item = new Rental();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            _item.setProductId(_tmpProductId);
            final long _tmpOwnerId;
            _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
            _item.setOwnerId(_tmpOwnerId);
            final long _tmpBorrowerId;
            _tmpBorrowerId = _cursor.getLong(_cursorIndexOfBorrowerId);
            _item.setBorrowerId(_tmpBorrowerId);
            final int _tmpDurationValue;
            _tmpDurationValue = _cursor.getInt(_cursorIndexOfDurationValue);
            _item.setDurationValue(_tmpDurationValue);
            final PriceUnit _tmpDurationUnit;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDurationUnit)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDurationUnit);
            }
            _tmpDurationUnit = Converters.toPriceUnit(_tmp);
            _item.setDurationUnit(_tmpDurationUnit);
            final int _tmpTotalCost;
            _tmpTotalCost = _cursor.getInt(_cursorIndexOfTotalCost);
            _item.setTotalCost(_tmpTotalCost);
            final int _tmpDeposit;
            _tmpDeposit = _cursor.getInt(_cursorIndexOfDeposit);
            _item.setDeposit(_tmpDeposit);
            final int _tmpLateFee;
            _tmpLateFee = _cursor.getInt(_cursorIndexOfLateFee);
            _item.setLateFee(_tmpLateFee);
            final RentalStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = Converters.toRentalStatus(_tmp_1);
            _item.setStatus(_tmpStatus);
            final long _tmpRequestedAt;
            _tmpRequestedAt = _cursor.getLong(_cursorIndexOfRequestedAt);
            _item.setRequestedAt(_tmpRequestedAt);
            final long _tmpConfirmedAt;
            _tmpConfirmedAt = _cursor.getLong(_cursorIndexOfConfirmedAt);
            _item.setConfirmedAt(_tmpConfirmedAt);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            _item.setStartAt(_tmpStartAt);
            final long _tmpEndAt;
            _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            _item.setEndAt(_tmpEndAt);
            final long _tmpReturnedAt;
            _tmpReturnedAt = _cursor.getLong(_cursorIndexOfReturnedAt);
            _item.setReturnedAt(_tmpReturnedAt);
            final String _tmpQrToken;
            if (_cursor.isNull(_cursorIndexOfQrToken)) {
              _tmpQrToken = null;
            } else {
              _tmpQrToken = _cursor.getString(_cursorIndexOfQrToken);
            }
            _item.setQrToken(_tmpQrToken);
            final String _tmpBeforePhotoPath;
            if (_cursor.isNull(_cursorIndexOfBeforePhotoPath)) {
              _tmpBeforePhotoPath = null;
            } else {
              _tmpBeforePhotoPath = _cursor.getString(_cursorIndexOfBeforePhotoPath);
            }
            _item.setBeforePhotoPath(_tmpBeforePhotoPath);
            final String _tmpAfterPhotoPath;
            if (_cursor.isNull(_cursorIndexOfAfterPhotoPath)) {
              _tmpAfterPhotoPath = null;
            } else {
              _tmpAfterPhotoPath = _cursor.getString(_cursorIndexOfAfterPhotoPath);
            }
            _item.setAfterPhotoPath(_tmpAfterPhotoPath);
            final boolean _tmpUrgent;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfUrgent);
            _tmpUrgent = _tmp_2 != 0;
            _item.setUrgent(_tmpUrgent);
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
  public LiveData<List<Rental>> getRentalsForUserLive(final long userId) {
    final String _sql = "SELECT * FROM rentals WHERE borrowerId = ? OR ownerId = ? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"rentals"}, false, new Callable<List<Rental>>() {
      @Override
      @Nullable
      public List<Rental> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
          final int _cursorIndexOfBorrowerId = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowerId");
          final int _cursorIndexOfDurationValue = CursorUtil.getColumnIndexOrThrow(_cursor, "durationValue");
          final int _cursorIndexOfDurationUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "durationUnit");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "deposit");
          final int _cursorIndexOfLateFee = CursorUtil.getColumnIndexOrThrow(_cursor, "lateFee");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfRequestedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "requestedAt");
          final int _cursorIndexOfConfirmedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "confirmedAt");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfReturnedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "returnedAt");
          final int _cursorIndexOfQrToken = CursorUtil.getColumnIndexOrThrow(_cursor, "qrToken");
          final int _cursorIndexOfBeforePhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "beforePhotoPath");
          final int _cursorIndexOfAfterPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "afterPhotoPath");
          final int _cursorIndexOfUrgent = CursorUtil.getColumnIndexOrThrow(_cursor, "urgent");
          final List<Rental> _result = new ArrayList<Rental>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Rental _item;
            _item = new Rental();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            _item.setProductId(_tmpProductId);
            final long _tmpOwnerId;
            _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
            _item.setOwnerId(_tmpOwnerId);
            final long _tmpBorrowerId;
            _tmpBorrowerId = _cursor.getLong(_cursorIndexOfBorrowerId);
            _item.setBorrowerId(_tmpBorrowerId);
            final int _tmpDurationValue;
            _tmpDurationValue = _cursor.getInt(_cursorIndexOfDurationValue);
            _item.setDurationValue(_tmpDurationValue);
            final PriceUnit _tmpDurationUnit;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDurationUnit)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDurationUnit);
            }
            _tmpDurationUnit = Converters.toPriceUnit(_tmp);
            _item.setDurationUnit(_tmpDurationUnit);
            final int _tmpTotalCost;
            _tmpTotalCost = _cursor.getInt(_cursorIndexOfTotalCost);
            _item.setTotalCost(_tmpTotalCost);
            final int _tmpDeposit;
            _tmpDeposit = _cursor.getInt(_cursorIndexOfDeposit);
            _item.setDeposit(_tmpDeposit);
            final int _tmpLateFee;
            _tmpLateFee = _cursor.getInt(_cursorIndexOfLateFee);
            _item.setLateFee(_tmpLateFee);
            final RentalStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = Converters.toRentalStatus(_tmp_1);
            _item.setStatus(_tmpStatus);
            final long _tmpRequestedAt;
            _tmpRequestedAt = _cursor.getLong(_cursorIndexOfRequestedAt);
            _item.setRequestedAt(_tmpRequestedAt);
            final long _tmpConfirmedAt;
            _tmpConfirmedAt = _cursor.getLong(_cursorIndexOfConfirmedAt);
            _item.setConfirmedAt(_tmpConfirmedAt);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            _item.setStartAt(_tmpStartAt);
            final long _tmpEndAt;
            _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            _item.setEndAt(_tmpEndAt);
            final long _tmpReturnedAt;
            _tmpReturnedAt = _cursor.getLong(_cursorIndexOfReturnedAt);
            _item.setReturnedAt(_tmpReturnedAt);
            final String _tmpQrToken;
            if (_cursor.isNull(_cursorIndexOfQrToken)) {
              _tmpQrToken = null;
            } else {
              _tmpQrToken = _cursor.getString(_cursorIndexOfQrToken);
            }
            _item.setQrToken(_tmpQrToken);
            final String _tmpBeforePhotoPath;
            if (_cursor.isNull(_cursorIndexOfBeforePhotoPath)) {
              _tmpBeforePhotoPath = null;
            } else {
              _tmpBeforePhotoPath = _cursor.getString(_cursorIndexOfBeforePhotoPath);
            }
            _item.setBeforePhotoPath(_tmpBeforePhotoPath);
            final String _tmpAfterPhotoPath;
            if (_cursor.isNull(_cursorIndexOfAfterPhotoPath)) {
              _tmpAfterPhotoPath = null;
            } else {
              _tmpAfterPhotoPath = _cursor.getString(_cursorIndexOfAfterPhotoPath);
            }
            _item.setAfterPhotoPath(_tmpAfterPhotoPath);
            final boolean _tmpUrgent;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfUrgent);
            _tmpUrgent = _tmp_2 != 0;
            _item.setUrgent(_tmpUrgent);
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
  public LiveData<List<Rental>> getRentalsByStatusLive(final long userId, final String status) {
    final String _sql = "SELECT * FROM rentals WHERE (borrowerId = ? OR ownerId = ?) AND status = ? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 3;
    if (status == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"rentals"}, false, new Callable<List<Rental>>() {
      @Override
      @Nullable
      public List<Rental> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
          final int _cursorIndexOfBorrowerId = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowerId");
          final int _cursorIndexOfDurationValue = CursorUtil.getColumnIndexOrThrow(_cursor, "durationValue");
          final int _cursorIndexOfDurationUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "durationUnit");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "deposit");
          final int _cursorIndexOfLateFee = CursorUtil.getColumnIndexOrThrow(_cursor, "lateFee");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfRequestedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "requestedAt");
          final int _cursorIndexOfConfirmedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "confirmedAt");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfReturnedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "returnedAt");
          final int _cursorIndexOfQrToken = CursorUtil.getColumnIndexOrThrow(_cursor, "qrToken");
          final int _cursorIndexOfBeforePhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "beforePhotoPath");
          final int _cursorIndexOfAfterPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "afterPhotoPath");
          final int _cursorIndexOfUrgent = CursorUtil.getColumnIndexOrThrow(_cursor, "urgent");
          final List<Rental> _result = new ArrayList<Rental>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Rental _item;
            _item = new Rental();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            _item.setProductId(_tmpProductId);
            final long _tmpOwnerId;
            _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
            _item.setOwnerId(_tmpOwnerId);
            final long _tmpBorrowerId;
            _tmpBorrowerId = _cursor.getLong(_cursorIndexOfBorrowerId);
            _item.setBorrowerId(_tmpBorrowerId);
            final int _tmpDurationValue;
            _tmpDurationValue = _cursor.getInt(_cursorIndexOfDurationValue);
            _item.setDurationValue(_tmpDurationValue);
            final PriceUnit _tmpDurationUnit;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDurationUnit)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDurationUnit);
            }
            _tmpDurationUnit = Converters.toPriceUnit(_tmp);
            _item.setDurationUnit(_tmpDurationUnit);
            final int _tmpTotalCost;
            _tmpTotalCost = _cursor.getInt(_cursorIndexOfTotalCost);
            _item.setTotalCost(_tmpTotalCost);
            final int _tmpDeposit;
            _tmpDeposit = _cursor.getInt(_cursorIndexOfDeposit);
            _item.setDeposit(_tmpDeposit);
            final int _tmpLateFee;
            _tmpLateFee = _cursor.getInt(_cursorIndexOfLateFee);
            _item.setLateFee(_tmpLateFee);
            final RentalStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = Converters.toRentalStatus(_tmp_1);
            _item.setStatus(_tmpStatus);
            final long _tmpRequestedAt;
            _tmpRequestedAt = _cursor.getLong(_cursorIndexOfRequestedAt);
            _item.setRequestedAt(_tmpRequestedAt);
            final long _tmpConfirmedAt;
            _tmpConfirmedAt = _cursor.getLong(_cursorIndexOfConfirmedAt);
            _item.setConfirmedAt(_tmpConfirmedAt);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            _item.setStartAt(_tmpStartAt);
            final long _tmpEndAt;
            _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            _item.setEndAt(_tmpEndAt);
            final long _tmpReturnedAt;
            _tmpReturnedAt = _cursor.getLong(_cursorIndexOfReturnedAt);
            _item.setReturnedAt(_tmpReturnedAt);
            final String _tmpQrToken;
            if (_cursor.isNull(_cursorIndexOfQrToken)) {
              _tmpQrToken = null;
            } else {
              _tmpQrToken = _cursor.getString(_cursorIndexOfQrToken);
            }
            _item.setQrToken(_tmpQrToken);
            final String _tmpBeforePhotoPath;
            if (_cursor.isNull(_cursorIndexOfBeforePhotoPath)) {
              _tmpBeforePhotoPath = null;
            } else {
              _tmpBeforePhotoPath = _cursor.getString(_cursorIndexOfBeforePhotoPath);
            }
            _item.setBeforePhotoPath(_tmpBeforePhotoPath);
            final String _tmpAfterPhotoPath;
            if (_cursor.isNull(_cursorIndexOfAfterPhotoPath)) {
              _tmpAfterPhotoPath = null;
            } else {
              _tmpAfterPhotoPath = _cursor.getString(_cursorIndexOfAfterPhotoPath);
            }
            _item.setAfterPhotoPath(_tmpAfterPhotoPath);
            final boolean _tmpUrgent;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfUrgent);
            _tmpUrgent = _tmp_2 != 0;
            _item.setUrgent(_tmpUrgent);
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
  public LiveData<List<Rental>> getRequestsForOwnerLive(final long ownerId) {
    final String _sql = "SELECT * FROM rentals WHERE ownerId = ? AND status = 'REQUESTED' ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, ownerId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"rentals"}, false, new Callable<List<Rental>>() {
      @Override
      @Nullable
      public List<Rental> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
          final int _cursorIndexOfBorrowerId = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowerId");
          final int _cursorIndexOfDurationValue = CursorUtil.getColumnIndexOrThrow(_cursor, "durationValue");
          final int _cursorIndexOfDurationUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "durationUnit");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "deposit");
          final int _cursorIndexOfLateFee = CursorUtil.getColumnIndexOrThrow(_cursor, "lateFee");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfRequestedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "requestedAt");
          final int _cursorIndexOfConfirmedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "confirmedAt");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfReturnedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "returnedAt");
          final int _cursorIndexOfQrToken = CursorUtil.getColumnIndexOrThrow(_cursor, "qrToken");
          final int _cursorIndexOfBeforePhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "beforePhotoPath");
          final int _cursorIndexOfAfterPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "afterPhotoPath");
          final int _cursorIndexOfUrgent = CursorUtil.getColumnIndexOrThrow(_cursor, "urgent");
          final List<Rental> _result = new ArrayList<Rental>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Rental _item;
            _item = new Rental();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            _item.setProductId(_tmpProductId);
            final long _tmpOwnerId;
            _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
            _item.setOwnerId(_tmpOwnerId);
            final long _tmpBorrowerId;
            _tmpBorrowerId = _cursor.getLong(_cursorIndexOfBorrowerId);
            _item.setBorrowerId(_tmpBorrowerId);
            final int _tmpDurationValue;
            _tmpDurationValue = _cursor.getInt(_cursorIndexOfDurationValue);
            _item.setDurationValue(_tmpDurationValue);
            final PriceUnit _tmpDurationUnit;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDurationUnit)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDurationUnit);
            }
            _tmpDurationUnit = Converters.toPriceUnit(_tmp);
            _item.setDurationUnit(_tmpDurationUnit);
            final int _tmpTotalCost;
            _tmpTotalCost = _cursor.getInt(_cursorIndexOfTotalCost);
            _item.setTotalCost(_tmpTotalCost);
            final int _tmpDeposit;
            _tmpDeposit = _cursor.getInt(_cursorIndexOfDeposit);
            _item.setDeposit(_tmpDeposit);
            final int _tmpLateFee;
            _tmpLateFee = _cursor.getInt(_cursorIndexOfLateFee);
            _item.setLateFee(_tmpLateFee);
            final RentalStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = Converters.toRentalStatus(_tmp_1);
            _item.setStatus(_tmpStatus);
            final long _tmpRequestedAt;
            _tmpRequestedAt = _cursor.getLong(_cursorIndexOfRequestedAt);
            _item.setRequestedAt(_tmpRequestedAt);
            final long _tmpConfirmedAt;
            _tmpConfirmedAt = _cursor.getLong(_cursorIndexOfConfirmedAt);
            _item.setConfirmedAt(_tmpConfirmedAt);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            _item.setStartAt(_tmpStartAt);
            final long _tmpEndAt;
            _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            _item.setEndAt(_tmpEndAt);
            final long _tmpReturnedAt;
            _tmpReturnedAt = _cursor.getLong(_cursorIndexOfReturnedAt);
            _item.setReturnedAt(_tmpReturnedAt);
            final String _tmpQrToken;
            if (_cursor.isNull(_cursorIndexOfQrToken)) {
              _tmpQrToken = null;
            } else {
              _tmpQrToken = _cursor.getString(_cursorIndexOfQrToken);
            }
            _item.setQrToken(_tmpQrToken);
            final String _tmpBeforePhotoPath;
            if (_cursor.isNull(_cursorIndexOfBeforePhotoPath)) {
              _tmpBeforePhotoPath = null;
            } else {
              _tmpBeforePhotoPath = _cursor.getString(_cursorIndexOfBeforePhotoPath);
            }
            _item.setBeforePhotoPath(_tmpBeforePhotoPath);
            final String _tmpAfterPhotoPath;
            if (_cursor.isNull(_cursorIndexOfAfterPhotoPath)) {
              _tmpAfterPhotoPath = null;
            } else {
              _tmpAfterPhotoPath = _cursor.getString(_cursorIndexOfAfterPhotoPath);
            }
            _item.setAfterPhotoPath(_tmpAfterPhotoPath);
            final boolean _tmpUrgent;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfUrgent);
            _tmpUrgent = _tmp_2 != 0;
            _item.setUrgent(_tmpUrgent);
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
  public LiveData<List<Rental>> getActiveRentalsLive(final long userId) {
    final String _sql = "SELECT * FROM rentals WHERE (borrowerId = ? OR ownerId = ?) AND status IN ('ACTIVE', 'OVERDUE') ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"rentals"}, false, new Callable<List<Rental>>() {
      @Override
      @Nullable
      public List<Rental> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
          final int _cursorIndexOfBorrowerId = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowerId");
          final int _cursorIndexOfDurationValue = CursorUtil.getColumnIndexOrThrow(_cursor, "durationValue");
          final int _cursorIndexOfDurationUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "durationUnit");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "deposit");
          final int _cursorIndexOfLateFee = CursorUtil.getColumnIndexOrThrow(_cursor, "lateFee");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfRequestedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "requestedAt");
          final int _cursorIndexOfConfirmedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "confirmedAt");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfReturnedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "returnedAt");
          final int _cursorIndexOfQrToken = CursorUtil.getColumnIndexOrThrow(_cursor, "qrToken");
          final int _cursorIndexOfBeforePhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "beforePhotoPath");
          final int _cursorIndexOfAfterPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "afterPhotoPath");
          final int _cursorIndexOfUrgent = CursorUtil.getColumnIndexOrThrow(_cursor, "urgent");
          final List<Rental> _result = new ArrayList<Rental>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Rental _item;
            _item = new Rental();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            _item.setProductId(_tmpProductId);
            final long _tmpOwnerId;
            _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
            _item.setOwnerId(_tmpOwnerId);
            final long _tmpBorrowerId;
            _tmpBorrowerId = _cursor.getLong(_cursorIndexOfBorrowerId);
            _item.setBorrowerId(_tmpBorrowerId);
            final int _tmpDurationValue;
            _tmpDurationValue = _cursor.getInt(_cursorIndexOfDurationValue);
            _item.setDurationValue(_tmpDurationValue);
            final PriceUnit _tmpDurationUnit;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDurationUnit)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDurationUnit);
            }
            _tmpDurationUnit = Converters.toPriceUnit(_tmp);
            _item.setDurationUnit(_tmpDurationUnit);
            final int _tmpTotalCost;
            _tmpTotalCost = _cursor.getInt(_cursorIndexOfTotalCost);
            _item.setTotalCost(_tmpTotalCost);
            final int _tmpDeposit;
            _tmpDeposit = _cursor.getInt(_cursorIndexOfDeposit);
            _item.setDeposit(_tmpDeposit);
            final int _tmpLateFee;
            _tmpLateFee = _cursor.getInt(_cursorIndexOfLateFee);
            _item.setLateFee(_tmpLateFee);
            final RentalStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = Converters.toRentalStatus(_tmp_1);
            _item.setStatus(_tmpStatus);
            final long _tmpRequestedAt;
            _tmpRequestedAt = _cursor.getLong(_cursorIndexOfRequestedAt);
            _item.setRequestedAt(_tmpRequestedAt);
            final long _tmpConfirmedAt;
            _tmpConfirmedAt = _cursor.getLong(_cursorIndexOfConfirmedAt);
            _item.setConfirmedAt(_tmpConfirmedAt);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            _item.setStartAt(_tmpStartAt);
            final long _tmpEndAt;
            _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            _item.setEndAt(_tmpEndAt);
            final long _tmpReturnedAt;
            _tmpReturnedAt = _cursor.getLong(_cursorIndexOfReturnedAt);
            _item.setReturnedAt(_tmpReturnedAt);
            final String _tmpQrToken;
            if (_cursor.isNull(_cursorIndexOfQrToken)) {
              _tmpQrToken = null;
            } else {
              _tmpQrToken = _cursor.getString(_cursorIndexOfQrToken);
            }
            _item.setQrToken(_tmpQrToken);
            final String _tmpBeforePhotoPath;
            if (_cursor.isNull(_cursorIndexOfBeforePhotoPath)) {
              _tmpBeforePhotoPath = null;
            } else {
              _tmpBeforePhotoPath = _cursor.getString(_cursorIndexOfBeforePhotoPath);
            }
            _item.setBeforePhotoPath(_tmpBeforePhotoPath);
            final String _tmpAfterPhotoPath;
            if (_cursor.isNull(_cursorIndexOfAfterPhotoPath)) {
              _tmpAfterPhotoPath = null;
            } else {
              _tmpAfterPhotoPath = _cursor.getString(_cursorIndexOfAfterPhotoPath);
            }
            _item.setAfterPhotoPath(_tmpAfterPhotoPath);
            final boolean _tmpUrgent;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfUrgent);
            _tmpUrgent = _tmp_2 != 0;
            _item.setUrgent(_tmpUrgent);
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
  public LiveData<List<Rental>> getHistoryRentalsLive(final long userId) {
    final String _sql = "SELECT * FROM rentals WHERE (borrowerId = ? OR ownerId = ?) AND status IN ('RETURNED', 'REJECTED', 'CANCELLED') ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"rentals"}, false, new Callable<List<Rental>>() {
      @Override
      @Nullable
      public List<Rental> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
          final int _cursorIndexOfBorrowerId = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowerId");
          final int _cursorIndexOfDurationValue = CursorUtil.getColumnIndexOrThrow(_cursor, "durationValue");
          final int _cursorIndexOfDurationUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "durationUnit");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "deposit");
          final int _cursorIndexOfLateFee = CursorUtil.getColumnIndexOrThrow(_cursor, "lateFee");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfRequestedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "requestedAt");
          final int _cursorIndexOfConfirmedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "confirmedAt");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfReturnedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "returnedAt");
          final int _cursorIndexOfQrToken = CursorUtil.getColumnIndexOrThrow(_cursor, "qrToken");
          final int _cursorIndexOfBeforePhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "beforePhotoPath");
          final int _cursorIndexOfAfterPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "afterPhotoPath");
          final int _cursorIndexOfUrgent = CursorUtil.getColumnIndexOrThrow(_cursor, "urgent");
          final List<Rental> _result = new ArrayList<Rental>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Rental _item;
            _item = new Rental();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            _item.setProductId(_tmpProductId);
            final long _tmpOwnerId;
            _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
            _item.setOwnerId(_tmpOwnerId);
            final long _tmpBorrowerId;
            _tmpBorrowerId = _cursor.getLong(_cursorIndexOfBorrowerId);
            _item.setBorrowerId(_tmpBorrowerId);
            final int _tmpDurationValue;
            _tmpDurationValue = _cursor.getInt(_cursorIndexOfDurationValue);
            _item.setDurationValue(_tmpDurationValue);
            final PriceUnit _tmpDurationUnit;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDurationUnit)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDurationUnit);
            }
            _tmpDurationUnit = Converters.toPriceUnit(_tmp);
            _item.setDurationUnit(_tmpDurationUnit);
            final int _tmpTotalCost;
            _tmpTotalCost = _cursor.getInt(_cursorIndexOfTotalCost);
            _item.setTotalCost(_tmpTotalCost);
            final int _tmpDeposit;
            _tmpDeposit = _cursor.getInt(_cursorIndexOfDeposit);
            _item.setDeposit(_tmpDeposit);
            final int _tmpLateFee;
            _tmpLateFee = _cursor.getInt(_cursorIndexOfLateFee);
            _item.setLateFee(_tmpLateFee);
            final RentalStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = Converters.toRentalStatus(_tmp_1);
            _item.setStatus(_tmpStatus);
            final long _tmpRequestedAt;
            _tmpRequestedAt = _cursor.getLong(_cursorIndexOfRequestedAt);
            _item.setRequestedAt(_tmpRequestedAt);
            final long _tmpConfirmedAt;
            _tmpConfirmedAt = _cursor.getLong(_cursorIndexOfConfirmedAt);
            _item.setConfirmedAt(_tmpConfirmedAt);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            _item.setStartAt(_tmpStartAt);
            final long _tmpEndAt;
            _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            _item.setEndAt(_tmpEndAt);
            final long _tmpReturnedAt;
            _tmpReturnedAt = _cursor.getLong(_cursorIndexOfReturnedAt);
            _item.setReturnedAt(_tmpReturnedAt);
            final String _tmpQrToken;
            if (_cursor.isNull(_cursorIndexOfQrToken)) {
              _tmpQrToken = null;
            } else {
              _tmpQrToken = _cursor.getString(_cursorIndexOfQrToken);
            }
            _item.setQrToken(_tmpQrToken);
            final String _tmpBeforePhotoPath;
            if (_cursor.isNull(_cursorIndexOfBeforePhotoPath)) {
              _tmpBeforePhotoPath = null;
            } else {
              _tmpBeforePhotoPath = _cursor.getString(_cursorIndexOfBeforePhotoPath);
            }
            _item.setBeforePhotoPath(_tmpBeforePhotoPath);
            final String _tmpAfterPhotoPath;
            if (_cursor.isNull(_cursorIndexOfAfterPhotoPath)) {
              _tmpAfterPhotoPath = null;
            } else {
              _tmpAfterPhotoPath = _cursor.getString(_cursorIndexOfAfterPhotoPath);
            }
            _item.setAfterPhotoPath(_tmpAfterPhotoPath);
            final boolean _tmpUrgent;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfUrgent);
            _tmpUrgent = _tmp_2 != 0;
            _item.setUrgent(_tmpUrgent);
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
  public LiveData<Integer> getGlobalActiveRentalsCountLive() {
    final String _sql = "SELECT COUNT(*) FROM rentals WHERE status IN ('ACTIVE', 'OVERDUE')";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"rentals"}, false, new Callable<Integer>() {
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
  public LiveData<Integer> getOverdueRentalsCountLive() {
    final String _sql = "SELECT COUNT(*) FROM rentals WHERE status = 'OVERDUE'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"rentals"}, false, new Callable<Integer>() {
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
  public LiveData<Integer> getTotalRentalsCountLive() {
    final String _sql = "SELECT COUNT(*) FROM rentals";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"rentals"}, false, new Callable<Integer>() {
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
