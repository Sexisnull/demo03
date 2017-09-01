package com.gsww.uids.gateway.util;

import java.io.InputStream;
import java.util.*;
/**
 * 读入配置文件
 * Sample:String value=PropertiesHelper.getProperty(PropertiesHelper.SYS_JAVA_NAMING_FACTORY_INITIAL).toString();
 */
public final class PropertiesHelper {

	private static final Properties properties;
	
	public static String JAVA_CUST_THREAD_NUMS = "jndi.cust.thread.nums";
	public static String JAVA_USER_THREAD_NUMS = "jndi.user.thread.nums";
	public static String MGS_RETURN_ADD = "msg.return.address";
	public static String MGS_RETURN_ADD_0931 = "msg.return.address_0931";
	public static String JMS_ADDRESS="jms.address";
	public static String CRM_REVERSE_INTERFACE="crm.reverse.interface";
	
	static {
		properties = new Properties();
		InputStream stream =
			PropertiesHelper.class.getResourceAsStream("/ApplicationResources.properties");
		try {
			properties.load(stream);
		} catch (Exception e) {

		}
		properties.putAll(System.getProperties());
	}

	/**
	 * @param filename :文件名称
	 * @return PropertyResourceBundle
	 * 取得文件
	 */
	private static PropertyResourceBundle getFile(String filename) {

		return (PropertyResourceBundle) ResourceBundle.getBundle(filename);
	}
	/**
	 * @param propertyName :对象名称
	 * @param filename :文件名称
	 * @return 对象值
	 * 取得配置文件的对象值
	 */
	private static String getProperty(String propertyName, String filename) {
		return getFile(filename).getString(propertyName);

	}

	/**
	 * @param propertyName :对象名称
	 * @return 对象值
	 * 取得配置文件的对象值
	 */
	public static Object getProperty(String propertyName) {
		Object result= properties.get(propertyName);
		return result;
	}
}
