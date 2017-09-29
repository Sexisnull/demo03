package com.gsww.jup.util;

import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.*;
/**
 *
 * 读入配置文件
 */
public final class ReadProperties {

	private static final Properties properties;
	static {
		properties = new Properties();
		InputStream stream =
			ReadProperties.class.getResourceAsStream("/ApplicationResources.properties");
		try {
			properties.load(stream);
		} catch (Exception e) {

		}
		properties.putAll(System.getProperties());
	}
	
	public static String getPropertyByStr(String propertyName) {
		return String.valueOf(ReadProperties.properties.get(propertyName));
	}
	
	public static int getPropertyByInt(String propertyName) {
		return Integer.parseInt(ReadProperties.getPropertyByStr(propertyName));
	}
}
