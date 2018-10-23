package com.eric.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 对于单行文本和图片展示的封装。左边、中间、右边各有TextView和ImageView
 */
public class TableView extends RelativeLayout implements View.OnClickListener {

    /**
     * 主题：无
     */
    public final static int THEME_NONE = 0;

    /**
     * 主题：白
     */
    public final static int THEME_WHITE = 1;

    /**
     * 主题：可变，正常白，有焦点，不可使用情况灰。
     */
    public final static int THEME_CHANGEABLE = 2;

    /**
     * 控件类型，上
     */
    public final static int TYPE_TOP = 0;

    /**
     * 控件类型中
     */
    public final static int TYPE_CENTER = 1;

    /**
     * 控件类型，下
     */
    public final static int TYPE_BOTTOM = 2;

    /**
     * 控件类型，单独条目
     */
    public final static int TYPE_SINGLE = 3;

    /**
     * 设置文字颜色红色
     */
    public final static int COLOR_RED = 0;

    /**
     * 设置文字颜色绿色
     */
    public final static int COLOR_GREEN = 1;

    /**
     * 设置文字颜色蓝色
     */
    public final static int COLOR_BLUE = 2;

    /**
     * 设置文字颜色灰色
     */
    public final static int COLOR_GRAY = 3;

    /**
     * 设置文字颜色黑色
     */
    public final static int COLOR_BLACK = 4;

    /**
     * 设置文字颜色黄色
     */
    public final static int COLOR_YELLOW = 5;

    /**
     * 左侧图片
     */
    private ImageView mLeftImage;

    /**
     * 内容图片
     */
    private ImageView mContentImage;

    /**
     * 右侧箭头图片
     */
    private ImageView mArrow;

    /**
     * 左侧文字
     */
    private TextView mLeftText;

    /**
     * 右侧文字
     */
    private TextView mRightText;

    /**
     * 内容文字
     */
    private TextView mContentText;

    /**
     * 左侧容器
     */
    private LinearLayout mLeftView;

    /**
     * 右侧容器
     */
    private LinearLayout mRightContainer;

    /**
     * 主内容
     */
    private LinearLayout mMainView;

    /**
     * 左侧文案内容
     */
    private String mLeft;

    /**
     * 右侧文案内容
     */
    private String mRight;

    /**
     * 主内容
     */
    private String mContent;

    /**
     * 是否显示右侧箭头
     */
    private boolean mShowArrow;

    /**
     * 划线类型
     */
    private int mType;

    /**
     * 图片resId
     */
    private int mImage;

    /**
     * 左侧文字颜色
     */
    private int mLeftColor;

    /**
     * 中间内容文字颜色
     */
    private int mContentColor;

    /**
     * 右侧内容文字颜色
     */
    private int mRightTextColor;

    /**
     * 左侧增加空额个数
     */
    private int mLeftCharNum;

    /**
     * 右侧textview的暗示内容的颜色
     */
    private int mRightTextHintColor;

    /**
     * 右侧textview的暗示内容
     */
    private String mRightHint;

    /**
     * 主题
     */
    private int mTheme;

    /**
     * 画笔，用于画分割线
     */
    private Paint mPaint;

    /**
     * 分割线高度
     */
    private int mLineHeight;

    /**
     * 是否显示分割线
     */
    private boolean mShowLine;

    public TableView(Context context) {
        this(context, null);
    }

    public TableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
        LayoutInflater.from(context).inflate(R.layout.baseview_table_view, this, true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Table_View);
        mContent = a.getString(R.styleable.Table_View_contentText);
        mLeft = a.getString(R.styleable.Table_View_leftText);
        mRight = a.getString(R.styleable.Table_View_rightText);
        mRightHint = a.getString(R.styleable.Table_View_rightTextHint);
        mShowArrow = a.getBoolean(R.styleable.Table_View_showArrow, true);
        mType = a.getInteger(R.styleable.Table_View_type, -1);
        mLeftCharNum = a.getInteger(R.styleable.Table_View_leftCharNum, 0);
        mLeftColor = a.getColor(R.styleable.Table_View_leftTextColor, context.getResources().getColor(R.color
                .baseview_title_text_color));
        mContentColor = a.getColor(R.styleable.Table_View_contentTextColor, context.getResources().getColor(R.color
                .baseview_textcolor_main_gray));
        mRightTextColor = a.getColor(R.styleable.Table_View_rightTextColor, context.getResources().getColor(R.color
                .baseview_textcolor_main_gray));
        mRightTextHintColor = a.getColor(R.styleable.Table_View_rightTextHintColor, context.getResources().getColor(R.color
                .lj_color_light_gray_hint));
        mImage = a.getResourceId(R.styleable.Table_View_leftImage, 0);
        mTheme = a.getInteger(R.styleable.Table_View_tableBackground, THEME_NONE);
        a.recycle();
        initView();

        mPaint = new Paint();
        mPaint.setColor(this.getContext().getResources().getColor(R.color.baseview_app_bg_color));
        mPaint.setAntiAlias(true);

        mLineHeight = this.getResources().getDimensionPixelSize(R.dimen.lj_view_separator_line_height);
    }

    private void initView() {
        mMainView = (LinearLayout) findViewById(R.id.ll_main);
        mLeftView = (LinearLayout) findViewById(R.id.ll_left);
        mRightContainer = (LinearLayout) findViewById(R.id.ll_right_container);
        mLeftImage = (ImageView) findViewById(R.id.iv_left);
        mContentImage = (ImageView) findViewById(R.id.iv_content_image);
        mArrow = (ImageView) findViewById(R.id.iv_arrow);
        mLeftText = (TextView) findViewById(R.id.tv_left);
        mRightText = (TextView) findViewById(R.id.tv_right);
        mContentText = (TextView) findViewById(R.id.tv_content);

        if (mLeftCharNum > 0) {
            setLeftText(mLeft, mLeftCharNum);
        } else {
            setLeftText(mLeft);
        }
        setRightText(mRight);
        setContent(mContent);
        if (mLeft != null && !"".equals(mLeft)) {
            mLeftText.setVisibility(View.VISIBLE);
        }
        if (mRight != null && !"".equals(mRight)) {
            mRightText.setVisibility(View.VISIBLE);
        }
        if (mContent != null && !"".equals(mContent)) {
            mContentText.setVisibility(View.VISIBLE);
        }
        showArrow(mShowArrow);
        setTableViewBackground(mTheme);
        setViewType(mType);
        if (mImage > 0) {
            setLeftImage(mImage);
        }
        mLeftText.setTextColor(mLeftColor);
        mContentText.setTextColor(mContentColor);
        mRightText.setTextColor(mRightTextColor);
        mRightText.setHintTextColor(mRightTextHintColor);
        if (mRightHint != null && mRightHint.length() > 0) {
            mRightText.setHint(mRightHint);
            mRightText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置标题栏主题 {@link #THEME_NONE},{@link #THEME_WHITE},{@link #THEME_CHANGEABLE},
     *
     * @param theme 主题
     */
    public void setTableViewBackground(int theme) {
        switch (theme) {
            case THEME_WHITE:
                setBackgroundResource(R.color.lj_color_white);
                break;
            case THEME_CHANGEABLE:
                setOnClickListener(this);
                setBackgroundResource(R.drawable.baseview_tableview_rect_white);
                break;
            case THEME_NONE:
            default:
                break;
        }
    }

    /**
     * 设置分割线类型
     * <li>{@link #TYPE_TOP}</li> <li>{@link #TYPE_CENTER}</li> <li>
     * {@link #TYPE_BOTTOM}</li> <li>{@link #TYPE_SINGLE}</li>
     *
     * @param type 类型
     */
    public void setViewType(int type) {
        mType = type;
        switch (type) {
            case TYPE_TOP:
                mShowLine = true;
                break;
            case TYPE_CENTER:
            case TYPE_BOTTOM:
            case TYPE_SINGLE:
            default:
                mShowLine = true;
                break;
        }
    }

    /**
     * 设置文字颜色 ,有效的colorId为： <li>{@link #COLOR_BLACK}</li> <li>
     * {@link #COLOR_BLUE}</li> <li>
     * {@link #COLOR_GRAY}</li> <li>{@link #COLOR_GREEN}</li> <li>
     * {@link #COLOR_RED}</li> <li>
     * {@link #COLOR_YELLOW}</li>
     *
     * @param view    控件
     * @param colorId 色值
     */
    public void setTextColor(TextView view, int colorId) {
        if (view != null) {
            int color = getResources().getColor(R.color.baseview_textcolor_second_black);
            switch (colorId) {
                case COLOR_BLACK:
                    color = getResources().getColor(R.color.baseview_textcolor_second_black);
                    break;
                case COLOR_BLUE:
                    color = getResources().getColor(R.color.lj_color_blue);
                    break;
                case COLOR_GRAY:
                    color = getResources().getColor(R.color.lj_color_light_gray_hint);
                    break;
                case COLOR_GREEN:
                    color = getResources().getColor(R.color.lj_color_green);
                    break;
                case COLOR_RED:
                    color = getResources().getColor(R.color.lj_color_red);
                    break;
                case COLOR_YELLOW:
                    color = getResources().getColor(R.color.lj_color_deep_yellow);
                    break;
            }
            view.setTextColor(color);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    /**
     * 获取左侧ImageView
     *
     * @return 左侧ImageView
     */
    public ImageView getLeftImageView() {
        return mLeftImage;
    }

    /**
     * 获取左侧TextView
     *
     * @return 左侧TextView
     */
    public TextView getLeftTextView() {
        return mLeftText;
    }

    /**
     * 获取右侧箭头ImageView
     *
     * @return 右侧箭头
     */
    public ImageView getRightArrowImageView() {
        return mArrow;
    }

    /**
     * 获取内容显示文字控件
     *
     * @return 内容TextView
     */
    public TextView getContentView() {
        return mContentText;
    }

    /**
     * 获取中间内容图片ImageView
     *
     * @return 中间内容图片ImageView
     */
    public ImageView getContentImageView() {
        return mContentImage;
    }

    /**
     * 获取右侧TextView
     *
     * @return 右侧TextView
     */
    public TextView getRightTextView() {
        return mRightText;
    }

    /**
     * 获取右侧显示容器，可添加自定义view
     *
     * @return 右侧内容容器
     */
    public LinearLayout getRightContainer() {
        return mRightContainer;
    }

    /**
     * 设置左侧图片
     *
     * @param resId 图片resId
     * @see #setLeftImage(Drawable)
     */
    public void setLeftImage(int resId) {
        mLeftImage.setImageResource(resId);
        mLeftImage.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左侧图片
     *
     * @param drawable 图片Drawable
     * @see #setLeftImage(int)
     */
    public void setLeftImage(Drawable drawable) {
        mLeftImage.setImageDrawable(drawable);
        mLeftImage.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左侧图片
     *
     * @param resId 图片resId
     * @see #setContentImage(Drawable)
     */
    public void setContentImage(int resId) {
        mContentImage.setImageResource(resId);
        mContentImage.setVisibility(View.VISIBLE);
    }

    /**
     * 设置中间图片
     *
     * @param drawable 图片Drawable
     * @see #setContentImage(int)
     */
    public void setContentImage(Drawable drawable) {
        mContentImage.setImageDrawable(drawable);
        mContentImage.setVisibility(View.VISIBLE);
    }

    /**
     * 设置显示的文字
     *
     * @param str 内容文案
     */
    public void setContent(String str) {
        mContentText.setText(str);
    }

    /**
     * 设置左侧文字
     *
     * @param str 内容文案
     */
    public void setLeftText(String str) {
        mLeftText.setText(str);
    }

    /**
     * 设置左侧文字
     *
     * @param str 内容文案
     * @param charNum 最大位数
     */
    public void setLeftText(String str, int charNum) {
        if (str != null && str.length() > 0 && charNum > 0) {
            while (str.length() < charNum) {
                str += "　";
            }
        }
        mLeftText.setText(str);
    }

    /**
     * 设置右侧文字
     *
     * @param str 内容文案
     */
    public void setRightText(String str) {
        mRightText.setText(str);
    }

    /**
     * 获取tableView
     *
     * @return 整个容器LinearLayout
     */
    public LinearLayout getMainView() {
        return mMainView;
    }

    /**
     * 在空件右侧，箭头左侧添加自定义view
     *
     * @param view View
     */
    public void addRightChildView(View view) {
        if (view != null) {
            int childs = mRightContainer.getChildCount();
            if (childs > 2) {
                for (int i = 0; i < childs - 2; i++) {
                    mRightContainer.removeView(mRightContainer.getChildAt(0));
                }
            }
            mRightContainer.addView(view, 0);
        }
    }

    /**
     * 是否显示右箭头
     *
     * @param show 是否显示
     */
    public void showArrow(boolean show) {
        if (show) {
            mArrow.setVisibility(View.VISIBLE);
        } else {
            mArrow.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NewApi")
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // draw top line
        if (this.mType == TYPE_TOP || this.mType == TYPE_SINGLE) {
            // 设置顶部线条颜色额
            mPaint.setColor(this.getContext().getResources().getColor(R.color.baseview_textcolor_second_gray));
            // canvas.drawRect(0, 0, this.getWidth(), mLineHeight, mPaint);
        }

        float posX;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            posX = mLeftView.getX();
        } else {
            posX = mLeftView.getLeft();
        }
        if (this.mType == TYPE_BOTTOM || this.mType == TYPE_SINGLE) {
            // 设置底部线条颜色
            mPaint.setColor(this.getContext().getResources().getColor(R.color.baseview_textcolor_second_gray));
            posX = 0;
        } else {
            // 设置中间线条颜色
            mPaint.setColor(this.getContext().getResources().getColor(R.color.baseview_textcolor_second_gray));
            // 可在此设置中间线条的左间距离
            // posX = 0;
            if (mShowLine) {
                canvas.drawRect(posX, this.getHeight() - mLineHeight, this.getWidth(), this.getHeight(), mPaint);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

}
