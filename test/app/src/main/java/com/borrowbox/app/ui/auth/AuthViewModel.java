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
import com.borrowbox.app.model.User;
import com.borrowbox.app.util.SecurityUtils;

public class AuthViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> authSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> userRole = new MutableLiveData<>(User.ROLE_STUDENT);

    public AuthViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = RepositoryProvider.getInstance(application).getUserRepository();
        this.sessionManager = new SessionManager(application);
    }

    public LiveData<String> getErrorMessage() { return errorMessage; }
    public LiveData<Boolean> getAuthSuccess() { return authSuccess; }
    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<String> getUserRole() { return userRole; }

    public void login(String emailOrId, String password) {
        String input = emailOrId != null ? emailOrId.trim() : "";
        if (input.isEmpty() || password == null || password.isEmpty()) {
            errorMessage.setValue("Please enter both email/ID and password");
            return;
        }
        loading.setValue(true);
        errorMessage.setValue(null);

        userRepository.login(input, password, new UserRepository.OnLoginCallback() {
            @Override
            public void onSuccess(Student student) {
                sessionManager.setLogin(student.getId(), student.getRole());
                userRole.setValue(student.getRole());
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

    public void register(String name, String email, String studentId, String phone,
                         String university, String department, String password, String confirmPassword) {
        String cleanName = name != null ? name.trim() : "";
        String cleanEmail = email != null ? email.trim().toLowerCase() : "";
        String cleanId = studentId != null ? studentId.trim() : "";
        String cleanPhone = phone != null ? phone.trim() : "";
        String cleanUni = university != null && !university.trim().isEmpty() ? university.trim() : "Daffodil International University";
        String cleanDept = department != null && !department.trim().isEmpty() ? department.trim() : "CSE";

        if (cleanName.isEmpty() || cleanEmail.isEmpty() || cleanId.isEmpty() || cleanPhone.isEmpty()) {
            errorMessage.setValue("Please fill all required profile fields");
            return;
        }

        if (!cleanEmail.contains("@") || !cleanEmail.contains(".")) {
            errorMessage.setValue("Please enter a valid email address");
            return;
        }

        if (password == null || password.length() < 6) {
            errorMessage.setValue("Password must be at least 6 characters");
            return;
        }

        if (confirmPassword != null && !password.equals(confirmPassword)) {
            errorMessage.setValue("Passwords do not match");
            return;
        }

        loading.setValue(true);
        errorMessage.setValue(null);

        String salt = SecurityUtils.generateSalt();
        String hash = SecurityUtils.hashPassword(password, salt);

        Student student = new Student(0, cleanName, cleanEmail, hash, salt, System.currentTimeMillis(),
                cleanUni, cleanDept, cleanId, 80, 5.0, 0, 0, true,
                false, "Active", 0, 0,
                User.ROLE_STUDENT, cleanPhone, true);

        userRepository.registerStudent(student, new UserRepository.OnUserCallback() {
            @Override
            public void onSuccess(Student created) {
                sessionManager.setLogin(created.getId(), created.getRole());
                userRole.setValue(created.getRole());
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

    // Overload for backward compatibility
    public void register(String name, String email, String password,
                         String university, String department, String studentId) {
        register(name, email, studentId, "01700-000000", university, department, password, password);
    }
}
