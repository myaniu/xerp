package com.google.code.insect.workflow.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Calendar;

public class DateTimeUtil {

	/**
	 * 查询当前日期相隔天数的日期
	 * 
	 * @param days
	 *            int
	 * @return String
	 */
	public static String getBeforDay(int days, String patten) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -days);
		return getDateTime(cal.getTime(), patten);
	}

	/**
	 * 查询当前日期相隔指定月的日期
	 * 
	 * @param days
	 *            int
	 * @return String
	 */
	public static String getBeforMonth(int months, String patten) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -months);
		return getDateTime(cal.getTime(), patten);
	}

	/**
	 * 查询当前日期相隔指定年的日期
	 * 
	 * @param days
	 *            int
	 * @return String
	 */
	public static String getBeforYear(int yeas, String patten) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -yeas);
		return getDateTime(cal.getTime(), patten);
	}

	/**
	 * 返回当前时间的字符串值
	 * 
	 * @param patten
	 *            String 如："yyyy-MM-dd HH:mm:ss"
	 * @return String
	 */
	public static String getDateTime(String patten) {
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		String dt = sdf.format(new Date());
		return dt;
	}

	/**
	 * 返回指定时间的指定格式的字符串值
	 * 
	 * @param date
	 *            Date
	 * @param patten
	 *            String 如："yyyy-MM-dd HH:mm:ss"
	 * @return String
	 */
	public static String getDateTime(Date date, String patten) {
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		return sdf.format(date);
	}

	/**
	 * 将指定字符串转换成日期格式
	 * 
	 * @param String
	 *            param, 格式: "yyyy-MM-dd HH:mm:ss".
	 * @return Date
	 */
	public static Date parseDateTime(String param, String patten) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		try {
			date = sdf.parse(param);
		} catch (ParseException ex) {
			ex.printStackTrace();
			// System.out.println(ex);
		}
		return date;
	}

	/**
	 * 将指定字符串转换成数据库日期格式
	 * 
	 * @param param
	 *            String
	 * @param patten
	 *            String 日期格式，如：yyyy-MM-dd
	 * @return Date
	 */
	public static java.sql.Date parseSQLDateTime(String param, String patten) {
		java.sql.Date sdf = null;
		SimpleDateFormat format = new SimpleDateFormat(patten);
		try {
			if (param != null) {
				java.util.Date dd = format.parse(param);
				sdf = new java.sql.Date(dd.getTime());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// System.out.println(ex);
		}
		return sdf;
	}

	/**
	 * 将指定字符串转换成日期格式
	 * 
	 * @param param
	 *            String
	 * @param patten
	 *            String 日期格式
	 * @return Date
	 */
	public static Date parseDate(String param, String patten) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		try {
			date = sdf.parse(param);
		} catch (ParseException ex) {
			ex.printStackTrace();
			// System.out.println(ex);
		}
		return date;
	}

	/**
	 * 判断输入的日期是否正确
	 * 
	 * @param param
	 *            String
	 * @param patten
	 *            String 格式
	 * @return boolean
	 */
	public static boolean isValidDate(String param, String patten) {
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		try {
			sdf.parse(param);
		} catch (ParseException ex) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是星期几
	 * 
	 * @param date
	 *            Date
	 * @param patten
	 *            String
	 * @return int
	 */
	public static int isWeekDay(java.sql.Date date, String patten) {
		int result = -1;
		java.util.Date d = parseDate(date.toString(), patten);
		SimpleDateFormat dd = new SimpleDateFormat("E");
		String str = dd.format(d);
		String tmpstr = str.substring(2, str.length());
		if (tmpstr.equals("一") || str.equals("Mon")) {
			result = 1;
		} else if (tmpstr.equals("二") || str.equals("Tue")) {
			result = 2;
		} else if (tmpstr.equals("三") || str.equals("Wed")) {
			result = 3;
		} else if (tmpstr.equals("四") || str.equals("Thu")) {
			result = 4;
		} else if (tmpstr.equals("五") || str.equals("Fri")) {
			result = 5;
		} else if (tmpstr.equals("六") || str.equals("Sat")) {
			result = 6;
		} else if (tmpstr.equals("日") || str.equals("Sun")) {
			result = 7;
		}
		return result;
	}

	/**
	 * 判断是星期几
	 * 
	 */

	public static String getWeekDay(String date, String patten) {
		String result = "";
		java.util.Date d = parseDate(date, patten);
		SimpleDateFormat dd = new SimpleDateFormat("E");
		result = dd.format(d);
		if (result.equals("Mon")) {
			result = "星期一";
		} else if (result.equals("Tue")) {
			result = "星期二";
		} else if (result.equals("Wed")) {
			result = "星期三";
		} else if (result.equals("Thu")) {
			result = "星期四";
		} else if (result.equals("Fri")) {
			result = "星期五";
		} else if (result.equals("Sat")) {
			result = "星期六";
		} else if (result.equals("Sun")) {
			result = "星期日";
		}
		return result;
	}

	/**
	 * 获取当前年所有月份的天数
	 * 
	 * @param d
	 *            Date
	 * @return int
	 */
	public static int[] getMothDay(java.util.Date d) {
		int[] result = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		String str = DateTimeUtil.getDateTime(d, "yyyy");
		int year = Integer.parseInt(str.substring(0, 4));
		if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
			result[1] = 29;
		}
		return result;

	}

	/**
	 * 获取当前周的第一天
	 * 
	 * @return Date
	 */
	public static Date firstDayOfThisWeek() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, cal.getMinimum(Calendar.DAY_OF_WEEK));
		return cal.getTime();
	}

	/**
	 * 获取当前周最后一天
	 * 
	 * @return Date
	 */
	public static Date endDayOfThisWeek() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_MONTH, cal.get(Calendar.WEEK_OF_MONTH) + 1);
		cal.set(Calendar.DAY_OF_WEEK, cal.getMinimum(Calendar.DAY_OF_WEEK));
		cal.add(Calendar.DAY_OF_WEEK, -1);
		return cal.getTime();
	}

	/**
	 * 返回本月的第一天
	 * 
	 * @return
	 */
	public static Date firstDayOfThisMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * 返回本月的最后一天
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date getLastDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		// 年
		cal.set(Calendar.YEAR, date.getYear() + 1900);
		// 月，因为Calendar里的月是从0开始，所以要-1
		cal.set(Calendar.MONTH, date.getMonth());// date.getMonth()默认值少1
		// 日，设为一号
		cal.set(Calendar.DATE, 1);
		// 月份加一，得到下个月的一号
		cal.add(Calendar.MONTH, 1);
		// 下一个月减一为本月最后一天
		cal.add(Calendar.DATE, -1);
		// return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));//获得月末是几号
		return cal.getTime();
	}

	public static void main(String args[]) {
		String firstDateOfMonth = DateTimeUtil.getDateTime(DateTimeUtil
				.firstDayOfThisMonth(), "yyyy-MM-dd");
		String lastDateOfMonth = DateTimeUtil.getDateTime(DateTimeUtil
				.getLastDayOfMonth(), "yyyy-MM-dd");
		System.out.println("firstDateOfMonth:" + firstDateOfMonth);
		System.out.println("lastDateOfMonth:" + lastDateOfMonth);
	}

}
