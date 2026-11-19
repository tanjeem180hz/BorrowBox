package com.borrowbox.app.ui.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * Draggable before/after condition comparison slider.
 */
public class ComparisonSliderView extends View {

    private Bitmap beforeBitmap;
    private Bitmap afterBitmap;
    private float sliderPosition = 0.5f; // 0.0 to 1.0
    private Paint linePaint;
    private Paint circlePaint;
    private Rect srcRect;
    private Rect dstRect;

    public ComparisonSliderView(Context context) {
        super(context);
        init();
    }

    public ComparisonSliderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ComparisonSliderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(6f);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.WHITE);

        srcRect = new Rect();
        dstRect = new Rect();
    }

    public void setBitmaps(Bitmap before, Bitmap after) {
        this.beforeBitmap = before;
        this.afterBitmap = after;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            sliderPosition = Math.max(0.05f, Math.min(0.95f, event.getX() / getWidth()));
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        if (w == 0 || h == 0) return;

        int splitX = (int) (w * sliderPosition);

        // Draw Before Bitmap (left side)
        if (beforeBitmap != null) {
            canvas.save();
            canvas.clipRect(0, 0, splitX, h);
            srcRect.set(0, 0, beforeBitmap.getWidth(), beforeBitmap.getHeight());
            dstRect.set(0, 0, w, h);
            canvas.drawBitmap(beforeBitmap, srcRect, dstRect, null);
            canvas.restore();
        } else {
            canvas.save();
            canvas.clipRect(0, 0, splitX, h);
            canvas.drawColor(Color.LTGRAY);
            canvas.restore();
        }

        // Draw After Bitmap (right side)
        if (afterBitmap != null) {
            canvas.save();
            canvas.clipRect(splitX, 0, w, h);
            srcRect.set(0, 0, afterBitmap.getWidth(), afterBitmap.getHeight());
            dstRect.set(0, 0, w, h);
            canvas.drawBitmap(afterBitmap, srcRect, dstRect, null);
            canvas.restore();
        } else {
            canvas.save();
            canvas.clipRect(splitX, 0, w, h);
            canvas.drawColor(Color.DKGRAY);
            canvas.restore();
        }

        // Draw divider slider line and handle
        canvas.drawLine(splitX, 0, splitX, h, linePaint);
        canvas.drawCircle(splitX, h / 2f, 24f, circlePaint);
    }
}
