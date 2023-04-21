package com.wja.keren.user.home.util;



import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class FormatUtil {

    //日期格式化
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    // 秒转成时:分:秒
    private static DecimalFormat secondFormat = new DecimalFormat("00");



    /**
     * 日期 格式化
     * @param date 时间 单位 毫秒
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate(long date) {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08")); //设置时区
        String createDate = dateFormat.format(date);   //格式转换
        return createDate;
    }

    /**
     * 日期格式化
     * @param year 年
     * @param month 月 (1 月 为0,12月 为11) 从0开始
     * @param day 天
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate(int year,int month,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return formatDate(calendar);
    }

    /**
     * 日期 格式化
     * @param calendar 日历
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate(Calendar calendar) {
        return  formatDate(calendar.getTimeInMillis());
    }

    public static int formatDateToSecond(String date) {

        return (int)(dateFormat.parse(date,new ParsePosition(0)).getTime() / 1000);
    }

    /**
     *  把秒变成时间格式
     * @param seconds 单位秒
     * @return 时:分:秒
     */
    public static String formatSeconds(int seconds) {
        int hour = seconds / 3600;
        int minute = (seconds - hour * 3600) / 60;
        int sec = seconds - hour * 3600 - minute * 60;

        String time = secondFormat.format(hour) +":" + secondFormat.format(minute) +":"+secondFormat.format(sec);

        if(hour == 0) return time.substring(3);
        return time;
    }

    public static String formatTime(int time){
        return  secondFormat.format(time);
    }

    public static String formatHourMinute(int minute){
        return secondFormat.format(minute / 60) + ":" + secondFormat.format(minute % 60);
    }
}
