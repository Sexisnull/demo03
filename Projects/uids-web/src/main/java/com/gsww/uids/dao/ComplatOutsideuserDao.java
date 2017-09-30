package com.gsww.uids.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.ComplatOutsideuser;

/**
 * Title: OutsideUserDao.java    
 * Description: 个人用户dao层
 * @author yangxia       
 * @created 2017年9月8日 上午10:37:24
*/
public interface ComplatOutsideuserDao extends 
	PagingAndSortingRepository<ComplatOutsideuser, Integer>,
	JpaSpecificationExecutor<ComplatOutsideuser> {
	/**
	 * 根据iid查找个人用户
	 * @param iid
	 * @return
	 */
	ComplatOutsideuser findByIid(Integer iid);
	
	/**
	 * 查找所用个人用户
	 */
	List<ComplatOutsideuser> findAll();
	
	/**
	 * 更新删除状态
	 */
	@Modifying
	@Query("update ComplatOutsideuser t set t.operSign = 3 where t.iid = ?1")
	public void updateOutsideuser(Integer iid);
	
	/**
     * @discription    根据登录名查询个人用户实体
     * @param loginName
     * @return
	 */
	ComplatOutsideuser findByLoginName(String loginName);

	ComplatOutsideuser findByMobile(String cellPhoneNum);

	ComplatOutsideuser findByPapersNumber(String idCard);

	@Modifying
	@Query("update ComplatOutsideuser t set t.loginTime = ?2 , t.loginIp=?3 where t.iid = ?1")
	int updateLoginIpAndLoginTime(int iid, Date time, String ip);

	@Modifying
	@Query("update ComplatOutsideuser t set t.operSign = ?2 where t.iid = ?1")
	int updateOpersign(String ids, int opersign);
	
	@Modifying
	@Query("update ComplatOutsideuser t set t.pwd = ?2 where t.iid = ?1")
	int updatePwd(int iid, String pwd);
}
