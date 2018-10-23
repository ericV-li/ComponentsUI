package com.eric.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eric.view.entity.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *  @author li
 *  @Package com.eric.view.AverageTabView
 *  @Title: AverageTabView
 *  @Description: Copyright (c)
 *  Create DateTime: 2015/10/19
 *
 *  均分Tab控件 每个Item(内容)占用的空间均分父width
 */
public class AverageTabView extends LinearLayout {

    /**
     * 当前选中的item的Layout
     */
    private RelativeLayout mCurrentItem;

    /**
     * 默认选中的tab的position
     */
    private int mSelectPosition = 0;

    /**
     * LayoutInflater
     */
    private LayoutInflater mLayoutInflater;

    /**
     * tab选中的回调
     */
    private OnItemSelectListener mListener;

    /**
     * 底部颜色，默认蓝色#0093ff
     */
    private int mBackgroundColor;

    /**
     * 选中item的text颜色。默认白色
     */
    private int mItemTextSelectColor;

    /**
     * 未选中item的text颜色。默认蓝色 #0093ff
     */
    private int mItemTextNoSelectColor;

    /**
     * Tab底部指示线颜色。默认白色。
     */
    private int mItemIndicatorDividerColor;

    /**
     * Tab底部指示线类型。0：INDICATOR_TYPE_MATCH_PARENT ，1：INDICATOR_TYPE_WRAP_CONTENT
     */
    private int mItemIndicatorType;

    /**
     * 指示线MATCH_PARENT。指示线均分父布局宽度。
     */
    private final int INDICATOR_TYPE_MATCH_PARENT = 0;

    /**
     * 指示线WRAP_CONTENT。指示线跟tab内容长度一样
     */
    private final int INDICATOR_TYPE_WRAP_CONTENT = 1;

    public AverageTabView(Context context) {
        this(context, null);
    }

    public AverageTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AverageTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
        initView(attrs);
    }

    /**
     * 初始化数据
     *
     * @param context 上下文
     */
    private void initData(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mBackgroundColor = getResources().getColor(R.color.lj_color_blue);
        mItemTextSelectColor = getResources().getColor(R.color.lj_color_white);
        mItemTextNoSelectColor = getResources().getColor(R.color.baseview_tab_view_white_60alpha);
        mItemIndicatorDividerColor = getResources().getColor(R.color.lj_color_white);
    }

    /**
     * 初始化控件布局
     *
     * @param attrs 属性
     */
    private void initView(AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AverageTabView);
        mBackgroundColor = typedArray.getColor(R.styleable.AverageTabView_aTabBackgroundColor, mBackgroundColor);
        mItemTextSelectColor = typedArray.getColor(R.styleable.AverageTabView_aTabSelectTextColor, mItemTextSelectColor);
        mItemTextNoSelectColor = typedArray.getColor(R.styleable.AverageTabView_aTabUnSelectTextColor, mItemTextNoSelectColor);
        mItemIndicatorDividerColor = typedArray.getColor(R.styleable.AverageTabView_aTabIndicatorDividerColor,
                mItemIndicatorDividerColor);
        mItemIndicatorType = typedArray.getInt(R.styleable.AverageTabView_aTabIndicatorDividerType, 0);
        setBackgroundColor(mBackgroundColor);
    }

    /**
     * 设置item被选中的文字颜色。
     *
     * @param itemTextSelectColor 颜色
     */
    public void setItemTextSelectColor(int itemTextSelectColor) {
        mItemTextSelectColor = itemTextSelectColor;
    }

    /**
     * 设置item未被选中的文字颜色。
     *
     * @param itemTextNoSelectColor 颜色
     */
    public void setItemTextNoSelectColor(int itemTextNoSelectColor) {
        mItemTextNoSelectColor = itemTextNoSelectColor;
    }

    /**
     * 设置item底部指示线颜色
     *
     * @param itemIndicatorDividerColor 颜色
     */
    public void setItemIndicatorDividerColor(int itemIndicatorDividerColor) {
        mItemIndicatorDividerColor = itemIndicatorDividerColor;
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

    public int getTabSelectPosition() {
        return mSelectPosition;
    }

    /**
     * 选择Tab
     *
     * @param position       0 <= position  <data.size
     * @param isNeedCallBack 是否需要选中回调
     */
    public void setTabSelect(int position, boolean isNeedCallBack) {
        mSelectPosition = position;
        if (mSelectPosition < 0 || mSelectPosition >= this.getChildCount()) {
            return;
        }
        if (isNeedCallBack) {
            this.post(new Runnable() {
                @Override
                public void run() {

                    getChildAt(mSelectPosition).performClick();
                }
            });
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    adjustItemView(getChildAt(mSelectPosition));
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
        this.removeAllViews();
        for (int i = 0; i < contents.size(); i++) {
            View tabItem;
            //根据不同Style，加载不同布局
            TextView textView;
            //主题分段
            tabItem = mLayoutInflater.inflate(R.layout.baseview_view_average_tab_item, null);
            LinearLayout.LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            tabItem.setLayoutParams(layoutParams);
            textView = (TextView) tabItem.findViewById(R.id.txt_item);
            View viewIndicator = tabItem.findViewById(R.id.view_item_divider);
            //根据不同theme，设置不同的颜色
            textView.setText(contents.get(i).getTitle());
            textView.setTextColor(mItemTextNoSelectColor);
            if (mItemIndicatorType == INDICATOR_TYPE_WRAP_CONTENT) {
                RelativeLayout.LayoutParams layoutParamsIndicator = (RelativeLayout.LayoutParams) viewIndicator.getLayoutParams();
                float textSize = textView.getTextSize();
                Paint paint = new Paint();
                paint.setTextSize(textSize);
                float indicatorWidth = paint.measureText(contents.get(i).getTitle());
                layoutParamsIndicator.width = (int) indicatorWidth;
                viewIndicator.setLayoutParams(layoutParamsIndicator);
            }
            viewIndicator.setBackgroundColor(mItemIndicatorDividerColor);
            this.addView(tabItem);
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
        for (int i = 0; i < getChildCount(); i++) {
            if (view == getChildAt(i)) {
                return i;
            }
        }
        return position;
    }

    /**
     * 调整item显示
     *
     * @param v 要更新的view
     */
    private void adjustItemView(View v) {
        if (mCurrentItem != null) {
            ((TextView) mCurrentItem.getChildAt(0)).setTextColor(mItemTextNoSelectColor);
            if (mCurrentItem.getChildAt(1) != null) {
                mCurrentItem.getChildAt(1).setVisibility(INVISIBLE);
            }
        }
        mCurrentItem = ((RelativeLayout) v);
        if (mCurrentItem.getChildAt(1) != null) {
            mCurrentItem.getChildAt(1).setVisibility(VISIBLE);//mCurrentItem.getChildAt(1)是item下面的那根线
        }
        ((TextView) mCurrentItem.getChildAt(0)).setTextColor(mItemTextSelectColor);
    }

    /**
     * 设置Tab点击回调
     *
     * @param listener 回调
     */
    public void setOnSelectListener(OnItemSelectListener listener) {
        mListener = listener;
    }

    /**
     * item点击回调接口
     */
    public interface OnItemSelectListener {

        /**
         * 点击item的回调
         * @param position 点击的位置
         * @param listItem 点击item的数据
         */
        void onItemSelect(int position, ListItem listItem);
    }
}
