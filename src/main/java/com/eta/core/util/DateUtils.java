package com.eta.core.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 *
 */
@Slf4j
public abstract class DateUtils {

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_ZHcn = "yyyy年MM月dd日HH时";

    public static final String DAY_BEGIN = "00:00:00";

    public static final String DAY_END = "23:59:59";

    public static final int ONE_DAY_SECOND = 24 * 60 * 60;

    public static final long ONE_DAY_MILLISECOND = 1000L * ONE_DAY_SECOND;


    /**
     * 获取指定日期的开始日期，如yyyy-MM-dd 00:00:00
     *
     * @param date 指定的日期
     * @return yyyy-MM-dd 00:00:00
     */
    public static String getDateBegin(Date date) {
        if (date == null) {
            return null;
        }
        return getDateBegin(format(date, YYYY_MM_DD));
    }

    /**
     * 获取指定日期的开始日期，如yyyy-MM-dd 00:00:00
     *
     * @param date 指定的日期
     * @return yyyy-MM-dd 00:00:00
     */
    public static String getDateBegin(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return date + " " + DAY_BEGIN;
    }

    /**
     * 获取指定日期的开始日期，如yyyy-MM-dd 23:59:59
     *
     * @param date 指定的日期
     * @return yyyy-MM-dd 23:59:59
     */
    public static String getDateEnd(Date date) {
        if (date == null) {
            return null;
        }
        return getDateEnd(format(date, YYYY_MM_DD));
    }

    /**
     * 获取指定日期的开始日期，如yyyy-MM-dd 23:59:59
     *
     * @param date 指定的日期
     * @return yyyy-MM-dd 23:59:59
     */
    public static String getDateEnd(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return date + " " + DAY_END;
    }

    /**
     * 格式化指定的日期
     *
     * @param date    指定的日期
     * @param pattern 格式
     * @return 格式化后的日期
     */
    public static String format(Date date, String pattern) {
        if (date == null || StringUtils.isBlank(pattern)) {
            return null;
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * @param pattern 格式
     * @return 格式化当前的日期
     */
    public static String format(String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return null;
        }
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * 转化为日期对象，参数单位毫秒
     *
     * @param time 毫秒
     * @return 日期对象
     */
    public static Date toDate(long time) {
        Date result = new Date();
        result.setTime(time);
        return result;
    }

    /**
     * 检验所给的日期是否匹配所给的格式
     *
     * @param date    日期对象
     * @param pattern 格式
     * @return 若是有效的日期则返回true
     */
    public static boolean isValidDate(String date, String pattern) {
        boolean result = true;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            format.setLenient(false);
            format.parse(date);
        }
        catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * 转化字符串为日期
     *
     * @param date    日期字符串
     * @param pattern 格式
     * @return 日期对象
     */
    public static Date parse(String date, String pattern) {
        if (StringUtils.isBlank(date) || StringUtils.isBlank(pattern)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date);
        }
        catch (ParseException e) {
            log.error("日期格式有误date=%s，pattern=%s", date, pattern);
            return null;
        }
    }

    /**
     * 两个日期相减
     *
     * @param minuend 被减数
     * @param meiosis 减数
     * @param pattern 日期格式
     * @return 毫秒数
     */
    public static Long getSubtractMillisecond(String minuend, String meiosis, String pattern) {
        if (StringUtils.isBlank(minuend) || StringUtils.isBlank(meiosis) || StringUtils.isBlank(pattern)) {
            return null;
        }
        Date minuendDate = parse(minuend, pattern);
        Date meiosisDate = parse(meiosis, pattern);
        return getSubtractMillisecond(minuendDate, meiosisDate);
    }

    /**
     * 两个日期相减
     *
     * @param minuend 被减数
     * @param meiosis 减数
     * @return 毫秒数
     */
    public static Long getSubtractMillisecond(Date minuend, Date meiosis) {
        if (minuend == null || meiosis == null) {
            return null;
        }
        return minuend.getTime() - meiosis.getTime();
    }

    /**
     * 日期减去毫秒数
     *
     * @param date        日期
     * @param millisecond 毫秒数
     * @return 相减后的日期
     */
    public static Date subtractMillisecond(Date date, long millisecond) {
        if (millisecond == 0) {
            return date;
        }
        return toDate(date.getTime() - millisecond);
    }

    /**
     * 两个日期相减的月数
     *
     * @param minuend 被减数
     * @param meiosis 减数
     * @param pattern 日期格式
     * @return 月份
     */
    public static Integer getSubtractMonth(String minuend, String meiosis, String pattern) {
        if (StringUtils.isBlank(minuend) || StringUtils.isBlank(meiosis) || StringUtils.isBlank(pattern)) {
            return null;
        }
        return getSubtractMonth(DateUtils.parse(minuend, pattern), DateUtils.parse(meiosis, pattern));
    }

    /**
     * 两个日期相减的月数
     *
     * @param minuend 被减数
     * @param meiosis 减数
     * @return 月份
     */
    public static Integer getSubtractMonth(Date minuend, Date meiosis) {
        if (minuend == null || meiosis == null) {
            return null;
        }
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(meiosis);
        c2.setTime(minuend);
        return (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12 + (c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH));
    }

    /**
     * 给指定的日期增加毫秒数
     *
     * @param date        日期
     * @param millisecond 毫秒数
     * @return 增加毫秒数后的日期
     */
    public static Date addMillisecond(Date date, long millisecond) {
        if (date == null) {
            return null;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(date.getTime() + millisecond);
        return calendar.getTime();
    }

    /**
     * 给指定的日期增加日
     *
     * @param date 日期
     * @param day  天数
     * @return 增加天数后的日期
     */
    public static Date addDay(Date date, int day) {
        if (date == null) {
            return null;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 给指定的日期增加月
     *
     * @param date  日期
     * @param month 月数
     * @return 增加月数后的日期
     */
    public static Date addMonth(Date date, int month) {
        if (date == null) {
            return null;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 计算两个日期相差的天数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 天数
     */
    public static Long betweenDay(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null) {
            return null;
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant beginInstant = beginDate.toInstant();
        Instant endInstant = endDate.toInstant();
        LocalDate beginLocal = beginInstant.atZone(zone).toLocalDate();
        LocalDate endLocal = endInstant.atZone(zone).toLocalDate();
        return endLocal.toEpochDay() - beginLocal.toEpochDay();
    }

    /**
     * @return 今天还剩多少秒
     */
    public static int getTodayLeftSeconds() {
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), tomorrow);
    }

    /**
     * 对日期进行加减操作
     *
     * @param date       要进行加减天数的日期
     * @param addOrMinus 对日期加减天数（eg：加一天：1 减一天：-1）
     * @return
     * @throws ParseException
     */
    public static Date dateAddOrMinus(Date date, Integer addOrMinus) {
        if (addOrMinus == null || "".equals(addOrMinus)) {
            addOrMinus = 0;
        }
        //使用默认时区和语言环境获得一个日历
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, +addOrMinus);

        return cal.getTime();
    }
}
