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

    @Update
    void update(Student student);

    @Query("SELECT * FROM students WHERE id = :id")
    LiveData<Student> getStudentByIdLive(long id);

    @Query("SELECT * FROM students WHERE id = :id")
    Student getStudentById(long id);

    @Query("SELECT * FROM students WHERE email = :email LIMIT 1")
    Student getStudentByEmail(String email);

    @Query("SELECT * FROM students")
    LiveData<List<Student>> getAllStudentsLive();

    @Query("SELECT * FROM students")
    List<Student> getAllStudents();
}
