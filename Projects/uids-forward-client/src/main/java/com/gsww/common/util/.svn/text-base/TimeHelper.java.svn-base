package com.gsww.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * <p>
 * 日期帮助类
 * </p>
 * @company 中国电信甘肃万维公司 
 *
 * @project jup-util
 *
 * @version V2.0.0
 *
 * @author lixin
 *
 * @date 2012-6-19 下午05:01:04	
 *
 * @class com.gsww.jup.util.TimeHelper
 *
 */
public class TimeHelper {
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
	 * 得到当天的最早日期时间,共23位 返回格式：yyyy-MM-dd 00:00:00 000
	 * 
	 * @return String
	 */
	public static String getCurrentStartDayDate() {
		Date NowDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		CurrentDate = formatter.format(NowDate)+" 00:00:00 000";
		return CurrentDate;
	}
	/**
	 * 得到当月的最早日期时间,共23位 返回格式：yyyy-MM-dd 00:00:00 000
	 * 
	 * @return String
	 */
	public static String getCurrentStartMonthDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		//获取当月的第一天
        Calendar cal=Calendar.getInstance();//获取当前日期 
        cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        CurrentDate = formatter.format(cal.getTime())+" 00:00:00 000";
		return CurrentDate;
	}
	/**
	 * 得到当天的最晚日期时间,共23位 返回格式：yyyy-MM-dd 23:59:59 999
	 * 
	 * @return String
	 */
	public static String getCurrentEndDayDate() {
		Date NowDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		CurrentDate = formatter.format(NowDate)+" 23:59:59 999";
		return CurrentDate;
	}
	/**
	 * 得到当月的最晚日期时间,共23位 返回格式：yyyy-MM-dd 23:59:59 999
	 * 
	 * @return String
	 */
	public static String getCurrentEndMonthDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		//获取前月的最后一天
        Calendar cale = Calendar.getInstance(); 
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
		CurrentDate = formatter.format(cale.getTime())+" 23:59:59 999";
		return CurrentDate;
	}
	/**
	 * 得到当前的日期,共10位 返回格式：yyyy年MM月dd日
	 * 
	 * @return String
	 */
	public static String getCurrentDateChinese() {
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
	
	/**
	 * 得到当前日期加上某一个整数的分钟 输入参数：currentdatetime : String 格式 yyyy-MM-dd HH:mm:ss
	 * add_minute : int 返回格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String addMinute(String currentdatetime, int add_minute) {
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(parseDateTime(currentdatetime));
			calendar.add(Calendar.MINUTE, add_minute);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return formatter.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 某一时间加上若干秒之后的时间
	 * 
	 * @param currentdatetime
	 * @param add_second
	 * @return
	 */
	public static String addSecond(String currentdatetime, int add_second) {
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(parseDateTime(currentdatetime));
			calendar.add(Calendar.SECOND, add_second);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return formatter.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 得到当前日期加上某一个整数的日期，整数代表月数 输入参数：currentdate : String 格式 yyyy-MM-dd add_month :
	 * int 返回格式：yyyy-MM-dd
	 */
	public static String addMonth(String currentdate, int add_month) {
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(parseDate(currentdate));
			calendar.add(Calendar.MONTH, add_month);
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
			theday   =   (timeend   -   timethis)   /   (1000   *   60   *   60   *   24); 
		}catch(Exception e) {
			return 0;
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
    	int mondayPlus = TimeHelper.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(monday);
    }
	/**
	 * 获取当前时间所在周的星期天
	 * @return
	 */
	public static String getNowTimeSunday(){
    	int mondayPlus = TimeHelper.getMondayPlus();
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
	
	@SuppressWarnings("deprecation")
	public static Date getLastDayOfMonth(Date sDate1)   {  
        Calendar cDay1 = Calendar.getInstance();  
        cDay1.setTime(sDate1);  
        int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);  
        Date lastDate = cDay1.getTime();  
        lastDate.setDate(lastDay);  
        return lastDate;  
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
	/**
	 * 获取本周时间所在周的最早日期时间
	 * 共23位 返回格式：yyyy-MM-dd 00:00:00 000
	 * @return
	 */
	public static String getWeekStart() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		// 当周开始时间
         Calendar cale = Calendar.getInstance();
         cale.setFirstDayOfWeek(Calendar.MONDAY);
         cale.set(Calendar.HOUR_OF_DAY, 0);
         cale.set(Calendar.MINUTE, 0);
         cale.set(Calendar.SECOND, 0);
         cale.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
         CurrentDate = formatter.format(cale.getTime())+" 00:00:00 000";
         return CurrentDate; 		
	 }
	/**
	 * 获取本周时间所在周的最晚日期时间
	 * 共23位 返回格式：yyyy-MM-dd 23:59:59 999
	 * @return
	 */
	public static String getWeekEnd() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		// 当周结束时间
        Calendar currentDate = Calendar.getInstance();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        CurrentDate = formatter.format(currentDate.getTime())+" 23:59:59 999";
        return CurrentDate; 
	} 
    public static Date nextMonthFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
    
	 /** 
     * 获取某年第一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static String getYearFirst(int year){ 
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
    	 Calendar calendar = Calendar.getInstance();  
    	 calendar.clear();
    	 calendar.set(Calendar.YEAR,year);
		 String first = format.format(calendar.getTime());
        return first;  
    } 
    /** 
     * 获取某年最后一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static String getYearLast(int year){  
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
     	Calendar calendar = Calendar.getInstance();  
   	    calendar.clear();
   	    calendar.set(Calendar.YEAR,year);//设置为1号,当前日期既为本月第一天
   	    calendar.roll(Calendar.DAY_OF_YEAR, -1); 
		 String last = format.format(calendar.getTime());
    	
        return last;  
    }  
    /** 
     * 获取当年的第一天 
     * @param year 
     * @return 
     */  
	public static String getCurrYearFirst(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearFirst(currentYear);  
    }
	/** 
     * 获取当年的最后一天 
     * @param year 
     * @return 
     */  
    public static String getCurrYearLast(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearLast(currentYear);  
    }
    
    /**
     * 获取当前月份
     * @param month 
     * @return 
     */
    public static int getCurrnetMonth(){
    	Calendar cal = Calendar.getInstance();
		  return  cal.get(Calendar.MONTH) + 1;
    }
    
    public static String getDateAddTime(String time,int s){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	   Date date = null;
    	   try {
	    	    date = format.parse(time);
	    	    Date afterDate = new Date(date .getTime() + s*60*1000);
	    	    return format.format(afterDate);
    	   } catch (ParseException e) {
    	        e.printStackTrace();
    	   }
    	   return "";
    }
    
	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 * 字符串的日期格式的计算
	 */
	public static int minuteBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_minute = (time2 - time1) / (1000 * 60);
		return Integer.parseInt(String.valueOf(between_minute));
	}
	
	
	/**
	 * 计算两个时间的时间差（以分钟计算）
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long minutesBetween( Date startTime, Date endTime ) {
		long time1 = startTime.getTime();
		long time2 = endTime.getTime();
		
		long minutes = (time2 - time1) / (1000 * 60);
		
		return minutes;
	}
	
	/**
	 * 计算两个时间的时间差（以秒计算）
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long secondsBetween(Date startTime, Date endTime) {
		long time1 = startTime.getTime();
		long time2 = endTime.getTime();
		
		long seconds = (time2 - time1) / 1000;
		
		return seconds;
	}
	
	/**
	 * 计算两个时间的时间差（以秒计算）
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long secondsBetween(String startTime, String endTime) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		long time1 = sdf.parse(startTime).getTime();
		long time2 = sdf.parse(endTime).getTime();
		
		long seconds = (time2 - time1) / 1000;
		
		return seconds;
	}	
}
