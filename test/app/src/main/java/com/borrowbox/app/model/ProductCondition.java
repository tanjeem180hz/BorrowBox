package com.borrowbox.app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain model and Room Entity representing pre-rental (pickup) and post-rental (return)
 * condition verification for a rented item.
 */
@Entity(tableName = "product_conditions")
public class ProductCondition {

    @PrimaryKey
    @NonNull
    private String checkId = "";

    private long rentalId;
    @NonNull
    private List<String> prePhotoUrl = new ArrayList<>();
    @NonNull
    private List<String> postPhotoUrl = new ArrayList<>();
    @NonNull
    private String preNotes = "";
    @NonNull
    private String postNotes = "";
    private long preTimestamp;
    private long postTimestamp;
    @NonNull
    private String preGpsLocation = "";
    @NonNull
    private String postGpsLocation = "";

    public ProductCondition() {
    }

    @Ignore
    public ProductCondition(@NonNull String checkId, long rentalId,
                            @NonNull List<String> prePhotoUrl, @NonNull List<String> postPhotoUrl,
                            @NonNull String preNotes, @NonNull String postNotes,
                            long preTimestamp, long postTimestamp,
                            @NonNull String preGpsLocation, @NonNull String postGpsLocation) {
        setCheckId(checkId);
        setRentalId(rentalId);
        setPrePhotoUrl(prePhotoUrl);
        setPostPhotoUrl(postPhotoUrl);
        setPreNotes(preNotes);
        setPostNotes(postNotes);
        setPreTimestamp(preTimestamp);
        setPostTimestamp(postTimestamp);
        setPreGpsLocation(preGpsLocation);
        setPostGpsLocation(postGpsLocation);
    }

    @NonNull
    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(@NonNull String checkId) {
        this.checkId = checkId != null ? checkId : "";
    }

    public long getRentalId() {
        return rentalId;
    }

    public void setRentalId(long rentalId) {
        this.rentalId = rentalId;
    }

    @NonNull
    public List<String> getPrePhotoUrl() {
        return prePhotoUrl != null ? prePhotoUrl : new ArrayList<>();
    }

    public void setPrePhotoUrl(List<String> prePhotoUrl) {
        this.prePhotoUrl = prePhotoUrl != null ? new ArrayList<>(prePhotoUrl) : new ArrayList<>();
    }

    @NonNull
    public List<String> getPostPhotoUrl() {
        return postPhotoUrl != null ? postPhotoUrl : new ArrayList<>();
    }

    public void setPostPhotoUrl(List<String> postPhotoUrl) {
        this.postPhotoUrl = postPhotoUrl != null ? new ArrayList<>(postPhotoUrl) : new ArrayList<>();
    }

    @NonNull
    public String getPreNotes() {
        return preNotes;
    }

    public void setPreNotes(String preNotes) {
        this.preNotes = preNotes != null ? preNotes : "";
    }

    @NonNull
    public String getPostNotes() {
        return postNotes;
    }

    public void setPostNotes(String postNotes) {
        this.postNotes = postNotes != null ? postNotes : "";
    }

    public long getPreTimestamp() {
        return preTimestamp;
    }

    public void setPreTimestamp(long preTimestamp) {
        this.preTimestamp = Math.max(0, preTimestamp);
    }

    public long getPostTimestamp() {
        return postTimestamp;
    }

    public void setPostTimestamp(long postTimestamp) {
        this.postTimestamp = Math.max(0, postTimestamp);
    }

    @NonNull
    public String getPreGpsLocation() {
        return preGpsLocation;
    }

    public void setPreGpsLocation(String preGpsLocation) {
        this.preGpsLocation = preGpsLocation != null ? preGpsLocation : "";
    }

    @NonNull
    public String getPostGpsLocation() {
        return postGpsLocation;
    }

    public void setPostGpsLocation(String postGpsLocation) {
        this.postGpsLocation = postGpsLocation != null ? postGpsLocation : "";
    }
}
