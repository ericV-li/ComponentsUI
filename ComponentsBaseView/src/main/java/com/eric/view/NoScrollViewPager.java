package com.eric.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author li
 * @Package com.eric.view.NoScrollViewPager
 * @Title: NoScrollViewPager
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 不可左右滑动的viewpager
 */

public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }


    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    /**
     * 触摸事件方法。对所以事件不做处理
     *
     * @param arg0 触摸事件
     * @return 是否消费事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    /**
     * 拦截事件方法。不拦截事件
     *
     * @param arg0 触摸事件
     * @return 是否拦截事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }
}
