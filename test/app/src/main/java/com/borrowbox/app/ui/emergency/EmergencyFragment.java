package com.borrowbox.app.ui.emergency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.borrowbox.app.R;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RentalRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.databinding.FragmentEmergencyBinding;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.model.RentalStatus;
import com.borrowbox.app.ui.home.ProductAdapter;
import com.google.android.material.snackbar.Snackbar;
import java.util.UUID;

public class EmergencyFragment extends Fragment {

    private FragmentEmergencyBinding binding;
    private ProductAdapter adapter;
    private String selectedKeyword = "Calculator";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEmergencyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProductRepository productRepository = RepositoryProvider.getInstance(requireContext()).getProductRepository();
        RentalRepository rentalRepository = RepositoryProvider.getInstance(requireContext()).getRentalRepository();

        adapter = new ProductAdapter(product -> {
            // Send instant URGENT request
            Rental urgentRental = new Rental();
            urgentRental.setProductId(product.getId());
            urgentRental.setOwnerId(product.getOwnerId());
            urgentRental.setBorrowerId(1L); // Active student
            urgentRental.setDurationValue(2);
            urgentRental.setDurationUnit(product.getPriceUnit());
            urgentRental.setTotalCost(product.getPrice() * 2);
            urgentRental.setDeposit(200);
            urgentRental.setStatus(RentalStatus.CONFIRMED); // Auto-confirmed for urgent demo
            urgentRental.setConfirmedAt(System.currentTimeMillis());
            urgentRental.setQrToken(UUID.randomUUID().toString().substring(0, 8).toUpperCase(java.util.Locale.ROOT));
            urgentRental.setUrgent(true);

            rentalRepository.createRental(urgentRental, rentalId -> {
                Snackbar.make(view, "Urgent Request Confirmed! Ready for handover.", Snackbar.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putLong("rental_id", rentalId);
                Navigation.findNavController(view).navigate(R.id.rentalConfirmedFragment, args);
            });
        });

        binding.recyclerEmergency.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerEmergency.setAdapter(adapter);

        filterEmergencyList(selectedKeyword);

        binding.chipGroupEmergency.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            int id = checkedIds.get(0);
            if (id == R.id.chip_calculator) selectedKeyword = "Calculator";
            else if (id == R.id.chip_charger) selectedKeyword = "Charger";
            else if (id == R.id.chip_powerbank) selectedKeyword = "Power";
            else if (id == R.id.chip_hdmi) selectedKeyword = "HDMI";
            filterEmergencyList(selectedKeyword);
        });
    }

    private void filterEmergencyList(String keyword) {
        ProductRepository productRepository = RepositoryProvider.getInstance(requireContext()).getProductRepository();
        productRepository.searchProductsLive(keyword).observe(getViewLifecycleOwner(), products -> {
            adapter.submitList(products);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
