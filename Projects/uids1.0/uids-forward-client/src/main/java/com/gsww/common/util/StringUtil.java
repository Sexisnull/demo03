package com.gsww.common.util;

/**
 * 字符串工具类
 * 
 * @author simplife
 *
 */
public class StringUtil {

	/**
	 * 第一个字符小写
	 * 
	 * @param str
	 * @return
	 */
	public static String lowerCaseFirstChar(String str) {

		char ch = str.charAt(0);

		if (!Character.isLowerCase(ch)) {

			str = str.replace(str.charAt(0), (char) (str.charAt(0) + 32));

		}

		return str;

	}

	/**
	 * 首字符大写
	 * 
	 * @param str
	 * @return
	 */
	public static String upperCaseFirstChar(String str) {

		char ch = str.charAt(0);

		if (!Character.isUpperCase(ch)) {

			str = String.valueOf(((char) (str.charAt(0) - 32)))
					+ str.substring(1);

		}

		return str;
	}

	/**
	 * 判断一个字符串是否为空
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isBlank(String src) {
		return src == null || src.length() == 0;
	}

	/**
	 * 判断一个字符串是否为空
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isBlank(Integer src) {
		return src == null;
	}

	/**
	 * 判断一个字符串是否不为空
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isNotBlank(String src) {
		return src != null && src.length() > 0;
	}

}
