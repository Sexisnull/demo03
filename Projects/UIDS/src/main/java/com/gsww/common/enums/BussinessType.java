package com.gsww.common.enums;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 业务类型
 * 
 * @author taolm
 *
 */
public enum BussinessType {
	// 注册账号
	REGISTER_ACCOUNT(1, "注册账号"),
	// 个人注册
	NATURAL_REGISTER(2, "个人注册"),	
	// 法人注册
	CORPORATE_REGISTER(3, "法人注册"),
	// 绑定用户
	BIND_USER(4, "绑定用户"),
	// 解绑用户
	UNBIND_USER(5, "解绑用户"),
	// 找回密码
	FETCH_PASSWORD(6, "找回密码"),
	// 动态验证码
	AUTH_MOBILE(7, "认证手机"),
	// 注销账号
	DELETE_ACCOUNT(8, "注销账号");
		
	/**
	 * 类型编号
	 */
	private int number;
	
	/**
	 * 类型值
	 */
	private String value;
	
	/**
	 * 私有的构造函数
	 * 
	 * @param number
	 * @param value
	 */
	private BussinessType(int number, String value) {
		this.number = number;
		this.value = value;
	}
	
	/**
	 * 根据编码获取类型值
	 * 
	 * @param number
	 * @return
	 */
	public static BussinessType getByNumber(int number) {
		for ( BussinessType element : BussinessType.values() ) {
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
		for ( BussinessType element : BussinessType.values() ) {
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
		for ( BussinessType element : BussinessType.values() ) {
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
