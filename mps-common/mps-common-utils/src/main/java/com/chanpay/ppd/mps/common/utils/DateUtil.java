package com.chanpay.ppd.mps.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/** 定义yyyyMMdd格式的日期字符串 */
	public static final String YYYY_MM_DD = "yyyyMMdd";

	/** 定义yyMMdd格式的日期字符串 */
	public static final String YY_MM_DD = "yyMMdd";

	/** 定义HH24mmss格式的时间字符串 */
	public static final String HH24_MM_SS = "HHmmss";

	/** 定义HH24:mm:ss格式日期字符串 */
	public static final String HH24_MM_SS_SEM = "HH:mm:ss";

	/** 定义yyyy-MM-dd HH24:mm:ss格式日期字符串 */
	public static final String YYYY_MM_DD_HH24_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/** 定义MMdd格式的日期字符串 */
	public static final String MM_DD = "MMdd";

	/** 定义yyyyMMddHHmmss格式的日期字符串 **/
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/** 定义yyyy-MM-dd HH24:mm:ss:SSS格式日期字符串 */
	public static final String YYYY_MM_DD_HH24_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss:SSS";

	/** 定义yyyyMMddHHmmssSSS格式日期字符串 */
	public static final String YYYYMMDDHH24MMSSSSS = "yyyyMMddHHmmssSSS";

	/**
	 * 将指定字符串转换成日期
	 * 
	 * @param dateString
	 *            String 日期字符串
	 * @param datePattern
	 *            String 日期格式
	 * @return Date
	 * @throws ParseException
	 */
	public static Date formatStringToDate(String dateString, String datePattern)
			throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
		return simpleDateFormat.parse(dateString);
	}

	/**
	 * 将指定日期对象转换成格式化字符串
	 * 
	 * @param date
	 *            Date XML日期对象
	 * @param datePattern
	 *            String 日期格式
	 * @return String
	 */
	public static String formatDateToString(Date date, String datePattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * @param date1
	 *            时间1
	 * @param date2
	 *            时间2
	 * @param minute
	 *            分钟数
	 * @return 如果date1比date2早minute分钟，则返回true;
	 */
	public static boolean compare(Date date1, Date date2, int minute) {
		return date1.getTime() - date2.getTime() > minute * 60 * 1000;
	}

	/**
	 * @param date1
	 *            时间1
	 * @param date2
	 *            时间2
	 * @return 如果date1比date2早，则返回true;
	 */
	public static boolean compare(Date date1, Date date2) {
		return date1.getTime() - date2.getTime() > 0;
	}

	/**
	 * 获取当前年份
	 * 
	 * @return 例如2014般的年份形式
	 */
	public static String getCurrentYearString() {
		Calendar cldCurrent = Calendar.getInstance();
		return String.valueOf(cldCurrent.get(Calendar.YEAR));
	}

	/**
	 * 获得当前的日期
	 * 
	 * @return 返回yyyy-MM-dd HH24:mm:ss格式的日期字符串
	 */
	public static String getCurrentDateString() {
		return new SimpleDateFormat(YYYY_MM_DD_HH24_MM_SS).format(new Date(
				System.currentTimeMillis()));
	}

	/**
	 * 获得当前的日期
	 * 
	 * @return 返回yyyyMMdd格式的日期字符串
	 */
	public static String getCurrentDayString() {
		return new SimpleDateFormat(YYYY_MM_DD).format(new Date(System
				.currentTimeMillis()));
	}

	/**
	 * 获得当前的时间
	 * 
	 * @return 返回HH24mmss格式的时间字符串
	 */
	public static String getCurrentTimeString() {
		return new SimpleDateFormat(HH24_MM_SS).format(new Date(System
				.currentTimeMillis()));
	}

	public static long getCurrentDateLong() {
		return Long.valueOf(formatDateToString(new Date(), YYYYMMDDHHMMSS));
	}

	/**
	 * 取系统时间戳的前12位
	 * 
	 * @return
	 */
	public static String getTimestampPrefix12() {
		return String.valueOf(System.currentTimeMillis()).substring(0, 12);
	}

	/**
	 * 
	 * <b>Description:</b>获取当前天的前几天<br/>
	 * <b>Author:</b>dongyinghui<br/>
	 * <b>CreateTime:</b>2015年4月29日下午5:06:39<br/>
	 * 
	 * @param date
	 * @return String
	 * @throws
	 * @since 0.1.0
	 */
	public static String formatDate(int date) {
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		Calendar cal = Calendar.getInstance();
		// 时间加减
		cal.add(Calendar.DAY_OF_YEAR, -date);
		return df.format(cal.getTime());
	}

	/**
	 * 
	 * <b>Description:</b>获取当前月第一天<br/>
	 * <b>Author:</b>dongyinghui<br/>
	 * <b>CreateTime:</b>2015年6月1日下午2:22:39<br/>
	 * 格式：yyyyMMdd
	 * 
	 * @return String
	 * @throws
	 * @since 0.1.0
	 */
	public static String getMonthFirstDate() {
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return format.format(c.getTime());
	}

	/**
	 * 
	 * <b>Description:</b>获取当前月最后一天<br/>
	 * <b>Author:</b>dongyinghui<br/>
	 * <b>CreateTime:</b>2015年6月1日下午2:25:01<br/>
	 * 格式：yyyyMMdd
	 * 
	 * @return String
	 * @throws
	 * @since 0.1.0
	 */
	public static String getMonthLastDate() {
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH,
				ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(ca.getTime());
	}

	/**
	 * 
	 * <b>Description:</b>获取前一天<br/>
	 * <b>Author:</b>dongyinghui<br/>
	 * <b>CreateTime:</b>2015年12月2日下午3:17:55<br/>
	 * 格式：yyyyMMdd
	 * 
	 * @return String
	 * @throws
	 * @since 1.0.0
	 */
	public static String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
		return format.format(cal.getTime());
	}

	/**
	 * 
	 * <b>Description:</b>获取下一天<br/>
	 * <b>Author:</b>dongyinghui<br/>
	 * <b>CreateTime:</b>2015年12月2日下午3:18:35<br/>
	 * 格式：yyyyMMdd
	 * 
	 * @return String
	 * @throws
	 * @since 1.0.0
	 */
	public static String getNextDay() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
		return format.format(cal.getTime());
	}

	/**
	 * 获得指定日期的前n天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getBeforeDay(String specifiedDay, int num)
			throws ParseException {
		Calendar c = Calendar.getInstance();
		Date date = new SimpleDateFormat(YYYY_MM_DD).parse(specifiedDay);
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - num);
		return new SimpleDateFormat(YYYY_MM_DD).format(c.getTime());
	}

	/**
	 * 获得指定日期的后n天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getAfterDay(String specifiedDay, int num)
			throws ParseException {
		Calendar c = Calendar.getInstance();
		Date date = new SimpleDateFormat(YYYY_MM_DD).parse(specifiedDay);
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + num);
		return new SimpleDateFormat(YYYY_MM_DD).format(c.getTime());
	}

	/**
	 * 
	 * <b>Description:</b>指定 minute分钟之前<br/>
	 * <b>Author:</b>dongyinghui<br/>
	 * <b>CreateTime:</b>2015年12月22日下午6:25:22<br/>
	 * 
	 * @param minute
	 * @return Date
	 * @throws
	 * @since 1.0.0
	 */
	public static Date getBeforeMinute(int minute) {
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		int time = c.get(Calendar.MINUTE);
		c.set(Calendar.MINUTE, time - minute);
		c.getTime();
		return c.getTime();
	}

	public static void main(String[] args) {
		Date now = new Date();
		System.out.println(formatDateToString(now, YYYYMMDDHHMMSS));
		System.out.println(formatDateToString(now, YYYY_MM_DD));
		System.out.println(formatDateToString(now, HH24_MM_SS));
		System.out.println(formatDateToString(now, YY_MM_DD));
		System.out.println(getCurrentDayString());
		System.out.println(getMonthFirstDate());
		System.out.println(getYesterday());
		System.out.println(getNextDay());
	}
}
