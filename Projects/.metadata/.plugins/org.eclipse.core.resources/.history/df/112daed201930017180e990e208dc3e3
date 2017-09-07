/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p><strong>公司&#9;:&#9;</strong>中国电信甘肃万维公司</p>
 * <p><strong>项目&#9;:&#9;</strong>jup-core</p>
 * <p><strong>创建时间&#9;:&#9;</strong>2011-10-17 下午10:45:01</p>
 * <p><strong>类描述&#9;:&#9;</strong>
 * 		
 * </p>
 * @version 1.0.0
 * @author 郭磊(软件二部)
 */
public class JupProperties {
	
	private static final JupProperties jupProperties = new JupProperties();
	private Properties properties = null;
	
	public static final String JUP_ADMIN_ACCOUNT = "JUP_ADMIN_ACCOUNT";
	public static final String JUP_ADMIN_PASSWORD = "JUP_ADMIN_PASSWORD";
	
	private JupProperties()
	{
		properties = new Properties();
		InputStream stream = this.getClass().getResourceAsStream("/JUP.properties");
		try {
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		properties.putAll(System.getProperties());
	}
	
	/**
	 * @param propertyName :对象名称
	 * @return 对象值
	 * 取得配置文件的对象值
	 */
	public String getPropertyByStr(String propertyName) {
		return String.valueOf(this.properties.get(propertyName));
	}
	/**
	 * @param propertyName :对象名称
	 * @return 对象值
	 * 取得配置文件的对象值
	 */
	public int getPropertyByInt(String propertyName) {
		return Integer.parseInt(String.valueOf(this.properties.get(propertyName)));
	}
	
	public static JupProperties getInstance()
	{
		return jupProperties;
	}
}
