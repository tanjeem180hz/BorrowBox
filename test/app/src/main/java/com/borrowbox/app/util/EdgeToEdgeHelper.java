package com.borrowbox.app.util;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * Universal helper to automatically manage display cutouts (notches, punch-holes, dynamic islands)
 * and system navigation cuts (gesture pills, 3-button bars, bottom cutouts) across any device.
 */
public final class EdgeToEdgeHelper {

    private EdgeToEdgeHelper() {
    }

    /**
     * Configures the activity window for edge-to-edge display with full cutout support.
     * Works on all Android versions and device form factors.
     */
    public static void applyEdgeToEdge(@NonNull AppCompatActivity activity, @Nullable View rootView) {
        // 1. Enable layout extension into system bars and display cutout
        WindowCompat.setDecorFitsSystemWindows(activity.getWindow(), false);

        // 2. Configure display cutout mode to seamlessly utilize notch and cutout areas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS;
            } else {
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }
            activity.getWindow().setAttributes(lp);
        }

        // 3. Set transparent system bars so app backgrounds draw naturally into cutouts
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);

        // 4. Configure light/dark system bar icons based on current device theme
        updateSystemBarIcons(activity);
    }

    /**
     * Updates status bar and navigation bar icon colors (light vs dark) based on Day/Night mode.
     */
    public static void updateSystemBarIcons(@NonNull Activity activity) {
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(
                activity.getWindow(), activity.getWindow().getDecorView());
        if (controller != null) {
            boolean isNight = (activity.getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
            controller.setAppearanceLightStatusBars(!isNight);
            controller.setAppearanceLightNavigationBars(!isNight);
        }
    }

    /**
     * Automatically applies top display cutout (notch/punch-hole) and status bar margin to a floating view/card.
     */
    public static void applyTopCutoutMargin(@Nullable View view) {
        if (view == null) return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof ViewGroup.MarginLayoutParams)) return;
        final int initialMarginTop = ((ViewGroup.MarginLayoutParams) params).topMargin;

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets cutInsets = insets.getInsets(
                    WindowInsetsCompat.Type.statusBars() | WindowInsetsCompat.Type.displayCutout()
            );
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            lp.topMargin = initialMarginTop + cutInsets.top;
            v.setLayoutParams(lp);
            return insets;
        });
        ViewCompat.requestApplyInsets(view);
    }

    /**
     * Automatically applies bottom cutout and navigation bar margin to a floating view/card.
     */
    public static void applyBottomCutoutMargin(@Nullable View view) {
        if (view == null) return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof ViewGroup.MarginLayoutParams)) return;
        final int initialMarginBottom = ((ViewGroup.MarginLayoutParams) params).bottomMargin;

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets cutInsets = insets.getInsets(
                    WindowInsetsCompat.Type.navigationBars() | WindowInsetsCompat.Type.displayCutout()
            );
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            lp.bottomMargin = initialMarginBottom + cutInsets.bottom;
            v.setLayoutParams(lp);
            return insets;
        });
        ViewCompat.requestApplyInsets(view);
    }

    /**
     * Automatically applies top display cutout (notch/punch-hole) and status bar padding to a view.
     */
    public static void applyTopCutout(@Nullable View view) {
        if (view == null) return;
        final int initialPaddingTop = view.getPaddingTop();
        final int initialPaddingLeft = view.getPaddingLeft();
        final int initialPaddingRight = view.getPaddingRight();

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets cutInsets = insets.getInsets(
                    WindowInsetsCompat.Type.statusBars() | WindowInsetsCompat.Type.displayCutout()
            );
            v.setPadding(
                    initialPaddingLeft + cutInsets.left,
                    initialPaddingTop + cutInsets.top,
                    initialPaddingRight + cutInsets.right,
                    v.getPaddingBottom()
            );
            return insets;
        });
        ViewCompat.requestApplyInsets(view);
    }

    /**
     * Automatically applies bottom cutout and system navigation bar padding to a view.
     */
    public static void applyBottomCutout(@Nullable View view) {
        if (view == null) return;
        final int initialPaddingBottom = view.getPaddingBottom();
        final int initialPaddingLeft = view.getPaddingLeft();
        final int initialPaddingRight = view.getPaddingRight();

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets cutInsets = insets.getInsets(
                    WindowInsetsCompat.Type.navigationBars() | WindowInsetsCompat.Type.displayCutout()
            );
            v.setPadding(
                    initialPaddingLeft + cutInsets.left,
                    v.getPaddingTop(),
                    initialPaddingRight + cutInsets.right,
                    initialPaddingBottom + cutInsets.bottom
            );
            return insets;
        });
        ViewCompat.requestApplyInsets(view);
    }

    /**
     * Automatically applies top cutout to topView and bottom cutout to bottomView.
     */
    public static void applyTopAndBottomCutout(@Nullable View topView, @Nullable View bottomView) {
        applyTopCutout(topView);
        applyBottomCutout(bottomView);
    }

    /**
     * Automatically applies top cutout to root view and bottom cutout to bottomView.
     */
    public static void applyRootCutouts(@Nullable View rootView, @Nullable View bottomActionView) {
        if (rootView == null) return;
        applyTopCutout(rootView);
        if (bottomActionView != null) {
            applyBottomCutout(bottomActionView);
        }
    }

    /**
     * Automatically handles software keyboard (IME) and bottom cutout on interactive input containers.
     * When the keyboard opens, it lifts above the keyboard; when closed, sits safely above the bottom cutout.
     */
    public static void applyImeAndBottomCutout(@Nullable View view) {
        if (view == null) return;
        final int initialPaddingBottom = view.getPaddingBottom();
        final int initialPaddingLeft = view.getPaddingLeft();
        final int initialPaddingRight = view.getPaddingRight();

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets navInsets = insets.getInsets(
                    WindowInsetsCompat.Type.navigationBars() | WindowInsetsCompat.Type.displayCutout()
            );
            Insets imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime());
            int bottomInset = Math.max(navInsets.bottom, imeInsets.bottom);

            v.setPadding(
                    initialPaddingLeft + navInsets.left,
                    v.getPaddingTop(),
                    initialPaddingRight + navInsets.right,
                    initialPaddingBottom + bottomInset
            );
            return insets;
        });
        ViewCompat.requestApplyInsets(view);
    }
}
