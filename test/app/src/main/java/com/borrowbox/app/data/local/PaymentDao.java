package com.borrowbox.app.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.borrowbox.app.model.Payment;
import java.util.List;

@Dao
public interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Payment payment);

    @Query("SELECT * FROM payments WHERE userId = :userId ORDER BY id DESC")
    LiveData<List<Payment>> getPaymentsForUserLive(long userId);

    @Query("SELECT * FROM payments WHERE rentalId = :rentalId")
    LiveData<List<Payment>> getPaymentsForRentalLive(long rentalId);
}
