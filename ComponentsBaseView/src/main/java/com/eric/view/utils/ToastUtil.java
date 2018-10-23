package com.eric.view.utils;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eric.view.R;

/**
 * @author li
 * @Package com.eric.view.utils
 * @Title: ToastUtil
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * Toast工具类
 */
public class ToastUtil {
    /**
     * 不显示文字上方的icon
     */
    private static int NO_TIP_DRAWABLE = 0;

    /**
     * Toast对象
     */
    private static Toast mToast;

    /**
     * 显示Toast
     *
     * @param context 上下文
     * @param text    提示内容
     */
    public static void showToast(Context context, String text) {
        showToast(context, text, NO_TIP_DRAWABLE, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast
     *
     * @param context 上下文
     * @param text    提示内容
     */
    public static void showToast(Context context, String text, int duration) {
        showToast(context, text, NO_TIP_DRAWABLE, duration);
    }


    /**
     * 显示成功的Toast 图片定死
     *
     * @param context 上下文
     * @param text    提示内容
     */
    public static void showSuccessToast(Context context, String text) {
        showToast(context, text, R.drawable.baseview_toast_success, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast
     *
     * @param context    上下文
     * @param text       提示
     * @param drawableId 提示上面的图片id
     */
    public static void showToast(Context context, String text, int drawableId, int duration) {

        if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
            if (context == null || TextUtils.isEmpty(text)) {
                return;
            }
            if (mToast == null) {
                View view = LayoutInflater.from(context).inflate(R.layout.baseview_toast, null);
                mToast = new Toast(context);
                mToast.setDuration(duration);
                mToast.setGravity(Gravity.CENTER, 0, 0);
                mToast.setView(view);
                updateUI(mToast.getView(), text, drawableId);
            } else {
                updateUI(mToast.getView(), text, drawableId);
            }
            try {
                mToast.show();
            } catch (NullPointerException var3) {

            }
        }
    }

    /**
     * 取消toast
     */
    public static void dismissToast(){
        if(mToast != null){
            mToast.cancel();
        }
    }

    /**
     * 通过数据更新UI
     *
     * @param view       整个容器
     * @param text       提示内容
     * @param drawableId 提示上面的图片id
     */
    private static void updateUI(View view, String text, int drawableId) {
        TextView txtContainer = (TextView) view.findViewById(R.id.txt_content);
        ImageView imgTip = (ImageView) view.findViewById(R.id.img_tip);
        txtContainer.setText(text);
        if (drawableId == NO_TIP_DRAWABLE) {
            imgTip.setVisibility(View.GONE);
        } else {
            imgTip.setVisibility(View.VISIBLE);
            imgTip.setImageResource(drawableId);
        }
    }
}
