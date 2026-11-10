package com.borrowbox.app.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(requireContext());

        binding.btnBack.setOnClickListener(v -> androidx.navigation.Navigation.findNavController(v).navigateUp());

        // Theme selection setup
        int currentTheme = sessionManager.getThemeMode();
        if (currentTheme == SessionManager.THEME_LIGHT) {
            binding.rbThemeLight.setChecked(true);
        } else if (currentTheme == SessionManager.THEME_DARK) {
            binding.rbThemeDark.setChecked(true);
        } else {
            binding.rbThemeSystem.setChecked(true);
        }

        binding.rgTheme.setOnCheckedChangeListener((group, checkedId) -> {
            int newTheme = SessionManager.THEME_SYSTEM;
            if (checkedId == com.borrowbox.app.R.id.rb_theme_light) {
                newTheme = SessionManager.THEME_LIGHT;
            } else if (checkedId == com.borrowbox.app.R.id.rb_theme_dark) {
                newTheme = SessionManager.THEME_DARK;
            }
            sessionManager.setThemeMode(newTheme);
            sessionManager.applyAppTheme();
        });

        binding.switchAutoAccept.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sessionManager.setAutoAcceptEnabled(isChecked);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
