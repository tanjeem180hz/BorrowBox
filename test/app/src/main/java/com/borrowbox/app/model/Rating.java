package com.borrowbox.app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ratings")
public class Rating {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long rentalId;
    private long fromUserId;
    private long toUserId;
    private int stars = 5;
    @NonNull private String comment = "";
    private long createdAt;

    public Rating() {
    }

    @Ignore
    public Rating(long id, long rentalId, long fromUserId, long toUserId,
                  int stars, @NonNull String comment, long createdAt) {
        this.id = id;
        this.rentalId = rentalId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.stars = stars;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getRentalId() { return rentalId; }
    public void setRentalId(long rentalId) { this.rentalId = rentalId; }

    public long getFromUserId() { return fromUserId; }
    public void setFromUserId(long fromUserId) { this.fromUserId = fromUserId; }

    public long getToUserId() { return toUserId; }
    public void setToUserId(long toUserId) { this.toUserId = toUserId; }

    public int getStars() { return stars; }
    public void setStars(int stars) { this.stars = stars; }

    @NonNull public String getComment() { return comment; }
    public void setComment(@NonNull String comment) { this.comment = comment; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
