package com.borrowbox.app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student extends User {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String university = "Daffodil International University";
    @NonNull
    private String department = "CSE";
    @NonNull
    private String studentId = "";
    private int trustScore = 75;
    private double ratingAverage = 5.0;
    private int ratingCount = 0;
    private int completedRentals = 0;
    private boolean verified = true;

    public Student() {
        super();
    }

    @Ignore
    public Student(long id, @NonNull String name, @NonNull String email,
                   @NonNull String passwordHash, @NonNull String salt, long createdAt,
                   @NonNull String university, @NonNull String department,
                   @NonNull String studentId, int trustScore, double ratingAverage,
                   int ratingCount, int completedRentals, boolean verified) {
        super(id, name, email, passwordHash, salt, createdAt);
        this.id = id;
        this.university = university;
        this.department = department;
        this.studentId = studentId;
        this.trustScore = trustScore;
        this.ratingAverage = ratingAverage;
        this.ratingCount = ratingCount;
        this.completedRentals = completedRentals;
        this.verified = verified;
    }

    @Override
    public long getId() { return id; }
    @Override
    public void setId(long id) { this.id = id; }

    @NonNull public String getUniversity() { return university; }
    public void setUniversity(@NonNull String university) { this.university = university; }

    @NonNull public String getDepartment() { return department; }
    public void setDepartment(@NonNull String department) { this.department = department; }

    @NonNull public String getStudentId() { return studentId; }
    public void setStudentId(@NonNull String studentId) { this.studentId = studentId; }

    public int getTrustScore() { return trustScore; }
    public void setTrustScore(int trustScore) { this.trustScore = trustScore; }

    public double getRatingAverage() { return ratingAverage; }
    public void setRatingAverage(double ratingAverage) { this.ratingAverage = ratingAverage; }

    public int getRatingCount() { return ratingCount; }
    public void setRatingCount(int ratingCount) { this.ratingCount = ratingCount; }

    public int getCompletedRentals() { return completedRentals; }
    public void setCompletedRentals(int completedRentals) { this.completedRentals = completedRentals; }

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
}
