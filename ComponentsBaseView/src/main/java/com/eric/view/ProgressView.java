package com.eric.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * @author li
 * @Package com.eric.view.ProgressView
 * @Title: ProgressView
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 转圈的控件，需要与LoadingView  结合使用
 */

public class ProgressView extends View {

    /**
     * 矩形区域，用于确定绘制转圈的范围。
     */
    private RectF mRectF;

    /**
     * 画笔，用于绘制转圈背景色
     */
    private Paint mPaintBackground;

    /**
     * 画笔，用于绘制转圈前景色
     */
    private Paint mPaintForeground;

    /**
     * 选择角度
     */
    private float mRotate;

    /**
     * 屏幕密度
     */
    private float mDensity;

    /**
     * 圈的宽度
     */
    private int mStrokeWidth;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDensity = getResources().getDisplayMetrics().density;
        mStrokeWidth = (int) (4 * mDensity);
        mPaintBackground = new Paint();
        mPaintBackground.setColor(getResources().getColor(R.color.lj_color_divider));
        mPaintBackground.setStyle(Paint.Style.STROKE);
        mPaintBackground.setStrokeWidth(mStrokeWidth);
        mPaintBackground.setAntiAlias(true);
        mPaintBackground.setStrokeCap(Paint.Cap.ROUND);
        mPaintForeground = new Paint();
        mPaintForeground.setColor(getResources().getColor(R.color.lj_color_blue));
        mPaintForeground.setStyle(Paint.Style.STROKE);
        mPaintForeground.setStrokeWidth(mStrokeWidth);
        mPaintForeground.setAntiAlias(true);
        mPaintForeground.setStrokeCap(Paint.Cap.ROUND);
        ViewTreeObserver observer = getViewTreeObserver();
        if (observer != null) {
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (getMeasuredHeight() != 0) {
                        mRectF = new RectF(0, 0, getWidth(), getHeight());
                        mRectF.inset(mStrokeWidth / 2, mStrokeWidth / 2);
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        if (mRectF == null) {
            mRectF = new RectF(0, 0, getWidth(), getHeight());
            mRectF.inset(mStrokeWidth / 2, mStrokeWidth / 2);
        }
        if (mRotate < 180) {
            canvas.drawArc(mRectF, -90, mRotate, false, mPaintBackground);
        } else {
            if (mRotate < 270) {
                canvas.drawArc(mRectF, mRotate / 2 - 180, mRotate / 2 + 90, false, mPaintBackground);
                canvas.drawArc(mRectF, mRotate / 2 - 180, 4 * mRotate / 3 - 240, false, mPaintForeground);
            } else {
                canvas.drawArc(mRectF, mRotate * 7 / 2 - 990, 900 - mRotate * 5 / 2, false, mPaintBackground);
                canvas.drawArc(mRectF, mRotate * 7 / 2 - 990, 480 - mRotate * 4 / 3, false, mPaintForeground);
            }
        }
    }

    @Override
    public void setRotation(float rotation) {
        mRotate = rotation;
        invalidate();
    }

    /**
     * 设置圈的宽
     *
     * @param mStrokeWidth 宽度
     */
    public void setStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        mPaintBackground.setStrokeWidth(mStrokeWidth);
        mPaintForeground.setStrokeWidth(mStrokeWidth);
    }
}
