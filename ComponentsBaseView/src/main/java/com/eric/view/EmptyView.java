package com.eric.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author li
 * @Package com.eric.view.EmptyView
 * @Title: EmptyView
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 请求列表接口，得到数据为null 和 请求数据出错情况下展示的view
 */

public class EmptyView extends RelativeLayout {

    /**
     * 中间的图片
     */
    private ImageView mIcon;

    /**
     * 图片下面的内容
     */
    private TextView mMsg;

    /**
     * 按钮。正常都是点击刷新用
     */
    private TextView mTextViewRefresh;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
        LayoutInflater.from(context).inflate(R.layout.baseview_view_empty, this, true);
        mIcon = (ImageView) findViewById(R.id.img_icon);
        mMsg = (TextView) findViewById(R.id.txt_msg);
        mTextViewRefresh = (TextView) findViewById(R.id.txt_refresh);
    }

    /**
     * 获取图片引用ImageView
     * @return ImageView
     */
    public ImageView getImageView() {
        return mIcon;
    }

    /**
     * 获取显示文字的TextView
     * @return TextView
     */
    public TextView getTextView() {
        return mMsg;
    }

    /**
     * 获取 TextViewRefresh
     *
     * @return TextView
     */
    public TextView getTextViewRefresh() {
        return mTextViewRefresh;
    }

    /**
     * 设置图标
     *
     * @param drawable 中间图片
     */
    public void setImage(Drawable drawable) {
        mIcon.setImageDrawable(drawable);
    }

    /**
     * 设置图标
     *
     * @param resId 图片资源id
     */
    public void setImage(int resId) {
        mIcon.setImageResource(resId);
    }

    /**
     * 设置文字描述
     *
     * @param resId id
     */
    public void setMsg(int resId) {
        mMsg.setText(resId);
        mMsg.setVisibility(View.VISIBLE);
    }

    /**
     * 设置空页面文字
     * @param msg 文字内容
     */
    public void setMsg(String msg) {
        mMsg.setText(msg);
        mMsg.setVisibility(View.VISIBLE);
    }

    /**
     * 设置TextViewRefresh的按钮文字
     *
     * @param resId id
     */
    public void setText(int resId) {
        mTextViewRefresh.setVisibility(VISIBLE);
        mTextViewRefresh.setText(resId);
    }

    /**
     * 设置TextViewRefresh的按钮文字
     *
     * @param text text
     */
    public void setText(String text) {
        mTextViewRefresh.setVisibility(VISIBLE);
        mTextViewRefresh.setText(text);
    }
}
