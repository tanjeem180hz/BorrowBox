package com.borrowbox.app.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.HapticFeedbackConstants;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * Provides authentic Messenger sound and haptic feedback for sent and received messages.
 */
public final class ChatSoundHelper {

    private static ToneGenerator toneGen;

    static {
        try {
            toneGen = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 60);
        } catch (Exception ignored) {
            toneGen = null;
        }
    }

    private ChatSoundHelper() {
    }

    public static void playMessageSent(@Nullable View anchorView) {
        if (anchorView != null) {
            anchorView.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        }
        if (toneGen != null) {
            try {
                toneGen.startTone(ToneGenerator.TONE_PROP_ACK, 70);
            } catch (Exception ignored) {}
        }
    }

    public static void playMessageReceived(@Nullable Context context, @Nullable View anchorView) {
        if (anchorView != null) {
            anchorView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
        } else if (context != null) {
            try {
                Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                if (v != null && v.hasVibrator()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createWaveform(new long[]{0, 40, 60, 40}, -1));
                    } else {
                        v.vibrate(80);
                    }
                }
            } catch (Exception ignored) {}
        }

        if (toneGen != null) {
            try {
                toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 120);
            } catch (Exception ignored) {}
        }
    }
}
