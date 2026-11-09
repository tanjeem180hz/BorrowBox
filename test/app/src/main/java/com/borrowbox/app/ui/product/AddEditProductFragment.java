package com.borrowbox.app.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.ProductRepository;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.databinding.FragmentAddEditProductBinding;
import com.borrowbox.app.model.PriceUnit;
import com.borrowbox.app.model.Product;
import com.google.android.material.snackbar.Snackbar;

public class AddEditProductFragment extends Fragment {

    private FragmentAddEditProductBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddEditProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProductRepository productRepository = RepositoryProvider.getInstance(requireContext()).getProductRepository();
        SessionManager sessionManager = new SessionManager(requireContext());

        binding.btnSaveProduct.setOnClickListener(v -> {
            String name = binding.etName.getText() != null ? binding.etName.getText().toString().trim() : "";
            String category = binding.etCategory.getText() != null ? binding.etCategory.getText().toString().trim() : "Electronics";
            String priceStr = binding.etPrice.getText() != null ? binding.etPrice.getText().toString().trim() : "20";
            String condition = binding.etCondition.getText() != null ? binding.etCondition.getText().toString().trim() : "Good";
            String desc = binding.etDescription.getText() != null ? binding.etDescription.getText().toString().trim() : "";

            if (name.isEmpty()) {
                Snackbar.make(view, "Please enter product name", Snackbar.LENGTH_SHORT).show();
                return;
            }

            int price = 20;
            try {
                price = Integer.parseInt(priceStr);
            } catch (NumberFormatException ignored) {}

            PriceUnit unit = binding.rbDay.isChecked() ? PriceUnit.DAY : PriceUnit.HOUR;
            long ownerId = sessionManager.getLoggedInUserId();

            Product product = new Product(0, ownerId, name, category, desc, price, unit, condition, true, 23.8759, 90.3198, "");
            productRepository.insertProduct(product);

            Snackbar.make(view, "Product listed successfully!", Snackbar.LENGTH_SHORT).show();
            Navigation.findNavController(view).popBackStack();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
