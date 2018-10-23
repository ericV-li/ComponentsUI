package com.eric.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * @author li
 * @Package com.eric.view.ExpandListView
 * @Title: ExpandListView
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 扩展的listView用于处理scrollView或ListView 嵌套ListView的类。ps：内容不多的时候可用，内容过多不建议使用
 */

public class ExpandListView extends ListView implements AbsListView.OnScrollListener {

    public ExpandListView(Context context) {
        super(context);
        init(context);
    }

    public ExpandListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        // empty here
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}