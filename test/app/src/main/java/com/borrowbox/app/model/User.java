package com.borrowbox.app.model;

import androidx.annotation.NonNull;

/**
 * Base abstract User class demonstrating OOP inheritance.
 * Aligned with CSE222 SRS Module 1 (FR-01 - FR-08).
 */
public abstract class User {

    public static final String ROLE_STUDENT = "STUDENT";
    public static final String ROLE_ADMIN = "ADMIN";

    protected long id;
    @NonNull protected String name = "";
    @NonNull protected String email = "";
    @NonNull protected String passwordHash = "";
    @NonNull protected String salt = "";
    @NonNull protected String role = ROLE_STUDENT;
    @NonNull protected String phone = "";
    protected boolean phoneVisible = true;
    protected long createdAt;

    public User() {
    }

    public User(long id, @NonNull String name, @NonNull String email,
                @NonNull String passwordHash, @NonNull String salt, long createdAt) {
        this(id, name, email, passwordHash, salt, ROLE_STUDENT, "", true, createdAt);
    }

    public User(long id, @NonNull String name, @NonNull String email,
                @NonNull String passwordHash, @NonNull String salt,
                @NonNull String role, @NonNull String phone, boolean phoneVisible, long createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.role = role;
        this.phone = phone;
        this.phoneVisible = phoneVisible;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @NonNull public String getName() { return name; }
    public void setName(@NonNull String name) { this.name = name; }

    @NonNull public String getEmail() { return email; }
    public void setEmail(@NonNull String email) { this.email = email; }

    @NonNull public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(@NonNull String passwordHash) { this.passwordHash = passwordHash; }

    @NonNull public String getSalt() { return salt; }
    public void setSalt(@NonNull String salt) { this.salt = salt; }

    @NonNull public String getRole() { return role; }
    public void setRole(@NonNull String role) { this.role = role; }

    @NonNull public String getPhone() { return phone; }
    public void setPhone(@NonNull String phone) { this.phone = phone; }

    public boolean isPhoneVisible() { return phoneVisible; }
    public void setPhoneVisible(boolean phoneVisible) { this.phoneVisible = phoneVisible; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public boolean isAdmin() {
        return ROLE_ADMIN.equalsIgnoreCase(role);
    }
}
