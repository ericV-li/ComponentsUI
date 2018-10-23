package com.eric.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @author li
 * @Package com.eric.view.RefreshListView
 * @Title: RefreshListView
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * ListView的封装。
 */

public class RefreshListView extends ListView implements OnScrollListener {

    /**
     * 上下文
     */
    private Context mContext;

    public RefreshListView(Context context) {
        super(context);
        init(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        ImageView imageView = null;
        if (getHeaderViewsCount() == 0 && getFooterViewsCount() == 0) {
            // 添加高度和宽度为0的空view，屏蔽addFooterView和addHeadView必须要在setAdapter前调用问题
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new LayoutParams(0, 0));
            addFooterView(imageView, null, false);
            //imageView.setVisibility(GONE);
        }
        super.setAdapter(adapter);
        if (imageView != null) {
            removeFooterView(imageView);
        }
    }

    /**
     * 绘制分发方法，这边用于捕获 IndexOutOfBoundsException异常
     *
     * @param canvas 画布
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        try {
            super.dispatchDraw(canvas);
        } catch (IndexOutOfBoundsException e) {
            Log.e("RefreshListView", "Ignore list view error ->" + e.toString());
        } catch (Exception e) {
            Log.e("RefreshListView", "DispatchDraw error ->" + e.toString());
        }
    }

}
