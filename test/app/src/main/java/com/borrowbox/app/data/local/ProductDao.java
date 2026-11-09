package com.borrowbox.app.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.borrowbox.app.model.Product;
import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Product product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Product> products);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM products WHERE id = :id")
    LiveData<Product> getProductByIdLive(long id);

    @Query("SELECT * FROM products WHERE id = :id")
    Product getProductById(long id);

    @Query("SELECT * FROM products ORDER BY id DESC")
    LiveData<List<Product>> getAllProductsLive();

    @Query("SELECT * FROM products WHERE ownerId = :ownerId ORDER BY id DESC")
    LiveData<List<Product>> getProductsByOwnerLive(long ownerId);

    @Query("SELECT * FROM products WHERE category = :category ORDER BY id DESC")
    LiveData<List<Product>> getProductsByCategoryLive(String category);

    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY id DESC")
    LiveData<List<Product>> searchProductsLive(String query);

    @Query("SELECT COUNT(*) FROM products")
    int getProductCount();
}
