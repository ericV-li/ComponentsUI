package com.eric.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * @author li
 * @Package com.eric.view.ProcessDlgAction
 * @Title: ProcessDlgAction
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 阻塞屏幕，弹窗帮助类
 */

public class ProcessDlgAction {

    /**
     * 弹窗
     */
    private Dialog dlg = null;

    /**
     * 弹窗监听
     */
    private onProcessDialogListener oLsner = null;

    /**
     * 弹窗监听接口
     */
    public interface onProcessDialogListener {

        /**
         * 取消弹窗
         */
        void onCancelled();
    }

    /**
     * 设置弹窗内容
     *
     * @param strMsg 弹窗内容
     */
    public void setMessage(String strMsg) {
        if (dlg != null) {
            ((TextView) dlg.findViewById(R.id.waiting_tv)).setText(strMsg);
        }
    }

    /**
     * 显示dialog
     *
     * @param ct     上下文
     * @param strMsg 弹窗文案
     * @param lsn    弹窗监听
     */
    public void showDialog(Context ct, String strMsg, onProcessDialogListener lsn) {
        try {
            if (TextUtils.isEmpty(strMsg)) {
                strMsg = "加载中...";
            }
            if (ct != null && ct instanceof Activity && !((Activity) ct).isFinishing()) {
                if (dlg == null) {
                    dlg = new Dialog(ct, R.style.LJProgressDialog);
                    dlg.setContentView(R.layout.baseview_view_progress_dialog);
                    dlg.findViewById(R.id.view_loading).setVisibility(View.VISIBLE);
                    dlg.setCancelable(true);
                    dlg.setCanceledOnTouchOutside(false);
                    ((TextView) dlg.findViewById(R.id.waiting_tv)).setText(strMsg);
                    oLsner = lsn;

                    dlg.setOnCancelListener(new OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface arg0) {
                            if (oLsner != null) {
                                oLsner.onCancelled();
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
     * 显示dialog
     *
     * @param ct     上下文
     * @param strMsg 弹窗文案
     */
    public void showDialog(Context ct, String strMsg) {
        try {
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
     * 取消弹窗
     */
    public void dismissDialog() {
        try {
            if (dlg != null && dlg.isShowing()) {
                dlg.dismiss();
                dlg = null;
            }
        } catch (Exception e) {
        }
    }
}