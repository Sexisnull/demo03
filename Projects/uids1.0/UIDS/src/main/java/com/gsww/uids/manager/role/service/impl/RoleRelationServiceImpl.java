package com.gsww.uids.manager.role.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.entity.PageObject;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeUtil;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.role.entity.RoleRelation;
import com.gsww.uids.manager.role.service.RoleRelationService;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.service.UserService;

/**
 * 用户角色业务层实现
 * 
 * @author taolm
 *
 */
@Service
public class RoleRelationServiceImpl implements RoleRelationService {
	
	@Autowired
	private HibernateBaseDao hibernateBaseDao;
	
	@Autowired
	private UserService userService;
	
	@Override
	public PageObject<RoleRelation> findPage(String userId, Integer currentPage, Integer pageSize) {
		
		StringBuilder query = new StringBuilder(" from ").append(RoleRelation.class.getName())
				.append(" where del = 0 and user.uuid = ? order by createTime desc");
		return hibernateBaseDao.findPage(query.toString(), currentPage, pageSize, userId);
	}
	
	@Override
	public RoleRelation findById(String id) {
		if (StringUtil.isBlank(id)) {
			return new RoleRelation();
		}
		return hibernateBaseDao.getById(RoleRelation.class, id);
	}
	
	@Override
	@Transactional
	public RoleRelation saveOrUpdate(RoleRelation info) {
		if (StringUtil.isBlank(info.getUuid())) {
			info.setCreateTime(TimeUtil.getExactCurrentTime());
			hibernateBaseDao.save(info);
		} else {
			hibernateBaseDao.update(info);
		}
		
		return info;
	}
	
	@Override
	@Transactional
	public void delete(String ids) {
		for (String id : ids.split(",")) {
			RoleRelation relation = this.findById(id);
			relation.setDel(1);
			hibernateBaseDao.update(relation);			
		}
	}
	
	@Override
	public boolean checkUnique(RoleRelation roleRelation, StringBuilder errInfo) {
		
		// 查找角色关系
		List<RoleRelation> relations = this.findListByParams(roleRelation.getUser().getUuid(), roleRelation.getOrg().getUuid(), 
				roleRelation.getRole().getUuid());

		// 验证唯一性
		if ( !relations.isEmpty() && !relations.get(0).getUuid().equalsIgnoreCase(roleRelation.getUuid()) ) {
			errInfo.append("此角色关系已经存在");
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean checkDelete(String ids, StringBuilder errInfo) {
		return true;
	}
	
	@Override
	public List<RoleRelation> findListByParams(String userId, String orgId, String roleId) {
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder(" from ").append(RoleRelation.class.getName()).append(" where del = 0");
		
		if ( StringUtil.isNotBlank(userId) ) {
			query.append(" and user.uuid = ?");
			params.add(userId);
		}
		
		if ( StringUtil.isNotBlank(orgId) ) {
			query.append(" and org.uuid = ?");
			params.add(orgId);
		}
		
		if ( StringUtil.isNotBlank(roleId) ) {
			query.append(" and role.uuid = ?");
			params.add(roleId);
		}		

		query.append(" order by createTime desc");
		return hibernateBaseDao.findList(query.toString(), params);
	}

	@Override
	public List<Organization> getOrganizationsOfCurrentLoginUserAs(String roleMark) {
		// 获取当前登录的用户
		User user = userService.getCurrentLoginUser();
		if ( user == null ) {
			return new ArrayList<Organization>();
		}
		
		// 获取用户的当前角色关系
		List<RoleRelation> relations = findByUserAndRole(user.getUuid(), roleMark);
		
		// 提取出机构
		List<Organization> organizations = new ArrayList<Organization>();
		for ( RoleRelation relation : relations ) {
			organizations.add(relation.getOrg());
		}
		
		return organizations;
	}
	
	/**
	 * 根据用户和角色查询
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	private List<RoleRelation> findByUserAndRole(String userId, String roleMark) {
		
		StringBuilder query = new StringBuilder(" from ").append(RoleRelation.class.getName())
				.append(" where del = 0")
				.append(" and user.uuid = ? and role.mark = ?");
		
		return hibernateBaseDao.findList(query.toString(), userId, roleMark);
	}
}
