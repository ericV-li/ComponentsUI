package com.eric.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * @author li
 * @Package com.eric.view.BadgeView
 * @Title: BadgeView
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 提示文案控件。 底部为黄色，中间文案，右边图标（小喇叭）
 */

public class BadgeView extends LinearLayout {

    /**
     * 文字内容
     */
    private TextView txtMsg;

    /**
     * 文字右边图片
     */
    private ImageView imgIcon;

    public BadgeView(Context context) {
        this(context, null);
    }

    public BadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.baseview_view_badge, this, true);
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(getResources().getColor(R.color.lj_color_light_yellow));
        txtMsg = (TextView) findViewById(R.id.txt_badge);
        imgIcon = (ImageView) findViewById(R.id.img_badge);
    }

    /**
     * 设置提示文案
     *
     * @param text 文案提示
     */
    public void setText(String text) {
        txtMsg.setText(text);
    }

    /**
     * 设置文案点击监听
     *
     * @param listener 监听
     */
    public void setOnClickListener(View.OnClickListener listener) {
        txtMsg.setOnClickListener(listener);
    }

    /**
     * 设置点击监听，并默认设置叉号图片
     *
     * @param listener 监听
     */
    public void setOnClearClickListener(View.OnClickListener listener) {
        if (listener != null) {
            imgIcon.setVisibility(VISIBLE);
            imgIcon.setImageDrawable(getResources().getDrawable(R.drawable.baseview_badge_clear));
            imgIcon.setOnClickListener(listener);
        }
    }

    /**
     * 设置点击监听，并默认设置向右箭头图片
     *
     * @param listener 监听
     */
    public void setOnNextClickListener(View.OnClickListener listener) {
        if (listener != null) {
            imgIcon.setVisibility(VISIBLE);
            imgIcon.setImageDrawable(getResources().getDrawable(R.drawable.baseview_badge_arrow));
            imgIcon.setOnClickListener(listener);
        }
    }

}
