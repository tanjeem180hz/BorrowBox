package com.borrowbox.app.ui.product;

 Branch--Rafi

import android.content.Intent;
 main
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
 Branch--Rafi
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.borrowbox.app.R;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
 main
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.databinding.FragmentMyProductsBinding;
import com.borrowbox.app.model.Product;
 Branch--Rafi
import com.borrowbox.app.util.EdgeToEdgeHelper;
import com.google.android.material.snackbar.Snackbar;

public class MyProductsFragment extends Fragment implements MyProductsAdapter.OnProductActionListener {

    private FragmentMyProductsBinding binding;
    private MyProductsAdapter adapter;
    private ProductRepository productRepository;
    private long currentUserId = 1L;

import com.google.android.material.snackbar.Snackbar;

public class MyProductsFragment extends Fragment implements MyProductsAdapter.OnMyProductActionListener {

    private FragmentMyProductsBinding binding;
    private ProductRepository productRepository;
    private MyProductsAdapter adapter;
 main

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

 Branch--Rafi
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
 main
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
