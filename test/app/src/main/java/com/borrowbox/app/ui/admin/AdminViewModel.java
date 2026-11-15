package com.borrowbox.app.ui.admin;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.borrowbox.app.data.local.AppDatabase;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.model.Dispute;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.model.Student;
import com.borrowbox.app.util.AppExecutors;
import java.util.List;

public class AdminViewModel extends AndroidViewModel {

    private final AppDatabase db;
    private final AppExecutors executors;

    private final LiveData<List<Student>> allStudents;
    private final LiveData<List<Product>> allProducts;
    private final LiveData<List<Dispute>> allDisputes;
    private final LiveData<Integer> totalStudentsCount;
    private final LiveData<Integer> productCount;
    private final LiveData<Integer> activeRentalsCount;
    private final LiveData<Integer> openDisputesCount;
    private final LiveData<Integer> suspendedStudentsCount;

    public AdminViewModel(@NonNull Application application) {
        super(application);
        this.db = RepositoryProvider.getInstance(application).getDatabase();
        this.executors = AppExecutors.getInstance();

        this.allStudents = db.studentDao().getAllStudentsLive();
        this.allProducts = db.productDao().getAllProductsLive();
        this.allDisputes = db.disputeDao().getAllDisputesLive();
        this.totalStudentsCount = db.studentDao().getTotalStudentsCountLive();
        this.productCount = db.productDao().getProductCountLive();
        this.activeRentalsCount = db.rentalDao().getGlobalActiveRentalsCountLive();
        this.openDisputesCount = db.disputeDao().getOpenDisputesCountLive();
        this.suspendedStudentsCount = db.studentDao().getSuspendedStudentsCountLive();
    }

    public LiveData<List<Student>> getAllStudents() { return allStudents; }
    public LiveData<List<Product>> getAllProducts() { return allProducts; }
    public LiveData<List<Dispute>> getAllDisputes() { return allDisputes; }
    public LiveData<Integer> getTotalStudentsCount() { return totalStudentsCount; }
    public LiveData<Integer> getProductCount() { return productCount; }
    public LiveData<Integer> getActiveRentalsCount() { return activeRentalsCount; }
    public LiveData<Integer> getOpenDisputesCount() { return openDisputesCount; }
    public LiveData<Integer> getSuspendedStudentsCount() { return suspendedStudentsCount; }

    public void toggleStudentSuspension(Student student) {
        if (student == null) return;
        boolean willSuspend = !student.isSuspended();
        String message = willSuspend ? "Suspended by Proctorial Body: Under review" : "Active";
        executors.diskIO().execute(() -> {
            db.studentDao().updateSuspension(student.getId(), willSuspend, message);
        });
    }

    public void toggleStudentVerification(Student student) {
        if (student == null) return;
        boolean newVerified = !student.isVerified();
        executors.diskIO().execute(() -> {
            db.studentDao().updateVerification(student.getId(), newVerified);
        });
    }

    public void resetStudentTrust(Student student) {
        if (student == null) return;
        executors.diskIO().execute(() -> {
            db.studentDao().updateTrustScore(student.getId(), 80);
        });
    }

    public void toggleProductAvailability(Product product) {
        if (product == null) return;
        boolean newStatus = !product.isAvailable();
        executors.diskIO().execute(() -> {
            db.productDao().updateAvailability(product.getId(), newStatus);
        });
    }

    public void resolveDispute(Dispute dispute, String verdict, int refundPercentage) {
        if (dispute == null) return;
        long now = System.currentTimeMillis();
        executors.diskIO().execute(() -> {
            db.disputeDao().resolveDispute(dispute.getId(), Dispute.STATUS_RESOLVED, verdict, refundPercentage, now);
        });
    }

    public void dismissDispute(Dispute dispute, String reason) {
        if (dispute == null) return;
        long now = System.currentTimeMillis();
        executors.diskIO().execute(() -> {
            db.disputeDao().resolveDispute(dispute.getId(), Dispute.STATUS_DISMISSED, reason, 100, now);
        });
    }
}
