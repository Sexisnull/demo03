package com.gsww.uids.manager.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.sso.entity.AuthcodeResponse;
import com.gsww.uids.manager.sso.service.AuthcodeResponseService;
import com.gsww.uids.manager.sys.entity.SystemBasicParam;
import com.gsww.uids.manager.sys.service.SysConfigService;

/**
 * authcode应答相关的业务层实现
 * 
 * @author taolm
 *
 */
@Service
public class AuthcodeResponseServiceImpl implements AuthcodeResponseService {

	@Autowired
	private HibernateBaseDao hibernateBaseDao;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private SysConfigService sysConfigService;

	@Override
	@Transactional
	public AuthcodeResponse persist(AuthcodeResponse obj) {
		
		// 设置有效期
		SystemBasicParam config = sysConfigService.getSystemBasicParam();
		obj.setExpiresIn((long)config.getTicketEffectiveTime());
		
		hibernateBaseDao.save(obj);
		return obj;
	}

	@Override
	@Transactional
	public void batchRemove(String ids) {
		if ( StringUtil.isBlank(ids) ) {
			return ;
		}
		
		for (String id : ids.split(",")) {
			hibernateBaseDao.deleteById(AuthcodeResponse.class, id);
		}
	}

	@Override
	@Transactional
	public void remove(AuthcodeResponse obj) {
		if ( obj != null ) {
			hibernateBaseDao.delete(obj);
		}
	}

	@Override
	public AuthcodeResponse getByAuthcode(String authcode) {
		if ( StringUtil.isBlank(authcode) ) {
			return null;
		}
		
		StringBuilder query = new StringBuilder(" from ").append(AuthcodeResponse.class.getName())
				.append(" where authcode = ?");
		
		List<AuthcodeResponse> records = hibernateBaseDao.findList(query.toString(), authcode);
		if ( records.isEmpty() ) {
			return null;
		} else {
			return records.get(0);
		}
	}

	@Override
	public Account getAccountByAuthcode(String authcode) {
		
		// 查找登录的账号名
		AuthcodeResponse authcodeResponse = this.getByAuthcode(authcode);
		if ( authcodeResponse == null ) {
			return null;
		}
		String accountName = authcodeResponse.getRequest().getAccountName();
		
		// 查找账号
		Account account = accountService.findByClientIdAndAccountName(authcodeResponse.getRequest().getClientIdOfAccount(), accountName);
		return account;
	}

	@Override
	public boolean canUseAuthcode(String authcode, StringBuilder err) {
		AuthcodeResponse record = this.getByAuthcode(authcode);
		return canUseAuthcode(record, err);
	}

	@Override
	@Transactional
	public void useAuthcode(String authcode) {
		AuthcodeResponse record = this.getByAuthcode(authcode);
		useAuthcode(record);
	}

	@Override
	public boolean canUseAuthcode(AuthcodeResponse obj, StringBuilder err) {
		if ( obj == null ) {
			err.append("错误的授权码");
			return false;
		}
		
		if (obj.isAuthcodeUsed()) {
			err.append("已经使用过的授权码");
			return false;
		}
		
		// 计算自创建到现在的时长
		Date baseTime = TimeHelper.parseDateTime(obj.getResponseTime());
		Date curTime = new Date();
		long timeToNow = TimeHelper.secondsBetween(baseTime, curTime);
		
		// 如果已经超过设定的有效期，则无效，否则有效
		if ( timeToNow > obj.getExpiresIn().longValue() ) {
			err.append("过期的授权码");
			return false;
		}
		
		return true;
	}

	@Override
	@Transactional
	public void useAuthcode(AuthcodeResponse obj) {
		if ( obj == null ) {
			return ;
		}
		
		obj.setAuthcodeUsed(1);
		hibernateBaseDao.update(obj);		
	}

	@Override
	public List<AuthcodeResponse> getInvalid() {
		StringBuilder query = new StringBuilder(" from ").append(AuthcodeResponse.class.getName())
				.append(" where authcodeUsed = ? or DATEDIFF(second, responseTime , ?) > expiresIn");
		
		String curTime = TimeHelper.formatDateByFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		return hibernateBaseDao.findList(query.toString(), 1, curTime);
	}
}
