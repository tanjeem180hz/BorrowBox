package com.borrowbox.app.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.borrowbox.app.R;
import com.borrowbox.app.databinding.ItemChatMessageBinding;
import com.borrowbox.app.model.ChatMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatMessageAdapter extends ListAdapter<ChatMessage, ChatMessageAdapter.ChatViewHolder> {

    private String ownerAvatarLetter = "S";
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());

    public ChatMessageAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setOwnerAvatarLetter(String letter) {
        if (letter != null && !letter.isEmpty()) {
            this.ownerAvatarLetter = letter.substring(0, 1).toUpperCase(Locale.ROOT);
        }
    }

    private static final DiffUtil.ItemCallback<ChatMessage> DIFF_CALLBACK = new DiffUtil.ItemCallback<ChatMessage>() {
        @Override
        public boolean areItemsTheSame(@NonNull ChatMessage oldItem, @NonNull ChatMessage newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ChatMessage oldItem, @NonNull ChatMessage newItem) {
            return oldItem.getText().equals(newItem.getText())
                    && oldItem.getStatus().equals(newItem.getStatus())
                    && oldItem.isLike() == newItem.isLike()
                    && oldItem.getTimestamp() == newItem.getTimestamp();
        }
    };

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChatMessageBinding binding = ItemChatMessageBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        private final ItemChatMessageBinding binding;

        public ChatViewHolder(ItemChatMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ChatMessage message) {
            Context context = itemView.getContext();
            String timeStr = timeFormat.format(new Date(message.getTimestamp()));

            if (message.isFromMe()) {
                // Sent by current student (Right aligned, Messenger Blue)
                binding.layoutSent.setVisibility(View.VISIBLE);
                binding.layoutReceived.setVisibility(View.GONE);

                if (message.isLike()) {
                    binding.imgSentLike.setVisibility(View.VISIBLE);
                    binding.tvSentText.setVisibility(View.GONE);
                } else {
                    binding.imgSentLike.setVisibility(View.GONE);
                    binding.tvSentText.setVisibility(View.VISIBLE);
                    binding.tvSentText.setText(message.getText());
                }

                binding.tvSentTime.setText(timeStr);

                String status = message.getStatus();
                if ("SEEN".equalsIgnoreCase(status)) {
                    binding.tvSentStatus.setText(" • " + context.getString(R.string.messenger_seen));
                    binding.tvSeenAvatar.setVisibility(View.VISIBLE);
                    binding.tvSeenAvatar.setText(ownerAvatarLetter);
                } else if ("DELIVERED".equalsIgnoreCase(status)) {
                    binding.tvSentStatus.setText(" • " + context.getString(R.string.messenger_delivered));
                    binding.tvSeenAvatar.setVisibility(View.GONE);
                } else {
                    binding.tvSentStatus.setText(" • " + context.getString(R.string.messenger_sent));
                    binding.tvSeenAvatar.setVisibility(View.GONE);
                }
            } else {
                // Received from owner (Left aligned, soft neutral bubble)
                binding.layoutReceived.setVisibility(View.VISIBLE);
                binding.layoutSent.setVisibility(View.GONE);

                binding.tvReceivedAvatar.setText(ownerAvatarLetter);

                if (message.isLike()) {
                    binding.imgReceivedLike.setVisibility(View.VISIBLE);
                    binding.tvReceivedText.setVisibility(View.GONE);
                } else {
                    binding.imgReceivedLike.setVisibility(View.GONE);
                    binding.tvReceivedText.setVisibility(View.VISIBLE);
                    binding.tvReceivedText.setText(message.getText());
                }

                binding.tvReceivedTime.setText(timeStr);
            }
        }
    }
}
