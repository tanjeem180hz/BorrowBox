package com.borrowbox.app.data.repository;

import androidx.lifecycle.LiveData;
import com.borrowbox.app.model.Student;
import java.util.List;

public interface UserRepository {
    LiveData<Student> getStudentLive(long id);
    LiveData<List<Student>> getAllStudentsLive();
    void registerStudent(Student student, OnUserCallback callback);
    void login(String email, String password, OnLoginCallback callback);
    void updateStudent(Student student);
    void suspendStudent(long studentId, boolean suspend, String reason, Runnable onComplete);
    void updateVerification(long studentId, boolean verified, Runnable onComplete);
    void resetTrustScore(long studentId, int newScore, Runnable onComplete);

    interface OnUserCallback {
        void onSuccess(Student student);
        void onError(String message);
    }

    interface OnLoginCallback {
        void onSuccess(Student student);
        void onError(String message);
    }
}
