package com.gsww.common.enums;

/**
 * 日志操作类型枚举类
 * @author jinwei
 *
 */
public enum OperateEnum {
	//操作类型
	INSERTTYPE("insert", "新增"),
	UPDATETYPE("update", "更新"),
	DELETETYPE("delete", "删除"),
	LOGIN("login", "登录"),
	LOGOUT("logout", "登出"),
	REGISTER("register", "注册");
	
	/**
	 * 模块string
	 */
	private String name;
	

	/**
	 * 模块中文名称
	 */
	private String value;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	private OperateEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * 根据编号获取中文名称
	 * 
	 * @param number
	 * @return
	 */
	public static String valueOfOpt(String name) {
		for ( OperateEnum element : OperateEnum.values() ) {
			if ( element.getName().equalsIgnoreCase(name)) {
				return element.getValue();
			}
		}
		
		return null;
	}
}
