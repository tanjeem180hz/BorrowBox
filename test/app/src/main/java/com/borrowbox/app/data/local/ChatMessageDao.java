package com.borrowbox.app.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.borrowbox.app.model.ChatMessage;
import java.util.List;

@Dao
public interface ChatMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ChatMessage message);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ChatMessage> messages);

    @Update
    void update(ChatMessage message);

    @Query("SELECT * FROM chat_messages WHERE productId = :productId ORDER BY timestamp ASC")
    LiveData<List<ChatMessage>> getMessagesForProductLive(long productId);

    @Query("SELECT * FROM chat_messages WHERE productId = :productId ORDER BY timestamp ASC")
    List<ChatMessage> getMessagesForProductSync(long productId);

    @Query("SELECT * FROM chat_messages WHERE productId = :productId AND " +
           "((senderId = :user1 AND receiverId = :user2) OR (senderId = :user2 AND receiverId = :user1)) " +
           "ORDER BY timestamp ASC")
    LiveData<List<ChatMessage>> getConversationLive(long productId, long user1, long user2);

    @Query("SELECT * FROM chat_messages WHERE productId = :productId AND " +
           "((senderId = :user1 AND receiverId = :user2) OR (senderId = :user2 AND receiverId = :user1)) " +
           "ORDER BY timestamp ASC")
    List<ChatMessage> getConversationSync(long productId, long user1, long user2);

    @Query("SELECT * FROM chat_messages WHERE senderId = :userId OR receiverId = :userId ORDER BY timestamp DESC")
    LiveData<List<ChatMessage>> getAllMessagesForUserLive(long userId);

    @Query("SELECT * FROM chat_messages WHERE senderId = :userId OR receiverId = :userId ORDER BY timestamp DESC")
    List<ChatMessage> getAllMessagesForUserSync(long userId);

    @Query("SELECT COUNT(*) FROM chat_messages WHERE receiverId = :userId AND status != 'SEEN'")
    LiveData<Integer> getUnreadCountLive(long userId);

    @Query("UPDATE chat_messages SET status = 'SEEN' WHERE productId = :productId AND receiverId = :currentUserId AND status != 'SEEN'")
    void markMessagesAsSeen(long productId, long currentUserId);

    @Query("SELECT * FROM chat_messages WHERE productId = :productId ORDER BY timestamp DESC LIMIT 1")
    ChatMessage getLatestMessageForProduct(long productId);

    @Query("DELETE FROM chat_messages WHERE productId = :productId")
    void deleteConversation(long productId);
}
