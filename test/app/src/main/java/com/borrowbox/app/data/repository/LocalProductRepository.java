package com.borrowbox.app.data.repository;

import androidx.lifecycle.LiveData;
import com.borrowbox.app.data.local.ProductDao;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.util.AppExecutors;
import java.util.List;

public class LocalProductRepository implements ProductRepository {

    private final ProductDao productDao;
    private final AppExecutors executors;

    public LocalProductRepository(ProductDao productDao, AppExecutors executors) {
        this.productDao = productDao;
        this.executors = executors;
    }

    @Override
    public LiveData<List<Product>> getAllProductsLive() {
        return productDao.getAllProductsLive();
    }

    @Override
    public LiveData<List<Product>> getProductsByCategoryLive(String category) {
        return productDao.getProductsByCategoryLive(category);
    }

    @Override
    public LiveData<List<Product>> searchProductsLive(String query) {
        return productDao.searchProductsLive(query);
    }

    @Override
    public LiveData<Product> getProductByIdLive(long id) {
        return productDao.getProductByIdLive(id);
    }

    @Override
    public LiveData<List<Product>> getProductsByOwnerLive(long ownerId) {
        return productDao.getProductsByOwnerLive(ownerId);
    }

    @Override
    public void insertProduct(Product product) {
        executors.diskIO().execute(() -> productDao.insert(product));
    }

    @Override
    public void updateProduct(Product product) {
        executors.diskIO().execute(() -> productDao.update(product));
    }

    @Override
    public void deleteProduct(Product product) {
        executors.diskIO().execute(() -> productDao.delete(product));
    }
}
