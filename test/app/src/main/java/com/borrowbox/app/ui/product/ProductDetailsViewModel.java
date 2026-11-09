package com.borrowbox.app.ui.product;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RentalRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.data.repository.UserRepository;
import com.borrowbox.app.domain.DailyPricing;
import com.borrowbox.app.domain.HourlyPricing;
import com.borrowbox.app.domain.PricingStrategy;
import com.borrowbox.app.model.PriceUnit;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.model.RentalStatus;
import com.borrowbox.app.model.Student;
import java.util.UUID;

public class ProductDetailsViewModel extends AndroidViewModel {

    private final ProductRepository productRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    private final MutableLiveData<Integer> selectedDuration = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> calculatedCost = new MutableLiveData<>(20);
    private final MutableLiveData<Long> requestSuccess = new MutableLiveData<>(null);

    private final PricingStrategy hourlyPricing = new HourlyPricing();
    private final PricingStrategy dailyPricing = new DailyPricing();

    public ProductDetailsViewModel(@NonNull Application application) {
        super(application);
        RepositoryProvider provider = RepositoryProvider.getInstance(application);
        this.productRepository = provider.getProductRepository();
        this.rentalRepository = provider.getRentalRepository();
        this.userRepository = provider.getUserRepository();
        this.sessionManager = new SessionManager(application);
    }

    public LiveData<Product> getProductLive(long productId) {
        return productRepository.getProductByIdLive(productId);
    }

    public LiveData<Student> getOwnerLive(long ownerId) {
        return userRepository.getStudentLive(ownerId);
    }

    public LiveData<Integer> getSelectedDuration() { return selectedDuration; }
    public LiveData<Integer> getCalculatedCost() { return calculatedCost; }
    public LiveData<Long> getRequestSuccess() { return requestSuccess; }
    public long getLoggedInUserId() { return sessionManager.getLoggedInUserId(); }

    public void setDuration(int units, Product product) {
        selectedDuration.setValue(units);
        if (product != null) {
            int cost;
            if (product.getPriceUnit() == PriceUnit.DAY) {
                cost = dailyPricing.calculateTotalCost(product.getPrice(), units);
            } else {
                cost = hourlyPricing.calculateTotalCost(product.getPrice(), units);
            }
            calculatedCost.setValue(cost);
        }
    }

    public void requestRental(Product product, boolean isUrgent) {
        if (product == null) return;
        long borrowerId = sessionManager.getLoggedInUserId();
        int duration = selectedDuration.getValue() != null ? selectedDuration.getValue() : 1;
        int cost = calculatedCost.getValue() != null ? calculatedCost.getValue() : product.getPrice();

        Rental rental = new Rental();
        rental.setProductId(product.getId());
        rental.setOwnerId(product.getOwnerId());
        rental.setBorrowerId(borrowerId);
        rental.setDurationValue(duration);
        rental.setDurationUnit(product.getPriceUnit());
        rental.setTotalCost(cost);
        rental.setDeposit(200);
        rental.setStatus(sessionManager.isAutoAcceptEnabled() ? RentalStatus.CONFIRMED : RentalStatus.REQUESTED);
        rental.setRequestedAt(System.currentTimeMillis());
        if (sessionManager.isAutoAcceptEnabled()) {
            rental.setConfirmedAt(System.currentTimeMillis());
        }
        rental.setQrToken(UUID.randomUUID().toString().substring(0, 8).toUpperCase(java.util.Locale.ROOT));
        rental.setUrgent(isUrgent);

        rentalRepository.createRental(rental, rentalId -> requestSuccess.setValue(rentalId));
    }
}
