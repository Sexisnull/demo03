package com.gsww.uids.gateway.util;

import com.hanweb.sso.ldap.util.MD5;

public class Md5Util {
	private static String a = "jcms2008";

	public static String md5encode(String paramString) {
		try {
			MD5 localMD5 = new MD5();
			return localMD5.encrypt(paramString, a);
		} catch (Exception localException) {
		}
		return paramString;
	}

	public static String md5decode(String paramString) {
		try {
			MD5 localMD5 = new MD5();
			return localMD5.decrypt(paramString, a);
		} catch (Exception localException) {
		}
		return paramString;
	}

	public static String md5encode(String paramString1, String paramString2) {
		try {
			MD5 localMD5 = new MD5();
			return localMD5.encrypt(paramString1, paramString2);
		} catch (Exception localException) {
		}
		return paramString1;
	}

	public static String md5decode(String paramString1, String paramString2) {
		try {
			MD5 localMD5 = new MD5();
			return localMD5.decrypt(paramString1, paramString2);
		} catch (Exception localException) {
		}
		return paramString1;
	}

	public static String md5Base64decode(String paramString1, String paramString2) {
		try {
			MD5 localMD5 = new MD5();
			return localMD5.decryptMB(paramString1, paramString2);
		} catch (Exception localException) {
		}
		return paramString1;
	}

	public static String md5Base64encode(String paramString1, String paramString2) {
		try {
			MD5 localMD5 = new MD5();
			return localMD5.encryptMB(paramString1, paramString2);
		} catch (Exception localException) {
		}
		return paramString1;
	}
}