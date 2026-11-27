package com.borrowbox.app.ui.condition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.borrowbox.app.R;
import com.borrowbox.app.data.repository.NotificationRepository;
import com.borrowbox.app.model.NotificationModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;

public class DepositDecisionDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_RENTAL_ID = "arg_rental_id";
    private static final String ARG_DEPOSIT_AMOUNT = "arg_deposit_amount";

    private long rentalId = 1L;
    private double depositAmount = 200.0;

    private TextView tvDialogDepositInfo;
    private TextView tvSliderLabel;
    private Slider sliderDepositRefund;
    private MaterialButton btnSettleDeposit;
    private MaterialButton btnCancelDepositDialog;

    public static DepositDecisionDialogFragment newInstance(long rentalId, double depositAmount) {
        DepositDecisionDialogFragment fragment = new DepositDecisionDialogFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_RENTAL_ID, rentalId);
        args.putDouble(ARG_DEPOSIT_AMOUNT, depositAmount);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_deposit_decision, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            rentalId = getArguments().getLong(ARG_RENTAL_ID, 1L);
            depositAmount = getArguments().getDouble(ARG_DEPOSIT_AMOUNT, 200.0);
        }

        tvDialogDepositInfo = view.findViewById(R.id.tvDialogDepositInfo);
        tvSliderLabel = view.findViewById(R.id.tvSliderLabel);
        sliderDepositRefund = view.findViewById(R.id.sliderDepositRefund);
        btnSettleDeposit = view.findViewById(R.id.btnSettleDeposit);
        btnCancelDepositDialog = view.findViewById(R.id.btnCancelDepositDialog);

        tvDialogDepositInfo.setText(String.format(java.util.Locale.US, "Escrow Security Deposit: ৳ %.2f", depositAmount));

        sliderDepositRefund.addOnChangeListener((slider, value, fromUser) -> {
            double refund = (depositAmount * value) / 100.0;
            tvSliderLabel.setText(String.format(java.util.Locale.US, "Borrower Refund: ৳ %.2f (%.0f%%)", refund, value));
        });

        btnSettleDeposit.setOnClickListener(v -> {
            int percent = (int) sliderDepositRefund.getValue();
            double refund = (depositAmount * percent) / 100.0;

            NotificationRepository.getInstance(requireContext()).sendNotification(
                    1L,
                    "Security Deposit Settled",
                    "Escrow settlement completed for Rental #" + rentalId + ". Refund: ৳" + refund,
                    NotificationModel.DEEP_LINK_RENTAL_DETAILS
            );

            Toast.makeText(requireContext(), "Deposit settlement confirmed!", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        btnCancelDepositDialog.setOnClickListener(v -> dismiss());
    }
}
