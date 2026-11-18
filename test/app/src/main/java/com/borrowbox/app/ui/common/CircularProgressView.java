package com.borrowbox.app.ui.common;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.borrowbox.app.R;

public class CircularProgressView extends View {

    private Paint backgroundPaint;
    private Paint progressPaint;
    private RectF rectF;
    private float progress = 0f; // 0 to 100
    private float strokeWidth = 24f;

    public CircularProgressView(Context context) {
        super(context);
        init();
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rectF = new RectF();

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.brand_tint));

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setColor(ContextCompat.getColor(getContext(), R.color.brand_primary));
    }

    public void setProgress(float progress) {
        this.progress = Math.max(0f, Math.min(100f, progress));
        invalidate();
    }

    public void animateProgress(float targetProgress) {
        ValueAnimator animator = ValueAnimator.ofFloat(this.progress, targetProgress);
        animator.setDuration(1200);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            this.progress = (float) animation.getAnimatedValue();
            invalidate();
        });
        animator.start();
    }

    public void setProgressColor(int color) {
        progressPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float pad = strokeWidth / 2f + 8f;
        rectF.set(pad, pad, getWidth() - pad, getHeight() - pad);

        // Draw background circle
        canvas.drawOval(rectF, backgroundPaint);

        // Draw progress arc (-90 degrees is 12 o'clock)
        float sweepAngle = (progress / 100f) * 360f;
        canvas.drawArc(rectF, -90, sweepAngle, false, progressPaint);
    }
}
