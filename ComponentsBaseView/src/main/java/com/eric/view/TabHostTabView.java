package com.eric.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eric.view.entity.ListItem;
import com.eric.view.utils.DensityUtils;

import java.util.List;

/**
 * 选项卡样式的标签控件
 */
public class TabHostTabView extends LinearLayout {


    /**
     * 上下文
     */
    private Context mContext;

    /**
     * tab选中的回调
     */
    private OnItemSelectListener mListener;

    /**
     * 蓝底
     */
    private static int THEME_BLUE = 0;

    /**
     * 白底
     */
    private static int THEME_WHITE = 1;

    /**
     * 主题
     */
    private int mSectionTabViewTheme;

    /**
     * 文字颜色
     */
    private int mTextColor;

    /**
     * 单个item的高
     */
    private int mItemHeight;

    /**
     * 最左边item背景
     */
    private int mLeftBackground;

    /**
     * 中间item背景
     */
    private int mMiddleBackground;

    /**
     * 右边item背景
     */
    private int mRightBackground;

    public TabHostTabView(Context context) {
        this(context, null);
    }

    public TabHostTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initView(attrs);
    }

    private void initData(Context context) {
        mContext = context;
        mItemHeight = DensityUtils.dip2px(mContext, 28);
    }

    private void initView(AttributeSet attrs) {
        TypedArray typedValue = mContext.obtainStyledAttributes(attrs, R.styleable.TabHostTabView);
        mSectionTabViewTheme = typedValue.getInt(R.styleable.TabHostTabView_tabHostTabViewTheme, THEME_BLUE);
        if (mSectionTabViewTheme == THEME_BLUE) {
            mTextColor = getResources().getColor(R.color.lj_color_white);
            setBackgroundResource(R.drawable.baseview_shape_solid_white_corner4);
            mLeftBackground = R.drawable.baseview_selector_solid_white_blue_left_corner;
            mMiddleBackground = R.drawable.baseview_selector_solid_white_blue;
            mRightBackground = R.drawable.baseview_selector_solid_white_blue_right_corner;
        } else {
            mTextColor = getResources().getColor(R.color.lj_color_blue);
            setBackgroundResource(R.drawable.baseview_shape_solid_blue_corner4);
            mLeftBackground = R.drawable.baseview_selector_solid_blue_white_left_corner;
            mMiddleBackground = R.drawable.baseview_selector_solid_blue_white;
            mRightBackground = R.drawable.baseview_selector_solid_blue_white_right_corner;
        }
        setPadding(DensityUtils.dip2px(mContext, 1f), DensityUtils.dip2px(mContext, 1f), DensityUtils.dip2px(mContext, 1f),
                DensityUtils.dip2px(mContext, 1f));
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
     * 根据Data数据，填充View。
     *
     * @param contents Data(数据要大于2)
     */
    private void inflateViews(final List<ListItem> contents) {
        this.removeAllViews();
        if (contents.size() < 2) {
            return;
        }
        int itemWidth = calculateItemWidth(contents);
        int textSize = contents.size() < 3 ? 18 : 16;
        TextView txtItem;
        for (int i = 0; i < contents.size(); i++) {
            if (i == 0) {
                txtItem = createTextView(itemWidth, mItemHeight, textSize, 0, mLeftBackground, i, contents.get(i));
            } else if (0 < i && i < contents.size() - 1) {
                txtItem = createTextView(itemWidth, mItemHeight, textSize, DensityUtils.dip2px(mContext, 1f), mMiddleBackground,
                        i, contents.get(i));
            } else {
                txtItem = createTextView(itemWidth, mItemHeight, textSize, DensityUtils.dip2px(mContext, 1f), mRightBackground,
                        i, contents.get(i));
            }
            addView(txtItem);
        }

    }

    /**
     * 计算单个item的宽度。计算规则：左padding（28dp）+右padding（28dp）+内容本身宽度（假如两item，字体18sp；若大于2item，字体16sp）
     *
     * @param contents 所有内容
     * @return 单个item的宽度
     */
    private int calculateItemWidth(List<ListItem> contents) {
        Paint paint = new Paint();
        paint.setTextSize(contents.size() < 3 ? 18 : 16);
        return DensityUtils.dip2px(mContext, paint.measureText("陆鲸科技") + 8 + 8);
    }

    /**
     * 创建TextView
     *
     * @param width      宽度
     * @param height     高度
     * @param textSize   文字大小
     * @param leftMargin 左边距
     * @param background 背景
     * @param position   item的索引
     * @param data       数据
     * @return TextView
     */
    private TextView createTextView(int width, int height, int textSize, int leftMargin, int background, final int position,
                                    final ListItem data) {
        LinearLayout.LayoutParams layoutParams = new LayoutParams(width, height);
        layoutParams.leftMargin = leftMargin;
        TextView txtItem = new TextView(mContext);
        txtItem.setTextSize(textSize);
        txtItem.setTextColor(mTextColor);
        txtItem.setBackgroundResource(background);
        txtItem.setGravity(Gravity.CENTER);
        txtItem.setText(data.getTitle());

        txtItem.setLayoutParams(layoutParams);
        txtItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateView(v);
                if (mListener != null) {
                    mListener.onItemSelect(position, data);
                }
            }
        });
        return txtItem;
    }

    /**
     * 更新item状态
     *
     * @param v 选中的item
     */
    private void updateView(View v) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setSelected(getChildAt(i) == v);
            if (mSectionTabViewTheme == THEME_BLUE) {
                ((TextView) getChildAt(i)).setTextColor(getResources().getColor(getChildAt(i) == v ? R.color.lj_color_blue : R
                        .color.lj_color_white));
            } else {
                ((TextView) getChildAt(i)).setTextColor(getResources().getColor(getChildAt(i) == v ? R.color.lj_color_white : R
                        .color.lj_color_blue));
            }
        }
    }


    /**
     * 选择Tab
     *
     * @param position 0 <= position  <data.size
     */
    public void setTabSelect(final int position) {
        if (position < 0 || position >= this.getChildCount()) {
            return;
        }
        this.post(new Runnable() {
            @Override
            public void run() {

                getChildAt(position).performClick();
            }
        });
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
         * item点击回调方法
         * @param position 位置
         * @param listItem 数据
         */
        void onItemSelect(int position, ListItem listItem);
    }
}
