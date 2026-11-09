package com.borrowbox.app.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.borrowbox.app.model.Rental;
import java.util.List;

@Dao
public interface RentalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Rental rental);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Rental> rentals);

    @Update
    void update(Rental rental);

    @Query("SELECT * FROM rentals WHERE id = :id")
    LiveData<Rental> getRentalByIdLive(long id);

    @Query("SELECT * FROM rentals WHERE id = :id")
    Rental getRentalById(long id);

    @Query("SELECT * FROM rentals WHERE borrowerId = :userId OR ownerId = :userId ORDER BY id DESC")
    LiveData<List<Rental>> getRentalsForUserLive(long userId);

    @Query("SELECT * FROM rentals WHERE (borrowerId = :userId OR ownerId = :userId) AND status = :status ORDER BY id DESC")
    LiveData<List<Rental>> getRentalsByStatusLive(long userId, String status);

    @Query("SELECT * FROM rentals WHERE ownerId = :ownerId AND status = 'REQUESTED' ORDER BY id DESC")
    LiveData<List<Rental>> getRequestsForOwnerLive(long ownerId);

    @Query("SELECT * FROM rentals WHERE (borrowerId = :userId OR ownerId = :userId) AND status IN ('ACTIVE', 'OVERDUE') ORDER BY id DESC")
    LiveData<List<Rental>> getActiveRentalsLive(long userId);

    @Query("SELECT * FROM rentals WHERE (borrowerId = :userId OR ownerId = :userId) AND status IN ('RETURNED', 'REJECTED', 'CANCELLED') ORDER BY id DESC")
    LiveData<List<Rental>> getHistoryRentalsLive(long userId);
}
