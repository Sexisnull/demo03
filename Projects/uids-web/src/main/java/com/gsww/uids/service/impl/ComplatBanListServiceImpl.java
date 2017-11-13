package com.gsww.uids.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.constant.JisSettings;
import com.gsww.uids.dao.ComplatBanListDao;
import com.gsww.uids.dao.ComplatUserDao;
import com.gsww.uids.entity.ComplatBanlist;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.service.ComplatBanListService;

@Transactional
@Service("ComplatBanListService")
public class ComplatBanListServiceImpl  implements ComplatBanListService{
	@Autowired
	private ComplatBanListDao complatBanListDao;
	@Autowired
	private JisSettings jisSettings;
	@Autowired
	private ComplatUserDao complatUserDao;
	
	@Override
	public Page<ComplatBanlist> getComplatBanPage(
			Specification<ComplatBanlist> spec, PageRequest pageRequest) {
		
		return complatBanListDao.findAll(spec, pageRequest);
	}

	@Override
	public List<ComplatBanlist> findComplatbanList() throws Exception {
		
		return complatBanListDao.findAll();
	}

	@Override
	public void delete(ComplatBanlist entity) throws Exception {
		complatBanListDao.delete(entity);
	}

	@Override
	public ComplatBanlist findByIid(Integer iid) throws Exception {
		return complatBanListDao.findByIid(iid);
	}
	
	@Override
	public ComplatBanlist checkLoginTimes(String loginName, String ipAddr, Integer userType,String groupId) {
		List<ComplatUser> list = complatUserDao.findByLoginnameAndGroupid(loginName, Integer.parseInt(groupId));
		if(list==null || list.size()==0){
			return null;
		}
		ComplatUser user = list.get(0);
		if(user==null){
			return null;
		}
		ComplatBanlist ban = complatBanListDao.findByLoginnameAndIpaddrAndUsertype(user.getLoginallname(),ipAddr,userType);
		if(ban!=null){
			Timestamp loginDate = ban.getLogindate();
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(loginDate);
		    calendar.set(12, calendar.get(12) + jisSettings.getBanTimes());
		    Date now = new Date();
		      boolean isBan = true;
		      if (calendar.getTime().compareTo(now) <= 0) {
		        isBan = false;
		      }
		      if ((isBan) && 
		        (ban.getLogintimes().intValue() >= jisSettings.getLoginError())) {
		    	  ban.setCanLogin(false);
		      } else if (isBan) {
		    	  ban.setCanLogin(true);
		      } else {
		    	  ban.setCanLogin(true);
		    	  ban.setLogintimes(Integer.valueOf(0));
		      }
			
		}else{
			ban = new ComplatBanlist();
			ban.setLoginname(user.getLoginallname());
		    ban.setIpaddr(ipAddr);
		    ban.setCanLogin(true);
		    ban.setLogintimes(Integer.valueOf(0));
		    ban.setUsertype(userType);
		}
		
		return ban;
	}
	
	@Override
	public void removeById(Integer iid) {
		complatBanListDao.delete(iid);	
	}
	
	@Override
	public void save(ComplatBanlist banList) {
		complatBanListDao.save(banList);
		
	}
}
