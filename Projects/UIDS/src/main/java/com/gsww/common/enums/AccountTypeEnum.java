package com.gsww.common.enums;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 账号类型枚举类
 * 
 * @author taolm
 *
 */
public enum AccountTypeEnum {

	// 公务账号
	GOVERNMENT(1, "government", "公务账号"),	
	// 个人账号
	PUBLIC(2, "public", "个人账号"),	
	// 法人账号
	CORPORATE(3, "corporate", "法人账号");
	
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
	private AccountTypeEnum(int number, String value, String name) {
		this.number = number;
		this.value = value;
		this.name = name;
	}
	
	/**
	 * 根据编号获取值
	 * 
	 * @param number
	 * @return
	 */
	public static String valueOf(int number) {
		for ( AccountTypeEnum element : AccountTypeEnum.values() ) {
			if ( element.getNumber() == number ) {
				return element.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * 根据中文名称获取编号
	 * 
	 * @param name
	 * @return
	 */
	public static int numberOf(String name) {
		for ( AccountTypeEnum element : AccountTypeEnum.values() ) {
			if ( element.getName().equalsIgnoreCase(name)  ) {
				return element.getNumber();
			}
		}
		
		return 1;
	}
	
	
	/**
	 * 根据编号获取中文名称
	 * 
	 * @param number
	 * @return
	 */
	public static String nameOf(int number) {
		for ( AccountTypeEnum element : AccountTypeEnum.values() ) {
			if ( element.getNumber() == number ) {
				return element.getName();
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
		for ( AccountTypeEnum element : AccountTypeEnum.values() ) {
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
