package com.gsww.uids.manager.sys.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.entity.PageObject;
import com.gsww.common.util.HttpUtil;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.sys.entity.BanLog;
import com.gsww.uids.manager.sys.service.BanService;

/**
 * 封停业务层实现
 * 
 * @author sunbw
 *
 */
@Service
public class BanServiceImpl implements BanService {
	
	private static final Logger logger = Logger.getLogger(AreaServiceImpl.class);

	@Autowired
	private HibernateBaseDao hibernateBaseDao;
	
	@Override
	@Transactional
	public boolean saveOrUpdate(BanLog info) {
		if (StringUtil.isBlank(info.getUuid())) {
			hibernateBaseDao.save(info);
			logger.info("封停日志保存成功");
		} else {
			hibernateBaseDao.update(info);
			logger.info("封停日志更新成功");
		}
		return true;
	}

	
	@Override
	public PageObject<BanLog> findPage(Integer currentPage, Integer pageSize,String searchText) {
		StringBuilder query = new StringBuilder(" from ").append(BanLog.class.getName()).append(" where isDelete = 0 ");
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.isNotBlank(searchText)){
			query.append(" and accountName like ? ");
			params.add("%" + searchText + "%");
		}
		query.append("order by lastLoginTime asc");
		return hibernateBaseDao.findPage(query.toString(), currentPage, pageSize,params);
	}


	@Override
	@Transactional
	public void delete(String ids) {
		BanLog info = null;
		for (String id : ids.split(",")) {
				info = hibernateBaseDao.getById(BanLog.class, id);
				info.setIsDelete(1);
				hibernateBaseDao.update(info);
				logger.info("逻辑删除成功");
		}
		
	}


	@Override
	public BanLog findByAccountName(String accountName) {
			
			StringBuilder query = new StringBuilder(" from ").append(BanLog.class.getName()).append(" where isDelete = 0 ");
			
			query.append(" and accountName = ? ");
			
			query.append("order by lastLoginTime desc");
			
			List<BanLog> logList = hibernateBaseDao.findList(query.toString(), accountName);
			if(logList.isEmpty()){
				return new BanLog();
			}
			return logList.get(0);
	}


	@Override
	public void writeBanLog(Account account, HttpServletRequest request) {
			BanLog banLog = findByAccountName(account.getName());
			
			String lastTime = banLog.getLastLoginTime();
			String currentTime = TimeHelper.getCurrentTime();
			
			if(StringUtil.isBlank(lastTime)){
				lastTime = currentTime;
			}
			
			try {
				int time = TimeHelper.minuteBetween(lastTime, currentTime);
				if( time> 30){
					banLog = new BanLog();
				}
			} catch (ParseException e) {
				logger.info("封停时间间隔异常{}"+e.getMessage());
			}
			
			String ip = HttpUtil.getIpAddress(request);
			
			banLog.setAccountName(account.getName());
			banLog.setAppName(account.getAppName());
			banLog.setLastLoginIp(ip);
			banLog.setLastLoginTime(currentTime);
			banLog.setRecentErrorTimes(banLog.getRecentErrorTimes() + 1);
			
			saveOrUpdate(banLog);
		
	}
}
