package com.eric.ui.utils;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.eric.view.LoadingView;
import com.eric.view.dialog.AlertComponentDialog;
import com.eric.view.dialog.ListComponentDialog;
import com.eric.view.dialog.ProgressDialog;
import com.eric.view.entity.ListItem;
import com.eric.view.utils.ToastUtil;

import java.util.List;

/**
 * activity帮助类，上层显示toast 转圈Dialog更方便
 */
public class ActivityUtil {

    /**
     * 统一化弹窗
     */
    private AlertComponentDialog mAlertDialog;
    /**
     * 转圈Dialog
     */
    private ProgressDialog mLJProgressDialog;

    /**
     * 列表弹窗
     */
    private ListComponentDialog mLJListDialog;

    /**
     * 转圈的View
     */
    private LoadingView mLJLoadingView;

    public ActivityUtil() {
        mLJProgressDialog = new ProgressDialog();
    }

    /**
     * 显示弹窗
     *
     * @param context          上下文
     * @param title            头部信息
     * @param message          内容
     * @param positiveLabel    底部右边按钮内容
     * @param onClickListener  点击右边按钮监听
     * @param negativeLabel    左边按钮内容
     * @param onClickListener1 左边按钮监听
     * @param cancelable       是否可以cancel
     */
    public void showAlertDialog(Activity context, String title, String message, String positiveLabel, View.OnClickListener
            onClickListener, String negativeLabel, View.OnClickListener onClickListener1, boolean cancelable) {
        if (context == null || context.isFinishing()) {
            return;
        }
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
        mAlertDialog = new AlertComponentDialog(context).setTitle(title).setMsg(message).setPositiveButton(positiveLabel,
                onClickListener).setNegativeButton(negativeLabel, onClickListener1);
        mAlertDialog.setCancelable(cancelable);
        mAlertDialog.show();
    }

    /**
     * 显示toast。默认时间 LENGTH_LONG
     *
     * @param context 上下文
     * @param str     显示内容
     */
    public void showToast(Activity context, String str) {
        showToast(context, str, Toast.LENGTH_LONG);
    }


    /**
     * 显示toast
     *
     * @param context  上下文
     * @param str      toast内容
     * @param duration 时间  Toast.LENGTH_LONG or LENGTH_SHORT
     */
    public void showToast(Activity context, String str, int duration) {
        ToastUtil.showToast(context, str, duration);
    }

    /**
     * 请求成功的toast。toast文字上面会有个勾的icon
     *
     * @param context 上下文
     * @param str     内容
     */
    public void showSuccessToast(Activity context, String str) {
        ToastUtil.showSuccessToast(context, str);
    }

    /**
     * 取消toast
     */
    public void dismissToast() {
        ToastUtil.dismissToast();
    }

    /**
     * 显示进度加载框
     *
     * @param context 上下文
     * @param str     加载框文案
     */
    public void showProgressDialog(Activity context, CharSequence str) {
        if (context == null || context.isFinishing()) {
            return;
        }
        mLJProgressDialog.showDialog(context, str);
    }

    /**
     * 显示进度加载框
     *
     * @param context  上下文
     * @param str      加载框文案
     * @param listener 记载进度监听
     */
    public void showProgressDialog(Activity context, CharSequence str, ProgressDialog.OnProgressDialogListener listener) {
        if (context == null || context.isFinishing()) {
            return;
        }
        mLJProgressDialog.showDialog(context, str, listener);
    }

    /**
     * 取消进度加载框
     */
    public void dismissProgressDialog() {
        mLJProgressDialog.dismissDialog();
    }

    /**
     * 显示lsit选择框，从底部弹出
     *
     * @param context      上下文
     * @param title        title
     * @param list         内容（List）
     * @param listener     点击回调
     * @param useBlueStyle 是否是蓝色样式。样式分为蓝色和白色
     */
    public void showListDialog(Activity context, String title, List<ListItem> list, ListComponentDialog.OnListDialogListener listener,
                               boolean useBlueStyle) {
        if (context == null || context.isFinishing()) {
            return;
        }
        mLJListDialog = new ListComponentDialog(context);
        mLJListDialog.setTitle(title);
        mLJListDialog.setDataSource(list);
        mLJListDialog.setOnListDialogListener(listener);
        mLJListDialog.setUseBlueStyle(useBlueStyle);
        mLJListDialog.show();
    }

    /**
     * 显示lsit选择框，从底部弹出
     *
     * @param context  上下文
     * @param title    title
     * @param list     内容（List）
     * @param listener 点击回调
     */
    public void showListDialog(Activity context, String title, List<ListItem> list, ListComponentDialog.OnListDialogListener listener) {
        this.showListDialog(context, title, list, listener, false);
    }

    /**
     * progressView 显示的父布局，居中显示
     *
     * @param view 依附的view
     */
    public void showProgressView(Activity context, View view) {
        if (context == null || context.isFinishing()) {
            return;
        }
        if (mLJLoadingView == null) {
            mLJLoadingView = new LoadingView(context);
        } else {
            mLJLoadingView.detachFromWindow();
        }
        mLJLoadingView.attachToWindowCenter(view);
    }

    /**
     * 取消progressView
     *
     * @param context 上下文
     */
    public void dismissProgressView(Activity context) {
        if (context == null || context.isFinishing()) {
            return;
        }
        if (mLJLoadingView != null) {
            mLJLoadingView.detachFromWindow();
        }
    }

    /**
     * 获取alter弹窗
     *
     * @return
     */
    public AlertComponentDialog getAlertDialog() {
        return mAlertDialog;
    }

    /**
     * 获取转圈弹窗
     *
     * @return
     */
    public ProgressDialog getProgressDialog() {
        return mLJProgressDialog;
    }

    /**
     * 获取加载控件
     *
     * @return
     */
    public LoadingView getLoadingView() {
        return mLJLoadingView;
    }

    /**
     * 获取列表弹窗
     *
     * @return
     */
    public ListComponentDialog getLJListDialog() {
        return mLJListDialog;
    }

}
