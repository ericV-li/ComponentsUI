package com.eric.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;


/**
 * @author li
 * @Package com.eric.view.BadgeProgressView
 * @Title: BadgeProgressView
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 旋转进度条
 */
public class BadgeProgressView extends ProgressBar {

    public BadgeProgressView(Context context) {
        this(context, null);
    }

    public BadgeProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.progressBarStyle);
    }

    public BadgeProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 控件初始化
     */
    private void init() {
        setIndeterminateDrawable(getResources().getDrawable(R.drawable.baseview_progress_jh_anim));
        if (!(getLayoutParams() instanceof FrameLayout.LayoutParams)) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(android.view.ViewGroup.LayoutParams
                    .WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            setLayoutParams(layoutParams);
        }
    }

    /**
     * progress开始转圈
     */
    public void startProgress() {
        setVisibility(VISIBLE);
    }

    /**
     * progress停止转圈
     */
    public void stopProgress() {

        setVisibility(INVISIBLE);

    }

    /**
     * 设置菊花转圈控件的宽高 ，宽高相等
     *
     * @param size 宽高(dp)
     */
    public void setBadgeSize(int size) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.width = dip2Px(size);
        layoutParams.height = dip2Px(size);
        setLayoutParams(layoutParams);
    }


    /**
     * 设置离父控件margin
     *
     * @param dipMargin 距离（dip）
     */
    public void setBadgeMargin(int dipMargin) {
        setBadgeMargin(dipMargin, dipMargin, dipMargin, dipMargin);
    }

    /**
     * 设置离父控件margin
     *
     * @param leftDipMargin   左距离（dip）
     * @param topDipMargin    上距离（dip）
     * @param rightDipMargin  右距离（dip）
     * @param bottomDipMargin 低距离（dip）
     */
    public void setBadgeMargin(int leftDipMargin, int topDipMargin, int rightDipMargin, int bottomDipMargin) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        params.leftMargin = dip2Px(leftDipMargin);
        params.topMargin = dip2Px(topDipMargin);
        params.rightMargin = dip2Px(rightDipMargin);
        params.bottomMargin = dip2Px(bottomDipMargin);
        setLayoutParams(params);
    }

    /**
     * 将当前控件依附到目的控件上
     *
     * @param target 目标控件
     */
    public void setTargetView(View target) {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        if (target == null) {
            return;
        }
        if (target.getParent() instanceof FrameLayout) {
            ((FrameLayout) target.getParent()).addView(this);
        } else if (target.getParent() instanceof ViewGroup) {
            // use a new Framelayout container for adding badge
            ViewGroup parentContainer = (ViewGroup) target.getParent();
            int groupIndex = parentContainer.indexOfChild(target);
            parentContainer.removeView(target);
            FrameLayout badgeContainer = new FrameLayout(getContext());
            ViewGroup.LayoutParams parentLayoutParams = target.getLayoutParams();
            badgeContainer.setLayoutParams(parentLayoutParams);
            target.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                    .MATCH_PARENT));
            parentContainer.addView(badgeContainer, groupIndex, parentLayoutParams);
            badgeContainer.addView(target);
            badgeContainer.addView(this);
        }
    }

    /**
     * dip 转成 px
     *
     * @param dip
     * @return px
     */
    private int dip2Px(float dip) {
        return (int) (dip * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }
}
