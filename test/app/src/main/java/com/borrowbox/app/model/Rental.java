package com.borrowbox.app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rentals")
public class Rental {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long productId;
    private long ownerId;
    private long borrowerId;
    private int durationValue = 1;
    @NonNull private PriceUnit durationUnit = PriceUnit.HOUR;
    private int totalCost = 0;
    private int deposit = 200;
    private int lateFee = 0;
    @NonNull private RentalStatus status = RentalStatus.REQUESTED;
    private long requestedAt;
    private long confirmedAt;
    private long startAt;
    private long endAt;
    private long returnedAt;
    @NonNull private String qrToken = "";
    @NonNull private String beforePhotoPath = "";
    @NonNull private String afterPhotoPath = "";
    private boolean urgent = false;

    public Rental() {
    }

    @androidx.room.Ignore
    public Rental(long id, long productId, long borrowerId, long ownerId, int durationValue,
                  @NonNull PriceUnit durationUnit, int totalCost, int deposit,
                  @NonNull RentalStatus status, @NonNull String qrToken, long requestedAt) {
        this.id = id;
        this.productId = productId;
        this.borrowerId = borrowerId;
        this.ownerId = ownerId;
        this.durationValue = durationValue;
        this.durationUnit = durationUnit;
        this.totalCost = totalCost;
        this.deposit = deposit;
        this.status = status;
        this.qrToken = qrToken;
        this.requestedAt = requestedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getProductId() { return productId; }
    public void setProductId(long productId) { this.productId = productId; }

    public long getOwnerId() { return ownerId; }
    public void setOwnerId(long ownerId) { this.ownerId = ownerId; }

    public long getBorrowerId() { return borrowerId; }
    public void setBorrowerId(long borrowerId) { this.borrowerId = borrowerId; }

    public int getDurationValue() { return durationValue; }
    public void setDurationValue(int durationValue) { this.durationValue = durationValue; }

    @NonNull public PriceUnit getDurationUnit() { return durationUnit; }
    public void setDurationUnit(@NonNull PriceUnit durationUnit) { this.durationUnit = durationUnit; }

    public int getTotalCost() { return totalCost; }
    public void setTotalCost(int totalCost) { this.totalCost = totalCost; }

    public int getDeposit() { return deposit; }
    public void setDeposit(int deposit) { this.deposit = deposit; }

    public int getLateFee() { return lateFee; }
    public void setLateFee(int lateFee) { this.lateFee = lateFee; }

    @NonNull public RentalStatus getStatus() { return status; }
    public void setStatus(@NonNull RentalStatus status) { this.status = status; }

    public long getRequestedAt() { return requestedAt; }
    public void setRequestedAt(long requestedAt) { this.requestedAt = requestedAt; }

    public long getConfirmedAt() { return confirmedAt; }
    public void setConfirmedAt(long confirmedAt) { this.confirmedAt = confirmedAt; }

    public long getStartAt() { return startAt; }
    public void setStartAt(long startAt) { this.startAt = startAt; }

    public long getEndAt() { return endAt; }
    public void setEndAt(long endAt) { this.endAt = endAt; }

    public long getReturnedAt() { return returnedAt; }
    public void setReturnedAt(long returnedAt) { this.returnedAt = returnedAt; }

    @NonNull public String getQrToken() { return qrToken; }
    public void setQrToken(@NonNull String qrToken) { this.qrToken = qrToken; }

    @NonNull public String getBeforePhotoPath() { return beforePhotoPath; }
    public void setBeforePhotoPath(@NonNull String beforePhotoPath) { this.beforePhotoPath = beforePhotoPath; }

    @NonNull public String getAfterPhotoPath() { return afterPhotoPath; }
    public void setAfterPhotoPath(@NonNull String afterPhotoPath) { this.afterPhotoPath = afterPhotoPath; }

    public boolean isUrgent() { return urgent; }
    public void setUrgent(boolean urgent) { this.urgent = urgent; }
}
