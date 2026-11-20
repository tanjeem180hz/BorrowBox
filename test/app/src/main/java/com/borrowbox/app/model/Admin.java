package com.borrowbox.app.model;

import androidx.annotation.NonNull;

/**
 * Admin entity inheriting User as specified in SRS Section 5 (OOP Architecture).
 * Holds administrative department, authorization permissions, and moderation capabilities.
 */
public class Admin extends User {

    @NonNull
    private String adminDepartment = "DIU CSE Department & Proctorial Body";
    private int permissionLevel = 1; // 1 = Full Platform Admin

    public Admin() {
        super();
        this.role = ROLE_ADMIN;
    }

    public Admin(long id, @NonNull String name, @NonNull String email,
                 @NonNull String passwordHash, @NonNull String salt, long createdAt,
                 @NonNull String adminDepartment) {
        super(id, name, email, passwordHash, salt, ROLE_ADMIN, "", true, createdAt);
        this.adminDepartment = adminDepartment;
        this.permissionLevel = 1;
    }

    @NonNull
    public String getAdminDepartment() {
        return adminDepartment;
    }

    public void setAdminDepartment(@NonNull String adminDepartment) {
        this.adminDepartment = adminDepartment;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public boolean canSuspendStudents() {
        return true;
    }

    public boolean canModerateListings() {
        return true;
    }

    public boolean canResolveDisputes() {
        return true;
    }
}
