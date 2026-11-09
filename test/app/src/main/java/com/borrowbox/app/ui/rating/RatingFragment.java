package com.borrowbox.app.ui.rating;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.borrowbox.app.R;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.RatingRepository;
import com.borrowbox.app.data.repository.RentalRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.data.repository.UserRepository;
import com.borrowbox.app.databinding.FragmentRatingBinding;
import com.borrowbox.app.domain.TrustScoreEngine;
import com.borrowbox.app.model.Rating;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.model.Student;
import com.borrowbox.app.ui.common.AnimatedCounter;
import com.google.android.material.snackbar.Snackbar;

public class RatingFragment extends Fragment {

    private FragmentRatingBinding binding;
    private int selectedStars = 5;
    private long rentalId = 1L;
    private Rental currentRental;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRatingBinding.inflate(inflater, container, false);
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
            }
        });

        // Animated Trust Score 92 -> 94 demo
        AnimatedCounter.animateValue(binding.tvAnimatedTrustScore, 92, 94, "", "");

        setupStarListeners();

        binding.btnSubmitRating.setOnClickListener(v -> submitRating());
    }

    private void setupStarListeners() {
        ImageView[] stars = {binding.star1, binding.star2, binding.star3, binding.star4, binding.star5};
        for (int i = 0; i < stars.length; i++) {
            final int starIndex = i + 1;
            stars[i].setOnClickListener(v -> {
                selectedStars = starIndex;
                for (int j = 0; j < stars.length; j++) {
                    if (j < starIndex) {
                        stars[j].setImageResource(R.drawable.ic_star_filled);
                    } else {
                        stars[j].setImageResource(R.drawable.ic_star_border);
                    }
                }
            });
        }
    }

    private void submitRating() {
        SessionManager sessionManager = new SessionManager(requireContext());
        UserRepository userRepository = RepositoryProvider.getInstance(requireContext()).getUserRepository();
        RatingRepository ratingRepository = RepositoryProvider.getInstance(requireContext()).getRatingRepository();

        long fromUserId = sessionManager.getLoggedInUserId();
        long toUserId = currentRental != null ? currentRental.getOwnerId() : 2L;

        String comment = binding.chipSmooth.isChecked() ?
                getString(R.string.rating_chip_smooth) : getString(R.string.rating_chip_recommended);

        Rating rating = new Rating(0, rentalId, fromUserId, toUserId, selectedStars, comment, System.currentTimeMillis());
        ratingRepository.insertRating(rating);

        // Update target user's Trust Score in database
        userRepository.getStudentLive(toUserId).observe(getViewLifecycleOwner(), student -> {
            if (student != null) {
                int newCompletions = student.getCompletedRentals() + 1;
                int newRatingCount = student.getRatingCount() + 1;
                double newAvg = ((student.getRatingAverage() * student.getRatingCount()) + selectedStars) / newRatingCount;

                int updatedScore = TrustScoreEngine.calculateTrustScore(
                        newAvg, newRatingCount, newCompletions, 0, student.isVerified());

                student.setTrustScore(updatedScore);
                student.setCompletedRentals(newCompletions);
                student.setRatingAverage(Math.round(newAvg * 10.0) / 10.0);
                student.setRatingCount(newRatingCount);

                userRepository.updateStudent(student);
            }
        });

        if (getView() != null) {
            Snackbar.make(getView(), "Review submitted successfully!", Snackbar.LENGTH_SHORT).show();
            Navigation.findNavController(getView()).navigate(R.id.nav_rentals);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
