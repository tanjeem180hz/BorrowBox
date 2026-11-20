package com.borrowbox.app.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.borrowbox.app.model.Student;
import java.util.List;

@Dao
public interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Student student);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Student> students);

    @Update
    void update(Student student);

    @Query("SELECT * FROM students WHERE id = :id")
    LiveData<Student> getStudentByIdLive(long id);

    @Query("SELECT * FROM students WHERE id = :id")
    Student getStudentById(long id);

    @Query("SELECT * FROM students WHERE email = :email LIMIT 1")
    Student getStudentByEmail(String email);

    @Query("SELECT * FROM students WHERE studentId = :studentId LIMIT 1")
    Student getStudentByStudentId(String studentId);

    @Query("SELECT * FROM students ORDER BY id ASC")
    LiveData<List<Student>> getAllStudentsLive();

    @Query("SELECT * FROM students ORDER BY id ASC")
    List<Student> getAllStudents();

    @Query("SELECT COUNT(*) FROM students")
    LiveData<Integer> getTotalStudentsCountLive();

    @Query("SELECT COUNT(*) FROM students")
    int getTotalStudentsCount();

    @Query("SELECT COUNT(*) FROM students WHERE isSuspended = 1")
    LiveData<Integer> getSuspendedStudentsCountLive();

    @Query("UPDATE students SET isSuspended = :suspended, statusMessage = :statusMessage WHERE id = :studentId")
    void updateSuspension(long studentId, boolean suspended, String statusMessage);

    @Query("UPDATE students SET verified = :verified WHERE id = :studentId")
    void updateVerification(long studentId, boolean verified);

    @Query("UPDATE students SET trustScore = :trustScore WHERE id = :studentId")
    void updateTrustScore(long studentId, int trustScore);
}
