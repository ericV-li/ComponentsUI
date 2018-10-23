package com.eric.ui.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.Looper;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;


/**
 * @author li
 * @Package com.eric.ui.utils
 * @Title: AppUtil
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class AppUtil {
    private static int iNotifyID = 0;
    private static int goodsNotify = 100000;

    /**
     * 应用是否在运行
     *
     * @param context 上下文
     * @return true代表运行, false代表不运行
     */
    public static boolean isRunning(Context context) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningTaskInfo> list = am.getRunningTasks(100);
            for (RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(context.getPackageName()) && info.baseActivity.getPackageName()
                        .equals(context.getPackageName())) {
                    return true;
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 获取移动设备IMEI码
     *
     * @param context 上下文
     * @return 移动设备IMEI码
     */
    public static String getPhoneIMEI(Context context) {
        String deviceId = "";
        try {
            if (context != null) {
                TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (mTelephonyMgr != null) {
                    deviceId = mTelephonyMgr.getDeviceId();
                    return deviceId;
                }
            }
        } catch (SecurityException e) {
        }
        return UUID.randomUUID().toString();
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getPhoneModel() {
        // 品牌
        String phoneType = Build.BRAND;
        StringBuilder sbStr = new StringBuilder();
        // 型号
        String phoneModel = Build.MODEL;
        if (!TextUtils.isEmpty(phoneType)) {
            sbStr.append(phoneType);
        }
        if (!TextUtils.isEmpty(phoneModel)) {
            sbStr.append(phoneModel);
        }
        return sbStr.toString();
    }

    /**
     * 获取操作系统
     *
     * @return 操作系统
     */
    public static String getPhoneSystem() {
        String phoneSystem = Build.VERSION.RELEASE;
        if (!TextUtils.isEmpty(phoneSystem)) {
            return phoneSystem;
        }
        return "";
    }

    /**
     * 判断是否为平板
     *
     * @param context 应用上下文
     * @return true代表平板，false代表不是平板
     */
    public static boolean isPad(Context context) {
        if (context == null) {
            return false;
        }
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration
                .SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * final Activity activity ：调用该方法的Activity实例 long milliseconds ：震动的时长，单位是毫秒
     * long[] pattern ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     *
     * @param activity     调用该方法的Activity实例
     * @param milliseconds 震动的时长
     */

    public static void vibrate(Activity activity, long milliseconds) {
        if (activity == null) {
            return;
        }
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * final Activity activity ：调用该方法的Activity实例 long milliseconds ：震动的时长，单位是毫秒
     * long[] pattern ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     *
     * @param activity 调用该方法的Activity实例
     * @param pattern  自定义震动模式
     * @param isRepeat 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static void vibrate(Activity activity, long[] pattern, boolean isRepeat) {
        if (activity == null) {
            return;
        }
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

    /**
     * 获取渠道号
     * 获取application中指定的meta-data
     *
     * @param ctx 应用上下文
     * @param key meta-data的key
     * @return 如果没有获取成功(没有对应值 ， 或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager
                        .GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        if (applicationInfo.metaData.get(key) != null) {
                            resultData = applicationInfo.metaData.get(key).toString();
                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {

        }

        return resultData;
    }


    /**
     * 获取SerialNumber
     *
     * @return SerialNumber
     */
    public static String getSerialNumber() {
        String str;
        try {
            str = Build.SERIAL;
        } catch (Exception e) {
            str = "";
        }
        return str;
    }


    /**
     * 获取 ipv4 new
     *
     * @return ipv4地址
     */
    public static String getLocalIpAddress() {

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {

        }
        return null;
    }

    /**
     * 获取IP
     *
     * @param activity 上下文
     * @return ip地址
     */
    public static String getIpAddress(Context activity) {
        if (activity == null) {
            return null;
        }
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo == null) {
                return null;
            }
            int ipAddress = wifiInfo.getIpAddress();
            return intToIp(ipAddress);
        }
        return null;
    }

    /**
     * 转成IP
     *
     * @param i 整形
     * @return IP字符串
     */
    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }

    private static Toast mToast;

    /**
     * 弹出toast
     *
     * @param ct   上下文
     * @param text 展示文案
     */
    public static void showToast(Context ct, String text) {
        if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
            if (mToast == null) {
                mToast = Toast.makeText(ct.getApplicationContext(), text, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                mToast.setText(text);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            try {
                mToast.show(); //VIVO Y913 4.4.4手机位于后台源码内部空指针
            } catch (NullPointerException e) {
            }

        }
//		showMessageBar(ct,text);
    }

    /**
     * 自动隐藏软键盘
     *
     * @param cxt  上下文
     * @param view 获取窗口焦点的view
     */
    public static void showInput(Context cxt, View view) {
        if (cxt == null || view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) cxt.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
    }

    /**
     * 防止连续点击的时间间隔
     */
    public static final long INTERVAL = 1000L;
    /**
     * 上一次点击的时间
     */
    private static long lastClickTime = 0L;

    /**
     * 防止按钮过快点击
     *
     * @return 是否点击过快
     */
    public static boolean clickFilter() {
        long curr_time = System.currentTimeMillis();

        if ((curr_time - lastClickTime) > INTERVAL) {
            lastClickTime = curr_time;
            return false;
        } else {
            lastClickTime = curr_time;
            return true;
        }

    }

    /**
     * 检查权限是否合法
     *
     * @param context 上下文
     * @param op      权限
     * @return 整形
     */
    public static int checkOp(Context context, String op) {
        if (context == null || TextUtils.isEmpty(op)) {
            return -1;
        }
        int opindex = strOpToOp(op);
        if (opindex == -1) {
            return -1;
        }
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            Object object = context.getSystemService(Context.APP_OPS_SERVICE);
            Class c = object.getClass();
            try {
                Class[] cArg = new Class[3];
                cArg[0] = int.class;
                cArg[1] = int.class;
                cArg[2] = String.class;
                Method lMethod = c.getDeclaredMethod("checkOp", cArg);
                return (Integer) lMethod.invoke(object, opindex, Binder.getCallingUid(), context.getPackageName());
            } catch (NoSuchMethodException e) {

            } catch (IllegalAccessException e) {
            } catch (IllegalArgumentException e) {

            } catch (InvocationTargetException e) {

            }
        }
        return -1;
    }

    /**
     * This provides a simple name for each operation to be used
     * in debug output.
     */
    private static String[] sOpNames = new String[]{"COARSE_LOCATION", "FINE_LOCATION", "GPS", "VIBRATE", "READ_CONTACTS",
            "WRITE_CONTACTS", "READ_CALL_LOG", "WRITE_CALL_LOG", "READ_CALENDAR", "WRITE_CALENDAR", "WIFI_SCAN",
            "POST_NOTIFICATION", "NEIGHBORING_CELLS", "CALL_PHONE", "READ_SMS", "WRITE_SMS", "RECEIVE_SMS",
            "RECEIVE_EMERGECY_SMS", "RECEIVE_MMS", "RECEIVE_WAP_PUSH", "SEND_SMS", "READ_ICC_SMS", "WRITE_ICC_SMS",
            "WRITE_SETTINGS", "SYSTEM_ALERT_WINDOW", "ACCESS_NOTIFICATIONS", "CAMERA", "RECORD_AUDIO", "PLAY_AUDIO",
            "READ_CLIPBOARD", "WRITE_CLIPBOARD", "TAKE_MEDIA_BUTTONS", "TAKE_AUDIO_FOCUS", "AUDIO_MASTER_VOLUME",
            "AUDIO_VOICE_VOLUME", "AUDIO_RING_VOLUME", "AUDIO_MEDIA_VOLUME", "AUDIO_ALARM_VOLUME", "AUDIO_NOTIFICATION_VOLUME",
            "AUDIO_BLUETOOTH_VOLUME", "WAKE_LOCK", "MONITOR_LOCATION", "MONITOR_HIGH_POWER_LOCATION", "GET_USAGE_STATS",
            "MUTE_MICROPHONE", "TOAST_WINDOW", "PROJECT_MEDIA", "ACTIVATE_VPN",};

    /**
     * 权限校验
     *
     * @param op 操作权限名称
     * @return 返回整形
     */
    public static int strOpToOp(String op) {
        if (TextUtils.isEmpty(op)) {
            return -1;
        }
        int index = -1;
        for (int i = 0; i < sOpNames.length; i++) {
            if (op.equals(sOpNames[i])) {
                index = i;
                break;
            }
        }

        return index;
    }

    /**
     * 字符串转整形
     *
     * @param str 字符串
     * @return 整形
     */
    public static int strToInt(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {

        }
        return -1;
    }

    /**
     * 字符串转float
     *
     * @param str 字符串
     * @return float
     */
    public static float strToFloat(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1.0f;
        }
        try {
            return Float.valueOf(str);
        } catch (NumberFormatException e) {

        }
        return -1.0f;
    }

    /**
     * 字符串转double
     *
     * @param str 字符串
     * @return double
     */
    public static double strToDouble(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1.00d;
        }
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e) {

        }
        return -1.00d;
    }

    /**
     * 字符串转long
     *
     * @param str 字符串
     * @return long
     */
    public static long strToLong(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1L;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {

        }
        return -1L;
    }

    /**
     * 获取APK VersionCode版本
     *
     * @param ct 上下文
     * @return APK VersionCode版本
     */
    public static Integer getApkVersionCode(Context ct) {
        Integer apkVer = 0;
        if (ct == null) {
            return apkVer;
        }
        try {
            PackageManager pm = ct.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ct.getPackageName().toString(), 0);
            apkVer = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return apkVer;
    }

    /**
     * 获取APK Version版本
     *
     * @param ct 上下文
     * @return APK Version版本
     */
    public static String getApkVerion(Context ct) {
        String apkVer = null;
        if (ct == null) {
            return apkVer;
        }
        try {
            PackageManager pm = ct.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ct.getPackageName().toString(), 0);
            apkVer = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return apkVer;
    }

    /**
     * 获取应用包名
     *
     * @param ct 应用上下文
     * @return 应用包名
     */
    public static String getPackagesName(Context ct) {
        if (ct == null) {
            return null;
        }
        PackageInfo info;
        try {
            info = ct.getPackageManager().getPackageInfo(ct.getPackageName(), 0);
            // 当前版本的包名
            String packageNames = info.packageName;
            return packageNames;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return null;
    }

    /**
     * 移除目标字符串内的空格
     *
     * @param key 目标文本字符串
     * @return 返回移除空格后的字符串
     */
    public static String textRemoveAllSpace(String key) {
        if (key == null) {
            return null;
        }
        return key.replaceAll(" +", "");
    }

    /**
     * 改变文字颜色
     *
     * @param context  上下文
     * @param str      文字
     * @param index    位置索引
     * @param resource 颜色资源
     * @return SpannableStringBuilder
     */

    public static SpannableStringBuilder setWarningColor(Context context, String str, int index, int resource) {
        if (str != null) {
            SpannableStringBuilder builder = new SpannableStringBuilder(str);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(context.getResources().getColor(resource));
            builder.setSpan(redSpan, index, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return builder;
        }
        return null;
    }

    /**
     * 改变文字颜色
     *
     * @param context  上下文
     * @param str      文字
     * @param index    其实索引
     * @param end      结束索引
     * @param resource 颜色资源
     * @return SpannableStringBuilder
     */
    public static SpannableStringBuilder setWarningColor(Context context, String str, int index, int end, int resource) {
        if (str != null) {
            SpannableStringBuilder builder = new SpannableStringBuilder(str);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(context.getResources().getColor(resource));
            builder.setSpan(redSpan, index, str.length() - end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return builder;
        }
        return null;
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static String getCurrentTimes() {
        String currentTime = "";
        SimpleDateFormat shijian = new SimpleDateFormat("HH:mm:ss");
        currentTime = shijian.format(new java.util.Date());
        return currentTime;
    }

    /**
     * 单独为了传信推送做的一个时间比较器，仅在xmpp和BaseActivity中使用，firstTime为获取的系统时间，必不为空，第二个有可能为空
     *
     * @param firstTime  firstTime为获取的系统时间 必不为空
     * @param secondTime 有可能为空
     * @return 返回比较结果
     */

    public static boolean timeCompare(String firstTime, String secondTime) {
        String[] saveTimes, nowTimes;
        if (!TextUtils.isEmpty(firstTime)) {
            nowTimes = firstTime.split(":");
            if (!TextUtils.isEmpty(secondTime)) {
                saveTimes = secondTime.split(":");
                if (saveTimes[0].equals(nowTimes[0]) && saveTimes[1].equals(nowTimes[1])) {
                    int result = Math.abs(strToInt(saveTimes[2]) - strToInt(nowTimes[2]));

                    if (result >= 30) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;

    }

    /**
     * 程序是否在前台运行
     *
     * @param context 应用上下文
     * @return 是否在前台运行
     */
    public static boolean isAppOnForeground(Context context) {
        // Returns a list of application processes that are running on the
        // device
        if (context == null) {
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo
                    .IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * H5页面是否在webView Layer层面禁用硬件加速
     *
     * @return 是否开启硬件加速
     */
    private static boolean shouldDisableHardwareRenderInLayer() {

        // case : samsung on android 4.3 is know to cause crashes at libPowerStretch.so:0x2d4c,目前发现机型有SM-G7106,GT-I9507V,
        // GT-I9500,SM-G3518,SM-N9009

//        final boolean isSamsung = (android.os.Build.MANUFACTURER != null) && (android.os.Build.MANUFACTURER.equalsIgnoreCase
//                ("samsung"));
        final boolean isJbMr2 = Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2;
//        if (isSamsung && isJbMr2) {
//            return true;
//        }
        return isJbMr2;
    }

    /**
     * 在webView Layer层面禁用硬件加速 兼容三星机器
     *
     * @param webView webView实例
     */
    public static void disableHardwareRenderInLayer(WebView webView) {
        if (shouldDisableHardwareRenderInLayer()) {
            try {
                if (webView != null) {
                    webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * 根据角色获取sourceCode
     *
     * @param role 角色对象
     * @return SourceCode
     */
    public static String getSourceCode(Role role) {
        String sourceCode = "";
        switch (role) {
            case DRIVER:
                sourceCode = "0101010101";
                break;
            case OWNER:
                sourceCode = "0103010101";
                break;
            case MERCHANT:
                sourceCode = "0104010101";
                break;
            case PEACE_INN:
                sourceCode = "2401010101";
                break;
        }
        return sourceCode;
    }

    /**
     * 获取角色名称
     *
     * @param role 角色对象
     * @return 角色名称
     */
    public static String getRole(Role role) {
        String roleName = "";
        switch (role) {
            case DRIVER:
                roleName = "driver";
                break;
            case OWNER:
                roleName = "shipper";
                break;
            case MERCHANT:
                roleName = "merchant";
                break;
            case PEACE_INN:
                roleName = "harbour";
                break;
        }
        return roleName;
    }

    /**
     * 角色枚举
     */
    public enum Role {
        /**
         * 司机
         */
        DRIVER, /**
         * 货主
         */
        OWNER, /**
         * 商家
         */
        MERCHANT,

        /**
         * 安心驿站
         */
        PEACE_INN
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {

        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {

            }
        }
        return null;
    }

    /**
     * Key
     */
    public static String KEY = "$&%@.!^~";
}
