package com.borrowbox.app.data.repository;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.borrowbox.app.data.local.AppDatabase;
import com.borrowbox.app.data.local.ProductConditionDao;
import com.borrowbox.app.model.ProductCondition;
import com.borrowbox.app.util.AppExecutors;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Repository handling pre-rental and post-rental condition checks.
 * Integrates Room DB cache with Firebase Firestore (condition_checks) & Cloud Storage (evidence_photos).
 */
public class ConditionRepository {

    private static volatile ConditionRepository instance;
    private final ProductConditionDao conditionDao;
    private final FirebaseFirestore firestore;
    private final FirebaseStorage storage;

    private ConditionRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.conditionDao = db.productConditionDao();
        this.firestore = FirebaseFirestore.getInstance();
        this.storage = FirebaseStorage.getInstance();
    }

    public static ConditionRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (ConditionRepository.class) {
                if (instance == null) {
                    instance = new ConditionRepository(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public LiveData<ProductCondition> getConditionForRentalLive(long rentalId) {
        return conditionDao.getConditionForRentalLive(rentalId);
    }

    public void saveCondition(ProductCondition condition, ConditionSaveCallback callback) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            // 1. Save locally to Room DB immediately
            conditionDao.insert(condition);

            // 2. Sync to Firebase Firestore & Storage
            Map<String, Object> data = new HashMap<>();
            data.put("checkId", condition.getCheckId());
            data.put("rentalId", condition.getRentalId());
            data.put("prePhotoUrl", condition.getPrePhotoUrl());
            data.put("postPhotoUrl", condition.getPostPhotoUrl());
            data.put("preNotes", condition.getPreNotes());
            data.put("postNotes", condition.getPostNotes());
            data.put("preTimestamp", condition.getPreTimestamp());
            data.put("postTimestamp", condition.getPostTimestamp());
            data.put("preGpsLocation", condition.getPreGpsLocation());
            data.put("postGpsLocation", condition.getPostGpsLocation());

            try {
                firestore.collection("condition_checks")
                        .document(condition.getCheckId())
                        .set(data)
                        .addOnSuccessListener(aVoid -> {
                            if (callback != null) {
                                AppExecutors.getInstance().mainThread().execute(callback::onSuccess);
                            }
                        })
                        .addOnFailureListener(e -> {
                            // Local save succeeded, notify callback with local fallback note
                            if (callback != null) {
                                AppExecutors.getInstance().mainThread().execute(callback::onSuccess);
                            }
                        });
            } catch (Exception e) {
                if (callback != null) {
                    AppExecutors.getInstance().mainThread().execute(callback::onSuccess);
                }
            }
        });
    }

    public void uploadPhoto(String photoPath, PhotoUploadCallback callback) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            try {
                File file = new File(photoPath);
                if (!file.exists()) {
                    if (callback != null) {
 Branch--Ahona
                        AppExecutors.getInstance().mainThread().execute(() -> callback.onError(new Exception("File does not exist")));

                        AppExecutors.getInstance().mainThread().execute(() -> callback::onError);
 main
                    }
                    return;
                }

                // Compress image to JPEG
                Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
                if (bitmap == null) {
                    if (callback != null) {
                        AppExecutors.getInstance().mainThread().execute(() -> callback.onError(new Exception("Failed to decode photo")));
                    }
                    return;
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos);
                byte[] imageBytes = baos.toByteArray();

                String filename = "evidence_photos/" + UUID.randomUUID().toString() + ".jpg";
                StorageReference ref = storage.getReference().child(filename);

                ref.putBytes(imageBytes)
                        .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {
                            if (callback != null) {
                                AppExecutors.getInstance().mainThread().execute(() -> callback.onSuccess(uri.toString()));
                            }
                        }).addOnFailureListener(e -> fallbackLocalUri(imageBytes, callback)))
                        .addOnFailureListener(e -> fallbackLocalUri(imageBytes, callback));

            } catch (Exception e) {
                if (callback != null) {
                    AppExecutors.getInstance().mainThread().execute(() -> callback.onError(e));
                }
            }
        });
    }

    private void fallbackLocalUri(byte[] bytes, PhotoUploadCallback callback) {
        // Fallback to Base64 data URI or local file URI if offline
        String base64 = "data:image/jpeg;base64," + Base64.encodeToString(bytes, Base64.NO_WRAP);
        if (callback != null) {
            AppExecutors.getInstance().mainThread().execute(() -> callback.onSuccess(base64));
        }
    }

    public interface ConditionSaveCallback {
        void onSuccess();
        void onError(Exception e);
    }

    public interface PhotoUploadCallback {
        void onSuccess(String downloadUrl);
        void onError(Exception e);
    }
}
