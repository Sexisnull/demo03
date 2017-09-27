package com.gsww.uids.service.impl;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.constant.ComplatSettings;
import com.gsww.uids.dao.AuthLogDAO;
import com.gsww.uids.entity.JisApplication;
import com.gsww.uids.entity.JisAuthlog;
import com.gsww.uids.service.AuthLogService;
import com.gsww.uids.service.JisApplicationService;
import com.gsww.jup.util.MD5;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("AuthLogService")
public class AuthLogServiceImpl implements AuthLogService{

	private static Logger logger = LoggerFactory.getLogger(AuthLogServiceImpl.class);
	
  @Autowired
  private JisApplicationService appService;

  @Autowired
  private AuthLogDAO authLogDAO;

  public String add(Object userEntity, String appmark, int userType)
  {
    if ((userEntity == null) || (appmark == null) || (StringUtil.isEmpty(appmark))) {
      return "";
    }
    JisAuthlog jisAuthLog = new JisAuthlog();
    String LoginName = "";

    String ticket = "";
    if (userType == 1) {
      ComplatOutsideuser user = (ComplatOutsideuser)userEntity;
      jisAuthLog.setUserid(user.getIid());
      jisAuthLog.setLoginname(user.getLoginName());
      jisAuthLog.setUsername(user.getName());
      LoginName = user.getLoginName();
    } else {
      ComplatCorporation user = (ComplatCorporation)userEntity;
      jisAuthLog.setUserid(user.getIid());
      jisAuthLog.setLoginname(user.getLoginName());
      jisAuthLog.setUsername(user.getName());
      LoginName = user.getLoginName();
    }
    jisAuthLog.setUsertype(Integer.valueOf(userType));
    jisAuthLog.setAuthtype(Integer.valueOf(1));
    jisAuthLog.setState(Integer.valueOf(0));

	try {
		JisApplication appLication = this.appService.findByMark(appmark);
		System.out.println("application ====" + appLication);
		if (appLication != null) {
			jisAuthLog.setAppid(appLication.getIid());
			jisAuthLog.setAppmark(appmark);
			Date createTime = new Date();
			jisAuthLog.setCreatetime(new Timestamp(createTime.getTime()));
			
			ComplatSettings settings = ComplatSettings.getSettings();
			String authEffectiveTime = settings.getTicketEffectiveTime();
			long time = createTime.getTime() + NumberUtil.getLong(authEffectiveTime) * 1000L;
			Date outTime = new Date(time);
			jisAuthLog.setOuttickettime(new Timestamp(outTime.getTime()));
			
			ticket = MD5.encodeMd5(appmark + appLication.getEncryptKey() + LoginName + 
					createTime);
			jisAuthLog.setTicket(ticket);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
    return ticket;
  }

  public JisAuthlog findByTicket(String ticket, int userType)
  {
    if ((StringUtil.isEmpty(ticket)) || (userType == 0)) {
      return null;
    }
	return authLogDAO.findByTicket(ticket,userType);
  }

  public boolean modifyJisAuthLog(JisAuthlog jisAuthLog)
  {
    if (NumberUtil.getInt(jisAuthLog.getIid()) == 0) {
      return false;
    }
    return this.authLogDAO.save(jisAuthLog) != null;
  }

  public JisAuthlog findByToken(String token, int userType)
  {
    if (StringUtil.isEmpty(token)) {
      return null;
    }
    return authLogDAO.findByToken(token,userType);
  }

  public boolean addJisAuthLog(JisAuthlog jisAuthLog)
  {
    if (jisAuthLog == null) {
      return false;
    }
    jisAuthLog = this.authLogDAO.save(jisAuthLog);
    int iid = jisAuthLog.getIid();
    return iid > 0;
  }

  public boolean removeAuthLogByIds(String ids)
  {
    List<Integer> idsLsit = StringUtil.toIntegerList(ids, ",");
    for(int i = 0;i<idsLsit.get(i);i++){
    	this.authLogDAO.delete(idsLsit.get(i));
    }
    return true;
  }
}