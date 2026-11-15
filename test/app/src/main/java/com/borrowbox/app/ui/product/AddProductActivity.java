package com.borrowbox.app.ui.product;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Activity for Product Catalog & Media Management module (Member 02 Assignment).
 * Implements clean UI form, CameraX direct photo capture, and Firebase Storage integration.
 */
public class AddProductActivity extends AppCompatActivity {

    private static final String TAG = "AddProductActivity";
    private ActivityAddProductBinding binding;
    private ImageCapture imageCapture;
    private File photoFile;
    private String capturedImagePath = "";

    private final ActivityResultLauncher<String> cameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    startCamera();
                } else {
                    Toast.makeText(this, "Camera permission is required to capture product photos", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ProductRepository productRepository = RepositoryProvider.getInstance(this).getProductRepository();
        SessionManager sessionManager = new SessionManager(this);

        checkCameraPermissionAndStart();

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnCapturePhoto.setOnClickListener(v -> takePhoto());

        binding.btnSaveProduct.setOnClickListener(v -> {
            String name = binding.etName.getText() != null ? binding.etName.getText().toString().trim() : "";
            String category = binding.etCategory.getText() != null ? binding.etCategory.getText().toString().trim() : "Electronics";
            String dailyPriceStr = binding.etDailyPrice.getText() != null ? binding.etDailyPrice.getText().toString().trim() : "100";
            String depositStr = binding.etSecurityDeposit.getText() != null ? binding.etSecurityDeposit.getText().toString().trim() : "500";
            String condition = binding.etCondition.getText() != null ? binding.etCondition.getText().toString().trim() : "Good";
            String desc = binding.etDescription.getText() != null ? binding.etDescription.getText().toString().trim() : "";

            if (name.isEmpty()) {
                Snackbar.make(v, "Please enter product name", Snackbar.LENGTH_SHORT).show();
                return;
            }

            double dailyPrice = 100.0;
            double deposit = 500.0;
            try {
                dailyPrice = Double.parseDouble(dailyPriceStr);
                deposit = Double.parseDouble(depositStr);
            } catch (NumberFormatException ignored) {}

            long ownerId = sessionManager.getLoggedInUserId();
            String productId = "PROD_" + UUID.randomUUID().toString().substring(0, 8);

            binding.progressUpload.setVisibility(View.VISIBLE);
            binding.btnSaveProduct.setEnabled(false);

            if (photoFile != null && photoFile.exists()) {
                uploadPhotoAndSaveProduct(productId, ownerId, name, category, desc, dailyPrice, deposit, condition, productRepository, v);
            } else {
                saveProductData(productId, ownerId, name, category, desc, dailyPrice, deposit, condition,
                        capturedImagePath.isEmpty() ? Collections.emptyList() : Collections.singletonList(capturedImagePath),
                        productRepository, v);
            }
        });
    }

    private void checkCameraPermissionAndStart() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

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
                Log.e(TAG, "Error starting CameraX", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto() {
        if (imageCapture == null) return;

        photoFile = new File(getCacheDir(), "product_" + System.currentTimeMillis() + ".jpg");

        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        capturedImagePath = photoFile.getAbsolutePath();
                        Uri savedUri = Uri.fromFile(photoFile);
                        binding.ivCapturedPreview.setImageURI(savedUri);
                        binding.ivCapturedPreview.setVisibility(View.VISIBLE);
                        binding.viewFinder.setVisibility(View.GONE);
                        Toast.makeText(AddProductActivity.this, "Photo captured successfully!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.e(TAG, "Photo capture failed: " + exception.getMessage(), exception);
                        Toast.makeText(AddProductActivity.this, "Failed to capture photo", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadPhotoAndSaveProduct(String productId, long ownerId, String name, String category,
                                           String desc, double dailyPrice, double deposit,
                                           String condition, ProductRepository repository, View view) {
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
                        String downloadUrl = downloadUri.toString();
                        saveProductData(productId, ownerId, name, category, desc, dailyPrice, deposit,
                                condition, Collections.singletonList(downloadUrl), repository, view);
                    })
                    .addOnFailureListener(e -> {
                        Log.w(TAG, "Firebase Storage upload failed, fallback to local path", e);
                        saveProductData(productId, ownerId, name, category, desc, dailyPrice, deposit,
                                condition, Collections.singletonList(photoFile.getAbsolutePath()), repository, view);
                    });
        } catch (Exception e) {
            Log.w(TAG, "Firebase not initialized, using local image path", e);
            saveProductData(productId, ownerId, name, category, desc, dailyPrice, deposit,
                    condition, Collections.singletonList(photoFile.getAbsolutePath()), repository, view);
        }
    }

    private void saveProductData(String productId, long ownerId, String name, String category,
                                 String desc, double dailyPrice, double deposit,
                                 String condition, List<String> imageUrls,
                                 ProductRepository repository, View view) {
        String mainImagePath = !imageUrls.isEmpty() ? imageUrls.get(0) : "";
        Product product = new Product(0, ownerId, name, category, desc, (int) dailyPrice, PriceUnit.HOUR, condition, true, 23.8759, 90.3198, mainImagePath);
        product.setProductId(productId);
        product.setDailyRentalPrice(dailyPrice);
        product.setSecurityDeposit(deposit);
        product.setImageUrls(imageUrls);

        // Save locally in Room
        repository.insertProduct(product);

        // Try saving to Firestore
        try {
            FirebaseFirestore.getInstance().collection("products")
                    .document(productId)
                    .set(product)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Saved to Firestore"))
                    .addOnFailureListener(e -> Log.w(TAG, "Firestore write error", e));
        } catch (Exception e) {
            Log.w(TAG, "Firebase Firestore not configured, saved locally", e);
        }

        binding.progressUpload.setVisibility(View.GONE);
        Toast.makeText(this, "Product listed successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
