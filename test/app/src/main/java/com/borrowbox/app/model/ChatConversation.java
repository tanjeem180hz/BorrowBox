package com.borrowbox.app.model;

import androidx.annotation.NonNull;

public class ChatConversation {

    private long productId;
    @NonNull
    private String productName = "";
    private long otherUserId;
    @NonNull
    private String otherUserName = "";
    @NonNull
    private String lastMessage = "";
    private long lastTimestamp;
    private int unreadCount;
    private boolean isLastMessageFromMe;
    private boolean isLike;

    public ChatConversation(long productId, @NonNull String productName, long otherUserId,
                            @NonNull String otherUserName, @NonNull String lastMessage,
                            long lastTimestamp, int unreadCount, boolean isLastMessageFromMe,
                            boolean isLike) {
        this.productId = productId;
        this.productName = productName != null ? productName : "";
        this.otherUserId = otherUserId;
        this.otherUserName = otherUserName != null ? otherUserName : "";
        this.lastMessage = lastMessage != null ? lastMessage : "";
        this.lastTimestamp = lastTimestamp;
        this.unreadCount = unreadCount;
        this.isLastMessageFromMe = isLastMessageFromMe;
        this.isLike = isLike;
    }

    public long getProductId() { return productId; }
    @NonNull public String getProductName() { return productName; }
    public long getOtherUserId() { return otherUserId; }
    @NonNull public String getOtherUserName() { return otherUserName; }
    @NonNull public String getLastMessage() { return lastMessage; }
    public long getLastTimestamp() { return lastTimestamp; }
    public int getUnreadCount() { return unreadCount; }
    public boolean isLastMessageFromMe() { return isLastMessageFromMe; }
    public boolean isLike() { return isLike; }
}
