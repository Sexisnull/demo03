package com.gsww.jup;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;

/**
 * 解决特殊字符查询的问题
 * @author ht
 * @date 2011.8.17
 */
public class RestrictionsUtils {
	public RestrictionsUtils(){  
	}  
	
	/** 
	 *  
	 * @description:处理字符串中含转义字符问题 
	 * @return 
	 */  
	public static Criterion ilike(final String propertyName, String value, MatchMode matchMode) {  
	    return new IlikeExpressionEx(propertyName, value, matchMode);  
	}  
}


