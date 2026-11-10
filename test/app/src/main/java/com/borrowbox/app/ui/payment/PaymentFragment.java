package com.borrowbox.app.ui.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.borrowbox.app.R;
import com.borrowbox.app.data.repository.PaymentRepository;
import com.borrowbox.app.data.repository.RentalRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.databinding.FragmentPaymentBinding;
import com.borrowbox.app.domain.DefaultLateFeePolicy;
import com.borrowbox.app.domain.LateFeePolicy;
import com.borrowbox.app.model.Payment;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.util.CurrencyUtils;
import com.google.android.material.snackbar.Snackbar;

public class PaymentFragment extends Fragment {

    private FragmentPaymentBinding binding;
    private Rental currentRental;
    private final LateFeePolicy lateFeePolicy = new DefaultLateFeePolicy();
    private long rentalId = 1L;
    private int finalTotal = 240;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            rentalId = getArguments().getLong("rental_id", 1L);
        }

        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        RentalRepository rentalRepository = RepositoryProvider.getInstance(requireContext()).getRentalRepository();
        rentalRepository.getRentalByIdLive(rentalId).observe(getViewLifecycleOwner(), rental -> {
            if (rental != null) {
                currentRental = rental;
                int rentalCost = rental.getTotalCost() > 0 ? rental.getTotalCost() : 40;
                int deposit = rental.getDeposit() > 0 ? rental.getDeposit() : 200;

                int lateFee = 0;
                if (rental.getReturnedAt() > rental.getEndAt() && rental.getEndAt() > 0) {
                    lateFee = lateFeePolicy.calculateLateFee(rental.getReturnedAt() - rental.getEndAt());
                }

                finalTotal = rentalCost + deposit + lateFee;

                binding.tvCostRental.setText(CurrencyUtils.format(rentalCost));
                binding.tvCostDeposit.setText(CurrencyUtils.format(deposit));
                binding.tvCostLate.setText(CurrencyUtils.format(lateFee));
                binding.tvCostTotal.setText(CurrencyUtils.format(finalTotal));
            }
        });

        binding.btnPay.setOnClickListener(v -> {
            String method = "Cash Handover";
            if (binding.rbBkash.isChecked()) method = "bKash (Mock)";
            else if (binding.rbNagad.isChecked()) method = "Nagad (Mock)";

            PaymentRepository paymentRepository = RepositoryProvider.getInstance(requireContext()).getPaymentRepository();
            long userId = currentRental != null ? currentRental.getBorrowerId() : 1L;

            Payment payment = new Payment(0, rentalId, userId, finalTotal, "RENTAL_SETTLEMENT", method, "COMPLETED", System.currentTimeMillis());
            paymentRepository.insertPayment(payment);

            Snackbar.make(view, R.string.payment_success, Snackbar.LENGTH_SHORT).show();

            Bundle args = new Bundle();
            args.putLong("rental_id", rentalId);
            Navigation.findNavController(view).navigate(R.id.ratingFragment, args);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
