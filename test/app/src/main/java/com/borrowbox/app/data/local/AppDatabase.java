package com.borrowbox.app.data.local;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.borrowbox.app.model.Payment;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.model.Rating;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.model.Student;
import com.borrowbox.app.util.AppExecutors;

@Database(
        entities = {Student.class, Product.class, Rental.class, Rating.class, Payment.class},
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
                            AppExecutors.getInstance().diskIO().execute(() -> {
                                AppDatabase database = getInstance(context);
                                for (Student s : SeedData.getInitialStudents()) {
                                    database.studentDao().insert(s);
                                }
                                database.productDao().insertAll(SeedData.getInitialProducts());
                                database.rentalDao().insertAll(SeedData.getInitialRentals());
                            });
                        }
                    })
                    .build();
                }
            }
        }
        return instance;
    }
}
