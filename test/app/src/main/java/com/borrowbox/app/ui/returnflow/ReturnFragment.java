package com.borrowbox.app.ui.returnflow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.borrowbox.app.R;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RentalRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.databinding.FragmentReturnBinding;
import com.borrowbox.app.domain.RentalStateMachine;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.model.RentalStatus;
import com.borrowbox.app.util.AppExecutors;
import com.google.android.material.snackbar.Snackbar;

public class ReturnFragment extends Fragment {

    private FragmentReturnBinding binding;
    private Rental currentRental;
    private long rentalId = 1L;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentReturnBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            rentalId = getArguments().getLong("rental_id", 1L);
        }

        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        loadSampleComparisonBitmaps();

        RentalRepository rentalRepository = RepositoryProvider.getInstance(requireContext()).getRentalRepository();
        rentalRepository.getRentalByIdLive(rentalId).observe(getViewLifecycleOwner(), rental -> {
            if (rental != null) {
                currentRental = rental;
            }
        });

        binding.btnSamplePhoto.setOnClickListener(v -> {
            loadSampleComparisonBitmaps();
            Snackbar.make(view, "Loaded sample inspection photos", Snackbar.LENGTH_SHORT).show();
        });

        binding.btnConfirmReturn.setOnClickListener(v -> confirmReturn());
    }

    private void loadSampleComparisonBitmaps() {
        // Create 2 contrasting sample bitmaps to show in the comparison slider
        Bitmap beforeBmp = Bitmap.createBitmap(400, 300, Bitmap.Config.ARGB_8888);
        Canvas c1 = new Canvas(beforeBmp);
        c1.drawColor(Color.parseColor("#4338CA"));
        Paint p1 = new Paint();
        p1.setColor(Color.WHITE);
        p1.setTextSize(28f);
        p1.setTextAlign(Paint.Align.CENTER);
        c1.drawText("Before: Pristine Condition", 200, 150, p1);

        Bitmap afterBmp = Bitmap.createBitmap(400, 300, Bitmap.Config.ARGB_8888);
        Canvas c2 = new Canvas(afterBmp);
        c2.drawColor(Color.parseColor("#059669"));
        Paint p2 = new Paint();
        p2.setColor(Color.WHITE);
        p2.setTextSize(28f);
        p2.setTextAlign(Paint.Align.CENTER);
        c2.drawText("After: Verified No Damage", 200, 150, p2);

        binding.comparisonSlider.setBitmaps(beforeBmp, afterBmp);
    }

    private void confirmReturn() {
        if (currentRental == null || !isAdded()) return;

        RentalStateMachine.validateTransition(currentRental.getStatus(), RentalStatus.RETURNED);
        currentRental.setStatus(RentalStatus.RETURNED);
        currentRental.setReturnedAt(System.currentTimeMillis());

        RentalRepository rentalRepository = RepositoryProvider.getInstance(requireContext()).getRentalRepository();
        ProductRepository productRepository = RepositoryProvider.getInstance(requireContext()).getProductRepository();

        rentalRepository.updateRental(currentRental);

        // Mark product back to available
        AppExecutors.getInstance().diskIO().execute(() -> {
            com.borrowbox.app.model.Product p = productRepository.getProductByIdLive(currentRental.getProductId()).getValue();
            if (p != null) {
                p.setAvailable(true);
                productRepository.updateProduct(p);
            }
        });

        if (getView() != null) {
            Snackbar.make(getView(), R.string.return_confirmed, Snackbar.LENGTH_SHORT).show();
            Bundle args = new Bundle();
            args.putLong("rental_id", currentRental.getId());
            Navigation.findNavController(getView()).navigate(R.id.paymentFragment, args);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
