package com.gsww.uids.manager.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.entity.PageObject;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.uids.manager.sys.entity.OperationLog;
import com.gsww.uids.manager.sys.service.LogService;


/**
 * 日志业务层实现
 * 
 * @author taolm
 *
 */
@Service
public class LogServiceImpl implements LogService {
	
	private static final Logger logger = Logger.getLogger(AreaServiceImpl.class);
	
	@Autowired
	private HibernateBaseDao hibernateBaseDao;
	
	@Override
	@Transactional
	public boolean saveOrUpdate(OperationLog info) {
		if (StringUtil.isBlank(info.getUuid())) {
			info.setTime(TimeHelper.getExactCurrentTime());
			hibernateBaseDao.save(info);
			logger.info(info.getLogDesc());
		} else {
			hibernateBaseDao.update(info);
			logger.info(info.getLogDesc());
		}
		return true;
	}

	@Override
	public OperationLog findById(String id) {
		if (StringUtil.isBlank(id)) {
			return new OperationLog();
		}
		return hibernateBaseDao.getById(OperationLog.class, id);
	}
	
	@Override
	public List<OperationLog> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageObject<OperationLog> findPage(Integer page, Integer size,String module,
			String logDesc,String startTime,String endTime){
		StringBuilder query = new StringBuilder(" from ").append(OperationLog.class.getName()).append(" where 1 = 1");
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.isNotBlank(module) && !"0".equals(module)){
			query.append(" and module = ? ");
			params.add(module);
		}
		if(StringUtil.isNotBlank(logDesc)){
			query.append(" and logDesc like ? ");
			params.add("%"+logDesc+"%");
		}
		if(StringUtil.isNotBlank(startTime)){
			query.append(" and time >= ? ");
			params.add(startTime);
		}
		if(StringUtil.isNotBlank(endTime)){
			query.append(" and time <= ? ");
			params.add(endTime);
		}
		query.append(" order by time desc");
		return hibernateBaseDao.findPage(query.toString(), page, size, params);
	}

	@Override
	public String findLastTimeLoginTimeByAccountName(String accountName) {
		List<Object> params = new ArrayList<Object>();

		StringBuilder query = new StringBuilder(" from ").append(OperationLog.class.getName()).append(" where 1 = 1");
		
		if(StringUtil.isNotBlank(accountName)){
			query.append(" and accountName = ? ");
			params.add(accountName);
		}
		query.append(" order by time desc");
		List<OperationLog> logList = hibernateBaseDao.findList(query.toString(), params);
		String time = "";
		if(logList.size() == 1){
			time = logList.get(0).getTime();
		}else{
			time = logList.get(1).getTime();
		}
		
		return time;
	}

}
