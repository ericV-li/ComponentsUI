package com.eric.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eric.view.utils.StatusBarUtil;

/**
 * @author shangfu.li
 * @Package com.eric.view.TitleBar
 * @Title: TitleBar
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 头部标题栏
 */

public class TitleBar extends RelativeLayout {

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
     * 左边回退文字
     */
    private TextView mBackTextView;

    /**
     * 左边回退按钮（图片为向下箭头）
     */
    private ImageView mBackIconView;

    /**
     * 中间的标题
     */
    private TextView mTitleTextView;

    /**
     * 中间标题右边图片
     */
    private ImageView mTitleIconView;

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
     * 中间标题边图片资源id
     */
    private int mTitleIconResource;

    /**
     * 标题
     */
    private String mTitleText;

    /**
     * 右侧文字文案
     */
    private String mTitleRightText;

    /**
     * 主题
     */
    private int mTitleTheme;

    /**
     * 是否需要沉浸式
     */
    private boolean isNeedImmersive;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
        LayoutInflater.from(context).inflate(R.layout.baseview_view_title_bar, this, true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Title_Bar);
        mTitleIconResource = a.getResourceId(R.styleable.Title_Bar_titleIcon, 0);
        mTitleRightText = a.getString(R.styleable.Title_Bar_titleRightText);
        mTitleTheme = a.getInteger(R.styleable.Title_Bar_titleTheme, THEME_NONE);
        mTitleText = a.getString(R.styleable.Title_Bar_titleText);
        isNeedImmersive = a.getBoolean(R.styleable.Title_Bar_titleNeedImmersive, true);
        a.recycle();
        initView(context);
    }

    private void initView(final Context context) {
        mBackImageView = (ImageView) findViewById(R.id.iv_back);
        mBackIconView = (ImageView) findViewById(R.id.iv_back_right);
        mTitleIconView = (ImageView) findViewById(R.id.iv_title_right);
        mRightImageView1 = (ImageView) findViewById(R.id.imageview_1);
        mRightImageView2 = (ImageView) findViewById(R.id.imageview_2);
        mBackTextView = (TextView) findViewById(R.id.tv_left);
        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mRightTextView = (TextView) findViewById(R.id.tv_right);
        mTitleContainer = (RelativeLayout) findViewById(R.id.rl_title);
        mBackContainer = (LinearLayout) findViewById(R.id.ll_back_container);
        setBackBtnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mBackImageView.getVisibility() == View.VISIBLE && context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });

        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.lj_color_divider));
        mPaint.setAntiAlias(true);
        mLineHeight = this.getResources().getDimensionPixelSize(R.dimen.lj_view_separator_line_height);

        setTitle(mTitleText);
        setRightText(mTitleRightText);
        if (mTitleIconResource > 0) {
            mTitleIconView.setImageResource(mTitleIconResource);
        }
        setTitleTheme(mTitleTheme);
    }

    /**
     * 设置标题栏主题 {@link #THEME_WHITE},{@link #THEME_BLUE},{@link #THEME_TRANSPARENT},
     * {@link #THEME_NONE}
     * 注：右侧图标扔需要自己设置
     *
     * @param theme 主题
     */
    public void setTitleTheme(int theme) {
        mTitleTheme = theme;
        switch (theme) {
            case THEME_BLUE:
                setBackgroundColor(Color.parseColor("#0093ff"));
                if (isNeedImmersive) {
                    StatusBarUtil.setColor(getContext(), this);
                }
                mBackImageView.setImageDrawable(getResources().getDrawable(R.drawable.baseview_back));
                mTitleTextView.setTextColor(Color.parseColor("#ffffff"));
                mRightTextView.setTextColor(Color.parseColor("#ffffff"));
                mBackTextView.setTextColor(Color.parseColor("#ffffff"));
                setShowLine(false);
                break;
            case THEME_TRANSPARENT:
                if (isNeedImmersive) {
                    StatusBarUtil.setColor(getContext(), this);
                }
                setBackgroundColor(getResources().getColor(R.color.lj_color_transparent));
                mBackImageView.setImageDrawable(getResources().getDrawable(R.drawable.baseview_back));
                mTitleTextView.setTextColor(Color.parseColor("#ffffff"));
                mRightTextView.setTextColor(Color.parseColor("#ffffff"));
                mBackTextView.setTextColor(Color.parseColor("#ffffff"));
                setShowLine(false);
                break;
            case THEME_WHITE:
                setBackgroundColor(Color.parseColor("#ffffff"));
                if (isNeedImmersive) {
                    StatusBarUtil.setColor(getContext(), this);
                }
                mBackImageView.setImageDrawable(getResources().getDrawable(R.drawable.baseview_back_common));
                mTitleTextView.setTextColor(Color.parseColor("#20304b"));
                mRightTextView.setTextColor(Color.parseColor("#0093ff"));
                mBackTextView.setTextColor(Color.parseColor("#20304b"));
                break;
            case THEME_COMMON_THEME:
                setBackgroundColor(Color.parseColor("#f7f7f7"));
                if (isNeedImmersive) {
                    StatusBarUtil.setColor(getContext(), this);
                }
                mBackImageView.setImageDrawable(getResources().getDrawable(R.drawable.baseview_back_common));
                mTitleTextView.setTextColor(Color.parseColor("#20304b"));
                mRightTextView.setTextColor(Color.parseColor("#0093ff"));
                mBackTextView.setTextColor(Color.parseColor("#20304b"));
                break;
            case THEME_NONE:
            default:
                break;
        }
    }

    /**
     * 是否沉浸式
     *
     * @return 是否沉浸式
     */
    public boolean isNeedImmersive() {
        return isNeedImmersive;
    }

    /**
     * 设置是否沉浸式
     *
     * @param needImmersive 是否沉浸式
     */
    public void setNeedImmersive(boolean needImmersive) {
        isNeedImmersive = needImmersive;
    }

    public int getTitleTheme() {
        return mTitleTheme;
    }

    /**
     * 获得状态栏高度
     *
     * @param context 上下文
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 是否显示返回图标
     *
     * @param visible 是否可见
     */
    public void setBackBtnVisible(boolean visible) {
        if (visible) {
            mBackImageView.setVisibility(View.VISIBLE);
        } else {
            mBackImageView.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示左侧文字
     *
     * @param visible 是否可见
     */
    public void setBackTextVisible(boolean visible) {
        if (visible) {
            mBackTextView.setVisibility(View.VISIBLE);
        } else {
            mBackTextView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置返回监听
     *
     * @param listener 监听器
     */
    public void setBackBtnClickListener(OnClickListener listener) {
        mBackContainer.setOnClickListener(listener);
    }

    /**
     * 设置左部分文字监听
     *
     * @param listener 监听器
     */
    public void setLeftClickListener(OnClickListener listener) {
        setBackBtnClickListener(listener);
    }

    /**
     * 设置左侧文字，默认不显示
     *
     * @param text 文案
     */
    public void setBackBtnText(String text) {
        mBackTextView.setText(text);
    }

    /**
     * 是否显示左侧文字后的小图标,默认为下箭头"∨"（可通过{@link #getLeftIconView()} 来修改图标），不显示
     *
     * @param visible 是否可见
     */
    public void setLeftIconVisible(boolean visible) {
        if (visible) {
            mBackIconView.setVisibility(View.VISIBLE);
        } else {
            mBackIconView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题
     *
     * @param title 标题文案
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

    /**
     * 是否显示标题文字后的小图标,默认为下箭头（可通过{@link #getTitleIconView()} 来修改图标），不显示
     *
     * @param visible 是否可见
     */
    public void setTitleRightIconVisible(boolean visible) {
        if (visible) {
            mTitleIconView.setVisibility(View.VISIBLE);
        } else {
            mTitleIconView.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示回退键
     *
     * @param show 是否显示
     */
    public void showBack(boolean show) {
        mBackContainer.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置右侧1的按钮监听事件
     *
     * @param listener 监听器
     */
    public void setRightBtn1ClickListener(OnClickListener listener) {
        mRightImageView1.setOnClickListener(listener);
    }

    /**
     * 设置右侧2的按钮监听事件
     *
     * @param listener 监听器
     */
    public void setRightBtn2ClickListener(OnClickListener listener) {
        mRightImageView2.setOnClickListener(listener);
    }

    /**
     * 设置右侧1按钮的图标
     *
     * @param drawable 图片
     */
    public void setRightBtn1Icon(Drawable drawable) {
        mRightImageView1.setImageDrawable(drawable);
        mRightImageView1.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右侧1按钮的图标
     *
     * @param resId 图片id
     */
    public void setRightBtn1Icon(int resId) {
        mRightImageView1.setImageResource(resId);
        mRightImageView1.setVisibility(View.VISIBLE);
    }

    /**
     * 设置标题栏右侧文字
     *
     * @param text 文案
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
     * @param listener 监听器
     */
    public void setRightTextClickListener(OnClickListener listener) {
        mRightTextView.setOnClickListener(listener);
    }


    /**
     * 设置右侧2按钮的图标
     *
     * @param drawable 图片Drawable
     */
    public void setRightBtn2Icon(Drawable drawable) {
        mRightImageView2.setImageDrawable(drawable);
        mRightImageView2.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右侧2按钮的图标
     *
     * @param resId 图片id
     */
    public void setRightBtn2Icon(int resId) {
        mRightImageView2.setImageResource(resId);
        mRightImageView2.setVisibility(View.VISIBLE);
    }

    /**
     * 获取左侧图标控件
     *
     * @return 获取左侧ImageView
     */
    public ImageView getLeftImageView() {
        return mBackImageView;
    }

    /**
     * 获取左侧文字后面的图标view
     *
     * @return 获取左侧ImageView
     */
    public ImageView getLeftIconView() {
        return mBackIconView;
    }

    /**
     * 获取标题文字后面的图标view
     *
     * @return 标题文字后面的图标
     */
    public ImageView getTitleIconView() {
        return mTitleIconView;
    }

    /**
     * 获取中间文字区域的容器，可添加自定义的View（注：不影响标题栏左侧和右侧显示）
     *
     * @return 间文字区域的容器
     */
    public RelativeLayout getTitleContainer() {
        return mTitleContainer;
    }

    /**
     * 获取标题栏
     *
     * @return 标题栏
     */
    public TextView getTitleView() {
        return mTitleTextView;
    }

    /**
     * 获取标题栏右侧按钮1View
     *
     * @return 右侧按钮
     */
    public ImageView getRightView1() {
        return mRightImageView1;
    }

    /**
     * 获取标题栏右侧按钮2View
     *
     * @return 右侧按钮
     */
    public ImageView getRightView2() {
        return mRightImageView2;
    }

    /**
     * 获取右侧文字View
     *
     * @return 右侧文字
     */
    public TextView getRightTextView() {
        return mRightTextView;
    }

    /**
     * 是否显示标题栏底部线条
     *
     * @param show 是否显示
     */
    public void setShowLine(boolean show) {
        mShowLine = show;
        invalidate();
    }

    @SuppressLint("NewApi")
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mShowLine) {
            canvas.drawRect(0, this.getHeight() - mLineHeight, this.getWidth(), this.getHeight(), mPaint);
        }
    }

}
