package com.eric.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.eric.view.R;

/**
 * @author li
 * @Package com.eric.view.dialog
 * @Title: ProgressDialog
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 加载弹窗
 */
public class ProgressDialog {
    /**
     * 弹窗
     */
    private Dialog dlg = null;

    /**
     * 对dialog的监听
     */
    private OnProgressDialogListener progressDialogListener = null;

    /**
     * dialog的监听接口
     */
    public interface OnProgressDialogListener {

        /**
         * dialog取消的回调
         */
        void onCancelled();
    }

    /**
     * 设置dialog中的提示文案
     *
     * @param strMsg 提示文案
     */
    public void setMessage(CharSequence strMsg) {
        if (dlg != null) {
            ((TextView) dlg.findViewById(R.id.waiting_tv)).setText(strMsg);
        }
    }

    /**
     * 显示dialog
     *
     * @param ct     上下文
     * @param strMsg 提示文案
     */
    public void showDialog(Context ct, CharSequence strMsg) {
        try {
            if (TextUtils.isEmpty(strMsg)) {
                strMsg = ct.getResources().getString(R.string.lj_text_loading);
            }
            if (ct != null && ct instanceof Activity && !((Activity) ct).isFinishing()) {
                if (dlg == null) {
                    dlg = new Dialog(ct, R.style.LJProgressDialog);
                    dlg.setContentView(R.layout.baseview_view_progress_dialog);
                    dlg.findViewById(R.id.view_loading).setVisibility(View.VISIBLE);
                    dlg.setCancelable(false);
                    dlg.setCanceledOnTouchOutside(false);
                    ((TextView) dlg.findViewById(R.id.waiting_tv)).setText(strMsg);
                    dlg.show();
                } else {
                    ((TextView) dlg.findViewById(R.id.waiting_tv)).setText(strMsg);
                    if (!dlg.isShowing()) {
                        dlg.show();
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 显示dialog
     *
     * @param ct     上下文
     * @param strMsg 提示文案
     * @param lsn    dialog监听
     */
    public void showDialog(Context ct, CharSequence strMsg, OnProgressDialogListener lsn) {
        try {
            if (TextUtils.isEmpty(strMsg)) {
                strMsg = ct.getResources().getString(R.string.lj_text_loading);
            }
            if (ct != null && ct instanceof Activity && !((Activity) ct).isFinishing()) {
                if (dlg == null) {
                    dlg = new Dialog(ct, R.style.LJProgressDialog);
                    dlg.setContentView(R.layout.baseview_view_progress_dialog);
                    dlg.findViewById(R.id.view_loading).setVisibility(View.VISIBLE);
                    dlg.setCancelable(true);
                    dlg.setCanceledOnTouchOutside(false);
                    ((TextView) dlg.findViewById(R.id.waiting_tv)).setText(strMsg);
                    progressDialogListener = lsn;

                    dlg.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface arg0) {
                            if (progressDialogListener != null) {
                                progressDialogListener.onCancelled();
                            }
                            dismissDialog();
                        }
                    });

                    dlg.show();
                } else {
                    ((TextView) dlg.findViewById(R.id.waiting_tv)).setText(strMsg);
                    if (!dlg.isShowing()) {
                        dlg.show();
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 取消弹窗
     */
    public void dismissDialog() {
        try {
            if (dlg != null && dlg.isShowing()) {
                dlg.findViewById(R.id.view_loading).setVisibility(View.GONE);
                dlg.dismiss();
                dlg = null;
            }
        } catch (Exception e) {
        }
    }
}
