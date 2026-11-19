package com.borrowbox.app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Product Data Model for BorrowBox - Product Catalog & Media Management Module.
 * Member 02 Assignment.
 */
@Entity(tableName = "products")
public class Product {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull private String productId = "";
    private long ownerId;
    @NonNull private String name = "";
    @NonNull private String category = "Electronics";
    @NonNull private String description = "";
    private int price = 20; // in BDT
    private double dailyRentalPrice = 100.0;
    private double securityDeposit = 500.0;
    @NonNull private PriceUnit priceUnit = PriceUnit.HOUR;
    @NonNull private String condition = "Good"; // Excellent, Good, Fair
    private boolean available = true;
    private double latitude = 23.8759;
    private double longitude = 90.3198;
    @NonNull private String imagePath = "";
    @NonNull private List<String> imageUrls = new ArrayList<>();

    public Product() {
    }

    @Ignore
    public Product(long id, long ownerId, @NonNull String name, @NonNull String category,
                   @NonNull String description, int price, @NonNull PriceUnit priceUnit,
                   @NonNull String condition, boolean available, double latitude,
                   double longitude, @NonNull String imagePath) {
        this.id = id;
        this.productId = "PROD_" + id;
        this.ownerId = ownerId;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.dailyRentalPrice = price * 5.0;
        this.securityDeposit = price * 10.0;
        this.priceUnit = priceUnit;
        this.condition = condition;
        this.available = available;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imagePath = imagePath;
        if (!imagePath.isEmpty()) {
            this.imageUrls.add(imagePath);
        }
    }

    @Ignore
    public Product(@NonNull String productId, long ownerId, @NonNull String name,
                   @NonNull String category, @NonNull String description,
                   double dailyRentalPrice, double securityDeposit, @NonNull String condition,
                   boolean available, @NonNull List<String> imageUrls) {
        this.productId = productId;
        this.ownerId = ownerId;
        this.name = name;
        this.category = category;
        this.description = description;
        this.dailyRentalPrice = dailyRentalPrice;
        this.securityDeposit = securityDeposit;
        this.price = (int) dailyRentalPrice;
        this.condition = condition;
        this.available = available;
        this.imageUrls = imageUrls != null ? imageUrls : new ArrayList<>();
        if (!this.imageUrls.isEmpty()) {
            this.imagePath = this.imageUrls.get(0);
        }
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @NonNull public String getProductId() {
        return (productId != null && !productId.isEmpty()) ? productId : "PROD_" + id;
    }
    public void setProductId(@NonNull String productId) { this.productId = productId; }

    public long getOwnerId() { return ownerId; }
    public void setOwnerId(long ownerId) { this.ownerId = ownerId; }

    @NonNull public String getName() { return name; }
    public void setName(@NonNull String name) { this.name = name; }

    // Helper alias method for productName requirement
    @NonNull public String getProductName() { return name; }
    public void setProductName(@NonNull String productName) { this.name = productName; }

    @NonNull public String getCategory() { return category; }
    public void setCategory(@NonNull String category) { this.category = category; }

    @NonNull public String getDescription() { return description; }
    public void setDescription(@NonNull String description) { this.description = description; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public double getDailyRentalPrice() { return dailyRentalPrice; }
    public void setDailyRentalPrice(double dailyRentalPrice) { this.dailyRentalPrice = dailyRentalPrice; }

    public double getSecurityDeposit() { return securityDeposit; }
    public void setSecurityDeposit(double securityDeposit) { this.securityDeposit = securityDeposit; }

    @NonNull public PriceUnit getPriceUnit() { return priceUnit; }
    public void setPriceUnit(@NonNull PriceUnit priceUnit) { this.priceUnit = priceUnit; }

    @NonNull public String getCondition() { return condition; }
    public void setCondition(@NonNull String condition) { this.condition = condition; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    @NonNull public String getImagePath() { return imagePath; }
    public void setImagePath(@NonNull String imagePath) { this.imagePath = imagePath; }

    @NonNull public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(@NonNull List<String> imageUrls) { this.imageUrls = imageUrls; }
}
