package com.borrowbox.app.model;

import androidx.annotation.NonNull;

/**
 * Base abstract User class demonstrating OOP inheritance.
 */
public abstract class User {
    protected long id;
    @NonNull protected String name = "";
    @NonNull protected String email = "";
    @NonNull protected String passwordHash = "";
    @NonNull protected String salt = "";
    protected long createdAt;

    public User() {
    }

    public User(long id, @NonNull String name, @NonNull String email,
                @NonNull String passwordHash, @NonNull String salt, long createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.salt = salt;
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

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
