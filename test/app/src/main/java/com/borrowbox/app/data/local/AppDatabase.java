package com.borrowbox.app.data.local;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.borrowbox.app.model.ChatMessage;
import com.borrowbox.app.model.Dispute;
import com.borrowbox.app.model.NotificationModel;
import com.borrowbox.app.model.Payment;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.model.ProductCondition;
import com.borrowbox.app.model.Rating;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.model.Student;
import com.borrowbox.app.util.AppExecutors;

@Database(
        entities = {
                Student.class,
                Product.class,
                Rental.class,
                Rating.class,
                Payment.class,
                ChatMessage.class,
                Dispute.class,
                ProductCondition.class,
                NotificationModel.class
        },
        version = 1,
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "borrowbox_db";
    private static volatile AppDatabase instance;

    public abstract StudentDao studentDao();
    public abstract ProductDao productDao();
    public abstract RentalDao rentalDao();
    public abstract RatingDao ratingDao();
    public abstract PaymentDao paymentDao();
    public abstract ChatMessageDao chatMessageDao();
    public abstract DisputeDao disputeDao();
    public abstract ProductConditionDao productConditionDao();
    public abstract NotificationDao notificationDao();

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            seedDatabase(context);
                        }

                        @Override
                        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                            super.onDestructiveMigration(db);
                            seedDatabase(context);
                        }
                    })
                    .build();
                }
            }
        }
        return instance;
    }

    public static void seedDatabase(final Context context) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            AppDatabase database = getInstance(context);
            for (Student s : SeedData.getInitialStudents()) {
                if (database.studentDao().getStudentByEmail(s.getEmail()) == null) {
                    database.studentDao().insert(s);
                }
            }
            if (database.productDao().getProductCount() == 0) {
                database.productDao().insertAll(SeedData.getInitialProducts());
                database.rentalDao().insertAll(SeedData.getInitialRentals());
                database.chatMessageDao().insertAll(SeedData.getInitialChatMessages());
                database.disputeDao().insertAll(SeedData.getInitialDisputes());
            }
        });
    }

    public static void ensureSeeded(final Context context) {
        seedDatabase(context);
    }
}
