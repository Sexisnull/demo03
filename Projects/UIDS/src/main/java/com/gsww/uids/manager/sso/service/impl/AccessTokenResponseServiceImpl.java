package com.gsww.uids.manager.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.uids.manager.sso.entity.AccessTokenResponse;
import com.gsww.uids.manager.sso.service.AccessTokenResponseService;
import com.gsww.uids.manager.sys.entity.SystemBasicParam;
import com.gsww.uids.manager.sys.service.SysConfigService;

/**
 * accessToken应答相关的业务层实现
 * 
 * @author taolm
 *
 */
@Service
public class AccessTokenResponseServiceImpl implements AccessTokenResponseService {

	@Autowired
	private HibernateBaseDao hibernateBaseDao;
	
	@Autowired
	private SysConfigService sysConfigService;

	@Override
	@Transactional
	public AccessTokenResponse persist(AccessTokenResponse obj) {
		
		// 设置token有效期
		SystemBasicParam config = sysConfigService.getSystemBasicParam();
		obj.setExpiresIn((long)config.getTokenEffectiveTime());
		
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
			hibernateBaseDao.deleteById(AccessTokenResponse.class, id);
		}
	}

	@Override
	@Transactional
	public void remove(AccessTokenResponse obj) {
		if ( obj != null ) {
			hibernateBaseDao.delete(obj);	
		}
	}

	@Override
	public AccessTokenResponse getByAccessToken(String accessToken) {
		if ( StringUtil.isBlank(accessToken) ) {
			return null;
		}
		
		StringBuilder query = new StringBuilder(" from ").append(AccessTokenResponse.class.getName())
				.append(" where accessToken = ?");
		
		List<AccessTokenResponse> records = hibernateBaseDao.findList(query.toString(), accessToken);
		if ( records.isEmpty() ) {
			return null;
		} else {
			return records.get(0);
		}
	}

	@Override
	public boolean isAccessTokenValid(String accessToken, StringBuilder err) {
		AccessTokenResponse record = this.getByAccessToken(accessToken);
		return isAccessTokenValid(record, err);
	}

	@Override
	public boolean isAccessTokenValid(AccessTokenResponse obj, StringBuilder err) {
		if ( obj == null ) {
			err.append("错误的accessToken");
			return false;
		}
		
		// 计算自创建到现在的时长
		Date baseTime = TimeHelper.parseDateTime(obj.getResponseTime());
		Date curTime = new Date();
		long timeToNow = TimeHelper.secondsBetween(baseTime, curTime);
		
		// 如果已经超过设定的有效期，则无效，否则有效
		if ( timeToNow > obj.getExpiresIn().longValue() ) {
			err.append("过期的accessToken");
			return false;
		}

		return true;
	}

	@Override
	public List<AccessTokenResponse> getInvalid() {
		
		StringBuilder query = new StringBuilder(" from ").append(AccessTokenResponse.class.getName())
				.append(" where DATEDIFF(second, responseTime , ?) > expiresIn"
						+ " and DATEDIFF(second, responseTime , ?) > refreshExpiresIn");
		
		String endTime = TimeHelper.getCurrentTime();
		return hibernateBaseDao.findList(query.toString(), endTime, endTime);
	}

	@Override
	@Transactional
	public void refreshAccessToken(AccessTokenResponse obj, String newAccessToken, String newRefreshToken) {
		if ( obj.getExpiresIn().longValue() <= 0L ) {
			SystemBasicParam config = sysConfigService.getSystemBasicParam();
			obj.setExpiresIn((long)config.getTokenEffectiveTime());
		}
		obj.setAccessToken(newAccessToken);
		obj.setRefreshToken(newRefreshToken);
		obj.setResponseTime(TimeHelper.getCurrentTime());
		hibernateBaseDao.update(obj);
	}

	@Override
	public AccessTokenResponse getByRefreshToken(String refreshToken) {
		if ( StringUtil.isBlank(refreshToken) ) {
			return null;
		}
		
		StringBuilder query = new StringBuilder(" from ").append(AccessTokenResponse.class.getName())
				.append(" where refreshToken = ?");
		
		List<AccessTokenResponse> records = hibernateBaseDao.findList(query.toString(), refreshToken);
		if ( records.isEmpty() ) {
			return null;
		} else {
			return records.get(0);
		}
	}

	@Override
	@Transactional
	public void logout(AccessTokenResponse obj) {
		obj.setExpiresIn(0L);
		hibernateBaseDao.update(obj);
	}
}
