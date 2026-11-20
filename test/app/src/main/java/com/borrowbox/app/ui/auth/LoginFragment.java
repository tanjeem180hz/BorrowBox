package com.borrowbox.app.ui.auth;

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
import com.borrowbox.app.databinding.FragmentLoginBinding;
import com.borrowbox.app.model.User;
import com.borrowbox.app.util.EdgeToEdgeHelper;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private AuthViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Automatic display cutout (notch/punch-hole) and keyboard/bottom cutout adaptation
        EdgeToEdgeHelper.applyTopCutout(binding.getRoot());
        EdgeToEdgeHelper.applyImeAndBottomCutout(binding.btnLogin);

        // Quick demo persona selectors for evaluation and testing
        binding.btnChipTanjeem.setOnClickListener(v -> {
            binding.etEmail.setText("tanjeem@diu.edu.bd");
            binding.etPassword.setText("123456");
            binding.tvError.setVisibility(View.GONE);
        });

        binding.btnChipSabbir.setOnClickListener(v -> {
            binding.etEmail.setText("sabbir@diu.edu.bd");
            binding.etPassword.setText("123456");
            binding.tvError.setVisibility(View.GONE);
        });

        binding.btnChipAdmin.setOnClickListener(v -> {
            binding.etEmail.setText("admin@diu.edu.bd");
            binding.etPassword.setText("admin123");
            binding.tvError.setVisibility(View.GONE);
        });

        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText() != null ? binding.etEmail.getText().toString().trim() : "";
            String pass = binding.etPassword.getText() != null ? binding.etPassword.getText().toString() : "";
            viewModel.login(email, pass);
        });

        binding.tvRegisterLink.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.registerFragment);
        });

        viewModel.getAuthSuccess().observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                String role = viewModel.getUserRole().getValue();
                androidx.navigation.NavOptions navOptions = new androidx.navigation.NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, true)
                        .build();

                if (User.ROLE_ADMIN.equalsIgnoreCase(role)) {
                    Navigation.findNavController(view).navigate(R.id.adminDashboardFragment, null, navOptions);
                } else {
                    Navigation.findNavController(view).navigate(R.id.nav_home, null, navOptions);
                }
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), msg -> {
            if (msg != null && !msg.isEmpty()) {
                binding.tvError.setText(msg);
                binding.tvError.setVisibility(View.VISIBLE);
            } else {
                binding.tvError.setVisibility(View.GONE);
            }
        });

        viewModel.getLoading().observe(getViewLifecycleOwner(), loading -> {
            binding.btnLogin.setEnabled(!Boolean.TRUE.equals(loading));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
