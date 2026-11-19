package com.borrowbox.app.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.borrowbox.app.model.ProductCondition;
import java.util.List;

@Dao
public interface ProductConditionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductCondition condition);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProductCondition> conditions);

    @Update
    void update(ProductCondition condition);

    @Query("SELECT * FROM product_conditions WHERE checkId = :checkId LIMIT 1")
    ProductCondition getConditionById(String checkId);

    @Query("SELECT * FROM product_conditions WHERE rentalId = :rentalId LIMIT 1")
    LiveData<ProductCondition> getConditionForRentalLive(long rentalId);

    @Query("SELECT * FROM product_conditions WHERE rentalId = :rentalId LIMIT 1")
    ProductCondition getConditionForRental(long rentalId);

    @Query("SELECT * FROM product_conditions ORDER BY preTimestamp DESC")
    LiveData<List<ProductCondition>> getAllConditionsLive();
}
