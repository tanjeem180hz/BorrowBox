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
import com.borrowbox.app.model.Student;
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
public final class StudentDao_Impl implements StudentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Student> __insertionAdapterOfStudent;

  private final EntityDeletionOrUpdateAdapter<Student> __updateAdapterOfStudent;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSuspension;

  private final SharedSQLiteStatement __preparedStmtOfUpdateVerification;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTrustScore;

  public StudentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudent = new EntityInsertionAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `students` (`id`,`university`,`department`,`studentId`,`trustScore`,`ratingAverage`,`ratingCount`,`completedRentals`,`verified`,`isSuspended`,`statusMessage`,`lateReturnsCount`,`disputePenaltiesCount`,`name`,`email`,`passwordHash`,`salt`,`role`,`phone`,`phoneVisible`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Student entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUniversity() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUniversity());
        }
        if (entity.getDepartment() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDepartment());
        }
        if (entity.getStudentId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getStudentId());
        }
        statement.bindLong(5, entity.getTrustScore());
        statement.bindDouble(6, entity.getRatingAverage());
        statement.bindLong(7, entity.getRatingCount());
        statement.bindLong(8, entity.getCompletedRentals());
        final int _tmp = entity.isVerified() ? 1 : 0;
        statement.bindLong(9, _tmp);
        final int _tmp_1 = entity.isSuspended() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        if (entity.getStatusMessage() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getStatusMessage());
        }
        statement.bindLong(12, entity.getLateReturnsCount());
        statement.bindLong(13, entity.getDisputePenaltiesCount());
        if (entity.getName() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getName());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getEmail());
        }
        if (entity.getPasswordHash() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getPasswordHash());
        }
        if (entity.getSalt() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getSalt());
        }
        if (entity.getRole() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getRole());
        }
        if (entity.getPhone() == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, entity.getPhone());
        }
        final int _tmp_2 = entity.isPhoneVisible() ? 1 : 0;
        statement.bindLong(20, _tmp_2);
        statement.bindLong(21, entity.getCreatedAt());
      }
    };
    this.__updateAdapterOfStudent = new EntityDeletionOrUpdateAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `students` SET `id` = ?,`university` = ?,`department` = ?,`studentId` = ?,`trustScore` = ?,`ratingAverage` = ?,`ratingCount` = ?,`completedRentals` = ?,`verified` = ?,`isSuspended` = ?,`statusMessage` = ?,`lateReturnsCount` = ?,`disputePenaltiesCount` = ?,`name` = ?,`email` = ?,`passwordHash` = ?,`salt` = ?,`role` = ?,`phone` = ?,`phoneVisible` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Student entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUniversity() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUniversity());
        }
        if (entity.getDepartment() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDepartment());
        }
        if (entity.getStudentId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getStudentId());
        }
        statement.bindLong(5, entity.getTrustScore());
        statement.bindDouble(6, entity.getRatingAverage());
        statement.bindLong(7, entity.getRatingCount());
        statement.bindLong(8, entity.getCompletedRentals());
        final int _tmp = entity.isVerified() ? 1 : 0;
        statement.bindLong(9, _tmp);
        final int _tmp_1 = entity.isSuspended() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        if (entity.getStatusMessage() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getStatusMessage());
        }
        statement.bindLong(12, entity.getLateReturnsCount());
        statement.bindLong(13, entity.getDisputePenaltiesCount());
        if (entity.getName() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getName());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getEmail());
        }
        if (entity.getPasswordHash() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getPasswordHash());
        }
        if (entity.getSalt() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getSalt());
        }
        if (entity.getRole() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getRole());
        }
        if (entity.getPhone() == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, entity.getPhone());
        }
        final int _tmp_2 = entity.isPhoneVisible() ? 1 : 0;
        statement.bindLong(20, _tmp_2);
        statement.bindLong(21, entity.getCreatedAt());
        statement.bindLong(22, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateSuspension = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE students SET isSuspended = ?, statusMessage = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateVerification = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE students SET verified = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTrustScore = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE students SET trustScore = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Student student) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfStudent.insertAndReturnId(student);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<Student> students) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfStudent.insert(students);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Student student) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfStudent.handle(student);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateSuspension(final long studentId, final boolean suspended,
      final String statusMessage) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSuspension.acquire();
    int _argIndex = 1;
    final int _tmp = suspended ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    if (statusMessage == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, statusMessage);
    }
    _argIndex = 3;
    _stmt.bindLong(_argIndex, studentId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateSuspension.release(_stmt);
    }
  }

  @Override
  public void updateVerification(final long studentId, final boolean verified) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateVerification.acquire();
    int _argIndex = 1;
    final int _tmp = verified ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, studentId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateVerification.release(_stmt);
    }
  }

  @Override
  public void updateTrustScore(final long studentId, final int trustScore) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTrustScore.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, trustScore);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, studentId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateTrustScore.release(_stmt);
    }
  }

  @Override
  public LiveData<Student> getStudentByIdLive(final long id) {
    final String _sql = "SELECT * FROM students WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"students"}, false, new Callable<Student>() {
      @Override
      @Nullable
      public Student call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUniversity = CursorUtil.getColumnIndexOrThrow(_cursor, "university");
          final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfTrustScore = CursorUtil.getColumnIndexOrThrow(_cursor, "trustScore");
          final int _cursorIndexOfRatingAverage = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingAverage");
          final int _cursorIndexOfRatingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingCount");
          final int _cursorIndexOfCompletedRentals = CursorUtil.getColumnIndexOrThrow(_cursor, "completedRentals");
          final int _cursorIndexOfVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "verified");
          final int _cursorIndexOfIsSuspended = CursorUtil.getColumnIndexOrThrow(_cursor, "isSuspended");
          final int _cursorIndexOfStatusMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "statusMessage");
          final int _cursorIndexOfLateReturnsCount = CursorUtil.getColumnIndexOrThrow(_cursor, "lateReturnsCount");
          final int _cursorIndexOfDisputePenaltiesCount = CursorUtil.getColumnIndexOrThrow(_cursor, "disputePenaltiesCount");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
          final int _cursorIndexOfSalt = CursorUtil.getColumnIndexOrThrow(_cursor, "salt");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfPhoneVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneVisible");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Student _result;
          if (_cursor.moveToFirst()) {
            _result = new Student();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpUniversity;
            if (_cursor.isNull(_cursorIndexOfUniversity)) {
              _tmpUniversity = null;
            } else {
              _tmpUniversity = _cursor.getString(_cursorIndexOfUniversity);
            }
            _result.setUniversity(_tmpUniversity);
            final String _tmpDepartment;
            if (_cursor.isNull(_cursorIndexOfDepartment)) {
              _tmpDepartment = null;
            } else {
              _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            }
            _result.setDepartment(_tmpDepartment);
            final String _tmpStudentId;
            if (_cursor.isNull(_cursorIndexOfStudentId)) {
              _tmpStudentId = null;
            } else {
              _tmpStudentId = _cursor.getString(_cursorIndexOfStudentId);
            }
            _result.setStudentId(_tmpStudentId);
            final int _tmpTrustScore;
            _tmpTrustScore = _cursor.getInt(_cursorIndexOfTrustScore);
            _result.setTrustScore(_tmpTrustScore);
            final double _tmpRatingAverage;
            _tmpRatingAverage = _cursor.getDouble(_cursorIndexOfRatingAverage);
            _result.setRatingAverage(_tmpRatingAverage);
            final int _tmpRatingCount;
            _tmpRatingCount = _cursor.getInt(_cursorIndexOfRatingCount);
            _result.setRatingCount(_tmpRatingCount);
            final int _tmpCompletedRentals;
            _tmpCompletedRentals = _cursor.getInt(_cursorIndexOfCompletedRentals);
            _result.setCompletedRentals(_tmpCompletedRentals);
            final boolean _tmpVerified;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfVerified);
            _tmpVerified = _tmp != 0;
            _result.setVerified(_tmpVerified);
            final boolean _tmpIsSuspended;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSuspended);
            _tmpIsSuspended = _tmp_1 != 0;
            _result.setSuspended(_tmpIsSuspended);
            final String _tmpStatusMessage;
            if (_cursor.isNull(_cursorIndexOfStatusMessage)) {
              _tmpStatusMessage = null;
            } else {
              _tmpStatusMessage = _cursor.getString(_cursorIndexOfStatusMessage);
            }
            _result.setStatusMessage(_tmpStatusMessage);
            final int _tmpLateReturnsCount;
            _tmpLateReturnsCount = _cursor.getInt(_cursorIndexOfLateReturnsCount);
            _result.setLateReturnsCount(_tmpLateReturnsCount);
            final int _tmpDisputePenaltiesCount;
            _tmpDisputePenaltiesCount = _cursor.getInt(_cursorIndexOfDisputePenaltiesCount);
            _result.setDisputePenaltiesCount(_tmpDisputePenaltiesCount);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _result.setName(_tmpName);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            _result.setEmail(_tmpEmail);
            final String _tmpPasswordHash;
            if (_cursor.isNull(_cursorIndexOfPasswordHash)) {
              _tmpPasswordHash = null;
            } else {
              _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
            }
            _result.setPasswordHash(_tmpPasswordHash);
            final String _tmpSalt;
            if (_cursor.isNull(_cursorIndexOfSalt)) {
              _tmpSalt = null;
            } else {
              _tmpSalt = _cursor.getString(_cursorIndexOfSalt);
            }
            _result.setSalt(_tmpSalt);
            final String _tmpRole;
            if (_cursor.isNull(_cursorIndexOfRole)) {
              _tmpRole = null;
            } else {
              _tmpRole = _cursor.getString(_cursorIndexOfRole);
            }
            _result.setRole(_tmpRole);
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            _result.setPhone(_tmpPhone);
            final boolean _tmpPhoneVisible;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfPhoneVisible);
            _tmpPhoneVisible = _tmp_2 != 0;
            _result.setPhoneVisible(_tmpPhoneVisible);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result.setCreatedAt(_tmpCreatedAt);
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
  public Student getStudentById(final long id) {
    final String _sql = "SELECT * FROM students WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUniversity = CursorUtil.getColumnIndexOrThrow(_cursor, "university");
      final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfTrustScore = CursorUtil.getColumnIndexOrThrow(_cursor, "trustScore");
      final int _cursorIndexOfRatingAverage = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingAverage");
      final int _cursorIndexOfRatingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingCount");
      final int _cursorIndexOfCompletedRentals = CursorUtil.getColumnIndexOrThrow(_cursor, "completedRentals");
      final int _cursorIndexOfVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "verified");
      final int _cursorIndexOfIsSuspended = CursorUtil.getColumnIndexOrThrow(_cursor, "isSuspended");
      final int _cursorIndexOfStatusMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "statusMessage");
      final int _cursorIndexOfLateReturnsCount = CursorUtil.getColumnIndexOrThrow(_cursor, "lateReturnsCount");
      final int _cursorIndexOfDisputePenaltiesCount = CursorUtil.getColumnIndexOrThrow(_cursor, "disputePenaltiesCount");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
      final int _cursorIndexOfSalt = CursorUtil.getColumnIndexOrThrow(_cursor, "salt");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfPhoneVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneVisible");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final Student _result;
      if (_cursor.moveToFirst()) {
        _result = new Student();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpUniversity;
        if (_cursor.isNull(_cursorIndexOfUniversity)) {
          _tmpUniversity = null;
        } else {
          _tmpUniversity = _cursor.getString(_cursorIndexOfUniversity);
        }
        _result.setUniversity(_tmpUniversity);
        final String _tmpDepartment;
        if (_cursor.isNull(_cursorIndexOfDepartment)) {
          _tmpDepartment = null;
        } else {
          _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
        }
        _result.setDepartment(_tmpDepartment);
        final String _tmpStudentId;
        if (_cursor.isNull(_cursorIndexOfStudentId)) {
          _tmpStudentId = null;
        } else {
          _tmpStudentId = _cursor.getString(_cursorIndexOfStudentId);
        }
        _result.setStudentId(_tmpStudentId);
        final int _tmpTrustScore;
        _tmpTrustScore = _cursor.getInt(_cursorIndexOfTrustScore);
        _result.setTrustScore(_tmpTrustScore);
        final double _tmpRatingAverage;
        _tmpRatingAverage = _cursor.getDouble(_cursorIndexOfRatingAverage);
        _result.setRatingAverage(_tmpRatingAverage);
        final int _tmpRatingCount;
        _tmpRatingCount = _cursor.getInt(_cursorIndexOfRatingCount);
        _result.setRatingCount(_tmpRatingCount);
        final int _tmpCompletedRentals;
        _tmpCompletedRentals = _cursor.getInt(_cursorIndexOfCompletedRentals);
        _result.setCompletedRentals(_tmpCompletedRentals);
        final boolean _tmpVerified;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfVerified);
        _tmpVerified = _tmp != 0;
        _result.setVerified(_tmpVerified);
        final boolean _tmpIsSuspended;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsSuspended);
        _tmpIsSuspended = _tmp_1 != 0;
        _result.setSuspended(_tmpIsSuspended);
        final String _tmpStatusMessage;
        if (_cursor.isNull(_cursorIndexOfStatusMessage)) {
          _tmpStatusMessage = null;
        } else {
          _tmpStatusMessage = _cursor.getString(_cursorIndexOfStatusMessage);
        }
        _result.setStatusMessage(_tmpStatusMessage);
        final int _tmpLateReturnsCount;
        _tmpLateReturnsCount = _cursor.getInt(_cursorIndexOfLateReturnsCount);
        _result.setLateReturnsCount(_tmpLateReturnsCount);
        final int _tmpDisputePenaltiesCount;
        _tmpDisputePenaltiesCount = _cursor.getInt(_cursorIndexOfDisputePenaltiesCount);
        _result.setDisputePenaltiesCount(_tmpDisputePenaltiesCount);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _result.setName(_tmpName);
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        _result.setEmail(_tmpEmail);
        final String _tmpPasswordHash;
        if (_cursor.isNull(_cursorIndexOfPasswordHash)) {
          _tmpPasswordHash = null;
        } else {
          _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
        }
        _result.setPasswordHash(_tmpPasswordHash);
        final String _tmpSalt;
        if (_cursor.isNull(_cursorIndexOfSalt)) {
          _tmpSalt = null;
        } else {
          _tmpSalt = _cursor.getString(_cursorIndexOfSalt);
        }
        _result.setSalt(_tmpSalt);
        final String _tmpRole;
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _tmpRole = null;
        } else {
          _tmpRole = _cursor.getString(_cursorIndexOfRole);
        }
        _result.setRole(_tmpRole);
        final String _tmpPhone;
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _tmpPhone = null;
        } else {
          _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
        }
        _result.setPhone(_tmpPhone);
        final boolean _tmpPhoneVisible;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfPhoneVisible);
        _tmpPhoneVisible = _tmp_2 != 0;
        _result.setPhoneVisible(_tmpPhoneVisible);
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

  @Override
  public Student getStudentByEmail(final String email) {
    final String _sql = "SELECT * FROM students WHERE email = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUniversity = CursorUtil.getColumnIndexOrThrow(_cursor, "university");
      final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfTrustScore = CursorUtil.getColumnIndexOrThrow(_cursor, "trustScore");
      final int _cursorIndexOfRatingAverage = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingAverage");
      final int _cursorIndexOfRatingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingCount");
      final int _cursorIndexOfCompletedRentals = CursorUtil.getColumnIndexOrThrow(_cursor, "completedRentals");
      final int _cursorIndexOfVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "verified");
      final int _cursorIndexOfIsSuspended = CursorUtil.getColumnIndexOrThrow(_cursor, "isSuspended");
      final int _cursorIndexOfStatusMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "statusMessage");
      final int _cursorIndexOfLateReturnsCount = CursorUtil.getColumnIndexOrThrow(_cursor, "lateReturnsCount");
      final int _cursorIndexOfDisputePenaltiesCount = CursorUtil.getColumnIndexOrThrow(_cursor, "disputePenaltiesCount");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
      final int _cursorIndexOfSalt = CursorUtil.getColumnIndexOrThrow(_cursor, "salt");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfPhoneVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneVisible");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final Student _result;
      if (_cursor.moveToFirst()) {
        _result = new Student();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpUniversity;
        if (_cursor.isNull(_cursorIndexOfUniversity)) {
          _tmpUniversity = null;
        } else {
          _tmpUniversity = _cursor.getString(_cursorIndexOfUniversity);
        }
        _result.setUniversity(_tmpUniversity);
        final String _tmpDepartment;
        if (_cursor.isNull(_cursorIndexOfDepartment)) {
          _tmpDepartment = null;
        } else {
          _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
        }
        _result.setDepartment(_tmpDepartment);
        final String _tmpStudentId;
        if (_cursor.isNull(_cursorIndexOfStudentId)) {
          _tmpStudentId = null;
        } else {
          _tmpStudentId = _cursor.getString(_cursorIndexOfStudentId);
        }
        _result.setStudentId(_tmpStudentId);
        final int _tmpTrustScore;
        _tmpTrustScore = _cursor.getInt(_cursorIndexOfTrustScore);
        _result.setTrustScore(_tmpTrustScore);
        final double _tmpRatingAverage;
        _tmpRatingAverage = _cursor.getDouble(_cursorIndexOfRatingAverage);
        _result.setRatingAverage(_tmpRatingAverage);
        final int _tmpRatingCount;
        _tmpRatingCount = _cursor.getInt(_cursorIndexOfRatingCount);
        _result.setRatingCount(_tmpRatingCount);
        final int _tmpCompletedRentals;
        _tmpCompletedRentals = _cursor.getInt(_cursorIndexOfCompletedRentals);
        _result.setCompletedRentals(_tmpCompletedRentals);
        final boolean _tmpVerified;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfVerified);
        _tmpVerified = _tmp != 0;
        _result.setVerified(_tmpVerified);
        final boolean _tmpIsSuspended;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsSuspended);
        _tmpIsSuspended = _tmp_1 != 0;
        _result.setSuspended(_tmpIsSuspended);
        final String _tmpStatusMessage;
        if (_cursor.isNull(_cursorIndexOfStatusMessage)) {
          _tmpStatusMessage = null;
        } else {
          _tmpStatusMessage = _cursor.getString(_cursorIndexOfStatusMessage);
        }
        _result.setStatusMessage(_tmpStatusMessage);
        final int _tmpLateReturnsCount;
        _tmpLateReturnsCount = _cursor.getInt(_cursorIndexOfLateReturnsCount);
        _result.setLateReturnsCount(_tmpLateReturnsCount);
        final int _tmpDisputePenaltiesCount;
        _tmpDisputePenaltiesCount = _cursor.getInt(_cursorIndexOfDisputePenaltiesCount);
        _result.setDisputePenaltiesCount(_tmpDisputePenaltiesCount);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _result.setName(_tmpName);
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        _result.setEmail(_tmpEmail);
        final String _tmpPasswordHash;
        if (_cursor.isNull(_cursorIndexOfPasswordHash)) {
          _tmpPasswordHash = null;
        } else {
          _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
        }
        _result.setPasswordHash(_tmpPasswordHash);
        final String _tmpSalt;
        if (_cursor.isNull(_cursorIndexOfSalt)) {
          _tmpSalt = null;
        } else {
          _tmpSalt = _cursor.getString(_cursorIndexOfSalt);
        }
        _result.setSalt(_tmpSalt);
        final String _tmpRole;
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _tmpRole = null;
        } else {
          _tmpRole = _cursor.getString(_cursorIndexOfRole);
        }
        _result.setRole(_tmpRole);
        final String _tmpPhone;
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _tmpPhone = null;
        } else {
          _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
        }
        _result.setPhone(_tmpPhone);
        final boolean _tmpPhoneVisible;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfPhoneVisible);
        _tmpPhoneVisible = _tmp_2 != 0;
        _result.setPhoneVisible(_tmpPhoneVisible);
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

  @Override
  public Student getStudentByStudentId(final String studentId) {
    final String _sql = "SELECT * FROM students WHERE studentId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (studentId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, studentId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUniversity = CursorUtil.getColumnIndexOrThrow(_cursor, "university");
      final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfTrustScore = CursorUtil.getColumnIndexOrThrow(_cursor, "trustScore");
      final int _cursorIndexOfRatingAverage = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingAverage");
      final int _cursorIndexOfRatingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingCount");
      final int _cursorIndexOfCompletedRentals = CursorUtil.getColumnIndexOrThrow(_cursor, "completedRentals");
      final int _cursorIndexOfVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "verified");
      final int _cursorIndexOfIsSuspended = CursorUtil.getColumnIndexOrThrow(_cursor, "isSuspended");
      final int _cursorIndexOfStatusMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "statusMessage");
      final int _cursorIndexOfLateReturnsCount = CursorUtil.getColumnIndexOrThrow(_cursor, "lateReturnsCount");
      final int _cursorIndexOfDisputePenaltiesCount = CursorUtil.getColumnIndexOrThrow(_cursor, "disputePenaltiesCount");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
      final int _cursorIndexOfSalt = CursorUtil.getColumnIndexOrThrow(_cursor, "salt");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfPhoneVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneVisible");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final Student _result;
      if (_cursor.moveToFirst()) {
        _result = new Student();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpUniversity;
        if (_cursor.isNull(_cursorIndexOfUniversity)) {
          _tmpUniversity = null;
        } else {
          _tmpUniversity = _cursor.getString(_cursorIndexOfUniversity);
        }
        _result.setUniversity(_tmpUniversity);
        final String _tmpDepartment;
        if (_cursor.isNull(_cursorIndexOfDepartment)) {
          _tmpDepartment = null;
        } else {
          _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
        }
        _result.setDepartment(_tmpDepartment);
        final String _tmpStudentId;
        if (_cursor.isNull(_cursorIndexOfStudentId)) {
          _tmpStudentId = null;
        } else {
          _tmpStudentId = _cursor.getString(_cursorIndexOfStudentId);
        }
        _result.setStudentId(_tmpStudentId);
        final int _tmpTrustScore;
        _tmpTrustScore = _cursor.getInt(_cursorIndexOfTrustScore);
        _result.setTrustScore(_tmpTrustScore);
        final double _tmpRatingAverage;
        _tmpRatingAverage = _cursor.getDouble(_cursorIndexOfRatingAverage);
        _result.setRatingAverage(_tmpRatingAverage);
        final int _tmpRatingCount;
        _tmpRatingCount = _cursor.getInt(_cursorIndexOfRatingCount);
        _result.setRatingCount(_tmpRatingCount);
        final int _tmpCompletedRentals;
        _tmpCompletedRentals = _cursor.getInt(_cursorIndexOfCompletedRentals);
        _result.setCompletedRentals(_tmpCompletedRentals);
        final boolean _tmpVerified;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfVerified);
        _tmpVerified = _tmp != 0;
        _result.setVerified(_tmpVerified);
        final boolean _tmpIsSuspended;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsSuspended);
        _tmpIsSuspended = _tmp_1 != 0;
        _result.setSuspended(_tmpIsSuspended);
        final String _tmpStatusMessage;
        if (_cursor.isNull(_cursorIndexOfStatusMessage)) {
          _tmpStatusMessage = null;
        } else {
          _tmpStatusMessage = _cursor.getString(_cursorIndexOfStatusMessage);
        }
        _result.setStatusMessage(_tmpStatusMessage);
        final int _tmpLateReturnsCount;
        _tmpLateReturnsCount = _cursor.getInt(_cursorIndexOfLateReturnsCount);
        _result.setLateReturnsCount(_tmpLateReturnsCount);
        final int _tmpDisputePenaltiesCount;
        _tmpDisputePenaltiesCount = _cursor.getInt(_cursorIndexOfDisputePenaltiesCount);
        _result.setDisputePenaltiesCount(_tmpDisputePenaltiesCount);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _result.setName(_tmpName);
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        _result.setEmail(_tmpEmail);
        final String _tmpPasswordHash;
        if (_cursor.isNull(_cursorIndexOfPasswordHash)) {
          _tmpPasswordHash = null;
        } else {
          _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
        }
        _result.setPasswordHash(_tmpPasswordHash);
        final String _tmpSalt;
        if (_cursor.isNull(_cursorIndexOfSalt)) {
          _tmpSalt = null;
        } else {
          _tmpSalt = _cursor.getString(_cursorIndexOfSalt);
        }
        _result.setSalt(_tmpSalt);
        final String _tmpRole;
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _tmpRole = null;
        } else {
          _tmpRole = _cursor.getString(_cursorIndexOfRole);
        }
        _result.setRole(_tmpRole);
        final String _tmpPhone;
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _tmpPhone = null;
        } else {
          _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
        }
        _result.setPhone(_tmpPhone);
        final boolean _tmpPhoneVisible;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfPhoneVisible);
        _tmpPhoneVisible = _tmp_2 != 0;
        _result.setPhoneVisible(_tmpPhoneVisible);
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

  @Override
  public LiveData<List<Student>> getAllStudentsLive() {
    final String _sql = "SELECT * FROM students ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"students"}, false, new Callable<List<Student>>() {
      @Override
      @Nullable
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUniversity = CursorUtil.getColumnIndexOrThrow(_cursor, "university");
          final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfTrustScore = CursorUtil.getColumnIndexOrThrow(_cursor, "trustScore");
          final int _cursorIndexOfRatingAverage = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingAverage");
          final int _cursorIndexOfRatingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingCount");
          final int _cursorIndexOfCompletedRentals = CursorUtil.getColumnIndexOrThrow(_cursor, "completedRentals");
          final int _cursorIndexOfVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "verified");
          final int _cursorIndexOfIsSuspended = CursorUtil.getColumnIndexOrThrow(_cursor, "isSuspended");
          final int _cursorIndexOfStatusMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "statusMessage");
          final int _cursorIndexOfLateReturnsCount = CursorUtil.getColumnIndexOrThrow(_cursor, "lateReturnsCount");
          final int _cursorIndexOfDisputePenaltiesCount = CursorUtil.getColumnIndexOrThrow(_cursor, "disputePenaltiesCount");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
          final int _cursorIndexOfSalt = CursorUtil.getColumnIndexOrThrow(_cursor, "salt");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfPhoneVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneVisible");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            _item = new Student();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpUniversity;
            if (_cursor.isNull(_cursorIndexOfUniversity)) {
              _tmpUniversity = null;
            } else {
              _tmpUniversity = _cursor.getString(_cursorIndexOfUniversity);
            }
            _item.setUniversity(_tmpUniversity);
            final String _tmpDepartment;
            if (_cursor.isNull(_cursorIndexOfDepartment)) {
              _tmpDepartment = null;
            } else {
              _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            }
            _item.setDepartment(_tmpDepartment);
            final String _tmpStudentId;
            if (_cursor.isNull(_cursorIndexOfStudentId)) {
              _tmpStudentId = null;
            } else {
              _tmpStudentId = _cursor.getString(_cursorIndexOfStudentId);
            }
            _item.setStudentId(_tmpStudentId);
            final int _tmpTrustScore;
            _tmpTrustScore = _cursor.getInt(_cursorIndexOfTrustScore);
            _item.setTrustScore(_tmpTrustScore);
            final double _tmpRatingAverage;
            _tmpRatingAverage = _cursor.getDouble(_cursorIndexOfRatingAverage);
            _item.setRatingAverage(_tmpRatingAverage);
            final int _tmpRatingCount;
            _tmpRatingCount = _cursor.getInt(_cursorIndexOfRatingCount);
            _item.setRatingCount(_tmpRatingCount);
            final int _tmpCompletedRentals;
            _tmpCompletedRentals = _cursor.getInt(_cursorIndexOfCompletedRentals);
            _item.setCompletedRentals(_tmpCompletedRentals);
            final boolean _tmpVerified;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfVerified);
            _tmpVerified = _tmp != 0;
            _item.setVerified(_tmpVerified);
            final boolean _tmpIsSuspended;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSuspended);
            _tmpIsSuspended = _tmp_1 != 0;
            _item.setSuspended(_tmpIsSuspended);
            final String _tmpStatusMessage;
            if (_cursor.isNull(_cursorIndexOfStatusMessage)) {
              _tmpStatusMessage = null;
            } else {
              _tmpStatusMessage = _cursor.getString(_cursorIndexOfStatusMessage);
            }
            _item.setStatusMessage(_tmpStatusMessage);
            final int _tmpLateReturnsCount;
            _tmpLateReturnsCount = _cursor.getInt(_cursorIndexOfLateReturnsCount);
            _item.setLateReturnsCount(_tmpLateReturnsCount);
            final int _tmpDisputePenaltiesCount;
            _tmpDisputePenaltiesCount = _cursor.getInt(_cursorIndexOfDisputePenaltiesCount);
            _item.setDisputePenaltiesCount(_tmpDisputePenaltiesCount);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            _item.setEmail(_tmpEmail);
            final String _tmpPasswordHash;
            if (_cursor.isNull(_cursorIndexOfPasswordHash)) {
              _tmpPasswordHash = null;
            } else {
              _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
            }
            _item.setPasswordHash(_tmpPasswordHash);
            final String _tmpSalt;
            if (_cursor.isNull(_cursorIndexOfSalt)) {
              _tmpSalt = null;
            } else {
              _tmpSalt = _cursor.getString(_cursorIndexOfSalt);
            }
            _item.setSalt(_tmpSalt);
            final String _tmpRole;
            if (_cursor.isNull(_cursorIndexOfRole)) {
              _tmpRole = null;
            } else {
              _tmpRole = _cursor.getString(_cursorIndexOfRole);
            }
            _item.setRole(_tmpRole);
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            _item.setPhone(_tmpPhone);
            final boolean _tmpPhoneVisible;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfPhoneVisible);
            _tmpPhoneVisible = _tmp_2 != 0;
            _item.setPhoneVisible(_tmpPhoneVisible);
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
  public List<Student> getAllStudents() {
    final String _sql = "SELECT * FROM students ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUniversity = CursorUtil.getColumnIndexOrThrow(_cursor, "university");
      final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfTrustScore = CursorUtil.getColumnIndexOrThrow(_cursor, "trustScore");
      final int _cursorIndexOfRatingAverage = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingAverage");
      final int _cursorIndexOfRatingCount = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingCount");
      final int _cursorIndexOfCompletedRentals = CursorUtil.getColumnIndexOrThrow(_cursor, "completedRentals");
      final int _cursorIndexOfVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "verified");
      final int _cursorIndexOfIsSuspended = CursorUtil.getColumnIndexOrThrow(_cursor, "isSuspended");
      final int _cursorIndexOfStatusMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "statusMessage");
      final int _cursorIndexOfLateReturnsCount = CursorUtil.getColumnIndexOrThrow(_cursor, "lateReturnsCount");
      final int _cursorIndexOfDisputePenaltiesCount = CursorUtil.getColumnIndexOrThrow(_cursor, "disputePenaltiesCount");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
      final int _cursorIndexOfSalt = CursorUtil.getColumnIndexOrThrow(_cursor, "salt");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfPhoneVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneVisible");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Student _item;
        _item = new Student();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpUniversity;
        if (_cursor.isNull(_cursorIndexOfUniversity)) {
          _tmpUniversity = null;
        } else {
          _tmpUniversity = _cursor.getString(_cursorIndexOfUniversity);
        }
        _item.setUniversity(_tmpUniversity);
        final String _tmpDepartment;
        if (_cursor.isNull(_cursorIndexOfDepartment)) {
          _tmpDepartment = null;
        } else {
          _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
        }
        _item.setDepartment(_tmpDepartment);
        final String _tmpStudentId;
        if (_cursor.isNull(_cursorIndexOfStudentId)) {
          _tmpStudentId = null;
        } else {
          _tmpStudentId = _cursor.getString(_cursorIndexOfStudentId);
        }
        _item.setStudentId(_tmpStudentId);
        final int _tmpTrustScore;
        _tmpTrustScore = _cursor.getInt(_cursorIndexOfTrustScore);
        _item.setTrustScore(_tmpTrustScore);
        final double _tmpRatingAverage;
        _tmpRatingAverage = _cursor.getDouble(_cursorIndexOfRatingAverage);
        _item.setRatingAverage(_tmpRatingAverage);
        final int _tmpRatingCount;
        _tmpRatingCount = _cursor.getInt(_cursorIndexOfRatingCount);
        _item.setRatingCount(_tmpRatingCount);
        final int _tmpCompletedRentals;
        _tmpCompletedRentals = _cursor.getInt(_cursorIndexOfCompletedRentals);
        _item.setCompletedRentals(_tmpCompletedRentals);
        final boolean _tmpVerified;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfVerified);
        _tmpVerified = _tmp != 0;
        _item.setVerified(_tmpVerified);
        final boolean _tmpIsSuspended;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsSuspended);
        _tmpIsSuspended = _tmp_1 != 0;
        _item.setSuspended(_tmpIsSuspended);
        final String _tmpStatusMessage;
        if (_cursor.isNull(_cursorIndexOfStatusMessage)) {
          _tmpStatusMessage = null;
        } else {
          _tmpStatusMessage = _cursor.getString(_cursorIndexOfStatusMessage);
        }
        _item.setStatusMessage(_tmpStatusMessage);
        final int _tmpLateReturnsCount;
        _tmpLateReturnsCount = _cursor.getInt(_cursorIndexOfLateReturnsCount);
        _item.setLateReturnsCount(_tmpLateReturnsCount);
        final int _tmpDisputePenaltiesCount;
        _tmpDisputePenaltiesCount = _cursor.getInt(_cursorIndexOfDisputePenaltiesCount);
        _item.setDisputePenaltiesCount(_tmpDisputePenaltiesCount);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item.setName(_tmpName);
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        _item.setEmail(_tmpEmail);
        final String _tmpPasswordHash;
        if (_cursor.isNull(_cursorIndexOfPasswordHash)) {
          _tmpPasswordHash = null;
        } else {
          _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
        }
        _item.setPasswordHash(_tmpPasswordHash);
        final String _tmpSalt;
        if (_cursor.isNull(_cursorIndexOfSalt)) {
          _tmpSalt = null;
        } else {
          _tmpSalt = _cursor.getString(_cursorIndexOfSalt);
        }
        _item.setSalt(_tmpSalt);
        final String _tmpRole;
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _tmpRole = null;
        } else {
          _tmpRole = _cursor.getString(_cursorIndexOfRole);
        }
        _item.setRole(_tmpRole);
        final String _tmpPhone;
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _tmpPhone = null;
        } else {
          _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
        }
        _item.setPhone(_tmpPhone);
        final boolean _tmpPhoneVisible;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfPhoneVisible);
        _tmpPhoneVisible = _tmp_2 != 0;
        _item.setPhoneVisible(_tmpPhoneVisible);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _item.setCreatedAt(_tmpCreatedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> getTotalStudentsCountLive() {
    final String _sql = "SELECT COUNT(*) FROM students";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"students"}, false, new Callable<Integer>() {
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
  public int getTotalStudentsCount() {
    final String _sql = "SELECT COUNT(*) FROM students";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
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

  @Override
  public LiveData<Integer> getSuspendedStudentsCountLive() {
    final String _sql = "SELECT COUNT(*) FROM students WHERE isSuspended = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"students"}, false, new Callable<Integer>() {
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
