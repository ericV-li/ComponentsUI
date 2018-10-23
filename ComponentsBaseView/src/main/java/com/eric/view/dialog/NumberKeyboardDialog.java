package com.eric.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eric.view.R;
import com.eric.view.adapter.BaseSimpleAdapter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author li
 * @Package com.eric.view.dialog
 * @Title: NumberKeyboardDialog
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 数字键盘
 */
public class NumberKeyboardDialog extends Dialog implements View.OnClickListener {


    /**
     * 头部
     */
    private FrameLayout frameLayoutHeader;
    /**
     * 数字键盘
     */
    private GridView grdNumbers;
    /**
     * 删除图标
     */
    private View imgDelete;
    /**
     * 确认图标
     */
    private View txtConfirm;
    /**
     * 头部内容，提供给外部添加
     */
    private View viewHeader;

    /**
     * 输入的内容
     */
    private String mClicked = "";
    /**
     * 监听
     */
    private OnKeyListener mListener;

    /**
     * 屏幕密度
     */
    private float mDensity;

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    /**
     * 数据
     */
    private List<String> data;


    /**
     * 键盘数字的值
     */
    private String[] KEYVALUE;

    public NumberKeyboardDialog(Context context, OnKeyListener listener) {
        this(context, listener, false, false);
    }

    public NumberKeyboardDialog(Context context, OnKeyListener listener, boolean dim) {
        this(context, listener, false, dim);

    }

    public NumberKeyboardDialog(Context context, OnKeyListener listener, boolean isInt, boolean dim) {
        super(context, dim ? R.style.dialogWidthMatchParent : R.style.dialogWidthMatchParentDimDisable);
        this.mListener = listener;
        setCanceledOnTouchOutside(true);
        getWindow().setGravity(Gravity.BOTTOM);
        KEYVALUE = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", isInt ? "" : ".", "0", ""};
        data = Arrays.asList(KEYVALUE);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseview_number_keyboard_dialog);
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        mDensity = dm.density;
        mScreenWidth = dm.widthPixels;
        initViews();
    }

    /**
     * 设置已输入内容
     *
     * @param input 已输入内容
     */
    public void setInput(String input) {
        mClicked = input;
    }

    private void initViews() {
        frameLayoutHeader = (FrameLayout) findViewById(R.id.frame_header);
        grdNumbers = (GridView) findViewById(R.id.grid_view);
        imgDelete = findViewById(R.id.img_board_delete);
        txtConfirm = findViewById(R.id.txt_board_confirm);
        if (viewHeader != null) {
            frameLayoutHeader.addView(viewHeader);
        }
        imgDelete.setOnClickListener(this);
        txtConfirm.setOnClickListener(this);
        grdNumbers.setAdapter(new NumAdapter(getContext(), data));
        grdNumbers.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 11) {
                    dismiss();
                    return;
                }
                String s = ((NumAdapter) parent.getAdapter()).getItem(position);
                if (TextUtils.isEmpty(s)) return;
                if (mClicked.trim().length() == 0 && (".".equals(s))) return;
                if (mClicked.contains(".")) {
                    if (".".equals(s)) return;
                }
                if ("0".equals(mClicked) && !".".equals(s)) {
                    mClicked = "";
                }
                String temp = mClicked.concat(s);
                if (mListener != null) {
                    if (mListener.onCheck(temp)) {
                        mClicked = temp;
                        callback();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.img_board_delete) {
            if (mClicked.length() > 0) {
                mClicked = mClicked.substring(0, mClicked.length() - 1);
                callback();
            }
        } else if (vId == R.id.txt_board_confirm) {
            if (mListener != null) {
                mListener.onCommit();
            }
            dismiss();
        }
    }

    /**
     * 添加头部控件
     *
     * @param view 添加的view
     */
    public void addHeader(View view) {
        viewHeader = view;
        setEditTextBoardHidden(view);
    }

    public interface OnKeyListener {

        /**
         * 点击数字按钮回调
         *
         * @param clicked 已输入的内容
         */
        void onKey(String clicked);

        /**
         * 检查输入内容。注：交给页面自己去处理
         *
         * @param checkStr
         * @return
         */
        boolean onCheck(String checkStr);

        /**
         * 确认按钮点击回调。注：这里只做关闭键盘，逻辑交给上层
         */
        void onCommit();

    }

    /**
     * 获取软键盘监听
     *
     * @return 软键盘监听
     */
    public OnKeyListener getListener() {
        return mListener;
    }

    /**
     * 设置软键盘监听
     *
     * @param listener 软键盘监听
     */
    public void setListener(OnKeyListener listener) {
        this.mListener = listener;
    }

    /**
     * 回调
     */
    private void callback() {
        if (mListener != null) {
            mListener.onKey(mClicked);
        }
    }

    private class NumAdapter extends BaseSimpleAdapter<String> {

        /**
         * 构造器
         *
         * @param context 上下文
         * @param data    数据（List）
         */
        public NumAdapter(Context context, List data) {
            super(context, data);
        }

        @Override
        public int getItemResource() {
            return R.layout.baseview_grid_item_unmber_keyboard;
        }

        @Override
        public View getItemView(int position, View convertView, ViewHolder holder) {
            RelativeLayout rlyContainer = holder.getView(R.id.rly_number_content);
            TextView txtNum = holder.getView(R.id.txt_number);
            GridView.LayoutParams layoutParams = new GridView.LayoutParams((int) ((mScreenWidth - 16 * mDensity) / 4), (int)
                    (44 * mDensity));
            rlyContainer.setLayoutParams(layoutParams);
            if (position < 11) {
                txtNum.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else {
                txtNum.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseview_keyboard_esc, 0, 0, 0);
            }
            txtNum.setText(getItem(position));
            return convertView;
        }
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = mScreenWidth;
        getWindow().setAttributes(lp);
    }

    /**
     * 设置隐藏系统默认软键盘方法
     *
     * @param view edittext
     */
    private void setEditTextBoardHidden(View view) {

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                setEditTextBoardHidden(((ViewGroup) view).getChildAt(i));
            }
        } else {
            if (view instanceof EditText) {
                setEditTextBoardHiddenLineShow(view);
            }
        }

    }

    /**
     * 设置隐藏系统默认软键盘方法
     *
     * @param view edittext
     */
    private void setEditTextBoardHiddenLineShow(View view) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setSoftInputShownOnFocus;
            setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setSoftInputShownOnFocus.setAccessible(true);
            setSoftInputShownOnFocus.invoke(view, false);
        } catch (Exception e) {
            Log.i("NumBoardDialog",e.toString());
        }
    }
}
