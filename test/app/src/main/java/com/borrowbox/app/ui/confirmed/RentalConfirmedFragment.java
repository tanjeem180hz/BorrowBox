package com.borrowbox.app.ui.confirmed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.borrowbox.app.R;
import com.borrowbox.app.data.repository.RentalRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.databinding.FragmentRentalConfirmedBinding;
import com.borrowbox.app.util.CurrencyUtils;

public class RentalConfirmedFragment extends Fragment {

    private FragmentRentalConfirmedBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRentalConfirmedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_home));

        // Animate checkmark
        binding.imgCheck.setScaleX(0.4f);
        binding.imgCheck.setScaleY(0.4f);
        binding.imgCheck.animate()
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(500)
                .start();

        long rentalId = getArguments() != null ? getArguments().getLong("rental_id", 1L) : 1L;

        RentalRepository rentalRepository = RepositoryProvider.getInstance(requireContext()).getRentalRepository();
        rentalRepository.getRentalByIdLive(rentalId).observe(getViewLifecycleOwner(), rental -> {
            if (rental != null) {
                String unit = rental.getDurationUnit().name().toLowerCase(java.util.Locale.ROOT);
                binding.tvSummaryRate.setText(rental.getDurationValue() + " " + unit + "(s) • "
                        + CurrencyUtils.format(rental.getTotalCost()));
            }
        });

        binding.btnOpenMap.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putLong("rental_id", rentalId);
            Navigation.findNavController(view).navigate(R.id.nav_map, args);
        });

        binding.btnQrHandover.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putLong("rental_id", rentalId);
            Navigation.findNavController(view).navigate(R.id.qrHandoverFragment, args);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
