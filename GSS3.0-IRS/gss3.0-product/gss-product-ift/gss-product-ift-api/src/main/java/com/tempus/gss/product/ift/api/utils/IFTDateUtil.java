/**
 * IFTDateUtil.java
 * com.tempus.gss.dps.util
 *
 * Function： 日期工具类 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2016年11月14日 		liuyu
 *
 * Copyright (c) 2016, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ift.api.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName:IFTDateUtil Function: 日期工具类 Reason:
 *
 * @author liuyu
 * @version
 * @since Ver 1.1
 * @Date 2016年11月14日 下午7:55:26
 * @see
 */
public abstract class IFTDateUtil {

	/** 最大日期: 2999-12-31 */
	public static final String MAX_DATE = "2999-12-31";

	/** 星期字典, 默认从1开始即代表星期一, 0结束即代表星期天 */
	public static final int WEEKS_DIGITS[] = { 0, 1, 2, 3, 4, 5, 6 };

	/** 模板: yyyy-MM-dd */
	public static final String DATE = "yyyy-MM-dd";

	/** 模板: yyyy-MM-dd HH:mm:ss */
	public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

	/** 模板: yyyyMMddHHmmss */
	public static final String DATE_TIME_SHORT = "yyyyMMddHHmmss";

	/** 模板: yyyy-MM-dd HH:mm:ss.SSS */
	public static final String DATE_TIME_STAMP = "yyyy-MM-dd HH:mm:ss.SSS";

	/** 模板: MM-dd */
	public static final String DATE_SHORT = "MM-dd";

	/** 模板: HH:mm:ss */
	public static final String TIME = "HH:mm:ss";

	/** 模板: HH:mm */
	public static final String TIME_SHORT = "HH:mm";

	/** 模板: HH:mm:ss.SSS */
	public static final String TIME_STAMP = "HH:mm:ss.SSS";

	/** 模板: HHmmss */
	public static final String SHORT_TIME = "HHmmss";

	/** 起飞、到达 模板: yyyy-MM-dd HH:mm */
	public static final String FLY_DATE_TIME = "yyyy-MM-dd HH:mm";

	/**
	 * 受保护的构造方法, 防止外部构建对象实例.
	 */
	protected IFTDateUtil() {
		super();
	}

	/**
	 * 当前时间.
	 * 
	 * @return Date 时间实例
	 */
	public static Date date() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 根据毫秒时间转成时间.
	 * 
	 * @param millis 时间毫秒数
	 * @return Date 时间实例
	 */
	public static Date date(long millis) {
		return new Date(millis);
	}

	/**
	 * 当前毫秒时间.
	 * 
	 * @return long 毫秒时间
	 */
	public static long millis() {
		return System.currentTimeMillis();
	}

	/**
	 * 当前时间戳.
	 * 
	 * @return Timestamp 时间戳
	 */
	public static Timestamp timestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 按模板格式化时间.
	 * 
	 * @param millis 毫秒时间
	 * @param FLY_DATE_PATTERN 模板
	 * @return String 格式后字符串
	 */
	public static String format(long millis) {
		return format(new Date(millis));
	}

	/**
	 * 按模板格式化时间.
	 * 
	 * @param date 时间
	 * @param DATE_TIME_STAMP 模板
	 * @return String 格式后字符串
	 */
	public static String format(Date date) {
		DateFormat format = new SimpleDateFormat(DATE_TIME_STAMP);
		return format.format(date);
	}

	/**
	 * 按模板格式化时间.
	 * 
	 * @param millis 毫秒时间
	 * @param pattern 模板
	 * @return String 格式后字符串
	 */
	public static String format(long millis, String pattern) {
		return format(new Date(millis), pattern);
	}

	/**
	 * 按模板格式化时间.
	 * 
	 * @param date 时间
	 * @param pattern 模板
	 * @return String 格式后字符串
	 */
	public static String format(Date date, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * <pre>
	 * 将字符串使用对应的时间格式转换为时间实例,
	 * 例如字符串为 2000-01-01 01:01:01,时间格式为 yyyy-MM-dd HH:mm:ss.SSS,则返回对应的时间实例,
	 * 如果 null==str或者 0==str() 或者 null==format 或者 0==format.length(),则返回 null.
	 * </pre>
	 * 
	 * @param str 字符串
	 * @param pattern 时间格式
	 * @return Date 时间实例
	 */
	public static Date parse(String str, String pattern) {
		if (null == pattern || pattern.length() == 0 || null == str || str.length() == 0) {
			return null;
		}

		DateFormat df = new SimpleDateFormat(pattern);
		df.setLenient(false);
		try {
			return df.parse(str);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <pre>
	 * 将字符串使用对应的时间格式化模板实例转换为时间实例,
	 * 例如字符串为 2000-01-01 01:01:01,时间格式为 yyyy-MM-dd HH:mm:ss.SSS,则返回对应的时间实例,
	 * 如果 null==str或者 0==str() 或者 df==null,则返回 null.
	 * </pre>
	 * 
	 * @param str 字符串
	 * @param df 时间格式化模板实例
	 * @return Date 时间实例
	 */
	public static Date parse(String str, DateFormat df) {
		if (null == df || null == str || str.length() == 0) {
			return null;
		}

		try {
			return df.parse(str);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 一天的开始时间
	 * 
	 * @param millis 时间毫秒数
	 * @return long 如:2017-01-01 00:00:00:000
	 */
	public static long startTime(final long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);

		Calendar _calendar = makeTime(calendar, true);
		return _calendar.getTimeInMillis();
	}

	/**
	 * 一天的开始时间
	 * 
	 * @param date 时间
	 * @return Date 如:2017-01-01 00:00:00:000
	 */
	public static Date startTime(final Date date) {
		if (null == date) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Calendar _calendar = makeTime(calendar, true);
		return _calendar.getTime();
	}

	/**
	 * 一天的开始时间
	 * 
	 * @param calendar 日历时间
	 * @return Calendar 如:2017-01-01 00:00:00:000
	 */
	public static Calendar startTime(final Calendar calendar) {
		return makeTime(calendar, true);
	}

	/**
	 * 一天的结束时间
	 * 
	 * @param millis 时间毫秒数
	 * @return long 如:2017-01-01 23:59:59:999
	 */
	public static long endTime(final long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);

		Calendar _calendar = makeTime(calendar, false);
		return _calendar.getTimeInMillis();
	}

	/**
	 * 一天的结束时间
	 * 
	 * @param date 时间
	 * @return Date 如:2017-01-01 23:59:59:999
	 */
	public static Date endTime(final Date date) {
		if (null == date) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Calendar _calendar = makeTime(calendar, false);
		return _calendar.getTime();
	}

	/**
	 * 一天的结束时间
	 * 
	 * @param calendar 日历时间
	 * @return Calendar 如:2017-01-01 23:59:59:999
	 */
	public static Calendar endTime(final Calendar calendar) {
		return makeTime(calendar, false);
	}

	/**
	 * 生成一天的开始或结束时间
	 * 
	 * @param calendar
	 * @param isStart true:一天的开始时间; false:一天的结束时间
	 * @return Calendar 如:2017-01-01 00:00:00:000 或者 2017-01-01 23:59:59:999
	 */
	public static Calendar makeTime(final Calendar calendar, boolean isStart) {
		if (null == calendar) {
			return null;
		}

		// 一天的开始时间
		if (isStart) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		}
		// 一天的结束时间
		else {
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
		}

		return calendar;
	}

	/**
	 * 计算两个日期相差天数，仅计算精确到“日”的部分
	 * 
	 * @param from 起始时间
	 * @param to 结束时间
	 * @return
	 */
	public static int dateDiffInDay(Date from, Date to) {
		long differ = 0;
		from = DateUtils.truncate(from, Calendar.DATE);
		to = DateUtils.truncate(to, Calendar.DATE);
		long time1 = from.getTime();
		long time2 = to.getTime();
		differ = time2 - time1;
		return (int) (differ / (1000 * 60 * 60 * 24));
	}

	public static void main(String[] args) {
		@SuppressWarnings("deprecation")
		Date to1 = new Date(117, 0, 1); // 2017-01-01
		Date date = DateUtils.truncate(new Date(), Calendar.DATE);
		Date to2 = DateUtils.addDays(date, 1);
		System.out.println(IFTDateUtil.dateDiffInDay(date, to1));
		System.out.println(IFTDateUtil.dateDiffInDay(date, to2));
	}
}
