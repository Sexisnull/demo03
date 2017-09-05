package com.gsww.uids.system.controller;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * 自定义注解 拦截controller
 * @author jinwei
 *
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Documented
public @interface SystemLog {
	String description() default ""; 	//描述
	String module() default "";   		//模块
	String actionType() default "";		//1 添加 2 修改 3 删除
}  
