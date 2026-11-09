package com.borrowbox.app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long ownerId;
    @NonNull private String name = "";
    @NonNull private String category = "Electronics";
    @NonNull private String description = "";
    private int price = 20; // in BDT
    @NonNull private PriceUnit priceUnit = PriceUnit.HOUR;
    @NonNull private String condition = "Good"; // Excellent, Good, Fair
    private boolean available = true;
    private double latitude = 23.8759;
    private double longitude = 90.3198;
    @NonNull private String imagePath = "";

    public Product() {
    }

    @Ignore
    public Product(long id, long ownerId, @NonNull String name, @NonNull String category,
                   @NonNull String description, int price, @NonNull PriceUnit priceUnit,
                   @NonNull String condition, boolean available, double latitude,
                   double longitude, @NonNull String imagePath) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.priceUnit = priceUnit;
        this.condition = condition;
        this.available = available;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imagePath = imagePath;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getOwnerId() { return ownerId; }
    public void setOwnerId(long ownerId) { this.ownerId = ownerId; }

    @NonNull public String getName() { return name; }
    public void setName(@NonNull String name) { this.name = name; }

    @NonNull public String getCategory() { return category; }
    public void setCategory(@NonNull String category) { this.category = category; }

    @NonNull public String getDescription() { return description; }
    public void setDescription(@NonNull String description) { this.description = description; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

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
}
