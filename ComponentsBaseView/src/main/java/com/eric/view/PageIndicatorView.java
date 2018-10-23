package com.eric.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * viewpager指示器。viewpager底部的小圆点，指示当前pager是第几个pager
 */
public class PageIndicatorView extends LinearLayout {

    /**
     * 上下文
     */
    private Context mContext;

    public PageIndicatorView(Context context) {
        this(context, null);
    }

    public PageIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 50));
        setGravity(Gravity.CENTER);
    }

    /**
     * 更新指示器个数
     *
     * @param number 指示器个数
     */
    public void updateIndicatorNum(int number) {
        removeAllViews();
        if (number > 1) {
            setBackgroundColor(Color.parseColor("#00000000"));
            float scale = getResources().getDisplayMetrics().density;
            int wh = (int) (5 * scale + 0.5f);
            LayoutParams param = new LayoutParams(wh, wh);
            param.setMargins(0, 0, 20, 0);
            ImageView view = null;
            for (int i = 0; i < number; i++) {
                view = new ImageView(mContext);
                view.setLayoutParams(param);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(getResources().getDrawable(R.drawable.baseview_bg_double_circle));
                } else {
                    view.setBackgroundDrawable(getResources().getDrawable(R.drawable.baseview_bg_double_circle));
                }
                addView(view);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                getChildAt(0).setBackground(getResources().getDrawable(R.drawable.baseview_bg_double_circle_selected));
            } else {
                getChildAt(0).setBackgroundDrawable(getResources().getDrawable(R.drawable.baseview_bg_double_circle_selected));
            }
        } else {
            setBackgroundColor(Color.parseColor("#00000000"));
        }
    }

    /**
     * 更新选中的指示器位置
     *
     * @param position 选中指示器位置
     */
    public void updateSelectedPosition(int position) {
        if (position >= 0 && getChildCount() > position) {
            for (int i = 0; i < getChildCount(); i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getChildAt(i).setBackground(getResources().getDrawable(R.drawable.baseview_bg_double_circle));
                } else {
                    getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.baseview_bg_double_circle));
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                getChildAt(position).setBackground(getResources().getDrawable(R.drawable.baseview_bg_double_circle_selected));
            } else {
                getChildAt(position).setBackgroundDrawable(getResources().getDrawable(R.drawable
                        .baseview_bg_double_circle_selected));
            }
        }
    }
}
