package com.borrowbox.app.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.borrowbox.app.databinding.ItemProductBinding;
import com.borrowbox.app.model.PriceUnit;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.util.CurrencyUtils;

public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ProductViewHolder> {

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    private final OnProductClickListener listener;

    public ProductAdapter(OnProductClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<Product> DIFF_CALLBACK = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getName().equals(newItem.getName())
                    && oldItem.getPrice() == newItem.getPrice()
                    && oldItem.isAvailable() == newItem.isAvailable();
        }
    };

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductBinding binding;

        public ProductViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION && listener != null) {
                    listener.onProductClick(getItem(pos));
                }
            });
        }

        public void bind(Product product) {
            binding.tvProductName.setText(product.getName());
            String unit = product.getPriceUnit() == PriceUnit.DAY ? "/day" : "/hour";
            binding.tvPrice.setText(CurrencyUtils.format(product.getPrice()) + unit);
            binding.tvCategory.setText(product.getCategory());

            // Realistic seeded simulated distance and owner trust badges
            int pseudoDist = (int) (100 + ((product.getId() * 67) % 450));
            binding.tvDistance.setText("📍 " + pseudoDist + " m away");

            int pseudoTrust = (int) (80 + ((product.getId() * 7) % 19));
            binding.tvTrustScore.setText("⭐ Trust " + pseudoTrust);

            if (product.isAvailable()) {
                binding.tvAvailability.setText("Available Now");
                binding.tvAvailability.setTextColor(androidx.core.content.ContextCompat.getColor(binding.getRoot().getContext(), com.borrowbox.app.R.color.success));
            } else {
                binding.tvAvailability.setText("Currently Rented");
                binding.tvAvailability.setTextColor(androidx.core.content.ContextCompat.getColor(binding.getRoot().getContext(), com.borrowbox.app.R.color.muted));
            }
        }
    }
}
