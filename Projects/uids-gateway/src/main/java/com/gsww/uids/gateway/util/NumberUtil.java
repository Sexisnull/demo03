package com.gsww.uids.gateway.util;

import org.apache.commons.lang.math.NumberUtils;

public class NumberUtil {

	public static int getInt(Object paramObject) {
		return NumberUtils.toInt(StringUtil.getString(paramObject));
	}

	public static long getLong(Object paramObject) {
		return NumberUtils.toLong(StringUtil.getString(paramObject));
	}

}
