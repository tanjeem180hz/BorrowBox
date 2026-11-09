package com.borrowbox.app.data.repository;

import androidx.lifecycle.LiveData;
import com.borrowbox.app.model.Payment;
import java.util.List;

public interface PaymentRepository {
    LiveData<List<Payment>> getPaymentsForUserLive(long userId);
    LiveData<List<Payment>> getPaymentsForRentalLive(long rentalId);
    void insertPayment(Payment payment);
}
