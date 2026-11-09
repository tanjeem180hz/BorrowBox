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
            Student existing = studentDao.getStudentByEmail(student.getEmail());
            if (existing != null) {
                executors.mainThread().execute(() -> callback.onError("Account already exists with this email"));
                return;
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
                // Check if this is a seeded demo student before initial DB callback completes
                for (Student s : com.borrowbox.app.data.local.SeedData.getInitialStudents()) {
                    if (s.getEmail().equalsIgnoreCase(email)) {
                        studentDao.insert(s);
                        student = s;
                        break;
                    }
                }
            }
            if (student == null) {
                executors.mainThread().execute(() -> callback.onError("Invalid email or password"));
                return;
            }
            boolean match = SecurityUtils.verifyPassword(password, student.getSalt(), student.getPasswordHash());
            if (match) {
                final Student authenticated = student;
                executors.mainThread().execute(() -> callback.onSuccess(authenticated));
            } else {
                executors.mainThread().execute(() -> callback.onError("Invalid email or password"));
            }
        });
    }

    @Override
    public void updateStudent(Student student) {
        executors.diskIO().execute(() -> studentDao.update(student));
    }
}
