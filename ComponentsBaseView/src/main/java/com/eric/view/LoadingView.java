package com.eric.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @author li
 * @Package com.eric.view.LoadingView
 * @Title: LoadingView
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 加载动画
 */

public class LoadingView extends RelativeLayout {

    /**
     * 中间的图片
     */
    private ImageView mImageViewCenter;

    /**
     * 转圈
     */
    private ProgressView mLJProgressView;

    /**
     * 控件最外层视图容器
     */
    private RelativeLayout mRelativeLayoutContainer;

    /**
     * 依附控件的父控件
     */
    private ViewGroup mViewParent;

    /**
     * 转圈的属性动画
     */
    private ObjectAnimator mObjectAnimator;

    /**
     * 屏幕密度
     */
    private float mDensity;


    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mDensity = getResources().getDisplayMetrics().density;
    }

    /**
     * 初始化view
     */
    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.baseview_view_lj_progress_bar, this, true);
        mRelativeLayoutContainer = (RelativeLayout) view.findViewById(R.id.rly_container);
        mImageViewCenter = (ImageView) view.findViewById(R.id.img_center);
        mLJProgressView = (ProgressView) view.findViewById(R.id.lj_progress_view);
        mObjectAnimator = ObjectAnimator.ofFloat(mLJProgressView, "rotation", 0, 360);
        mObjectAnimator.setDuration(2000);
        mObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        ViewTreeObserver observer = mRelativeLayoutContainer.getViewTreeObserver();
        if (observer != null) {
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (getMeasuredHeight() != 0) {
                        mLJProgressView.setStrokeWidth(mRelativeLayoutContainer.getMeasuredWidth() / 25);
                        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mImageViewCenter.getLayoutParams();
                        layoutParams.width = mRelativeLayoutContainer.getMeasuredWidth() * 3 / 5;
                        layoutParams.height = mRelativeLayoutContainer.getMeasuredHeight() * 3 / 5;
                        mImageViewCenter.setLayoutParams(layoutParams);
                        mImageViewCenter.setVisibility(VISIBLE);
                        mLJProgressView.setVisibility(VISIBLE);
                        ViewTreeObserver observer = getViewTreeObserver();
                        if (observer != null) {
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                observer.removeGlobalOnLayoutListener(this);
                            } else {
                                observer.removeOnGlobalLayoutListener(this);
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            mObjectAnimator.start();
        } else {
            mObjectAnimator.cancel();
        }
    }

    /**
     * 将LJLoadingView依附到控件上显示
     *
     * @param view 想要依附的目标控件
     */
    public void attachToWindowCenter(View view) {
        this.attachToView(view, 50, 50, 0, 0, 0, 0);
    }

    /**
     * 将LJLoadingView依附到控件上显示
     *
     * @param view   想要依附的目标控件
     * @param width  控件宽度  dp
     * @param height 控件高度  dp
     */
    public void attachToWindowCenter(View view, int width, int height) {
        this.attachToView(view, width, height, 0, 0, 0, 0);
    }

    /**
     * 将LJLoadingView在屏幕上移除
     */
    public void detachFromWindow() {
        detachFromView();
    }

    /**
     * 将LJLoadingView 依附到控件上显示
     *
     * @param view 要显示LJLoadingView的View
     */
    public void attachToView(View view) {
        this.attachToView(view, 0, 0);
    }

    /**
     * 将LJLoadingView 依附到控件上显示
     *
     * @param view   想要依附的目标控件
     * @param width  控件宽度  dp
     * @param height 控件高度  dp
     */
    public void attachToView(View view, int width, int height) {
        this.attachToView(view, width, height, 0, 0, 0, 0);
    }

    /**
     * 将LJLoadingView 依附到控件上显示
     *
     * @param view         想要依附的目标控件
     * @param marginLeft   距离左边距离
     * @param marginTop    距离上边距离
     * @param marginRight  距离右边距离
     * @param marginBottom 距离下边距离
     */
    public void attachToView(View view, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        this.attachToView(view, 0, 0, marginLeft, marginTop, marginRight, marginBottom);
    }

    /**
     * 将LJLoadingView 显示到控件上  根据margin自动控制大小与位置，会显示到控件的中间.
     * ps:只能被attach一次
     *
     * @param view         要显示LJLoadingView的View
     * @param marginLeft   marginLeft
     * @param marginTop    marginTop
     * @param marginRight  marginRight
     * @param marginBottom marginBottom
     */
    public void attachToView(View view, int width, int height, int marginLeft, int marginTop, int marginRight, int marginBottom) {

        if (view == null) {
            return;
        }

        if (mViewParent == null) {
            mViewParent = (ViewGroup) view.getParent();
        }
        if (mViewParent.indexOfChild(this) >= 0) {
            if (!mRelativeLayoutContainer.isShown()) {
                mRelativeLayoutContainer.setVisibility(VISIBLE);
            }
            return;
        }
        int viewWidth;
        int viewHeight;
        if (width == 0 || height == 0) {
            viewWidth = view.getWidth();
            viewHeight = view.getHeight();
        } else {
            viewWidth = (int) (width * mDensity + 0.5);
            viewHeight = (int) (height * mDensity + 0.5);
        }
        int progressBarWidth = (int) (viewWidth - (marginLeft + marginRight) * mDensity);
        int progressBarHeight = (int) (viewHeight - (marginTop + marginBottom) * mDensity);
        if (mViewParent instanceof RelativeLayout) {
            this.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                    .MATCH_PARENT));
            mViewParent.addView(this);
            RelativeLayout.LayoutParams layoutParams;
            if (progressBarWidth < progressBarHeight) {
                layoutParams = new RelativeLayout.LayoutParams(progressBarWidth, progressBarWidth);
                layoutParams.setMargins(view.getLeft() + (marginLeft + marginRight) / 2, view.getTop() + (viewHeight / 2 -
                        progressBarWidth / 2), 0, 0);
            } else {
                layoutParams = new RelativeLayout.LayoutParams(progressBarHeight, progressBarHeight);
                layoutParams.setMargins(view.getLeft() + viewWidth / 2 - progressBarHeight / 2, view.getTop() + (marginTop +
                        marginBottom) / 2, 0, 0);
            }
            mRelativeLayoutContainer.setLayoutParams(layoutParams);
            mObjectAnimator.start();
        } else {
            int index = mViewParent.indexOfChild(view);
            setLayoutParams(view.getLayoutParams());
            mViewParent.removeView(view);
            mViewParent.addView(this, index);
            view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            addView(view, 0);
            RelativeLayout.LayoutParams layoutParams;
            layoutParams = new RelativeLayout.LayoutParams(progressBarWidth < progressBarHeight ? progressBarWidth :
                    progressBarHeight, progressBarWidth < progressBarHeight ? progressBarWidth : progressBarHeight);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mRelativeLayoutContainer.setLayoutParams(layoutParams);
            mObjectAnimator.start();
        }
    }

    /**
     * 将LJLoadingView 从view上移除
     */
    public void detachFromView() {
        mRelativeLayoutContainer.setVisibility(GONE);
        mObjectAnimator.cancel();
    }
}
