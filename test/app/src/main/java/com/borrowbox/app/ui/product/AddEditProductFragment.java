package com.borrowbox.app.ui.product;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.databinding.FragmentAddEditProductBinding;
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
 * Fragment for Product Catalog & Media Management module.
 * Implements CameraX photo capture and Firebase Storage upload.
 */
public class AddEditProductFragment extends Fragment {

    private static final String TAG = "AddEditProductFragment";
    private FragmentAddEditProductBinding binding;
    private ImageCapture imageCapture;
    private File photoFile;
    private String capturedImagePath = "";

    private final ActivityResultLauncher<String> cameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    startCamera();
                } else {
                    Toast.makeText(requireContext(), "Camera permission is required to capture product photos", Toast.LENGTH_SHORT).show();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddEditProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        com.borrowbox.app.util.EdgeToEdgeHelper.applyTopCutout(binding.getRoot());
        com.borrowbox.app.util.EdgeToEdgeHelper.applyBottomCutout(binding.getRoot());

        ProductRepository productRepository = RepositoryProvider.getInstance(requireContext()).getProductRepository();
        SessionManager sessionManager = new SessionManager(requireContext());

        checkCameraPermissionAndStart();

        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        binding.btnCapturePhoto.setOnClickListener(v -> takePhoto());

        binding.btnSaveProduct.setOnClickListener(v -> {
            String name = binding.etName.getText() != null ? binding.etName.getText().toString().trim() : "";
            String category = binding.etCategory.getText() != null ? binding.etCategory.getText().toString().trim() : "Electronics";
            String priceStr = binding.etPrice.getText() != null ? binding.etPrice.getText().toString().trim() : "20";
            String dailyPriceStr = binding.etDailyPrice.getText() != null ? binding.etDailyPrice.getText().toString().trim() : "100";
            String depositStr = binding.etSecurityDeposit.getText() != null ? binding.etSecurityDeposit.getText().toString().trim() : "500";
            String condition = binding.etCondition.getText() != null ? binding.etCondition.getText().toString().trim() : "Good";
            String desc = binding.etDescription.getText() != null ? binding.etDescription.getText().toString().trim() : "";

            if (name.isEmpty()) {
                Snackbar.make(view, "Please enter product name", Snackbar.LENGTH_SHORT).show();
                return;
            }

            int price = 20;
            double dailyPrice = 100.0;
            double deposit = 500.0;
            try {
                price = Integer.parseInt(priceStr);
                dailyPrice = Double.parseDouble(dailyPriceStr);
                deposit = Double.parseDouble(depositStr);
            } catch (NumberFormatException ignored) {}

            long ownerId = sessionManager.getLoggedInUserId();
            String productId = "PROD_" + UUID.randomUUID().toString().substring(0, 8);

            binding.progressUpload.setVisibility(View.VISIBLE);
            binding.btnSaveProduct.setEnabled(false);

            if (photoFile != null && photoFile.exists()) {
                uploadPhotoAndSaveProduct(productId, ownerId, name, category, desc, price, dailyPrice, deposit, condition, productRepository, view);
            } else {
                saveProductData(productId, ownerId, name, category, desc, price, dailyPrice, deposit, condition,
                        capturedImagePath.isEmpty() ? Collections.emptyList() : Collections.singletonList(capturedImagePath),
                        productRepository, view);
            }
        });
    }

    private void checkCameraPermissionAndStart() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(requireContext());

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
                cameraProvider.bindToLifecycle(getViewLifecycleOwner(), cameraSelector, preview, imageCapture);

            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Error starting CameraX", e);
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void takePhoto() {
        if (imageCapture == null) return;

        photoFile = new File(requireContext().getCacheDir(), "product_" + System.currentTimeMillis() + ".jpg");

        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(requireContext()),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        capturedImagePath = photoFile.getAbsolutePath();
                        Uri savedUri = Uri.fromFile(photoFile);
                        binding.ivCapturedPreview.setImageURI(savedUri);
                        binding.ivCapturedPreview.setVisibility(View.VISIBLE);
                        binding.viewFinder.setVisibility(View.GONE);
                        Toast.makeText(requireContext(), "Photo captured successfully!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.e(TAG, "Photo capture failed: " + exception.getMessage(), exception);
                        Toast.makeText(requireContext(), "Failed to capture photo", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadPhotoAndSaveProduct(String productId, long ownerId, String name, String category,
                                           String desc, int price, double dailyPrice, double deposit,
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
                        saveProductData(productId, ownerId, name, category, desc, price, dailyPrice, deposit,
                                condition, Collections.singletonList(downloadUrl), repository, view);
                    })
                    .addOnFailureListener(e -> {
                        Log.w(TAG, "Firebase Storage upload failed, fallback to local path", e);
                        saveProductData(productId, ownerId, name, category, desc, price, dailyPrice, deposit,
                                condition, Collections.singletonList(photoFile.getAbsolutePath()), repository, view);
                    });
        } catch (Exception e) {
            Log.w(TAG, "Firebase not initialized, using local image path", e);
            saveProductData(productId, ownerId, name, category, desc, price, dailyPrice, deposit,
                    condition, Collections.singletonList(photoFile.getAbsolutePath()), repository, view);
        }
    }

    private void saveProductData(String productId, long ownerId, String name, String category,
                                 String desc, int price, double dailyPrice, double deposit,
                                 String condition, List<String> imageUrls,
                                 ProductRepository repository, View view) {
        String mainImagePath = !imageUrls.isEmpty() ? imageUrls.get(0) : "";
        Product product = new Product(0, ownerId, name, category, desc, price, PriceUnit.HOUR, condition, true, 23.8759, 90.3198, mainImagePath);
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
        Snackbar.make(view, "Product listed successfully!", Snackbar.LENGTH_SHORT).show();
        Navigation.findNavController(view).popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
