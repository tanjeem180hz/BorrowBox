package com.borrowbox.app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "chat_messages")
public class ChatMessage {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long productId;
    private long senderId;
    private long receiverId;
    @NonNull
    private String text = "";
    private long timestamp;
    private boolean isFromMe;
    @NonNull
    private String status = "SENT"; // SENT, DELIVERED, SEEN
    private boolean isLike;
    @NonNull
    private String attachmentType = "TEXT"; // TEXT, IMAGE, AUDIO
    private String attachmentUri = "";

    public ChatMessage() {
    }

    @Ignore
    public ChatMessage(long id, long productId, long senderId, long receiverId,
                       @NonNull String text, long timestamp, boolean isFromMe,
                       @NonNull String status, boolean isLike) {
        this(id, productId, senderId, receiverId, text, timestamp, isFromMe, status, isLike, "TEXT", "");
    }

    @Ignore
    public ChatMessage(long id, long productId, long senderId, long receiverId,
                       @NonNull String text, long timestamp, boolean isFromMe,
                       @NonNull String status, boolean isLike,
                       @NonNull String attachmentType, String attachmentUri) {
        this.id = id;
        this.productId = productId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text != null ? text : "";
        this.timestamp = timestamp;
        this.isFromMe = isFromMe;
        this.status = status != null ? status : "SENT";
        this.isLike = isLike;
        this.attachmentType = attachmentType != null ? attachmentType : "TEXT";
        this.attachmentUri = attachmentUri != null ? attachmentUri : "";
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getProductId() { return productId; }
    public void setProductId(long productId) { this.productId = productId; }

    public long getSenderId() { return senderId; }
    public void setSenderId(long senderId) { this.senderId = senderId; }

    public long getReceiverId() { return receiverId; }
    public void setReceiverId(long receiverId) { this.receiverId = receiverId; }

    @NonNull
    public String getText() { return text; }
    public void setText(@NonNull String text) { this.text = text; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public boolean isFromMe() { return isFromMe; }
    public void setFromMe(boolean fromMe) { isFromMe = fromMe; }

    @NonNull
    public String getStatus() { return status; }
    public void setStatus(@NonNull String status) { this.status = status; }

    public boolean isLike() { return isLike; }
    public void setLike(boolean like) { isLike = like; }

    @NonNull
    public String getAttachmentType() { return attachmentType; }
    public void setAttachmentType(@NonNull String attachmentType) { this.attachmentType = attachmentType; }

    public String getAttachmentUri() { return attachmentUri; }
    public void setAttachmentUri(String attachmentUri) { this.attachmentUri = attachmentUri != null ? attachmentUri : ""; }
}
