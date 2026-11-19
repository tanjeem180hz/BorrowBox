package com.borrowbox.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile StudentDao _studentDao;

  private volatile ProductDao _productDao;

  private volatile RentalDao _rentalDao;

  private volatile RatingDao _ratingDao;

  private volatile PaymentDao _paymentDao;

  private volatile ChatMessageDao _chatMessageDao;

  private volatile DisputeDao _disputeDao;

  private volatile ProductConditionDao _productConditionDao;

  private volatile NotificationDao _notificationDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `students` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `university` TEXT NOT NULL, `department` TEXT NOT NULL, `studentId` TEXT NOT NULL, `trustScore` INTEGER NOT NULL, `ratingAverage` REAL NOT NULL, `ratingCount` INTEGER NOT NULL, `completedRentals` INTEGER NOT NULL, `verified` INTEGER NOT NULL, `isSuspended` INTEGER NOT NULL, `statusMessage` TEXT NOT NULL, `lateReturnsCount` INTEGER NOT NULL, `disputePenaltiesCount` INTEGER NOT NULL, `name` TEXT NOT NULL, `email` TEXT NOT NULL, `passwordHash` TEXT NOT NULL, `salt` TEXT NOT NULL, `role` TEXT NOT NULL, `phone` TEXT NOT NULL, `phoneVisible` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `products` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` TEXT NOT NULL, `ownerId` INTEGER NOT NULL, `name` TEXT NOT NULL, `category` TEXT NOT NULL, `description` TEXT NOT NULL, `price` INTEGER NOT NULL, `dailyRentalPrice` REAL NOT NULL, `securityDeposit` REAL NOT NULL, `priceUnit` TEXT NOT NULL, `condition` TEXT NOT NULL, `available` INTEGER NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `imagePath` TEXT NOT NULL, `imageUrls` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `rentals` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `ownerId` INTEGER NOT NULL, `borrowerId` INTEGER NOT NULL, `durationValue` INTEGER NOT NULL, `durationUnit` TEXT NOT NULL, `totalCost` INTEGER NOT NULL, `deposit` INTEGER NOT NULL, `lateFee` INTEGER NOT NULL, `status` TEXT NOT NULL, `requestedAt` INTEGER NOT NULL, `confirmedAt` INTEGER NOT NULL, `startAt` INTEGER NOT NULL, `endAt` INTEGER NOT NULL, `returnedAt` INTEGER NOT NULL, `qrToken` TEXT NOT NULL, `beforePhotoPath` TEXT NOT NULL, `afterPhotoPath` TEXT NOT NULL, `urgent` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `ratings` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `rentalId` INTEGER NOT NULL, `fromUserId` INTEGER NOT NULL, `toUserId` INTEGER NOT NULL, `stars` INTEGER NOT NULL, `comment` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `payments` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `rentalId` INTEGER NOT NULL, `userId` INTEGER NOT NULL, `amount` INTEGER NOT NULL, `type` TEXT NOT NULL, `method` TEXT NOT NULL, `status` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `chat_messages` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `senderId` INTEGER NOT NULL, `receiverId` INTEGER NOT NULL, `text` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `isFromMe` INTEGER NOT NULL, `status` TEXT NOT NULL, `isLike` INTEGER NOT NULL, `attachmentType` TEXT NOT NULL, `attachmentUri` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `disputes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `rentalId` INTEGER NOT NULL, `reporterId` INTEGER NOT NULL, `reporterName` TEXT NOT NULL, `reportedStudentId` INTEGER NOT NULL, `reportedStudentName` TEXT NOT NULL, `productId` INTEGER NOT NULL, `productTitle` TEXT NOT NULL, `disputeType` TEXT NOT NULL, `description` TEXT NOT NULL, `evidenceUri` TEXT NOT NULL, `depositAmount` REAL NOT NULL, `status` TEXT NOT NULL, `adminVerdict` TEXT NOT NULL, `depositRefundPercentage` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `resolvedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `product_conditions` (`checkId` TEXT NOT NULL, `rentalId` INTEGER NOT NULL, `prePhotoUrl` TEXT NOT NULL, `postPhotoUrl` TEXT NOT NULL, `preNotes` TEXT NOT NULL, `postNotes` TEXT NOT NULL, `preTimestamp` INTEGER NOT NULL, `postTimestamp` INTEGER NOT NULL, `preGpsLocation` TEXT NOT NULL, `postGpsLocation` TEXT NOT NULL, PRIMARY KEY(`checkId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `notifications` (`notifId` TEXT NOT NULL, `recipientId` INTEGER NOT NULL, `title` TEXT NOT NULL, `body` TEXT NOT NULL, `deepLinkScreen` TEXT NOT NULL, `isRead` INTEGER NOT NULL, `sentAt` INTEGER NOT NULL, PRIMARY KEY(`notifId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '1af4c87d2d29789362b1023304020fec')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `students`");
        db.execSQL("DROP TABLE IF EXISTS `products`");
        db.execSQL("DROP TABLE IF EXISTS `rentals`");
        db.execSQL("DROP TABLE IF EXISTS `ratings`");
        db.execSQL("DROP TABLE IF EXISTS `payments`");
        db.execSQL("DROP TABLE IF EXISTS `chat_messages`");
        db.execSQL("DROP TABLE IF EXISTS `disputes`");
        db.execSQL("DROP TABLE IF EXISTS `product_conditions`");
        db.execSQL("DROP TABLE IF EXISTS `notifications`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsStudents = new HashMap<String, TableInfo.Column>(21);
        _columnsStudents.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("university", new TableInfo.Column("university", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("department", new TableInfo.Column("department", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("studentId", new TableInfo.Column("studentId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("trustScore", new TableInfo.Column("trustScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("ratingAverage", new TableInfo.Column("ratingAverage", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("ratingCount", new TableInfo.Column("ratingCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("completedRentals", new TableInfo.Column("completedRentals", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("verified", new TableInfo.Column("verified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("isSuspended", new TableInfo.Column("isSuspended", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("statusMessage", new TableInfo.Column("statusMessage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("lateReturnsCount", new TableInfo.Column("lateReturnsCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("disputePenaltiesCount", new TableInfo.Column("disputePenaltiesCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("passwordHash", new TableInfo.Column("passwordHash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("salt", new TableInfo.Column("salt", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("role", new TableInfo.Column("role", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("phone", new TableInfo.Column("phone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("phoneVisible", new TableInfo.Column("phoneVisible", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStudents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStudents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStudents = new TableInfo("students", _columnsStudents, _foreignKeysStudents, _indicesStudents);
        final TableInfo _existingStudents = TableInfo.read(db, "students");
        if (!_infoStudents.equals(_existingStudents)) {
          return new RoomOpenHelper.ValidationResult(false, "students(com.borrowbox.app.model.Student).\n"
                  + " Expected:\n" + _infoStudents + "\n"
                  + " Found:\n" + _existingStudents);
        }
        final HashMap<String, TableInfo.Column> _columnsProducts = new HashMap<String, TableInfo.Column>(16);
        _columnsProducts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("productId", new TableInfo.Column("productId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("ownerId", new TableInfo.Column("ownerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("price", new TableInfo.Column("price", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("dailyRentalPrice", new TableInfo.Column("dailyRentalPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("securityDeposit", new TableInfo.Column("securityDeposit", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("priceUnit", new TableInfo.Column("priceUnit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("condition", new TableInfo.Column("condition", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("available", new TableInfo.Column("available", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("imagePath", new TableInfo.Column("imagePath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("imageUrls", new TableInfo.Column("imageUrls", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProducts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProducts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProducts = new TableInfo("products", _columnsProducts, _foreignKeysProducts, _indicesProducts);
        final TableInfo _existingProducts = TableInfo.read(db, "products");
        if (!_infoProducts.equals(_existingProducts)) {
          return new RoomOpenHelper.ValidationResult(false, "products(com.borrowbox.app.model.Product).\n"
                  + " Expected:\n" + _infoProducts + "\n"
                  + " Found:\n" + _existingProducts);
        }
        final HashMap<String, TableInfo.Column> _columnsRentals = new HashMap<String, TableInfo.Column>(19);
        _columnsRentals.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("ownerId", new TableInfo.Column("ownerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("borrowerId", new TableInfo.Column("borrowerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("durationValue", new TableInfo.Column("durationValue", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("durationUnit", new TableInfo.Column("durationUnit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("totalCost", new TableInfo.Column("totalCost", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("deposit", new TableInfo.Column("deposit", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("lateFee", new TableInfo.Column("lateFee", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("requestedAt", new TableInfo.Column("requestedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("confirmedAt", new TableInfo.Column("confirmedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("startAt", new TableInfo.Column("startAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("endAt", new TableInfo.Column("endAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("returnedAt", new TableInfo.Column("returnedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("qrToken", new TableInfo.Column("qrToken", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("beforePhotoPath", new TableInfo.Column("beforePhotoPath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("afterPhotoPath", new TableInfo.Column("afterPhotoPath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRentals.put("urgent", new TableInfo.Column("urgent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRentals = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRentals = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRentals = new TableInfo("rentals", _columnsRentals, _foreignKeysRentals, _indicesRentals);
        final TableInfo _existingRentals = TableInfo.read(db, "rentals");
        if (!_infoRentals.equals(_existingRentals)) {
          return new RoomOpenHelper.ValidationResult(false, "rentals(com.borrowbox.app.model.Rental).\n"
                  + " Expected:\n" + _infoRentals + "\n"
                  + " Found:\n" + _existingRentals);
        }
        final HashMap<String, TableInfo.Column> _columnsRatings = new HashMap<String, TableInfo.Column>(7);
        _columnsRatings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRatings.put("rentalId", new TableInfo.Column("rentalId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRatings.put("fromUserId", new TableInfo.Column("fromUserId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRatings.put("toUserId", new TableInfo.Column("toUserId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRatings.put("stars", new TableInfo.Column("stars", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRatings.put("comment", new TableInfo.Column("comment", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRatings.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRatings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRatings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRatings = new TableInfo("ratings", _columnsRatings, _foreignKeysRatings, _indicesRatings);
        final TableInfo _existingRatings = TableInfo.read(db, "ratings");
        if (!_infoRatings.equals(_existingRatings)) {
          return new RoomOpenHelper.ValidationResult(false, "ratings(com.borrowbox.app.model.Rating).\n"
                  + " Expected:\n" + _infoRatings + "\n"
                  + " Found:\n" + _existingRatings);
        }
        final HashMap<String, TableInfo.Column> _columnsPayments = new HashMap<String, TableInfo.Column>(8);
        _columnsPayments.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("rentalId", new TableInfo.Column("rentalId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("amount", new TableInfo.Column("amount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("method", new TableInfo.Column("method", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPayments = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPayments = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPayments = new TableInfo("payments", _columnsPayments, _foreignKeysPayments, _indicesPayments);
        final TableInfo _existingPayments = TableInfo.read(db, "payments");
        if (!_infoPayments.equals(_existingPayments)) {
          return new RoomOpenHelper.ValidationResult(false, "payments(com.borrowbox.app.model.Payment).\n"
                  + " Expected:\n" + _infoPayments + "\n"
                  + " Found:\n" + _existingPayments);
        }
        final HashMap<String, TableInfo.Column> _columnsChatMessages = new HashMap<String, TableInfo.Column>(11);
        _columnsChatMessages.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("senderId", new TableInfo.Column("senderId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("receiverId", new TableInfo.Column("receiverId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("isFromMe", new TableInfo.Column("isFromMe", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("isLike", new TableInfo.Column("isLike", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("attachmentType", new TableInfo.Column("attachmentType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("attachmentUri", new TableInfo.Column("attachmentUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChatMessages = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesChatMessages = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChatMessages = new TableInfo("chat_messages", _columnsChatMessages, _foreignKeysChatMessages, _indicesChatMessages);
        final TableInfo _existingChatMessages = TableInfo.read(db, "chat_messages");
        if (!_infoChatMessages.equals(_existingChatMessages)) {
          return new RoomOpenHelper.ValidationResult(false, "chat_messages(com.borrowbox.app.model.ChatMessage).\n"
                  + " Expected:\n" + _infoChatMessages + "\n"
                  + " Found:\n" + _existingChatMessages);
        }
        final HashMap<String, TableInfo.Column> _columnsDisputes = new HashMap<String, TableInfo.Column>(17);
        _columnsDisputes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("rentalId", new TableInfo.Column("rentalId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("reporterId", new TableInfo.Column("reporterId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("reporterName", new TableInfo.Column("reporterName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("reportedStudentId", new TableInfo.Column("reportedStudentId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("reportedStudentName", new TableInfo.Column("reportedStudentName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("productTitle", new TableInfo.Column("productTitle", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("disputeType", new TableInfo.Column("disputeType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("evidenceUri", new TableInfo.Column("evidenceUri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("depositAmount", new TableInfo.Column("depositAmount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("adminVerdict", new TableInfo.Column("adminVerdict", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("depositRefundPercentage", new TableInfo.Column("depositRefundPercentage", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDisputes.put("resolvedAt", new TableInfo.Column("resolvedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDisputes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDisputes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDisputes = new TableInfo("disputes", _columnsDisputes, _foreignKeysDisputes, _indicesDisputes);
        final TableInfo _existingDisputes = TableInfo.read(db, "disputes");
        if (!_infoDisputes.equals(_existingDisputes)) {
          return new RoomOpenHelper.ValidationResult(false, "disputes(com.borrowbox.app.model.Dispute).\n"
                  + " Expected:\n" + _infoDisputes + "\n"
                  + " Found:\n" + _existingDisputes);
        }
        final HashMap<String, TableInfo.Column> _columnsProductConditions = new HashMap<String, TableInfo.Column>(10);
        _columnsProductConditions.put("checkId", new TableInfo.Column("checkId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductConditions.put("rentalId", new TableInfo.Column("rentalId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductConditions.put("prePhotoUrl", new TableInfo.Column("prePhotoUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductConditions.put("postPhotoUrl", new TableInfo.Column("postPhotoUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductConditions.put("preNotes", new TableInfo.Column("preNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductConditions.put("postNotes", new TableInfo.Column("postNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductConditions.put("preTimestamp", new TableInfo.Column("preTimestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductConditions.put("postTimestamp", new TableInfo.Column("postTimestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductConditions.put("preGpsLocation", new TableInfo.Column("preGpsLocation", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductConditions.put("postGpsLocation", new TableInfo.Column("postGpsLocation", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProductConditions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProductConditions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProductConditions = new TableInfo("product_conditions", _columnsProductConditions, _foreignKeysProductConditions, _indicesProductConditions);
        final TableInfo _existingProductConditions = TableInfo.read(db, "product_conditions");
        if (!_infoProductConditions.equals(_existingProductConditions)) {
          return new RoomOpenHelper.ValidationResult(false, "product_conditions(com.borrowbox.app.model.ProductCondition).\n"
                  + " Expected:\n" + _infoProductConditions + "\n"
                  + " Found:\n" + _existingProductConditions);
        }
        final HashMap<String, TableInfo.Column> _columnsNotifications = new HashMap<String, TableInfo.Column>(7);
        _columnsNotifications.put("notifId", new TableInfo.Column("notifId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotifications.put("recipientId", new TableInfo.Column("recipientId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotifications.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotifications.put("body", new TableInfo.Column("body", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotifications.put("deepLinkScreen", new TableInfo.Column("deepLinkScreen", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotifications.put("isRead", new TableInfo.Column("isRead", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotifications.put("sentAt", new TableInfo.Column("sentAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotifications = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNotifications = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNotifications = new TableInfo("notifications", _columnsNotifications, _foreignKeysNotifications, _indicesNotifications);
        final TableInfo _existingNotifications = TableInfo.read(db, "notifications");
        if (!_infoNotifications.equals(_existingNotifications)) {
          return new RoomOpenHelper.ValidationResult(false, "notifications(com.borrowbox.app.model.NotificationModel).\n"
                  + " Expected:\n" + _infoNotifications + "\n"
                  + " Found:\n" + _existingNotifications);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "1af4c87d2d29789362b1023304020fec", "c9dc2f607d47b34eddc66e7b7b8e62ed");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "students","products","rentals","ratings","payments","chat_messages","disputes","product_conditions","notifications");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `students`");
      _db.execSQL("DELETE FROM `products`");
      _db.execSQL("DELETE FROM `rentals`");
      _db.execSQL("DELETE FROM `ratings`");
      _db.execSQL("DELETE FROM `payments`");
      _db.execSQL("DELETE FROM `chat_messages`");
      _db.execSQL("DELETE FROM `disputes`");
      _db.execSQL("DELETE FROM `product_conditions`");
      _db.execSQL("DELETE FROM `notifications`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(StudentDao.class, StudentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProductDao.class, ProductDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RentalDao.class, RentalDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RatingDao.class, RatingDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PaymentDao.class, PaymentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ChatMessageDao.class, ChatMessageDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DisputeDao.class, DisputeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProductConditionDao.class, ProductConditionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NotificationDao.class, NotificationDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public StudentDao studentDao() {
    if (_studentDao != null) {
      return _studentDao;
    } else {
      synchronized(this) {
        if(_studentDao == null) {
          _studentDao = new StudentDao_Impl(this);
        }
        return _studentDao;
      }
    }
  }

  @Override
  public ProductDao productDao() {
    if (_productDao != null) {
      return _productDao;
    } else {
      synchronized(this) {
        if(_productDao == null) {
          _productDao = new ProductDao_Impl(this);
        }
        return _productDao;
      }
    }
  }

  @Override
  public RentalDao rentalDao() {
    if (_rentalDao != null) {
      return _rentalDao;
    } else {
      synchronized(this) {
        if(_rentalDao == null) {
          _rentalDao = new RentalDao_Impl(this);
        }
        return _rentalDao;
      }
    }
  }

  @Override
  public RatingDao ratingDao() {
    if (_ratingDao != null) {
      return _ratingDao;
    } else {
      synchronized(this) {
        if(_ratingDao == null) {
          _ratingDao = new RatingDao_Impl(this);
        }
        return _ratingDao;
      }
    }
  }

  @Override
  public PaymentDao paymentDao() {
    if (_paymentDao != null) {
      return _paymentDao;
    } else {
      synchronized(this) {
        if(_paymentDao == null) {
          _paymentDao = new PaymentDao_Impl(this);
        }
        return _paymentDao;
      }
    }
  }

  @Override
  public ChatMessageDao chatMessageDao() {
    if (_chatMessageDao != null) {
      return _chatMessageDao;
    } else {
      synchronized(this) {
        if(_chatMessageDao == null) {
          _chatMessageDao = new ChatMessageDao_Impl(this);
        }
        return _chatMessageDao;
      }
    }
  }

  @Override
  public DisputeDao disputeDao() {
    if (_disputeDao != null) {
      return _disputeDao;
    } else {
      synchronized(this) {
        if(_disputeDao == null) {
          _disputeDao = new DisputeDao_Impl(this);
        }
        return _disputeDao;
      }
    }
  }

  @Override
  public ProductConditionDao productConditionDao() {
    if (_productConditionDao != null) {
      return _productConditionDao;
    } else {
      synchronized(this) {
        if(_productConditionDao == null) {
          _productConditionDao = new ProductConditionDao_Impl(this);
        }
        return _productConditionDao;
      }
    }
  }

  @Override
  public NotificationDao notificationDao() {
    if (_notificationDao != null) {
      return _notificationDao;
    } else {
      synchronized(this) {
        if(_notificationDao == null) {
          _notificationDao = new NotificationDao_Impl(this);
        }
        return _notificationDao;
      }
    }
  }
}
