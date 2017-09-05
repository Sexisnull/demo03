package com.gsww.common.enums;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * 日志操作模块枚举类
 * @author jinwei
 *
 */
public enum ModuleEnum {
//	login, organization, source, account, app, role, area, organizationGroup, user, sysParam, logout
	ORGANIZATION("organization", "机构"),
	ORGANIZATIONGROUP("organizationGroup","分组机构"),
	USER("user","用户"),
	ACCOUNT("account","账号"),
	ROLE("role","角色"),
	APP("app","应用"),
	SOURCE("source","资源"),
	SYSPARAM("sysParam", "系统参数"),
	AREA("area", "区域"),
	LOGIN("login", "登录"),
	LOGOUT("logout", "登出"),
	REGISTER("register", "注册");
	
	/**
	 * 模块string
	 */
	private String number;
	
	/**
	 * 模块中文名称
	 */
	private String value;
	

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
	
	/**
	 * 私有的构造函数
	 * 
	 * @param number
	 * @param value
	 */
	private ModuleEnum(String number, String value) {
		this.number = number;
		this.value = value;
	}
	
	/**
	 * 根据编号获取中文名称
	 * 
	 * @param number
	 * @return
	 */
	public static String valueOfLog(String number) {
		for ( ModuleEnum element : ModuleEnum.values() ) {
			if ( element.getNumber().equalsIgnoreCase(number)) {
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
		for ( ModuleEnum element : ModuleEnum.values() ) {
			object = new JSONObject();
			object.put("number", element.getNumber());
			object.put("value", element.getValue());
			
			array.put(object);
		}
		
		return array;
	}
}
