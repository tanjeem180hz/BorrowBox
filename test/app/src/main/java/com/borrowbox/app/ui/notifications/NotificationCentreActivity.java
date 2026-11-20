package com.borrowbox.app.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.borrowbox.app.MainActivity;
import com.borrowbox.app.R;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.NotificationRepository;
import com.borrowbox.app.model.NotificationModel;
import com.borrowbox.app.ui.condition.ConditionComparisonActivity;
import com.borrowbox.app.ui.dispute.ReportStatusActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.List;

public class NotificationCentreActivity extends AppCompatActivity {

    private TextView tvUnreadBadge;
    private MaterialButton btnMarkAllRead;
    private ChipGroup chipGroupFilter;
    private RecyclerView rvNotifications;

    private NotificationRepository notificationRepository;
    private SessionManager sessionManager;
    private NotificationAdapter adapter;
    private final List<NotificationModel> fullList = new ArrayList<>();
    private long loggedInUserId = 1L;

    public static Intent createIntent(Context context) {
        return new Intent(context, NotificationCentreActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_centre);

        notificationRepository = NotificationRepository.getInstance(this);
        sessionManager = new SessionManager(this);
        loggedInUserId = sessionManager.getLoggedInUserId();

        initViews();
        setupToolbar();
        loadNotifications();
        seedMockNotificationsIfEmpty();
    }

    private void initViews() {
        tvUnreadBadge = findViewById(R.id.tvUnreadBadge);
        btnMarkAllRead = findViewById(R.id.btnMarkAllRead);
        chipGroupFilter = findViewById(R.id.chipGroupFilter);
        rvNotifications = findViewById(R.id.rvNotifications);

        adapter = new NotificationAdapter(this, new ArrayList<>(), new NotificationAdapter.OnNotificationClickListener() {
            @Override
            public void onItemClick(NotificationModel notification) {
                notificationRepository.markAsRead(notification.getNotifId());
                navigateToDeepLink(notification.getDeepLinkScreen());
            }

            @Override
            public void onDeleteClick(NotificationModel notification) {
                notificationRepository.deleteNotification(notification.getNotifId());
                Toast.makeText(NotificationCentreActivity.this, "Notification deleted", Toast.LENGTH_SHORT).show();
            }
        });

        rvNotifications.setAdapter(adapter);

        btnMarkAllRead.setOnClickListener(v -> {
            notificationRepository.markAllAsRead(loggedInUserId);
            Toast.makeText(this, "All notifications marked as read", Toast.LENGTH_SHORT).show();
        });

        chipGroupFilter.setOnCheckedStateChangeListener((group, checkedIds) -> filterList());
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void loadNotifications() {
        notificationRepository.getNotificationsForUserLive(loggedInUserId).observe(this, notifications -> {
            fullList.clear();
            if (notifications != null) {
                fullList.addAll(notifications);
            }
            filterList();
        });

        notificationRepository.getUnreadCountLive(loggedInUserId).observe(this, count -> {
            int unread = count != null ? count : 0;
            tvUnreadBadge.setText("Unread: " + unread);
        });
    }

    private void seedMockNotificationsIfEmpty() {
        notificationRepository.sendNotification(
                loggedInUserId,
                "⏱️ 15-Min Return Reminder",
                "Your rental for Laptop Charger expires in 15 minutes. Prepare for handover.",
                NotificationModel.DEEP_LINK_CONDITION_COMPARISON
        );

        notificationRepository.sendNotification(
                loggedInUserId,
                "⚖️ Admin Dispute Verdict",
                "Verdict issued for Dispute #1: Full deposit refunded.",
                NotificationModel.DEEP_LINK_REPORT_STATUS
        );
    }

    private void filterList() {
        int checkedId = chipGroupFilter.getCheckedChipId();
        List<NotificationModel> filtered = new ArrayList<>();

        for (NotificationModel item : fullList) {
            if (checkedId == R.id.chipFilterReminders) {
                if (item.getTitle().contains("Reminder") || item.getTitle().contains("Overdue")) {
                    filtered.add(item);
                }
            } else if (checkedId == R.id.chipFilterVerdicts) {
                if (item.getTitle().contains("Verdict") || item.getTitle().contains("Dispute")) {
                    filtered.add(item);
                }
            } else {
                filtered.add(item);
            }
        }

        adapter.setItems(filtered);
    }

    private void navigateToDeepLink(String deepLink) {
        Intent intent;
        if (NotificationModel.DEEP_LINK_CONDITION_COMPARISON.equals(deepLink)) {
            intent = ConditionComparisonActivity.createIntent(this, 1L);
        } else if (NotificationModel.DEEP_LINK_REPORT_STATUS.equals(deepLink)) {
            intent = ReportStatusActivity.createIntent(this, 1L);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
    }

    private static class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotifViewHolder> {

        public interface OnNotificationClickListener {
            void onItemClick(NotificationModel notification);
            void onDeleteClick(NotificationModel notification);
        }

        private final Context context;
        private final List<NotificationModel> items;
        private final OnNotificationClickListener listener;

        NotificationAdapter(Context context, List<NotificationModel> items, OnNotificationClickListener listener) {
            this.context = context;
            this.items = items;
            this.listener = listener;
        }

        void setItems(List<NotificationModel> newItems) {
            this.items.clear();
            if (newItems != null) {
                this.items.addAll(newItems);
            }
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public NotifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            return new NotifViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NotifViewHolder holder, int position) {
            NotificationModel item = items.get(position);

            holder.tvNotifTitle.setText(item.getTitle());
            holder.tvNotifBody.setText(item.getBody());

            CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(
                    item.getSentAt(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
            holder.tvNotifTime.setText(relativeTime);

            holder.vUnreadDot.setVisibility(item.isRead() ? View.GONE : View.VISIBLE);

            holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
            holder.btnDeleteNotif.setOnClickListener(v -> listener.onDeleteClick(item));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class NotifViewHolder extends RecyclerView.ViewHolder {
            View vUnreadDot;
            ImageView ivNotifIcon;
            TextView tvNotifTitle;
            TextView tvNotifBody;
            TextView tvNotifTime;
            ImageButton btnDeleteNotif;

            NotifViewHolder(View itemView) {
                super(itemView);
                vUnreadDot = itemView.findViewById(R.id.vUnreadDot);
                ivNotifIcon = itemView.findViewById(R.id.ivNotifIcon);
                tvNotifTitle = itemView.findViewById(R.id.tvNotifTitle);
                tvNotifBody = itemView.findViewById(R.id.tvNotifBody);
                tvNotifTime = itemView.findViewById(R.id.tvNotifTime);
                btnDeleteNotif = itemView.findViewById(R.id.btnDeleteNotif);
            }
        }
    }
}
