package com.borrowbox.app.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.borrowbox.app.data.local.AppDatabase;
import com.borrowbox.app.data.local.ChatMessageDao;
import com.borrowbox.app.data.local.ProductDao;
import com.borrowbox.app.data.local.StudentDao;
import com.borrowbox.app.model.ChatConversation;
import com.borrowbox.app.model.ChatMessage;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.model.Student;
import com.borrowbox.app.util.AppExecutors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Real-life persistent chat repository backed by Room SQLite.
 * Supports multi-account two-way conversations, live Room observations, and simulated peer responses.
 */
public class ChatMessageRepository {

    private static volatile ChatMessageRepository instance;

    private final ChatMessageDao chatMessageDao;
    private final StudentDao studentDao;
    private final ProductDao productDao;
    private final AppExecutors executors;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private final Map<Long, MutableLiveData<Boolean>> typingLiveMap = new HashMap<>();

    public ChatMessageRepository(@NonNull ChatMessageDao chatMessageDao,
                                 @NonNull StudentDao studentDao,
                                 @NonNull ProductDao productDao,
                                 @NonNull AppExecutors executors) {
        this.chatMessageDao = chatMessageDao;
        this.studentDao = studentDao;
        this.productDao = productDao;
        this.executors = executors;
    }

    public static ChatMessageRepository getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (ChatMessageRepository.class) {
                if (instance == null) {
                    AppDatabase db = AppDatabase.getInstance(context);
                    instance = new ChatMessageRepository(
                            db.chatMessageDao(),
                            db.studentDao(),
                            db.productDao(),
                            AppExecutors.getInstance()
                    );
                }
            }
        }
        return instance;
    }

    /**
     * Returns LiveData of chat messages for a product, automatically marking messages
     * as 'from me' or 'from other' based on currently logged in user ID.
     */
    public LiveData<List<ChatMessage>> getMessagesLive(long productId, long currentUserId,
                                                       long otherUserId, String productName,
                                                       boolean isBengali) {
        // Mark existing messages from other user as SEEN
        executors.diskIO().execute(() -> {
            chatMessageDao.markMessagesAsSeen(productId, currentUserId);
            List<ChatMessage> existing = chatMessageDao.getMessagesForProductSync(productId);
            if (existing == null || existing.isEmpty()) {
                // Seed initial greeting message from the lender
                long now = System.currentTimeMillis() - (15 * 60 * 1000L);
                String welcome = isBengali
                        ? "আসসালামু আলাইকুম! " + (productName != null ? productName : "সামগ্রীটি") + " সম্পর্কে কোনো তথ্য জানার থাকলে সরাসরি মেসেজ দিন।"
                        : "Hello! If you need to borrow this " + (productName != null ? productName : "item") + " or have any questions, feel free to ask!";

                ChatMessage seed = new ChatMessage(
                        0,
                        productId,
                        otherUserId,
                        currentUserId,
                        welcome,
                        now,
                        false,
                        "SEEN",
                        false,
                        "TEXT",
                        ""
                );
                chatMessageDao.insert(seed);
            }
        });

        LiveData<List<ChatMessage>> roomLive = chatMessageDao.getMessagesForProductLive(productId);

        return Transformations.map(roomLive, list -> {
            List<ChatMessage> result = new ArrayList<>();
            if (list != null) {
                for (ChatMessage m : list) {
                    m.setFromMe(m.getSenderId() == currentUserId);
                    result.add(m);
                }
            }
            return result;
        });
    }

    public synchronized LiveData<Boolean> getTypingLive(long productId) {
        if (!typingLiveMap.containsKey(productId)) {
            typingLiveMap.put(productId, new MutableLiveData<>(false));
        }
        return typingLiveMap.get(productId);
    }

    public void setTyping(long productId, boolean isTyping) {
        MutableLiveData<Boolean> typing = typingLiveMap.get(productId);
        if (typing != null) {
            typing.postValue(isTyping);
        }
    }

    /**
     * Sends a chat message, saves it permanently to SQLite, and updates delivery receipts.
     */
    public void sendMessage(long productId, long senderId, long receiverId,
                            String text, boolean isLike, String productName,
                            boolean isBengali, String attachmentType, String attachmentUri) {
        ChatMessage myMsg = new ChatMessage(
                0,
                productId,
                senderId,
                receiverId,
                text,
                System.currentTimeMillis(),
                true,
                "SENT",
                isLike,
                attachmentType != null ? attachmentType : "TEXT",
                attachmentUri != null ? attachmentUri : ""
        );

        executors.diskIO().execute(() -> {
            long insertedId = chatMessageDao.insert(myMsg);
            myMsg.setId(insertedId);

            // Step 1: Status becomes DELIVERED after 400ms
            mainHandler.postDelayed(() -> executors.diskIO().execute(() -> {
                myMsg.setStatus("DELIVERED");
                chatMessageDao.update(myMsg);
            }), 400);

            // Step 2: Status becomes SEEN after 900ms and owner starts typing
            mainHandler.postDelayed(() -> {
                executors.diskIO().execute(() -> {
                    myMsg.setStatus("SEEN");
                    chatMessageDao.update(myMsg);
                });
                setTyping(productId, true);
            }, 900);

            // Step 3: Owner automated reply after 2.0s
            mainHandler.postDelayed(() -> {
                setTyping(productId, false);
                String replyText = generateOwnerReply(text, isLike, productName, isBengali, attachmentType);
                ChatMessage ownerReply = new ChatMessage(
                        0,
                        productId,
                        receiverId,
                        senderId,
                        replyText,
                        System.currentTimeMillis(),
                        false,
                        "SEEN",
                        isLike,
                        "TEXT",
                        ""
                );
                executors.diskIO().execute(() -> chatMessageDao.insert(ownerReply));
            }, 2000);
        });
    }

    public void sendMessage(long productId, long senderId, long receiverId,
                            String text, boolean isLike, String productName,
                            boolean isBengali) {
        sendMessage(productId, senderId, receiverId, text, isLike, productName, isBengali, "TEXT", "");
    }

    /**
     * Returns a LiveData list of all active conversations for the current student's Messenger Inbox.
     */
    public LiveData<List<ChatConversation>> getInboxConversationsLive(long currentUserId) {
        MediatorLiveData<List<ChatConversation>> mediator = new MediatorLiveData<>();
        LiveData<List<ChatMessage>> source = chatMessageDao.getAllMessagesForUserLive(currentUserId);

        mediator.addSource(source, messages -> executors.diskIO().execute(() -> {
            Map<Long, ChatConversation> conversationMap = new LinkedHashMap<>();
            if (messages != null && !messages.isEmpty()) {
                for (ChatMessage msg : messages) {
                    long pId = msg.getProductId();
                    if (!conversationMap.containsKey(pId)) {
                        long otherUserId = (msg.getSenderId() == currentUserId) ? msg.getReceiverId() : msg.getSenderId();

                        Student otherStudent = studentDao.getStudentById(otherUserId);
                        String otherName = otherStudent != null ? otherStudent.getName() : "Campus Peer";

                        Product product = productDao.getProductById(pId);
                        String pName = product != null ? product.getName() : "Campus Equipment";

                        boolean isFromMe = (msg.getSenderId() == currentUserId);
                        int unread = (!isFromMe && !"SEEN".equals(msg.getStatus())) ? 1 : 0;

                        ChatConversation conv = new ChatConversation(
                                pId,
                                pName,
                                otherUserId,
                                otherName,
                                msg.getText(),
                                msg.getTimestamp(),
                                unread,
                                isFromMe,
                                msg.isLike()
                        );
                        conversationMap.put(pId, conv);
                    }
                }
            }
            mediator.postValue(new ArrayList<>(conversationMap.values()));
        }));

        return mediator;
    }

    /**
     * Returns total unread messages count for badge indicators.
     */
    public LiveData<Integer> getUnreadCountLive(long currentUserId) {
        return chatMessageDao.getUnreadCountLive(currentUserId);
    }

    private String generateOwnerReply(String userMessage, boolean isLike, String productName,
                                      boolean isBengali, String attachmentType) {
        if ("IMAGE".equalsIgnoreCase(attachmentType)) {
            return isBengali
                    ? "ছবিটি পেয়েছি, ধন্যবাদ! জিনিসটির কন্ডিশন একদম পরিষ্কার। আপনি কখন পিকআপ করতে চান?"
                    : "Received the photo, thanks! Everything looks in great shape. When would you like to pick it up?";
        }
        if ("AUDIO".equalsIgnoreCase(attachmentType)) {
            return isBengali
                    ? "ভয়েস মেসেজটি শুনেছি। কোনো সমস্যা নেই, আপনি আসার আগে কল অথবা মেসেজ দিলেই দেখা করব।"
                    : "Heard your voice note! No problem at all, just text or call me before meeting.";
        }
        if (isLike) {
            return isBengali
                    ? "👍 দারুণ! আপনার লাগলে অ্যাপ থেকে 'Request to Borrow' চাপলেই আমি হ্যান্ডওভার নিশ্চিত করব।"
                    : "👍 Sounds great! Feel free to send a borrow request whenever you are ready for handover.";
        }

        String lower = userMessage != null ? userMessage.toLowerCase(java.util.Locale.ROOT) : "";

        if (lower.contains("available") || lower.contains("ধার") || lower.contains("আছে") || lower.contains("পাওয়া")) {
            return isBengali
                    ? "জি ভাইয়া, এটি সম্পূর্ণ রেডি এবং ভালো কন্ডিশনে আছে। আপনি চাইলে আজই ড্যাফোডিল ক্যাম্পাস থেকে নিতে পারেন।"
                    : "Yes! It is completely available and in great condition. You can collect it today on campus.";
        }

        if (lower.contains("meet") || lower.contains("location") || lower.contains("কোথায়") || lower.contains("লাইব্রেরি") || lower.contains("দেখা")) {
            return isBengali
                    ? "আমরা ডিআইইউ সেন্ট্রাল লাইব্রেরি বা সিএসই বিল্ডিং এর নিচে দেখা করতে পারি। আপনি আসার আগে জানাবেন।"
                    : "We can meet at the DIU Central Library entrance or CSE Building ground floor. Let me know when you arrive!";
        }

        if (lower.contains("now") || lower.contains("এখন") || lower.contains("তাড়াতাড়ি") || lower.contains("urgent") || lower.contains("জরুরি")) {
            return isBengali
                    ? "হ্যাঁ, এখনই নিতে পারবেন! আমি ক্যাম্পাসে আছি। আপনি ১০-১৫ মিনিটের মধ্যে চলে আসুন।"
                    : "Sure, you can pick it up right now! I'm on campus. Feel free to come over in 10-15 minutes.";
        }

        if (lower.contains("price") || lower.contains("cost") || lower.contains("টাকা") || lower.contains("ভাড়া") || lower.contains("দাম")) {
            return isBengali
                    ? "রেটটি ফিক্সড রাখা হয়েছে যাতে সকল সহপাঠীর জন্য সাশ্রয়ী হয়। কোনো সিকিউরিটি নিয়ে চিন্তা নেই।"
                    : "The student rental rate is fixed and affordable for peers. Smooth return builds trust score!";
        }

        return isBengali
                ? "ধন্যবাদ নক দেওয়ার জন্য! এটি আপনার কাজে লাগলে জানাতে পারেন, আমি ক্যাম্পাসে আছি।"
                : "Thanks for messaging! The item is ready for you whenever you need it on campus.";
    }
}
