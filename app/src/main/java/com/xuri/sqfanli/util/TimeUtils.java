package com.xuri.sqfanli.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 何明洋
 * @Title: 时间相关的工具类
 * @Description:
 */
public class TimeUtils {

    public static String stampToString(String timeStamp, String formate) {
        if (timeStamp.length() == 10) {
            timeStamp = timeStamp + "000";
        } else if (timeStamp.length() == 13) {

        } else {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        return sdf.format(new Date(Long.valueOf(timeStamp)));
    }

    public static String getTime(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    public static String stampToString(long timeStamp, String formate) {
        if (timeStamp < 10000000000l) {
            timeStamp = timeStamp * 1000;
        } else if (timeStamp > 99999999999l) {

        } else {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        return sdf.format(new Date(timeStamp));
    }

    public static String getAbbreviateTime(long timeStamp) {
        long oneDay = 3600 * 24 * 1000;
        long current = System.currentTimeMillis();
        long startOfToday = getTimeStamp_startOfToday();
        long todayTotal = current - startOfToday;

        if (timeStamp - startOfToday < todayTotal
                && timeStamp - startOfToday > 0) {
            // 今天，显示时分
            return stampToString(timeStamp, "HH:mm");
            // return "今天";
        } else if (timeStamp < startOfToday - oneDay) {
            // timeSatamp - todayTotal - oneDay > 0
            return stampToString(timeStamp, "MM-dd");
            // return "昨天以前";
        } else {
            return "昨天";
        }

    }

    public static String getAbbreviateTime_chat(long timeStamp) {
        long oneDay = 3600 * 24 * 1000;
        long current = System.currentTimeMillis();
        long startOfToday = getTimeStamp_startOfToday();
        long todayTotal = current - startOfToday;

        if (timeStamp - startOfToday < todayTotal
                && timeStamp - startOfToday > 0) {
            // 今天
            int hour = Integer.valueOf(stampToString(timeStamp, "HH"));
            if (hour <= 12) {
                return "上午 " + stampToString(timeStamp, "HH:mm");
            } else {
                return "下午 " + stampToString(timeStamp, "HH:mm");
            }

        } else if (timeStamp < startOfToday - oneDay) {
            // 昨天以前
            return stampToString(timeStamp, "MM-dd HH:mm");
        } else {
            // 昨天
            return "昨天 " + stampToString(timeStamp, "HH:mm");
        }
    }

    /**
     * 获取今天零点的时间戳
     *
     * @return
     */
    public static long getTimeStamp_startOfToday() {
        long current = System.currentTimeMillis();
        String stamp = stampToString(String.valueOf(current), "yyyy-MM-dd");
        stamp = stringToStamp(stamp);
        return Long.valueOf(stamp);
    }

    public static String stringToStamp(String time) {
        String pattern = "";
        if (time.indexOf("-") != -1) {
            pattern = "yyyy-MM-dd";
        } else if (time.indexOf("日") != -1) {
            pattern = "yyyy年MM月dd日";
        } else if (time.indexOf("/") != -1) {
            pattern = "yyyy/MM/dd";
        } else {
            pattern = "yyyyMMdd";
        }

        if (time.indexOf(":") != -1) {
            pattern = pattern + " HH:mm:ss";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return "";
        }
        return String.valueOf(date.getTime());
    }

    /**
     * @param time
     * @param pattern
     * @return
     */
    public static long stringToStamp(String time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getRecentDate(String format) {
        try {
            String time = "";
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            time = formatter.format(now);
            return time;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回13位的时间戳
     *
     * @return
     */
    public static String getTimeStamp() {
        return String.valueOf(new Date().getTime());
    }


    /**
     * 获取两个时间的时间差 如1天2小时30分钟40秒 ，返回1:2:3:40
     */
    public static String getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        String day_s = String.valueOf(day);
        if (day_s.length() == 1) {
            day_s = "0" + day_s;
        }
        String hour_s = String.valueOf(hour);
        if (hour_s.length() == 1) {
            hour_s = "0" + hour_s;
        }
        String min_s = String.valueOf(min);
        if (min_s.length() == 1) {
            min_s = "0" + min_s;
        }
        String sec_s = String.valueOf(sec);
        if (sec_s.length() == 1) {
            sec_s = "0" + sec_s;
        }

        return day_s + ":" + hour_s + ":" + min_s + ":" + sec_s;
    }
}
