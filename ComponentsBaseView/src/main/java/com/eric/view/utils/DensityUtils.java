package com.eric.view.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author li
 * @Package com.eric.view
 * @Title: DensityUtils
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 系统屏幕的一些操作
 */
public class DensityUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue 目标dp值
     * @return 目标px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue 目标px值
     * @return 目标dp值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     *
     * @param context 上下文
     * @param pxValue 目标px值
     * @return 目标sp值
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px
     *
     * @param context 上下文
     * @param spValue 目标sp值
     * @return 目标px值
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取dialog宽度
     *
     * @param aty 应用上下文
     * @return dialog宽度
     */
    public static int getDialogW(Context aty) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = aty.getResources().getDisplayMetrics();
        int w = dm.widthPixels - 100;
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth() - 100;
        return w;
    }

    /**
     * 获取屏幕宽度
     *
     * @param aty 应用上下文
     * @return 屏幕宽度
     */
    public static int getScreenW(Context aty) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = aty.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth();
        return w;
    }

    /**
     * 获取屏幕高度
     *
     * @param aty 应用上下文
     * @return 屏幕高度
     */
    public static int getScreenH(Context aty) {
        if (aty == null || aty instanceof Activity && ((Activity) aty).isFinishing()) {
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        dm = aty.getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        // int h = aty.getWindowManager().getDefaultDisplay().getHeight();
        return h;
    }

    /**
     * 获取屏幕密度
     *
     * @param context 应用上下文
     * @return 屏幕密度
     */
    public static float getDensity(Context context) {

        if (context == null || context instanceof Activity && ((Activity) context).isFinishing()) {
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();

        dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }
}
