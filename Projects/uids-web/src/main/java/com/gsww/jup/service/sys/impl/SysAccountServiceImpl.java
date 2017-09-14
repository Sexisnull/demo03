package com.gsww.jup.service.sys.impl;


import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.dao.sys.SysAccountDao;
import com.gsww.jup.dao.sys.SysRoleAcctRelDao;
import com.gsww.jup.dao.sys.SysRoleDao;
import com.gsww.jup.entity.sys.SysAccount;
import com.gsww.jup.entity.sys.SysDepartment;
import com.gsww.jup.entity.sys.SysRole;
import com.gsww.jup.entity.sys.SysRoleAcctRel;
import com.gsww.jup.service.sys.SysAccountService;
import com.gsww.jup.util.MD5;
@Transactional
@Service("sysAccountService")
public class SysAccountServiceImpl implements SysAccountService{
	@Autowired
	private SysAccountDao sysAccountDao;
	@Autowired
	private SysRoleAcctRelDao sysRoleAcctRelDao;
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	protected org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;
	
	@Override
	public String delete(SysAccount entity) throws Exception {		
		JSONObject jsonObject = JSONObject.fromObject(entity);  
		String logMsg=jsonObject.toString();
		//先删除用户角色表中的数据
		if(entity!=null){
		List<SysRoleAcctRel> list=sysRoleAcctRelDao.findByUserAcctId(entity.getUserAcctId());
		if(list.size()>0){
			for (SysRoleAcctRel sysRoleAcctRel : list) {
				sysRoleAcctRelDao.delete(sysRoleAcctRel);
			}			
		}
		//删除用户
		sysAccountDao.delete(entity);
		}
		return logMsg;
	}

	@Override
	public SysAccount findByKey(String pk) throws Exception {
		SysAccount sysAccount=sysAccountDao.findByUserAcctId(pk);
		return sysAccount;
	}

	@Override
	public Page<SysAccount> getUserPage(Specification<SysAccount> spec,
			PageRequest pageRequest) {
		return sysAccountDao.findAll(spec, pageRequest);
	}

	@Override
	public SysAccount save(SysAccount entity) throws Exception {
		return sysAccountDao.save(entity);
	}

	@Override
	public SysAccount findByLoginAccount(String loginAccount) throws Exception {
		List<SysAccount> list = sysAccountDao.findByLoginAccount(loginAccount);
		if(list.size()!=0){
			return sysAccountDao.findByLoginAccount(loginAccount).get(0);
		}else{
			return null;
		}
	}
	@Override
	public SysAccount queryLoginAccountIsUsed(String loginAccount) throws Exception {
		List<SysAccount> list = sysAccountDao.findByLoginAccount(loginAccount);
		if (CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public String getAccountLoginPassword(String loginAccount) throws Exception {
		String sysAccountPassword = null;
		try {
			if (findByLoginAccount(loginAccount)!=null) {
				sysAccountPassword = findByLoginAccount(loginAccount)
						.getLoginPassword();
				return sysAccountPassword;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sysAccountPassword;
	}

	@Override
	public void startAccount(String id) throws Exception {
		sysAccountDao.updateState("1",id);
	}

	@Override
	public void stopAccount(String id) throws Exception {
		sysAccountDao.updateState("0",id);
	}

	@Override
	public void initPassWord(String pwd,String id) throws Exception {
		MD5 m = new MD5();
		sysAccountDao.updatePassword(m.getMD5ofStr(pwd), id);
	}
	/**
	 * 保存用户角色关系表
	 * @param userId
	 * @param roleId
	 * @throws Exception
	 */
	@Override
	public void saveUserRole(String userId,String roleId) throws Exception{
		SysRoleAcctRel sysRoleAcctRel=new SysRoleAcctRel();
		sysRoleAcctRel.setUserAcctId(userId);
		sysRoleAcctRel.setRoleId(roleId);
		sysRoleAcctRelDao.save(sysRoleAcctRel);
	}
	/**
	 * 查询用户角色列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<SysRole> findAccountRoleList(String userId)throws Exception{
		List<SysRole> list=new ArrayList<SysRole>();
		List<SysRoleAcctRel> roleAcctRelList=new ArrayList<SysRoleAcctRel>();
		roleAcctRelList=sysRoleAcctRelDao.findByUserAcctId(userId);
		if(roleAcctRelList.size()>0){
			for (SysRoleAcctRel sysRoleAcctRel : roleAcctRelList) {
				SysRole sysRole=sysRoleDao.findByRoleId(sysRoleAcctRel.getRoleId());
				list.add(sysRole);
			}
		}
		return list;
	}
	/**
	 * 删除用户角色中间表数据
	 * @param userInfo
	 * @throws Exception
	 */
	public void deleteAccountRole(SysAccount entity) throws Exception{
		if(entity!=null){
			List<SysRoleAcctRel> list=sysRoleAcctRelDao.findByUserAcctId(entity.getUserAcctId());
			if(list.size()>0){
				for (SysRoleAcctRel sysRoleAcctRel : list) {
					sysRoleAcctRelDao.delete(sysRoleAcctRel);
				}			
			}
		}
	}
	
	public List<SysAccount> findSysAccountListByDept(SysDepartment sysDepartment)throws Exception{
		List<SysAccount> list=new ArrayList<SysAccount>();
		list=sysAccountDao.findBySysDepartment(sysDepartment);
		return list;
	}
	
	/**
	  修改密码	 
	 */
	@Override
	public boolean modifyPassword(String userAccount, String oldPassword,
		String newPassword) throws Exception {
		String sql="update sys_account set login_password=?3 where login_account=?1 and login_password=?2";
		int	result=	jdbcTemplate.update(sql,new Object[]{userAccount,oldPassword,newPassword});
		if(result>0){
			return true;	
		}else{
			return false;
		}	
	}
	
	/**
	 *同步用户 1
	 */
	@Override
	public SysAccount saveUser(SysAccount sysAccount){				
		return sysAccountDao.save(sysAccount);
	}
	/**
	 *同步用户 2
	 */
//	@Override
//	public SysUserApps saveSync(SysUserApps sysUserApps) {		
//		return sysUserAppsDao.save(sysUserApps);
//	}
	/**
	 *查找用户是否存在
	 */
	@Override
	public boolean getByLoginAccount(String loginAccount)
			throws Exception {
	List list= sysAccountDao.findByLoginAccount(loginAccount);
	if(list.size()>0){
		return true;	
	}else{
		return false;	
		}	
	}
}
