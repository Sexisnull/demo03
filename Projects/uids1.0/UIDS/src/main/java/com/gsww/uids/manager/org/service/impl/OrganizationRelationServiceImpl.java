package com.gsww.uids.manager.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.entity.PageObject;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.entity.OrganizationRelation;
import com.gsww.uids.manager.org.entity.OrganizationRelationType;
import com.gsww.uids.manager.org.service.OrganizationRelationService;

/**
 * 机构关系业务层
 * @author jinwei
 *
 */
@Service
public class OrganizationRelationServiceImpl implements OrganizationRelationService {

public static final Logger logger = LoggerFactory.getLogger(OrganizationRelationServiceImpl.class);
	
	@Autowired
    private HibernateBaseDao hibernateBaseDao;
	
	@Override
	@Transactional
	public OrganizationRelation saveOrUpdate(OrganizationRelation info) {
		if (StringUtil.isBlank(info.getUuid())) {
			info.setCreateTime(TimeHelper.getExactCurrentTime());
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
			OrganizationRelation re = null;
			for (String id : ids.split(",")) {
				re = findById(id);
				if (null != re) {
					//逻辑删除
					re.setDel(1);
					hibernateBaseDao.update(re);
				}
			}
		}
	}
	
	@Override
	@Transactional
	public void deleteByOrgId(String ids) {
		if (StringUtil.isNotBlank(ids)) {
			OrganizationRelation re = null;
			for (String id : ids.split(",")) {
				re = findByOrgId(id);
				if (null != re) {
					//逻辑删除
					re.setDel(1);
					hibernateBaseDao.update(re);
				}
			}
		}
	}
	
	@Override
	@Transactional
	public OrganizationRelation findById(String id) {
		if (StringUtil.isBlank(id)) {
			return new OrganizationRelation();
		}
		return hibernateBaseDao.getById(OrganizationRelation.class, id);
	}
	
	@Override
	public List<OrganizationRelation> finParent(String orgId, String classifyCode) {
		//查询参数
		List<Object> params = new ArrayList<Object>();

		StringBuilder query = new StringBuilder(" from ").append(OrganizationRelation.class.getName()).append(" where del = 0 ");

		query.append(" and organization.uuid = ? ");
		params.add(orgId);
		
		if(StringUtil.isNotBlank(classifyCode)){
			query.append(" and classify.code = ? ");
			params.add(classifyCode);
		}
		
		query.append(" order by createTime desc");
		
		return hibernateBaseDao.findList(query.toString(), params);
	}

	@Override
	public List<OrganizationRelationType> findAll() {

		StringBuilder query = new StringBuilder(" from ").append(OrganizationRelationType.class.getName()).append(" where del = 0 ");
		query.append(" order by createTime desc");
		
		return hibernateBaseDao.findList(query.toString());
	}

	@Override
	@Transactional
	public OrganizationRelationType findTypeById(String id) {
		if (StringUtil.isBlank(id)) {
			return new OrganizationRelationType();
		}
		return hibernateBaseDao.getById(OrganizationRelationType.class, id);
	}

	@Override
	public List<OrganizationRelation> findChildByPIdAndCode(String id, String code) {
			//查询参数
			List<Object> params = new ArrayList<Object>();
			StringBuilder query = new StringBuilder(" from ").append(OrganizationRelation.class.getName()).append(" where del = 0 ");

			query.append(" and parentOrganization.uuid = ? ");
			params.add(id);
			
			if(StringUtil.isNotBlank(code)){
				query.append(" and classify.code = ? ");
				params.add(code);
			}
			query.append(" order by createTime desc");
			
			return hibernateBaseDao.findList(query.toString(), params);
	}
	
	@Override
	public OrganizationRelation getRootOrganization() {
		StringBuilder query = new StringBuilder(" from ").append(OrganizationRelation.class.getName())
				.append(" where del = 0 and parentOrganization.parent is null");

		List<OrganizationRelation> rootOrgs = hibernateBaseDao.findList(query.toString());
		if ( rootOrgs.isEmpty() ) {
			return new OrganizationRelation();
		} else {
			return rootOrgs.get(0);
		}
	}

	@Override
	public String findPage(Integer currentPage, Integer pageSize, String parentId, String relationCode) {

		if(StringUtil.isBlank(parentId)){
			parentId = getRootOrganization().getUuid();
		}
		try {
			List<Object> params = new ArrayList<Object>();
			StringBuilder query = new StringBuilder(" from ").append(OrganizationRelation.class.getName()).append(" where del = 0 ");

			query.append(" and parentOrganization.uuid = ? ");
			params.add(parentId);
			
			if(StringUtil.isNotBlank(relationCode)){
				query.append(" and classify.code = ? ");
				params.add(relationCode);
			}
			
			query.append(" order by createTime desc");
			
			PageObject<OrganizationRelation> pageList = hibernateBaseDao.findPage(query.toString(), currentPage, pageSize, params);
			
			List<Organization> dataList = new ArrayList<Organization>();
			for(OrganizationRelation relation : pageList.getDataList()){
				dataList.add(relation.getOrganization());
			}
			
			PageObject<Organization> List = new PageObject<Organization>();
			
			List.setBeginRecord(pageList.getBeginRecord());
			List.setCurrentPage(pageList.getCurrentPage());
			List.setDataList(dataList);
			List.setEndRecord(pageList.getEndRecord());
			List.setPageCount(pageList.getPageCount());
			List.setPageSize(pageList.getPageSize());
			List.setTotalCount(pageList.getTotalCount());
			
			return List.toJSONString();
			
		} catch (Exception e) {
			logger.info("机构列表信息获取失败！" + e);
		}
		return null;	
	}
	
	@Override
	public boolean checkDelete(String ids, StringBuilder errInfo) {
		for ( String id : ids.split(",") ) {
			
			// 是否被机构上下级关系引用
			if ( findListByParams(null, null, id).size() >0 ) {
				String err = "在";
				OrganizationRelation relation = findListByParams(null, null, id).get(0);
				err += relation.getClassify().getName()+"下存在下级机构";
				errInfo.append(String.format("机构【%s】"+err+"，不能删除！", relation.getParentOrganization().getShortName()));
				return false;
			}
		}
		return true;
	}

	@Override
	public List<OrganizationRelation> findListByParams(String orgId, String orgTypeId, String pOrgId) {
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder query = new StringBuilder(" from ").append(OrganizationRelation.class.getName())
				.append(" where del = 0 ");
		
		if ( StringUtil.isNotBlank(orgId) ) {
			query.append(" and organization.uuid = ? ");
			params.add(orgId);
		}
		
		if ( StringUtil.isNotBlank(orgTypeId) ) {
			query.append(" and classify.uuid = ? ");
			params.add(orgTypeId);
		}
		
		if ( StringUtil.isNotBlank(pOrgId) ) {
			query.append(" and parentOrganization.uuid = ? ");
			params.add(pOrgId);
		}
		
		query.append(" order by createTime desc");
		
		return hibernateBaseDao.findList(query.toString(), params);
	}

	@Override
	public OrganizationRelation findByOrgId(String orgId) {
		
		StringBuilder query = new StringBuilder(" from ").append(OrganizationRelation.class.getName()).append(" where del = 0 ");

		query.append(" and organization.uuid = ? ");
		return hibernateBaseDao.getByHql(query.toString(), orgId);
	}

	@Override
	public OrganizationRelation findByParentOrgId(String Pid) {
		StringBuilder query = new StringBuilder(" from ").append(OrganizationRelation.class.getName()).append(" where del = 0 ");

		query.append(" and organization.uuid = ? ");
		return hibernateBaseDao.getByHql(query.toString(), Pid);
	}
}
