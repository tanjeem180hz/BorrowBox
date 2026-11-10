package com.borrowbox.app.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.borrowbox.app.R;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.databinding.FragmentSplashBinding;

public class SplashFragment extends Fragment {

    private FragmentSplashBinding binding;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable navigateRunnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Subtle scale & fade animation
        binding.imgLogo.setScaleX(0.7f);
        binding.imgLogo.setScaleY(0.7f);
        binding.imgLogo.setAlpha(0f);
        binding.imgLogo.animate()
                .scaleX(1.0f)
                .scaleY(1.0f)
                .alpha(1.0f)
                .setDuration(1000)
                .start();

        SessionManager sessionManager = new SessionManager(requireContext());

        navigateRunnable = () -> {
            if (!isAdded()) return;
            androidx.navigation.NavOptions navOptions = new androidx.navigation.NavOptions.Builder()
                    .setPopUpTo(R.id.splashFragment, true)
                    .build();
            if (!sessionManager.isOnboardingCompleted()) {
                Navigation.findNavController(view).navigate(R.id.onboardingFragment, null, navOptions);
            } else if (!sessionManager.isLoggedIn()) {
                Navigation.findNavController(view).navigate(R.id.loginFragment, null, navOptions);
            } else {
                Navigation.findNavController(view).navigate(R.id.nav_home, null, navOptions);
            }
        };

        handler.postDelayed(navigateRunnable, 1400);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(navigateRunnable);
        binding = null;
    }
}
