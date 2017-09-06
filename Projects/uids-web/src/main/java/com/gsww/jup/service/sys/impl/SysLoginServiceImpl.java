/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.dao.sys.SysAccountDao;
import com.gsww.jup.dao.sys.SysRoleAcctRelDao;
import com.gsww.jup.dao.sys.SysRoleDao;
import com.gsww.jup.entity.sys.SysAccount;
import com.gsww.jup.entity.sys.SysRoleAcctRel;
import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.service.sys.SysLoginService;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2014年7月23日 下午11:36:33</p>
 * <p>类描述 : 用户登录实现类        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">lzxij</a>
 */
@Transactional
@Service("sysLoginService")
public class SysLoginServiceImpl implements SysLoginService {

	@Autowired
	private SysAccountDao sysAccountDao;
	@Autowired
	private SysRoleAcctRelDao sysRoleAcctRelDao;
	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Override
	public SysUserSession login(String userName, String password, String ip) throws Exception {
		
		List<SysAccount> userList = sysAccountDao.findByLoginAccountAndLoginPassword(userName,password);
		if (userList != null && userList.size() == 1) {
			SysAccount user = userList.get(0);
			SysUserSession sysUserSession = new SysUserSession();
			sysUserSession.setAccountId(user.getUserAcctId());
			sysUserSession.setUserName(user.getUserName());
/*			sysUserSession.setDeptId(user.getSysDepartment().getDeptId());
			sysUserSession.setDeptCode(user.getSysDepartment().getDeptCode());
			sysUserSession.setDeptName(user.getSysDepartment().getDeptName());*/
			sysUserSession.setUserIp(ip);
			List<SysRoleAcctRel> roleList = sysRoleAcctRelDao.findByUserAcctId(user.getUserAcctId());
			String roles = "";
			String roleNames = "";
			if(roleList!=null && roleList.size()>0){
				for(SysRoleAcctRel ra : roleList){
					roles += ra.getRoleId().toString()+",";
					roleNames += sysRoleDao.findByRoleId(ra.getRoleId()).getRoleName()+",";
				}
				roles = roles.substring(0, roles.length()-1);
				roleNames = roleNames.substring(0, roleNames.length()-1);
			}
			sysUserSession.setRoleIds(roles);
			sysUserSession.setUserSex(user.getUserSex());
			sysUserSession.setRoleNames(roleNames);
			sysUserSession.setUserState(user.getUserState());
			return sysUserSession;
		}
		return null;
	}

	@Override
	public SysUserSession login(String userName) throws Exception {
		List<SysAccount> userList = sysAccountDao.findByLoginAccount(userName);
		if (userList != null && userList.size() == 1) {
			SysAccount user = userList.get(0);
			SysUserSession sysUserSession = new SysUserSession();
			sysUserSession.setAccountId(user.getUserAcctId());
			sysUserSession.setUserName(user.getUserName());
			sysUserSession.setDeptId(user.getSysDepartment().getDeptId());
			sysUserSession.setDeptCode(user.getSysDepartment().getDeptCode());
			sysUserSession.setDeptName(user.getSysDepartment().getDeptName());
			List<SysRoleAcctRel> roleList = sysRoleAcctRelDao.findByUserAcctId(user.getUserAcctId());
			String roles = "";
			String roleNames = "";
			if(roleList!=null && roleList.size()>0){
				for(SysRoleAcctRel ra : roleList){
					roles += ra.getRoleId().toString()+",";
					roleNames += sysRoleDao.findByRoleId(ra.getRoleId()).getRoleName()+",";
				}
				roles = roles.substring(0, roles.length()-1);
				roleNames = roleNames.substring(0, roleNames.length()-1);
			}
			sysUserSession.setRoleIds(roles);
			sysUserSession.setUserSex(user.getUserSex());
			sysUserSession.setRoleNames(roleNames);
			sysUserSession.setUserState(user.getUserState());
			return sysUserSession;
		}
		return null;
	}

}
