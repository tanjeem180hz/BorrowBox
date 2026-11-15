package com.borrowbox.app.ui.chat;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.ChatMessageRepository;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.data.repository.UserRepository;
import com.borrowbox.app.model.ChatMessage;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.model.Student;
import java.util.List;

public class MessengerChatViewModel extends AndroidViewModel {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatRepository;
    private final SessionManager sessionManager;

    public MessengerChatViewModel(@NonNull Application application) {
        super(application);
        RepositoryProvider provider = RepositoryProvider.getInstance(application);
        this.productRepository = provider.getProductRepository();
        this.userRepository = provider.getUserRepository();
        this.chatRepository = ChatMessageRepository.getInstance();
        this.sessionManager = new SessionManager(application);
    }

    public LiveData<Product> getProduct(long productId) {
        return productRepository.getProductByIdLive(productId);
    }

    public LiveData<Student> getOwner(long ownerId) {
        return userRepository.getStudentLive(ownerId);
    }

    public LiveData<List<ChatMessage>> getMessages(long productId, long ownerId, String productName) {
        boolean isBengali = SessionManager.LANG_BN.equals(sessionManager.getLanguage());
        return chatRepository.getMessagesLive(productId, sessionManager.getLoggedInUserId(), ownerId, productName, isBengali);
    }

    public LiveData<Boolean> getTyping(long productId) {
        return chatRepository.getTypingLive(productId);
    }

    public long getLoggedInUserId() {
        return sessionManager.getLoggedInUserId();
    }

    public boolean isBengali() {
        return SessionManager.LANG_BN.equals(sessionManager.getLanguage());
    }

    public void sendMessage(long productId, long ownerId, String text, boolean isLike, String productName) {
        long myId = sessionManager.getLoggedInUserId();
        chatRepository.sendMessage(productId, myId, ownerId, text, isLike, productName, isBengali());
    }
}
