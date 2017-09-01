package com.gsww.common.entity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 认证状态
 * 
 * @author taolm
 *
 */
public enum AuthStatusEnum {

	// 所有认证状态
	UNAUDIT(1, "未审核"),
	ONAUDIT(2, "待审核"),
	AUDITED(3, "已认证"),
	DENY(4, "审核不通过");
	
	/**
	 * 状态编号
	 */
	private int number;
	
	/**
	 * 状态值
	 */
	private String value;
	
	/**
	 * 私有的构造函数
	 * 
	 * @param number
	 * @param value
	 */
	private AuthStatusEnum(int number, String value) {
		this.number = number;
		this.value = value;
	}
	
	/**
	 * 根据编号获取值
	 * 
	 * @param number
	 * @return
	 */
	public static String valueOf(int number) {
		for ( AuthStatusEnum element : AuthStatusEnum.values() ) {
			if ( element.getNumber() == number ) {
				return element.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * 根据值获取编号
	 * 
	 * @param number
	 * @return
	 */
	public static int numberOf(String value) {
		for ( AuthStatusEnum element : AuthStatusEnum.values() ) {
			if ( element.getValue().equalsIgnoreCase(value)) {
				return element.getNumber();
			}
		}
		
		return 1;
	}
	
	/**
	 * 获得对象的json数组
	 */
	public static JSONArray getValueArray() {
		JSONArray array = new JSONArray();
		
		JSONObject object = null;
		for ( AuthStatusEnum element : AuthStatusEnum.values() ) {
			object = new JSONObject();
			object.put("number", element.getNumber());
			object.put("value", element.getValue());
			
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
}
