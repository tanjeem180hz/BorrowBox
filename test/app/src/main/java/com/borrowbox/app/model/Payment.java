package com.borrowbox.app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "payments")
public class Payment {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long rentalId;
    private long userId;
    private int amount;
    @NonNull private String type = "RENTAL"; // RENTAL, DEPOSIT, LATE_FEE, REFUND
    @NonNull private String method = "Cash"; // Cash, bKash, Nagad
    @NonNull private String status = "COMPLETED";
    private long createdAt;

    public Payment() {
    }

    @Ignore
    public Payment(long id, long rentalId, long userId, int amount,
                   @NonNull String type, @NonNull String method,
                   @NonNull String status, long createdAt) {
        this.id = id;
        this.rentalId = rentalId;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.method = method;
        this.status = status;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getRentalId() { return rentalId; }
    public void setRentalId(long rentalId) { this.rentalId = rentalId; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    @NonNull public String getType() { return type; }
    public void setType(@NonNull String type) { this.type = type; }

    @NonNull public String getMethod() { return method; }
    public void setMethod(@NonNull String method) { this.method = method; }

    @NonNull public String getStatus() { return status; }
    public void setStatus(@NonNull String status) { this.status = status; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
