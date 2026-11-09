package com.borrowbox.app.ui.profile;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.data.repository.UserRepository;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.model.Student;
import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SessionManager sessionManager;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        RepositoryProvider provider = RepositoryProvider.getInstance(application);
        this.userRepository = provider.getUserRepository();
        this.productRepository = provider.getProductRepository();
        this.sessionManager = new SessionManager(application);
    }

    public LiveData<Student> getCurrentStudentLive() {
        return userRepository.getStudentLive(sessionManager.getLoggedInUserId());
    }

    public LiveData<List<Student>> getAllStudentsLive() {
        return userRepository.getAllStudentsLive();
    }

    public LiveData<List<Product>> getMyProductsLive() {
        return productRepository.getProductsByOwnerLive(sessionManager.getLoggedInUserId());
    }

    public void switchDemoUser(long targetUserId) {
        sessionManager.setLogin(targetUserId);
    }

    public void logout() {
        sessionManager.logout();
    }
}
