package com.eric.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eric.view.utils.StatusBarUtil;


/**
 * @author li
 * @Package com.eric.view.SearchBar
 * @Title: SearchBar
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 搜索控件的封装，最左边一个返回按钮，然后左边是文字和图片。中间输入框，输入框右边一个叉号。右边一个文字
 */

public class SearchBar extends RelativeLayout implements View.OnFocusChangeListener, TextWatcher {

    /**
     * 输入框
     */
    private EditText edt;

    /**
     * 左侧返回容器
     */
    private LinearLayout llyBackContainer;

    /**
     * 右侧容器
     */
    private LinearLayout llyRightContainer;

    /**
     * 搜索类型选择容器
     */
    private LinearLayout llySearchType;

    /**
     * 搜索类型
     */
    private TextView txtSearchType;

    /**
     * 输入框容器
     */
    private LinearLayout edtContainer;

    /**
     * 清除输入信息的控件
     */
    private ImageView imgViewClear;

    /**
     * 右侧TextView控件
     */
    private TextView txtRight;

    /**
     * 画笔。用于画底部分割线
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
     * 输入内容改变的监听
     */
    private OnTextChangedListener mListener;

    /**
     * 上下文
     */
    private Context mContext;

    public SearchBar(Context context) {
        this(context, null);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setWillNotDraw(false);
        LayoutInflater.from(context).inflate(R.layout.baseview_view_search_bar, this, true);
        initView(context);
    }

    private void initView(final Context context) {
        edt = (EditText) findViewById(R.id.et_title);
        llyBackContainer = (LinearLayout) findViewById(R.id.ll_back_container);
        llyRightContainer = (LinearLayout) findViewById(R.id.ll_right_container);
        llySearchType = (LinearLayout) findViewById(R.id.lly_search_type);
        txtSearchType = (TextView) findViewById(R.id.txt_search_type);
        edtContainer = (LinearLayout) findViewById(R.id.lly_edittext_container);
        imgViewClear = (ImageView) findViewById(R.id.img_clear);
        txtRight = (TextView) findViewById(R.id.tv_right);
        imgViewClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.setText("");
            }
        });
        edt.addTextChangedListener(this);
        edt.setOnFocusChangeListener(this);
        setBackClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.lj_color_black_light));
        mPaint.setAntiAlias(true);
        mLineHeight = this.getResources().getDimensionPixelSize(R.dimen.lj_view_separator_line_height_bold);
        StatusBarUtil.setColor(getContext(), this);
    }

    /**
     * 设置点击返回按钮的监听
     *
     * @param click 监听
     */
    public void setBackClickListener(OnClickListener click) {
        llyBackContainer.setOnClickListener(click);
    }

    /**
     * 设置右侧清除按钮的监听
     *
     * @param click 监听
     */
    public void setOnRightTextClickListener(final OnClickListener click) {
        if (click != null) {
            llyRightContainer.setVisibility(VISIBLE);
            llyRightContainer.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    txtRight.requestFocus();
                    if (click != null && txtRight.isEnabled()) {
                        click.onClick(v);
                    }
                }
            });
        }
    }

    /**
     * 设置左侧搜索类型文案
     *
     * @param type 类型文案
     */
    public void setSearchType(String type) {
        if (!TextUtils.isEmpty(type)) {
            llySearchType.setVisibility(VISIBLE);
            txtSearchType.setText(type);
            edt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    /**
     * 获取搜索类型容器
     *
     * @return 搜索类型容器
     */
    public View getSearchTypeView() {
        return llySearchType;
    }

    /**
     * 设置搜索类型点击监听
     *
     * @param onClickListener 监听
     */
    public void setSearchTypeClickListener(OnClickListener onClickListener) {
        llySearchType.setOnClickListener(onClickListener);
    }

    /**
     * 设置输入的监听
     *
     * @param click 监听
     */
    public void setSearchClickerListener(final OnClickListener click) {
        edt.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    txtRight.requestFocus();
                    showSoftInput(edt, false);
                    if (click != null) {
                        click.onClick(v);
                    }
                }
                return false;
            }
        });
    }

    /**
     * 设置软键盘显示和隐藏
     *
     * @param view 用于软键盘绑定的句柄
     * @param show 是否显示
     */
    private void showSoftInput(View view, boolean show) {
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (show) {
            im.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } else {
            im.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 设置右侧文案
     *
     * @param text 文案
     */
    public void setSearchBtnText(String text) {
        txtRight.setText(text);
    }

    /**
     * 设置输入框hint文案
     *
     * @param hint hint文案
     */
    public void setHint(String hint) {
        edt.setHint(hint);
    }

    /**
     * 获取输入框内容
     *
     * @return 输入框内容
     */
    public String getContent() {
        return edt.getText().toString();
    }

    /**
     * 设置输入框内容
     *
     * @param content 输入框内容
     */
    public void setContent(String content) {
        edt.setText(content);
        edt.setSelection(edt.getText().length());
    }

    /**
     * 是否显示返回图标
     *
     * @param visible 是否显示
     */
    public void setBackBtnVisible(boolean visible) {
        if (visible) {
            llyBackContainer.setVisibility(View.VISIBLE);
        } else {
            llyBackContainer.setVisibility(View.GONE);
        }
    }

    /**
     * 返回输入框
     *
     * @return 输入框
     */
    public EditText getEditText() {
        return edt;
    }

    /**
     * 获取右侧容器
     *
     * @return 右侧容器
     */
    @Deprecated
    public View getRightView() {
        return llyRightContainer;
    }

    /**
     * 获取右侧TextView
     *
     * @return 右侧TextView
     */
    @Deprecated
    public TextView getRightTextView() {
        return txtRight;
    }

    /**
     * 设置输入内容修改的监听
     *
     * @param listener 监听
     */
    public void setOnTextChangedListener(OnTextChangedListener listener) {
        mListener = listener;
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     *
     * @param v        当前控件
     * @param hasFocus 是否获取焦点
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(edt.getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        mPaint.setColor(getResources().getColor(R.color.lj_color_black_light));
        this.mLineHeight = hasFocus ? getResources().getDimensionPixelSize(R.dimen.lj_view_separator_line_height_bold) :
                getResources().getDimensionPixelSize(R.dimen.lj_view_separator_line_height_bold);
        postInvalidate();
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible 是否显示
     */
    protected void setClearIconVisible(boolean visible) {
        imgViewClear.setVisibility(visible ? VISIBLE : GONE);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();
        if ("".equals(text.trim())) {
            setClearIconVisible(false);
            txtRight.setEnabled(false);
        } else {
            setClearIconVisible(true);
            txtRight.setEnabled(true);
        }
        if (mListener != null) {
            mListener.onTextChange(text);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        // empty here
    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        // empty here
    }

    /**
     * 是否显示标题栏底部线条
     *
     * @param show 是否显示
     */
    public void setShowLine(boolean show) {
        mShowLine = show;
    }

    @SuppressLint("NewApi")
    public void draw(Canvas canvas) {
        super.draw(canvas);
       /* if (mShowLine) {
            float left;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                left = edtContainer.getX();
            } else {
                left = edtContainer.getLeft();
            }
            canvas.drawRect(left, (int) (this.getHeight() - mLineHeight * 9), edtContainer.getWidth() + llyBackContainer
                    .getWidth(), this.getHeight() - mLineHeight * 8, mPaint);
        }*/
    }

    /**
     * 输入框内容修改的监听
     */
    public interface OnTextChangedListener {
        void onTextChange(String text);
    }
}
