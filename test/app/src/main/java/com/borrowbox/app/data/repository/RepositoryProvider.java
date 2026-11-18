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

    private final AppDatabase database;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RentalRepository rentalRepository;
    private final RatingRepository ratingRepository;
    private final PaymentRepository paymentRepository;
    private final ChatMessageRepository chatMessageRepository;

    private RepositoryProvider(@NonNull Context context) {
        this.database = AppDatabase.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();

        this.userRepository = new LocalUserRepository(database.studentDao(), executors);
        this.productRepository = new LocalProductRepository(database.productDao(), executors);
        this.rentalRepository = new LocalRentalRepository(database.rentalDao(), executors);
        this.ratingRepository = new LocalRatingRepository(database.ratingDao(), executors);
        this.paymentRepository = new LocalPaymentRepository(database.paymentDao(), executors);
        this.chatMessageRepository = new ChatMessageRepository(
                database.chatMessageDao(),
                database.studentDao(),
                database.productDao(),
                executors
        );
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

    public AppDatabase getDatabase() { return database; }
    public UserRepository getUserRepository() { return userRepository; }
    public ProductRepository getProductRepository() { return productRepository; }
    public RentalRepository getRentalRepository() { return rentalRepository; }
    public RatingRepository getRatingRepository() { return ratingRepository; }
    public PaymentRepository getPaymentRepository() { return paymentRepository; }
    public ChatMessageRepository getChatMessageRepository() { return chatMessageRepository; }
}
