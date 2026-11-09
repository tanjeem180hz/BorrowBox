package com.borrowbox.app.ui.auth;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.data.repository.UserRepository;
import com.borrowbox.app.model.Student;
import com.borrowbox.app.util.SecurityUtils;

public class AuthViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> authSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    public AuthViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = RepositoryProvider.getInstance(application).getUserRepository();
        this.sessionManager = new SessionManager(application);
    }

    public LiveData<String> getErrorMessage() { return errorMessage; }
    public LiveData<Boolean> getAuthSuccess() { return authSuccess; }
    public LiveData<Boolean> getLoading() { return loading; }

    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            errorMessage.setValue("Please enter both email and password");
            return;
        }
        loading.setValue(true);
        userRepository.login(email, password, new UserRepository.OnLoginCallback() {
            @Override
            public void onSuccess(Student student) {
                sessionManager.setLogin(student.getId());
                loading.setValue(false);
                authSuccess.setValue(true);
            }

            @Override
            public void onError(String message) {
                loading.setValue(false);
                errorMessage.setValue(message);
            }
        });
    }

    public void register(String name, String email, String password,
                         String university, String department, String studentId) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || studentId.isEmpty()) {
            errorMessage.setValue("Please fill all required fields");
            return;
        }
        if (password.length() < 6) {
            errorMessage.setValue("Password must be at least 6 characters");
            return;
        }
        loading.setValue(true);
        String salt = SecurityUtils.generateSalt();
        String hash = SecurityUtils.hashPassword(password, salt);

        Student student = new Student(0, name, email, hash, salt, System.currentTimeMillis(),
                university.isEmpty() ? "Daffodil International University" : university,
                department.isEmpty() ? "CSE" : department,
                studentId, 75, 5.0, 0, 0, true);

        userRepository.registerStudent(student, new UserRepository.OnUserCallback() {
            @Override
            public void onSuccess(Student created) {
                sessionManager.setLogin(created.getId());
                loading.setValue(false);
                authSuccess.setValue(true);
            }

            @Override
            public void onError(String message) {
                loading.setValue(false);
                errorMessage.setValue(message);
            }
        });
    }
}
