package com.borrowbox.app.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.borrowbox.app.R;
import com.borrowbox.app.model.Dispute;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class AdminDisputeAdapter extends RecyclerView.Adapter<AdminDisputeAdapter.ViewHolder> {

    public interface OnDisputeActionListener {
        void onFullRefund(Dispute dispute);
        void onPenalize(Dispute dispute);
        void onDismiss(Dispute dispute);
    }

    private final List<Dispute> items = new ArrayList<>();
    private final OnDisputeActionListener listener;

    public AdminDisputeAdapter(OnDisputeActionListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Dispute> newItems) {
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_dispute, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dispute d = items.get(position);

        holder.tvType.setText(d.getDisputeType());
        holder.tvDeposit.setText("Escrow Deposit: ৳" + ((int) d.getDepositAmount()));
        holder.tvStatus.setText(d.getStatus());

        if (Dispute.STATUS_RESOLVED.equals(d.getStatus())) {
            holder.tvStatus.setTextColor(0xFF10B981);
            holder.layoutActions.setVisibility(View.GONE);
            holder.tvVerdict.setVisibility(View.VISIBLE);
            holder.tvVerdict.setText("Verdict: " + d.getAdminVerdict());
        } else if (Dispute.STATUS_DISMISSED.equals(d.getStatus())) {
            holder.tvStatus.setTextColor(0xFF94A3B8);
            holder.layoutActions.setVisibility(View.GONE);
            holder.tvVerdict.setVisibility(View.VISIBLE);
            holder.tvVerdict.setText("Dismissed: " + d.getAdminVerdict());
        } else {
            holder.tvStatus.setTextColor(0xFFF59E0B);
            holder.layoutActions.setVisibility(View.VISIBLE);
            holder.tvVerdict.setVisibility(View.GONE);
        }

        holder.tvProduct.setText("Item: " + d.getProductTitle());
        holder.tvParties.setText("Reporter: " + d.getReporterName() + " • Against: " + d.getReportedStudentName());
        holder.tvDesc.setText(d.getDescription());

        holder.btnFullRefund.setOnClickListener(v -> {
            if (listener != null) listener.onFullRefund(d);
        });

        holder.btnPenalize.setOnClickListener(v -> {
            if (listener != null) listener.onPenalize(d);
        });

        holder.btnDismiss.setOnClickListener(v -> {
            if (listener != null) listener.onDismiss(d);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvType;
        final TextView tvDeposit;
        final TextView tvStatus;
        final TextView tvProduct;
        final TextView tvParties;
        final TextView tvDesc;
        final TextView tvVerdict;
        final LinearLayout layoutActions;
        final MaterialButton btnDismiss;
        final MaterialButton btnPenalize;
        final MaterialButton btnFullRefund;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tv_dispute_type);
            tvDeposit = itemView.findViewById(R.id.tv_dispute_deposit);
            tvStatus = itemView.findViewById(R.id.tv_dispute_status);
            tvProduct = itemView.findViewById(R.id.tv_dispute_product);
            tvParties = itemView.findViewById(R.id.tv_dispute_parties);
            tvDesc = itemView.findViewById(R.id.tv_dispute_desc);
            tvVerdict = itemView.findViewById(R.id.tv_dispute_verdict);
            layoutActions = itemView.findViewById(R.id.layout_dispute_actions);
            btnDismiss = itemView.findViewById(R.id.btn_dismiss_dispute);
            btnPenalize = itemView.findViewById(R.id.btn_penalize_deposit);
            btnFullRefund = itemView.findViewById(R.id.btn_refund_borrower);
        }
    }
}
