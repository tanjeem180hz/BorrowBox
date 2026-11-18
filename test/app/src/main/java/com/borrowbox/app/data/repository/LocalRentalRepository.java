package com.borrowbox.app.data.repository;

import androidx.lifecycle.LiveData;
import com.borrowbox.app.data.local.RentalDao;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.util.AppExecutors;
import java.util.List;

public class LocalRentalRepository implements RentalRepository {

    private final RentalDao rentalDao;
    private final AppExecutors executors;

    public LocalRentalRepository(RentalDao rentalDao, AppExecutors executors) {
        this.rentalDao = rentalDao;
        this.executors = executors;
    }

    @Override
    public LiveData<Rental> getRentalByIdLive(long id) {
        return rentalDao.getRentalByIdLive(id);
    }

    @Override
    public LiveData<List<Rental>> getRentalsForUserLive(long userId) {
        return rentalDao.getRentalsForUserLive(userId);
    }

    @Override
    public LiveData<List<Rental>> getActiveRentalsLive(long userId) {
        return rentalDao.getActiveRentalsLive(userId);
    }

    @Override
    public LiveData<List<Rental>> getRequestsForOwnerLive(long ownerId) {
        return rentalDao.getRequestsForOwnerLive(ownerId);
    }

    @Override
    public LiveData<List<Rental>> getHistoryRentalsLive(long userId) {
        return rentalDao.getHistoryRentalsLive(userId);
    }

    @Override
    public void createRental(Rental rental, OnRentalCreatedCallback callback) {
        executors.diskIO().execute(() -> {
            long id = rentalDao.insert(rental);
            rental.setId(id);
            if (callback != null) {
                executors.mainThread().execute(() -> callback.onCreated(id));
            }
        });
    }

    @Override
    public void updateRental(Rental rental) {
        executors.diskIO().execute(() -> rentalDao.update(rental));
    }
}
