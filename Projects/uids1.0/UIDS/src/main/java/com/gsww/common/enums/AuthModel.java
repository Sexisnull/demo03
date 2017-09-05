package com.gsww.common.enums;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 认证模式
 * @author sunbw
 *
 */
public enum AuthModel {
	PNA("1", "身份证+姓名"),
	PNAOC("2", "身份证+姓名+机构编码"),
	PNAONA("3", "身份证+姓名+机构名称"),
	PNAAC("4", "身份证+姓名+区域编码"),
	PNAOCONAAC("5", "身份证+姓名+机构编码+机构名称+区域编码");
	
	/**
	 * 认证编号
	 */
	private String  number;
	
	/**
	 * 认证中文名称
	 */
	private String value;
	
	/**
	 * 私有的构造函数
	 * 
	 * @param number
	 * @param value
	 */
	private AuthModel(String number, String value) {
		this.number = number;
		this.value = value;
	}
	
	/**
	 * 根据编号获取中文名称
	 * 
	 * @param number
	 * @return
	 */
	public static String valueOf(int number) {
		for ( AreaEnum element : AreaEnum.values() ) {
			if ( element.getNumber() == number ) {
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
		for ( AuthModel element : AuthModel.values() ) {
			object = new JSONObject();
			object.put("number", element.getNumber());
			object.put("value", element.getValue());
			
			array.put(object);
		}
		
		return array;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
