package com.gsww.jup.service.sys;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.jup.entity.sys.SysAccount;
import com.gsww.jup.entity.sys.SysDepartment;
import com.gsww.jup.entity.sys.SysRole;
/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2014-6-08 下午10:35:48</p>
 * <p>类描述 : 账号管理模块接口        </p>
 *
 *
 * @version 3.0.0
 * @author <a href=" ">zhangtb</a>
 */
public interface SysAccountService {
	/**
	 * 保存或修改对象
	 * @param userInfo
	 * @throws Exception
	 */
	public SysAccount save(SysAccount entity) throws Exception;
	
	/**
	 * 删除单个对象
	 * @param userInfo
	 * @throws Exception
	 */
	public String delete(SysAccount entity) throws Exception;

	
	/**
	 * 根据主键查找对象
	 * @param pk
	 * @throws Exception
	 */
	public SysAccount findByKey(String pk) throws Exception;
	

	/**
	 * 
	 * 方法描述 : 分页描述
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<SysAccount> getUserPage(Specification<SysAccount> spec, PageRequest pageRequest);

	/**
	 * 根据用户账号查找对象
	 * @param loginAccount
	 * @throws Exception
	 */
	public SysAccount findByLoginAccount(String loginAccount) throws Exception;
	
	/**
	 * 判断账号是否存在
	 * @param loginAccount
	 * @return
	 * @throws Exception
	 */
	public SysAccount queryLoginAccountIsUsed(String loginAccount)throws Exception;
	
	/**
	 * 获取密码
	 * @param loginAccount
	 * @return
	 * @throws Exception
	 */
	public String getAccountLoginPassword(String loginAccount) throws Exception;
	
	/**
	 * 根据主键启用账号
	 * @param id
	 * @throws Exception
	 */
	public void startAccount(String id) throws Exception;
	/**
	 * 根据主键停用账号
	 * @param id
	 * @throws Exception
	 */
	public void stopAccount(String id) throws Exception;
	/**
	 * 初始化密码
	 * @param pwd
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void initPassWord(String pwd,String id) throws Exception;
	/**
	 * 保存用户角色关系表
	 * @param userId
	 * @param roleId
	 * @throws Exception
	 */
	public void saveUserRole(String userId,String roleId) throws Exception;
	/**
	 * 查询用户角色列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<SysRole> findAccountRoleList(String userId)throws Exception;
	/**
	 * 删除用户角色中间表数据
	 * @param userInfo
	 * @throws Exception
	 */
	public void deleteAccountRole(SysAccount entity) throws Exception;
	
	public List<SysAccount> findSysAccountListByDept(SysDepartment sysDepartment)throws Exception;
	
	/**
	 * 修改密码
	 * @param userAccount
	 * @param oldPassword
	 * @param newPassword
	 * @throws Exception
	 * @author anhang
	 */
	public boolean modifyPassword(String userAccount,String oldPassword,String newPassword)throws Exception;
	
	/**
	 * 同步用户
	 * @param sysAccount
	 * @throws Exception
	 * @author anhang
	 */
	public SysAccount saveUser(SysAccount sysAccount);
	/**
	 * 同步用户
	 * @param sysUserApps
	 * @throws Exception
	 * @author anhang
	 */
//	public SysUserApps saveSync(SysUserApps sysUserApps);	
	/**
	 * 查找用户是否存在
	 * @param loginAccount
	 * @throws Exception
	 * @author anhang
	 */
	public boolean getByLoginAccount(String loginAccount) throws Exception;
		
}
