package com.borrowbox.app.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.borrowbox.app.R;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.databinding.FragmentAdminDashboardBinding;
import com.borrowbox.app.model.Dispute;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.model.Student;
import com.borrowbox.app.util.EdgeToEdgeHelper;
import java.util.List;

public class AdminDashboardFragment extends Fragment {

    private static final int TAB_STUDENTS = 0;
    private static final int TAB_PRODUCTS = 1;
    private static final int TAB_DISPUTES = 2;

    private FragmentAdminDashboardBinding binding;
    private AdminViewModel viewModel;
    private SessionManager sessionManager;

    private AdminStudentAdapter studentAdapter;
    private AdminProductAdapter productAdapter;
    private AdminDisputeAdapter disputeAdapter;

    private int currentTab = TAB_STUDENTS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AdminViewModel.class);
        sessionManager = new SessionManager(requireContext());

        // Apply display cutout (notch/punch-hole) insets
        EdgeToEdgeHelper.applyTopCutout(binding.layoutAdminHeader);

        setupAdapters();
        setupTabs();
        setupHeaderActions();
        observeMetrics();
        switchTab(TAB_STUDENTS);
    }

    private void setupAdapters() {
        binding.rvAdminItems.setLayoutManager(new LinearLayoutManager(requireContext()));

        studentAdapter = new AdminStudentAdapter(new AdminStudentAdapter.OnStudentActionListener() {
            @Override
            public void onToggleVerify(Student student) {
                viewModel.toggleStudentVerification(student);
                Toast.makeText(requireContext(), "Updated verification for " + student.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onToggleSuspend(Student student) {
                viewModel.toggleStudentSuspension(student);
                String state = student.isSuspended() ? "reinstated" : "suspended";
                Toast.makeText(requireContext(), "Student " + student.getName() + " " + state, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResetTrust(Student student) {
                viewModel.resetStudentTrust(student);
                Toast.makeText(requireContext(), "Trust score reset to default (80) for " + student.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        productAdapter = new AdminProductAdapter(product -> {
            viewModel.toggleProductAvailability(product);
            String state = product.isAvailable() ? "taken down" : "reinstated";
            Toast.makeText(requireContext(), "Listing '" + product.getName() + "' " + state, Toast.LENGTH_SHORT).show();
        });

        disputeAdapter = new AdminDisputeAdapter(new AdminDisputeAdapter.OnDisputeActionListener() {
            @Override
            public void onFullRefund(Dispute dispute) {
                viewModel.resolveDispute(dispute, "Full deposit released to borrower.", 100);
                Toast.makeText(requireContext(), "Dispute #" + dispute.getId() + " resolved: Full Refund", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPenalize(Dispute dispute) {
                viewModel.resolveDispute(dispute, "50% deposit penalty deducted for item condition.", 50);
                Toast.makeText(requireContext(), "Dispute #" + dispute.getId() + " resolved: 50% Penalty", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDismiss(Dispute dispute) {
                viewModel.dismissDispute(dispute, "Dismissed after administrative review.");
                Toast.makeText(requireContext(), "Dispute #" + dispute.getId() + " dismissed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupTabs() {
        binding.tabStudents.setOnClickListener(v -> switchTab(TAB_STUDENTS));
        binding.tabProducts.setOnClickListener(v -> switchTab(TAB_PRODUCTS));
        binding.tabDisputes.setOnClickListener(v -> switchTab(TAB_DISPUTES));
    }

    private void switchTab(int tab) {
        currentTab = tab;

        // Reset tab button styles
        highlightTab(binding.tabStudents, tab == TAB_STUDENTS);
        highlightTab(binding.tabProducts, tab == TAB_PRODUCTS);
        highlightTab(binding.tabDisputes, tab == TAB_DISPUTES);

        if (tab == TAB_STUDENTS) {
            binding.rvAdminItems.setAdapter(studentAdapter);
            viewModel.getAllStudents().observe(getViewLifecycleOwner(), list -> {
                if (currentTab == TAB_STUDENTS) {
                    studentAdapter.submitList(list);
                    updateEmptyState(list == null || list.isEmpty(), "No student accounts found.");
                }
            });
        } else if (tab == TAB_PRODUCTS) {
            binding.rvAdminItems.setAdapter(productAdapter);
            viewModel.getAllProducts().observe(getViewLifecycleOwner(), list -> {
                if (currentTab == TAB_PRODUCTS) {
                    productAdapter.submitList(list);
                    updateEmptyState(list == null || list.isEmpty(), "No equipment listings found.");
                }
            });
        } else {
            binding.rvAdminItems.setAdapter(disputeAdapter);
            viewModel.getAllDisputes().observe(getViewLifecycleOwner(), list -> {
                if (currentTab == TAB_DISPUTES) {
                    disputeAdapter.submitList(list);
                    updateEmptyState(list == null || list.isEmpty(), "No dispute reports found.");
                }
            });
        }
    }

    private void highlightTab(com.google.android.material.button.MaterialButton btn, boolean isSelected) {
        if (isSelected) {
            btn.setBackgroundColor(getResources().getColor(R.color.brand_primary, null));
            btn.setTextColor(0xFFFFFFFF);
            btn.setStrokeWidth(0);
        } else {
            btn.setBackgroundColor(0x00000000);
            btn.setTextColor(getResources().getColor(R.color.muted, null));
            btn.setStrokeColorResource(R.color.divider);
            btn.setStrokeWidth(1);
        }
    }

    private void updateEmptyState(boolean isEmpty, String message) {
        if (isEmpty) {
            binding.tvEmptyState.setText(message);
            binding.tvEmptyState.setVisibility(View.VISIBLE);
            binding.rvAdminItems.setVisibility(View.GONE);
        } else {
            binding.tvEmptyState.setVisibility(View.GONE);
            binding.rvAdminItems.setVisibility(View.VISIBLE);
        }
    }

    private void setupHeaderActions() {
        binding.btnSwitchStudent.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.nav_home);
        });

        binding.btnAdminLogout.setOnClickListener(v -> {
            sessionManager.logout();
            androidx.navigation.NavOptions navOptions = new androidx.navigation.NavOptions.Builder()
                    .setPopUpTo(R.id.adminDashboardFragment, true)
                    .build();
            Navigation.findNavController(requireView()).navigate(R.id.loginFragment, null, navOptions);
        });
    }

    private void observeMetrics() {
        viewModel.getTotalStudentsCount().observe(getViewLifecycleOwner(), count -> {
            binding.tvKpiStudents.setText(String.valueOf(count != null ? count : 0));
        });

        viewModel.getProductCount().observe(getViewLifecycleOwner(), count -> {
            binding.tvKpiProducts.setText(String.valueOf(count != null ? count : 0));
        });

        viewModel.getActiveRentalsCount().observe(getViewLifecycleOwner(), count -> {
            binding.tvKpiRentals.setText(String.valueOf(count != null ? count : 0));
        });

        viewModel.getOpenDisputesCount().observe(getViewLifecycleOwner(), count -> {
            binding.tvKpiDisputes.setText(String.valueOf(count != null ? count : 0));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
