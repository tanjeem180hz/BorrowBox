package com.borrowbox.app.ui.profile;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
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
    private final MutableLiveData<Long> currentUserIdLive = new MutableLiveData<>();
    private final LiveData<Student> currentStudentLive;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        RepositoryProvider provider = RepositoryProvider.getInstance(application);
        this.userRepository = provider.getUserRepository();
        this.productRepository = provider.getProductRepository();
        this.sessionManager = new SessionManager(application);

        this.currentUserIdLive.setValue(sessionManager.getLoggedInUserId());
        this.currentStudentLive = Transformations.switchMap(currentUserIdLive, id ->
                userRepository.getStudentLive(id != null ? id : 1L));
    }

    public LiveData<Student> getCurrentStudentLive() {
        return currentStudentLive;
    }

    public LiveData<List<Student>> getAllStudentsLive() {
        return userRepository.getAllStudentsLive();
    }

    public LiveData<List<Product>> getMyProductsLive() {
        return Transformations.switchMap(currentUserIdLive, id ->
                productRepository.getProductsByOwnerLive(id != null ? id : 1L));
    }

    public void switchDemoUser(long targetUserId) {
        sessionManager.setLogin(targetUserId);
        currentUserIdLive.setValue(targetUserId);
    }

    public void logout() {
        sessionManager.logout();
    }
}
