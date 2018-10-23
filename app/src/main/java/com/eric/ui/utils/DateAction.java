package com.eric.ui.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author li
 * @Package com.eric.ui.utils
 * @Title: DateAction
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class DateAction {
    /**
     * 返回HH:mm:ss格式时间
     *
     * @return String
     */
    public static String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 返回yyyy-MM-dd格式时间
     *
     * @return String
     */
    public static String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    /**
     * 返回yyyy-MM-dd HH:mm:ss格式时间
     *
     * @return String
     */
    public static String getDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 返回yyyyMMdd格式时间
     *
     * @return String
     */
    public static String getDateTimeNoNormal() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }

    /**
     * 根据根式返回时间
     *
     * @param seconds UTC时间戳
     * @param formate 时间格式化
     * @return String
     */
    public static String dateFormate(long seconds, String formate) {
        Date date = new Date(seconds);
        return dateFormate(date, formate);
    }

    /**
     * 根据根式返回时间
     *
     * @param date    Date对象
     * @param formate 时间格式化
     * @return String
     */
    public static String dateFormate(Date date, String formate) {
        if (date != null) {
            SimpleDateFormat sdf = null;
            sdf = new SimpleDateFormat(formate);
            return sdf.format(date);
        } else {
            return null;
        }
    }

    /**
     * 返回两个时间之间差值
     *
     * @param earlyDate        初始时间
     * @param lateDate         结束时间
     * @param returnTimeFormat 返回时间格式
     * @return 两个时间之间差值
     */
    public static int getBetweenTime(Calendar earlyDate, Calendar lateDate, int returnTimeFormat) {
        earlyDate = (Calendar) earlyDate.clone();
        lateDate = (Calendar) lateDate.clone();
        int time = 0;
        while (earlyDate.before(lateDate)) {
            earlyDate.add(returnTimeFormat, 1);
            time++;
        }
        return time;
    }

    /**
     * 返回两个时间之间差值
     *
     * @param earlyDate        初始时间
     * @param lateDate         结束时间
     * @param returnTimeFormat 返回时间格式
     * @return 两个时间之间差值
     */
    public static int getBetweenTime(Date earlyDate, Date lateDate, int returnTimeFormat) {
        Calendar cnow = Calendar.getInstance();
        cnow.setTime(earlyDate);
        Calendar clast = Calendar.getInstance();
        clast.setTime(lateDate);

        return getBetweenTime(cnow, clast, returnTimeFormat);
    }

    /**
     * 返回两个时间之间的天数差值
     *
     * @param begin 初始时间
     * @param last  结束时间
     * @return 天数差值
     */
    public static int getBetweenDays(Date begin, Date last) {
        return getBetweenTime(begin, last, Calendar.DATE);
    }

    /**
     * @param time     判断时间戳
     * @param isMinSec 除了今天，是否显示完整时间格式
     * @return 通俗理解时间
     */
    public static String getDateFinal(long time, boolean isMinSec) {
        long current = System.currentTimeMillis();
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(time);
        if (isSameDay(current, time)) {

            int hour = c1.get(Calendar.HOUR_OF_DAY);
            int min = c1.get(Calendar.MINUTE);
            if (isMinSec) {

                return "今天 " + (hour < 10 ? ("0" + hour) : hour) + ":" + (min < 10 ? ("0" + min) : min);
            } else {
                return (hour < 10 ? ("0" + hour) : hour) + ":" + (min < 10 ? ("0" + min) : min);

            }
        } else if (isYesterday(current, time)) {
            if (isMinSec) {
                int hour = c1.get(Calendar.HOUR_OF_DAY);
                int min = c1.get(Calendar.MINUTE);

                return "昨天 " + (hour < 10 ? ("0" + hour) : hour) + ":" + (min < 10 ? ("0" + min) : min);
            } else {
                return "昨天";
            }
        } else {
            if (isMinSec) {
                int hour = c1.get(Calendar.HOUR_OF_DAY);
                int min = c1.get(Calendar.MINUTE);
                int day = c1.get(Calendar.DAY_OF_MONTH);
                int mon = c1.get(Calendar.MONTH) + 1;

                return mon + "月" + day + "日 " + (hour < 10 ? ("0" + hour) : hour) + ":" + (min < 10 ? ("0" + min) : min);
            } else {
                int day = c1.get(Calendar.DAY_OF_MONTH);
                int mon = c1.get(Calendar.MONTH) + 1;
                return mon + "月" + day + "日";
            }
        }
    }

    /**
     * 是否为同一天
     *
     * @param current 新时间
     * @param old     老时间
     * @return 是否为同一天
     */
    private static boolean isSameDay(long current, long old) {
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(current);
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(old);
        return c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR) && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
    }

    /**
     * 是否是昨天
     *
     * @param current 当前时间
     * @param old     需判定的时间
     * @return 是否是昨天
     */
    private static boolean isYesterday(long current, long old) {
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(current);
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(old);
        return (c1.get(Calendar.DAY_OF_YEAR) - 1) == c2.get(Calendar.DAY_OF_YEAR) && c1.get(Calendar.YEAR) == c2.get(Calendar
                .YEAR);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss时间字符串转换成long时间戳毫秒数
     *
     * @param str yyyy-MM-dd HH:mm:ss时间字符串
     * @return long时间戳毫秒数
     */
    public static long strToDateLong(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            return 0;
        }
        return date != null ? date.getTime() : -1;
    }

    /**
     * 处理时间中有2016-02-29 17:04:59.0,将时间格式精确到分
     *
     * @param time 时间
     * @return 处理时间中有2016-02-29 17:04:59.0,将时间格式精确到分
     */

    public static String getFinalTime(String time) {
        int index = time.lastIndexOf(":");
        if (index > 0) {
            time = time.substring(0, index);
        }
        return time;
    }

    /**
     * 时间转换成日期
     * 根据时间戳进行转换xx后到期
     *
     * @param now   当前时间
     * @param time1 目标时间
     * @return 根据时间戳进行转换xx后到期
     */
    public static String showExpireTime(long now, long time1) {
        long min1 = 60000l;
        long hours1 = 3600000l;
        long hours24 = 86400000l;
        long month = 2592000000l;
        long spaceTime = time1 - now;
        String tempTime = "";
        if (spaceTime < min1) {
            tempTime = "1分钟后到期";
        } else if (spaceTime > min1 && spaceTime <= hours1) {
            tempTime = (int) (spaceTime / min1) + "分钟后到期";
        } else if (spaceTime > hours1 && spaceTime <= hours24) {
            tempTime = (int) (spaceTime / hours1) + "小时后到期";
        } else if (spaceTime > hours24 && spaceTime < month) {
            // Date time2 = new Date(inputdateTime);
//			tempTime =(int) (spaceTime/hours24)+"天前";
            int expireDay = (int) (spaceTime / hours24);//得到到期的天数
            int expireHour = 0;
            long time2 = spaceTime % hours24;
            if (time2 > hours1) {
                expireHour = (int) (time2 / hours1);//得到不足一天的小时数
            }
            tempTime = expireDay + "天" + expireHour + "小时后到期";
        } else {
            if (time1 == 0l) {
                return "";
            }
            Date d = new Date(time1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            tempTime = sdf.format(d);
        }
        return tempTime;
    }

    /**
     * 字符串转换成yyy-MM-dd HH:mm:ss日期
     *
     * @param str 时间字符串
     * @return 字符串转换成yyy-MM-dd HH:mm:ss日期
     */
    public static String strToDateString(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date(AppUtil.strToLong(str)));
        return date;
    }

    /**
     * 根据时间格式返回毫秒值
     *
     * @param timeStr 时间
     * @return long 根据时间格式返回毫秒值
     */
    public static long getFormateTimeToLong(String timeStr) {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = d.parse(timeStr);
            return date.getTime();
        } catch (ParseException e) {
        }
        return 0l;
    }

    /**
     * 根据时间格式返回毫秒值
     *
     * @param timeStr 时间
     * @param format  时间格式
     * @return long 根据时间格式返回毫秒值
     */
    public static long getCustommateTimeToLong(String timeStr, String format) {
        SimpleDateFormat d = new SimpleDateFormat(format);
        try {
            Date date = d.parse(timeStr);
            return date.getTime();
        } catch (ParseException e) {
        }
        return 0l;
    }

    /**
     * 获取时间对应月
     *
     * @param time 时间
     * @return 获取时间对应月
     */
    public static String getDateMonth(String time) {
        String finalDate = null;
        if (!TextUtils.isEmpty(time)) {
            if (time.length() > 5) {
                finalDate = getFinalTime(time.substring(5, time.length()));
                return finalDate;
            }
        }
        return "";
    }

    /**
     * 获取时间对应小时
     *
     * @return 获取时间对应小时
     */
    public static String getDateHour() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
        return df.format(new Date());
    }

    /**
     * 获取时间对应的long型时间戳
     *
     * @param timeStr 时间字符串
     * @return 获取时间对应的long型时间戳
     */
    public static long StrToDateLong(String timeStr) {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try {
            Date date = d.parse(timeStr);
            return date.getTime();
        } catch (ParseException e) {

        }
        return 0l;
    }
}
