package com.borrowbox.app;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import com.borrowbox.app.data.local.SessionManager;
import com.borrowbox.app.data.repository.RepositoryProvider;
import com.borrowbox.app.data.repository.UserRepository;
import com.borrowbox.app.databinding.ActivityMainBinding;
import com.borrowbox.app.model.Student;
import com.borrowbox.app.ui.common.AvatarDrawableHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private Student currentStudent;
    private SessionManager sessionManager;
    private androidx.lifecycle.LiveData<Student> studentLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            // Observe current student to dynamically update bottom navigation profile picture
            observeCurrentStudent();

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
                    updateBottomNavAvatar();
                } else {
                    binding.bottomNavigation.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBottomNavAvatar();
    }

    private void observeCurrentStudent() {
        if (studentLiveData != null) {
            studentLiveData.removeObservers(this);
        }
        long loggedInId = sessionManager.getLoggedInUserId();
        // Seeded student fallback for instant display
        for (Student s : com.borrowbox.app.data.local.SeedData.getInitialStudents()) {
            if (s.getId() == loggedInId) {
                currentStudent = s;
                break;
            }
        }
        UserRepository userRepository = RepositoryProvider.getInstance(this).getUserRepository();
        studentLiveData = userRepository.getStudentLive(loggedInId);
        studentLiveData.observe(this, student -> {
            if (student != null) {
                currentStudent = student;
                renderBottomNavAvatar();
            }
        });
    }

    /**
     * Replaces generic bottom nav profile icon with the user's authentic circular profile picture/avatar.
     */
    public void updateBottomNavAvatar() {
        observeCurrentStudent();
        renderBottomNavAvatar();
    }

    private void renderBottomNavAvatar() {
        if (binding == null || navController == null) return;

        boolean isSelected = navController.getCurrentDestination() != null
                && navController.getCurrentDestination().getId() == R.id.nav_profile;

        Drawable avatarDrawable = AvatarDrawableHelper.createAvatarDrawable(this, currentStudent, 26, isSelected);

        MenuItem profileItem = binding.bottomNavigation.getMenu().findItem(R.id.nav_profile);
        if (profileItem != null) {
            profileItem.setIcon(avatarDrawable);
        }

        binding.bottomNavigation.post(() -> {
            View profileView = binding.bottomNavigation.findViewById(R.id.nav_profile);
            if (profileView != null) {
                ImageView iconView = profileView.findViewById(com.google.android.material.R.id.navigation_bar_item_icon_view);
                if (iconView != null) {
                    iconView.setImageTintList(null);
                    iconView.setImageDrawable(avatarDrawable);
                }
            }
        });
    }
}
