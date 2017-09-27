package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.ComplatCorporation;
import com.hanweb.complat.entity.Corporation;
/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2017-09-07 下午14:30:23</p>
 * <p>类描述 :   法人管理模块DAO层    </p>
 *
 *
 * @version 3.0.0
 * @author <a href=" ">yaoxi</a>
 */
public interface ComplatCorporationDao extends  PagingAndSortingRepository<ComplatCorporation, String>,JpaSpecificationExecutor<ComplatCorporation>{
	
	/**
	 * 根据主键查询
	 */
	public ComplatCorporation findByIid(Integer iid);
	
	/**
	 * 删除为假删
	 */
	@Modifying
	@Query("update ComplatCorporation t set t.operSign = 3 where t.iid = ?")
	public void updateCorporation(Integer iid);
	
	/**
     * @discription    根据登录名查找法人用户实体
     * @param loginName
     * @return
	 */
	ComplatCorporation findByLoginName(String loginName);
	
	@Modifying
	@Query("update ComplatCorporation t set t.pwd = ?2 where t.loginName = ?1")
	public boolean updatePwd(String loginName, String pwd);

	public ComplatCorporation findByRegNumber(String regnumber);

	public List<ComplatCorporation> findByOrgNumber(String orgnumber);
}
