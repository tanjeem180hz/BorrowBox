package com.borrowbox.app.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.borrowbox.app.model.Rating;
import java.util.List;

@Dao
public interface RatingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Rating rating);

    @Query("SELECT * FROM ratings WHERE toUserId = :userId ORDER BY id DESC")
    LiveData<List<Rating>> getRatingsForUserLive(long userId);

    @Query("SELECT * FROM ratings WHERE rentalId = :rentalId LIMIT 1")
    Rating getRatingByRentalId(long rentalId);
}
