package com.gsww.uids.system.service;

import com.gsww.common.util.StringUtil;

/**
 * 数据业务层的抽象类：用于实现一些公共的功能
 * 
 * @author taolm
 *
 */
public abstract class AbstractCommonService {

	/**
	 * 为模糊查询产生相应的sql语句段
	 * 
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public String generateSearchSql(String fieldName, StringBuilder fieldValue) {
		
		// 匹配特殊字符_
		if (fieldValue.indexOf("_") >= 0) {
			// 保存原来的字段值
			String oldFieldValue = fieldValue.toString();
			
			// 清空原来的字段值
			fieldValue.delete(0, fieldValue.length());
			
			// 新的字段值
			fieldValue.append(oldFieldValue.replace("_", "#_"));
			
			// sql语句段
			return fieldName + " like ? ESCAPE '#'";
			
		} else {
			return fieldName + " like ?";
		}
	}
	
	/**
	 * 给手机号增加掩码
	 * 
	 * @param mobile
	 * @return
	 */
	public String addMaskToMobile(String mobile) {
		
		return addMask(mobile, 3, 4);
	}
	
	/**
	 * 给身份证号增加掩码
	 * 
	 * @param identity
	 * @return
	 */
	public String addMaskToIdentity(String identity) {
		
		return addMask(identity, 3, 4);
	}
	
	/**
	 * 给字符串增加掩码：保留前first和后last位，中间用掩码替换
	 * 
	 * @param origin
	 * @param first
	 * @param last
	 * @return
	 */
	private String addMask(String origin, int first, int last) {
		
		// 长度不足，返回原字符串
		if ( StringUtil.isBlank(origin) || origin.length() <= (first + last) ) {
			return origin;
		}
		
		// 前first位后last位是明文，中间用*代替
		int length = origin.length();
		String firstStr = origin.substring(0, first);
		String lastStr = origin.substring(length-last, length);
		String middleStr = "";
		for ( int i=0; i<length-first-last; i++ ) {
			middleStr += "*";
		}
		
		return (firstStr + middleStr + lastStr);
	}
}
