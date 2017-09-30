package com.gsww.uids.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.PinyinUtil;
import com.hanweb.common.util.StringUtil;
import com.gsww.jup.util.StringHelper;
import com.gsww.uids.dao.ComplatCorporationDao;
import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.service.ComplatCorporationService;
import com.gsww.uids.util.exception.OperationException;



@Transactional
@Service("ComplatCorporationService")
public class ComplatCorporationServiceImpl implements ComplatCorporationService{
	private static Logger logger = LoggerFactory.getLogger(ComplatCorporationServiceImpl.class);
	
	@Autowired
	private ComplatCorporationDao complatCorporationDao;

	@Override
	public Page<ComplatCorporation> getCorporationPage(Specification<ComplatCorporation> spec, PageRequest pageRequest) {
		return complatCorporationDao.findAll(spec, pageRequest);
	}

	@Override
	public ComplatCorporation findByKey(Integer iid) throws Exception {
		
		return complatCorporationDao.findByIid(iid);
	}

	@Override
	public void save(ComplatCorporation corporation) {
		
		complatCorporationDao.save(corporation);
	}

	@Override
	public void updateCorporation(Integer iid) throws Exception {
		
		complatCorporationDao.updateCorporation(iid);
	}

	@Override
	public ComplatCorporation findByLoginName(String loginName) {
	    return this.complatCorporationDao.findByLoginName(loginName);
	}

	@Override
	public boolean updatePwd(String loginName, String pwd) {
	    if (StringUtil.isEmpty(loginName)) {
	        return false;
	      }
	    int i = this.complatCorporationDao.updatePwd(loginName, pwd);
	      return i==1;
	}
	
	  public ComplatCorporation findByLoginName1(String loginName)
	  {
	    return this.complatCorporationDao.findByLoginName(loginName);
	  }

	  public ComplatCorporation findByRegNumber(String regnumber){
		  
		    if (("".equals(regnumber)) || (regnumber.length() == 0)) {
		      return null;
		    }
		    return this.complatCorporationDao.findByRegNumber(regnumber);
		  }

	  public List<ComplatCorporation> findByOrgNumber(String orgnumber){
	    return this.complatCorporationDao.findByOrgNumber(orgnumber);
	  }
	  
	@Override
	public synchronized ComplatCorporation checkUserLogin(String loginName, String pwd, String ip) {
		
		ComplatCorporation corporation = null;
	    corporation = this.complatCorporationDao.findByLoginName(loginName);
	      if (corporation == null) {
		        corporation = findByRegNumber(loginName);
		        if (corporation == null) {
		          List<ComplatCorporation> corporationList = findByOrgNumber(loginName);
		          if (CollectionUtils.isNotEmpty(corporationList)) {
		            corporation = (ComplatCorporation)corporationList.get(0);
		          }
		        }
	      }
	      
	      if (corporation != null) {
	          if (corporation.getEnable().intValue() == 0) {
	            logger.error("login.isnotallowed");
	          }
	          String password = Md5Util.md5decode(corporation.getPwd());
	          if (!StringUtils.equals(password, pwd)) {
	            corporation = null;
	          } else {
	            corporation.setLoginIp(ip);
	            corporation.setLoginTime(new Date());
	          }
	        }
	      
	      return corporation;
	}

	@Override
	public void updateLoginIpAndLoginTime(ComplatCorporation corporation) {
		complatCorporationDao.save(corporation);
	}

	@Override
	public ComplatCorporation findByRegNum(String regNum) {
		if("".equals(regNum) || regNum.length()==0){
			return null;
		}
		return complatCorporationDao.findByRegNumber(regNum);
	}
	
	@Override
	public ComplatCorporation findByOrgName(String inputByGuest) {
		return complatCorporationDao.findByOrgNumber(inputByGuest).get(0);
	}
	
	@Override
	public ComplatCorporation findByManyWay(String inputByGuest) {
		ComplatCorporation cor= null;
		if(StringHelper.isNotBlack(inputByGuest)){
			cor = findByLoginName(inputByGuest);
			if(cor==null){
				cor = findByRegNum(inputByGuest);
				if(cor==null){
					cor = findByOrgName(inputByGuest);
				}
			}
		}
		return cor;
	}

	@Override
	public ComplatCorporation findByLoginNameIsUsed(String loginName) {
		return complatCorporationDao.findByLoginName(loginName);

	}

	@Override
	public boolean updateIsUpload(int intValue, int i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modify(ComplatCorporation corporation) {
		return complatCorporationDao.save(corporation) != null;
	}

	@Override
	public boolean add(ComplatCorporation corporation) throws OperationException {
		if (corporation == null) {
			return false;
		}

		ComplatCorporation cor1 = findByRegNumber(corporation.getRegNumber());
		if (cor1 != null) {
			throw new OperationException("工商注册号已存在,请重新设置！");
		}

		if(!StringUtil.isEmpty(corporation.getOrgNumber())){
			List cor2 = findByOrgNumber(corporation.getOrgNumber());
			if ((cor2 != null) && (cor2.size() > 0)) {
				throw new OperationException("组织机构代码已存在,请重新设置！");
			}
		}

		ComplatCorporation complatCorporation = this.complatCorporationDao.findByLoginName(corporation.getLoginName());
		if(null != complatCorporation){
			throw new OperationException("用户名已存在,请重新设置！");
		}
		
		/*int num = 0;
		num = this.complatCorporationDao.findByLoginName(corporation.getLoginName()).getIid();
		if (num > 0) {
			throw new OperationException("用户名已存在,请重新设置！");
		}*/

		convertFormat(corporation);
		corporation.setPwd(Md5Util.md5encode(corporation.getPwd()));
		corporation.setCreateTime(new Date());
		corporation.setPinyin(PinyinUtil.getHeadByString(corporation.getName()));
		corporation.setOperSign(Integer.valueOf(1));
		corporation.setSynState(Integer.valueOf(0));
		int iid = 0;

		//JsonResult jr = this.realNameAuthService.verifyCorRealName(corporation);

		if (true) {
		//if (jr.isSuccess()) {
			corporation.setRegNumber(corporation.getRegNumber().toUpperCase());
			//iid = ((Integer) this.complatCorporationDao.insert(corporation)).intValue();
			iid = this.complatCorporationDao.save(corporation).getIid();
		} else {
			throw new OperationException("实名认证失败");
		}
		if (iid > 0) {
			//com.hanweb.common.util.CacheUtil.setValue(corporation.getLoginName(), corporation, "corusers");
			return true;
		}
		return false;
	}
	
	private void convertFormat(ComplatCorporation corporation) {
		if (StringUtil.isNotEmpty(corporation.getOrgNumber()))
			corporation.setOrgNumber(corporation.getOrgNumber().toUpperCase());
	}

	@Override
	public void delete(ComplatCorporation corporation) {
		complatCorporationDao.delete(corporation);
	}
}
