/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.entity.sys.SysUserSession;
import com.gsww.jup.service.sys.SysLoginService;
import com.gsww.uids.dao.ComplatRoleDao;
import com.gsww.uids.dao.ComplatUserDao;
import com.gsww.uids.entity.ComplatRole;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.entity.JisRoleobject;
import com.gsww.uids.service.JisRoleobjectService;
import com.hanweb.common.util.Md5Util;

/**
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * 公司名称 : 中国电信甘肃万维公司
 * </p>
 * <p>
 * 项目名称 : jup-core
 * </p>
 * <p>
 * 创建时间 : 2014年7月23日 下午11:36:33
 * </p>
 * <p>
 * 类描述 : 用户登录实现类
 * </p>
 * 
 * 
 * @version 1.0.0
 * @author <a href=" ">lzxij</a>
 */
@Transactional
@Service("sysLoginService")
public class SysLoginServiceImpl implements SysLoginService {
	@Autowired
	private ComplatUserDao complatUserDao;
	@Autowired
	private JisRoleobjectService jidRoleobjectService;
	@Autowired
	private ComplatRoleDao complatRoleDao;

	@Override
	public SysUserSession login(String userName, String password,
			String groupId, String ip) throws Exception {

		List<ComplatUser> userList = complatUserDao.findByLoginnameAndGroupid(
				userName, Integer.parseInt(groupId));
		if (userList != null && userList.size() == 1) {
			ComplatUser user = userList.get(0);
			String pwd = Md5Util.md5decode(user.getPwd());
			if (!pwd.equals(password)) {
				return null;
			} else {
				SysUserSession sysUserSession = new SysUserSession();
				sysUserSession.setLoginAccount(user.getLoginname());
				sysUserSession.setAccountId(user.getIid() + "");
				sysUserSession.setUserName(user.getName());
				sysUserSession.setDeptId(user.getGroupid()+"");
				/*
				 * sysUserSession.setDeptId(user.getSysDepartment().getDeptId());
				 * sysUserSession
				 * .setDeptCode(user.getSysDepartment().getDeptCode());
				 * sysUserSession
				 * .setDeptName(user.getSysDepartment().getDeptName());
				 */
				
				sysUserSession.setUserIp(ip);
				List<JisRoleobject> roleList =jidRoleobjectService.findByObjectIdAndType(user.getIid(),0);
				if(roleList.isEmpty()){
					roleList =jidRoleobjectService.findByObjectIdAndType(user.getGroupid(),2);
				}
				String roles = "";
				String roleTypes="";
				String roleNames = "";
				if (roleList != null && roleList.size() > 0) {
					for (JisRoleobject ra : roleList) {
						ComplatRole role = complatRoleDao.findByIid(ra.getRoleid());
						roles += ra.getRoleid().toString() + ",";
						roleTypes +=role.getType().toString() + ",";
						roleNames += role.getName()+ ",";
					}
					roles = roles.substring(0, roles.length() - 1);
					roleTypes = roleTypes.substring(0, roleTypes.length() - 1);
					roleNames = roleNames.substring(0, roleNames.length() - 1);
				}
				sysUserSession.setRoleIds(roles);
				sysUserSession.setRoleTypes(roleTypes);
				sysUserSession.setUserSex(user.getSex() + "");
				sysUserSession.setRoleNames(roleNames);
				sysUserSession.setUserState(user.getEnable() + "");
				return sysUserSession;
			}
		}
		return null;
	}

	@Override
	public SysUserSession login(String userName) throws Exception {
		/*
		 * List<SysAccount> userList =
		 * complatUserDao.findByLoginAccount(userName); if (userList != null &&
		 * userList.size() == 1) { SysAccount user = userList.get(0);
		 * SysUserSession sysUserSession = new SysUserSession();
		 * sysUserSession.setAccountId(user.getUserAcctId());
		 * sysUserSession.setUserName(user.getUserName());
		 * sysUserSession.setDeptId(user.getSysDepartment().getDeptId());
		 * sysUserSession.setDeptCode(user.getSysDepartment().getDeptCode());
		 * sysUserSession.setDeptName(user.getSysDepartment().getDeptName());
		 * List<SysRoleAcctRel> roleList =
		 * sysRoleAcctRelDao.findByUserAcctId(user.getUserAcctId()); String
		 * roles = ""; String roleNames = ""; if(roleList!=null &&
		 * roleList.size()>0){ for(SysRoleAcctRel ra : roleList){ roles +=
		 * ra.getRoleId().toString()+","; roleNames +=
		 * sysRoleDao.findByRoleId(ra.getRoleId()).getRoleName()+","; } roles =
		 * roles.substring(0, roles.length()-1); roleNames =
		 * roleNames.substring(0, roleNames.length()-1); }
		 * sysUserSession.setRoleIds(roles);
		 * sysUserSession.setUserSex(user.getUserSex());
		 * sysUserSession.setRoleNames(roleNames);
		 * sysUserSession.setUserState(user.getUserState()); return
		 * sysUserSession; } return null;
		 */
		return null;
	}

}
