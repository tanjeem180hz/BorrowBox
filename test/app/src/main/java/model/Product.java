package model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.borrowbox.app.model.PriceUnit;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "products")
public class Product {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull private String productId = "";
    private long ownerId;
    @NonNull private String name = "";
    @NonNull private String category = "Electronics";
    @NonNull private String description = "";
    private int price = 20; // Hourly price in BDT
    private double dailyRentalPrice = 100.0;
    private double securityDeposit = 500.0;
    @NonNull private PriceUnit priceUnit = PriceUnit.HOUR;
    @NonNull private String condition = "Good";
    private boolean available = true;
    private double latitude = 23.8759;
    private double longitude = 90.3198;
    @NonNull private String imagePath = "";
    @NonNull private List<String> imageUrls = new ArrayList<>();

    public Product() {}

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

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @NonNull public String getProductId() { return productId; }
    public void setProductId(@NonNull String productId) { this.productId = productId; }

    public long getOwnerId() { return ownerId; }
    public void setOwnerId(long ownerId) { this.ownerId = ownerId; }

    @NonNull public String getName() { return name; }
    public void setName(@NonNull String name) { this.name = name; }

    @NonNull public String getProductName() { return name; }
    public void setProductName(@NonNull String productName) { this.name = productName; }

    @NonNull public String getCategory() { return category; }
    public void setCategory(@NonNull String category) { this.category = category; }

    @NonNull public String getDescription() { return description; }
    public void setDescription(@NonNull String description) { this.description = description; }

    public double getDailyRentalPrice() { return dailyRentalPrice; }
    public void setDailyRentalPrice(double dailyRentalPrice) { this.dailyRentalPrice = dailyRentalPrice; }

    public double getSecurityDeposit() { return securityDeposit; }
    public void setSecurityDeposit(double securityDeposit) { this.securityDeposit = securityDeposit; }

    @NonNull public String getCondition() { return condition; }
    public void setCondition(@NonNull String condition) { this.condition = condition; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @NonNull public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(@NonNull List<String> imageUrls) { this.imageUrls = imageUrls; }
}