package com.eric.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author li
 * @Package com.eric.view.ExpandGridView
 * @Title: ExpandGridView
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 解决List/ScrollView里嵌套GridView 滚动冲突问题。ps：内容不多的时候可用，内容过多不建议使用
 */

public class ExpandGridView extends GridView {

    public ExpandGridView(Context context) {
        super(context);
    }

    public ExpandGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
