package com.borrowbox.app.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.borrowbox.app.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        adapter = new ProductAdapter(product -> {
            Bundle args = new Bundle();
            args.putLong("product_id", product.getId());
            Navigation.findNavController(view).navigate(R.id.productDetailsFragment, args);
        });

        binding.recyclerProducts.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2));
        binding.recyclerProducts.setAdapter(adapter);

        viewModel.getCurrentUserLive().observe(getViewLifecycleOwner(), student -> {
            if (student != null) {
                binding.tvGreeting.setText("Hello, " + student.getName());
            }
        });

        viewModel.getFilteredProducts().observe(getViewLifecycleOwner(), products -> {
            adapter.submitList(products);
            if (products == null || products.isEmpty()) {
                binding.tvEmpty.setVisibility(View.VISIBLE);
            } else {
                binding.tvEmpty.setVisibility(View.GONE);
            }
        });

        // Search text listener
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setSearchQuery(s != null ? s.toString() : "");
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Category chips
        binding.chipGroupCategories.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            int id = checkedIds.get(0);
            if (id == R.id.chip_all) viewModel.setCategory("All");
            else if (id == R.id.chip_electronics) viewModel.setCategory("Electronics");
            else if (id == R.id.chip_study) viewModel.setCategory("Study");
            else if (id == R.id.chip_accessories) viewModel.setCategory("Accessories");
            else if (id == R.id.chip_camera) viewModel.setCategory("Camera");
        });

        // Emergency FAB
        binding.fabEmergency.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.emergencyFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
