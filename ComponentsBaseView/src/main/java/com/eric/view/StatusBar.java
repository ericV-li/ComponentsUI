package com.eric.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.eric.view.utils.StatusBarUtil;

/**
 * 状态栏控件，用于没有使用TitleBar下的沉浸式需求
 */

public class StatusBar extends LinearLayout {

    public StatusBar(Context context) {
        this(context, null);
    }

    public StatusBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedValue = context.obtainStyledAttributes(attrs, R.styleable.StatusBar);
        int color = typedValue.getColor(R.styleable.StatusBar_statusColor, Color.parseColor("#0093ff"));
        StatusBarUtil.setColor(getContext(), this);
        setBackgroundColor(color);
    }

}
