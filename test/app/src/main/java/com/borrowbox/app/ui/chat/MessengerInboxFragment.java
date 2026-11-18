package com.borrowbox.app.ui.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.borrowbox.app.R;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.ChatMessageRepository;
import com.borrowbox.app.databinding.FragmentMessengerInboxBinding;
import com.borrowbox.app.model.ChatConversation;
import com.borrowbox.app.util.EdgeToEdgeHelper;
import java.util.ArrayList;
import java.util.List;

public class MessengerInboxFragment extends Fragment {

    private FragmentMessengerInboxBinding binding;
    private ConversationAdapter adapter;
    private List<ChatConversation> fullList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMessengerInboxBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Apply display cutout and system bars adaptation
        EdgeToEdgeHelper.applyTopCutout(binding.layoutInboxHeader);
        EdgeToEdgeHelper.applyBottomCutout(binding.getRoot());

        // Header Back Navigation
        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        // Setup RecyclerView
        adapter = new ConversationAdapter(conv -> {
            Bundle args = new Bundle();
            args.putLong("product_id", conv.getProductId());
            args.putLong("owner_id", conv.getOtherUserId());
            Navigation.findNavController(view).navigate(R.id.messengerChatFragment, args);
        });

        binding.recyclerConversations.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerConversations.setAdapter(adapter);

        SessionManager sessionManager = new SessionManager(requireContext());
        long currentUserId = sessionManager.getLoggedInUserId();

        ChatMessageRepository chatRepo = ChatMessageRepository.getInstance(requireContext());
        chatRepo.getInboxConversationsLive(currentUserId).observe(getViewLifecycleOwner(), list -> {
            fullList = list != null ? list : new ArrayList<>();
            filterConversations(binding.etSearchInbox.getText() != null ? binding.etSearchInbox.getText().toString() : "");
        });

        // Search Filter
        binding.etSearchInbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterConversations(s != null ? s.toString() : "");
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Quick Peer Circle Clicks
        binding.peerSabbir.setOnClickListener(v -> openPeerChat(1L, 2L)); // Laptop charger with Sabbir
        binding.peerTasnim.setOnClickListener(v -> openPeerChat(4L, 4L)); // DSLR Camera with Tasnim
        binding.peerHimel.setOnClickListener(v -> openPeerChat(3L, 3L));  // Power Bank with Himel
        binding.peerTanjeem.setOnClickListener(v -> openPeerChat(2L, 1L));
    }

    private void openPeerChat(long productId, long ownerId) {
        Bundle args = new Bundle();
        args.putLong("product_id", productId);
        args.putLong("owner_id", ownerId);
        Navigation.findNavController(requireView()).navigate(R.id.messengerChatFragment, args);
    }

    private void filterConversations(String query) {
        if (query == null || query.trim().isEmpty()) {
            adapter.submitList(new ArrayList<>(fullList));
            updateEmptyState(fullList.isEmpty());
            return;
        }

        String q = query.trim().toLowerCase(java.util.Locale.ROOT);
        List<ChatConversation> filtered = new ArrayList<>();
        for (ChatConversation conv : fullList) {
            if (conv.getOtherUserName().toLowerCase(java.util.Locale.ROOT).contains(q)
                    || conv.getProductName().toLowerCase(java.util.Locale.ROOT).contains(q)
                    || conv.getLastMessage().toLowerCase(java.util.Locale.ROOT).contains(q)) {
                filtered.add(conv);
            }
        }
        adapter.submitList(filtered);
        updateEmptyState(filtered.isEmpty());
    }

    private void updateEmptyState(boolean isEmpty) {
        if (isEmpty) {
            binding.layoutEmptyState.setVisibility(View.VISIBLE);
            binding.recyclerConversations.setVisibility(View.GONE);
        } else {
            binding.layoutEmptyState.setVisibility(View.GONE);
            binding.recyclerConversations.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
