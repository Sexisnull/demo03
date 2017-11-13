package com.gsww.uids.service.impl;

import com.gsww.uids.dao.ComplatOutsideuserDao;
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.service.ComplatOutsideuserService;
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

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	public Integer findByLoginNameIsUsed(String loginName) {
		String sql= "select count(1) from complat_outsideuser where loginname = '"+loginName+"' and Opersign <> 3";
		return jdbcTemplate.queryForObject(sql, Integer.class);
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
	      return updateLoginIpAndLoginTime(user,time,ip);
	}
	
	  public boolean updateLoginIpAndLoginTime(ComplatOutsideuser user, Date time, String ip)
	  {
	    boolean isSuccess = false;

	    if ((ip == null) || ("".equals(ip)) || (time == null)) {
	      return isSuccess;
	    }
	    
	    ComplatOutsideuser users = outsideUserDao.save(user);
	    if(users!=null){
	    	isSuccess = true;
	    }

	    if (isSuccess) {
	      isSuccess = modifyOpersign(String.valueOf(user.getIid()), 0);
	    }

	    return isSuccess;
	  }
	  
	  public boolean modifyOpersign(String ids, int opersign)
	  {
	    if ((ids == null) || (ids.length() <= 0)) {
	      return false;
	    }
	    int i =this.outsideUserDao.updateOpersign(ids, opersign);
	    return i==1;
	  }

	@Override
	public boolean updatePwd(int iid, String pwd) {
		int i = this.outsideUserDao.updatePwd(iid, pwd);
		return i==1;
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

	@Override
	public boolean insert(ComplatOutsideuser outsideUser) {
		return outsideUserDao.save(outsideUser) !=null;
	}

	@Override
	public boolean modifyAuthing(ComplatOutsideuser outsideUser) {
		return false;
	}

	@Override
	public Integer findByMobileIsUsed(String mobile) {
		String sql= "select count(1) from complat_outsideuser where mobile = '"+mobile+"' and Opersign <> 3";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public Integer findByIdCardIsUsed(String papersNumber) {
		
		String sql= "select count(1) from complat_outsideuser where papersnumber = '"+papersNumber+"' and Opersign <> 3";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

}
