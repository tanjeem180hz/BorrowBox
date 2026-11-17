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
import com.borrowbox.app.model.PriceUnit;
import com.borrowbox.app.model.Product;
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
public final class ProductDao_Impl implements ProductDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Product> __insertionAdapterOfProduct;

  private final EntityDeletionOrUpdateAdapter<Product> __deletionAdapterOfProduct;

  private final EntityDeletionOrUpdateAdapter<Product> __updateAdapterOfProduct;

  private final SharedSQLiteStatement __preparedStmtOfUpdateAvailability;

  public ProductDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProduct = new EntityInsertionAdapter<Product>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `products` (`id`,`productId`,`ownerId`,`name`,`category`,`description`,`price`,`dailyRentalPrice`,`securityDeposit`,`priceUnit`,`condition`,`available`,`latitude`,`longitude`,`imagePath`,`imageUrls`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Product entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getProductId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getProductId());
        }
        statement.bindLong(3, entity.getOwnerId());
        if (entity.getName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getName());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCategory());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDescription());
        }
        statement.bindLong(7, entity.getPrice());
        statement.bindDouble(8, entity.getDailyRentalPrice());
        statement.bindDouble(9, entity.getSecurityDeposit());
        final String _tmp = Converters.fromPriceUnit(entity.getPriceUnit());
        if (_tmp == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp);
        }
        if (entity.getCondition() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getCondition());
        }
        final int _tmp_1 = entity.isAvailable() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        statement.bindDouble(13, entity.getLatitude());
        statement.bindDouble(14, entity.getLongitude());
        if (entity.getImagePath() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getImagePath());
        }
        final String _tmp_2 = Converters.fromStringList(entity.getImageUrls());
        if (_tmp_2 == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, _tmp_2);
        }
      }
    };
    this.__deletionAdapterOfProduct = new EntityDeletionOrUpdateAdapter<Product>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `products` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Product entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfProduct = new EntityDeletionOrUpdateAdapter<Product>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `products` SET `id` = ?,`productId` = ?,`ownerId` = ?,`name` = ?,`category` = ?,`description` = ?,`price` = ?,`dailyRentalPrice` = ?,`securityDeposit` = ?,`priceUnit` = ?,`condition` = ?,`available` = ?,`latitude` = ?,`longitude` = ?,`imagePath` = ?,`imageUrls` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Product entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getProductId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getProductId());
        }
        statement.bindLong(3, entity.getOwnerId());
        if (entity.getName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getName());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCategory());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDescription());
        }
        statement.bindLong(7, entity.getPrice());
        statement.bindDouble(8, entity.getDailyRentalPrice());
        statement.bindDouble(9, entity.getSecurityDeposit());
        final String _tmp = Converters.fromPriceUnit(entity.getPriceUnit());
        if (_tmp == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp);
        }
        if (entity.getCondition() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getCondition());
        }
        final int _tmp_1 = entity.isAvailable() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        statement.bindDouble(13, entity.getLatitude());
        statement.bindDouble(14, entity.getLongitude());
        if (entity.getImagePath() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getImagePath());
        }
        final String _tmp_2 = Converters.fromStringList(entity.getImageUrls());
        if (_tmp_2 == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, _tmp_2);
        }
        statement.bindLong(17, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateAvailability = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE products SET available = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Product product) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfProduct.insertAndReturnId(product);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<Product> products) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfProduct.insert(products);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Product product) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfProduct.handle(product);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Product product) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfProduct.handle(product);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateAvailability(final long productId, final boolean available) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateAvailability.acquire();
    int _argIndex = 1;
    final int _tmp = available ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 2;
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
      __preparedStmtOfUpdateAvailability.release(_stmt);
    }
  }

  @Override
  public LiveData<Product> getProductByIdLive(final long id) {
    final String _sql = "SELECT * FROM products WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<Product>() {
      @Override
      @Nullable
      public Product call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfDailyRentalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyRentalPrice");
          final int _cursorIndexOfSecurityDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "securityDeposit");
          final int _cursorIndexOfPriceUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "priceUnit");
          final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
          final int _cursorIndexOfAvailable = CursorUtil.getColumnIndexOrThrow(_cursor, "available");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfImageUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrls");
          final Product _result;
          if (_cursor.moveToFirst()) {
            _result = new Product();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            _result.setProductId(_tmpProductId);
            final long _tmpOwnerId;
            _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
            _result.setOwnerId(_tmpOwnerId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _result.setName(_tmpName);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _result.setCategory(_tmpCategory);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _result.setDescription(_tmpDescription);
            final int _tmpPrice;
            _tmpPrice = _cursor.getInt(_cursorIndexOfPrice);
            _result.setPrice(_tmpPrice);
            final double _tmpDailyRentalPrice;
            _tmpDailyRentalPrice = _cursor.getDouble(_cursorIndexOfDailyRentalPrice);
            _result.setDailyRentalPrice(_tmpDailyRentalPrice);
            final double _tmpSecurityDeposit;
            _tmpSecurityDeposit = _cursor.getDouble(_cursorIndexOfSecurityDeposit);
            _result.setSecurityDeposit(_tmpSecurityDeposit);
            final PriceUnit _tmpPriceUnit;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriceUnit)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriceUnit);
            }
            _tmpPriceUnit = Converters.toPriceUnit(_tmp);
            _result.setPriceUnit(_tmpPriceUnit);
            final String _tmpCondition;
            if (_cursor.isNull(_cursorIndexOfCondition)) {
              _tmpCondition = null;
            } else {
              _tmpCondition = _cursor.getString(_cursorIndexOfCondition);
            }
            _result.setCondition(_tmpCondition);
            final boolean _tmpAvailable;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAvailable);
            _tmpAvailable = _tmp_1 != 0;
            _result.setAvailable(_tmpAvailable);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _result.setLatitude(_tmpLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _result.setLongitude(_tmpLongitude);
            final String _tmpImagePath;
            if (_cursor.isNull(_cursorIndexOfImagePath)) {
              _tmpImagePath = null;
            } else {
              _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            }
            _result.setImagePath(_tmpImagePath);
            final List<String> _tmpImageUrls;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfImageUrls)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfImageUrls);
            }
            _tmpImageUrls = Converters.toStringList(_tmp_2);
            _result.setImageUrls(_tmpImageUrls);
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
  public Product getProductById(final long id) {
    final String _sql = "SELECT * FROM products WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
      final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
      final int _cursorIndexOfDailyRentalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyRentalPrice");
      final int _cursorIndexOfSecurityDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "securityDeposit");
      final int _cursorIndexOfPriceUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "priceUnit");
      final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
      final int _cursorIndexOfAvailable = CursorUtil.getColumnIndexOrThrow(_cursor, "available");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
      final int _cursorIndexOfImageUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrls");
      final Product _result;
      if (_cursor.moveToFirst()) {
        _result = new Product();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpProductId;
        if (_cursor.isNull(_cursorIndexOfProductId)) {
          _tmpProductId = null;
        } else {
          _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
        }
        _result.setProductId(_tmpProductId);
        final long _tmpOwnerId;
        _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
        _result.setOwnerId(_tmpOwnerId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _result.setName(_tmpName);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _result.setCategory(_tmpCategory);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        _result.setDescription(_tmpDescription);
        final int _tmpPrice;
        _tmpPrice = _cursor.getInt(_cursorIndexOfPrice);
        _result.setPrice(_tmpPrice);
        final double _tmpDailyRentalPrice;
        _tmpDailyRentalPrice = _cursor.getDouble(_cursorIndexOfDailyRentalPrice);
        _result.setDailyRentalPrice(_tmpDailyRentalPrice);
        final double _tmpSecurityDeposit;
        _tmpSecurityDeposit = _cursor.getDouble(_cursorIndexOfSecurityDeposit);
        _result.setSecurityDeposit(_tmpSecurityDeposit);
        final PriceUnit _tmpPriceUnit;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfPriceUnit)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfPriceUnit);
        }
        _tmpPriceUnit = Converters.toPriceUnit(_tmp);
        _result.setPriceUnit(_tmpPriceUnit);
        final String _tmpCondition;
        if (_cursor.isNull(_cursorIndexOfCondition)) {
          _tmpCondition = null;
        } else {
          _tmpCondition = _cursor.getString(_cursorIndexOfCondition);
        }
        _result.setCondition(_tmpCondition);
        final boolean _tmpAvailable;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfAvailable);
        _tmpAvailable = _tmp_1 != 0;
        _result.setAvailable(_tmpAvailable);
        final double _tmpLatitude;
        _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
        _result.setLatitude(_tmpLatitude);
        final double _tmpLongitude;
        _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
        _result.setLongitude(_tmpLongitude);
        final String _tmpImagePath;
        if (_cursor.isNull(_cursorIndexOfImagePath)) {
          _tmpImagePath = null;
        } else {
          _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
        }
        _result.setImagePath(_tmpImagePath);
        final List<String> _tmpImageUrls;
        final String _tmp_2;
        if (_cursor.isNull(_cursorIndexOfImageUrls)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getString(_cursorIndexOfImageUrls);
        }
        _tmpImageUrls = Converters.toStringList(_tmp_2);
        _result.setImageUrls(_tmpImageUrls);
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
  public LiveData<List<Product>> getAllProductsLive() {
    final String _sql = "SELECT * FROM products ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<List<Product>>() {
      @Override
      @Nullable
      public List<Product> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfDailyRentalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyRentalPrice");
          final int _cursorIndexOfSecurityDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "securityDeposit");
          final int _cursorIndexOfPriceUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "priceUnit");
          final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
          final int _cursorIndexOfAvailable = CursorUtil.getColumnIndexOrThrow(_cursor, "available");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfImageUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrls");
          final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Product _item;
            _item = new Product();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            _item.setProductId(_tmpProductId);
            final long _tmpOwnerId;
            _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
            _item.setOwnerId(_tmpOwnerId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _item.setCategory(_tmpCategory);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final int _tmpPrice;
            _tmpPrice = _cursor.getInt(_cursorIndexOfPrice);
            _item.setPrice(_tmpPrice);
            final double _tmpDailyRentalPrice;
            _tmpDailyRentalPrice = _cursor.getDouble(_cursorIndexOfDailyRentalPrice);
            _item.setDailyRentalPrice(_tmpDailyRentalPrice);
            final double _tmpSecurityDeposit;
            _tmpSecurityDeposit = _cursor.getDouble(_cursorIndexOfSecurityDeposit);
            _item.setSecurityDeposit(_tmpSecurityDeposit);
            final PriceUnit _tmpPriceUnit;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriceUnit)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriceUnit);
            }
            _tmpPriceUnit = Converters.toPriceUnit(_tmp);
            _item.setPriceUnit(_tmpPriceUnit);
            final String _tmpCondition;
            if (_cursor.isNull(_cursorIndexOfCondition)) {
              _tmpCondition = null;
            } else {
              _tmpCondition = _cursor.getString(_cursorIndexOfCondition);
            }
            _item.setCondition(_tmpCondition);
            final boolean _tmpAvailable;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAvailable);
            _tmpAvailable = _tmp_1 != 0;
            _item.setAvailable(_tmpAvailable);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final String _tmpImagePath;
            if (_cursor.isNull(_cursorIndexOfImagePath)) {
              _tmpImagePath = null;
            } else {
              _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            }
            _item.setImagePath(_tmpImagePath);
            final List<String> _tmpImageUrls;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfImageUrls)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfImageUrls);
            }
            _tmpImageUrls = Converters.toStringList(_tmp_2);
            _item.setImageUrls(_tmpImageUrls);
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
  public List<Product> getAllProducts() {
    final String _sql = "SELECT * FROM products ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
      final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
      final int _cursorIndexOfDailyRentalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyRentalPrice");
      final int _cursorIndexOfSecurityDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "securityDeposit");
      final int _cursorIndexOfPriceUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "priceUnit");
      final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
      final int _cursorIndexOfAvailable = CursorUtil.getColumnIndexOrThrow(_cursor, "available");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
      final int _cursorIndexOfImageUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrls");
      final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Product _item;
        _item = new Product();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpProductId;
        if (_cursor.isNull(_cursorIndexOfProductId)) {
          _tmpProductId = null;
        } else {
          _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
        }
        _item.setProductId(_tmpProductId);
        final long _tmpOwnerId;
        _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
        _item.setOwnerId(_tmpOwnerId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item.setName(_tmpName);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _item.setCategory(_tmpCategory);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        _item.setDescription(_tmpDescription);
        final int _tmpPrice;
        _tmpPrice = _cursor.getInt(_cursorIndexOfPrice);
        _item.setPrice(_tmpPrice);
        final double _tmpDailyRentalPrice;
        _tmpDailyRentalPrice = _cursor.getDouble(_cursorIndexOfDailyRentalPrice);
        _item.setDailyRentalPrice(_tmpDailyRentalPrice);
        final double _tmpSecurityDeposit;
        _tmpSecurityDeposit = _cursor.getDouble(_cursorIndexOfSecurityDeposit);
        _item.setSecurityDeposit(_tmpSecurityDeposit);
        final PriceUnit _tmpPriceUnit;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfPriceUnit)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfPriceUnit);
        }
        _tmpPriceUnit = Converters.toPriceUnit(_tmp);
        _item.setPriceUnit(_tmpPriceUnit);
        final String _tmpCondition;
        if (_cursor.isNull(_cursorIndexOfCondition)) {
          _tmpCondition = null;
        } else {
          _tmpCondition = _cursor.getString(_cursorIndexOfCondition);
        }
        _item.setCondition(_tmpCondition);
        final boolean _tmpAvailable;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfAvailable);
        _tmpAvailable = _tmp_1 != 0;
        _item.setAvailable(_tmpAvailable);
        final double _tmpLatitude;
        _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
        _item.setLatitude(_tmpLatitude);
        final double _tmpLongitude;
        _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
        _item.setLongitude(_tmpLongitude);
        final String _tmpImagePath;
        if (_cursor.isNull(_cursorIndexOfImagePath)) {
          _tmpImagePath = null;
        } else {
          _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
        }
        _item.setImagePath(_tmpImagePath);
        final List<String> _tmpImageUrls;
        final String _tmp_2;
        if (_cursor.isNull(_cursorIndexOfImageUrls)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getString(_cursorIndexOfImageUrls);
        }
        _tmpImageUrls = Converters.toStringList(_tmp_2);
        _item.setImageUrls(_tmpImageUrls);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Product>> getProductsByOwnerLive(final long ownerId) {
    final String _sql = "SELECT * FROM products WHERE ownerId = ? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, ownerId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<List<Product>>() {
      @Override
      @Nullable
      public List<Product> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfDailyRentalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyRentalPrice");
          final int _cursorIndexOfSecurityDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "securityDeposit");
          final int _cursorIndexOfPriceUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "priceUnit");
          final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
          final int _cursorIndexOfAvailable = CursorUtil.getColumnIndexOrThrow(_cursor, "available");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfImageUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrls");
          final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Product _item;
            _item = new Product();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            _item.setProductId(_tmpProductId);
            final long _tmpOwnerId;
            _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
            _item.setOwnerId(_tmpOwnerId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _item.setCategory(_tmpCategory);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final int _tmpPrice;
            _tmpPrice = _cursor.getInt(_cursorIndexOfPrice);
            _item.setPrice(_tmpPrice);
            final double _tmpDailyRentalPrice;
            _tmpDailyRentalPrice = _cursor.getDouble(_cursorIndexOfDailyRentalPrice);
            _item.setDailyRentalPrice(_tmpDailyRentalPrice);
            final double _tmpSecurityDeposit;
            _tmpSecurityDeposit = _cursor.getDouble(_cursorIndexOfSecurityDeposit);
            _item.setSecurityDeposit(_tmpSecurityDeposit);
            final PriceUnit _tmpPriceUnit;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriceUnit)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriceUnit);
            }
            _tmpPriceUnit = Converters.toPriceUnit(_tmp);
            _item.setPriceUnit(_tmpPriceUnit);
            final String _tmpCondition;
            if (_cursor.isNull(_cursorIndexOfCondition)) {
              _tmpCondition = null;
            } else {
              _tmpCondition = _cursor.getString(_cursorIndexOfCondition);
            }
            _item.setCondition(_tmpCondition);
            final boolean _tmpAvailable;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAvailable);
            _tmpAvailable = _tmp_1 != 0;
            _item.setAvailable(_tmpAvailable);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final String _tmpImagePath;
            if (_cursor.isNull(_cursorIndexOfImagePath)) {
              _tmpImagePath = null;
            } else {
              _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            }
            _item.setImagePath(_tmpImagePath);
            final List<String> _tmpImageUrls;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfImageUrls)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfImageUrls);
            }
            _tmpImageUrls = Converters.toStringList(_tmp_2);
            _item.setImageUrls(_tmpImageUrls);
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
  public LiveData<List<Product>> getProductsByCategoryLive(final String category) {
    final String _sql = "SELECT * FROM products WHERE category = ? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (category == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, category);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<List<Product>>() {
      @Override
      @Nullable
      public List<Product> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfDailyRentalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyRentalPrice");
          final int _cursorIndexOfSecurityDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "securityDeposit");
          final int _cursorIndexOfPriceUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "priceUnit");
          final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
          final int _cursorIndexOfAvailable = CursorUtil.getColumnIndexOrThrow(_cursor, "available");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfImageUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrls");
          final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Product _item;
            _item = new Product();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            _item.setProductId(_tmpProductId);
            final long _tmpOwnerId;
            _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
            _item.setOwnerId(_tmpOwnerId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _item.setCategory(_tmpCategory);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final int _tmpPrice;
            _tmpPrice = _cursor.getInt(_cursorIndexOfPrice);
            _item.setPrice(_tmpPrice);
            final double _tmpDailyRentalPrice;
            _tmpDailyRentalPrice = _cursor.getDouble(_cursorIndexOfDailyRentalPrice);
            _item.setDailyRentalPrice(_tmpDailyRentalPrice);
            final double _tmpSecurityDeposit;
            _tmpSecurityDeposit = _cursor.getDouble(_cursorIndexOfSecurityDeposit);
            _item.setSecurityDeposit(_tmpSecurityDeposit);
            final PriceUnit _tmpPriceUnit;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriceUnit)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriceUnit);
            }
            _tmpPriceUnit = Converters.toPriceUnit(_tmp);
            _item.setPriceUnit(_tmpPriceUnit);
            final String _tmpCondition;
            if (_cursor.isNull(_cursorIndexOfCondition)) {
              _tmpCondition = null;
            } else {
              _tmpCondition = _cursor.getString(_cursorIndexOfCondition);
            }
            _item.setCondition(_tmpCondition);
            final boolean _tmpAvailable;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAvailable);
            _tmpAvailable = _tmp_1 != 0;
            _item.setAvailable(_tmpAvailable);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final String _tmpImagePath;
            if (_cursor.isNull(_cursorIndexOfImagePath)) {
              _tmpImagePath = null;
            } else {
              _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            }
            _item.setImagePath(_tmpImagePath);
            final List<String> _tmpImageUrls;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfImageUrls)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfImageUrls);
            }
            _tmpImageUrls = Converters.toStringList(_tmp_2);
            _item.setImageUrls(_tmpImageUrls);
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
  public LiveData<List<Product>> searchProductsLive(final String query) {
    final String _sql = "SELECT * FROM products WHERE name LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%' ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<List<Product>>() {
      @Override
      @Nullable
      public List<Product> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfDailyRentalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyRentalPrice");
          final int _cursorIndexOfSecurityDeposit = CursorUtil.getColumnIndexOrThrow(_cursor, "securityDeposit");
          final int _cursorIndexOfPriceUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "priceUnit");
          final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
          final int _cursorIndexOfAvailable = CursorUtil.getColumnIndexOrThrow(_cursor, "available");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfImageUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrls");
          final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Product _item;
            _item = new Product();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            _item.setProductId(_tmpProductId);
            final long _tmpOwnerId;
            _tmpOwnerId = _cursor.getLong(_cursorIndexOfOwnerId);
            _item.setOwnerId(_tmpOwnerId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _item.setCategory(_tmpCategory);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final int _tmpPrice;
            _tmpPrice = _cursor.getInt(_cursorIndexOfPrice);
            _item.setPrice(_tmpPrice);
            final double _tmpDailyRentalPrice;
            _tmpDailyRentalPrice = _cursor.getDouble(_cursorIndexOfDailyRentalPrice);
            _item.setDailyRentalPrice(_tmpDailyRentalPrice);
            final double _tmpSecurityDeposit;
            _tmpSecurityDeposit = _cursor.getDouble(_cursorIndexOfSecurityDeposit);
            _item.setSecurityDeposit(_tmpSecurityDeposit);
            final PriceUnit _tmpPriceUnit;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriceUnit)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriceUnit);
            }
            _tmpPriceUnit = Converters.toPriceUnit(_tmp);
            _item.setPriceUnit(_tmpPriceUnit);
            final String _tmpCondition;
            if (_cursor.isNull(_cursorIndexOfCondition)) {
              _tmpCondition = null;
            } else {
              _tmpCondition = _cursor.getString(_cursorIndexOfCondition);
            }
            _item.setCondition(_tmpCondition);
            final boolean _tmpAvailable;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAvailable);
            _tmpAvailable = _tmp_1 != 0;
            _item.setAvailable(_tmpAvailable);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final String _tmpImagePath;
            if (_cursor.isNull(_cursorIndexOfImagePath)) {
              _tmpImagePath = null;
            } else {
              _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            }
            _item.setImagePath(_tmpImagePath);
            final List<String> _tmpImageUrls;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfImageUrls)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfImageUrls);
            }
            _tmpImageUrls = Converters.toStringList(_tmp_2);
            _item.setImageUrls(_tmpImageUrls);
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
  public int getProductCount() {
    final String _sql = "SELECT COUNT(*) FROM products";
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
  public LiveData<Integer> getProductCountLive() {
    final String _sql = "SELECT COUNT(*) FROM products";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<Integer>() {
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
  public LiveData<Integer> getAvailableProductCountLive() {
    final String _sql = "SELECT COUNT(*) FROM products WHERE available = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<Integer>() {
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
