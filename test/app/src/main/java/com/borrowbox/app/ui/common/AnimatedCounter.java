package com.borrowbox.app.ui.common;

import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

public final class AnimatedCounter {

    private AnimatedCounter() {
    }

    public static void animateValue(TextView textView, int start, int end, String prefix, String suffix) {
        if (textView == null) return;
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(1000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            int val = (int) animation.getAnimatedValue();
            textView.setText(prefix + val + suffix);
        });
        animator.start();
    }
}
