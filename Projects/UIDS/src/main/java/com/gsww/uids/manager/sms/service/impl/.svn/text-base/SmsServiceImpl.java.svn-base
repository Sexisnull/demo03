package com.gsww.uids.manager.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.uids.manager.sms.entity.SmsRecord;
import com.gsww.uids.manager.sms.service.SmsService;

/**
 * 验证短信信息
 * @author simplife
 *
 */
@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	private HibernateBaseDao hibernateBaseDao;
	
	@Override
	@Transactional
	public void saveOrUpdate(SmsRecord info) {
		if (StringUtil.isBlank(info.getUuid())) {
			hibernateBaseDao.save(info);
		} else {
			hibernateBaseDao.update(info);
		}
	}

	@Override
	public SmsRecord getObj(String mobile, String code) {
		if (StringUtil.isBlank(mobile) || StringUtil.isBlank(code)) {
			return null;
		}
		StringBuilder query = new StringBuilder(" from ").append(SmsRecord.class.getName()).append(" where mobile = ? and code = ?");
		query.append(" order by sendTime desc");
		
		List<SmsRecord> list = hibernateBaseDao.findList(query.toString(), mobile, code);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	@Override
	public SmsRecord findById(String uuid) {
		if (StringUtil.isBlank(uuid)) {
			return new SmsRecord();
		}
		return hibernateBaseDao.getById(SmsRecord.class, uuid);
	}
	
	@Override
	@Transactional
	public void delete(String ids) {
		for (String id : ids.split(",")) {
			hibernateBaseDao.deleteById(SmsRecord.class, id);			
		}
	}
	
	@Scheduled(cron = "0 0 2 * * ? ")
	@Override
	@Transactional
	public void timeClear() {
		
		StringBuilder query = new StringBuilder(" from ").append(SmsRecord.class.getName()).append(" where failureTime <= ?");
		List<SmsRecord> records = hibernateBaseDao.findList(query.toString(), TimeHelper.getCurrentTime());
		for (SmsRecord record : records) {
			hibernateBaseDao.delete(record);
		}
	}
}
