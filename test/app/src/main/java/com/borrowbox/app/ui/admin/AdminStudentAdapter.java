package com.borrowbox.app.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.borrowbox.app.R;
import com.borrowbox.app.model.Student;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class AdminStudentAdapter extends RecyclerView.Adapter<AdminStudentAdapter.ViewHolder> {

    public interface OnStudentActionListener {
        void onToggleVerify(Student student);
        void onToggleSuspend(Student student);
        void onResetTrust(Student student);
    }

    private final List<Student> items = new ArrayList<>();
    private final OnStudentActionListener listener;

    public AdminStudentAdapter(OnStudentActionListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Student> newItems) {
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student s = items.get(position);

        String initials = "";
        String[] parts = s.getName().split(" ");
        if (parts.length >= 2) {
            initials = ("" + parts[0].charAt(0) + parts[parts.length - 1].charAt(0)).toUpperCase();
        } else if (!s.getName().isEmpty()) {
            initials = s.getName().substring(0, Math.min(2, s.getName().length())).toUpperCase();
        }
        holder.tvAvatar.setText(initials);
        holder.tvName.setText(s.getName());

        holder.tvVerifiedBadge.setVisibility(s.isVerified() ? View.VISIBLE : View.GONE);

        holder.tvDetails.setText("ID: " + s.getStudentId() + " • " + s.getDepartment() + " • " + s.getEmail());

        if (s.isSuspended()) {
            holder.tvStatus.setText("SUSPENDED");
            holder.tvStatus.setTextColor(0xFFEF4444);
            holder.btnToggleSuspend.setText("Reinstate");
            holder.btnToggleSuspend.setTextColor(0xFF10B981);
        } else {
            holder.tvStatus.setText("Active");
            holder.tvStatus.setTextColor(0xFF10B981);
            holder.btnToggleSuspend.setText("Suspend");
            holder.btnToggleSuspend.setTextColor(0xFFEF4444);
        }

        holder.tvTrustScore.setText("Trust Score: " + s.getTrustScore() + "/100 • " + s.getCompletedRentals() + " Rentals");
        holder.tvPhone.setText(s.getPhone().isEmpty() ? "No Phone" : s.getPhone());

        holder.btnToggleVerify.setText(s.isVerified() ? "Unverify" : "Verify");
        holder.btnToggleVerify.setOnClickListener(v -> {
            if (listener != null) listener.onToggleVerify(s);
        });

        holder.btnToggleSuspend.setOnClickListener(v -> {
            if (listener != null) listener.onToggleSuspend(s);
        });

        holder.btnResetTrust.setOnClickListener(v -> {
            if (listener != null) listener.onResetTrust(s);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvAvatar;
        final TextView tvName;
        final TextView tvVerifiedBadge;
        final TextView tvDetails;
        final TextView tvStatus;
        final TextView tvTrustScore;
        final TextView tvPhone;
        final MaterialButton btnToggleVerify;
        final MaterialButton btnToggleSuspend;
        final MaterialButton btnResetTrust;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAvatar = itemView.findViewById(R.id.tv_student_avatar);
            tvName = itemView.findViewById(R.id.tv_student_name);
            tvVerifiedBadge = itemView.findViewById(R.id.tv_verified_badge);
            tvDetails = itemView.findViewById(R.id.tv_student_details);
            tvStatus = itemView.findViewById(R.id.tv_account_status);
            tvTrustScore = itemView.findViewById(R.id.tv_trust_score);
            tvPhone = itemView.findViewById(R.id.tv_student_phone);
            btnToggleVerify = itemView.findViewById(R.id.btn_toggle_verify);
            btnToggleSuspend = itemView.findViewById(R.id.btn_toggle_suspend);
            btnResetTrust = itemView.findViewById(R.id.btn_reset_trust);
        }
    }
}
