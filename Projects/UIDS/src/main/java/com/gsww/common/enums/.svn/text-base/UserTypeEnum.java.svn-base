package com.gsww.common.enums;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户类型
 * 
 * @author taolm
 *
 */
public enum UserTypeEnum {
	
	// 自然人
	NATURAL(1, "natural", "个人"),	
	// 企业法人
	CORPORATE(2, "corporation", "法人");
		
	/**
	 * 类型编号
	 */
	private int number;
	
	/**
	 * 类型值
	 */
	private String value;
	
	/**
	 * 类型中文名称
	 */
	private String name;
	
	/**
	 * 私有的构造函数
	 * 
	 * @param number
	 * @param value
	 */
	private UserTypeEnum(int number, String value, String name) {
		this.number = number;
		this.value = value;
		this.name = name;
	}
	
	/**
	 * 根据编号获取类型
	 * 
	 * @param number
	 * @return
	 */
	public static UserTypeEnum getByNumber(int number) {
		for ( UserTypeEnum element : UserTypeEnum.values() ) {
			if ( element.getNumber() == number ) {
				return element;
			}
		}
		
		return null;
	}
	
	/**
	 * 根据编号获取值
	 * 
	 * @param number
	 * @return
	 */
	public static String valueOf(int number) {
		for ( UserTypeEnum element : UserTypeEnum.values() ) {
			if ( element.getNumber() == number ) {
				return element.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * 根据编号获取中文名称
	 * 
	 * @param number
	 * @return
	 */
	public static String nameOf(int number) {
		for ( UserTypeEnum element : UserTypeEnum.values() ) {
			if ( element.getNumber() == number ) {
				return element.getName();
			}
		}
		
		return null;
	}
	
	/**
	 * 根据中文名称获取编号
	 * 
	 * @param number
	 * @return
	 */
	public static int numberOf(String name) {
		for ( UserTypeEnum element : UserTypeEnum.values() ) {
			if ( element.getName().equalsIgnoreCase(name)) {
				return element.getNumber();
			}
		}
		
		return 1;
	}
	
	/**
	 * 根据中文名称获取值
	 * 
	 * @param name
	 * @return
	 */
	public static String valueOfName(String name) {
		for ( UserTypeEnum element : UserTypeEnum.values() ) {
			if ( element.getName().equalsIgnoreCase(name)) {
				return element.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * 获得对象的json数组
	 */
	public static JSONArray getValueArray() {
		JSONArray array = new JSONArray();
		
		JSONObject object = null;
		for ( UserTypeEnum element : UserTypeEnum.values() ) {
			object = new JSONObject();
			object.put("number", element.getNumber());
			object.put("value", element.getValue());
			object.put("name", element.getName());
			
			array.add(object);
		}
		
		return array;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}