package com.eric.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eric.view.utils.DensityUtils;
import com.eric.view.utils.StatusBarUtil;
/**
 * @author li
 * @Package com.eric.view.H5TitleBar
 * @Title: H5TitleBar
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * H5页面用的头部标题栏
 */

public class H5TitleBar extends RelativeLayout {

    /**
     * 白色主题
     */
    public final static int THEME_WHITE = 0;

    /**
     * 色主题
     */
    public final static int THEME_BLUE = 1;

    /**
     * 透明主题
     */
    public final static int THEME_TRANSPARENT = 2;

    /**
     * 无主题
     */
    public final static int THEME_NONE = 3;

    /**
     * 通用主题（暂时是灰色）
     */
    public final static int THEME_COMMON_THEME = 4;

    /**
     * 左边回退按钮（图片为向左箭头）
     */
    private ImageView mBackImageView;

    /**
     * 左边回退按钮（图片为叉号）
     */
    private ImageView mCloseImageView;

    /**
     * 中间的标题
     */
    private TextView mTitleTextView;

    /**
     * 右侧第一个图片
     */
    private ImageView mRightImageView1;

    /**
     * 右侧第二个图片
     */
    private ImageView mRightImageView2;

    /**
     * 右侧文字
     */
    private TextView mRightTextView;

    /**
     * 中间标题容器
     */
    private RelativeLayout mTitleContainer;

    /**
     * 左侧控件容器
     */
    private LinearLayout mBackContainer;

    /**
     * 画笔（用于画底部分割线）
     */
    private Paint mPaint;

    /**
     * 底部分割线高度
     */
    private int mLineHeight;

    /**
     * 是否显示底部分割线
     */
    private boolean mShowLine = true;

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 标题
     */
    private String mTitleText;

    /**
     * 主题
     */
    private int mTitleTheme;

    /**
     * 点击首页按钮监听
     */
    private OnClickListener mOnHomeClicker;
    /**
     * 点击刷新按钮监听
     */
    private OnClickListener mOnRefreshClicker;

    /**
     * 是否需要沉浸式
     */
    private boolean isNeedImmersive;

    public H5TitleBar(Context context) {
        this(context, null);
    }

    public H5TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public H5TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setWillNotDraw(false);
        LayoutInflater.from(context).inflate(R.layout.baseview_view_h5_title_bar, this, true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Title_Bar);
        mTitleTheme = a.getInteger(R.styleable.Title_Bar_titleTheme, THEME_NONE);
        mTitleText = a.getString(R.styleable.Title_Bar_titleText);
        isNeedImmersive = a.getBoolean(R.styleable.Title_Bar_titleNeedImmersive, true);
        a.recycle();
        initView(context);
    }

    private void initView(final Context context) {
        mBackImageView = (ImageView) findViewById(R.id.iv_back);
        mCloseImageView = (ImageView) findViewById(R.id.iv_left_close);
        mRightImageView1 = (ImageView) findViewById(R.id.imageview_1);
        mRightImageView2 = (ImageView) findViewById(R.id.imageview_2);
        mRightTextView = (TextView) findViewById(R.id.tv_right);
        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mTitleContainer = (RelativeLayout) findViewById(R.id.rl_title_container);
        mBackContainer = (LinearLayout) findViewById(R.id.ll_left_container);
        setCloseBtnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.lj_color_divider));
        mPaint.setAntiAlias(true);
        mLineHeight = this.getResources().getDimensionPixelSize(R.dimen.lj_view_separator_line_height);

        setTitle(mTitleText);
        setTitleTheme(mTitleTheme);
    }

    /**
     * 设置标题栏主题 {@link #THEME_WHITE},{@link #THEME_BLUE},{@link #THEME_TRANSPARENT},
     * {@link #THEME_NONE}
     * 注：右侧图标扔需要自己设置
     *
     * @param theme 主题
     */
    private void setTitleTheme(int theme) {
        switch (theme) {
            case THEME_BLUE:
                setBackgroundColor(Color.parseColor("#0093ff"));
                if (isNeedImmersive) {
                    StatusBarUtil.setColor(getContext(), this);
                }
                mBackImageView.setImageDrawable(getResources().getDrawable(R.drawable.baseview_back));
                mTitleTextView.setTextColor(Color.parseColor("#ffffff"));
                mRightTextView.setTextColor(Color.parseColor("#ffffff"));
                setShowLine(false);
                break;
            case THEME_TRANSPARENT:
                setBackgroundColor(getResources().getColor(R.color.lj_color_transparent));
                if (isNeedImmersive) {
                    StatusBarUtil.setColor(getContext(), this);
                }
                mBackImageView.setImageDrawable(getResources().getDrawable(R.drawable.baseview_back));
                mTitleTextView.setTextColor(Color.parseColor("#ffffff"));
                mRightTextView.setTextColor(Color.parseColor("#ffffff"));
                setShowLine(false);
                break;
            case THEME_WHITE:
                setBackgroundColor(Color.parseColor("#ffffff"));
                if (isNeedImmersive) {
                    StatusBarUtil.setColor(getContext(), this);
                }
                mBackImageView.setImageDrawable(getResources().getDrawable(R.drawable.baseview_back_common));
                mCloseImageView.setImageDrawable(getResources().getDrawable(R.drawable.baseview_icon_close));
                mTitleTextView.setTextColor(Color.parseColor("#20304b"));
                mRightTextView.setTextColor(Color.parseColor("#0093ff"));
                break;
            case THEME_COMMON_THEME:
                setBackgroundColor(Color.parseColor("#f7f7f7"));
                if (isNeedImmersive) {
                    StatusBarUtil.setColor(getContext(), this);
                }
                mBackImageView.setImageDrawable(getResources().getDrawable(R.drawable.baseview_back_common));
                mCloseImageView.setImageDrawable(getResources().getDrawable(R.drawable.baseview_icon_close));
                mTitleTextView.setTextColor(Color.parseColor("#20304b"));
                mRightTextView.setTextColor(Color.parseColor("#0093ff"));
                break;
            case THEME_NONE:
            default:
                break;
        }
    }

    /**
     * 设置回退按钮是否显示
     *
     * @param show 是否显示
     */
    public void showBackBtn(boolean show) {
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mTitleContainer.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(getContext(), 48));
        }
        if (show) {
            //显示回退按钮
            if (mCloseImageView.getVisibility() == VISIBLE) {
                //关闭按钮也显示。设置中间title，左右margin 88。
                mCloseImageView.setPadding(DensityUtils.dip2px(getContext(), 12), 0, 0, 0);
                layoutParams.setMargins(DensityUtils.dip2px(getContext(), 88), 0, DensityUtils.dip2px(getContext(), 88), 0);
            } else {
                //关闭按钮隐藏。设置中间title，左右margin 44。
                layoutParams.setMargins(DensityUtils.dip2px(getContext(), 44), 0, DensityUtils.dip2px(getContext(), 44), 0);
            }
            mBackImageView.setVisibility(VISIBLE);
        } else {
            //隐藏回退按钮
            if (mCloseImageView.getVisibility() == VISIBLE) {
                //关闭按钮显示。设置中间title，左右margin 44。
                mCloseImageView.setPadding(0, 0, 0, 0);
                layoutParams.setMargins(DensityUtils.dip2px(getContext(), 44), 0, DensityUtils.dip2px(getContext(), 44), 0);
            } else {
                //关闭按钮不显示。设置中间title，左右margin 12。
                layoutParams.setMargins(DensityUtils.dip2px(getContext(), 12), 0, DensityUtils.dip2px(getContext(), 12), 0);
            }
            mTitleContainer.setLayoutParams(layoutParams);
            mBackImageView.setVisibility(GONE);
        }
    }

    /**
     * 设置关闭按钮是否显示
     *
     * @param show 是否显示
     */
    public void showCloseBtn(boolean show) {
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mTitleContainer.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(getContext(), 48));
        }
        if (show) {
            //显示关闭按钮
            if (mBackImageView.getVisibility() == VISIBLE) {
                //回退按钮也显示。设置中间title，左右margin 88。
                mCloseImageView.setPadding(DensityUtils.dip2px(getContext(), 12), 0, 0, 0);
                layoutParams.setMargins(DensityUtils.dip2px(getContext(), 88), 0, DensityUtils.dip2px(getContext(), 88), 0);
            } else {
                //回退按钮隐藏。设置中间title，左右margin 44。
                mCloseImageView.setPadding(0, 0, 0, 0);
                layoutParams.setMargins(DensityUtils.dip2px(getContext(), 44), 0, DensityUtils.dip2px(getContext(), 44), 0);
            }
            mCloseImageView.setVisibility(VISIBLE);
        } else {
            //隐藏关闭按钮
            if (mBackImageView.getVisibility() == VISIBLE) {
                //回退按钮显示
                layoutParams.setMargins(DensityUtils.dip2px(getContext(), 44), 0, DensityUtils.dip2px(getContext(), 44), 0);
            } else {
                //回退按钮不显示。设置中间title，左右margin 12。
                layoutParams.setMargins(DensityUtils.dip2px(getContext(), 12), 0, DensityUtils.dip2px(getContext(), 12), 0);
            }
            mTitleContainer.setLayoutParams(layoutParams);
            mCloseImageView.setVisibility(GONE);
        }
    }

    /**
     * 弹出PopupWindow
     */
    public void showPopupWindow() {
        final PopupWindow popupWindow = new PopupWindow();
        View view = LayoutInflater.from(mContext).inflate(R.layout.baseview_h5_title_pupop_window, null);
        popupWindow.setContentView(view);
        int width = getResources().getDisplayMetrics().widthPixels;
//        popupWindow.setWidth(width / 2);
//        popupWindow.setHeight(getResources().getDimensionPixelSize(R.dimen.lj_table_height));
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // // 设置SelectPicPopupWindow的View
        // this.setContentView(conentView);
        // // 设置SelectPicPopupWindow弹出窗体的宽
        // this.setWidth(w / 2 + 50);
        // // 设置SelectPicPopupWindow弹出窗体的高
        // this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 刷新状态
        popupWindow.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        popupWindow.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.AnimationPreview);
        final TextView homeView = (TextView) view.findViewById(R.id.tv_home);
        final TextView refreshView = (TextView) view.findViewById(R.id.tv_refresh);
        homeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (mOnHomeClicker != null) {
                    mOnHomeClicker.onClick(homeView);
                }
            }
        });
        refreshView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (mOnRefreshClicker != null) {
                    mOnRefreshClicker.onClick(refreshView);
                }
            }
        });
        popupWindow.showAsDropDown(mRightImageView2, 0, 0);
    }

    /**
     * 设置点击主页监听
     *
     * @param listener 监听
     */
    public void setOnHomeClickListener(OnClickListener listener) {
        mOnHomeClicker = listener;
    }

    /**
     * 设置点击刷新监听
     *
     * @param listener 监听
     */
    public void setOnRefreshClickListener(OnClickListener listener) {
        mOnRefreshClicker = listener;
    }

    /**
     * 设置返回监听
     *
     * @param listener 监听
     */
    public void setBackBtnClickListener(OnClickListener listener) {
        mBackImageView.setOnClickListener(listener);
    }

    /**
     * 设置左侧关闭按钮监听
     *
     * @param listener 监听
     */
    public void setCloseBtnClickListener(OnClickListener listener) {
        mCloseImageView.setOnClickListener(listener);
    }

    /**
     * 设置左部分文字监听
     *
     * @param listener 监听
     */
    public void setLeftClickListener(OnClickListener listener) {
        setBackBtnClickListener(listener);
    }

    /**
     * 设置标题
     *
     * @param title 监听
     */
    public void setTitle(String title) {
        mTitleTextView.setText(title);
        mTitleTextView.setVisibility(View.VISIBLE);
    }

    /**
     * 设置标题点击事件监听
     *
     * @param listener 监听
     */
    public void setTitleClickListener(OnClickListener listener) {
        mTitleContainer.setOnClickListener(listener);
    }

    public void showBack(boolean show) {
        mBackContainer.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置右侧1的按钮监听事件
     *
     * @param listener 监听
     */
    public void setRightBtn1ClickListener(OnClickListener listener) {
        mRightImageView1.setOnClickListener(listener);
    }

    /**
     * 设置右侧2的按钮监听事件
     *
     * @param listener 监听
     */
    public void setRightBtn2ClickListener(OnClickListener listener) {
        mRightImageView2.setOnClickListener(listener);
    }

    /**
     * 设置右侧1按钮的图标
     *
     * @param drawable 图标Drawable
     */
    public void setRightBtn1Icon(Drawable drawable) {
        mRightImageView1.setImageDrawable(drawable);
        mRightImageView1.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右侧1按钮的图标
     *
     * @param resId 图标resId
     */
    public void setRightBtn1Icon(int resId) {
        mRightImageView1.setImageResource(resId);
        mRightImageView1.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右侧2按钮的图标
     *
     * @param drawable 图标Drawable
     */
    public void setRightBtn2Icon(Drawable drawable) {
        mRightImageView2.setImageDrawable(drawable);
        mRightImageView2.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右侧2按钮的图标
     *
     * @param resId 图标resId
     */
    public void setRightBtn2Icon(int resId) {
        mRightImageView2.setImageResource(resId);
        mRightImageView2.setVisibility(View.VISIBLE);
    }

    /**
     * 获取左侧图标控件
     *
     * @return ImageView
     */
    public ImageView getLeftImageView() {
        return mBackImageView;
    }

    /**
     * 获取中间文字区域的容器，可添加自定义的View（注：不影响标题栏左侧和右侧显示）
     *
     * @return 间文字区域的容器RelativeLayout
     */
    public RelativeLayout getTitleContainer() {
        return mTitleContainer;
    }

    /**
     * 获取标题栏
     *
     * @return TextView
     */
    public TextView getTitleView() {
        return mTitleTextView;
    }

    /**
     * 获取标题栏右侧按钮1View
     *
     * @return ImageView
     */
    public ImageView getRightView1() {
        return mRightImageView1;
    }

    /**
     * 获取标题栏右侧按钮2View
     *
     * @return ImageView
     */
    public ImageView getRightView2() {
        return mRightImageView2;
    }

    /**
     * 是否显示标题栏底部线条
     *
     * @param show 是否显示下划线
     */
    public void setShowLine(boolean show) {
        mShowLine = show;
        invalidate();
    }

    /**
     * 设置标题栏右侧文字
     *
     * @param text 右侧文字
     */
    public void setRightText(String text) {
        mRightTextView.setText(text);
    }

    /**
     * 设置标题栏右侧文字是否显示
     *
     * @param show 是否显示
     */
    public void setRightTextVisibility(boolean show) {
        if (show) {
            mRightTextView.setVisibility(View.VISIBLE);
        } else {
            mRightTextView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右侧文字点击监听事件
     *
     * @param listener 监听
     */
    public void setRightTextClickListener(OnClickListener listener) {
        mRightTextView.setOnClickListener(listener);
    }

    /**
     * 获取右侧文字View
     *
     * @return TextView
     */
    public TextView getRightTextView() {
        return mRightTextView;
    }

    @SuppressLint("NewApi")
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mShowLine) {
            canvas.drawRect(0, this.getHeight() - mLineHeight, this.getWidth(), this.getHeight(), mPaint);
        }
    }

}
