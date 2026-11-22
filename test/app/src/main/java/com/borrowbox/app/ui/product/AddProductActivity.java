package com.borrowbox.app.ui.product;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.databinding.ActivityAddProductBinding;
import com.borrowbox.app.model.PriceUnit;
import com.borrowbox.app.model.Product;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * AddProductActivity - Product Catalog & Media Management Module (Member 02 Assignment).
 * <p>
 * Key Features:
 * 1. Robust Data Validation & Material 3 Form UI.
 * 2. On-device CameraX integration for product photo capture with retake support.
 * 3. Asynchronous Firebase Storage image upload and Firebase Firestore document persistence.
 * 4. Local Room Database synchronization for offline resilience.
 */
public class AddProductActivity extends AppCompatActivity {

    private static final String TAG = "AddProductActivity";

    private ActivityAddProductBinding binding;
    private ImageCapture imageCapture;
    private File photoFile;
    private boolean isPhotoCaptured = false;

    private ProductRepository productRepository;
    private SessionManager sessionManager;

    private final ActivityResultLauncher<String> cameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    startCamera();
                } else {
                    Toast.makeText(this, "Camera permission is required to capture product photos", Toast.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productRepository = RepositoryProvider.getInstance(this).getProductRepository();
        sessionManager = new SessionManager(this);

        setupUI();
        setupTextWatchers();
        checkCameraPermissionAndStart();
    }

    /**
     * Initializes UI click listeners and view bindings.
     */
    private void setupUI() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnCapturePhoto.setOnClickListener(v -> {
            if (isPhotoCaptured) {
                resetCameraPreview();
            } else {
                takePhoto();
            }
        });

        binding.btnSaveProduct.setOnClickListener(v -> processProductSubmission());
    }

    /**
     * Dynamically clears validation errors as the user edits the input text.
     */
    private void setupTextWatchers() {
        TextWatcher clearErrorWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilName.setError(null);
                binding.tilDailyPrice.setError(null);
                binding.tilSecurityDeposit.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        binding.etName.addTextChangedListener(clearErrorWatcher);
        binding.etDailyPrice.addTextChangedListener(clearErrorWatcher);
        binding.etSecurityDeposit.addTextChangedListener(clearErrorWatcher);
    }

    /**
     * Verifies Camera permissions and initializes CameraX.
     */
    private void checkCameraPermissionAndStart() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    /**
     * Binds CameraX Preview and ImageCapture to Activity lifecycle.
     */
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(binding.viewFinder.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Error initializing CameraX provider", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    /**
     * Captures photo via CameraX and stores in cache directory.
     */
    private void takePhoto() {
        if (imageCapture == null) {
            Toast.makeText(this, "Camera is not ready yet", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            photoFile = File.createTempFile("product_", ".jpg", getCacheDir());
        } catch (IOException e) {
            Log.e(TAG, "Error creating photo file", e);
            Toast.makeText(this, "Failed to create image file", Toast.LENGTH_SHORT).show();
            return;
        }

        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults results) {
                isPhotoCaptured = true;
                binding.ivCapturedPreview.setImageURI(Uri.fromFile(photoFile));
                binding.ivCapturedPreview.setVisibility(View.VISIBLE);
                binding.viewFinder.setVisibility(View.GONE);
                binding.btnCapturePhoto.setText("Retake Photo");
                Toast.makeText(AddProductActivity.this, "Photo captured successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e(TAG, "CameraX capture error: " + exception.getMessage(), exception);
                Toast.makeText(AddProductActivity.this, "Photo capture failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Resets preview state to allow retaking the photo.
     */
    private void resetCameraPreview() {
        isPhotoCaptured = false;
        photoFile = null;
        binding.ivCapturedPreview.setVisibility(View.GONE);
        binding.viewFinder.setVisibility(View.VISIBLE);
        binding.btnCapturePhoto.setText("Capture Photo (CameraX)");
    }

    /**
     * Validates user inputs before starting background upload.
     */
    private void processProductSubmission() {
        String name = binding.etName.getText() != null ? binding.etName.getText().toString().trim() : "";
        String category = binding.etCategory.getText() != null ? binding.etCategory.getText().toString().trim() : "Electronics";
        String dailyPriceStr = binding.etDailyPrice.getText() != null ? binding.etDailyPrice.getText().toString().trim() : "";
        String depositStr = binding.etSecurityDeposit.getText() != null ? binding.etSecurityDeposit.getText().toString().trim() : "";
        String condition = binding.etCondition.getText() != null ? binding.etCondition.getText().toString().trim() : "Good";
        String desc = binding.etDescription.getText() != null ? binding.etDescription.getText().toString().trim() : "";

        // Form Validation
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

        long ownerId = sessionManager.getLoggedInUserId();
        String productId = "PROD_" + UUID.randomUUID().toString().substring(0, 8);

        setLoadingState(true);

        if (photoFile != null && photoFile.exists()) {
            uploadPhotoToFirebase(productId, ownerId, name, category, desc, dailyPrice, deposit, condition);
        } else {
            saveProductToDatabases(productId, ownerId, name, category, desc, dailyPrice, deposit, condition, Collections.emptyList());
        }
    }

    /**
     * Uploads captured product image to Firebase Storage and retrieves public download URL.
     */
    private void uploadPhotoToFirebase(String productId, long ownerId, String name, String category,
                                       String desc, double dailyPrice, double deposit, String condition) {
        try {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                    .child("product_images/" + productId + ".jpg");

            Uri fileUri = Uri.fromFile(photoFile);
            storageRef.putFile(fileUri)
                    .continueWithTask(task -> {
                        if (!task.isSuccessful() && task.getException() != null) {
                            throw task.getException();
                        }
                        return storageRef.getDownloadUrl();
                    })
                    .addOnSuccessListener(downloadUri -> {
                        List<String> imageUrls = Collections.singletonList(downloadUri.toString());
                        saveProductToDatabases(productId, ownerId, name, category, desc, dailyPrice, deposit, condition, imageUrls);
                    })
                    .addOnFailureListener(e -> {
                        Log.w(TAG, "Firebase Storage upload failed, falling back to local file path", e);
                        List<String> localUrls = Collections.singletonList(photoFile.getAbsolutePath());
                        saveProductToDatabases(productId, ownerId, name, category, desc, dailyPrice, deposit, condition, localUrls);
                    });
        } catch (Exception e) {
            Log.w(TAG, "Firebase not configured, saving locally", e);
            List<String> localUrls = Collections.singletonList(photoFile.getAbsolutePath());
            saveProductToDatabases(productId, ownerId, name, category, desc, dailyPrice, deposit, condition, localUrls);
        }
    }

    /**
     * Saves complete Product object to Room Database and Firebase Firestore.
     */
    private void saveProductToDatabases(String productId, long ownerId, String name, String category,
                                        String desc, double dailyPrice, double deposit, String condition,
                                        List<String> imageUrls) {
        String mainImagePath = !imageUrls.isEmpty() ? imageUrls.get(0) : "";

        Product product = new Product(0, ownerId, name, category, desc, (int) dailyPrice, PriceUnit.HOUR, condition, true, 23.8759, 90.3198, mainImagePath);
        product.setProductId(productId);
        product.setDailyRentalPrice(dailyPrice);
        product.setSecurityDeposit(deposit);
        product.setImageUrls(imageUrls);

        // 1. Local Room DB Persistence
        productRepository.insertProduct(product);

        // 2. Firebase Firestore Cloud Persistence
        try {
            FirebaseFirestore.getInstance().collection("products")
                    .document(productId)
                    .set(product)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Successfully synced to Firestore"))
                    .addOnFailureListener(e -> Log.w(TAG, "Firestore sync failed", e));
        } catch (Exception e) {
            Log.w(TAG, "Firebase Firestore unavailable", e);
        }

        setLoadingState(false);
        Toast.makeText(this, "Product listed successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * Controls loading indicator and disables user interaction during async uploads.
     */
    private void setLoadingState(boolean isLoading) {
        binding.progressUpload.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        binding.btnSaveProduct.setEnabled(!isLoading);
        binding.btnCapturePhoto.setEnabled(!isLoading);
    }
}
