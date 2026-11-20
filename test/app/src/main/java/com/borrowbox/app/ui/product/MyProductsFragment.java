package com.borrowbox.app.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.databinding.FragmentMyProductsBinding;
import com.borrowbox.app.model.Product;
import com.google.android.material.snackbar.Snackbar;

public class MyProductsFragment extends Fragment implements MyProductsAdapter.OnMyProductActionListener {

    private FragmentMyProductsBinding binding;
    private ProductRepository productRepository;
    private MyProductsAdapter adapter;

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

        productRepository = RepositoryProvider.getInstance(requireContext()).getProductRepository();
        SessionManager sessionManager = new SessionManager(requireContext());
        long loggedInUserId = sessionManager.getLoggedInUserId();

        adapter = new MyProductsAdapter(this);
        binding.rvMyProducts.setAdapter(adapter);

        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        binding.fabAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AddProductActivity.class);
            startActivity(intent);
        });

        productRepository.getProductsByOwnerLive(loggedInUserId).observe(getViewLifecycleOwner(), products -> {
            if (products == null || products.isEmpty()) {
                binding.layoutEmptyState.setVisibility(View.VISIBLE);
                binding.rvMyProducts.setVisibility(View.GONE);
            } else {
                binding.layoutEmptyState.setVisibility(View.GONE);
                binding.rvMyProducts.setVisibility(View.VISIBLE);
                adapter.setProducts(products);
            }
        });
    }

    @Override
    public void onToggleAvailability(Product product) {
        boolean newStatus = !product.isAvailable();
        product.setAvailable(newStatus);
        productRepository.updateProduct(product);

        String message = newStatus ? "Product marked as Available" : "Product marked as Rented";
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onEditProduct(Product product) {
        Intent intent = new Intent(requireContext(), EditProductActivity.class);
        intent.putExtra("product_id", product.getId());
        startActivity(intent);
    }

    @Override
    public void onDeleteProduct(Product product) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Listing")
                .setMessage("Are you sure you want to remove '" + product.getName() + "' from your rental catalog?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    productRepository.deleteProduct(product);
                    Snackbar.make(binding.getRoot(), "Product removed from catalog", Snackbar.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
