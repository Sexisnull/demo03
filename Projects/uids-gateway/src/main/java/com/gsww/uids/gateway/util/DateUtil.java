package com.gsww.uids.gateway.util;

import java.util.Date;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {
	protected static Log logger = LogFactory.getLog(DateUtil.class);
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYYMMDD_HHMMSS = "yyyyMMdd-HHmmss";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String MM_DD = "MM-dd";
	public static final String HH_MM_SS = "HH:mm:ss";
	public static final String YYYY_MM = "yyyy-MM";
	public static final int SUB_YEAR = 1;
	public static final int SUB_MONTH = 2;
	public static final int SUB_DAY = 5;
	public static final int SUB_HOUR = 10;
	public static final int SUB_MINUTE = 12;
	public static final int SUB_SECOND = 13;
	static final String[] a = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	private static final SimpleDateFormat b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Date stringtoDate(String paramString1, String paramString2) {
		Date localDate = null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2);
		localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			localSimpleDateFormat.setLenient(false);
			localDate = localSimpleDateFormat.parse(paramString1);
		} catch (Exception localException) {
			localDate = null;
		}
		return localDate;
	}

	public static Date stringtoDate(String paramString1, String paramString2, ParsePosition paramParsePosition) {
		Date localDate = null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2);
		localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			localSimpleDateFormat.setLenient(false);
			localDate = localSimpleDateFormat.parse(paramString1, paramParsePosition);
		} catch (Exception localException) {
			localDate = null;
		}
		return localDate;
	}

	public static String dateToString(Date paramDate, String paramString) {
		String str = "";
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
		localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			str = localSimpleDateFormat.format(paramDate);
		} catch (Exception localException) {
		}
		return str;
	}

	public static String getCurrDate(String paramString) {
		return dateToString(new Date(), paramString);
	}

	public static String dateSub(int paramInt1, String paramString, int paramInt2) {
		Date localDate = stringtoDate(paramString, "yyyy-MM-dd HH:mm:ss");
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(localDate);
		localCalendar.add(paramInt1, paramInt2);
		return dateToString(localCalendar.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	public static long timeSub(String paramString1, String paramString2) {
		long l1 = stringtoDate(paramString1, "yyyy-MM-dd HH:mm:ss").getTime();
		long l2 = stringtoDate(paramString2, "yyyy-MM-dd HH:mm:ss").getTime();
		return (l2 - l1) / 1000L;
	}

	public static int getDaysOfMonth(String paramString1, String paramString2) {
		int i = 0;
		if ((paramString2.equals("1")) || (paramString2.equals("3")) || (paramString2.equals("5"))
				|| (paramString2.equals("7")) || (paramString2.equals("8")) || (paramString2.equals("10"))
				|| (paramString2.equals("12")))
			i = 31;
		else if ((paramString2.equals("4")) || (paramString2.equals("6")) || (paramString2.equals("9"))
				|| (paramString2.equals("11"))) {
			i = 30;
		} else if (((Integer.parseInt(paramString1) % 4 == 0) && (Integer.parseInt(paramString1) % 100 != 0))
				|| (Integer.parseInt(paramString1) % 400 == 0))
			i = 29;
		else {
			i = 28;
		}

		return i;
	}

	public static int getDaysOfMonth(int paramInt1, int paramInt2) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(paramInt1, paramInt2 - 1, 1);
		return localCalendar.getActualMaximum(5);
	}

	public static int getToday() {
		Calendar localCalendar = Calendar.getInstance();
		return localCalendar.get(5);
	}

	public static int getToMonth() {
		Calendar localCalendar = Calendar.getInstance();
		return localCalendar.get(2) + 1;
	}

	public static int getToYear() {
		Calendar localCalendar = Calendar.getInstance();
		return localCalendar.get(1);
	}

	public static int getDay(Date paramDate) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		return localCalendar.get(5);
	}

	public static int getYear(Date paramDate) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		return localCalendar.get(1);
	}

	public static int getMonth(Date paramDate) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		return localCalendar.get(2) + 1;
	}

	public static long dayDiff(Date paramDate1, Date paramDate2) {
		return (paramDate2.getTime() - paramDate1.getTime()) / 86400000L;
	}

	public static int yearDiff(String paramString1, String paramString2) {
		Date localDate1 = stringtoDate(paramString1, "yyyy-MM-dd");
		Date localDate2 = stringtoDate(paramString2, "yyyy-MM-dd");
		return getYear(localDate2) - getYear(localDate1);
	}

	public static int yearDiffCurr(String paramString) {
		Date localDate1 = new Date();
		Date localDate2 = stringtoDate(paramString, "yyyy-MM-dd");
		return getYear(localDate1) - getYear(localDate2);
	}

	public static long dayDiffCurr(String paramString) {
		Date localDate1 = stringtoDate(currDay(), "yyyy-MM-dd");
		Date localDate2 = stringtoDate(paramString, "yyyy-MM-dd");
		return (localDate1.getTime() - localDate2.getTime()) / 86400000L;
	}

	public static int getFirstWeekdayOfMonth(int paramInt1, int paramInt2) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setFirstDayOfWeek(7);
		localCalendar.set(paramInt1, paramInt2 - 1, 1);
		return localCalendar.get(7);
	}

	public static int getLastWeekdayOfMonth(int paramInt1, int paramInt2) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setFirstDayOfWeek(7);
		localCalendar.set(paramInt1, paramInt2 - 1, getDaysOfMonth(paramInt1, paramInt2));
		return localCalendar.get(7);
	}

	public static String getCurrDateTime() {
		Calendar localCalendar = Calendar.getInstance();
		return dateToString(localCalendar.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	public static String getAstro(String paramString) {
		if (!isDate(paramString)) {
			paramString = "2000" + paramString;
		}
		if (!isDate(paramString)) {
			return "";
		}
		int i = Integer.parseInt(paramString.substring(paramString.indexOf("-") + 1, paramString.lastIndexOf("-")));
		int j = Integer.parseInt(paramString.substring(paramString.lastIndexOf("-") + 1));
		String str = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
		int[] arrayOfInt = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };
		int k = i * 2 - (j < arrayOfInt[(i - 1)] ? 2 : 0);
		return str.substring(k, k + 2) + "座";
	}

	public static boolean isDate(String paramString) {
		StringBuffer localStringBuffer = new StringBuffer("^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
		localStringBuffer.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
		localStringBuffer.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
		localStringBuffer.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
		localStringBuffer.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
		localStringBuffer.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
		localStringBuffer.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
		localStringBuffer.append("1-9])|(1[0-9])|(2[0-8]))))))");
		Pattern localPattern = Pattern.compile(localStringBuffer.toString());
		return localPattern.matcher(paramString).matches();
	}

	public static Date nextMonth(Date paramDate, int paramInt) {
		Calendar localCalendar = Calendar.getInstance();
		if (paramDate != null) {
			localCalendar.setTime(paramDate);
		}
		localCalendar.add(2, paramInt);
		return localCalendar.getTime();
	}

	public static Date nextDay(Date paramDate, int paramInt) {
		Calendar localCalendar = Calendar.getInstance();
		if (paramDate != null) {
			localCalendar.setTime(paramDate);
		}
		localCalendar.add(6, paramInt);
		return localCalendar.getTime();
	}

	public static String nextDay(int paramInt, String paramString) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(new Date());
		localCalendar.add(6, paramInt);
		return dateToString(localCalendar.getTime(), paramString);
	}

	public static Date nextWeek(Date paramDate, int paramInt) {
		Calendar localCalendar = Calendar.getInstance();
		if (paramDate != null) {
			localCalendar.setTime(paramDate);
		}
		localCalendar.add(4, paramInt);
		return localCalendar.getTime();
	}

	public static String currDay() {
		return dateToString(new Date(), "yyyy-MM-dd");
	}

	public static String befoDay(int paramInt) {
		return befoDay(paramInt, "yyyy-MM-dd");
	}

	public static String befoDay(int paramInt, String paramString) {
		return dateToString(nextDay(new Date(), -paramInt), paramString);
	}

	public static String afterDay() {
		return dateToString(nextDay(new Date(), 1), "yyyy-MM-dd");
	}

	public static int getDayNum() {
		int i = 0;
		GregorianCalendar localGregorianCalendar1 = new GregorianCalendar();
		Date localDate1 = localGregorianCalendar1.getTime();
		GregorianCalendar localGregorianCalendar2 = new GregorianCalendar(1900, 1, 1);
		Date localDate2 = localGregorianCalendar2.getTime();
		i = (int) ((localDate1.getTime() - localDate2.getTime()) / 86400000L);
		return i;
	}

	public static Date getDateByNum(int paramInt) {
		GregorianCalendar localGregorianCalendar = new GregorianCalendar(1900, 1, 1);
		Date localDate = localGregorianCalendar.getTime();
		localDate = nextDay(localDate, paramInt);
		return localDate;
	}

	public static String getYmdDateCN(String paramString) {
		if (paramString == null)
			return "";
		if (paramString.length() < 10)
			return "";
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(paramString.substring(0, 4)).append(paramString.substring(5, 7))
				.append(paramString.substring(8, 10));
		return localStringBuffer.toString();
	}

	public static String getFirstDayOfMonth(String paramString) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(5, 1);
		return dateToString(localCalendar.getTime(), paramString);
	}

	public static String getLastDayOfMonth(String paramString) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(5, 1);
		localCalendar.add(2, 1);
		localCalendar.add(5, -1);
		return dateToString(localCalendar.getTime(), paramString);
	}
}
