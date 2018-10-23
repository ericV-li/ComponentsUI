package com.eric.view.wheelWidget;

import android.view.MotionEvent;

/**
 * @author li
 * @Package com.eric.view.wheelWidget
 * @Title: LoopViewGestureListener
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 */

public class LoopViewGestureListener extends android.view.GestureDetector.SimpleOnGestureListener{
    final WheelView loopView;

    LoopViewGestureListener(WheelView loopview) {
        loopView = loopview;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        loopView.scrollBy(velocityY);
        return true;
    }
}
