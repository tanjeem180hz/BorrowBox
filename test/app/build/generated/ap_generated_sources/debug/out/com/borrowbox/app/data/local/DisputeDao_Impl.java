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
import com.borrowbox.app.model.Dispute;
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
public final class DisputeDao_Impl implements DisputeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Dispute> __insertionAdapterOfDispute;

  private final EntityDeletionOrUpdateAdapter<Dispute> __updateAdapterOfDispute;

  private final SharedSQLiteStatement __preparedStmtOfResolveDispute;

  public DisputeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDispute = new EntityInsertionAdapter<Dispute>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `disputes` (`id`,`rentalId`,`reporterId`,`reporterName`,`reportedStudentId`,`reportedStudentName`,`productId`,`productTitle`,`disputeType`,`description`,`evidenceUri`,`depositAmount`,`status`,`adminVerdict`,`depositRefundPercentage`,`createdAt`,`resolvedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Dispute entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getRentalId());
        statement.bindLong(3, entity.getReporterId());
        if (entity.getReporterName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getReporterName());
        }
        statement.bindLong(5, entity.getReportedStudentId());
        if (entity.getReportedStudentName() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getReportedStudentName());
        }
        statement.bindLong(7, entity.getProductId());
        if (entity.getProductTitle() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getProductTitle());
        }
        if (entity.getDisputeType() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getDisputeType());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getDescription());
        }
        if (entity.getEvidenceUri() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getEvidenceUri());
        }
        statement.bindDouble(12, entity.getDepositAmount());
        if (entity.getStatus() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getStatus());
        }
        if (entity.getAdminVerdict() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getAdminVerdict());
        }
        statement.bindLong(15, entity.getDepositRefundPercentage());
        statement.bindLong(16, entity.getCreatedAt());
        statement.bindLong(17, entity.getResolvedAt());
      }
    };
    this.__updateAdapterOfDispute = new EntityDeletionOrUpdateAdapter<Dispute>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `disputes` SET `id` = ?,`rentalId` = ?,`reporterId` = ?,`reporterName` = ?,`reportedStudentId` = ?,`reportedStudentName` = ?,`productId` = ?,`productTitle` = ?,`disputeType` = ?,`description` = ?,`evidenceUri` = ?,`depositAmount` = ?,`status` = ?,`adminVerdict` = ?,`depositRefundPercentage` = ?,`createdAt` = ?,`resolvedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Dispute entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getRentalId());
        statement.bindLong(3, entity.getReporterId());
        if (entity.getReporterName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getReporterName());
        }
        statement.bindLong(5, entity.getReportedStudentId());
        if (entity.getReportedStudentName() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getReportedStudentName());
        }
        statement.bindLong(7, entity.getProductId());
        if (entity.getProductTitle() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getProductTitle());
        }
        if (entity.getDisputeType() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getDisputeType());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getDescription());
        }
        if (entity.getEvidenceUri() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getEvidenceUri());
        }
        statement.bindDouble(12, entity.getDepositAmount());
        if (entity.getStatus() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getStatus());
        }
        if (entity.getAdminVerdict() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getAdminVerdict());
        }
        statement.bindLong(15, entity.getDepositRefundPercentage());
        statement.bindLong(16, entity.getCreatedAt());
        statement.bindLong(17, entity.getResolvedAt());
        statement.bindLong(18, entity.getId());
      }
    };
    this.__preparedStmtOfResolveDispute = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE disputes SET status = ?, adminVerdict = ?, depositRefundPercentage = ?, resolvedAt = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Dispute dispute) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfDispute.insertAndReturnId(dispute);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<Dispute> disputes) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDispute.insert(disputes);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Dispute dispute) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfDispute.handle(dispute);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void resolveDispute(final long disputeId, final String status, final String verdict,
      final int refundPercent, final long resolvedAt) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfResolveDispute.acquire();
    int _argIndex = 1;
    if (status == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, status);
    }
    _argIndex = 2;
    if (verdict == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, verdict);
    }
    _argIndex = 3;
    _stmt.bindLong(_argIndex, refundPercent);
    _argIndex = 4;
    _stmt.bindLong(_argIndex, resolvedAt);
    _argIndex = 5;
    _stmt.bindLong(_argIndex, disputeId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfResolveDispute.release(_stmt);
    }
  }

  @Override
  public LiveData<Dispute> getDisputeByIdLive(final long id) {
    final String _sql = "SELECT * FROM disputes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"disputes"}, false, new Callable<Dispute>() {
      @Override
      @Nullable
      public Dispute call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRentalId = CursorUtil.getColumnIndexOrThrow(_cursor, "rentalId");
          final int _cursorIndexOfReporterId = CursorUtil.getColumnIndexOrThrow(_cursor, "reporterId");
          final int _cursorIndexOfReporterName = CursorUtil.getColumnIndexOrThrow(_cursor, "reporterName");
          final int _cursorIndexOfReportedStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "reportedStudentId");
          final int _cursorIndexOfReportedStudentName = CursorUtil.getColumnIndexOrThrow(_cursor, "reportedStudentName");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfProductTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "productTitle");
          final int _cursorIndexOfDisputeType = CursorUtil.getColumnIndexOrThrow(_cursor, "disputeType");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEvidenceUri = CursorUtil.getColumnIndexOrThrow(_cursor, "evidenceUri");
          final int _cursorIndexOfDepositAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "depositAmount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfAdminVerdict = CursorUtil.getColumnIndexOrThrow(_cursor, "adminVerdict");
          final int _cursorIndexOfDepositRefundPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "depositRefundPercentage");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
          final Dispute _result;
          if (_cursor.moveToFirst()) {
            _result = new Dispute();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final long _tmpRentalId;
            _tmpRentalId = _cursor.getLong(_cursorIndexOfRentalId);
            _result.setRentalId(_tmpRentalId);
            final long _tmpReporterId;
            _tmpReporterId = _cursor.getLong(_cursorIndexOfReporterId);
            _result.setReporterId(_tmpReporterId);
            final String _tmpReporterName;
            if (_cursor.isNull(_cursorIndexOfReporterName)) {
              _tmpReporterName = null;
            } else {
              _tmpReporterName = _cursor.getString(_cursorIndexOfReporterName);
            }
            _result.setReporterName(_tmpReporterName);
            final long _tmpReportedStudentId;
            _tmpReportedStudentId = _cursor.getLong(_cursorIndexOfReportedStudentId);
            _result.setReportedStudentId(_tmpReportedStudentId);
            final String _tmpReportedStudentName;
            if (_cursor.isNull(_cursorIndexOfReportedStudentName)) {
              _tmpReportedStudentName = null;
            } else {
              _tmpReportedStudentName = _cursor.getString(_cursorIndexOfReportedStudentName);
            }
            _result.setReportedStudentName(_tmpReportedStudentName);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            _result.setProductId(_tmpProductId);
            final String _tmpProductTitle;
            if (_cursor.isNull(_cursorIndexOfProductTitle)) {
              _tmpProductTitle = null;
            } else {
              _tmpProductTitle = _cursor.getString(_cursorIndexOfProductTitle);
            }
            _result.setProductTitle(_tmpProductTitle);
            final String _tmpDisputeType;
            if (_cursor.isNull(_cursorIndexOfDisputeType)) {
              _tmpDisputeType = null;
            } else {
              _tmpDisputeType = _cursor.getString(_cursorIndexOfDisputeType);
            }
            _result.setDisputeType(_tmpDisputeType);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _result.setDescription(_tmpDescription);
            final String _tmpEvidenceUri;
            if (_cursor.isNull(_cursorIndexOfEvidenceUri)) {
              _tmpEvidenceUri = null;
            } else {
              _tmpEvidenceUri = _cursor.getString(_cursorIndexOfEvidenceUri);
            }
            _result.setEvidenceUri(_tmpEvidenceUri);
            final double _tmpDepositAmount;
            _tmpDepositAmount = _cursor.getDouble(_cursorIndexOfDepositAmount);
            _result.setDepositAmount(_tmpDepositAmount);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _result.setStatus(_tmpStatus);
            final String _tmpAdminVerdict;
            if (_cursor.isNull(_cursorIndexOfAdminVerdict)) {
              _tmpAdminVerdict = null;
            } else {
              _tmpAdminVerdict = _cursor.getString(_cursorIndexOfAdminVerdict);
            }
            _result.setAdminVerdict(_tmpAdminVerdict);
            final int _tmpDepositRefundPercentage;
            _tmpDepositRefundPercentage = _cursor.getInt(_cursorIndexOfDepositRefundPercentage);
            _result.setDepositRefundPercentage(_tmpDepositRefundPercentage);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result.setCreatedAt(_tmpCreatedAt);
            final long _tmpResolvedAt;
            _tmpResolvedAt = _cursor.getLong(_cursorIndexOfResolvedAt);
            _result.setResolvedAt(_tmpResolvedAt);
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
  public Dispute getDisputeById(final long id) {
    final String _sql = "SELECT * FROM disputes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfRentalId = CursorUtil.getColumnIndexOrThrow(_cursor, "rentalId");
      final int _cursorIndexOfReporterId = CursorUtil.getColumnIndexOrThrow(_cursor, "reporterId");
      final int _cursorIndexOfReporterName = CursorUtil.getColumnIndexOrThrow(_cursor, "reporterName");
      final int _cursorIndexOfReportedStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "reportedStudentId");
      final int _cursorIndexOfReportedStudentName = CursorUtil.getColumnIndexOrThrow(_cursor, "reportedStudentName");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
      final int _cursorIndexOfProductTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "productTitle");
      final int _cursorIndexOfDisputeType = CursorUtil.getColumnIndexOrThrow(_cursor, "disputeType");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfEvidenceUri = CursorUtil.getColumnIndexOrThrow(_cursor, "evidenceUri");
      final int _cursorIndexOfDepositAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "depositAmount");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfAdminVerdict = CursorUtil.getColumnIndexOrThrow(_cursor, "adminVerdict");
      final int _cursorIndexOfDepositRefundPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "depositRefundPercentage");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
      final Dispute _result;
      if (_cursor.moveToFirst()) {
        _result = new Dispute();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final long _tmpRentalId;
        _tmpRentalId = _cursor.getLong(_cursorIndexOfRentalId);
        _result.setRentalId(_tmpRentalId);
        final long _tmpReporterId;
        _tmpReporterId = _cursor.getLong(_cursorIndexOfReporterId);
        _result.setReporterId(_tmpReporterId);
        final String _tmpReporterName;
        if (_cursor.isNull(_cursorIndexOfReporterName)) {
          _tmpReporterName = null;
        } else {
          _tmpReporterName = _cursor.getString(_cursorIndexOfReporterName);
        }
        _result.setReporterName(_tmpReporterName);
        final long _tmpReportedStudentId;
        _tmpReportedStudentId = _cursor.getLong(_cursorIndexOfReportedStudentId);
        _result.setReportedStudentId(_tmpReportedStudentId);
        final String _tmpReportedStudentName;
        if (_cursor.isNull(_cursorIndexOfReportedStudentName)) {
          _tmpReportedStudentName = null;
        } else {
          _tmpReportedStudentName = _cursor.getString(_cursorIndexOfReportedStudentName);
        }
        _result.setReportedStudentName(_tmpReportedStudentName);
        final long _tmpProductId;
        _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
        _result.setProductId(_tmpProductId);
        final String _tmpProductTitle;
        if (_cursor.isNull(_cursorIndexOfProductTitle)) {
          _tmpProductTitle = null;
        } else {
          _tmpProductTitle = _cursor.getString(_cursorIndexOfProductTitle);
        }
        _result.setProductTitle(_tmpProductTitle);
        final String _tmpDisputeType;
        if (_cursor.isNull(_cursorIndexOfDisputeType)) {
          _tmpDisputeType = null;
        } else {
          _tmpDisputeType = _cursor.getString(_cursorIndexOfDisputeType);
        }
        _result.setDisputeType(_tmpDisputeType);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        _result.setDescription(_tmpDescription);
        final String _tmpEvidenceUri;
        if (_cursor.isNull(_cursorIndexOfEvidenceUri)) {
          _tmpEvidenceUri = null;
        } else {
          _tmpEvidenceUri = _cursor.getString(_cursorIndexOfEvidenceUri);
        }
        _result.setEvidenceUri(_tmpEvidenceUri);
        final double _tmpDepositAmount;
        _tmpDepositAmount = _cursor.getDouble(_cursorIndexOfDepositAmount);
        _result.setDepositAmount(_tmpDepositAmount);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        _result.setStatus(_tmpStatus);
        final String _tmpAdminVerdict;
        if (_cursor.isNull(_cursorIndexOfAdminVerdict)) {
          _tmpAdminVerdict = null;
        } else {
          _tmpAdminVerdict = _cursor.getString(_cursorIndexOfAdminVerdict);
        }
        _result.setAdminVerdict(_tmpAdminVerdict);
        final int _tmpDepositRefundPercentage;
        _tmpDepositRefundPercentage = _cursor.getInt(_cursorIndexOfDepositRefundPercentage);
        _result.setDepositRefundPercentage(_tmpDepositRefundPercentage);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _result.setCreatedAt(_tmpCreatedAt);
        final long _tmpResolvedAt;
        _tmpResolvedAt = _cursor.getLong(_cursorIndexOfResolvedAt);
        _result.setResolvedAt(_tmpResolvedAt);
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
  public LiveData<List<Dispute>> getAllDisputesLive() {
    final String _sql = "SELECT * FROM disputes ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"disputes"}, false, new Callable<List<Dispute>>() {
      @Override
      @Nullable
      public List<Dispute> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRentalId = CursorUtil.getColumnIndexOrThrow(_cursor, "rentalId");
          final int _cursorIndexOfReporterId = CursorUtil.getColumnIndexOrThrow(_cursor, "reporterId");
          final int _cursorIndexOfReporterName = CursorUtil.getColumnIndexOrThrow(_cursor, "reporterName");
          final int _cursorIndexOfReportedStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "reportedStudentId");
          final int _cursorIndexOfReportedStudentName = CursorUtil.getColumnIndexOrThrow(_cursor, "reportedStudentName");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfProductTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "productTitle");
          final int _cursorIndexOfDisputeType = CursorUtil.getColumnIndexOrThrow(_cursor, "disputeType");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEvidenceUri = CursorUtil.getColumnIndexOrThrow(_cursor, "evidenceUri");
          final int _cursorIndexOfDepositAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "depositAmount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfAdminVerdict = CursorUtil.getColumnIndexOrThrow(_cursor, "adminVerdict");
          final int _cursorIndexOfDepositRefundPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "depositRefundPercentage");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
          final List<Dispute> _result = new ArrayList<Dispute>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Dispute _item;
            _item = new Dispute();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpRentalId;
            _tmpRentalId = _cursor.getLong(_cursorIndexOfRentalId);
            _item.setRentalId(_tmpRentalId);
            final long _tmpReporterId;
            _tmpReporterId = _cursor.getLong(_cursorIndexOfReporterId);
            _item.setReporterId(_tmpReporterId);
            final String _tmpReporterName;
            if (_cursor.isNull(_cursorIndexOfReporterName)) {
              _tmpReporterName = null;
            } else {
              _tmpReporterName = _cursor.getString(_cursorIndexOfReporterName);
            }
            _item.setReporterName(_tmpReporterName);
            final long _tmpReportedStudentId;
            _tmpReportedStudentId = _cursor.getLong(_cursorIndexOfReportedStudentId);
            _item.setReportedStudentId(_tmpReportedStudentId);
            final String _tmpReportedStudentName;
            if (_cursor.isNull(_cursorIndexOfReportedStudentName)) {
              _tmpReportedStudentName = null;
            } else {
              _tmpReportedStudentName = _cursor.getString(_cursorIndexOfReportedStudentName);
            }
            _item.setReportedStudentName(_tmpReportedStudentName);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            _item.setProductId(_tmpProductId);
            final String _tmpProductTitle;
            if (_cursor.isNull(_cursorIndexOfProductTitle)) {
              _tmpProductTitle = null;
            } else {
              _tmpProductTitle = _cursor.getString(_cursorIndexOfProductTitle);
            }
            _item.setProductTitle(_tmpProductTitle);
            final String _tmpDisputeType;
            if (_cursor.isNull(_cursorIndexOfDisputeType)) {
              _tmpDisputeType = null;
            } else {
              _tmpDisputeType = _cursor.getString(_cursorIndexOfDisputeType);
            }
            _item.setDisputeType(_tmpDisputeType);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final String _tmpEvidenceUri;
            if (_cursor.isNull(_cursorIndexOfEvidenceUri)) {
              _tmpEvidenceUri = null;
            } else {
              _tmpEvidenceUri = _cursor.getString(_cursorIndexOfEvidenceUri);
            }
            _item.setEvidenceUri(_tmpEvidenceUri);
            final double _tmpDepositAmount;
            _tmpDepositAmount = _cursor.getDouble(_cursorIndexOfDepositAmount);
            _item.setDepositAmount(_tmpDepositAmount);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item.setStatus(_tmpStatus);
            final String _tmpAdminVerdict;
            if (_cursor.isNull(_cursorIndexOfAdminVerdict)) {
              _tmpAdminVerdict = null;
            } else {
              _tmpAdminVerdict = _cursor.getString(_cursorIndexOfAdminVerdict);
            }
            _item.setAdminVerdict(_tmpAdminVerdict);
            final int _tmpDepositRefundPercentage;
            _tmpDepositRefundPercentage = _cursor.getInt(_cursorIndexOfDepositRefundPercentage);
            _item.setDepositRefundPercentage(_tmpDepositRefundPercentage);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
            final long _tmpResolvedAt;
            _tmpResolvedAt = _cursor.getLong(_cursorIndexOfResolvedAt);
            _item.setResolvedAt(_tmpResolvedAt);
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
  public List<Dispute> getAllDisputes() {
    final String _sql = "SELECT * FROM disputes ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfRentalId = CursorUtil.getColumnIndexOrThrow(_cursor, "rentalId");
      final int _cursorIndexOfReporterId = CursorUtil.getColumnIndexOrThrow(_cursor, "reporterId");
      final int _cursorIndexOfReporterName = CursorUtil.getColumnIndexOrThrow(_cursor, "reporterName");
      final int _cursorIndexOfReportedStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "reportedStudentId");
      final int _cursorIndexOfReportedStudentName = CursorUtil.getColumnIndexOrThrow(_cursor, "reportedStudentName");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
      final int _cursorIndexOfProductTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "productTitle");
      final int _cursorIndexOfDisputeType = CursorUtil.getColumnIndexOrThrow(_cursor, "disputeType");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfEvidenceUri = CursorUtil.getColumnIndexOrThrow(_cursor, "evidenceUri");
      final int _cursorIndexOfDepositAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "depositAmount");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfAdminVerdict = CursorUtil.getColumnIndexOrThrow(_cursor, "adminVerdict");
      final int _cursorIndexOfDepositRefundPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "depositRefundPercentage");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
      final List<Dispute> _result = new ArrayList<Dispute>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Dispute _item;
        _item = new Dispute();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final long _tmpRentalId;
        _tmpRentalId = _cursor.getLong(_cursorIndexOfRentalId);
        _item.setRentalId(_tmpRentalId);
        final long _tmpReporterId;
        _tmpReporterId = _cursor.getLong(_cursorIndexOfReporterId);
        _item.setReporterId(_tmpReporterId);
        final String _tmpReporterName;
        if (_cursor.isNull(_cursorIndexOfReporterName)) {
          _tmpReporterName = null;
        } else {
          _tmpReporterName = _cursor.getString(_cursorIndexOfReporterName);
        }
        _item.setReporterName(_tmpReporterName);
        final long _tmpReportedStudentId;
        _tmpReportedStudentId = _cursor.getLong(_cursorIndexOfReportedStudentId);
        _item.setReportedStudentId(_tmpReportedStudentId);
        final String _tmpReportedStudentName;
        if (_cursor.isNull(_cursorIndexOfReportedStudentName)) {
          _tmpReportedStudentName = null;
        } else {
          _tmpReportedStudentName = _cursor.getString(_cursorIndexOfReportedStudentName);
        }
        _item.setReportedStudentName(_tmpReportedStudentName);
        final long _tmpProductId;
        _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
        _item.setProductId(_tmpProductId);
        final String _tmpProductTitle;
        if (_cursor.isNull(_cursorIndexOfProductTitle)) {
          _tmpProductTitle = null;
        } else {
          _tmpProductTitle = _cursor.getString(_cursorIndexOfProductTitle);
        }
        _item.setProductTitle(_tmpProductTitle);
        final String _tmpDisputeType;
        if (_cursor.isNull(_cursorIndexOfDisputeType)) {
          _tmpDisputeType = null;
        } else {
          _tmpDisputeType = _cursor.getString(_cursorIndexOfDisputeType);
        }
        _item.setDisputeType(_tmpDisputeType);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        _item.setDescription(_tmpDescription);
        final String _tmpEvidenceUri;
        if (_cursor.isNull(_cursorIndexOfEvidenceUri)) {
          _tmpEvidenceUri = null;
        } else {
          _tmpEvidenceUri = _cursor.getString(_cursorIndexOfEvidenceUri);
        }
        _item.setEvidenceUri(_tmpEvidenceUri);
        final double _tmpDepositAmount;
        _tmpDepositAmount = _cursor.getDouble(_cursorIndexOfDepositAmount);
        _item.setDepositAmount(_tmpDepositAmount);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        _item.setStatus(_tmpStatus);
        final String _tmpAdminVerdict;
        if (_cursor.isNull(_cursorIndexOfAdminVerdict)) {
          _tmpAdminVerdict = null;
        } else {
          _tmpAdminVerdict = _cursor.getString(_cursorIndexOfAdminVerdict);
        }
        _item.setAdminVerdict(_tmpAdminVerdict);
        final int _tmpDepositRefundPercentage;
        _tmpDepositRefundPercentage = _cursor.getInt(_cursorIndexOfDepositRefundPercentage);
        _item.setDepositRefundPercentage(_tmpDepositRefundPercentage);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _item.setCreatedAt(_tmpCreatedAt);
        final long _tmpResolvedAt;
        _tmpResolvedAt = _cursor.getLong(_cursorIndexOfResolvedAt);
        _item.setResolvedAt(_tmpResolvedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Dispute>> getDisputesByStatusLive(final String status) {
    final String _sql = "SELECT * FROM disputes WHERE status = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (status == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"disputes"}, false, new Callable<List<Dispute>>() {
      @Override
      @Nullable
      public List<Dispute> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRentalId = CursorUtil.getColumnIndexOrThrow(_cursor, "rentalId");
          final int _cursorIndexOfReporterId = CursorUtil.getColumnIndexOrThrow(_cursor, "reporterId");
          final int _cursorIndexOfReporterName = CursorUtil.getColumnIndexOrThrow(_cursor, "reporterName");
          final int _cursorIndexOfReportedStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "reportedStudentId");
          final int _cursorIndexOfReportedStudentName = CursorUtil.getColumnIndexOrThrow(_cursor, "reportedStudentName");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfProductTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "productTitle");
          final int _cursorIndexOfDisputeType = CursorUtil.getColumnIndexOrThrow(_cursor, "disputeType");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEvidenceUri = CursorUtil.getColumnIndexOrThrow(_cursor, "evidenceUri");
          final int _cursorIndexOfDepositAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "depositAmount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfAdminVerdict = CursorUtil.getColumnIndexOrThrow(_cursor, "adminVerdict");
          final int _cursorIndexOfDepositRefundPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "depositRefundPercentage");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
          final List<Dispute> _result = new ArrayList<Dispute>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Dispute _item;
            _item = new Dispute();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpRentalId;
            _tmpRentalId = _cursor.getLong(_cursorIndexOfRentalId);
            _item.setRentalId(_tmpRentalId);
            final long _tmpReporterId;
            _tmpReporterId = _cursor.getLong(_cursorIndexOfReporterId);
            _item.setReporterId(_tmpReporterId);
            final String _tmpReporterName;
            if (_cursor.isNull(_cursorIndexOfReporterName)) {
              _tmpReporterName = null;
            } else {
              _tmpReporterName = _cursor.getString(_cursorIndexOfReporterName);
            }
            _item.setReporterName(_tmpReporterName);
            final long _tmpReportedStudentId;
            _tmpReportedStudentId = _cursor.getLong(_cursorIndexOfReportedStudentId);
            _item.setReportedStudentId(_tmpReportedStudentId);
            final String _tmpReportedStudentName;
            if (_cursor.isNull(_cursorIndexOfReportedStudentName)) {
              _tmpReportedStudentName = null;
            } else {
              _tmpReportedStudentName = _cursor.getString(_cursorIndexOfReportedStudentName);
            }
            _item.setReportedStudentName(_tmpReportedStudentName);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            _item.setProductId(_tmpProductId);
            final String _tmpProductTitle;
            if (_cursor.isNull(_cursorIndexOfProductTitle)) {
              _tmpProductTitle = null;
            } else {
              _tmpProductTitle = _cursor.getString(_cursorIndexOfProductTitle);
            }
            _item.setProductTitle(_tmpProductTitle);
            final String _tmpDisputeType;
            if (_cursor.isNull(_cursorIndexOfDisputeType)) {
              _tmpDisputeType = null;
            } else {
              _tmpDisputeType = _cursor.getString(_cursorIndexOfDisputeType);
            }
            _item.setDisputeType(_tmpDisputeType);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final String _tmpEvidenceUri;
            if (_cursor.isNull(_cursorIndexOfEvidenceUri)) {
              _tmpEvidenceUri = null;
            } else {
              _tmpEvidenceUri = _cursor.getString(_cursorIndexOfEvidenceUri);
            }
            _item.setEvidenceUri(_tmpEvidenceUri);
            final double _tmpDepositAmount;
            _tmpDepositAmount = _cursor.getDouble(_cursorIndexOfDepositAmount);
            _item.setDepositAmount(_tmpDepositAmount);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item.setStatus(_tmpStatus);
            final String _tmpAdminVerdict;
            if (_cursor.isNull(_cursorIndexOfAdminVerdict)) {
              _tmpAdminVerdict = null;
            } else {
              _tmpAdminVerdict = _cursor.getString(_cursorIndexOfAdminVerdict);
            }
            _item.setAdminVerdict(_tmpAdminVerdict);
            final int _tmpDepositRefundPercentage;
            _tmpDepositRefundPercentage = _cursor.getInt(_cursorIndexOfDepositRefundPercentage);
            _item.setDepositRefundPercentage(_tmpDepositRefundPercentage);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
            final long _tmpResolvedAt;
            _tmpResolvedAt = _cursor.getLong(_cursorIndexOfResolvedAt);
            _item.setResolvedAt(_tmpResolvedAt);
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
  public LiveData<Integer> getOpenDisputesCountLive() {
    final String _sql = "SELECT COUNT(*) FROM disputes WHERE status IN ('SUBMITTED', 'UNDER_REVIEW')";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"disputes"}, false, new Callable<Integer>() {
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
  public LiveData<Integer> getResolvedDisputesCountLive() {
    final String _sql = "SELECT COUNT(*) FROM disputes WHERE status = 'RESOLVED'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"disputes"}, false, new Callable<Integer>() {
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
  public LiveData<Integer> getTotalDisputesCountLive() {
    final String _sql = "SELECT COUNT(*) FROM disputes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"disputes"}, false, new Callable<Integer>() {
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
