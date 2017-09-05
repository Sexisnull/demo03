package com.gsww.uids.manager.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.enums.AccountTypeEnum;
import com.gsww.common.enums.BussinessType;
import com.gsww.common.enums.UserTypeEnum;
import com.gsww.common.util.StringUtil;
import com.gsww.uids.manager.sys.entity.SystemAuthParam;
import com.gsww.uids.manager.sys.entity.SystemBasicParam;
import com.gsww.uids.manager.sys.entity.SystemEmailParam;
import com.gsww.uids.manager.sys.entity.SystemSMSParam;
import com.gsww.uids.manager.sys.service.SysConfigService;

/**
 * 配置参数
 * @author simplife
 *
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {
	
	@Autowired
	private HibernateBaseDao hibernateBaseDao;

	@Override
	@Transactional
	public boolean saveOrUpdate(SystemBasicParam info) {
		if (StringUtil.isBlank(info.getUuid())) {
			hibernateBaseDao.save(info);
		} else {
			hibernateBaseDao.update(info);
		}
		return true;
	}

	@Override
	@Transactional
	public boolean saveOrUpdate(SystemSMSParam info) {
		if (StringUtil.isBlank(info.getUuid())) {
			hibernateBaseDao.save(info);
		} else {
			hibernateBaseDao.update(info);
		}
		return true;
	}
	
	@Override
	@Transactional
	public boolean saveOrUpdate(SystemEmailParam info) {
		if (StringUtil.isBlank(info.getUuid())) {
			hibernateBaseDao.save(info);
		} else {
			hibernateBaseDao.update(info);
		}
		return true;
	}

	@Override
	public SystemBasicParam getSystemBasicParam() {
		StringBuilder query = new StringBuilder(" from ").append(SystemBasicParam.class.getName());	
		List<SystemBasicParam> list = hibernateBaseDao.findList(query.toString());
		return !list.isEmpty() ? list.get(0) : new SystemBasicParam();
	}

	@Override
	public SystemSMSParam getSystemSMSParam() {
		StringBuilder query = new StringBuilder(" from ").append(SystemSMSParam.class.getName());	
		List<SystemSMSParam> list = hibernateBaseDao.findList(query.toString());
		return !list.isEmpty() ? list.get(0) : new SystemSMSParam();
	}

	@Override
	public SystemEmailParam getSystemEmailParam() {
		StringBuilder query = new StringBuilder(" from ").append(SystemEmailParam.class.getName());	
		List<SystemEmailParam> list = hibernateBaseDao.findList(query.toString());
		return !list.isEmpty() ? list.get(0) : new SystemEmailParam();
	}

	@Override
	@Transactional
	public boolean saveOrUpdate(SystemAuthParam info) {
		if (StringUtil.isBlank(info.getUuid())) {
			hibernateBaseDao.save(info);
		} else {
			hibernateBaseDao.update(info);
		}
		return true;
	}

	@Override
	public SystemAuthParam getSystemAuthParam() {
		StringBuilder query = new StringBuilder(" from ").append(SystemAuthParam.class.getName());	
		List<SystemAuthParam> list = hibernateBaseDao.findList(query.toString());
		return !list.isEmpty() ? list.get(0) : new SystemAuthParam();
	}

	@Override
	public int getTimeoutOfSessionByAccountType(int type) {

		SystemBasicParam config = getSystemBasicParam();
		
		if (AccountTypeEnum.CORPORATE.getNumber() == type) {
			return config.getCorporateSessionTimeout();
		} else if (AccountTypeEnum.GOVERNMENT.getNumber() == type) {
			return config.getGovernmentSessionTimeout();
		} else if (AccountTypeEnum.PUBLIC.getNumber() == type) {
			return config.getPublicSessionTimeout();
		}
		
		return 0;
	}

	@Override
	public String getSMSBusinessIdByType(BussinessType type) {
		
		SystemSMSParam config = getSystemSMSParam();
		if ( BussinessType.REGISTER_ACCOUNT == type ) {
			return config.getAccountRegisterBussinessId();
		} else if ( BussinessType.NATURAL_REGISTER == type ) {
			return config.getNaturalRegisterBussinessId();
		} else if ( BussinessType.CORPORATE_REGISTER == type ) {
			return config.getCorporateRegisterBussinessId();
		} else if ( BussinessType.BIND_USER == type ) {
			return config.getBindUserBussinessId();
		} else if ( BussinessType.UNBIND_USER == type ) {
			return config.getUnbindUserBussinessId();
		} else if ( BussinessType.FETCH_PASSWORD == type ) {
			return config.getFindPwdBussinessId();
		} else if ( BussinessType.AUTH_MOBILE == type ) {
			return config.getAuthMobileBussinessId();
		} else if ( BussinessType.DELETE_ACCOUNT == type ) {
			return config.getDeleteAccountBussinessId();
		} else {
			return null;
		} 
	}

	@Override
	public String getSMSBusinessNameByType(BussinessType type) {
		
		SystemSMSParam config = getSystemSMSParam();
		if ( BussinessType.REGISTER_ACCOUNT == type ) {
			return config.getAccountRegisterBussinessName();
		} else if ( BussinessType.NATURAL_REGISTER == type ) {
			return config.getNaturalRegisterBussinessName();
		} else if ( BussinessType.CORPORATE_REGISTER == type ) {
			return config.getCorporateRegisterBussinessName();
		} else if ( BussinessType.BIND_USER == type ) {
			return config.getBindUserBussinessName();
		} else if ( BussinessType.UNBIND_USER == type ) {
			return config.getUnbindUserBussinessName();
		} else if ( BussinessType.FETCH_PASSWORD == type ) {
			return config.getFindPwdBussinessName();
		} else if ( BussinessType.AUTH_MOBILE == type ) {
			return config.getAuthMobileBussinessName();
		} else if ( BussinessType.DELETE_ACCOUNT == type ) {
			return config.getDeleteAccountBussinessName();
		} else {
			return null;
		} 
	}

	@Override
	public String getSMSBusinessMessageByType(BussinessType type) {
		
		SystemSMSParam config = getSystemSMSParam();
		if ( BussinessType.REGISTER_ACCOUNT == type ) {
			return config.getAccountRegisterBussinessMessage();
		} else if ( BussinessType.NATURAL_REGISTER == type ) {
			return config.getNaturalRegisterBussinessMessage();
		} else if ( BussinessType.CORPORATE_REGISTER == type ) {
			return config.getCorporateRegisterBussinessMessage();
		} else if ( BussinessType.BIND_USER == type ) {
			return config.getBindUserBussinessMessage();
		} else if ( BussinessType.UNBIND_USER == type ) {
			return config.getUnbindUserBussinessMessage();
		} else if ( BussinessType.FETCH_PASSWORD == type ) {
			return config.getFindPwdBussinessMessage();
		} else if ( BussinessType.AUTH_MOBILE == type ) {
			return config.getAuthMobileBussinessMessage();
		} else if ( BussinessType.DELETE_ACCOUNT == type ) {
			return config.getDeleteAccountBussinessMessage();
		} else {
			return null;
		} 
	}

	@Override
	public boolean checkAuthRealNameOpen(UserTypeEnum userType) {
		
		SystemAuthParam config = getSystemAuthParam();
		
		if ( UserTypeEnum.NATURAL == userType ) {
			return (config.getPersonalAuthSwitch() == 0);
		} else {
			return (config.getCorpAuthSwitch() == 0);
		}
	}
}
