package com.borrowbox.app.data.repository;

import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.borrowbox.app.model.ChatMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ChatMessageRepository {

    private static volatile ChatMessageRepository instance;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final AtomicLong idGenerator = new AtomicLong(100);

    // Map of productId -> MutableLiveData<List<ChatMessage>>
    private final Map<Long, MutableLiveData<List<ChatMessage>>> conversationsLive = new HashMap<>();
    private final Map<Long, List<ChatMessage>> conversationsData = new HashMap<>();
    private final Map<Long, MutableLiveData<Boolean>> typingLive = new HashMap<>();

    private ChatMessageRepository() {
    }

    public static ChatMessageRepository getInstance() {
        if (instance == null) {
            synchronized (ChatMessageRepository.class) {
                if (instance == null) {
                    instance = new ChatMessageRepository();
                }
            }
        }
        return instance;
    }

    public synchronized LiveData<List<ChatMessage>> getMessagesLive(long productId, long borrowerId,
                                                                    long lenderId, String productName,
                                                                    boolean isBengali) {
        if (!conversationsLive.containsKey(productId)) {
            MutableLiveData<List<ChatMessage>> liveData = new MutableLiveData<>();
            List<ChatMessage> initialList = new ArrayList<>();

            // Seed an initial friendly welcome message from the owner
            long now = System.currentTimeMillis() - (15 * 60 * 1000L); // 15 mins ago
            String welcome = isBengali
                    ? "আসসালামু আলাইকুম! " + (productName != null ? productName : "সামগ্রীটি") + " সম্পর্কে কোনো তথ্য জানার থাকলে সরাসরি মেসেজ দিন।"
                    : "Hello! If you need to borrow this " + (productName != null ? productName : "item") + " or have any questions, feel free to ask!";

            ChatMessage seed = new ChatMessage(
                    idGenerator.incrementAndGet(),
                    productId,
                    lenderId,
                    borrowerId,
                    welcome,
                    now,
                    false,
                    "SEEN",
                    false
            );
            initialList.add(seed);

            conversationsData.put(productId, initialList);
            liveData.setValue(new ArrayList<>(initialList));
            conversationsLive.put(productId, liveData);
        }
        return conversationsLive.get(productId);
    }

    public synchronized LiveData<Boolean> getTypingLive(long productId) {
        if (!typingLive.containsKey(productId)) {
            typingLive.put(productId, new MutableLiveData<>(false));
        }
        return typingLive.get(productId);
    }

    public synchronized void sendMessage(long productId, long senderId, long receiverId,
                                         String text, boolean isLike, String productName,
                                         boolean isBengali) {
        List<ChatMessage> list = conversationsData.get(productId);
        if (list == null) {
            list = new ArrayList<>();
            conversationsData.put(productId, list);
        }

        long msgId = idGenerator.incrementAndGet();
        ChatMessage myMsg = new ChatMessage(
                msgId,
                productId,
                senderId,
                receiverId,
                text,
                System.currentTimeMillis(),
                true,
                "SENT",
                isLike
        );
        list.add(myMsg);
        postUpdate(productId);

        // Step 1: Update status to DELIVERED after 400ms
        mainHandler.postDelayed(() -> {
            synchronized (ChatMessageRepository.this) {
                myMsg.setStatus("DELIVERED");
                postUpdate(productId);
            }
        }, 400);

        // Step 2: Update status to SEEN after 900ms and start typing
        mainHandler.postDelayed(() -> {
            synchronized (ChatMessageRepository.this) {
                myMsg.setStatus("SEEN");
                postUpdate(productId);
                setTyping(productId, true);
            }
        }, 900);

        // Step 3: Owner replies after realistic typing delay (~2.2s)
        mainHandler.postDelayed(() -> {
            synchronized (ChatMessageRepository.this) {
                setTyping(productId, false);
                String replyText = generateOwnerReply(text, isLike, productName, isBengali);
                ChatMessage ownerReply = new ChatMessage(
                        idGenerator.incrementAndGet(),
                        productId,
                        receiverId,
                        senderId,
                        replyText,
                        System.currentTimeMillis(),
                        false,
                        "SEEN",
                        isLike
                );
                List<ChatMessage> currentList = conversationsData.get(productId);
                if (currentList != null) {
                    currentList.add(ownerReply);
                    postUpdate(productId);
                }
            }
        }, 2200);
    }

    private void setTyping(long productId, boolean isTyping) {
        MutableLiveData<Boolean> typing = typingLive.get(productId);
        if (typing != null) {
            typing.postValue(isTyping);
        }
    }

    private void postUpdate(long productId) {
        MutableLiveData<List<ChatMessage>> liveData = conversationsLive.get(productId);
        List<ChatMessage> list = conversationsData.get(productId);
        if (liveData != null && list != null) {
            liveData.postValue(new ArrayList<>(list));
        }
    }

    private String generateOwnerReply(String userMessage, boolean isLike, String productName, boolean isBengali) {
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
