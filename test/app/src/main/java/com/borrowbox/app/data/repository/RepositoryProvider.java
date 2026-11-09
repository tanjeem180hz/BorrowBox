package com.borrowbox.app.data.repository;

import android.content.Context;
import androidx.annotation.NonNull;
import com.borrowbox.app.data.local.AppDatabase;
import com.borrowbox.app.util.AppExecutors;

/**
 * Factory class providing repository instances. Can swap between local and remote implementations.
 */
public final class RepositoryProvider {

    private static volatile RepositoryProvider instance;

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RentalRepository rentalRepository;
    private final RatingRepository ratingRepository;
    private final PaymentRepository paymentRepository;

    private RepositoryProvider(@NonNull Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();

        this.userRepository = new LocalUserRepository(db.studentDao(), executors);
        this.productRepository = new LocalProductRepository(db.productDao(), executors);
        this.rentalRepository = new LocalRentalRepository(db.rentalDao(), executors);
        this.ratingRepository = new LocalRatingRepository(db.ratingDao(), executors);
        this.paymentRepository = new LocalPaymentRepository(db.paymentDao(), executors);
    }

    public static RepositoryProvider getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (RepositoryProvider.class) {
                if (instance == null) {
                    instance = new RepositoryProvider(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public UserRepository getUserRepository() { return userRepository; }
    public ProductRepository getProductRepository() { return productRepository; }
    public RentalRepository getRentalRepository() { return rentalRepository; }
    public RatingRepository getRatingRepository() { return ratingRepository; }
    public PaymentRepository getPaymentRepository() { return paymentRepository; }
}
