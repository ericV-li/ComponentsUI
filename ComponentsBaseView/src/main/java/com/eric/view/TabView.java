package com.eric.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eric.view.entity.ListItem;

import java.util.ArrayList;
import java.util.List;
/**
 * @author li
 * @Package com.eric.view.TabView
 * @Title: TabView
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * Tab控件 在数据个数多余4个的时候使用。
 */

public class TabView extends LinearLayout {

    /**
     * 样式：分段
     */
    private static int STYLE_SECTION = 0;
    /**
     * 样式：切换
     */
    private static int STYLE_SWITCH = 1;

    /**
     * 蓝底
     */
    private static int THEME_BLUE = 0;

    /**
     * 白底
     */
    private static int THEME_WHITE = 1;

    /**
     * 横向滚动控件
     */
    private HorizontalScrollView mHorizontalScrollView;

    /**
     * 内容容器
     */
    private LinearLayout mLinearLayout;

    /**
     * 当前选中的item
     */
    private RelativeLayout mCurrentItem;

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * item点击监听
     */
    private OnItemSelectListener mListener;

    /**
     * 填充器
     */
    private LayoutInflater mLayoutInflater;

    /**
     * 屏幕宽度的一半
     */
    private int mHalfScreenWidth;

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    /**
     * 当前X轴滚动的距离
     */
    private int mCurrentScrollerX;

    /**
     * 初始化position， 默认点击哪个Tab
     */
    private int mSelectPosition = 0;

    /**
     * 主题
     */
    private int mSectionTabViewTheme;

    /**
     * 样式
     */
    private int mSectionTabViewStyle;


    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.baseview_view_horizon_scroll_indicator, this, true);
        initData(context);
        initView(attrs);
    }

    private void initData(Context context) {
        mContext = context;
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        mHalfScreenWidth = mScreenWidth / 2;
        mLayoutInflater = LayoutInflater.from(context);
    }

    private void initView(AttributeSet attrs) {
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        mLinearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        TypedArray typedValue = mContext.obtainStyledAttributes(attrs, R.styleable.TabView);
        mSectionTabViewTheme = typedValue.getInt(R.styleable.TabView_tabViewTheme, THEME_BLUE);
        mSectionTabViewStyle = typedValue.getInt(R.styleable.TabView_tabViewStyle, STYLE_SECTION);
        if (mSectionTabViewTheme == THEME_BLUE) {
            mHorizontalScrollView.setBackgroundColor(getResources().getColor(R.color.lj_color_blue));
        } else {
            mHorizontalScrollView.setBackgroundColor(getResources().getColor(R.color.lj_color_white));
        }
    }

    /**
     * 设置控件背景色
     *
     * @param color 颜色resId
     */
    public void setWidgetBackground(int color) {
        try {
            mHorizontalScrollView.setBackgroundColor(getResources().getColor(color));
        } catch (Exception e) {
            // empty
        }
    }

    /**
     * 设置要展示的内容
     *
     * @param contents 内容
     */
    public void setDataString(List<String> contents) {
        if (contents == null || contents.isEmpty()) {
            return;
        }
        List<ListItem> list = new ArrayList<>();
        for (String temp : contents) {
            ListItem listItem = new ListItem();
            listItem.setTitle(temp);
            list.add(listItem);
        }
        inflateViews(list);
    }

    /**
     * 设置要展示的内容
     *
     * @param contents 内容
     */
    public void setDataListItem(List<ListItem> contents) {
        if (contents == null || contents.isEmpty()) {
            return;
        }
        inflateViews(contents);
    }

    /**
     * 获取选中item的索引
     *
     * @return 选中item的索引
     */
    public int getTabSelectPosition() {
        return mSelectPosition;
    }

    /**
     * 选择Tab
     *
     * @param position             0 < position  <data.size
     * @param isNeedSelectCallBack 是否需要选中回调
     */
    public void setTabSelect(int position, boolean isNeedSelectCallBack) {
        mSelectPosition = position;
        if (mSelectPosition < 0 || mSelectPosition >= mLinearLayout.getChildCount()) {
            return;
        }
        if (isNeedSelectCallBack) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mLinearLayout.getChildAt(mSelectPosition).performClick();
                }
            });
        } else {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    adjustItemView(mLinearLayout.getChildAt(mSelectPosition));
                }
            });
        }
    }

    /**
     * 根据Data数据，填充View。
     *
     * @param contents Data
     */
    private void inflateViews(final List<ListItem> contents) {

        mLinearLayout.removeAllViews();
        for (int i = 0; i < contents.size(); i++) {
            View tabItem;
            //根据不同Style，加载不同布局
            TextView textView;
            if (mSectionTabViewStyle == STYLE_SECTION) {
                //主题分段
                tabItem = mLayoutInflater.inflate(R.layout.baseview_view_tab_item, null);
                textView = (TextView) tabItem.findViewById(R.id.txt_item);
                View viewIndicator = tabItem.findViewById(R.id.view_item_divider);
                //根据不同theme，设置不同的颜色
                if (mSectionTabViewTheme == THEME_BLUE) {
                    textView.setTextColor(getResources().getColor(R.color.baseview_tab_view_white_60alpha));
                    viewIndicator.setBackgroundColor(getResources().getColor(R.color.lj_color_white));
                } else {
                    textView.setTextColor(getResources().getColor(R.color.lj_color_black_light));
                    viewIndicator.setBackgroundColor(getResources().getColor(R.color.lj_color_blue));
                }
            } else {
                //主题切换
                tabItem = mLayoutInflater.inflate(R.layout.baseview_view_switch_item, null);
                textView = (TextView) tabItem.findViewById(R.id.txt_item);
                //根据不同theme，设置不同的颜色
                if (mSectionTabViewTheme == THEME_BLUE) {
                    textView.setTextColor(getResources().getColor(R.color.baseview_tab_view_white_60alpha));
                    textView.setBackgroundResource(R.drawable.baseview_tabview_rect_white);
                } else {
                    textView.setTextColor(getResources().getColor(R.color.lj_color_black_light));
                    textView.setBackgroundResource(R.drawable.baseview_tabview_rect_blue);
                }

            }
            textView.setText(contents.get(i).getTitle());
            mLinearLayout.addView(tabItem);
            tabItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //当前item点击不进行刷新，直接返回
                    if (mCurrentItem == v) {
                        return;
                    }
                    adjustItemView(v);
                    mSelectPosition = getPosition(v);
                    if (mListener != null) {
                        //设置监听，进行回调。
                        mListener.onItemSelect(mSelectPosition, contents.get(mSelectPosition));
                    }
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            //对view进行滚动
                            scrollToHead(mSelectPosition);
                        }
                    });
                }
            });
        }
    }

    /**
     * 获取view 位置
     *
     * @param view view
     * @return position
     */
    private int getPosition(View view) {
        int position = -1;
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            if (view == mLinearLayout.getChildAt(i)) {
                return i;
            }
        }
        return position;
    }

    /**
     * 点击后，调整item的显示效果
     *
     * @param v 调整item
     */
    private void adjustItemView(View v) {
        if (mSectionTabViewStyle == STYLE_SECTION) {
            //主题 分段
            if (mCurrentItem != null) {
                if (mSectionTabViewTheme == THEME_BLUE) {
                    ((TextView) mCurrentItem.getChildAt(0)).setTextColor(getResources().getColor(R.color
                            .baseview_tab_view_white_60alpha));
                } else {
                    ((TextView) mCurrentItem.getChildAt(0)).setTextColor(getResources().getColor(R.color.lj_color_black_light));
                }
                if (mCurrentItem.getChildAt(1) != null) {
                    mCurrentItem.getChildAt(1).setVisibility(INVISIBLE);
                }
            }
            mCurrentItem = ((RelativeLayout) v);
            if (mCurrentItem.getChildAt(1) != null) {
                mCurrentItem.getChildAt(1).setVisibility(VISIBLE);//mCurrentItem.getChildAt(1)是item下面的那根线
            }
            if (mSectionTabViewTheme == THEME_BLUE) {
                ((TextView) mCurrentItem.getChildAt(0)).setTextColor(getResources().getColor(R.color.lj_color_white));
            } else {
                ((TextView) mCurrentItem.getChildAt(0)).setTextColor(getResources().getColor(R.color.lj_color_blue));
            }
        } else {
            //主题 切换
            if (mCurrentItem != null) {
                mCurrentItem.getChildAt(0).setSelected(false);
                if (mSectionTabViewTheme == THEME_BLUE) {
                    ((TextView) mCurrentItem.getChildAt(0)).setTextColor(getResources().getColor(R.color
                            .baseview_tab_view_white_60alpha));
                } else {
                    ((TextView) mCurrentItem.getChildAt(0)).setTextColor(getResources().getColor(R.color.lj_color_black_light));
                }
            }
            mCurrentItem = ((RelativeLayout) v);
            if (mCurrentItem.getChildAt(0) != null) {
                mCurrentItem.getChildAt(0).setSelected(true);
            }
            if (mSectionTabViewTheme == THEME_BLUE) {
                ((TextView) mCurrentItem.getChildAt(0)).setTextColor(getResources().getColor(R.color.lj_color_blue));
            } else {
                ((TextView) mCurrentItem.getChildAt(0)).setTextColor(getResources().getColor(R.color.lj_color_white));
            }
        }
    }

    /**
     * 根据position位置的item 滚动到最前面
     *
     * @param position 位置
     */
    private void scrollToHead(int position) {
        if (position == 0) {
            mHorizontalScrollView.scrollTo(0, 0);
            return;
        }
        int scrollX = 0;
        for (int i = 0; i < position; i++) {
            scrollX += mLinearLayout.getChildAt(i).getWidth();
        }
        scrollX -= mHalfScreenWidth;//滚到屏幕中间
        mCurrentScrollerX = mHorizontalScrollView.getScrollX();
        int dx = scrollX - mCurrentScrollerX;
        mHorizontalScrollView.smoothScrollTo(mCurrentScrollerX + dx, 0);
    }

    /**
     * 设置选择item的监听
     *
     * @param listener 监听器
     */
    public void setOnSelectListener(OnItemSelectListener listener) {
        mListener = listener;
    }

    /**
     * 选择item接口
     */
    public interface OnItemSelectListener {

        void onItemSelect(int position, ListItem listItem);
    }
}
