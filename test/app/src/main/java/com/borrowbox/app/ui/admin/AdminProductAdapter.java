package com.borrowbox.app.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.borrowbox.app.R;
import com.borrowbox.app.model.Product;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class AdminProductAdapter extends RecyclerView.Adapter<AdminProductAdapter.ViewHolder> {

    public interface OnProductActionListener {
        void onToggleStatus(Product product);
    }

    private final List<Product> items = new ArrayList<>();
    private final OnProductActionListener listener;

    public AdminProductAdapter(OnProductActionListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Product> newItems) {
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product p = items.get(position);

        holder.tvTitle.setText(p.getName());
        holder.tvMeta.setText("Category: " + p.getCategory() + " • Rate: ৳" + p.getPrice() + "/" + p.getPriceUnit().name().toLowerCase());
        holder.tvDesc.setText(p.getDescription());

        if (p.isAvailable()) {
            holder.tvStatus.setText("Listed");
            holder.tvStatus.setTextColor(0xFF10B981);
            holder.btnToggleStatus.setText("Take Down Listing");
            holder.btnToggleStatus.setTextColor(0xFFEF4444);
        } else {
            holder.tvStatus.setText("Suspended");
            holder.tvStatus.setTextColor(0xFFEF4444);
            holder.btnToggleStatus.setText("Reinstate Listing");
            holder.btnToggleStatus.setTextColor(0xFF10B981);
        }

        holder.btnToggleStatus.setOnClickListener(v -> {
            if (listener != null) listener.onToggleStatus(p);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final TextView tvMeta;
        final TextView tvDesc;
        final TextView tvStatus;
        final MaterialButton btnToggleStatus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_product_title);
            tvMeta = itemView.findViewById(R.id.tv_product_meta);
            tvDesc = itemView.findViewById(R.id.tv_product_desc);
            tvStatus = itemView.findViewById(R.id.tv_product_status);
            btnToggleStatus = itemView.findViewById(R.id.btn_toggle_product_status);
        }
    }
}
