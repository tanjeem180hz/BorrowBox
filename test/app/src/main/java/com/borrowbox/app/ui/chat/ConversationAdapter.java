package com.borrowbox.app.ui.chat;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.borrowbox.app.R;
import com.borrowbox.app.databinding.ItemConversationBinding;
import com.borrowbox.app.model.ChatConversation;

public class ConversationAdapter extends ListAdapter<ChatConversation, ConversationAdapter.ViewHolder> {

    public interface OnConversationClickListener {
        void onConversationClick(ChatConversation conversation);
    }

    private final OnConversationClickListener listener;

    private static final int[] AVATAR_COLORS = {
            Color.parseColor("#0084FF"),
            Color.parseColor("#8B5CF6"),
            Color.parseColor("#10B981"),
            Color.parseColor("#F59E0B"),
            Color.parseColor("#EC4899"),
            Color.parseColor("#3B82F6")
    };

    public ConversationAdapter(OnConversationClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<ChatConversation> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ChatConversation>() {
                @Override
                public boolean areItemsTheSame(@NonNull ChatConversation oldItem, @NonNull ChatConversation newItem) {
                    return oldItem.getProductId() == newItem.getProductId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull ChatConversation oldItem, @NonNull ChatConversation newItem) {
                    return oldItem.getLastTimestamp() == newItem.getLastTimestamp()
                            && oldItem.getLastMessage().equals(newItem.getLastMessage())
                            && oldItem.getUnreadCount() == newItem.getUnreadCount();
                }
            };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemConversationBinding binding = ItemConversationBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemConversationBinding binding;

        ViewHolder(ItemConversationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ChatConversation conv) {
            Context context = itemView.getContext();

            // Avatar letter & color
            String name = conv.getOtherUserName();
            String initial = (!name.isEmpty()) ? String.valueOf(name.charAt(0)).toUpperCase(java.util.Locale.ROOT) : "S";
            binding.tvAvatar.setText(initial);
            int colorIndex = (int) (Math.abs(conv.getOtherUserId()) % AVATAR_COLORS.length);
            binding.tvAvatar.getBackground().setTint(AVATAR_COLORS[colorIndex]);

            // Peer name
            binding.tvPeerName.setText(name);

            // Product tag
            binding.tvProductTag.setText("📦 " + conv.getProductName());

            // Relative timestamp
            long now = System.currentTimeMillis();
            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    conv.getLastTimestamp(), now, DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE
            );
            binding.tvTimestamp.setText(timeAgo);

            // Last message snippet
            String lastText;
            if (conv.isLike()) {
                lastText = context.getString(R.string.messenger_thumbs_sent);
            } else {
                lastText = conv.getLastMessage();
            }

            if (conv.isLastMessageFromMe()) {
                binding.tvLastMessage.setText(context.getString(R.string.messenger_you_prefix, lastText));
                binding.tvLastMessage.setTextColor(context.getColor(R.color.muted));
            } else {
                binding.tvLastMessage.setText(lastText);
                if (conv.getUnreadCount() > 0) {
                    binding.tvLastMessage.setTextColor(context.getColor(R.color.ink));
                } else {
                    binding.tvLastMessage.setTextColor(context.getColor(R.color.muted));
                }
            }

            // Unread Badge
            if (conv.getUnreadCount() > 0) {
                binding.tvUnreadBadge.setVisibility(View.VISIBLE);
                binding.tvUnreadBadge.setText(String.valueOf(conv.getUnreadCount()));
            } else {
                binding.tvUnreadBadge.setVisibility(View.GONE);
            }

            // Click listener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onConversationClick(conv);
                }
            });
        }
    }
}
