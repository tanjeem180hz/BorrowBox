package com.borrowbox.app.ui.product;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.databinding.ActivityEditProductBinding;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.util.ProductImageHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * EditProductActivity - Product Catalog & Media Management Module (Member 02 Assignment).
 * Allows owners to edit their listed product details, price, security deposit, and description.
 */
public class EditProductActivity extends AppCompatActivity {

    private static final String TAG = "EditProductActivity";
    private ActivityEditProductBinding binding;
    private ProductRepository productRepository;
    private Product targetProduct;
    private long productId = -1L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productRepository = RepositoryProvider.getInstance(this).getProductRepository();
        productId = getIntent().getLongExtra("product_id", -1L);

        binding.btnBack.setOnClickListener(v -> finish());

        if (productId != -1L) {
            productRepository.getProductByIdLive(productId).observe(this, product -> {
                if (product != null) {
                    targetProduct = product;
                    populateFields(product);
                }
            });
        } else {
            Toast.makeText(this, "Invalid product ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        binding.btnUpdateProduct.setOnClickListener(v -> updateProductData());
    }

    private void populateFields(Product product) {
        binding.ivProductHero.setImageResource(ProductImageHelper.getDrawableResForProduct(product));
        binding.etName.setText(product.getName());
        binding.etCategory.setText(product.getCategory());
        binding.etDailyPrice.setText(String.valueOf(product.getDailyRentalPrice()));
        binding.etSecurityDeposit.setText(String.valueOf(product.getSecurityDeposit()));
        binding.etCondition.setText(product.getCondition());
        binding.etDescription.setText(product.getDescription());
    }

    private void updateProductData() {
        if (targetProduct == null) return;

        String name = binding.etName.getText() != null ? binding.etName.getText().toString().trim() : "";
        String category = binding.etCategory.getText() != null ? binding.etCategory.getText().toString().trim() : "Electronics";
        String dailyPriceStr = binding.etDailyPrice.getText() != null ? binding.etDailyPrice.getText().toString().trim() : "";
        String depositStr = binding.etSecurityDeposit.getText() != null ? binding.etSecurityDeposit.getText().toString().trim() : "";
        String condition = binding.etCondition.getText() != null ? binding.etCondition.getText().toString().trim() : "Good";
        String desc = binding.etDescription.getText() != null ? binding.etDescription.getText().toString().trim() : "";

        if (name.isEmpty()) {
            binding.tilName.setError("Product name is required");
            binding.etName.requestFocus();
            return;
        }

        double dailyPrice;
        try {
            dailyPrice = Double.parseDouble(dailyPriceStr);
            if (dailyPrice <= 0) {
                binding.tilDailyPrice.setError("Price must be greater than 0");
                binding.etDailyPrice.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            binding.tilDailyPrice.setError("Invalid daily price");
            binding.etDailyPrice.requestFocus();
            return;
        }

        double deposit;
        try {
            deposit = Double.parseDouble(depositStr);
            if (deposit < 0) {
                binding.tilSecurityDeposit.setError("Deposit cannot be negative");
                binding.etSecurityDeposit.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            binding.tilSecurityDeposit.setError("Invalid security deposit");
            binding.etSecurityDeposit.requestFocus();
            return;
        }

        targetProduct.setName(name);
        targetProduct.setCategory(category);
        targetProduct.setDailyRentalPrice(dailyPrice);
        targetProduct.setPrice((int) dailyPrice);
        targetProduct.setSecurityDeposit(deposit);
        targetProduct.setCondition(condition);
        targetProduct.setDescription(desc);

        // 1. Update local Room DB
        productRepository.updateProduct(targetProduct);

        // 2. Update Firebase Firestore document
        try {
            if (targetProduct.getProductId() != null && !targetProduct.getProductId().isEmpty()) {
                FirebaseFirestore.getInstance().collection("products")
                        .document(targetProduct.getProductId())
                        .set(targetProduct)
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "Updated Firestore product"))
                        .addOnFailureListener(e -> Log.w(TAG, "Firestore update failed", e));
            }
        } catch (Exception e) {
            Log.w(TAG, "Firebase unavailable", e);
        }

        Toast.makeText(this, "Product listing updated!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
