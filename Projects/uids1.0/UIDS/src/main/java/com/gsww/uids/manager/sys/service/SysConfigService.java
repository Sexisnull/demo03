package com.gsww.uids.manager.sys.service;

import org.springframework.stereotype.Service;

import com.gsww.common.enums.BussinessType;
import com.gsww.common.enums.UserTypeEnum;
import com.gsww.uids.manager.sys.entity.SystemAuthParam;
import com.gsww.uids.manager.sys.entity.SystemBasicParam;
import com.gsww.uids.manager.sys.entity.SystemEmailParam;
import com.gsww.uids.manager.sys.entity.SystemSMSParam;

/**
 * 系统参数业务层
 * @author simplife
 *
 */
@Service
public interface SysConfigService {
	
	/**
	 * 保存、修改-基础参数
	 * @param info
	 * @return
	 */
	boolean saveOrUpdate(SystemBasicParam info);
	
	/**
	 * 保存、修改-注册、密码找回、动态密码发送短信相关参数
	 * @param info
	 * @return
	 */
	boolean saveOrUpdate(SystemSMSParam info);
	
	/**
	 * 保存、修改-邮箱参数
	 * @param info
	 * @return
	 */
	boolean saveOrUpdate(SystemEmailParam info);
	
	/**
	 * 保存、修改-认证信息
	 * @param info
	 * @return
	 */
	boolean saveOrUpdate(SystemAuthParam info);
	
	/**
	 * 获取对象-基础参数
	 * @param info
	 * @return
	 */
	SystemBasicParam getSystemBasicParam();
	
	/**
	 * 获取对象-注册、密码找回、动态密码发送短信相关参数
	 * @param info
	 * @return
	 */
	SystemSMSParam getSystemSMSParam();
	
	/**
	 * 获取邮件参数
	 * @return
	 */
	SystemEmailParam getSystemEmailParam();
	
	/**
	 * 获取认证参数
	 * @return
	 */
	SystemAuthParam getSystemAuthParam();
	
	/**
	 * 获取各类账号的前台登录登录超时时间
	 * 
	 * @param type
	 * @return
	 */
	int getTimeoutOfSessionByAccountType(int type);
	
	/** 根据短信业务类型获取业务id
	 * 
	 * @param type
	 * @return
	 */
	String getSMSBusinessIdByType(BussinessType type);
	
	/** 根据短信业务类型获取业务名称
	 * 
	 * @param type
	 * @return
	 */
	String getSMSBusinessNameByType(BussinessType type);
	
	/** 根据短信业务类型获取短信内容
	 * 
	 * @param type
	 * @return
	 */
	String getSMSBusinessMessageByType(BussinessType type);
	
	/**
	 * 判断用户的实名认证是否开启
	 * 
	 * @param userType
	 * @return
	 */
	boolean checkAuthRealNameOpen(UserTypeEnum userType);
}
