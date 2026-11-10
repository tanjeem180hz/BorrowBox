package com.borrowbox.app.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.borrowbox.app.R;
import com.borrowbox.app.databinding.FragmentProductDetailsBinding;
import com.borrowbox.app.model.PriceUnit;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.util.CurrencyUtils;
import com.borrowbox.app.util.ProductImageHelper;

public class ProductDetailsFragment extends Fragment {

    private FragmentProductDetailsBinding binding;
    private ProductDetailsViewModel viewModel;
    private Product currentProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);

        // Functional Back Button
        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        long productId = getArguments() != null ? getArguments().getLong("product_id", 1L) : 1L;

        viewModel.getProductLive(productId).observe(getViewLifecycleOwner(), product -> {
            if (product != null) {
                currentProduct = product;
                binding.imgHero.setImageResource(ProductImageHelper.getDrawableResForProduct(product));
                binding.tvTitle.setText(product.getName());
                String unit = product.getPriceUnit() == PriceUnit.DAY ? "/day" : "/h";
                binding.tvPriceTag.setText(CurrencyUtils.format(product.getPrice()) + unit);
                binding.tvCondition.setText(product.getCondition() + " condition");
                binding.tvDescription.setText(product.getDescription());

                if (product.isAvailable()) {
                    binding.dotStatus.setBackgroundResource(R.drawable.dot_green);
                    binding.tvAvailability.setText("Available for borrow");
                    binding.tvAvailability.setTextColor(ContextCompat.getColor(requireContext(), R.color.success));
                } else {
                    binding.dotStatus.setBackgroundResource(R.drawable.dot_gray);
                    binding.tvAvailability.setText("Currently rented");
                    binding.tvAvailability.setTextColor(ContextCompat.getColor(requireContext(), R.color.muted));
                }

                // Update duration calculation
                viewModel.setDuration(2, product);

                // Fetch owner details
                viewModel.getOwnerLive(product.getOwnerId()).observe(getViewLifecycleOwner(), owner -> {
                    if (owner != null) {
                        binding.tvOwnerName.setText(owner.getName());
                        binding.tvOwnerAvatar.setText(owner.getName().substring(0, 1));
                        binding.tvOwnerRating.setText("⭐ " + owner.getRatingAverage() + " • " + owner.getDepartment());
                        binding.tvOwnerTrust.setText("Trust " + owner.getTrustScore() + "%");
                    }
                });

                // Check ownership and availability
                if (product.getOwnerId() == viewModel.getLoggedInUserId()) {
                    binding.btnRequestRental.setEnabled(false);
                    binding.tvRequestStatusMsg.setText(R.string.product_request_owner_warning);
                    binding.tvRequestStatusMsg.setVisibility(View.VISIBLE);
                } else if (!product.isAvailable()) {
                    binding.btnRequestRental.setEnabled(false);
                    binding.tvRequestStatusMsg.setText(R.string.product_request_unavailable);
                    binding.tvRequestStatusMsg.setVisibility(View.VISIBLE);
                } else {
                    binding.btnRequestRental.setEnabled(true);
                    binding.tvRequestStatusMsg.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getCalculatedCost().observe(getViewLifecycleOwner(), cost -> {
            if (cost != null) {
                binding.tvTotalCalculated.setText(CurrencyUtils.format(cost));
            }
        });

        // Duration chips
        binding.chipGroupDuration.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty() || currentProduct == null) return;
            int id = checkedIds.get(0);
            if (id == R.id.chip_1h) viewModel.setDuration(1, currentProduct);
            else if (id == R.id.chip_2h) viewModel.setDuration(2, currentProduct);
            else if (id == R.id.chip_3h) viewModel.setDuration(3, currentProduct);
            else if (id == R.id.chip_1d) viewModel.setDuration(1, currentProduct);
        });

        binding.btnRequestRental.setOnClickListener(v -> {
            if (currentProduct != null) {
                binding.btnRequestRental.setEnabled(false);
                binding.btnRequestRental.setText("Processing...");
                viewModel.requestRental(currentProduct, false);
            }
        });

        viewModel.getRequestSuccess().observe(getViewLifecycleOwner(), rentalId -> {
            if (rentalId != null) {
                Bundle args = new Bundle();
                args.putLong("rental_id", rentalId);
                Navigation.findNavController(view).navigate(R.id.rentalConfirmedFragment, args);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
