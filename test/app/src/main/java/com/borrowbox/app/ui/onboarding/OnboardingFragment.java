package com.borrowbox.app.ui.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.borrowbox.app.R;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.databinding.FragmentOnboardingBinding;

public class OnboardingFragment extends Fragment {

    private FragmentOnboardingBinding binding;
    private int currentPage = 0;

    private final int[] titles = {
            R.string.onboarding_title_1,
            R.string.onboarding_title_2,
            R.string.onboarding_title_3
    };

    private final int[] descs = {
            R.string.onboarding_desc_1,
            R.string.onboarding_desc_2,
            R.string.onboarding_desc_3
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updatePage();

        binding.btnSkip.setOnClickListener(v -> finishOnboarding());

        binding.btnNext.setOnClickListener(v -> {
            if (currentPage < 2) {
                currentPage++;
                updatePage();
            } else {
                finishOnboarding();
            }
        });
    }

    private void updatePage() {
        binding.tvTitle.setText(titles[currentPage]);
        binding.tvDesc.setText(descs[currentPage]);
        binding.tvPageIndicator.setText((currentPage + 1) + " of 3");

        if (currentPage == 2) {
            binding.btnNext.setText(R.string.onboarding_get_started);
            binding.btnSkip.setVisibility(View.INVISIBLE);
        } else {
            binding.btnNext.setText(R.string.onboarding_next);
            binding.btnSkip.setVisibility(View.VISIBLE);
        }
    }

    private void finishOnboarding() {
        SessionManager sessionManager = new SessionManager(requireContext());
        sessionManager.setOnboardingCompleted(true);
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(R.id.loginFragment);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
