package com.gsww.jup.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTime {

	/**
	 * 当前时间加分钟
	 * @param minute
	 * @return
	 */
	public static String addNowTime(int minute) {
		Calendar c = Calendar.getInstance();
		DateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
		c.add(Calendar.MINUTE, minute);
		return format1.format(c.getTime());
	}
	/**
	 * 当前时间加秒
	 * @param second
	 * @return
	 */
	public static String addSecond(int second){
		Calendar c = Calendar.getInstance();
		DateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
		c.add(Calendar.SECOND, second);
		return format1.format(c.getTime());
	}
	
	public static void main(String args[]){
		System.out.println(DateTime.addNowTime(1));
		System.out.println(DateTime.addSecond(1));		
	}
}
