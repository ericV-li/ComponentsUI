package com.eric.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eric.view.utils.DensityUtils;

/**
 * 转换标签选择控件，用于点击弹出popupWindow
 */
public class SwitchTabView extends RelativeLayout {

    /**
     * Tab的个数
     */
    private static final int MAX_TAB_COUNT = 3;

    /**
     * Tab容器，用于存放选项控件数组
     */
    public View[] mTabContainers;

    /**
     * tab文案边上的图标数组
     */
    private ImageView[] mImageViews;

    /**
     * tab的TextView数组
     */
    private TextView[] mTextViews;

    /**
     * TextView的文案数组
     */
    private String[] mTextViewTexts;

    /**
     * 顶部分割线
     */
    private TextView mTopDivider;

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 点击tab的监听
     */
    private OnTabClickListener mListener;

    /**
     * 当前选中的索引
     */
    private int mCurrentSelection = -1;

    public SwitchTabView(Context context) {
        this(context, null);
    }

    public SwitchTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.baseview_view_switch_tab, this, true);
        initView();
        initClickListener();
    }

    private void initView() {
        mTopDivider = (TextView) findViewById(R.id.tv_top_divider);
        mTabContainers = new View[3];
        mTabContainers[0] = findViewById(R.id.rly_tab1);
        mTabContainers[1] = findViewById(R.id.ll_tab2);
        mTabContainers[2] = findViewById(R.id.ll_tab3);
        mImageViews = new ImageView[3];
        mImageViews[0] = (ImageView) findViewById(R.id.iv_tab1);
        mImageViews[1] = (ImageView) findViewById(R.id.iv_tab2);
        mImageViews[2] = (ImageView) findViewById(R.id.iv_tab3);
        mTextViews = new TextView[3];
        mTextViews[0] = (TextView) findViewById(R.id.tv_tab1);
        mTextViews[1] = (TextView) findViewById(R.id.tv_tab2);
        mTextViews[2] = (TextView) findViewById(R.id.tv_tab3);
        mTextViewTexts = new String[3];
    }

    /**
     * 初始化tab点击监听
     */
    private void initClickListener() {
        mTabContainers[0].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                clickTab(0);
            }
        });
        mTabContainers[1].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                clickTab(1);
            }
        });
        mTabContainers[2].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                clickTab(2);
            }
        });
    }

    /**
     * 点击tab
     *
     * @param position 点击索引
     */
    public void clickTab(int position) {
        boolean show;
        if (mCurrentSelection != position) {
            show = true;
            setSelectTab(position);
        } else {
            show = false;
            resetTabMode();
        }
        if (mListener != null) {
            mListener.onTabClick(position, show);
        }
    }

    /**
     * 设置tab的文案
     *
     * @param txt1 第一个tab文案
     * @param txt2 第二个tab文案
     * @param txt3 第三个tab文案
     */
    public void setTabText(String txt1, String txt2, String txt3) {
        mTextViews[0].setText(toEscapeText(0, txt1));
        mTextViews[1].setText(toEscapeText(1, txt2));
        mTextViews[2].setText(toEscapeText(2, txt3));
        setTabText(txt1, null, true, txt2, null, true, txt3, null, true);
    }

    /**
     * 设置tab的文案
     *
     * @param txt1 第一个tab文案
     * @param txt2 第二个tab文案
     * @param txt3 第三个tab文案
     */
    /**
     * 设置tab的文案,左侧图片，右侧箭头是否显示
     *
     * @param txt1              第一个tab文案
     * @param txt1LeftDrawable  第一个tab文案左侧图片(若左侧设置了图片 textview居中)
     * @param txt1ShowRightIcon 第一个tab文案右侧箭头是否显示
     * @param txt2              第二个tab文案
     * @param txt2LeftDrawable  第二个tab文案左侧图片(若左侧设置了图片 textview居中)
     * @param txt2ShowRightIcon 第二个个tab文案右侧箭头是否显示
     * @param txt3              第三个tab文案
     * @param txt3LeftDrawable  第三个tab文案左侧图片(若左侧设置了图片 textview居中)
     * @param txt3ShowRightIcon 第三个tab文案右侧箭头是否显示
     */
    public void setTabText(String txt1, Drawable txt1LeftDrawable, boolean txt1ShowRightIcon, String txt2, Drawable
            txt2LeftDrawable, boolean txt2ShowRightIcon, String txt3, Drawable txt3LeftDrawable, boolean txt3ShowRightIcon) {
        mTextViews[0].setText(toEscapeText(0, txt1));
        if (txt1LeftDrawable != null) {
            RelativeLayout.LayoutParams params = (LayoutParams) mTextViews[0].getLayoutParams();
            params.addRule(CENTER_IN_PARENT);
            mTextViews[0].setCompoundDrawablePadding(DensityUtils.dip2px(mContext, 12));
            mTextViews[0].setCompoundDrawablesWithIntrinsicBounds(txt1LeftDrawable, null, null, null);
        }
        mImageViews[0].setVisibility(txt1ShowRightIcon ? VISIBLE : GONE);

        mTextViews[1].setText(toEscapeText(1, txt2));
        if (txt2LeftDrawable != null) {
            RelativeLayout.LayoutParams params = (LayoutParams) mTextViews[1].getLayoutParams();
            params.addRule(CENTER_IN_PARENT);
            mTextViews[1].setCompoundDrawablePadding(DensityUtils.dip2px(mContext, 12));
            mTextViews[1].setCompoundDrawablesWithIntrinsicBounds(txt2LeftDrawable, null, null, null);
        }
        mImageViews[1].setVisibility(txt2ShowRightIcon ? VISIBLE : GONE);

        mTextViews[2].setText(toEscapeText(2, txt3));
        if (txt3LeftDrawable != null) {
            RelativeLayout.LayoutParams params = (LayoutParams) mTextViews[2].getLayoutParams();
            params.addRule(CENTER_IN_PARENT);
            mTextViews[2].setCompoundDrawablePadding(DensityUtils.dip2px(mContext, 12));
            mTextViews[2].setCompoundDrawablesWithIntrinsicBounds(txt3LeftDrawable, null, null, null);
        }
        mImageViews[2].setVisibility(txt3ShowRightIcon ? VISIBLE : GONE);
    }

    /**
     * 设置第几个tab的文案
     *
     * @param position 索引
     * @param str      文案
     */
    public void setTabText(int position, String str) {
        if (position >= 0 && position < MAX_TAB_COUNT) {
            mTextViews[position].setText(toEscapeText(position, str));
        }
    }

    /**
     * 对文案进行数据处理，只取前三个，后面有省略号
     *
     * @param position 索引
     * @param str      文案
     */
    private String toEscapeText(int position, String str) {
        if (position >= 0 && position < MAX_TAB_COUNT) {
            mTextViewTexts[position] = str;
        }
        if (str != null && str.length() > 4) {
            str = str.substring(0, 3) + "...";
        }
        return str;
    }

    /**
     * 获取tab的文案
     *
     * @param position 索引
     * @return tab的文案
     */
    public String getTabText(int position) {
        if (position >= 0 && position < MAX_TAB_COUNT) {
            return mTextViewTexts[position];
        } else {
            return null;
        }
    }

    /**
     * 设置tab个数
     *
     * @param count tab个数
     */
    public void setTabCount(int count) {
        if (count < 1) {
            return;
        }
        if (count > MAX_TAB_COUNT) {
            count = MAX_TAB_COUNT;
        }
        int i = 0;
        while (i < MAX_TAB_COUNT) {
            mTabContainers[i].setVisibility(i < count ? View.VISIBLE : View.GONE);
            i++;
        }
    }

    /**
     * tab重置回到原始状态
     */
    public void resetTabMode() {
        int i = 0;
        mCurrentSelection = -1;
        while (i < MAX_TAB_COUNT) {
            mImageViews[i].setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseview_icon_down));
            i++;
        }
    }

    /**
     * 设置选中的tab
     *
     * @param position 索引
     */
    public void setSelectTab(int position) {
        if (position < 0 || position > MAX_TAB_COUNT) {
            return;
        }
        resetTabMode();
        mCurrentSelection = position;
        if (position < MAX_TAB_COUNT) {
            mImageViews[position].setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseview_icon_up));
        }
    }

    /**
     * 设置是否显示top分割线
     *
     * @param show 是否显示
     */
    public void setShowTopDivider(boolean show) {
        if (show) {
            mTopDivider.setVisibility(View.VISIBLE);
        } else {
            mTopDivider.setVisibility(View.GONE);
        }
    }

    /**
     * 设置tab点击监听
     *
     * @param listener 监听
     */
    public void setOnTabClickListener(OnTabClickListener listener) {
        mListener = listener;
    }

    /**
     * tab点击接口
     */
    public interface OnTabClickListener {
        void onTabClick(int position, boolean show);
    }

}
