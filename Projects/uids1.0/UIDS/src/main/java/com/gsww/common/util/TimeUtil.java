package com.gsww.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间日期工具类
 * 
 * @author simplife
 *
 */
public class TimeUtil {
	
	private static String CurrentTime;

	private static String CurrentDate;

	/**
	 * 得到当前的年份 返回格式:yyyy
	 * 
	 * @return String
	 */
	public static String getCurrentYear() {
		java.util.Date NowDate = new java.util.Date();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return formatter.format(NowDate);
	}

	/**
	 * 得到当前的月份 返回格式:MM
	 * 
	 * @return String
	 */
	public static String getCurrentMonth() {
		java.util.Date NowDate = new java.util.Date();

		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		return formatter.format(NowDate);
	}

	/**
	 * 得到当前的日期 返回格式:dd
	 * 
	 * @return String
	 */
	public static String getCurrentDay() {
		java.util.Date NowDate = new java.util.Date();

		SimpleDateFormat formatter = new SimpleDateFormat("dd");
		return formatter.format(NowDate);
	}
	/**
	 * 得到当前的时间，精确到秒,共19位 返回格式:yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getCurrentTime() {
		Date NowDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CurrentTime = formatter.format(NowDate);
		return CurrentTime;
	}
	
	/**
	 * 得到当前的时间，精确到毫秒,共23位 返回格式:yyyy-MM-dd HH:mm:ss SS
	 * 
	 * @return String
	 */
	public static String getExactCurrentTime() {
		Date NowDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SS");
		CurrentTime = formatter.format(NowDate);
		return CurrentTime;
	}

	public static String getCurrentCompactTime() {
		Date NowDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		CurrentTime = formatter.format(NowDate);
		return CurrentTime;
	}
	@SuppressWarnings("static-access")
	public static String getYesterdayCompactTime() {
		Calendar cal = Calendar.getInstance(); 
		cal.add(cal.DATE, -1); 
		System.out.print(cal.getTime()); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		CurrentTime = formatter.format(cal.getTime());
		return CurrentTime;
	}
	@SuppressWarnings("static-access")
	public static String getYesterdayCompactTimeForFileName() {
		Calendar cal = Calendar.getInstance(); 
		cal.add(cal.DATE, -1); 
		System.out.print(cal.getTime()); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CurrentTime = formatter.format(cal.getTime());
		return CurrentTime;
	}
	/**
	 * 得到当前的日期,共10位 返回格式：yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getCurrentDate() {
		Date NowDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		CurrentDate = formatter.format(NowDate);
		return CurrentDate;
	}
	/**
	 * 得到当前的日期,共10位 返回格式：yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getCurrentDate1() {
		Date NowDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		CurrentDate = formatter.format(NowDate);
		return CurrentDate;
	}
	/**
	 * 得到当月的第一天,共10位 返回格式：yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getCurrentFirstDate() {
		Date NowDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-01");
		CurrentDate = formatter.format(NowDate);
		return CurrentDate;
	}
	/**
	 * 得到当月的最后一天,共10位 返回格式：yyyy-MM-dd
	 * 
	 * @return String
	 * @throws ParseException 
	 */
	public static String getCurrentLastDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar= null;
		try {
			java.util.Date date =formatter.parse(getCurrentFirstDate());
			calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH,1);
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			 return formatDate(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}


	/**
     * 常用的格式化日期
     *
     * @param date Date
     * @return String
     */
    public static String formatDate(java.util.Date date)
    {
        return formatDateByFormat(date,"yyyy-MM-dd");
    }
    /**
     * 以指定的格式来格式化日期
     *
     * @param date Date
     * @param format String
     * @return String
     */
    public static String formatDateByFormat(java.util.Date date,String format)
    {
        String result = "";
        if(date != null)
        {
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            }
            catch(Exception ex)
            {
                
                ex.printStackTrace();
            }
        }
        return result;
    }
	
    /**
	 * 得到当前日期加上某一个整数的日期，整数代表天数 输入参数：
	 * currentdate : String 格式 yyyy-MM-dd 
	 * add_day: int 
	 * 返回格式：yyyy-MM-dd
	 */
	public static String addDay(String date, int add_day) {
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(parseDate(date));
			calendar.add(Calendar.DATE, add_day);//把日期往后增加一天.整数往后推,负数往前移动  
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			return formatter.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 得到当前日期加上某一个整数的日期
	 * 返回格式：yyyy-MM-dd
	 * @param add_day
	 * @return
	 */
	public static String addNowTimeDay(int add_day) {
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, add_day);//把日期往后增加一天.整数往后推,负数往前移动  
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
			return formatter.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date parseDate(String sDate) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = bartDateFormat.parse(sDate);
			return date;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}

	/**
	 * 解析日期及时间
	 * 
	 * @param sDateTime
	 *            日期及时间字符串
	 * @return 日期
	 */
	public static Date parseDateTime(String sDateTime) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		try {
			Date date = bartDateFormat.parse(sDateTime);
			return date;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}

	/**
	 * 取得一个月的天数 date:yyyy-MM-dd
	 * 
	 * @throws ParseException
	 */
	public static int getTotalDaysOfMonth(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();

		Date date = new Date();
		try {
			date = sdf.parse(strDate);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		calendar.setTime(date);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 天数
		return day;
	}
	
	public static long getDateSubDay(String startDate ,String endDate ) {
		Calendar calendar = Calendar.getInstance(); 
		SimpleDateFormat   sdf   =   new   SimpleDateFormat("yyyy-MM-dd"); 
		long theday = 0;
		try  {
			calendar.setTime(sdf.parse(startDate)); 
			long   timethis   =   calendar.getTimeInMillis(); 
			calendar.setTime(sdf.parse(endDate)); 
			long   timeend   =   calendar.getTimeInMillis(); 
			theday   =   (timethis   -   timeend)   /   (1000   *   60   *   60   *   24); 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return theday;
	}
	public static String getCurrentYMDDate() {
		Calendar cal = Calendar.getInstance(); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		CurrentTime = formatter.format(cal.getTime());
		return CurrentTime;
	}
	
	/**
	 * 获取当前时间所在周的星期一
	 * @return
	 */
	public static String getNowTimeMonday(){
    	int mondayPlus = TimeUtil.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }
	/**
	 * 获取当前时间所在周的星期天
	 * @return
	 */
	public static String getNowTimeSunday(){
    	int mondayPlus = TimeUtil.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * 0 + 5);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }
	
	public static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }
    
	/**
	 * 获取当前时间所在月的第一天日期
	 * @return
	 */
	public static String getMonthFirstDay(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();   
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		String first = format.format(c.getTime());
		return first;
    }
	
	/**
	 * 获取当前时间所在月的最后一天天日期
	 * @return
	 */
	public static String getMonthEndDay(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar ca = Calendar.getInstance();   
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		String last = format.format(ca.getTime());
		return last;
    }
	
    public static Date nextMonthFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
 
}
