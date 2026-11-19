package com.borrowbox.app.ui.rentals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.borrowbox.app.R;
import com.borrowbox.app.databinding.ItemRentalBinding;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.model.RentalStatus;
import com.borrowbox.app.util.CurrencyUtils;

public class RentalsAdapter extends ListAdapter<Rental, RentalsAdapter.RentalViewHolder> {

    public interface OnRentalActionListener {
        void onRentalClick(Rental rental);
        void onAccept(Rental rental);
        void onReject(Rental rental);
    }

    private final OnRentalActionListener listener;
    private final boolean showOwnerActions;

    public RentalsAdapter(boolean showOwnerActions, OnRentalActionListener listener) {
        super(DIFF_CALLBACK);
        this.showOwnerActions = showOwnerActions;
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<Rental> DIFF_CALLBACK = new DiffUtil.ItemCallback<Rental>() {
        @Override
        public boolean areItemsTheSame(@NonNull Rental oldItem, @NonNull Rental newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Rental oldItem, @NonNull Rental newItem) {
            return oldItem.getStatus().equals(newItem.getStatus())
                    && oldItem.getTotalCost() == newItem.getTotalCost();
        }
    };

    @NonNull
    @Override
    public RentalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRentalBinding binding = ItemRentalBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new RentalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RentalViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class RentalViewHolder extends RecyclerView.ViewHolder {
        private final ItemRentalBinding binding;

        public RentalViewHolder(ItemRentalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Rental rental) {
            binding.tvRentalTitle.setText("Rental #" + rental.getId());
            binding.tvStatusBadge.setText(rental.getStatus().name());

            String unit = rental.getDurationUnit().name().toLowerCase(java.util.Locale.ROOT);
            binding.tvRentalDetails.setText(rental.getDurationValue() + " " + unit + "(s) • "
                    + CurrencyUtils.format(rental.getTotalCost()) + " • Deposit: "
                    + CurrencyUtils.format(rental.getDeposit()));

            if (rental.isUrgent()) {
                binding.tvUrgentIndicator.setVisibility(View.VISIBLE);
            } else {
                binding.tvUrgentIndicator.setVisibility(View.GONE);
            }

            // Status color styling
            if (rental.getStatus() == RentalStatus.ACTIVE) {
                binding.tvStatusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.brand_primary));
            } else if (rental.getStatus() == RentalStatus.OVERDUE || rental.getStatus() == RentalStatus.REJECTED) {
                binding.tvStatusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.danger));
            } else if (rental.getStatus() == RentalStatus.RETURNED || rental.getStatus() == RentalStatus.CONFIRMED) {
                binding.tvStatusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.success));
            }

            // Owner Action Buttons
            if (showOwnerActions && rental.getStatus() == RentalStatus.REQUESTED) {
                binding.layoutRequestActions.setVisibility(View.VISIBLE);
                binding.btnAccept.setOnClickListener(v -> {
                    if (listener != null) listener.onAccept(rental);
                });
                binding.btnReject.setOnClickListener(v -> {
                    if (listener != null) listener.onReject(rental);
                });
            } else {
                binding.layoutRequestActions.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onRentalClick(rental);
            });
        }
    }
}
