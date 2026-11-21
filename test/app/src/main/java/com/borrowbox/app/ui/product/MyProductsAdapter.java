package com.borrowbox.app.ui.product;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
 Branch--Rafi
import androidx.recyclerview.widget.RecyclerView;
import com.borrowbox.app.databinding.ItemMyProductBinding;
import com.borrowbox.app.model.Product;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.borrowbox.app.R;
import com.borrowbox.app.databinding.ItemMyProductBinding;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.util.CurrencyUtils;
 main
import com.borrowbox.app.util.ProductImageHelper;
import java.util.ArrayList;
import java.util.List;

public class MyProductsAdapter extends RecyclerView.Adapter<MyProductsAdapter.ViewHolder> {

 Branch--Rafi
    public interface OnProductActionListener {
        void onToggleAvailability(Product product, boolean isAvailable);
        void onDeleteProduct(Product product);
        void onProductClick(Product product);
    }

    private final List<Product> products = new ArrayList<>();
    private final OnProductActionListener listener;

    public MyProductsAdapter(OnProductActionListener listener) {

    public interface OnMyProductActionListener {
        void onToggleAvailability(Product product);
        void onEditProduct(Product product);
        void onDeleteProduct(Product product);
    }

    private final List<Product> products = new ArrayList<>();
    private final OnMyProductActionListener listener;

    public MyProductsAdapter(OnMyProductActionListener listener) {
 main
        this.listener = listener;
    }

    public void setProducts(List<Product> newProducts) {
 Branch--Rafi
        this.products.clear();
        if (newProducts != null) {
            this.products.addAll(newProducts);

        products.clear();
        if (newProducts != null) {
            products.addAll(newProducts);
 main
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyProductBinding binding = ItemMyProductBinding.inflate(
 Branch--Rafi
                LayoutInflater.from(parent.getContext()), parent, false
        );

                LayoutInflater.from(parent.getContext()), parent, false);
 main
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
 Branch--Rafi
        Product product = products.get(position);
        holder.bind(product);

        holder.bind(products.get(position));
 main
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMyProductBinding binding;

        ViewHolder(ItemMyProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Product product) {
 Branch--Rafi
            binding.tvProductName.setText(product.getProductName());
            binding.tvCategory.setText(product.getCategory() + " • " + product.getCondition());

            String priceText = "৳" + (int) product.getDailyRentalPrice() + "/day (Deposit: ৳" + (int) product.getSecurityDeposit() + ")";
            binding.tvProductPrice.setText(priceText);

            int resId = ProductImageHelper.getDrawableResByName(product.getProductName(), product.getCategory());
            binding.imgProduct.setImageResource(resId);

            binding.switchAvailability.setOnCheckedChangeListener(null);
            binding.switchAvailability.setChecked(product.isAvailable());
            binding.tvStatusLabel.setText(product.isAvailable() ? "Available for borrowing" : "Currently Unavailable");

            binding.switchAvailability.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (listener != null) {
                    listener.onToggleAvailability(product, isChecked);
                }
            });

            binding.btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteProduct(product);
                }
            });

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onProductClick(product);
                }

            binding.tvProductName.setText(product.getName());
            binding.tvPrice.setText(CurrencyUtils.format(product.getDailyRentalPrice()) + "/day");
            binding.tvCategoryCondition.setText(product.getCategory() + " • " + product.getCondition() + " condition");

            binding.ivProductImage.setImageResource(ProductImageHelper.getDrawableResForProduct(product));

            if (product.isAvailable()) {
                binding.tvStatusBadge.setText("Available");
                binding.tvStatusBadge.setBackgroundResource(R.drawable.badge_pill_available);
                binding.tvStatusBadge.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.success));
                binding.btnToggleAvailability.setText("Mark as Rented");
            } else {
                binding.tvStatusBadge.setText("Currently Rented");
                binding.tvStatusBadge.setBackgroundResource(R.drawable.badge_pill_rented);
                binding.tvStatusBadge.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.muted));
                binding.btnToggleAvailability.setText("Mark as Available");
            }

            binding.btnToggleAvailability.setOnClickListener(v -> {
                if (listener != null) listener.onToggleAvailability(product);
            });

            binding.btnEditProduct.setOnClickListener(v -> {
                if (listener != null) listener.onEditProduct(product);
            });

            binding.btnDeleteProduct.setOnClickListener(v -> {
                if (listener != null) listener.onDeleteProduct(product);
 main
            });
        }
    }
}
