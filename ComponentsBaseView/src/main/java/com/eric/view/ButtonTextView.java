package com.eric.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import com.eric.view.utils.DensityUtils;

/**
 * @author li
 * @Package com.eric.view.ButtonTextView
 * @Title: ButtonTextView
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 继承textview 按钮统一封装，增加蓝色（默认）、白色、灰色、红色、绿色主题，点击效果，4度圆角（默认）、直角。
 */

public class ButtonTextView extends AppCompatTextView {

    /**
     * 蓝色主题：按钮蓝色，点击深蓝
     */
    public static final int THEME_BLUE = 0;

    /**
     * 白色主题：按钮白色，点击淡灰色f7f7f7
     */
    public static final int THEME_WHITE = 1;

    /**
     * 灰色主题：按钮灰，点击深灰色dfdfdf
     */
    public static final int THEME_GRAY = 2;

    /**
     * 绿色主题：按钮灰，点击dfdfdf
     */
    public static final int THEME_GREEN = 3;

    /**
     * 红色主题：按钮红色，点击深红
     */
    public static final int THEME_RED = 4;

    /**
     * 圆角样式，角度4
     */
    private static int SHAPE_ROUND = 0;

    /**
     * 直角样式
     */
    private static int SHAPE_RECTANGLE = 1;

    /**
     * TextView颜色
     */
    private int srcColor;

    /**
     * 样式
     */
    private int shape;

    /**
     * 默认文字大小
     */
    private static final int DEFAULT_TEXT_SIZE = 18;
    /**
     * 默认高度
     */
    private static final int DEFAULT_HEIGHT = 48;

    public ButtonTextView(Context context) {
        this(context, null);
    }

    public ButtonTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ButtonTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(final Context context, AttributeSet attrs) {
        TypedArray typedValue = context.obtainStyledAttributes(attrs, R.styleable.ButtonTextView);
        int theme = typedValue.getInt(R.styleable.ButtonTextView_buttonTheme, THEME_BLUE);
        shape = typedValue.getInt(R.styleable.ButtonTextView_buttonShape, SHAPE_ROUND);
        setTextSize(DEFAULT_TEXT_SIZE);//默认字体大小
        initTheme(theme, shape);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                if (layoutParams.width < 0) {
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                if (layoutParams.height < 0) {
                    layoutParams.height = DensityUtils.dip2px(context, DEFAULT_HEIGHT);
                }
                setLayoutParams(layoutParams);
            }
        });
        setGravity(Gravity.CENTER);
    }

    /**
     * 初始化主题样式
     *
     * @param theme 主题
     * @param shape 样式
     */
    private void initTheme(int theme, int shape) {
        if (theme == THEME_BLUE) {
            srcColor = getResources().getColor(R.color.lj_color_white);
            setBackgroundResource(shape == SHAPE_ROUND ? R.drawable.baseview_selector_button_blue_corner4 : R.drawable
                    .baseview_selector_button_blue);
        } else if (theme == THEME_WHITE) {
            srcColor = getResources().getColor(R.color.lj_color_blue);
            setBackgroundResource(shape == SHAPE_ROUND ? R.drawable.baseview_selector_button_white_corner4 : R.drawable
                    .baseview_selector_button_white);
        } else if (theme == THEME_GRAY) {
            srcColor = getResources().getColor(R.color.lj_color_black_light);
            setBackgroundResource(shape == SHAPE_ROUND ? R.drawable.baseview_selector_button_f7f7f7_corner4 : R.drawable
                    .baseview_selector_button_f7f7f7);
        } else if (theme == THEME_GREEN) {
            srcColor = getResources().getColor(R.color.lj_color_white);
            setBackgroundResource(shape == SHAPE_ROUND ? R.drawable.baseview_selector_button_green_corner4 : R.drawable
                    .baseview_selector_button_green);
        } else if (theme == THEME_RED) {
            srcColor = getResources().getColor(R.color.lj_color_white);
            setBackgroundResource(shape == SHAPE_ROUND ? R.drawable.baseview_selector_button_red_corner4 : R.drawable
                    .baseview_selector_button_red);
        }
        setTextColor(srcColor);
    }

    /**
     * 设置按钮是否可用。若不可用，会替换灰色背景
     *
     * @param enable 是否可用
     */
    public void setButtonEnable(boolean enable) {
        setTextColor(enable ? srcColor : getResources().getColor(R.color.lj_color_white));
        setEnabled(enable);
    }

    /**
     * 动态设置按钮主题
     *
     * @param theme 主题
     */
    public void setTheme(int theme) {
        initTheme(theme, shape);
    }
}
