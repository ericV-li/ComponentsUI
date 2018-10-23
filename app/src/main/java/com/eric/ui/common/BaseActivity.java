package com.eric.ui.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eric.ui.utils.ActivityUtil;
import com.eric.view.dialog.ListComponentDialog;
import com.eric.view.dialog.ProgressDialog;
import com.eric.view.entity.ListItem;

import java.util.List;

/**
 * @author li
 * @Package com.eric.view.common
 * @Title: BaseActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class BaseActivity extends AppCompatActivity {

    private ActivityUtil mActivityUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityUtil = new ActivityUtil();
    }

    protected void showAlertDialog(String title, String message, String positiveLabel,
                                   View.OnClickListener onClickListener, String negativeLabel,
                                   View.OnClickListener onClickListener1, boolean cancelalbe) {
        mActivityUtil.showAlertDialog(this, title, message, positiveLabel, onClickListener, negativeLabel, onClickListener1, cancelalbe);
    }

    public void showConfirmDialog(String title, String message,
                                  String positiveLabel, View.OnClickListener onClickListener) {
        showAlertDialog(title, message, positiveLabel, onClickListener, null,
                null, false);
    }

    public void showConfirmDialog(String title, String message,
                                  String positiveLabel, View.OnClickListener onClickListener,
                                  boolean cancelable) {
        showAlertDialog(title, message, positiveLabel,
                onClickListener, null, null, cancelable);
    }

    public void showToast(String str, int duration) {
        mActivityUtil.showToast(this, str, duration);
    }

    public void showToast(String str) {
        mActivityUtil.showToast(this, str);
    }

    /**
     * 请求成功的toast。toast文字上面会有个勾的icon
     *
     * @param context 上下文
     * @param str     内容
     */
    public void showSuccessToast(Activity context, String str) {

        mActivityUtil.showSuccessToast(context, str);
    }

    public void showProgressDialog(String str) {
        mActivityUtil.showProgressDialog(this, str);
    }

    public void showProgressDialog(String str, ProgressDialog.OnProgressDialogListener listener) {
        mActivityUtil.showProgressDialog(this, str, listener);
    }

    public void showListDialog(String title, List<ListItem> list, ListComponentDialog.OnListDialogListener listener) {
        mActivityUtil.showListDialog(this, title, list, listener, false);
    }

    public void showListDialog(String title, List<ListItem> list, ListComponentDialog.OnListDialogListener listener, boolean useBlueStyle) {
        mActivityUtil.showListDialog(this, title, list, listener, useBlueStyle);
    }
}

