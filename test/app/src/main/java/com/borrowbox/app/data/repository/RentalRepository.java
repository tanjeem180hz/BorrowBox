package com.borrowbox.app.data.repository;

import androidx.lifecycle.LiveData;
import com.borrowbox.app.model.Rental;
import java.util.List;

public interface RentalRepository {
    LiveData<Rental> getRentalByIdLive(long id);
    LiveData<List<Rental>> getRentalsForUserLive(long userId);
    LiveData<List<Rental>> getActiveRentalsLive(long userId);
    LiveData<List<Rental>> getRequestsForOwnerLive(long ownerId);
    LiveData<List<Rental>> getHistoryRentalsLive(long userId);
    void createRental(Rental rental, OnRentalCreatedCallback callback);
    void updateRental(Rental rental);

    interface OnRentalCreatedCallback {
        void onCreated(long rentalId);
    }
}
