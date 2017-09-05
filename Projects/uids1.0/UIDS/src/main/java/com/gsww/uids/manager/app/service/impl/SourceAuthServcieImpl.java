package com.gsww.uids.manager.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.entity.PageObject;
import com.gsww.common.util.JsonUtils;
import com.gsww.common.util.ObjectJsonValueProcessor;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.app.entity.AppResource;
import com.gsww.uids.manager.app.entity.AppResourceAuth;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ResourceService;
import com.gsww.uids.manager.app.service.SourceAuthService;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.entity.OrganizationGroup;
import com.gsww.uids.manager.org.entity.OrganizationRelationType;
import com.gsww.uids.manager.role.entity.Role;
import com.gsww.uids.manager.role.entity.RoleRelation;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * 资源授权业务层
 * 
 * @author lich
 *
 */
@Service("sourceAuthServcie")
public class SourceAuthServcieImpl implements SourceAuthService {
	private static final Logger logger = Logger.getLogger(ResourceServiceImpl.class);

	@Autowired
	private HibernateBaseDao hibernateBaseDao;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ResourceService resourceService;

	@Override
	public String findPageBySource(Integer currentPage, Integer pageSize, String sourceId) {
		List<Object> params = new ArrayList<Object>();
		PageObject<AppResourceAuth> pageList = new PageObject<>();
		try {
			params.add(sourceId);
			StringBuilder query = new StringBuilder(" from ").append(AppResourceAuth.class.getName())
					.append(" where isDelete = 0 and resource.uuid = ? order by createTime DESC");
			pageList = hibernateBaseDao.findPage(query.toString(), currentPage, pageSize, params);
			if (pageList != null) {
				JsonConfig config = new JsonConfig();
				config.registerJsonValueProcessor(Organization.class,new ObjectJsonValueProcessor(new String[] { "shortName" }, Organization.class));
				config.registerJsonValueProcessor(OrganizationGroup.class,new ObjectJsonValueProcessor(new String[] { "name" }, OrganizationGroup.class));
				config.registerJsonValueProcessor(Role.class,new ObjectJsonValueProcessor(new String[] { "name" }, Role.class));
				config.registerJsonValueProcessor(OrganizationRelationType.class,new ObjectJsonValueProcessor(new String[] { "name" }, OrganizationRelationType.class));
				config.registerJsonValueProcessor(AppResource.class,new ObjectJsonValueProcessor(new String[] { "name" }, AppResource.class));
				JSONArray jsonArray = JSONArray.fromObject(pageList, config);
				return new JsonUtils().datagridJson(jsonArray);
			}
		} catch (Exception e) {
			logger.info("授权信息获取失败！" + e);
		}
		return null;
	}

	@Override
	public String findPageByRole(Integer currentPage, Integer pageSize,
			String roleId) {
		
		StringBuilder query = new StringBuilder(" from ").append(AppResourceAuth.class.getName())
				.append(" where isDelete = 0 and role.uuid = ? order by createTime DESC");
		return hibernateBaseDao.findPage(query.toString(), currentPage, pageSize, roleId).toJSONString();
	}
	
	@Override
	public AppResourceAuth findSourceAuthById(String authId) {
		if (StringUtil.isBlank(authId)) {
			return new AppResourceAuth();
		}
		return hibernateBaseDao.getById(AppResourceAuth.class, authId);
	}

	@Override
	public boolean authDelete(String ids) {
		
		for ( String id : ids.split(",") ) {
			AppResourceAuth sourceAuth = this.findSourceAuthById(id);
			sourceAuth.setIsDelete(1);
			hibernateBaseDao.update(sourceAuth);
		}

		logger.info("逻辑删除资源授权成功");
		
		return true;
	}

	@Override
	public boolean saveOrUpdateSourceAuth(AppResourceAuth sourceAuth) {
		try {
			if (StringUtil.isBlank(sourceAuth.getUuid())) {
				logger.info("授权信息新增！！！！！！！！！！！");
				sourceAuth.setCreateTime(TimeHelper.getExactCurrentTime());
				hibernateBaseDao.save(sourceAuth);

			} else {
				logger.info("授权信息编辑！！！！！！！！！！！");
				hibernateBaseDao.update(sourceAuth);
			}
		} catch (Exception e) {
			logger.info("新增或者更新异常：" + e);
		}
		return true;
	}

	@Override
	public List<AppResourceAuth> findListByParames(String orgId, String orgGroupId, String roleId, String sourceId) {
		StringBuilder query = new StringBuilder(" from ").append(AppResourceAuth.class.getName()).append(" where isDelete = 0 ");
		List<Object> params = new ArrayList<Object>();

		if(StringUtil.isNotBlank(orgId)){
			query.append(" and org.uuid = ? ");
			params.add(orgId);
		}
		if(StringUtil.isNotBlank(orgGroupId)){
			query.append(" and orgGroupId.uuid = ? ");
			params.add(orgGroupId);
		}
		if(StringUtil.isNotBlank(roleId)){
			query.append(" and role.uuid = ? ");
			params.add(roleId);
		}
		if(StringUtil.isNotBlank(sourceId)){
			query.append(" and resource.uuid = ? ");
			params.add(sourceId);
		}
		return hibernateBaseDao.findList(query.toString(), params);
	}
	
	@Override
	public List<AppResourceAuth> findByAccount(String accountId) {
		
		// 所有资源权限
		List<AppResourceAuth> auths = new ArrayList<AppResourceAuth>();
		
		// 查找账号
		Account account = accountService.findById(accountId);
		if ( StringUtil.isBlank(accountId) ) {
			return auths;
		}
		
		// 用户角色关系
		Set<RoleRelation> roleRelations = null;
		if ( account.hasBindUser() ) {
			// 如果账号绑定了用户，则查询用户的授权
			roleRelations = account.getUser().getRoleRelationSet();			
		}
		
		if( roleRelations == null ){
			return auths;
		}
		
		// 所有角色关系所拥有的资源权限
		for (RoleRelation role : roleRelations) {
			List<AppResourceAuth> roleAuths = findResourceAuthOfRoleRelation(role);
			auths.addAll(roleAuths);
		}
		
		// 返回资源权限
		return auths;
	}

	@Override
	public List<AppResourceAuth> findAuthByAppRes(String sourceId) {
		StringBuilder query = new StringBuilder(" from ").append(AppResourceAuth.class.getName())
				.append(" where isDelete = 0 and resource.uuid = ?");
		return hibernateBaseDao.findList(query.toString(), sourceId);
	}
	
	@Override
	public boolean checkDelete(String ids, StringBuilder errInfo) {
		return true;
	}
	
	@Override
	public List<AppResourceAuth> findListByParams(String orgId, String orgGroupId, String roleId, String resourceId) {
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder query = new StringBuilder(" from ").append(AppResourceAuth.class.getName())
				.append(" where isDelete = 0");
		
		if ( StringUtil.isNotBlank(orgId) ) {
			query.append(" and org is not null and org.uuid = ?");
			params.add(orgId);
		}
		
		if ( StringUtil.isNotBlank(orgGroupId) ) {
			query.append(" and orgGroupId is not null and orgGroupId.uuid = ?");
			params.add(orgGroupId);
		}
		
		if ( StringUtil.isNotBlank(roleId) ) {
			query.append(" and role is not null and role.uuid = ?");
			params.add(roleId);
		}
		
		if ( StringUtil.isNotBlank(resourceId) ) {
			query.append(" and resource.uuid = ?");
			params.add(resourceId);
		}
		
		query.append(" order by createTime desc");
		
		return hibernateBaseDao.findList(query.toString(), params);
	}

	/**
	 * 根据角色关系查询授权信息
	 * @param relation
	 * @return
	 */
	private List<AppResourceAuth> findResourceAuthOfRoleRelation(RoleRelation relation) {
		
		// 角色关系的授权资源
		List<AppResourceAuth> auths = new ArrayList<AppResourceAuth>();
		
		// 单个机构授权
		List<AppResourceAuth> authsOfSingleOrg = findAuthBySingleOrg(relation);
		auths.addAll(authsOfSingleOrg);
		
		// 机构分组授权
		List<AppResourceAuth> authsOfOrgGroup = findAuthByOrgGroup(relation);
		auths.addAll(authsOfOrgGroup);
		
		return auths;
	}
	
	/**
	 * 按照单个机构授权的方式下角色关系的授权资源
	 * 
	 * @param relation
	 * @return
	 */
	private List<AppResourceAuth> findAuthBySingleOrg(RoleRelation relation) {
		
		StringBuilder query = new StringBuilder(" from ").append(AppResourceAuth.class.getName())
				.append(" where isDelete = 0 ")
				.append(" and type = ?")
				.append(" and org.uuid = ?")
				.append(" and (role is null or role.uuid = ?)");
		
		List<Object> params = new ArrayList<Object>();
		params.add(AppResourceAuth.ROLE_GRANT);
		params.add(relation.getOrgId());	
		params.add(relation.getRoleId());		
		
		return hibernateBaseDao.findList(query.toString(), params);
	}
	
	/**
	 * 按照机构分组授权的方式下角色关系的授权资源
	 * 
	 * @param relation
	 * @return
	 */
	private List<AppResourceAuth> findAuthByOrgGroup(RoleRelation relation) {
		
		StringBuilder query = new StringBuilder(" from ").append(AppResourceAuth.class.getName())
				.append(" where isDelete = 0 ")
				.append(" and type = ?")
				.append(" and (role is null or role.uuid = ?)");
		
		List<Object> params = new ArrayList<Object>();
		params.add(AppResourceAuth.GROUP_GRANT);	
		params.add(relation.getRoleId());	
		
		// 查找所有机构所在分组
		Set<OrganizationGroup> orgGroups = relation.getOrg().getGroupSet();
		if ( !orgGroups.isEmpty() ) {
			query.append(" and orgGroupId.uuid in ");
			query.append("(");
			String split = "?";
			for ( OrganizationGroup orgGroup : orgGroups ) {
				query.append(split);
				params.add(orgGroup.getUuid());
				split = ", ?";			
			}
			query.append(")");
		}
		
		return hibernateBaseDao.findList(query.toString(), params);
	}

	@Override
	public boolean checkUnique(AppResourceAuth reAuth,String orgId, String orgGroupId, String roleId,
			String resourceId,StringBuilder errInfo) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder(" from ").append(AppResourceAuth.class.getName())
				.append(" where isDelete = 0");
		
		if ( StringUtil.isNotBlank(orgId) ) {
			query.append(" and org is not null and org.uuid = ?");
			params.add(orgId);
		}
		
		if ( StringUtil.isNotBlank(orgGroupId) ) {
			query.append(" and orgGroupId is not null and orgGroupId.uuid = ?");
			params.add(orgGroupId);
		}
		
		if ( StringUtil.isNotBlank(roleId) ) {
			query.append(" and role is not null and role.uuid = ?");
			params.add(roleId);
		}
		if ( StringUtil.isNotBlank(resourceId) ) {
			query.append(" and resource.uuid = ?");
			params.add(resourceId);
		}
		List<AppResourceAuth> list =  hibernateBaseDao.findList(query.toString(), params);
		if (StringUtil.isBlank(reAuth.getUuid())) {
			if (list.size() > 0) {
				errInfo.append(String.format("资源【%s】下已经存在该权限", list.get(0).getResource().getName()));
				return false;
			}
		} else {
			if (list.size() > 0) {
				for (int i =0; i<list.size(); i++){
					if (!list.get(i).getUuid().equalsIgnoreCase(reAuth.getUuid())) {
						errInfo.append(String.format("资源【%s】下已经存在该权限", list.get(0).getResource().getName()));
						return false;	
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean canAccessResource(Account account, Application app, String url) {

		if ( account == null || app == null || StringUtil.isBlank(url) ) {
			return false;
		}
		
		// 查找资源
		AppResource resource = resourceService.findByAppAndUrl(app.getUuid(), url);
		if ( resource == null ) {
			return false;
		}
		
		// 如果是账号所属应用下的资源，直接可以访问
		if ( account.getApp().getUuid().equalsIgnoreCase(app.getUuid()) ) {
			return true;
		}
		
		// 查找账号的所有资源授权
		List<AppResourceAuth> auths = this.findByAccount(account.getUuid());
		// 查找授权资源下，有没有要验证的资源
		for ( AppResourceAuth auth : auths ) {
			AppResource res = auth.getResource();
			if ( res.getUuid().equalsIgnoreCase(resource.getUuid()) ) {
				return true;
			}
		}	
		
		return false;
	}
}