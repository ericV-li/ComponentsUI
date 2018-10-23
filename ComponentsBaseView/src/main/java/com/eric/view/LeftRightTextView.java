package com.eric.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author li
 * @Package com.eric.view.LeftRightTextView
 * @Title: LeftRightTextView
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 左侧一个textview 右侧一个textview 的封装，两个textview中间24dp
 */

public class LeftRightTextView extends RelativeLayout {

    /**
     * 左侧textview
     */
    private TextView mTextViewLeft;
    /**
     * 右侧textview
     */
    private TextView mTextViewRight;

    /**
     * 默认文字大小
     */
    private final int DEFAULT_TEXT_SIZE = 16;

    public LeftRightTextView(Context context) {
        this(context, null);
    }

    public LeftRightTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftRightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.baseview_view_left_right_text, this, true);
        mTextViewLeft = (TextView) findViewById(R.id.tv_left);
        mTextViewRight = (TextView) findViewById(R.id.tv_right);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LeftRightTextView);
        String leftStr = a.getString(R.styleable.LeftRightTextView_lrLeftText);
        int leftTextSize = a.getInt(R.styleable.LeftRightTextView_lrLeftTextSize, DEFAULT_TEXT_SIZE);
        ColorStateList leftTextColor = a.getColorStateList(R.styleable.LeftRightTextView_lrLeftTextColor);
        String rightStr = a.getString(R.styleable.LeftRightTextView_lrRightText);
        int rightTextSize = a.getInt(R.styleable.LeftRightTextView_lrRightTextSize, DEFAULT_TEXT_SIZE);
        ColorStateList rightTextColor = a.getColorStateList(R.styleable.LeftRightTextView_lrRightTextColor);
        //右侧text的gravity是否左侧。默认：左。true:left。 false: right
        boolean rightTextGravityLeft = a.getBoolean(R.styleable.LeftRightTextView_lrRightTextGravityLeft, true);
        boolean rightTextSingleLine = a.getBoolean(R.styleable.LeftRightTextView_lrRightTextSingleLine, false);
        a.recycle();
        mTextViewLeft.setText(leftStr);
        mTextViewLeft.setTextSize(leftTextSize);
        if (leftTextColor != null) {
            mTextViewLeft.setTextColor(leftTextColor);
        }
        mTextViewRight.setTextSize(rightTextSize);
        if (rightTextColor != null) {
            mTextViewRight.setTextColor(rightTextColor);
        }
        mTextViewRight.setText(rightStr);
        if (!rightTextGravityLeft) {
            mTextViewRight.setGravity(Gravity.RIGHT);
        }
        mTextViewRight.setSingleLine(rightTextSingleLine);
    }

    /**
     * 设置左侧textview文案
     *
     * @param leftText 左侧文案
     */
    public void setLeftText(String leftText) {
        mTextViewLeft.setText(leftText);
    }

    /**
     * 设置左侧textview文案
     *
     * @param resid 文案ID
     */
    public void setLeftText(int resid) {

        mTextViewLeft.setText(resid);
    }

    /**
     * 设置左侧textview文案
     *
     * @param leftText 文案CharSequence
     */
    public void setLeftText(CharSequence leftText) {
        mTextViewLeft.setText(leftText);
    }

    /**
     * 设置左侧textview颜色
     *
     * @param color 颜色
     */
    public void setLeftTextColor(int color) {
        mTextViewLeft.setTextColor(color);
    }

    /**
     * 设置左侧textview 文字尺寸
     *
     * @param size 文字大小sp
     */
    public void setLeftTextSize(float size) {
        mTextViewLeft.setTextSize(size);
    }

    /**
     * 设置右侧textview文案
     *
     * @param rightText 文案
     */
    public void setRightText(String rightText) {
        mTextViewRight.setText(rightText);
    }

    /**
     * 设置右侧textview文案
     *
     * @param resid 文案的string资源ID
     */
    public void setRightText(int resid) {
        mTextViewRight.setText(resid);
    }

    /**
     * 设置右侧textview文案
     *
     * @param rightText 文案
     */
    public void setRightText(CharSequence rightText) {
        mTextViewRight.setText(rightText);
    }

    /**
     * 设置右侧textview颜色
     *
     * @param color 颜色
     */
    public void setRightTextColor(int color) {
        mTextViewRight.setTextColor(color);
    }

    /**
     * 设置右侧textview 文字尺寸
     *
     * @param size 文字大小sp
     */
    public void setRightTextSize(float size) {
        mTextViewRight.setTextSize(size);
    }

    /**
     * 设置右侧textview的重力。
     *
     * @param gravity 重力
     */
    public void setRightTextGravity(int gravity) {
        mTextViewRight.setGravity(gravity);
    }
}
