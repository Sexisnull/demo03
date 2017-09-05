package com.gsww.uids.manager.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.entity.PageObject;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.common.util.TimeUtil;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.app.service.SourceAuthService;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.entity.OrganizationErrorTemp;
import com.gsww.uids.manager.org.entity.OrganizationMergeTemp;
import com.gsww.uids.manager.org.entity.OrganizationRelation;
import com.gsww.uids.manager.org.service.OrganizationRelationService;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.role.service.RoleRelationService;
import com.gsww.uids.manager.sys.service.AreaService;
import com.gsww.uids.manager.user.service.UserService;
import com.gsww.uids.system.service.AbstractCommonService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 机构管理业务员层
 * @author jinwei
 *
 */
@Service
public class OrganizationServiceImpl extends AbstractCommonService implements OrganizationService {

	public static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	
	@Autowired
    private HibernateBaseDao hibernateBaseDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRelationService relationService;
	
	@Autowired
	private OrganizationRelationService orgRelationService;
	
	@Autowired
	private ApplicationService appService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private SourceAuthService sourceAuthService;
	
	@Override
	@Transactional
	public Organization saveOrUpdate(Organization info) {
		if (StringUtil.isBlank(info.getUuid())) {
			// 生成机构编码
			String code = generateCodeOfOrganization(info);
			info.setCode(code);
			
			// 设置时间
			info.setCreateTime(TimeHelper.getExactCurrentTime());
			
			// 保存
			hibernateBaseDao.save(info);
		} else {
			hibernateBaseDao.update(info);
		}
		return info;
	}
	
	@Override
	@Transactional
	public void delete(String ids) {
		if (StringUtil.isNotBlank(ids)) {
			Organization org = null;
			for (String id : ids.split(",")) {
				org = findById(id);
				if (null != org) {
					//逻辑删除
					org.setDel(1);
					hibernateBaseDao.update(org);
				}
				List<Organization> childList = findChild(org.getParent().getUuid());
				
				if(childList.size() < 1){
					Organization parent = findById(org.getParent().getUuid());
					parent.setParentIs(0);
					hibernateBaseDao.update(parent);
				}
			}
		}
	}
	
	@Override
	@Transactional
	public Organization findById(String id) {
		if (StringUtil.isBlank(id)) {
			return new Organization();
		}
		return hibernateBaseDao.getById(Organization.class, id);
	}

	@Override
	@Transactional
	public PageObject<Organization> findPage(Integer currentPage, Integer pageSize, String parentId,
			String orgName, String nodeType, String areaCode, String areaType, String code) {
	
		if (StringUtil.isBlank(parentId)){
			parentId = getRootOrganization().getUuid();
		}
		
		try {
			List<Object> params = new ArrayList<Object>();
			StringBuilder query = new StringBuilder(" from ").append(Organization.class.getName()).append(" where del = 0 ");
			if(StringUtil.isNotBlank(orgName)){
				StringBuilder newOrgName = new StringBuilder();
				newOrgName = new StringBuilder(orgName);
				query.append(" and ").append(generateSearchSql("fullName", newOrgName));
				params.add("%" + newOrgName + "%");
			}
			if(StringUtil.isNotBlank(areaCode)){
				StringBuilder newAreaCode = new StringBuilder();
				newAreaCode = new StringBuilder(areaCode);
				query.append(" and ").append(generateSearchSql("area.code", newAreaCode));
				params.add("%" + newAreaCode + "%");
			}
			if(StringUtil.isNotBlank(code)){
				StringBuilder newOrgCode = new StringBuilder();
				newOrgCode = new StringBuilder(code);
				query.append(" and ").append(generateSearchSql("code", newOrgCode));
				params.add("%" + newOrgCode + "%");
			}
			query.append("and parent.uuid = ? ");
					
			params.add(parentId );
			
			if(StringUtil.isNotBlank(areaType) && !"0".endsWith(areaType) && !"0".equals(nodeType)){
				query.append(" and area.level = ? ");
				params.add(areaType );
			}
			
			if(StringUtil.isNotBlank(nodeType) && !"0".equals(nodeType)){
				query.append(" and type = ? ");
				params.add(nodeType);
			}
			
			query.append(" order by sequence asc ");
			
			return hibernateBaseDao.findPage(query.toString(), currentPage, pageSize, params);
			
		} catch (Exception e) {
			logger.info("机构列表信息获取失败！" + e);
			return new PageObject<Organization>();
		}
	}


	@Override
	public List<Organization> findChild(String pid) {

		StringBuilder query = new StringBuilder(" from ").append(Organization.class.getName())
				.append(" where del = 0 ")
				.append(" and parent.uuid = ? ")
				.append(" order by sequence asc");
		
		List<Object> params = new ArrayList<Object>();
		params.add(pid);
		
		return hibernateBaseDao.findList(query.toString(), params);
	}
	
	

	@Override
	@Transactional
	public List<Organization> findAll() {

		StringBuilder query = new StringBuilder(" from ").append(Organization.class.getName()).append(" where del = 0 ");
		query.append(" order by sequence asc");
		
		return hibernateBaseDao.findList(query.toString());
	}
	
	@Override
	public Organization getByName(String name) {
		StringBuilder query = new StringBuilder(" from ").append(Organization.class.getName());
		query.append(" where del = 0 and shortName = ?");
		return hibernateBaseDao.getByHql(query.toString(), name);
	}
	
	@Override
	public Organization getRootOrganization() {
		StringBuilder query = new StringBuilder(" from ").append(Organization.class.getName())
				.append(" where del = 0 and parent is null");

		List<Organization> rootOrgs = hibernateBaseDao.findList(query.toString());
		if ( rootOrgs.isEmpty() ) {
			return new Organization();
		} else {
			return rootOrgs.get(0);
		}
	}

	@Override
	public List<Organization> findListByParamer(String areaId) {
		StringBuilder query = new StringBuilder(" from ").append(Organization.class.getName()).append(" where del = 0 ");
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.isNotBlank(areaId)){
			query.append(" and area.uuid = ? ");
			params.add(areaId);
		}
		return hibernateBaseDao.findList(query.toString(), params);
	}

	@Override
	public List<Organization> findListByFullName(String fullName) {

		if(StringUtil.isBlank(fullName)){
			return new  ArrayList<Organization>();
		}
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder(" from ").append(Organization.class.getName()).append(" where del = 0 and ");
		
		StringBuilder newOrgName = new StringBuilder(fullName);
		query.append(generateSearchSql("fullName", newOrgName));
		params.add("%" + newOrgName + "%");
		
		query.append(" order by sequence asc");
		
		return hibernateBaseDao.findList(query.toString(), params);
	}
	
	@Override
	public boolean checkDelete(String ids, StringBuilder errInfo) {
		
		for ( String id : ids.split(",") ) {
			// 要删除的机构
			Organization org = this.findById(id);
			
			// 是否被子机构引用
			if ( this.findChild(id).size() > 0 ){
				errInfo.append(String.format("机构【%s】下存在子节点使用，不能删除！", org.getShortName()));
				return false;
			}
			
			// 是否被机构上下级关系引用
			if ( orgRelationService.findListByParams(null, null, id).size() >0 ) {
				String err = "在";
				OrganizationRelation relation = orgRelationService.findListByParams(null, null, id).get(0);
				err += relation.getClassify().getName()+"下存在下级机构";
				errInfo.append(String.format("机构【%s】"+err+"，不能删除！", org.getShortName()));
				return false;
			}
			
			// 是否被资源授权引用
			if( sourceAuthService.findListByParames(id, null, null, null).size() > 0 ){
				errInfo.append(String.format("机构【%s】被资源授权引用，不能删除！", org.getShortName()));
				return false;
			}
			
			// 是否被机构分组引用
			if ( org.getGroupSet().size() > 0 ) {
				errInfo.append(String.format("机构【%s】是机构分组的成员，不能删除！", org.getShortName()));
				return false;
			}
			
			// 是否被账号引用
			if ( !userService.findListByParamer(null, id).isEmpty() ) {
				errInfo.append(String.format("机构【%s】被用户引用，不能删除！", org.getShortName()));
				return false;
			}
			
			// 是否被角色关系引用
			if ( relationService.findListByParams(null, id, null).size() > 0 ) {
				errInfo.append(String.format("机构【%s】被角色关系引用，不能删除！", org.getShortName()));
				return false;
			}
			
			// 是否被应用引用
			if ( appService.findListByParams(id, null).size() > 0 ) {
				errInfo.append(String.format("机构【%s】被应用引用，不能删除！", org.getShortName()));
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean checkUnique(Organization info, StringBuilder errInfo) {
		Organization org = null;
		// 校验机构名称
		org = findByShortName(info.getShortName());
		if ( org != null && !org.getUuid().equalsIgnoreCase(info.getUuid()) ) {
			errInfo.append(String.format("机构名称【%s】已经存在", info.getShortName()));
			return false;
		}
		//机构后缀
		org = findBySuffix(info.getSuffix());
		if ( org != null && !org.getUuid().equalsIgnoreCase(info.getUuid()) ) {
			errInfo.append(String.format("机构后缀【%s】已经存在", info.getSuffix()));
			return false;
		}
		return true;
	}
	
	@Override
	public Organization findByShortName(String shortName) {
		StringBuilder query = new StringBuilder(" from ").append(Organization.class.getName()).append(" where del = 0 and shortName = ? order by createTime desc");
		List<Organization> org = hibernateBaseDao.findList(query.toString(), shortName);
		if ( org.isEmpty() ) {
			return null;
		} else {
			return org.get(0);
		}
	}

	@Override
	public Organization findByFullName(String fullName) {
		StringBuilder query = new StringBuilder(" from ").append(Organization.class.getName()).append(" where del = 0 and fullName = ? order by createTime desc");
		List<Organization> org = hibernateBaseDao.findList(query.toString(), fullName);
		if ( org.isEmpty() ) {
			return null;
		} else {
			return org.get(0);
		}
	}

	@Override
	public Organization findBySuffix(String suffix) {
		StringBuilder query = new StringBuilder(" from ").append(Organization.class.getName()).append(" where del = 0 and suffix = ? order by createTime desc");
		List<Organization> org = hibernateBaseDao.findList(query.toString(), suffix);
		if ( org.isEmpty() ) {
			return null;
		} else {
			return org.get(0);
		}
	}

	@Override
	public Organization findByCode(String code) {
		StringBuilder query = new StringBuilder(" from ").append(Organization.class.getName()).append(" where del = 0 and code = ? order by createTime desc");
		List<Organization> org = hibernateBaseDao.findList(query.toString(), code);
		if ( org.isEmpty() ) {
			return null;
		} else {
			return org.get(0);
		}
	}
	
	@Override
	public boolean isRoot(String orgId) {
		Organization root = this.getRootOrganization();
		return root.getUuid().equalsIgnoreCase(orgId);
	}

	@Override
	public List<Organization> findListByParams(String areaId, String pId) {
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder query = new StringBuilder(" from ").append(Organization.class.getName())
				.append(" where del = 0");
		
		if ( StringUtil.isNotBlank(areaId) ) {
			query.append(" and area.uuid = ?");
			params.add(areaId);
		}
		
		if ( StringUtil.isNotBlank(pId) ) {
			query.append(" and parent is not null and parent.uuid = ?");
			params.add(pId);
		}
		
		query.append(" order by sequence asc ");
		
		return hibernateBaseDao.findList(query.toString(), params);
	}

	@Override
	public void mergeAfterUpdate(String tableinfo) {
		JSONArray jsonArr = JSONArray.fromObject(tableinfo);
		for(int i = 0; i < jsonArr.size(); i++){
			JSONObject jsonObject = jsonArr.getJSONObject(i);
			OrganizationMergeTemp omt = (OrganizationMergeTemp) JSONObject.toBean(jsonObject, OrganizationMergeTemp.class);
			Organization org = new Organization();
			if(StringUtil.isNotBlank(omt.getAreaId())){
				org.setArea(areaService.findById(omt.getAreaId()));
			}
			if(StringUtil.isNotBlank(omt.getParentId())){
				org.setParent(findById(omt.getParentId()));
			}
			org.setUuid(omt.getId());
			org.setCode(omt.getCode());
			org.setCreateTime(org.getCreateTime());
			org.setDel(org.getDel());
			org.setDesc(omt.getDesc());
			org.setFullName(omt.getFullName());
			org.setParentIs(omt.getParentIs());
			org.setSequence(omt.getSequence());
			org.setShortName(omt.getShortName());
			org.setSuffix(omt.getSuffix());
			org.setType(omt.getType());
			saveOrUpdate(org);//更新到数据库中
		}
		deleteErrorTemp();//清空错误账号信息
		deleteMergeTemp();//清空合并的账号信息
	}

	@Override
	@Transactional
	public boolean saveOrUpdate(OrganizationErrorTemp info) {
		if (StringUtil.isBlank(info.getUuid())) {
			hibernateBaseDao.save(info);
		} else {
			hibernateBaseDao.update(info);
		}
		return true;
	}

	@Override
	@Transactional
	public boolean saveOrUpdate(OrganizationMergeTemp info) {
		if (StringUtil.isBlank(info.getUuid())) {
			info.setCreateTime(TimeUtil.getExactCurrentTime());
			hibernateBaseDao.save(info);
		} else {
			hibernateBaseDao.update(info);
		}
		return true;
	}

	@Override
	@Transactional
	public void deleteErrorTemp() {
		String sql = " delete from UIDS_ORGANIZATION_ERROR_TEMP ";
		hibernateBaseDao.executeUpdate(sql);
	}

	@Override
	@Transactional
	public void deleteMergeTemp() {
		String sql = " delete  from UIDS_ORGANIZATION_MERGE_TEMP ";
		hibernateBaseDao.executeUpdate(sql);
	}

	@Override
	public List<OrganizationErrorTemp> findErrAll() {
		StringBuilder query = new StringBuilder(" from ").append(OrganizationErrorTemp.class.getName());
		return hibernateBaseDao.findList(query.toString());
	}

	@Override
	public List<OrganizationMergeTemp> findMergeAll() {
		StringBuilder query = new StringBuilder(" from ").append(OrganizationMergeTemp.class.getName());
		return hibernateBaseDao.findList(query.toString());
	}

	@Override
	public boolean closeWindows() {
		try {
			deleteErrorTemp();//清空错误账号信息
			deleteMergeTemp();//清空合并的账号信息
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public String saveOrderResult(String ids) {

		JSONObject resultJson = new JSONObject();
		Organization org = null;
		int i = 0;
		for (String id : ids.split(",")) {
			org = findById(id);
			if (null != org) {
				//逻辑删除
				org.setSequence(i);
				hibernateBaseDao.update(org);
				i++;
			}
		}
		resultJson.put("state", 1);
		resultJson.put("info", "保存成功！");
		return resultJson.toString();
	}
	
	@Override
	public boolean canCurrentLoginUserAccessOrganization(Organization org, String roleMark) {
		// 如果是系统管理员，则可以访问任何机构
		Subject subject = SecurityUtils.getSubject();
		if ( subject.isPermitted(WebConstants.ROLE_KEY_ADMIN) ) {
			return true;
		}
		
		// 判断要访问的模块是否是分级授权的模块
		if ( !WebConstants.ROLE_KEY_APP_ADMIN.equalsIgnoreCase(roleMark) 
				&& !WebConstants.ROLE_KEY_USER_ADMIN.equalsIgnoreCase(roleMark) 
				&& !WebConstants.ROLE_KEY_ORG_ADMIN.equalsIgnoreCase(roleMark) ) {
			return false;
		}
		
		// 判断是否拥有对应的管理员权限
		if ( !subject.isPermitted(roleMark) ) {
			return false;
		}
		
		// 查询对应模块下能访问的机构
		List<Organization> organizations = relationService.getOrganizationsOfCurrentLoginUserAs(roleMark);
		
		// 如果要访问的机构，是在能访问的机构或者子树中，就可以访问
		for ( Organization grantOrganization : organizations ) {
			if ( isAncestor(grantOrganization.getUuid(), org.getUuid()) ) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * ancestorOrgId机构是否是progenitureOrgId机构的祖先（两者相同也认为是祖先）
	 * 
	 * @param ancestorOrgId
	 * @param progenitureOrgId
	 * @return
	 */
	private boolean isAncestor(String ancestorOrgId, String progenitureOrgId) {
		Organization progenitureOrg = findById(progenitureOrgId);
		Organization parent = progenitureOrg;
		while ( parent != null ) {
			if ( ancestorOrgId.equalsIgnoreCase(parent.getUuid()) ) {
				return true;
			}
			parent = parent.getParent();
		}
		return false;
	}
	
	/**
	 * 生成机构的机构编码
	 * 
	 * @param org
	 * @return
	 */
	private String generateCodeOfOrganization(Organization org) {
		
		// 机构编码
		String code = "";
		
		// 如果是根节点，则编码为空
		if ( org.getParent() == null ) {
			return code;
		}
		
		// 如果不是根节点，找出所有兄弟节点，比其中最大的编码大1
		int maxSiblingCode = findMaxCodeOfSons(org.getParent());
		
		// 生成新的机构编码
		code = org.getParent().getCode() + String.format("%03d", maxSiblingCode + 1);
		
		return code;
	}
	
	/**
	 * 找出机构的子机构中编码最大的子机构的机构编号（最后3位）
	 * 
	 * @param parent
	 * @return
	 */
	private int findMaxCodeOfSons(Organization parent) {
		
		// 按照机构编码的递减排序找出所有子机构
		List<Organization> sons = findChildrenInOrderOfCodeDesc(parent);
		
		// 如果没有子机构，则返回父机构的 机构编号+000
		if ( sons.isEmpty() ) {
			return 0;
		}
		
		// 一组机构编号的长度（3位为一组）
		final int GROUP_CODE_LENGTH = 3;
		
		// 找出最大的子机构编号，即第一个子机构的编号
		String code = sons.get(0).getCode();
		int length = code.length();
		
		// 最后3位机构编号
		String sLastGroupCode = code.substring(length - GROUP_CODE_LENGTH, length);
		int iLastGroupCode = Integer.parseInt(sLastGroupCode);
		
		return iLastGroupCode;
	}
	
	/**
	 * 按照机构编码的递减排序找出所有子机构
	 * 
	 * @param parent
	 * @return
	 */
	private List<Organization> findChildrenInOrderOfCodeDesc(Organization parent) {
		
		StringBuilder query = new StringBuilder(" from ").append(Organization.class.getName())
				.append(" where del = 0 ")
				.append(" and parent.uuid = ? ")
				.append(" order by code desc");
		
		return hibernateBaseDao.findList(query.toString(), parent.getUuid());
	}
}
