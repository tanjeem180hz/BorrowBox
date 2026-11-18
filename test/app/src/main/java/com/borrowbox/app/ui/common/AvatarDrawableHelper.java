package com.borrowbox.app.ui.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.borrowbox.app.R;
import com.borrowbox.app.model.Student;

public class AvatarDrawableHelper {

    private static final int[] AVATAR_PALETTE = {
            0xFF4F46E5, // Indigo
            0xFF059669, // Emerald
            0xFFD97706, // Amber
            0xFFDC2626, // Rose
            0xFF7C3AED, // Violet
            0xFF0284C7  // Sky
    };

    /**
     * Creates a high-resolution circular avatar BitmapDrawable suitable for BottomNavigationView.
     *
     * @param context    Android context
     * @param student    The active student
     * @param sizeDp     Desired size in density-independent pixels (e.g. 26dp)
     * @param isSelected Whether the profile tab is currently selected
     * @return Circular avatar Drawable
     */
    public static Drawable createAvatarDrawable(Context context, Student student, int sizeDp, boolean isSelected) {
        float density = context.getResources().getDisplayMetrics().density;
        int sizePx = Math.round(sizeDp * density);
        if (sizePx <= 0) sizePx = 72;

        Bitmap bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        float center = sizePx / 2.0f;
        float radius = center - (isSelected ? 2.5f * density : 0.5f * density);

        // Pick distinct color from user id or name
        int bgColor = AVATAR_PALETTE[0];
        String initial = "U";

        if (student != null) {
            long id = student.getId();
            bgColor = AVATAR_PALETTE[(int) (Math.abs(id) % AVATAR_PALETTE.length)];
            String name = student.getName();
            if (name != null && !name.trim().isEmpty()) {
                initial = name.trim().substring(0, 1).toUpperCase(java.util.Locale.ROOT);
            }
        }

        // 1. Draw outer ring indicator if selected (Instagram style)
        if (isSelected) {
            Paint ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            ringPaint.setStyle(Paint.Style.STROKE);
            ringPaint.setStrokeWidth(2.0f * density);
            int primaryColor = ContextCompat.getColor(context, R.color.brand_primary);
            ringPaint.setColor(primaryColor);
            canvas.drawCircle(center, center, center - (1.0f * density), ringPaint);
        }

        // 2. Draw circular avatar background
        Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(bgColor);
        canvas.drawCircle(center, center, radius, bgPaint);

        // 3. Draw initial text centered
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(0xFFFFFFFF);
        textPaint.setTextSize(radius * 1.05f);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        textPaint.setTextAlign(Paint.Align.CENTER);

        Rect bounds = new Rect();
        textPaint.getTextBounds(initial, 0, initial.length(), bounds);
        float textY = center + (bounds.height() / 2.0f) - bounds.bottom;
        canvas.drawText(initial, center, textY, textPaint);

        return new BitmapDrawable(context.getResources(), bitmap);
    }
}
