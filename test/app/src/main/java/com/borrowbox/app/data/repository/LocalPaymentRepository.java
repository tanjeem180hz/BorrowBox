package com.borrowbox.app.data.repository;

import androidx.lifecycle.LiveData;
import com.borrowbox.app.data.local.PaymentDao;
import com.borrowbox.app.model.Payment;
import com.borrowbox.app.util.AppExecutors;
import java.util.List;

public class LocalPaymentRepository implements PaymentRepository {

    private final PaymentDao paymentDao;
    private final AppExecutors executors;

    public LocalPaymentRepository(PaymentDao paymentDao, AppExecutors executors) {
        this.paymentDao = paymentDao;
        this.executors = executors;
    }

    @Override
    public LiveData<List<Payment>> getPaymentsForUserLive(long userId) {
        return paymentDao.getPaymentsForUserLive(userId);
    }

    @Override
    public LiveData<List<Payment>> getPaymentsForRentalLive(long rentalId) {
        return paymentDao.getPaymentsForRentalLive(rentalId);
    }

    @Override
    public void insertPayment(Payment payment) {
        executors.diskIO().execute(() -> paymentDao.insert(payment));
    }
}
