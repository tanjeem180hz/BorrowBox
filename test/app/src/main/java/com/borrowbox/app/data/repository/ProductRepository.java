package com.borrowbox.app.data.repository;

import androidx.lifecycle.LiveData;
import com.borrowbox.app.model.Product;
import java.util.List;

public interface ProductRepository {
    LiveData<List<Product>> getAllProductsLive();
    LiveData<List<Product>> getProductsByCategoryLive(String category);
    LiveData<List<Product>> searchProductsLive(String query);
    LiveData<Product> getProductByIdLive(long id);
    LiveData<List<Product>> getProductsByOwnerLive(long ownerId);
    void insertProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);
}
