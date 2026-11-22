package com.borrowbox.app.ui.product;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.borrowbox.app.databinding.ItemMyProductBinding;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.util.ProductImageHelper;
import java.util.ArrayList;
import java.util.List;

public class MyProductsAdapter extends RecyclerView.Adapter<MyProductsAdapter.ViewHolder> {

    public interface OnProductActionListener {
        void onToggleAvailability(Product product, boolean isAvailable);
        void onDeleteProduct(Product product);
        void onProductClick(Product product);
    }

    private final List<Product> products = new ArrayList<>();
    private final OnProductActionListener listener;

    public MyProductsAdapter(OnProductActionListener listener) {
        this.listener = listener;
    }

    public void setProducts(List<Product> newProducts) {
        this.products.clear();
        if (newProducts != null) {
            this.products.addAll(newProducts);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyProductBinding binding = ItemMyProductBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
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
            });
        }
    }
}
