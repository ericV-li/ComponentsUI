package com.eric.view.utils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * @author li
 * @Package com.eric.view.utils
 * @Title: AndroidWorkaroundUtil
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 * 网上解决沉浸式与软件盘的adjustResize不兼容问题。
 * 在activity 的oncreate里添加
 * AndroidBug5497WorkaroundUtil.assistActivity(findViewById(android.R.id.content));
 */
public class AndroidWorkaroundUtil {
    public static void assistActivity(View content) {
        new AndroidWorkaroundUtil(content);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private ViewGroup.LayoutParams frameLayoutParams;

    private AndroidWorkaroundUtil(View content) {
        if (content != null) {
            mChildOfContent = content;
            mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    possiblyResizeChildOfContent();
                }
            });
            frameLayoutParams = mChildOfContent.getLayoutParams();
        }
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            //如果两次高度不一致
            //将计算的可视高度设置成视图的高度
            frameLayoutParams.height = usableHeightNow;
            mChildOfContent.requestLayout();//请求重新布局
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        //计算视图可视高度
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom);
    }
}
