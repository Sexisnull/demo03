package com.gsww.uids.gateway.util;

import org.apache.commons.lang.RandomStringUtils;

public class MathUtil {
	/* 获取随机数 */
	public static String randomNumeric(Integer paramInteger) {
		return RandomStringUtils.randomNumeric(paramInteger.intValue());
	}

}
