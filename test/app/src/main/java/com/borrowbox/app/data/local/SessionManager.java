package com.borrowbox.app.data.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages user login session and demo settings.
 */
public class SessionManager {

    private static final String PREF_NAME = "borrowbox_session";
    private static final String KEY_USER_ID = "logged_user_id";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_ONBOARDING_COMPLETED = "onboarding_completed";
    private static final String KEY_AUTO_ACCEPT = "auto_accept_demo";
    private static final String KEY_DARK_MODE = "dark_mode_enabled";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setLogin(long userId) {
        prefs.edit()
                .putLong(KEY_USER_ID, userId)
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .apply();
    }

    public void logout() {
        prefs.edit()
                .remove(KEY_USER_ID)
                .putBoolean(KEY_IS_LOGGED_IN, false)
                .apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public long getLoggedInUserId() {
        return prefs.getLong(KEY_USER_ID, 1L); // default to student 1 (Tanjeem)
    }

    public boolean isOnboardingCompleted() {
        return prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false);
    }

    public void setOnboardingCompleted(boolean completed) {
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, completed).apply();
    }

    public boolean isAutoAcceptEnabled() {
        return prefs.getBoolean(KEY_AUTO_ACCEPT, false);
    }

    public void setAutoAcceptEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_AUTO_ACCEPT, enabled).apply();
    }

    public boolean isDarkModeEnabled() {
        return prefs.getBoolean(KEY_DARK_MODE, false);
    }

    public void setDarkModeEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_DARK_MODE, enabled).apply();
    }
}
