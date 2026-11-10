package com.borrowbox.app.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.borrowbox.app.R;
import com.borrowbox.app.databinding.FragmentProfileBinding;
import com.borrowbox.app.ui.common.AnimatedCounter;
import com.google.android.material.snackbar.Snackbar;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        viewModel.getCurrentStudentLive().observe(getViewLifecycleOwner(), student -> {
            if (student != null) {
                String name = student.getName() != null && !student.getName().trim().isEmpty()
                        ? student.getName().trim() : "Student";
                binding.tvStudentName.setText(name);
                binding.tvAvatar.setText(name.substring(0, 1).toUpperCase(java.util.Locale.ROOT));
                binding.tvStudentMeta.setText("ID: " + student.getStudentId() + " • " + student.getDepartment() + " • DIU");
                binding.tvStats.setText(student.getRatingAverage() + " Rating • " + student.getCompletedRentals() + " Completed");

                // Animate Trust Score
                AnimatedCounter.animateValue(binding.tvTrustScoreValue, 0, student.getTrustScore(), "", "");
                binding.trustProgressView.animateProgress(student.getTrustScore());
            }
        });

        // Demo User Switcher
        binding.btnUserTanjeem.setOnClickListener(v -> switchUser(1L, "Muhammad Tanjeem"));
        binding.btnUserSabbir.setOnClickListener(v -> switchUser(2L, "Md. Sabbir Hossain Shamim"));
        binding.btnUserHimel.setOnClickListener(v -> switchUser(3L, "Chandan Acharjee Himel"));
        binding.btnUserTasnim.setOnClickListener(v -> switchUser(4L, "Mst Tasnim Binty Ekram"));

        binding.btnAddProduct.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.addEditProductFragment);
        });

        binding.btnSettings.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.settingsFragment);
        });

        binding.btnLogout.setOnClickListener(v -> {
            viewModel.logout();
            androidx.navigation.NavOptions navOptions = new androidx.navigation.NavOptions.Builder()
                    .setPopUpTo(R.id.nav_graph, true)
                    .build();
            Navigation.findNavController(view).navigate(R.id.loginFragment, null, navOptions);
        });
    }

    private void switchUser(long userId, String name) {
        viewModel.switchDemoUser(userId);
        if (getView() != null) {
            Snackbar.make(getView(), "Switched account to: " + name, Snackbar.LENGTH_SHORT).show();
        }
        if (getActivity() instanceof com.borrowbox.app.MainActivity) {
            ((com.borrowbox.app.MainActivity) getActivity()).updateBottomNavAvatar();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
