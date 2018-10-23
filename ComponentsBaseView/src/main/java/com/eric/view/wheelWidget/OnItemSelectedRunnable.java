package com.eric.view.wheelWidget;

/**
 * @author li
 * @Package com.eric.view.wheelWidget
 * @Title: OnItemSelectedRunnable
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 */
public class OnItemSelectedRunnable implements Runnable {
    WheelView loopView;

    public OnItemSelectedRunnable(WheelView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        loopView.onItemSelectedListener.onItemSelected(loopView.getCurrentItem());
    }
}
