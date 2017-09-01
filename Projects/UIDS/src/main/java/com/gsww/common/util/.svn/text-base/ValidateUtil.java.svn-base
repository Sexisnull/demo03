package com.gsww.common.util;

import java.lang.reflect.Field;

import javax.persistence.Column;

import com.gsww.uids.manager.account.entity.Account;

public class ValidateUtil{
	
	/**
	 * 长度校验
	 * @param str 检测的字符串
	 * @param column 字段名
	 * @param entity 实体类
	 * @return
	 */
	public static <T> boolean validateLenght(String str,String columnName,T entity){
        @SuppressWarnings("unchecked")
		Class<? extends Account> cls = (Class<? extends Account>) entity.getClass();
		try {
			Field tableColumn = cls.getDeclaredField(columnName);
			Column columnAnnotation = (Column) tableColumn.getAnnotation(Column.class);
			int len = columnAnnotation.length();
			int t = str.length()-len;
			if(t == 0){
				return true;
			}else if(t < 0 ){
				return true;
			}else{
				return false;
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 非空校验
	 * @param str 被检验字符串
	 * @param columnName 对应列名
	 * @param account 对象
	 * @return
	 */
	public static <T> boolean validateNullable(String str,String columnName,T entity){
		@SuppressWarnings("unchecked")
		Class<? extends Account> cls = (Class<? extends Account>) entity.getClass();
		try {
			Field tableColumn = cls.getDeclaredField(columnName);
			Column columnAnnotation = (Column) tableColumn.getAnnotation(Column.class);
			boolean flag = columnAnnotation.nullable();
			
			if(!flag && str == null || str == ""){
				return false;
			}else{
				return true;
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
