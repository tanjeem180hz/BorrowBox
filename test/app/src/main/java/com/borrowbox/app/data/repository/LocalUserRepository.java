package com.borrowbox.app.data.repository;

import androidx.lifecycle.LiveData;
import com.borrowbox.app.data.local.StudentDao;
import com.borrowbox.app.model.Student;
import com.borrowbox.app.util.AppExecutors;
import com.borrowbox.app.util.SecurityUtils;
import java.util.List;

public class LocalUserRepository implements UserRepository {

    private final StudentDao studentDao;
    private final AppExecutors executors;

    public LocalUserRepository(StudentDao studentDao, AppExecutors executors) {
        this.studentDao = studentDao;
        this.executors = executors;
    }

    @Override
    public LiveData<Student> getStudentLive(long id) {
        return studentDao.getStudentByIdLive(id);
    }

    @Override
    public LiveData<List<Student>> getAllStudentsLive() {
        return studentDao.getAllStudentsLive();
    }

    @Override
    public void registerStudent(Student student, OnUserCallback callback) {
        executors.diskIO().execute(() -> {
            Student existingEmail = studentDao.getStudentByEmail(student.getEmail());
            if (existingEmail != null) {
                executors.mainThread().execute(() -> callback.onError("Account already exists with email: " + student.getEmail()));
                return;
            }
            if (!student.getStudentId().isEmpty()) {
                Student existingId = studentDao.getStudentByStudentId(student.getStudentId());
                if (existingId != null) {
                    executors.mainThread().execute(() -> callback.onError("Student ID " + student.getStudentId() + " is already registered."));
                    return;
                }
            }
            long id = studentDao.insert(student);
            student.setId(id);
            executors.mainThread().execute(() -> callback.onSuccess(student));
        });
    }

    @Override
    public void login(String email, String password, OnLoginCallback callback) {
        executors.diskIO().execute(() -> {
            Student student = studentDao.getStudentByEmail(email);
            if (student == null) {
                // Also check if entered student ID instead of email
                student = studentDao.getStudentByStudentId(email);
            }
            if (student == null) {
                // Fallback check against initial seeded list in case initial DB callback was interrupted
                for (Student s : com.borrowbox.app.data.local.SeedData.getInitialStudents()) {
                    if (s.getEmail().equalsIgnoreCase(email) || s.getStudentId().equalsIgnoreCase(email)) {
                        studentDao.insert(s);
                        student = s;
                        break;
                    }
                }
            }
            if (student == null) {
                executors.mainThread().execute(() -> callback.onError("Invalid credentials. Please check your email and password."));
                return;
            }

            // Check if student account is suspended
            if (student.isSuspended()) {
                final String reason = student.getStatusMessage().isEmpty() ? "Violation of campus sharing policy" : student.getStatusMessage();
                executors.mainThread().execute(() -> callback.onError("Account Suspended: " + reason + ". Contact DIU Proctorial Admin."));
                return;
            }

            boolean match = SecurityUtils.verifyPassword(password, student.getSalt(), student.getPasswordHash());
            if (match) {
                final Student authenticated = student;
                executors.mainThread().execute(() -> callback.onSuccess(authenticated));
            } else {
                executors.mainThread().execute(() -> callback.onError("Invalid credentials. Please check your email and password."));
            }
        });
    }

    @Override
    public void updateStudent(Student student) {
        executors.diskIO().execute(() -> studentDao.update(student));
    }

    @Override
    public void suspendStudent(long studentId, boolean suspend, String reason, Runnable onComplete) {
        executors.diskIO().execute(() -> {
            studentDao.updateSuspension(studentId, suspend, reason);
            if (onComplete != null) {
                executors.mainThread().execute(onComplete);
            }
        });
    }

    @Override
    public void updateVerification(long studentId, boolean verified, Runnable onComplete) {
        executors.diskIO().execute(() -> {
            studentDao.updateVerification(studentId, verified);
            if (onComplete != null) {
                executors.mainThread().execute(onComplete);
            }
        });
    }

    @Override
    public void resetTrustScore(long studentId, int newScore, Runnable onComplete) {
        executors.diskIO().execute(() -> {
            studentDao.updateTrustScore(studentId, newScore);
            if (onComplete != null) {
                executors.mainThread().execute(onComplete);
            }
        });
    }
}
