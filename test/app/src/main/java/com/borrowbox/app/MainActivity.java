package com.borrowbox.app;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import com.borrowbox.app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            // Robust bottom navigation handling without corrupted multi-stack state
            binding.bottomNavigation.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (navController.getCurrentDestination() != null
                        && navController.getCurrentDestination().getId() == itemId) {
                    return true;
                }

                NavOptions options = new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo(R.id.nav_home, false)
                        .build();

                try {
                    navController.navigate(itemId, null, options);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            });

            binding.bottomNavigation.setOnItemReselectedListener(item -> {
                // Do not recreate fragment on reselection
            });

            // Synchronize bottom nav visibility and selected item with navigation destination
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                int id = destination.getId();
                if (id == R.id.nav_home || id == R.id.nav_map
                        || id == R.id.nav_rentals || id == R.id.nav_profile) {
                    binding.bottomNavigation.setVisibility(View.VISIBLE);
                    MenuItem menuItem = binding.bottomNavigation.getMenu().findItem(id);
                    if (menuItem != null && !menuItem.isChecked()) {
                        menuItem.setChecked(true);
                    }
                } else {
                    binding.bottomNavigation.setVisibility(View.GONE);
                }
            });
        }
    }
}
