package com.borrowbox.app.ui.timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.borrowbox.app.R;
import com.borrowbox.app.data.repository.RentalRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.databinding.FragmentTimerBinding;
import com.borrowbox.app.domain.DefaultLateFeePolicy;
import com.borrowbox.app.domain.LateFeePolicy;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.model.RentalStatus;
import com.borrowbox.app.util.CurrencyUtils;
import com.borrowbox.app.util.DateUtils;

public class TimerFragment extends Fragment {

    private FragmentTimerBinding binding;
    private final Handler timerHandler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable;
    private Rental currentRental;
    private final LateFeePolicy lateFeePolicy = new DefaultLateFeePolicy();
    private long rentalId = 1L;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTimerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            rentalId = getArguments().getLong("rental_id", 1L);
        }

        RentalRepository rentalRepository = RepositoryProvider.getInstance(requireContext()).getRentalRepository();
        rentalRepository.getRentalByIdLive(rentalId).observe(getViewLifecycleOwner(), rental -> {
            if (rental != null) {
                currentRental = rental;
                startTimerLoop();
            }
        });
        startTimerLoop();

        // Fast-forward demo button
        binding.btnSimulateFastForward.setOnClickListener(v -> {
            if (currentRental != null) {
                // Set endAt to 25 minutes ago
                currentRental.setEndAt(System.currentTimeMillis() - (25 * 60 * 1000L));
                currentRental.setStatus(RentalStatus.OVERDUE);
                rentalRepository.updateRental(currentRental);
                updateTimerDisplay();
            }
        });

        binding.btnProceedReturn.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putLong("rental_id", rentalId);
            Navigation.findNavController(view).navigate(R.id.returnFragment, args);
        });
    }

    private void startTimerLoop() {
        if (timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (isAdded()) {
                    updateTimerDisplay();
                    timerHandler.postDelayed(this, 1000);
                }
            }
        };
        timerHandler.post(timerRunnable);
    }

    private void updateTimerDisplay() {
        if (binding == null) return;

        long now = System.currentTimeMillis();
        long end = (currentRental != null && currentRental.getEndAt() > 0) ?
                currentRental.getEndAt() : now + (2 * 60 * 60 * 1000L);
        long remaining = end - now;

        if (remaining > 0) {
            binding.tvCountdown.setText(DateUtils.formatDuration(remaining));
            binding.tvCountdown.setTextColor(ContextCompat.getColor(requireContext(), R.color.brand_primary));
            binding.tvStatusSub.setText(R.string.timer_remaining);

            long totalDuration = currentRental != null ? (currentRental.getEndAt() - currentRental.getStartAt()) : (2 * 60 * 60 * 1000L);
            if (totalDuration > 0) {
                float progress = ((float) remaining / (float) totalDuration) * 100f;
                binding.circularProgress.setProgress(progress);
            }
        } else {
            // OVERDUE
            long overdueMillis = Math.abs(remaining);
            binding.tvCountdown.setText("-" + DateUtils.formatDuration(overdueMillis));
            binding.tvCountdown.setTextColor(ContextCompat.getColor(requireContext(), R.color.danger));
            binding.tvStatusSub.setText(R.string.timer_overdue);

            binding.circularProgress.setProgressColor(ContextCompat.getColor(requireContext(), R.color.danger));
            binding.circularProgress.setProgress(100f);

            int lateFee = lateFeePolicy.calculateLateFee(overdueMillis);
            binding.tvLateFeeTicker.setText("Overdue by " + (overdueMillis / (60 * 1000L)) + " min • Late Fee: " + CurrencyUtils.format(lateFee));
            binding.tvLateFeeTicker.setTextColor(ContextCompat.getColor(requireContext(), R.color.danger));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
        binding = null;
    }
}
