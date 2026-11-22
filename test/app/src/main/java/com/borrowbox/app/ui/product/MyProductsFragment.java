package com.borrowbox.app.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.borrowbox.app.R;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.databinding.FragmentMyProductsBinding;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.util.EdgeToEdgeHelper;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

public class MyProductsFragment extends Fragment implements MyProductsAdapter.OnProductActionListener {

    private FragmentMyProductsBinding binding;
    private MyProductsAdapter adapter;
    private ProductRepository productRepository;
    private long currentUserId = 1L;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMyProductsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EdgeToEdgeHelper.applyTopCutout(binding.getRoot());

        SessionManager sessionManager = new SessionManager(requireContext());
        currentUserId = sessionManager.getLoggedInUserId();

        productRepository = RepositoryProvider.getInstance(requireContext()).getProductRepository();

        setupRecyclerView();

        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        binding.btnAddProduct.setOnClickListener(v -> navigateToAddProduct());
        binding.btnEmptyAddProduct.setOnClickListener(v -> navigateToAddProduct());

        loadMyProducts();
    }

    private void setupRecyclerView() {
        adapter = new MyProductsAdapter(this);
        binding.rvMyProducts.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvMyProducts.setAdapter(adapter);
    }

    private void loadMyProducts() {
        productRepository.getProductsByOwnerLive(currentUserId).observe(getViewLifecycleOwner(), myProducts -> {
            if (myProducts == null || myProducts.isEmpty()) {
                binding.rvMyProducts.setVisibility(View.GONE);
                binding.layoutEmptyState.setVisibility(View.VISIBLE);
            } else {
                binding.rvMyProducts.setVisibility(View.VISIBLE);
                binding.layoutEmptyState.setVisibility(View.GONE);
                adapter.setProducts(myProducts);
            }
        });
    }

    private void navigateToAddProduct() {
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(R.id.addEditProductFragment);
        }
    }

    @Override
    public void onToggleAvailability(Product product, boolean isAvailable) {
        product.setAvailable(isAvailable);
        productRepository.updateProduct(product);
        if (getView() != null) {
            String statusMsg = isAvailable ? "Listing marked as Available" : "Listing marked as Unavailable";
            Snackbar.make(getView(), statusMsg, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteProduct(Product product) {
        productRepository.deleteProduct(product);
        if (getView() != null) {
            Snackbar.make(getView(), "Product listing deleted", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProductClick(Product product) {
        if (getView() != null && product != null) {
            Bundle args = new Bundle();
            args.putLong("product_id", product.getId());
            Navigation.findNavController(getView()).navigate(R.id.productDetailsFragment, args);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
