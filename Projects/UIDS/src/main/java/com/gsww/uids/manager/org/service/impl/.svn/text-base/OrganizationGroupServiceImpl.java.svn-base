package com.gsww.uids.manager.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.uids.manager.app.service.SourceAuthService;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.entity.OrganizationGroup;
import com.gsww.uids.manager.org.service.OrganizationGroupService;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.system.service.AbstractCommonService;

@Service
public class OrganizationGroupServiceImpl extends AbstractCommonService implements OrganizationGroupService {

	@Autowired
    private HibernateBaseDao hibernateBaseDao;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private SourceAuthService authService;
	
	@Override
	@Transactional
	public OrganizationGroup saveOrUpdate(OrganizationGroup info) {
		if (StringUtil.isBlank(info.getUuid())) {
			info.setCreateTime(TimeHelper.getExactCurrentTime());
			hibernateBaseDao.save(info);
		} else {
			hibernateBaseDao.update(info);
		}
		return info;
	}
	
	@Override
	public List<OrganizationGroup> findAll() {
		
		StringBuilder query = new StringBuilder(" from ").append(OrganizationGroup.class.getName()).append(" where del = 0");
		
		return hibernateBaseDao.findList(query.toString());
	}
	
	@Override
	@Transactional
	public OrganizationGroup findById(String id) {
		if (StringUtil.isBlank(id)) {
			return new OrganizationGroup();
		}
		return hibernateBaseDao.getById(OrganizationGroup.class, id);
	}

	@Override
	@Transactional
	public void delete(String ids) {
		if (StringUtil.isNotBlank(ids)) {
			OrganizationGroup orgGroup = null;
			for (String id : ids.split(",")) {
				orgGroup = findById(id);
				if (null != orgGroup) {
					//逻辑删除
					orgGroup.setDel(1);
					hibernateBaseDao.update(orgGroup);
				}
			}
		}
	}

	@Override
	public String findPage(Integer currentPage, Integer pageSize, String groupName) {
		
		StringBuilder newGroupName = null;

		List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder(" from ").append(OrganizationGroup.class.getName()).append(" where del = 0 ");

		if(StringUtil.isNotBlank(groupName)){
			newGroupName = new StringBuilder(groupName);
			query.append(" and ");
			query.append(generateSearchSql("name", newGroupName));
			params.add("%" + newGroupName + "%");
		}
		query.append(" order by createTime desc ");
		
		return  hibernateBaseDao.findPage(query.toString(), currentPage, pageSize, params).toJSONString();
	}

	@Override
	public String findGroupOrg(String groupId, String orgName) {
		
		JSONArray result = new JSONArray();
		JSONObject json = null;
		
		if(StringUtil.isNotBlank(groupId)){
			OrganizationGroup group = findById(groupId);
			for(Organization org : group.getOrgSet()){
				if(StringUtil.isNotBlank(orgName)){
					if(org.getShortName().contains(orgName)){
						json = new JSONObject();
						json.put("uuid", org.getUuid());
						json.put("shortName", org.getShortName());
						json.put("code", org.getCode());
						json.put("area", org.getArea().getName());
						result.put(json);
					}
				}else{
					json = new JSONObject();
					json.put("uuid", org.getUuid());
					json.put("shortName", org.getShortName());
					json.put("code", org.getCode());
					json.put("area", org.getArea().getName());
					result.put(json);
				}
				
			}
		}

		return  result.toString();
	}

	@Override
	public String saveGroupOrg(String orgIds, String groupId) {
		//返回结果
		JSONObject resultJson = new JSONObject();
		
		if(StringUtil.isBlank(orgIds)){
			resultJson.put("state", 7);
			resultJson.put("info", "您未选择机构，请选择或取消！");
			return resultJson.toString();
		}
		
		try {
			if(checkOrgGroup(groupId, orgIds)){
				Organization org = null;
				OrganizationGroup group = findById(groupId);
				//给机构增加分组
				for (String id : orgIds.split(",")) {
					org = organizationService.findById(id);
					org.getGroupSet().add(group);
					organizationService.saveOrUpdate(org);
				}
			}else{
				resultJson.put("state", 7);
				resultJson.put("info", "机构已存在，请重新添加");
				return resultJson.toString();
			}
		} catch (Exception e) {
			resultJson.put("state", 7);
			resultJson.put("info", "保存失败，请重新配置信息！");
			return resultJson.toString();
		}
		
		resultJson.put("state", 1);
		resultJson.put("info", "保存成功！");
		return resultJson.toString();		
	}
	
	/**
	 * 验证机构分组内是否不存在该机构
	 * @return
	 */
	private boolean checkOrgGroup( String groupId, String orgIds ){
		
		OrganizationGroup group = findById(groupId);
		
		for(Organization org : group.getOrgSet()){
			for (String id : orgIds.split(",")) {
				if(id.equals(org.getUuid())){
					return false;
				}
			}
				
		}
		return true;
	}

	@Override
	public boolean checkDelete(String ids, StringBuilder errInfo) {
		
		for ( String id : ids.split(",") ) {
			// 要删除的机构分组
			OrganizationGroup group = this.findById(id);
			
			// 是否分组有成员
			if ( group.getOrgSet().size() > 0 ) {
				errInfo.append(String.format("机构分组【%s】下存在机构成员，不能删除！", group.getName()));
				return false;
			}
			
			// 是否被资源授权引用
			if ( authService.findListByParames(null, id, null, null).size() > 0 ) {
				errInfo.append(String.format("机构分组【%s】被资源授权引用，不能删除！", group.getName()));
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public boolean checkUnique(OrganizationGroup info, StringBuilder errInfo) {
		OrganizationGroup orgGroup = null;
		// 校验机构分组名称
		orgGroup = findByOrgGtoupName(info.getName());
		if ( orgGroup != null && !orgGroup.getUuid().equalsIgnoreCase(info.getUuid()) ) {
			errInfo.append(String.format("机构分组名称【%s】已经存在", info.getName()));
			return false;
		}
		//校验机构分组标志
		orgGroup = findByOrgGroupMark(info.getMark());
		if ( orgGroup != null && !orgGroup.getUuid().equalsIgnoreCase(info.getUuid()) ) {
			errInfo.append(String.format("机构分组标志【%s】已经存在", info.getMark()));
			return false;
		}
		return true;
	}
	
	@Override
	public OrganizationGroup findByOrgGtoupName(String orgGroupName) {
		StringBuilder query = new StringBuilder(" from ").append(OrganizationGroup.class.getName()).append(" where del = 0 and name = ? order by createTime desc");
		List<OrganizationGroup> orgGroup = hibernateBaseDao.findList(query.toString(), orgGroupName);
		if ( orgGroup.isEmpty() ) {
			return null;
		} else {
			return orgGroup.get(0);
		}
	}
	
	@Override
	public OrganizationGroup findByOrgGroupMark(String ogrGroupMark) {
		StringBuilder query = new StringBuilder(" from ").append(OrganizationGroup.class.getName()).append(" where del = 0 and mark = ? order by createTime desc");
		List<OrganizationGroup> orgGroup = hibernateBaseDao.findList(query.toString(), ogrGroupMark);
		if ( orgGroup.isEmpty() ) {
			return null;
		} else {
			return orgGroup.get(0);
		}
	}
}
