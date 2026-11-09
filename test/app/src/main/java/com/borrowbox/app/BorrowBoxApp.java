package com.borrowbox.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.appcompat.app.AppCompatDelegate;
import com.borrowbox.app.data.local.SessionManager;
import org.osmdroid.config.Configuration;
import java.io.File;

public class BorrowBoxApp extends Application {

    public static final String CHANNEL_REMINDERS = "borrowbox_reminders";
    public static final String CHANNEL_REQUESTS = "borrowbox_requests";

    @Override
    public void onCreate() {
        super.onCreate();

        // 1. Configure osmdroid OpenStreetMap
        Configuration.getInstance().setUserAgentValue(getPackageName());
        File basePath = new File(getCacheDir(), "osmdroid");
        Configuration.getInstance().setOsmdroidBasePath(basePath);
        File tileCache = new File(basePath, "tiles");
        Configuration.getInstance().setOsmdroidTileCache(tileCache);
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid_prefs", MODE_PRIVATE));

        // 2. Setup Notification Channels
        createNotificationChannels();

        // 3. Apply saved theme
        SessionManager sessionManager = new SessionManager(this);
        if (sessionManager.isDarkModeEnabled()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }

        // 4. Pre-warm database
        com.borrowbox.app.util.AppExecutors.getInstance().diskIO().execute(() -> {
            com.borrowbox.app.data.local.AppDatabase.getInstance(this).studentDao().getAllStudents();
        });
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                NotificationChannel reminders = new NotificationChannel(
                        CHANNEL_REMINDERS,
                        getString(R.string.notif_channel_reminders),
                        NotificationManager.IMPORTANCE_HIGH
                );
                reminders.setDescription("Active rental countdown alerts and return notifications");

                NotificationChannel requests = new NotificationChannel(
                        CHANNEL_REQUESTS,
                        getString(R.string.notif_channel_requests),
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                requests.setDescription("Peer product rental requests and approvals");

                manager.createNotificationChannel(reminders);
                manager.createNotificationChannel(requests);
            }
        }
    }
}
