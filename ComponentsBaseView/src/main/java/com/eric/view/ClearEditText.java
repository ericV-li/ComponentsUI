package com.eric.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * @author li
 * @Package com.eric.view.ClearEditText
 * @Title: ClearEditText
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 右边有清空输入内容的输入框。
 */

public class ClearEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {

    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 输入框底部划线高度
     */
    private int mLineHeight;

    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFocus;

    /**
     * 是否显示下划线
     */
    private boolean mShowLine = true;

    public ClearEditText(Context context) {
        super(context, null);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    /**
     * 初始化
     */
    private void init() {
        setWillNotDraw(false);
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
//          throw new NullPointerException("You can wt_add drawableRight attribute in XML");
            mClearDrawable = getResources().getDrawable(R.drawable.baseview_icon_clear);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
        setBackgroundResource(0);
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.parseColor("#68758e"));
        this.mPaint.setAntiAlias(true);
        this.mLineHeight = getResources().getDimensionPixelSize(R.dimen.lj_view_separator_line_height);
    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     *
     * @param event 事件
     * @return 事件是否被消费
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() -
                        getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     *
     * @param v        当前view
     * @param hasFocus 是否有焦点
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        mPaint.setColor(hasFocus ? Color.parseColor("#0093ff") : Color.parseColor("#68758e"));
        this.mLineHeight = hasFocus ? getResources().getDimensionPixelSize(R.dimen.lj_view_separator_line_height_bold) :
                getResources().getDimensionPixelSize(R.dimen.lj_view_separator_line_height);
        postInvalidate();
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible 清楚按钮是否显示
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
        setCompoundDrawablePadding((int) (12 * getResources().getDisplayMetrics().density));
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (hasFocus) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (mOnTextChangedListener != null) {
            mOnTextChangedListener.onTextChanged(s.toString());
        }

    }


    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }


    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return 动画
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShowLine) {
            canvas.drawRect(0, getHeight() - mLineHeight, getWidth(), getHeight(), mPaint);
        }
    }

    /**
     * 是否显示标题栏底部线条
     *
     * @param show 是否显示
     */
    public void setShowLine(boolean show) {
        mShowLine = show;
    }

    /**
     * 输入内容修改的回调
     */
    private OnTextChangedListener mOnTextChangedListener;

    /**
     * 获取内容修改回调
     *
     * @return OnTextChangedListener
     */
    public OnTextChangedListener getOnTextChangedListener() {
        return mOnTextChangedListener;
    }

    /**
     * 设置内容修改回调
     *
     * @param mOnTextChangedListener 回调
     */
    public void setOnTextChangedListener(OnTextChangedListener mOnTextChangedListener) {
        this.mOnTextChangedListener = mOnTextChangedListener;
    }

    /**
     * 内容修改回调接口
     */
    public interface OnTextChangedListener {
        void onTextChanged(String text);
    }

}
