package com.sf.gis.cds.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：
 * <p>
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2016年8月31日      01174604         Create
 * ****************************************************************************
 * </pre>
 *
 * @author 01174604
 * @since 1.0
 */
public class DateUtil {
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 默认日期格式:yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";// Timestamp
	// format
	// must
	// be
	// yyyy-mm-dd
	// hh:mm:ss[.fffffffff]
	/** yyyy-MM-dd HH:mm:ss */
	public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**UTC时间格式:yyyy-MM-dd HH:mm:ss Z*/
	public static final String UTC_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss Z";

	/**
	 * 精确到天的日期格式:yyyy-MM-dd
	 */
	public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 精确到天的日期格式(八位):yyyyMMdd
	 */
	public static final String SHORT_DATE_FORMAT_EIGHT = "yyyyMMdd";

	/**
	 * 精确到天的日期格式:yyyy-MM-dd
	 */
	public static final String SHORT_DATE_FORMAT_E = "yyyy-MM-dd";

	/**
	 * 精确到分的日期格式:yyyy-MM-dd HH:mm
	 */
	public static final String MEDIUM_DATE_FORMAT = "yyyy-MM-dd HH:mm";

	/**
	 * 带斜线格式,精确到天:yyyy/MM/dd
	 */
	public static final String SHORT_DATE_FORMAT_SLASH_SHORT = "yyyy/MM/dd";

	/**
	 * 带斜线格式,精确到秒:yyyy/MM/dd hh:mm:ss
	 */
	public static final String SHORT_DATE_FORMAT_SLASH = "yyyy/MM/dd hh:mm:ss";

	/**
	 * 默认构造函数
	 */
	private DateUtil() {
	}

	public static long getNowTS() {
		return new Date().getTime() / 1000;
	}

	/**
	 * get date
	 *
	 * @param day
	 *            -xxx ~ xxx
	 * @return target date ... 1 = tomorrow 0 = now date -1 = yesterday -2 = the
	 *         day before yesterday ...
	 */
	public static Date getDate(int day) {
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.DATE, day);
		Date dat = cd.getTime();
		return dat;
	}

	public static long getDateTS(int day) {
		return date2Timestamp(getDate(day));
	}

	public static Date getDateZero(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date dat = calendar.getTime();
		return dat;
	}

	public static long getDateZeroTS(int day) {
		return date2Timestamp(getDateZero(day));
	}

	/**
	 * date format to unix timestamp
	 * 
	 * @param date
	 * @return
	 */
	public static long date2Timestamp(Date date) {
		return date.getTime() / 1000;
	}

	/**
	 * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
	 *
	 * @param str
	 *            字符串
	 * @param format
	 *            日期格式
	 * @return 日期
	 * @throws ParseException
	 */
	public static Date str2Date(String str, String format) {
		if (null == str || "".equals(str)) {
			return null;
		}
		// 如果没有指定字符串转换的格式，则用默认格式进行转换
		if (null == format || "".equals(format)) {
			format = DEFAULT_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(str);
			return date;
		} catch (ParseException e) {
			logger.error(e.toString(), e);
		}
		return null;
	}

	/**
	 * 日期转换为字符串
	 *
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(Date date, String format) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 时间戳转换为字符串
	 *
	 * @param time
	 * @return
	 */
	public static String timestamp2Str(Timestamp time) {
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
		}
		return date2Str(date, DEFAULT_FORMAT);
	}

	/**
	 * 字符串转换时间戳
	 *
	 * @param str
	 * @return
	 */
	public static Timestamp str2Timestamp(String str) {
		Date date = str2Date(str, DEFAULT_FORMAT);
		return new Timestamp(date.getTime());
	}

	/**
	 * 获取两个时间相差的天数，不足一天补一天。
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getCeilDays(Date startDate, Date endDate) {
		if (null == startDate || null == endDate) {
			return 0;
		}
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();

		return (int) Math.ceil((endTime - startTime) / (1000f * 60 * 60 * 24));
	}

	/**
	 * 获取两个时间相差的分钟数，不足一分钟补一分钟。
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer getCeilMinutes(Date startDate, Date endDate) {
		if (null == startDate || null == endDate) {
			return null;
		}
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();

		return (int) Math.ceil((endTime - startTime) / (1000f * 60));
	}

	/**
	 * 根据日期和偏移数计算新的日期。<br/>
	 * days > 0,日期加；days < 0,日期减。
	 *
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getDateOfDay(Date date, int days) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);

		return calendar.getTime();
	}

	/**
	 * 根据日期、偏移字段、偏移数计算新的日期。<br/>
	 * days > 0,日期加；days < 0,日期减。
	 *
	 * @param date
	 * @param field
	 *            如：Calendar.DAY_OF_YEAR
	 * @param amount
	 * @return
	 */
	public static Date getDateOfTime(Date date, int field, int amount) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);

		return calendar.getTime();
	}

	/**
	 * 返回一个日期当天最晚的时间，精确至23:59:59。<br/>
	 * 如2012-11-07 10:23 返回 2012-11-07 23:59:59。
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastTimeOfDate(Date date) {
		return str2Date(date2Str(date, SHORT_DATE_FORMAT) + " 23:59:59",
				NORMAL_DATE_FORMAT);
	}

	/**
	 * 返回一个日期 00:00:00 <br/>
	 * 如2012-11-07 10:23 返回 2012-11-07 00:00:00。
	 *
	 * @param date
	 * @return
	 */
	public static Date get0TimeOfDate(Date date) {
		return str2Date(date2Str(date, SHORT_DATE_FORMAT) + " 00:00:00",
				NORMAL_DATE_FORMAT);
	}

	/**
	 * 返回一个日期 24:00:00 <br/>
	 * 如2012-11-07 10:23 返回 2012-11-07 24:00:00。
	 *
	 * @param date
	 * @return
	 */
	public static Date get24TimeOfDate(Date date) {
		return str2Date(date2Str(date, SHORT_DATE_FORMAT) + " 24:00:00",
				NORMAL_DATE_FORMAT);
	}

	/**
	 * 获取精确到天的日期<br/>
	 * 如2012-11-07 10:23 返回2012-11-07
	 *
	 * @param date
	 * @return
	 */
	public static Date truncDate(Date date) {
		if (null == date) {
			return null;
		}
		return str2Date(date2Str(date, SHORT_DATE_FORMAT), SHORT_DATE_FORMAT);
	}

	/**
	 * 获取当前日期下一天精确到天的日期<br/>
	 * 如2012-11-07 10:23 返回2012-11-08
	 *
	 * @param date
	 * @return
	 */
	public static Date ceilDate(Date date) {
		if (null == date) {
			return null;
		}
		return getDateOfDay(truncDate(date), 1);
	}

	public static int converSecondsToDays(int seconds) {
		if (seconds <= 0) {
			return 0;
		}

		return seconds / 60 / 60 / 24;
	}

	/**
	 * 比较两个时间是否是同一天
	 *
	 * @param date
	 * @return
	 */
	public static boolean isSameDay(Date date, Date otherDate) {
		if (null == date || null == otherDate) {
			return false;
		}
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(date);
		calendar2.setTime(otherDate);
		return calendar1.get(Calendar.DAY_OF_YEAR) == calendar2
				.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 预先格式化成UTC的时间字符格式
	 *
	 * @param timeStr
	 * @return
	 */
	public static String filterUtcTime(String timeStr) {
		if (timeStr == null) {
			return null;
		}
		Pattern p = Pattern
				.compile("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z)");
		Matcher m = p.matcher(timeStr);
		StringBuffer sb = new StringBuffer();

		boolean result = m.find();
		while (result) {
			String key = m.group().replace("T", " ").replace("Z", " utc");
			m.appendReplacement(sb, key);
			result = m.find();
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 获取日期中的某数值。如获取月份
	 *
	 * @param date
	 *            日期
	 * @param dateType
	 *            日期格式
	 * @return 数值
	 */
	private static int getInteger(Date date, int dateType) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(dateType);
	}

	/**
	 * 获取日期的年份。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 年份
	 */
	public static int getYear(Date date) {
		return getInteger(date, Calendar.YEAR);
	}

	/**
	 * 获取日期的月份。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 月份
	 */
	public static int getMonth(Date date) {
		return getInteger(date, Calendar.MONTH);
	}

	/**
	 * 获取日期的天数。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 天
	 */
	public static int getDay(Date date) {
		return getInteger(date, Calendar.DATE);
	}

	/**
	 * 获取日期的小时。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 小时
	 */
	public static int getHour(Date date) {
		return getInteger(date, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取日期的分钟。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 分钟
	 */
	public static int getMinute(Date date) {
		return getInteger(date, Calendar.MINUTE);
	}

	/**
	 * 获取日期的秒钟。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 秒钟
	 */
	public static int getSecond(Date date) {
		return getInteger(date, Calendar.SECOND);
	}

	/**
	 * 获取当前日期上一天精确到天的日期<br/>
	 * 如2012-11-07 10:23 返回2012-11-06
	 *
	 * @param date
	 * @return
	 */
	public static Date floorDate(Date date) {
		if (null == date) {
			return null;
		}
		return getDateOfDay(truncDate(date), -1);
	}

	/**
	 * 月初判断
	 *
	 * @param date
	 * @return boolean
	 */
	public static boolean isMonthFirstDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (c.get(Calendar.DAY_OF_MONTH) != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 月末判断
	 *
	 * @param date
	 * @return boolean
	 */
	public static boolean isMonthLastDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, 1);
		if (c.get(Calendar.DAY_OF_MONTH) != 1) {
			return false;
		}
		return true;
	}

	public static String timestamp2Str(Timestamp timestamp, String format) {
		Date date = null;
		if (null != timestamp) {
			date = new Date(timestamp.getTime());
		}
		return date2Str(date, format);
	}

	public static Date parse(String beginTime, String shortDateFormat) {
		return str2Date(beginTime, shortDateFormat);
	}

}
