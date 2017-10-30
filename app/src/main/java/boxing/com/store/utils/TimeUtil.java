// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TimeUtil.java

package boxing.com.store.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


// Referenced classes of package com.lty.jar.util:
//			LogUtil

public class TimeUtil {

    public static final String PATTERN_ALL = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_ALL_LESS = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_DAY = "yyyy-MM-dd";
    public static final String PATTERN_DATE_SHORT = "MM-dd HH:mm";

    public TimeUtil() {
    }


    public static String getDateTime() {
        SimpleDateFormat sdf = (SimpleDateFormat) DateFormat
                .getDateTimeInstance();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    public static String getChinaDateTime() {
        SimpleDateFormat sdf = (SimpleDateFormat) DateFormat
                .getDateTimeInstance();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * @param time long
     * @return "yyyy-MM-dd"
     */
    public static String getChinaDateTime(long time) {
        SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateTimeInstance();
        sdf.applyPattern("yyyy-MM-dd");
        return sdf.format(new Date(time));
    }

    /*将时间戳转为字符串*/
    public static String getDate2String(long time, String pattern) {
        SimpleDateFormat sdf = (SimpleDateFormat) DateFormat
                .getDateTimeInstance();
        sdf.applyPattern(pattern);
        return sdf.format(new Date(time));
    }

    /**
     * 将字符串转为时间戳
     *
     * @param time 格式"yyyy-MM-dd"
     **/
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 剩余时间天,时,秒
     *
     * @param time
     * @return
     */
    public static String getHourTime(long time) {
        int day = (int) (time / 1000 / 60 / 60 / 24);
        int hour = (int) ((time - day * 24 * 60 * 60 * 1000L) / 1000 / 60 / 60);
        int minute = (int) ((time - day * 24 * 60 * 60 * 1000L - hour * 60 * 60 * 1000L) / 1000 / 60);
        int second = (int) ((time - day * 24 * 60 * 60 * 1000L - hour * 60 * 60
                * 1000L - minute * 60 * 1000L) / 1000);
        return day + "天" + hour + "小时" + minute + "分钟" + second + "秒";
    }

    /**
     * 大概时间
     *
     * @param time
     * @return
     */
    public static String getAboutHourTime(long time) {
        int day = (int) (time / 1000 / 60 / 60 / 24);
        int hour = (int) ((time - day * 24 * 60 * 60 * 1000L) / 1000 / 60 / 60);
        int minute = (int) ((time - day * 24 * 60 * 60 * 1000L - hour * 60 * 60 * 1000L) / 1000 / 60);
        int second = (int) ((time - day * 24 * 60 * 60 * 1000L - hour * 60 * 60
                * 1000L - minute * 60 * 1000L) / 1000);
        if (day > 0) {
            return day + "天前";
        } else {
            if (hour > 0) {
                return "" + hour + "小时前";
            } else {
                if (minute > 0) {
                    return "" + minute + "分钟前";
                } else {
                    return "刚刚";
                }
            }
        }
    }

    public static String getDate() {
        SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance();
        sdf.applyPattern("yyyy-MM-dd");
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 服务器上的时间转换成客户端时间
     */
    public static String serverToClientTime(String times) {
        if (times == null)
            return "";
        Calendar serverNow = Calendar.getInstance();
        // 从PHP转成Java的时间值,在末尾添加三位
        try {
            serverNow.setTime(new Date(Long.parseLong(times + "000")));
        } catch (NumberFormatException e) {
            return times;
        }
        int serverHour = serverNow.get(Calendar.HOUR_OF_DAY);
        int serverMinute = serverNow.get(Calendar.MINUTE);

        return serverHour + ":" + serverMinute;
    }

    public static String get(int index, String pattern) {
        SimpleDateFormat sdf;
        Calendar cal;
        sdf = (SimpleDateFormat) DateFormat.getDateInstance();
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, index);
        sdf.applyPattern(pattern);
        return sdf.format(cal.getTime());
    }

    public static String get(Date date, String pattern) {
        SimpleDateFormat sdf;
        Calendar cal;
        sdf = (SimpleDateFormat) DateFormat.getDateInstance();
        cal = Calendar.getInstance();
        cal.setTime(date);
        sdf.applyPattern(pattern);
        return sdf.format(cal.getTime());
    }

    public static Date get(String time, String pattern) {
        SimpleDateFormat sdf;
        sdf = (SimpleDateFormat) DateFormat.getDateTimeInstance();
        sdf.applyPattern(pattern);
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static long compare(Date former, Date later) {
        if (former == null || later == null)
            return 0L;
        else
            return (later.getTime() - former.getTime()) / 1000L;
    }

    public static String getCompare(Date begin, Date end) {
        if (begin == null || end == null)
            return "";
        long value = (end.getTime() - begin.getTime()) / 1000L;
        long day = value / 0x15180L;
        if (day > 0L)
            return (new StringBuilder(String.valueOf(day))).append("��ǰ")
                    .toString();
        if (day < 0L)
            return (new StringBuilder(String.valueOf(-day))).append("���")
                    .toString();
        long hour = value / 3600L;
        if (hour > 0L)
            return (new StringBuilder(String.valueOf(hour))).append("Сʱǰ")
                    .toString();
        if (hour < 0L)
            return (new StringBuilder(String.valueOf(-hour))).append("Сʱ��")
                    .toString();
        long min = value / 60L;
        if (min > 0L)
            return (new StringBuilder(String.valueOf(min))).append("����ǰ")
                    .toString();
        if (min < 0L)
            return (new StringBuilder(String.valueOf(-min))).append("���Ӻ�")
                    .toString();
        if (value >= 0L)
            return "�ո�";
        else
            return (new StringBuilder(String.valueOf(-value))).append("���Ӻ�")
                    .toString();
    }

    /**
     * 年,月,日
     *
     * @param dateString
     * @param i          (0,1,2)
     * @return
     */
    public static String getStringDateMonth(String dateString, int i) {
        String s_nd = dateString.substring(0, 4); // 年份
        String s_yf = dateString.substring(5, 7); // 月份
        String s_rq = dateString.substring(8, 10); // 日期
        switch (i) {
            case 0:
                return s_rq + "日";
            case 1:
                return s_yf + "月" + s_rq + "日";
            case 2:
                return s_nd + "年" + s_yf + "月" + s_rq + "日";
            default:
                break;
        }
        return s_nd + "年" + s_yf + "月" + s_rq + "日";
    }

    /**
     * 获取前几天或后几天
     *
     * @param day
     * @param i
     * @return
     */
    public static String getLastDay(String day, int i) {
        String[] split = day.split("-");
        int s_nd = Integer.parseInt(split[0]); // 年份
        int s_yf = Integer.parseInt(split[1]); // 月份
        int s_rq = Integer.parseInt(split[2]); // 日期
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(s_nd, s_yf - 1, s_rq);//月份是从0开始的，所以11表示12月
        Date now = ca.getTime();
        ca.add(Calendar.DAY_OF_YEAR, i); //减1
        Date lastMonth = ca.getTime(); //结果
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(lastMonth);

    }

    /**
     * 时分秒
     *
     * @param time
     * @return
     */
    public static String getHMSTime(long time) {
        int hour = (int) time / 1000 / 60 / 60;
        int minute = (int) ((time - hour * 60 * 60 * 1000L) / 1000 / 60);
        int second = (int) ((time - hour * 60 * 60
                * 1000L - minute * 60 * 1000L) / 1000);

        if (hour > 0) {
            return "" + hour + "小时";
        } else {
            if (minute > 0) {
                return "" + minute + "分钟" + second + "秒";
            } else {
                return "" + second + "秒";
            }
        }
    }

    /**
     * 弹出日期选择器
     *
     * @param textView 设置日期的textview
     */
    public static void showDataPicker(Context context, final TextView textView, final DialogInterface dialogInterface) {
        showDataPicker(context, textView, 0, dialogInterface);
    }

    /**
     *
     * @param textView 设置日期的textview
     * @param minDate  限制的最小时间
     * @param dialogInterface  回调
     */
    public static void showDataPicker(Context context, final TextView textView, long minDate, final DialogInterface
            dialogInterface) {
        long timeLong = getStringToDate(textView.getText().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeLong);
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        // 绑定监听器
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                String text = year + "-" + (++month) + "-" + dayOfMonth;
                textView.setText(getChinaDateTime(getStringToDate(text)));
                if (dialogInterface != null) {
                    dialogInterface.onClickYes(textView.getText().toString());
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMaxDate(System.currentTimeMillis());
        if (minDate >0){
            datePicker.setMinDate(minDate);
        }
    }

    public interface DialogInterface {
        void onClickYes(String str);
    }
}
