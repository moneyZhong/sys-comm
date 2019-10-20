package com.sys.comm.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    public static String formatDateToString(String zoneId, Date date, String format) {
        String result = null;
        if (date == null) {
            result = "";
        } else {
            SimpleDateFormat stdFormat = new SimpleDateFormat(format);
            if (zoneId != null && !"".equals(zoneId)) {
                stdFormat.setTimeZone(TimeZone.getTimeZone(zoneId));
            }
            result = stdFormat.format(date);
        }
        return result;
    }

    public static String formatDateToString(Date date, String format) {
        return formatDateToString(null, date, format);
    }

    public static Date stringToDate(String dateString,String format) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(dateString);
        return date;
    }

    public static String dateToString(Date date,String forMat){
        DateFormat dataFormat = new SimpleDateFormat(forMat);
        return dataFormat.format(date);
    }
    /**
     * 日期加上月数的时间
     * @param date
     * @param month
     * @return
     */
    public static Date dateAddMonth(Date date,int month){
        return add(date, Calendar.MONTH,month);
    }

    /**
     * 日期加上天数的时间
     * @param date
     * @param day
     * @return
     */
    public static Date dateAddDay(Date date,int day){
        return add(date,Calendar.DAY_OF_YEAR,day);
    }

    /**
     * 日期加上年数的时间
     * @param date
     * @param year
     * @return
     */
    public static Date dateAddYear(Date date,int year){
        return add(date,Calendar.YEAR,year);
    }
    /****
     * 根据类型添加日期
     * @param date
     * @param type 1-年,2-月，5-天
     * @param value
     * @return
     */
    public static Date add(Date date,int type,int value){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, value);
        return calendar.getTime();
    }
    /**
     * 得到当前日期
     * @return
     */
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(date);
    }

    /**
     * 得到指定时间当月第一天
     * @param date
     * @return
     */
    public static Date getMonthFirstDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
}
