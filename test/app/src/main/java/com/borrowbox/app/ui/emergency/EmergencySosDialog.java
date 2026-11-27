package com.borrowbox.app.ui.emergency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.borrowbox.app.R;
import com.borrowbox.app.data.repository.NotificationRepository;
import com.borrowbox.app.model.NotificationModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

public class EmergencySosDialog extends BottomSheetDialogFragment {

    public static EmergencySosDialog newInstance() {
        return new EmergencySosDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_emergency_sos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton btnBroadcastSos = view.findViewById(R.id.btnBroadcastSos);
        MaterialButton btnCancelSos = view.findViewById(R.id.btnCancelSos);

        btnBroadcastSos.setOnClickListener(v -> {
            NotificationRepository.getInstance(requireContext()).sendNotification(
                    1L,
                    "🚨 EMERGENCY SOS ALERT",
                    "Emergency SOS broadcasted from 23.8762, 90.3201 (DIU Campus). Campus Security notified.",
                    NotificationModel.DEEP_LINK_NOTIFICATION_CENTRE
            );

            Toast.makeText(requireContext(), "🚨 Campus Security & Proctorial Team Notified!", Toast.LENGTH_LONG).show();
            dismiss();
        });

        btnCancelSos.setOnClickListener(v -> dismiss());
    }
}
