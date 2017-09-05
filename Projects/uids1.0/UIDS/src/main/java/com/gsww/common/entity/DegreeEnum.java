package com.gsww.common.entity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
* 学历等级
* 
* @author taolm
*
*/
public enum DegreeEnum {
	DOCTOR(1, "博士"),
	MASTER(2, "硕士"),
	COLLEGE(3, "本科"),
	ACADEMY(4, "专科"),
	SENIOR (5, "高中"),
	JUNIOR(6, "初中"),
	PRIMARY(7, "小学"),
	OTHER(8, "其它");
	
	/**
	 * 学历编号
	 */
	private int number;
	
	/**
	 * 学历
	 */
	private String value;
	
	/**
	 * 私有的构造函数
	 * 
	 * @param number
	 * @param value
	 */
	private DegreeEnum(int number, String value) {
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
		for ( DegreeEnum element : DegreeEnum.values() ) {
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
		for ( DegreeEnum element : DegreeEnum.values() ) {
			if ( element.getValue().equalsIgnoreCase(value)) {
				return element.getNumber();
			}
		}
		
		return 0;
	}
	/**
	 * 获得对象的json数组
	 */
	public static JSONArray getValueArray() {
		JSONArray array = new JSONArray();
		
		JSONObject object = null;
		for ( DegreeEnum element : DegreeEnum.values() ) {
			object = new JSONObject();
			object.put("number", element.getNumber());
			object.put("value", element.getValue());
			
			array.put(object);
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
