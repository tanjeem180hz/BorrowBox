package com.borrowbox.app.ui.handover;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.borrowbox.app.R;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RentalRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.databinding.FragmentQrHandoverBinding;
import com.borrowbox.app.domain.RentalStateMachine;
import com.borrowbox.app.model.PriceUnit;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.model.RentalStatus;
import com.borrowbox.app.util.AppExecutors;
import com.borrowbox.app.util.QrUtils;
import com.google.android.material.snackbar.Snackbar;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class QrHandoverFragment extends Fragment {

    private FragmentQrHandoverBinding binding;
    private ObjectAnimator scanLineAnimator;
    private Rental currentRental;
    private long rentalId = 1L;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(
            new ScanContract(), result -> {
                if (result.getContents() != null) {
                    confirmHandover();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentQrHandoverBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            rentalId = getArguments().getLong("rental_id", 1L);
        }

        // Animate scanning line
        scanLineAnimator = ObjectAnimator.ofFloat(binding.viewScanLine, "translationY", 0f, 600f);
        scanLineAnimator.setDuration(1800);
        scanLineAnimator.setRepeatCount(ValueAnimator.INFINITE);
        scanLineAnimator.setRepeatMode(ValueAnimator.REVERSE);
        scanLineAnimator.start();

        RentalRepository rentalRepository = RepositoryProvider.getInstance(requireContext()).getRentalRepository();
        rentalRepository.getRentalByIdLive(rentalId).observe(getViewLifecycleOwner(), rental -> {
            if (rental != null) {
                currentRental = rental;
                String token = rental.getQrToken().isEmpty() ? "BB-" + rental.getId() : rental.getQrToken();
                binding.tvTokenPreview.setText("Verification Token: " + token);

                String qrPayload = "BORROWBOX:RENTAL:" + rental.getId() + ":TOKEN:" + token;
                Bitmap qrBitmap = QrUtils.generateQrBitmap(qrPayload, 512);
                if (qrBitmap != null) {
                    binding.imgQrCode.setImageBitmap(qrBitmap);
                }
            }
        });

        // Simulate Scan (Emulator Friendly)
        binding.btnSimulateScan.setOnClickListener(v -> confirmHandover());

        // Camera Scan for real devices
        binding.btnCameraScan.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Align QR code inside viewfinder");
            options.setBeepEnabled(true);
            options.setOrientationLocked(false);
            barcodeLauncher.launch(options);
        });
    }

    private void confirmHandover() {
        if (currentRental == null || !isAdded()) return;

        RentalStateMachine.validateTransition(currentRental.getStatus(), RentalStatus.ACTIVE);
        currentRental.setStatus(RentalStatus.ACTIVE);
        long now = System.currentTimeMillis();
        currentRental.setStartAt(now);

        long durationMillis = currentRental.getDurationUnit() == PriceUnit.DAY ?
                currentRental.getDurationValue() * 24 * 60 * 60 * 1000L :
                currentRental.getDurationValue() * 60 * 60 * 1000L;

        currentRental.setEndAt(now + durationMillis);

        RentalRepository rentalRepository = RepositoryProvider.getInstance(requireContext()).getRentalRepository();
        ProductRepository productRepository = RepositoryProvider.getInstance(requireContext()).getProductRepository();

        rentalRepository.updateRental(currentRental);

        // Update product availability to false
        AppExecutors.getInstance().diskIO().execute(() -> {
            com.borrowbox.app.model.Product p = productRepository.getProductByIdLive(currentRental.getProductId()).getValue();
            if (p != null) {
                p.setAvailable(false);
                productRepository.updateProduct(p);
            }
        });

        if (getView() != null) {
            Snackbar.make(getView(), R.string.qr_confirmed, Snackbar.LENGTH_SHORT).show();
            Bundle args = new Bundle();
            args.putLong("rental_id", currentRental.getId());
            Navigation.findNavController(getView()).navigate(R.id.timerFragment, args);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (scanLineAnimator != null) scanLineAnimator.cancel();
        binding = null;
    }
}
