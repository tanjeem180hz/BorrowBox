package com.borrowbox.app.ui.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.borrowbox.app.R;
import com.borrowbox.app.databinding.DialogMessengerCallBinding;
import com.borrowbox.app.databinding.FragmentMessengerChatBinding;
import com.borrowbox.app.model.ChatMessage;
import com.borrowbox.app.model.PriceUnit;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.model.Student;
import com.borrowbox.app.util.CurrencyUtils;
import com.borrowbox.app.util.ProductImageHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MessengerChatFragment extends Fragment {

    private FragmentMessengerChatBinding binding;
    private MessengerChatViewModel viewModel;
    private ChatMessageAdapter adapter;

    private long productId = 1L;
    private long ownerId = 2L;
    private String initialMessage = null;
    private boolean initialSent = false;

    private Product currentProduct;
    private Student currentOwner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMessengerChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MessengerChatViewModel.class);

        if (getArguments() != null) {
            productId = getArguments().getLong("product_id", 1L);
            ownerId = getArguments().getLong("owner_id", 2L);
            initialMessage = getArguments().getString("initial_message", null);
        }

        // Setup Header Back Navigation
        binding.btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        // Automatic top display cutout (notch/punch-hole) and bottom keyboard/navigation cutout adaptation
        com.borrowbox.app.util.EdgeToEdgeHelper.applyTopCutout(binding.layoutHeader);
        com.borrowbox.app.util.EdgeToEdgeHelper.applyImeAndBottomCutout(binding.layoutBottomBar);

        // Setup RecyclerView
        adapter = new ChatMessageAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setStackFromEnd(true);
        binding.recyclerChatMessages.setLayoutManager(layoutManager);
        binding.recyclerChatMessages.setAdapter(adapter);

        // Fetch Owner Details
        viewModel.getOwner(ownerId).observe(getViewLifecycleOwner(), owner -> {
            if (owner != null) {
                currentOwner = owner;
                binding.tvHeaderName.setText(owner.getName());
                String letter = owner.getName().substring(0, 1).toUpperCase(java.util.Locale.ROOT);
                binding.tvHeaderAvatar.setText(letter);
                binding.tvTypingAvatar.setText(letter);
                adapter.setOwnerAvatarLetter(letter);
                adapter.notifyDataSetChanged();

                String typingLabel = getString(R.string.messenger_typing, owner.getName().split(" ")[0]);
                binding.tvTypingLabel.setText(typingLabel);
            }
        });

        // Fetch Product Details
        viewModel.getProduct(productId).observe(getViewLifecycleOwner(), product -> {
            if (product != null) {
                currentProduct = product;
                binding.tvProductTitle.setText(product.getName());
                String unit = product.getPriceUnit() == PriceUnit.DAY ? "/day" : "/hour";
                binding.tvProductPrice.setText(CurrencyUtils.format(product.getPrice()) + unit + " • DIU Campus");
                binding.imgProductThumb.setImageResource(ProductImageHelper.getDrawableResForProduct(product));

                // Start observing messages once product name is known
                observeMessages(product.getName());
            }
        });

        // View Listing Click
        binding.btnViewListing.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putLong("product_id", productId);
            Navigation.findNavController(v).navigate(R.id.productDetailsFragment, args);
        });

        // Typing LiveData
        viewModel.getTyping(productId).observe(getViewLifecycleOwner(), isTyping -> {
            if (Boolean.TRUE.equals(isTyping)) {
                binding.layoutTypingIndicator.setVisibility(View.VISIBLE);
                binding.recyclerChatMessages.smoothScrollToPosition(adapter.getItemCount());
            } else {
                binding.layoutTypingIndicator.setVisibility(View.GONE);
            }
        });

        // TextWatcher on input field: Toggle Send vs Like (Thumbs up)
        binding.etChatInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean hasText = s != null && s.toString().trim().length() > 0;
                if (hasText) {
                    binding.btnSendMessage.setVisibility(View.VISIBLE);
                    binding.btnSendLike.setVisibility(View.GONE);
                } else {
                    binding.btnSendMessage.setVisibility(View.GONE);
                    binding.btnSendLike.setVisibility(View.VISIBLE);
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Send Message Button
        binding.btnSendMessage.setOnClickListener(v -> sendCurrentText());

        // Send Like (Thumbs-up) Button
        binding.btnSendLike.setOnClickListener(v -> {
            com.borrowbox.app.util.ChatSoundHelper.playMessageSent(binding.btnSendLike);
            String pName = currentProduct != null ? currentProduct.getName() : "Item";
            viewModel.sendMessage(productId, ownerId, "👍", true, pName);
        });

        // Quick Suggestion Chips
        binding.chipAvailable.setOnClickListener(v -> sendPresetMessage(binding.chipAvailable.getText().toString()));
        binding.chipMeet.setOnClickListener(v -> sendPresetMessage(binding.chipMeet.getText().toString()));
        binding.chipPickupNow.setOnClickListener(v -> sendPresetMessage(binding.chipPickupNow.getText().toString()));
        binding.chipLocation.setOnClickListener(v -> sendPresetMessage(binding.chipLocation.getText().toString()));

        // Messenger Call & Video Call simulation
        binding.btnAudioCall.setOnClickListener(v -> showCallDialog(false));
        binding.btnVideoCall.setOnClickListener(v -> showCallDialog(true));
        binding.btnInfo.setOnClickListener(v -> {
            if (currentOwner != null) {
                String info = currentOwner.getName() + "\n" + currentOwner.getDepartment() + " • " + currentOwner.getUniversity()
                        + "\nTrust Score: " + currentOwner.getTrustScore() + "% • Rating: ⭐ " + currentOwner.getRatingAverage();
                Toast.makeText(requireContext(), info, Toast.LENGTH_LONG).show();
            }
        });

        // Rich Media & Attachment actions
        binding.btnActionMore.setOnClickListener(v -> showMoreOptionsDialog());
        binding.btnActionCamera.setOnClickListener(v -> sendSamplePhotoAttachment("Camera"));
        binding.btnActionGallery.setOnClickListener(v -> sendSamplePhotoAttachment("Gallery"));
        binding.btnActionMic.setOnClickListener(v -> sendVoiceNoteAttachment());
    }

    private int lastMessageCount = -1;

    private void observeMessages(String productName) {
        viewModel.getMessages(productId, ownerId, productName).observe(getViewLifecycleOwner(), messages -> {
            if (messages != null) {
                if (lastMessageCount != -1 && messages.size() > lastMessageCount) {
                    ChatMessage latest = messages.get(messages.size() - 1);
                    if (!latest.isFromMe()) {
                        com.borrowbox.app.util.ChatSoundHelper.playMessageReceived(requireContext(), binding.recyclerChatMessages);
                    }
                }
                lastMessageCount = messages.size();

                adapter.submitList(messages, () -> {
                    if (!messages.isEmpty()) {
                        binding.recyclerChatMessages.scrollToPosition(messages.size() - 1);
                    }
                });

                // Auto-send initial message if requested from the listing prompt
                if (!initialSent && initialMessage != null && !initialMessage.trim().isEmpty()) {
                    initialSent = true;
                    viewModel.sendMessage(productId, ownerId, initialMessage.trim(), false, productName);
                }
            }
        });
    }

    private void sendCurrentText() {
        String text = binding.etChatInput.getText() != null
                ? binding.etChatInput.getText().toString().trim() : "";
        if (!text.isEmpty()) {
            com.borrowbox.app.util.ChatSoundHelper.playMessageSent(binding.btnSendMessage);
            String pName = currentProduct != null ? currentProduct.getName() : "Item";
            viewModel.sendMessage(productId, ownerId, text, false, pName);
            binding.etChatInput.setText("");
        }
    }

    private void sendPresetMessage(String preset) {
        com.borrowbox.app.util.ChatSoundHelper.playMessageSent(binding.btnSendMessage);
        String pName = currentProduct != null ? currentProduct.getName() : "Item";
        viewModel.sendMessage(productId, ownerId, preset, false, pName);
    }

    private void sendSamplePhotoAttachment(String source) {
        com.borrowbox.app.util.ChatSoundHelper.playMessageSent(binding.btnActionCamera);
        String pName = currentProduct != null ? currentProduct.getName() : "Item";
        String prompt = "📷 Photo attached from " + source;
        viewModel.sendAttachment(productId, ownerId, prompt, pName, "IMAGE", "");
        Toast.makeText(requireContext(), "Photo sent!", Toast.LENGTH_SHORT).show();
    }

    private void sendVoiceNoteAttachment() {
        com.borrowbox.app.util.ChatSoundHelper.playMessageSent(binding.btnActionMic);
        String pName = currentProduct != null ? currentProduct.getName() : "Item";
        viewModel.sendAttachment(productId, ownerId, "🎙️ Voice message (0:08)", pName, "AUDIO", "");
        Toast.makeText(requireContext(), "Voice note sent (0:08)!", Toast.LENGTH_SHORT).show();
    }

    private void showMoreOptionsDialog() {
        String[] options = {
                "📍 Share Campus Meeting Point",
                "📷 Send Item Condition Photo",
                "🆔 Share Student ID Card",
                "🤝 Confirm Handover Schedule"
        };
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Messenger Actions")
                .setItems(options, (dialog, which) -> {
                    String selected = options[which];
                    sendPresetMessage(selected);
                })
                .show();
    }

    private void showCallDialog(boolean isVideo) {
        if (getContext() == null || currentOwner == null) return;

        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        DialogMessengerCallBinding callBinding = DialogMessengerCallBinding.inflate(getLayoutInflater());
        dialog.setContentView(callBinding.getRoot());

        callBinding.tvCallName.setText(currentOwner.getName());
        String letter = currentOwner.getName().substring(0, 1).toUpperCase(java.util.Locale.ROOT);
        callBinding.tvCallAvatar.setText(letter);

        String subtitle = isVideo
                ? getString(R.string.messenger_video_calling_subtitle)
                : getString(R.string.messenger_calling_subtitle);
        callBinding.tvCallStatus.setText(subtitle + " • " + getString(R.string.messenger_ringing));

        callBinding.btnCallEnd.setOnClickListener(v -> dialog.dismiss());
        callBinding.btnCallSpeaker.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Speaker toggled", Toast.LENGTH_SHORT).show());
        callBinding.btnCallMute.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Microphone muted", Toast.LENGTH_SHORT).show());

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
