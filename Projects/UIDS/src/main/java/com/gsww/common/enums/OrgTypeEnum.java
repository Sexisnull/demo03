package com.gsww.common.enums;

/**
 * 机构类型枚举类
 * @author jinwei
 *
 */
public enum OrgTypeEnum {
	//操作类型
	AREATYPE("area", "区域"),
	INSTITUTIONTYPE("institution", "单位"),
	DEPARTMENTTYPE("department", "部门或处室"),
	OTHERTYPE("other", "其他");
	
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
	private OrgTypeEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * 根据编号获取中文名称
	 * 
	 * @param number
	 * @return
	 */
	public static String valueOfOrgType(String name) {
		for ( OrgTypeEnum element : OrgTypeEnum.values() ) {
			if ( element.getName().equalsIgnoreCase(name)) {
				return element.getValue();
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
	public static String nameOfOrgType(String value) {
		for ( OrgTypeEnum element : OrgTypeEnum.values() ) {
			if ( element.getValue().equalsIgnoreCase(value) ) {
				return element.getName();
			}
		}
		return null;
	}
}
