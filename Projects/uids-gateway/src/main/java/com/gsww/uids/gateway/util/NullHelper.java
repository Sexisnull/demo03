package com.gsww.uids.gateway.util;

import java.math.BigDecimal;
/**
 * Created on 2014-6-21 Title:
 * <p/>
 * Description:
 * <p/>
 * Copyright: Copyright GSWW (c) 2014
 * <p/>
 * Company: wanwei.com
 * <p/>
 * 
 * @author wangcf
 * @version 1.0
 */
public class NullHelper {

	public static Long convertObjectToLong(Object obj){
		if (obj==null || obj.toString().equals("")){
			return new Long(-1);
		}
		return new Long(String.valueOf(obj));
	}
	public static String convertNullToNothing(Object obj){
		if (obj==null || obj.toString().equals("") || obj.toString().equals("null")){
			return "&nbsp;";
		}
		return obj.toString();
	}
	public static String convertNullToNothingnull(Object obj){
		if (obj==null || obj.toString().equals("") || obj.toString().equals("null")){
			return "";
		}
		return obj.toString();
	}
	public static double convertNullToZero(Object obj){
		if (obj==null || obj.toString().equals("") || obj.toString().equals("null")){
			return 0.00;
		}
		return Double.parseDouble(obj.toString());
	}
	public static int convertNullToIntZero(Object obj){
		if (obj==null || obj.toString().equals("") || obj.toString().equals("null")){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * 此类专门用于商业计算的
	 * @param obj
	 * @return
	 */
	public static BigDecimal convertNullToBigZero(Object obj){
		if (obj==null || obj.toString().equals("") || obj.toString().equals("null")){
			return new BigDecimal("0.00");
		}
		return new BigDecimal(obj.toString());
	}
}
