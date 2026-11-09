package com.borrowbox.app.ui.rentals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.borrowbox.app.R;
import com.borrowbox.app.databinding.FragmentRentalsBinding;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.model.RentalStatus;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class RentalsFragment extends Fragment {

    private FragmentRentalsBinding binding;
    private RentalsViewModel viewModel;
    private RentalsAdapter adapter;
    private int currentTab = 0; // 0 = Active, 1 = Requests, 2 = History

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRentalsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RentalsViewModel.class);

        binding.recyclerRentals.setLayoutManager(new LinearLayoutManager(requireContext()));

        setupAdapter();
        loadTabData(0);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
                setupAdapter();
                loadTabData(currentTab);
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupAdapter() {
        boolean isRequestsTab = (currentTab == 1);
        adapter = new RentalsAdapter(isRequestsTab, new RentalsAdapter.OnRentalActionListener() {
            @Override
            public void onRentalClick(Rental rental) {
                if (getView() == null) return;
                Bundle args = new Bundle();
                args.putLong("rental_id", rental.getId());

                if (rental.getStatus() == RentalStatus.CONFIRMED) {
                    Navigation.findNavController(getView()).navigate(R.id.rentalConfirmedFragment, args);
                } else if (rental.getStatus() == RentalStatus.ACTIVE || rental.getStatus() == RentalStatus.OVERDUE) {
                    Navigation.findNavController(getView()).navigate(R.id.timerFragment, args);
                } else if (rental.getStatus() == RentalStatus.RETURNED) {
                    Navigation.findNavController(getView()).navigate(R.id.paymentFragment, args);
                }
            }

            @Override
            public void onAccept(Rental rental) {
                viewModel.acceptRequest(rental);
                if (getView() != null) {
                    Snackbar.make(getView(), "Request Accepted! Ready for handover.", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onReject(Rental rental) {
                viewModel.rejectRequest(rental);
                if (getView() != null) {
                    Snackbar.make(getView(), "Request Rejected.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        binding.recyclerRentals.setAdapter(adapter);
    }

    private void loadTabData(int tabIndex) {
        if (tabIndex == 0) {
            viewModel.getActiveRentalsLive().observe(getViewLifecycleOwner(), list -> {
                adapter.submitList(list);
                binding.tvEmpty.setVisibility((list == null || list.isEmpty()) ? View.VISIBLE : View.GONE);
            });
        } else if (tabIndex == 1) {
            viewModel.getRequestsForOwnerLive().observe(getViewLifecycleOwner(), list -> {
                adapter.submitList(list);
                binding.tvEmpty.setVisibility((list == null || list.isEmpty()) ? View.VISIBLE : View.GONE);
            });
        } else {
            viewModel.getHistoryRentalsLive().observe(getViewLifecycleOwner(), list -> {
                adapter.submitList(list);
                binding.tvEmpty.setVisibility((list == null || list.isEmpty()) ? View.VISIBLE : View.GONE);
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
