package com.lab.hosaily.core.im.utils;

import com.lab.hosaily.core.im.consts.UtilsException;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
public class DateUtils {

    //默认时间格式模板
    private static final String DEFAULT_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    //时间单位换算
    private static final long SECOND_TO_MILLISECOND  = 1000;
    private static final long MINUTE_TO_MILLISECOND  = SECOND_TO_MILLISECOND * 60;
    private static final long HOUR_TO_MILLISECOND    = MINUTE_TO_MILLISECOND * 60;
    private static final long DAY_TO_MILLISECOND     = HOUR_TO_MILLISECOND * 24;
    private static final long WEEK_TO_MILLISECOND    = DAY_TO_MILLISECOND * 7;

    /**
     * 格式化日期
     */
    public static String format(Date date, String pattern){
        if(date == null){
            throw new UtilsException("日期不能为空");
        }

        if(StringUtils.isBlank(pattern)){
            throw new UtilsException("时间格式不能为空");
        }

        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 格式化日期
     */
    public static String format(Date date){
        return format(date, DEFAULT_FORMAT_PATTERN);
    }

    /**
     * 格式化时间戳
     */
    public static String format(Long timestamp, String pattern){
        if(timestamp == null){
            throw new UtilsException("时间戳不能为空");
        }

        if(StringUtils.isBlank(pattern)){
            throw new UtilsException("时间格式不能为空");
        }

        Date date = GsonUtils.deserialize(timestamp.toString(), Date.class);
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 格式化时间戳
     */
    public static String format(Long timestamp){
        return format(timestamp, DEFAULT_FORMAT_PATTERN);
    }

    /**
     * 解析时间戳
     */
    public static Date parse(Long timestamp){
        if(timestamp == null){
            throw new UtilsException("时间戳不能为空");
        }

        return GsonUtils.deserialize(timestamp.toString(), Date.class);
    }

    /**
     * 根据模板解析日期字符串
     */
    public static Date parse(String date, String pattern, boolean lenient){
        if(StringUtils.isBlank(date)){
            throw new UtilsException("日期字符串不能为空");
        }

        if(StringUtils.isBlank(pattern)){
            throw new UtilsException("日期模板不能为空");
        }

        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.setLenient(lenient);

        final ParsePosition position = new ParsePosition(0);
        String _pattern = pattern;

        if(pattern.endsWith("ZZ")){
            _pattern = _pattern.substring(0, _pattern.length() - 1);
        }

        formatter.applyPattern(_pattern);
        position.setIndex(0);

        String content = date;
        if(pattern.endsWith("ZZ")){
            content = date.replaceAll("([-+][0-9][0-9]):([0-9][0-9])$", "$1$2");
        }

        Date result = formatter.parse(content, position);

        if(result == null || position.getIndex() != content.length()){
            throw new UtilsException("日期字符串[{0}]和模板[{1}]不相符", date, pattern);
        }

        return result;
    }

    /**
     * 判断两个时间是否为相同瞬间
     */
    public static boolean isSameTime(Date first, Date second){
        if(first == null || second == null){
            throw new UtilsException("比较的时间不能为空");
        }

        if(first == second){
            return true;
        }

        return (first.getTime() == second.getTime());
    }

    /**
     * 增加变量值
     */
    public static Date add(Date date, int field, int amount){
        if(date == null){
            date = new Date();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);

        return calendar.getTime();
    }

    /**
     * 为当前时间添加变量值
     */
    public static Date add(int field, int amount){
        return add(new Date(), field, amount);
    }

    /**
     * 为当前时间添加年数
     */
    public static Date addYears(int amount){
        return add(new Date(), Calendar.YEAR, amount);
    }

    /**
     * 为时间添加年数
     */
    public static Date addYears(Date date, int amount){
        if(date == null){
            throw new UtilsException("需要添加增量的时间不能为空");
        }

        return add(date, Calendar.YEAR, amount);
    }

    /**
     * 为当前时间添加月数
     */
    public static Date addMonths(int amount){
        return add(new Date(), Calendar.MONTH, amount);
    }

    /**
     * 为时间添加月数
     */
    public static Date addMonths(Date date, int amount){
        if(date == null){
            throw new UtilsException("需要添加增量的时间不能为空");
        }

        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 为当前时间添加日数
     */
    public static Date addDays(int amount){
        return add(new Date(), Calendar.DAY_OF_YEAR, amount);
    }

    /**
     * 为时间添加日数
     */
    public static Date addDays(Date date, int amount){
        if(date == null){
            throw new UtilsException("需要添加增量的时间不能为空");
        }

        return add(date, Calendar.DAY_OF_YEAR, amount);
    }

    /**
     * 为当前时间添加小时数
     */
    public static Date addHours(int amount){
        return add(new Date(), Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 为时间添加小时数
     */
    public static Date addHours(Date date, int amount){
        if(date == null){
            throw new UtilsException("需要添加增量的时间不能为空");
        }

        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 为当前时间添加分钟数
     */
    public static Date addMinutes(int amount){
        return add(new Date(), Calendar.MINUTE, amount);
    }

    /**
     * 为时间添加分钟数
     */
    public static Date addMinutes(Date date, int amount){
        if(date == null){
            throw new UtilsException("需要添加增量的时间不能为空");
        }

        return add(date, Calendar.MINUTE, amount);
    }

    /**
     * 为当前时间添加秒数
     */
    public static Date addSeconds(int amount){
        return add(new Date(), Calendar.SECOND, amount);
    }

    /**
     * 为时间添加秒数
     */
    public static Date addSeconds(Date date, int amount){
        if(date == null){
            throw new UtilsException("需要添加增量的时间不能为空");
        }

        return add(date, Calendar.SECOND, amount);
    }

    /**
     * 为当前时间添加毫秒数
     */
    public static Date addMilliseconds(int amount){
        return add(new Date(), Calendar.MILLISECOND, amount);
    }

    /**
     * 为时间添加毫秒数
     */
    public static Date addMilliseconds(Date date, int amount){
        if(date == null){
            throw new UtilsException("需要添加增量的时间不能为空");
        }

        return add(date, Calendar.MILLISECOND, amount);
    }

    /**
     * 指定日期的凌晨
     */
    public static Date firstTimeOfDate(Date date){
        if(date == null){
            throw new UtilsException("需要查询的时间不能为空");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 指定日期的午夜
     */
    public static Date lastTimeOfDate(Date date){
        if(date == null){
            throw new UtilsException("需要查询的时间不能为空");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 当天凌晨
     */
    public static Date firstTimeOfCurrentDate(){
        return firstTimeOfDate(new Date());
    }

    /**
     * 当天午夜
     */
    public static Date lastTimeOfCurrentDate(){
        return lastTimeOfDate(new Date());
    }

    /**
     * 指定日期所在月份的第一天凌晨
     */
    public static Date firstTimeOfMonth(Date date){
        if(date == null){
            throw new UtilsException("需要查询的时间不能为空");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 指定日期所在月份的最后一天午夜
     */
    public static Date lastTimeOfMonth(Date date){
        if(date == null){
            throw new UtilsException("需要查询的时间不能为空");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 当前日期所在月份的第一天凌晨
     */
    public static Date firstTimeOfCurrentMonth(){
        return firstTimeOfMonth(new Date());
    }

    /**
     * 当前日期所在月份的最后一天午夜
     */
    public static Date lastTimeOfCurrentMonth(){
        return lastTimeOfMonth(new Date());
    }

    /**
     * 指定日期所在年份的第一天凌晨
     */
    public static Date firstTimeOfYear(Date date){
        if(date == null){
            throw new UtilsException("需要查询的时间不能为空");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 指定日期所在年份的最后一天午夜
     */
    public static Date lastTimeOfYear(Date date){
        if(date == null){
            throw new UtilsException("需要查询的时间不能为空");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 当前日期所在年份的第一天凌晨
     */
    public static Date firstTimeOfCurrentYear(){
        return firstTimeOfYear(new Date());
    }

    /**
     * 当前日期所在年份的最后一天午夜
     */
    public static Date lastTimeOfCurrentYear(){
        return lastTimeOfYear(new Date());
    }

    /**
     * 休眠
     */
    public static void sleep(long milliseconds){
        try{
            Thread.sleep(milliseconds);
        }catch(Exception e){
            throw new UtilsException(e.getMessage(), e);
        }
    }

    /**
     * 查询当前时间
     */
    public static Long timestamp(boolean unix){
        return System.currentTimeMillis() / (unix ? 1000 : 1);
    }

    /**
     * 查询月份天数
     */
    public static Integer getDaysByMonth(int month){
        if(month < 0){
            throw new UtilsException("无效的月份");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 查询指定日期所在年份
     */
    public static Integer getYearByDate(Date date){
        if(date == null){
            throw new UtilsException("查询的日期不能为空");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);
    }

    /**
     * 查询指定日期所在月份
     */
    public static Integer getMonthByDate(Date date){
        if(date == null){
            throw new UtilsException("查询的日期不能为空");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.MONTH);
    }

    /**
     * 查询指定日期所在周数
     */
    public static Integer getWeekInYearByDate(Date date){
        if(date == null){
            throw new UtilsException("查询的日期不能为空");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 查询指定日期所在的星期
     */
    public static Integer getWeekInMonthByDate(Date date){
        if(date == null){
            throw new UtilsException("查询的日期不能为空");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.WEEK_OF_MONTH);
    }
}
