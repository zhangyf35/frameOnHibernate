package org.webframe.tools.systemUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日历日期工具类
 * ,提供常用的获取日期、星期、周数以及转换操作
 * @author 张永葑
 */
public class CalendarUtil {
	
	private static int WEEKS = 0;
	private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat SDFT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取当前年份
	 * @return String 例如：2014
	 */
	public static String getYear() {
		Calendar cd = Calendar.getInstance(); 
		return String.valueOf(cd.get(Calendar.YEAR));
	}
	
	/**
	 * 获取当前月份
	 * @return String 例如：4
	 */
	public static String getMonth() {
		Calendar cd = Calendar.getInstance(); 
		return String.valueOf(cd.get(Calendar.MONTH) + 1);
	}
	
	/**
	 * 获取当前日期
	 * @return String 例如：23
	 */
	public static String getDay() {
		Calendar cd = Calendar.getInstance(); 
		return String.valueOf(cd.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * 获取某个日期中的年份
	 * @return String 例如：2014-05-16 返回 2014
	 */
	public static String getYearOfDate(String date) {
		Calendar cd = Calendar.getInstance();
		try {
			cd.setTime(SDF.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(cd.get(Calendar.YEAR));
	}

	/**
	 * 获取某个日期中的月份
	 * @return String 例如：2014-05-16 返回 5
	 */
	public static String getMonthOfDate(String date) {
		Calendar cd = Calendar.getInstance();
		try {
			cd.setTime(SDF.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(cd.get(Calendar.MONTH) + 1);
	}
	
	/**
	 * 获取日期中的英文月份缩写
	 * @return String
	 */
	public static String getMonthShortEnNameOfDate(String date) {
		Calendar cd = Calendar.getInstance();
		String result = "";
		try {
			cd.setTime(SDF.parse(date));
			int month = cd.get(Calendar.MONTH) + 1;
			switch (month) {
			case 1:
				result = "JAN";
				break;
			case 2:
				result = "FEB";
				break;
			case 3:
				result = "MAR";
				break;
			case 4:
				result = "APR";
				break;
			case 5:
				result = "MAY";
				break;
			case 6:
				result = "JUN";
				break;
			case 7:
				result = "JUL";
				break;
			case 8:
				result = "AUG";
				break;
			case 9:
				result = "SEP";
				break;
			case 10:
				result = "OCT";
				break;
			case 11:
				result = "NOV";
				break;
			case 12:
				result = "DEC";
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取某个日期中的日期
	 * @return String 例如：2014-05-16 返回 16
	 */
	public static String getDayOfDate(String date) {
		Calendar cd = Calendar.getInstance();
		try {
			cd.setTime(SDF.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(cd.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 获取昨天日期
	 * @return yyyy-MM-dd
	 */
	public static String getYesterday() {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(SDF.parse(getToday()));  
			cal.add(Calendar.DAY_OF_YEAR, -1);  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SDF.format(cal.getTime());		
	}

	/**
	 * 获取今天日期
	 * @return yyyy-MM-dd
	 */
	public static String getToday() {
		return SDF.format(new Date());
	}

	/**
	 * 获取明天日期
	 * @return yyyy-MM-dd
	 */
	public static String getTommorow() {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(SDF.parse(getToday()));  
			cal.add(Calendar.DAY_OF_YEAR, +1);  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SDF.format(cal.getTime());		
	}

	/**
	 * 获取当前日期和时间
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTime() {
		return SDFT.format(new Date());
	}

	/**
	 * 获得当前日期与本周一相差的天数
	 * @return int
	 */
	private static int getMondayPlus() {  
		Calendar cd = Calendar.getInstance();  
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......  
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);  
		if (dayOfWeek == 1) {  
			return -6;  
		} else {  
			return 2 - dayOfWeek;  
		}  
	}

	/**
	 * 获得上周星期一的日期
	 * @return yyyy-MM-dd
	 */
	public static String getPreMonday() {  
		WEEKS--;  
		int mondayPlus = getMondayPlus();  
		GregorianCalendar currentDate = new GregorianCalendar();  
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * WEEKS);  
		Date monday = currentDate.getTime();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(monday);
	}  

	/**
	 * 获得本周星期一的日期
	 * @return yyyy-MM-dd
	 */
	public static String getMonday() {  
		WEEKS = 0;
		int mondayPlus = getMondayPlus();  
		GregorianCalendar currentDate = new GregorianCalendar();  
		currentDate.add(GregorianCalendar.DATE, mondayPlus);  
		Date monday = currentDate.getTime();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(monday);
	}  

	/**
	 * 获得下周星期一的日期
	 * @return yyyy-MM-dd
	 */
	public static String getNextMonday() {  
		WEEKS++;  
		int mondayPlus = getMondayPlus();  
		GregorianCalendar currentDate = new GregorianCalendar();  
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * WEEKS);  
		Date monday = currentDate.getTime();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(monday);
	}
	
	/**
	 * 获取某一年第几周的星期一
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfWeek(int year, int week) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR,week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return SDF.format(cal.getTime());
	}
	
	/**
	 * 获取某一年第几周的星期日
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfWeek(int year, int week) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR,week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return SDF.format(cal.getTime());
	}

	/**
	 * 获取指定周星期日的日期
	 * @return yyyy-MM-dd
	 */
	public static String getSunday() {
		int mondayPlus = getMondayPlus();  
		GregorianCalendar currentDate = new GregorianCalendar();  
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * WEEKS + 6);  
		Date monday = currentDate.getTime();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(monday);
	}

	/**
	 * 获取当前周是本月的第几周
	 * @return String
	 */
	public static int getWeekOfMonth() {
		Calendar cd = Calendar.getInstance(); 
		cd.setFirstDayOfWeek(Calendar.MONDAY);
		return cd.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 获取当前周是本年的第几周
	 * @return String
	 */
	public static int getWeekOfYear() {
		Calendar cd = Calendar.getInstance();
		cd.setFirstDayOfWeek(Calendar.MONDAY);
		return cd.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取本月第一天
	 * @return yyyy-MM-dd
	 */
	public static String getFirstDayOfThisMonth() {
		String year = getYear();
		String month = getMonth();
		if (month.length() == 1) {
			return year + "-0" + month + "-01";
		}
		return year + "-" + month + "-01";
	}
	
	/**
	 * 获取本月最后一天
	 * @return yyyy-MM-dd
	 */
	public static String getLastDayOfThisMonth() {
		Calendar calendar = Calendar.getInstance();  
		try {
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);  
			calendar.add(Calendar.DATE, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SDF.format(calendar.getTime());
	}

	/**
	 * 获取指定年份月份的最后一天（整形参数）
	 * @param year 年份
	 * @param month 月份
	 * @return yyyy-MM-dd
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();  
		try {
			calendar.set(year, month, 1);
			calendar.add(Calendar.DATE, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SDF.format(calendar.getTime());
	}
	
	/**
	 * 获取指定年份月份的最后一天（字符串参数）
	 * @param year 年份
	 * @param month 月份
	 * @return yyyy-MM-dd
	 */
	public static String getLastDayOfMonth(String year, String month) {
		int y = Integer.parseInt(year);
		int m = Integer.parseInt(month);
		return getLastDayOfMonth(y, m);
	}

	/**
	 * 获取两个日期之间相差天数
	 * @param fromDay
	 * @param toDay
	 * @return long
	 */
	public static long getDaysBetween(String fromDay, String toDay) {
		long days = 0;
		try {
			Date d1 = SDF.parse(fromDay);
			Date d2 = SDF.parse(toDay);
			long diff = d2.getTime() - d1.getTime();
			days = diff / (1000 * 60 * 60 * 24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return days;
	}
	
	/**
	 * 按指定格式转换日期对象
	 * @param date 日期对象
	 * @param format 日期格式，如：yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String convertDateToString(Date date, String format) {
		SimpleDateFormat mySDF = new SimpleDateFormat(format);
		String dateStr = mySDF.format(date);
		return dateStr;
	}
	
	/**
	 * 获取指定分钟前的时间字符串
	 * @param minute 分钟
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String getPastTimeByMinute(int minute) {
		Date date = new Date(new Date().getTime() - (minute * 60 * 1000));
		return convertDateToString(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取指定分钟前的时间字符串（自定义格式）
	 * @param minute 分钟
	 * @param format 日期格式，如：yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String getPastTimeByMinute(int minute, String format) {
		Date date = new Date(new Date().getTime() - (minute * 60 * 1000));
		return convertDateToString(date, format);
	}
	
	/**
	 * 获取指定天数前的时间字符串
	 * @param day 天数
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String getPastTimeByDay(int day) {
		Date date = new Date(new Date().getTime() - (day * 24 * 60 * 60 * 1000));
		return convertDateToString(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取指定天数前的时间字符串（自定义格式）
	 * @param day 天数
	 * @param format 日期格式，如：yyyy-MM-dd HH:mm:ss
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String getPastTimeByDay(int day, String format) {
		Date date = new Date(new Date().getTime() - (day * 24 * 60 * 60 * 1000));
		return convertDateToString(date, format);
	}
	
	/**
	 * 获取基于某个时间点后多少天后的时间点
	 * ，如：2015-08-01 16:00:05，15天之后的时间点是
	 * @param beginTime 开始时间点 yyyy-MM-dd HH:mm:ss
	 * @param day 天数
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String getFutureTimeByDay(String beginTime, int day) {
		Date futureDate = new Date();
		try {
			Date debinDate = SDFT.parse(beginTime);
			futureDate = new Date(debinDate.getTime() + (day * 24 * 60 * 60 * 1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertDateToString(futureDate, "yyyy-MM-dd HH:mm:ss");
	}

}
