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
    private int trustScore = 80;
    private double ratingAverage = 5.0;
    private int ratingCount = 0;
    private int completedRentals = 0;
    private boolean verified = true;
    private boolean isSuspended = false;
    @NonNull
    private String statusMessage = "Active";
    private int lateReturnsCount = 0;
    private int disputePenaltiesCount = 0;

    public Student() {
        super();
        this.role = ROLE_STUDENT;
    }

    @Ignore
    public Student(long id, @NonNull String name, @NonNull String email,
                   @NonNull String passwordHash, @NonNull String salt, long createdAt,
                   @NonNull String university, @NonNull String department,
                   @NonNull String studentId, int trustScore, double ratingAverage,
                   int ratingCount, int completedRentals, boolean verified) {
        this(id, name, email, passwordHash, salt, createdAt, university, department,
                studentId, trustScore, ratingAverage, ratingCount, completedRentals,
                verified, false, "Active", 0, 0, ROLE_STUDENT, "", true);
    }

    @Ignore
    public Student(long id, @NonNull String name, @NonNull String email,
                   @NonNull String passwordHash, @NonNull String salt, long createdAt,
                   @NonNull String university, @NonNull String department,
                   @NonNull String studentId, int trustScore, double ratingAverage,
                   int ratingCount, int completedRentals, boolean verified,
                   boolean isSuspended, @NonNull String statusMessage,
                   int lateReturnsCount, int disputePenaltiesCount,
                   @NonNull String role, @NonNull String phone, boolean phoneVisible) {
        super(id, name, email, passwordHash, salt, role, phone, phoneVisible, createdAt);
        this.id = id;
        this.university = university;
        this.department = department;
        this.studentId = studentId;
        this.trustScore = trustScore;
        this.ratingAverage = ratingAverage;
        this.ratingCount = ratingCount;
        this.completedRentals = completedRentals;
        this.verified = verified;
        this.isSuspended = isSuspended;
        this.statusMessage = statusMessage;
        this.lateReturnsCount = lateReturnsCount;
        this.disputePenaltiesCount = disputePenaltiesCount;
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

    public boolean isSuspended() { return isSuspended; }
    public void setSuspended(boolean suspended) { isSuspended = suspended; }

    @NonNull public String getStatusMessage() { return statusMessage; }
    public void setStatusMessage(@NonNull String statusMessage) { this.statusMessage = statusMessage; }

    public int getLateReturnsCount() { return lateReturnsCount; }
    public void setLateReturnsCount(int lateReturnsCount) { this.lateReturnsCount = lateReturnsCount; }

    public int getDisputePenaltiesCount() { return disputePenaltiesCount; }
    public void setDisputePenaltiesCount(int disputePenaltiesCount) { this.disputePenaltiesCount = disputePenaltiesCount; }
}
