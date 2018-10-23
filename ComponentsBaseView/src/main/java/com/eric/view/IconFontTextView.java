package com.eric.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * @author li
 * @Package com.eric.view.IconFontTextView
 * @Title: IconFontTextView
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 支持iconFont的textView
 */

public class IconFontTextView extends AppCompatTextView {
    public IconFontTextView(Context context) {
        this(context, null);
    }

    public IconFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IconFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.i("text",getContext().getAssets().toString());
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/iconFont.ttf");
        setTypeface(tf);
    }
}
