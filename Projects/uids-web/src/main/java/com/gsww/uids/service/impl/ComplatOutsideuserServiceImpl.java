package com.gsww.uids.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.hanweb.common.util.Md5Util;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.controller.ComplatRoleController;
import com.gsww.uids.dao.ComplatOutsideuserDao;
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.service.ComplatOutsideuserService;

/**
 * 
 * Title: OutsideUserServiceImpl.java Description: 个人用户Service实现层
 * 
 * @author yangxia
 * @created 2017年9月8日 上午10:40:50
 */
@Transactional
@Service("outsideUserService")
public class ComplatOutsideuserServiceImpl implements ComplatOutsideuserService {
	private static Logger logger = LoggerFactory.getLogger(ComplatOutsideuserServiceImpl.class);
	
	@Autowired
	private ComplatOutsideuserDao outsideUserDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Page<ComplatOutsideuser> getOutsideUserPage(Specification<ComplatOutsideuser> spec, PageRequest pageRequest) {
		return outsideUserDao.findAll(spec, pageRequest);
	}

	@Override
	public ComplatOutsideuser findByKey(Integer iid) {
		return outsideUserDao.findByIid(iid);
	}

	@Override
	public void save(ComplatOutsideuser outsideUser) {
		outsideUserDao.save(outsideUser);
	}

	@Override
	public void delete(ComplatOutsideuser outsideUser) {
		outsideUserDao.delete(outsideUser);
	}
	
	@Override
	public List<ComplatOutsideuser> findAll() {
		return outsideUserDao.findAll();
	}
	
	@Override
	public List<Map<String, Object>> findByNameOrPinYin(String keyword) {
		String sql = "SELECT iid, name, loginname FROM complat_outsideuser" +
				" WHERE name LIKE '%"+keyword+"%' OR pinyin LIKE '%"+keyword+"%' ";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public void delete(Integer iid) {
		outsideUserDao.updateOutsideuser(iid);
	}

	@Override
	public ComplatOutsideuser findByLoginNameIsUsed(String loginName) {
		ComplatOutsideuser oList = outsideUserDao.findByLoginName(loginName);
		return oList;
	}

	@Override
	public ComplatOutsideuser findByMobile(String cellPhoneNum) {
	    return this.outsideUserDao.findByMobile(cellPhoneNum);
	}

	@Override
	public ComplatOutsideuser findByIdCard(String IdCard) {
		return this.outsideUserDao.findByPapersNumber(IdCard);
	}

	@Override
	public ComplatOutsideuser findByLoginName(String loginName) {
		return this.outsideUserDao.findByLoginName(loginName);
	}

	@Override
	public ComplatOutsideuser checkUserLogin(HttpSession session, String loginName, String password, String ip) {
		ComplatOutsideuser outsideUser = this.outsideUserDao.findByLoginName(loginName);

	    if (outsideUser == null) {
	      return null;
	    }
	    if (outsideUser.getEnable().intValue() == 0) {
	      logger.error("login.isnotallowed");
	    }
	    String cellphoneDynamicPwdMadeByJava = null;
	    if (session.getAttribute("cellphoneDynamicPwdMadeByJava") == null) {
	      logger.error("您输入的用户名或密码错误，或手机动态密码超时需重新获取！");
	    }

	    cellphoneDynamicPwdMadeByJava = ((String)session.getAttribute("cellphoneDynamicPwdMadeByJava")).trim();

	    if (StringUtils.equals(password, cellphoneDynamicPwdMadeByJava)) {
	      outsideUser.setLoginIp(ip);
	      outsideUser.setLoginTime(new Date());
	    } else {
	      logger.error("您输入的用户名或密码错误，或手机动态密码超时需重新获取！");
	    }

	    return outsideUser;
	}

	@Override
	public boolean updateLoginIpAndLoginTime(ComplatOutsideuser user) {
	    if (user == null) {
	        return false;
	      }
	      String ip = user.getLoginIp().trim();
	      Date time = user.getLoginTime();
	      if ((ip == null) || ("".equals(ip)) || (time == null)) {
	        return false;
	      }
	      return updateLoginIpAndLoginTime(user.getIid().intValue(), time, ip);
	}
	
	  public boolean updateLoginIpAndLoginTime(int iid, Date time, String ip)
	  {
	    boolean isSuccess = false;

	    if ((ip == null) || ("".equals(ip)) || (time == null)) {
	      return isSuccess;
	    }
	    isSuccess = this.outsideUserDao.updateLoginIpAndLoginTime(iid, time, ip);

	    if (isSuccess) {
	      isSuccess = modifyOpersign(String.valueOf(iid), 0);
	    }

	    return isSuccess;
	  }
	  
	  public boolean modifyOpersign(String ids, int opersign)
	  {
	    if ((ids == null) || (ids.length() <= 0)) {
	      return false;
	    }
	    return this.outsideUserDao.updateOpersign(ids, opersign);
	  }

	@Override
	public boolean updatePwd(int iid, String pwd) {
		return this.outsideUserDao.updatePwd(iid, pwd);
	}

	@Override
	public ComplatOutsideuser checkUserLogin(String loginName, String pwd, String ip) {
		ComplatOutsideuser outsideUser = this.outsideUserDao.findByLoginName(loginName);
	    if (outsideUser != null)
	    {
	      if (outsideUser.getEnable().intValue() == 0) {
	        logger.error("login.isnotallowed");
	      }
	      String password = Md5Util.md5decode(outsideUser.getPwd());
	      if (StringUtils.equals(password, pwd))
	      {
	        outsideUser.setLoginIp(ip);
	        outsideUser.setLoginTime(new Date());
	      }
	      else {
	        outsideUser = null;
	      }
	    }
	    return outsideUser;
	}
}
