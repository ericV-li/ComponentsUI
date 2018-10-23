package com.eric.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eric.view.R;

/**
 * @author li
 * @Package com.eric.view.dialog
 * @Title: AlertComponentDialog
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 提示对话框
 */
public class AlertComponentDialog {
    /**
     * 上下文
     */
    private Context context;

    /**
     * 弹窗
     */
    private Dialog dialog;

    /**
     * Dialog布局组件
     */
    private LinearLayout llyContainer;

    /**
     * Dialog标题
     */
    private TextView txtTitle;

    /**
     * Dialog 内容
     */
    private TextView txtMsg;

    /**
     * 内容之下的一个FrameLayout
     */
    private ViewGroup container = null;

    /**
     * 左边按钮
     */
    private Button btnLeft;

    /**
     * 底部横向分割线
     */
    private View viewDividerVertical;

    /**
     * 右边按钮
     */
    private Button btnRight;

    /**
     * 是否显示标题
     */
    private boolean showTitle = false;

    /**
     * 是否显示内容
     */
    private boolean showMsg = false;

    /**
     * 是否显示左边按钮
     */
    private boolean showPosBtn = false;

    /**
     * 是否显示右边按钮
     */
    private boolean showNegBtn = false;


    /**
     * 弹窗构造器
     *
     * @param context 上下文
     */
    public AlertComponentDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.baseview_alert_dialog, null);

        // 获取自定义Dialog布局中的控件
        llyContainer = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txtTitle = (TextView) view.findViewById(R.id.txt_title);
        txtTitle.setVisibility(View.GONE);
        txtMsg = (TextView) view.findViewById(R.id.txt_msg);
        txtMsg.setVisibility(View.GONE);
        btnLeft = (Button) view.findViewById(R.id.btn_neg);
        btnLeft.setVisibility(View.GONE);
        viewDividerVertical = view.findViewById(R.id.view_divider_vertical);
        viewDividerVertical.setVisibility(View.GONE);
        btnRight = (Button) view.findViewById(R.id.btn_pos);
        btnRight.setVisibility(View.GONE);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.LJAlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        // int width = (int) (display.getWidth() * 0.95);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.8);// display.getWidth();
        llyContainer.setLayoutParams(new FrameLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT));
        container = (ViewGroup) view.findViewById(R.id.content);
    }

    /**
     * 构建弹窗
     *
     * @return LJAlertDialog弹窗
     */
    @Deprecated
    public AlertComponentDialog builder() {
        // 获取Dialog布局
        return this;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     * @return LJAlertDialog
     */
    public AlertComponentDialog setTitle(String title) {
        if (null != title && !"".equals(title)) {
            showTitle = true;
            txtTitle.setText(title);
        }
        return this;
    }

    /**
     * 设置标题文字颜色
     *
     * @param resId 当resId为0时，使用默认颜色，不再设置颜色。
     * @return LJAlertDialog
     */
    public AlertComponentDialog setTitleColor(int resId) {
        if (resId != 0) {
            txtTitle.setTextColor(resId);
        }
        return this;
    }

    /**
     * 设置内容
     *
     * @param msg 内容
     * @return LJAlertDialog
     */
    public AlertComponentDialog setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            showMsg = true;
            txtMsg.setText(msg);
        }
        return this;
    }

    /**
     * 设置弹窗是否可取消弹窗
     *
     * @param cancel true：点击可取消弹窗。 false：点击不可取消。
     * @return LJAlertDialog
     */
    public AlertComponentDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * 设置弹窗点击外部是否可取消弹窗
     *
     * @param cancel true：点击外部可取消弹窗。 false：点击外部不可取消
     * @return LJAlertDialog
     */
    public AlertComponentDialog setCancelableOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 设置弹窗消失的监听回调
     *
     * @param listener 监听
     * @return LJAlertDialog
     */
    public AlertComponentDialog setOnDismissListener(DialogInterface.OnDismissListener listener) {
        dialog.setOnDismissListener(listener);
        return this;
    }

    /**
     * 设置左边按钮
     *
     * @param text     左边按钮显示文本
     * @param listener 回调
     * @return LJAlertDialog
     */
    public AlertComponentDialog setPositiveButton(String text, final View.OnClickListener listener) {
        if (text == null && listener == null) {
            return this;
        } else {
            showPosBtn = true;
            if (null == text || "".equals(text)) {
                btnRight.setText("确定");
            } else {
                btnRight.setText(text);
            }
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(v);
                    }
                    dialog.dismiss();
                }
            });
        }
        return this;
    }

    /**
     * 设置右边按钮
     *
     * @param text     按钮文本
     * @param listener 点击监听
     * @return LJAlertDialog
     */
    public AlertComponentDialog setNegativeButton(String text, final View.OnClickListener listener) {
        if (text == null && listener == null) {
            return this;
        } else {
            showNegBtn = true;
            if (null == text || "".equals(text)) {
                btnLeft.setText("取消");
            } else {
                btnLeft.setText(text);
            }
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(v);
                    }
                    dialog.dismiss();
                }
            });
        }
        return this;
    }

    /**
     * 设置整体布局
     */
    private void setLayout() {
        if (showTitle) {
            txtTitle.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txtMsg.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btnRight.setText("确定");
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btnRight.setVisibility(View.VISIBLE);
            btnLeft.setVisibility(View.VISIBLE);
            viewDividerVertical.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btnRight.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && showNegBtn) {
            btnLeft.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置弹窗内容下的视图
     *
     * @param contentView 显示的组件
     * @return LJAlertDialog
     */
    public AlertComponentDialog setContentView(View contentView) {
        showTitle = false;
        txtTitle.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
        container.removeAllViews();
        container.addView(contentView);
        return this;
    }

    /**
     * 显示弹窗
     */
    public void show() {
        setLayout();
        dialog.show();
    }

    /**
     * 关闭弹窗
     */
    public void dismiss() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
    }

    /**
     * 返回左侧按钮整个button
     *
     * @return
     */
    public Button getLeftButton() {
        return btnLeft;
    }

    /**
     * 返回右侧按钮整个button
     *
     * @return
     */
    public Button getRightButton() {
        return btnRight;
    }
}
