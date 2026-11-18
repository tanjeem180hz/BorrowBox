package com.borrowbox.app.ui.rentals;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RentalRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.domain.RentalStateMachine;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.model.RentalStatus;
import java.util.List;

public class RentalsViewModel extends AndroidViewModel {

    private final RentalRepository rentalRepository;
    private final ProductRepository productRepository;
    private final SessionManager sessionManager;

    public RentalsViewModel(@NonNull Application application) {
        super(application);
        RepositoryProvider provider = RepositoryProvider.getInstance(application);
        this.rentalRepository = provider.getRentalRepository();
        this.productRepository = provider.getProductRepository();
        this.sessionManager = new SessionManager(application);
    }

    public long getCurrentUserId() {
        return sessionManager.getLoggedInUserId();
    }

    public LiveData<List<Rental>> getActiveRentalsLive() {
        return rentalRepository.getActiveRentalsLive(sessionManager.getLoggedInUserId());
    }

    public LiveData<List<Rental>> getRequestsForOwnerLive() {
        return rentalRepository.getRequestsForOwnerLive(sessionManager.getLoggedInUserId());
    }

    public LiveData<List<Rental>> getHistoryRentalsLive() {
        return rentalRepository.getHistoryRentalsLive(sessionManager.getLoggedInUserId());
    }

    public LiveData<Product> getProductByIdLive(long id) {
        return productRepository.getProductByIdLive(id);
    }

    public void acceptRequest(Rental rental) {
        if (rental == null) return;
        RentalStateMachine.validateTransition(rental.getStatus(), RentalStatus.CONFIRMED);
        rental.setStatus(RentalStatus.CONFIRMED);
        rental.setConfirmedAt(System.currentTimeMillis());
        rentalRepository.updateRental(rental);
    }

    public void rejectRequest(Rental rental) {
        if (rental == null) return;
        RentalStateMachine.validateTransition(rental.getStatus(), RentalStatus.REJECTED);
        rental.setStatus(RentalStatus.REJECTED);
        rentalRepository.updateRental(rental);
    }
}
