package com.gsww.jup.dao.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.SysAccount;
import com.gsww.jup.entity.sys.SysDepartment;
/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2014-6-08 下午10:30:23</p>
 * <p>类描述 :   账号管理模块DAO层    </p>
 *
 *
 * @version 3.0.0
 * @author <a href=" ">zhangtb</a>
 */
public interface SysAccountDao extends
PagingAndSortingRepository<SysAccount, String>,
JpaSpecificationExecutor<SysAccount>{
	/**
	 * 根据登录账号查询账号信息
	 */
	List<SysAccount> findByLoginAccount(String loginAccount);
	/**
	 * 根据账号主键查询账号信息
	 * @param accountId
	 * @return
	 */
	SysAccount findByUserAcctId(String userAcctId);
	
	/**
	 * 
	 * 方法描述 : 通过账号id更新该账号状态
	 * @param state 状态
	 * @param id  账号id
	 */
	@Modifying
	@Query("update SysAccount t set t.userState =?1 where t.userAcctId =?2")
	void updateState(String state, String id);
	
	/**
	 * 
	 * 方法描述 : 通过账号id初始化密码
	 * @param loginPassword MD5加密过的密码
	 * @param id  账号id
	 */
	@Modifying
	@Query("update SysAccount t set t.loginPassword =? where t.userAcctId =?")
	void updatePassword(String loginPassword, String id);
	
	/**
	 * 方法描述 : 根据用户ID和密码查询用户
	 * @param userName
	 * @param password
	 * @return
	 */
	List<SysAccount> findByLoginAccountAndLoginPassword(String userName,String password);
	
	
	
	List<SysAccount> findBySysDepartment(SysDepartment sysDepartment);
}
