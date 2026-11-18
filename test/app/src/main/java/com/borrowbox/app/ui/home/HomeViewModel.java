package com.borrowbox.app.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.data.repository.UserRepository;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeViewModel extends AndroidViewModel {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    private final MutableLiveData<String> selectedCategory = new MutableLiveData<>("All");
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>("");
    private final MediatorLiveData<List<Product>> filteredProducts = new MediatorLiveData<>();
    private final MutableLiveData<Long> currentUserIdLive = new MutableLiveData<>();
    private final LiveData<Student> currentUserLive;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        RepositoryProvider provider = RepositoryProvider.getInstance(application);
        this.productRepository = provider.getProductRepository();
        this.userRepository = provider.getUserRepository();
        this.sessionManager = new SessionManager(application);

        this.currentUserIdLive.setValue(sessionManager.getLoggedInUserId());
        this.currentUserLive = Transformations.switchMap(currentUserIdLive, id ->
                userRepository.getStudentLive(id != null ? id : 1L));

        LiveData<List<Product>> allProducts = productRepository.getAllProductsLive();

        filteredProducts.addSource(allProducts, products -> applyFilter(products, selectedCategory.getValue(), searchQuery.getValue()));
        filteredProducts.addSource(selectedCategory, cat -> applyFilter(allProducts.getValue(), cat, searchQuery.getValue()));
        filteredProducts.addSource(searchQuery, q -> applyFilter(allProducts.getValue(), selectedCategory.getValue(), q));
    }

    public LiveData<List<Product>> getFilteredProducts() {
        return filteredProducts;
    }

    public LiveData<Student> getCurrentUserLive() {
        return currentUserLive;
    }

    public void refreshUser() {
        currentUserIdLive.setValue(sessionManager.getLoggedInUserId());
    }

    public void setCategory(String category) {
        selectedCategory.setValue(category);
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    private void applyFilter(List<Product> list, String category, String query) {
        if (list == null) {
            filteredProducts.setValue(new ArrayList<>());
            return;
        }

        List<Product> result = new ArrayList<>();
        String normalizedQuery = query != null ? query.trim().toLowerCase(Locale.ROOT) : "";
        boolean isAllCategory = category == null || "All".equalsIgnoreCase(category);

        for (Product product : list) {
            boolean matchesCategory = isAllCategory || product.getCategory().equalsIgnoreCase(category);
            boolean matchesQuery = normalizedQuery.isEmpty()
                    || product.getName().toLowerCase(Locale.ROOT).contains(normalizedQuery)
                    || product.getDescription().toLowerCase(Locale.ROOT).contains(normalizedQuery);

            if (matchesCategory && matchesQuery) {
                result.add(product);
            }
        }
        filteredProducts.setValue(result);
    }
}
