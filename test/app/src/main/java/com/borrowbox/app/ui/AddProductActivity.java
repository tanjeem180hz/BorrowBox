package com.borrowbox.app.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.borrowbox.app.databinding.ActivityAddProductBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class AddProductActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 1001;
    private ActivityAddProductBinding binding;
    private ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View Binding সেটআপ
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ১. ক্যামেরা পারমিশন চেক ও রিকোয়েস্ট করা
        if (checkCameraPermission()) {
            startCamera();
        } else {
            requestCameraPermission();
        }

        // ২. ক্যাপচার বাটন ক্লিক লিসেনার
        binding.btnCapturePhoto.setOnClickListener(v -> takePhoto());

        // ৩. সেভ বাটন ক্লিক লিসেনার
        binding.btnSaveProduct.setOnClickListener(v -> saveProductToDatabase());

        // ৪. ব্যাক বাটন
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Camera permission is required to capture photos.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // CameraX প্রিভিউ স্টার্ট করার মেথড
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(binding.viewFinder.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder().build();

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

            } catch (ExecutionException | InterruptedException e) {
                Log.e("AddProductActivity", "Use case binding failed", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    // ছবি ক্যাপচার করার মেথড
    private void takePhoto() {
        if (imageCapture == null) return;

        File photoFile = new File(getExternalFilesDir(null), System.currentTimeMillis() + ".jpg");
        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        binding.progressUpload.setVisibility(View.VISIBLE);

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                binding.progressUpload.setVisibility(View.GONE);
                Toast.makeText(AddProductActivity.this, "Photo captured successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                binding.progressUpload.setVisibility(View.GONE);
                Toast.makeText(AddProductActivity.this, "Capture failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveProductToDatabase() {
        String productName = binding.etName.getText().toString().trim();
        if (productName.isEmpty()) {
            binding.tilName.setError("Product name cannot be empty");
            return;
        }
        Toast.makeText(this, "Product data validated successfully!", Toast.LENGTH_SHORT).show();
    }
}