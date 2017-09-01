package com.gsww.uids.manager.role.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.entity.PageObject;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.app.service.SourceAuthService;
import com.gsww.uids.manager.role.entity.Role;
import com.gsww.uids.manager.role.service.RoleRelationService;
import com.gsww.uids.manager.role.service.RoleService;
import com.gsww.uids.manager.sys.service.impl.AreaServiceImpl;

/**
 * 角色业务层
 * @author sunbw
 *
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService{
	
	private static final Logger logger = Logger.getLogger(AreaServiceImpl.class);
	
	@Autowired
	private HibernateBaseDao hibernateBaseDao;
	
	@Autowired
	private RoleRelationService roleRelationService;
	
	@Autowired
	private SourceAuthService sourceAuthService;
	
	@Override
	public boolean saveOrUpdate(Role role) {
		if (StringUtil.isBlank(role.getUuid())) {
			role.setCreateTime(TimeHelper.getExactCurrentTime());
			hibernateBaseDao.save(role);
			logger.info("保存成功");
		} else {
			hibernateBaseDao.update(role);
			logger.info("更新成功");
		}
		return true;
	}

	@Override
	public void delete(String ids) {
		Role info = null;
		for (String id : ids.split(",")) {
				info = hibernateBaseDao.getById(Role.class, id);
				info.setIsDelete(1);
				hibernateBaseDao.update(info);
				logger.info("逻辑删除成功");
		}
	}

	@Override
	public Role findById(String id) {
		if (StringUtil.isBlank(id)) {
			return new Role();
		}
		return hibernateBaseDao.getById(Role.class, id);
	}

	@Override
	public PageObject<Role> findPage(Integer currentPage, Integer pageSize,String searchText) {
		StringBuilder query = new StringBuilder(" from ").append(Role.class.getName()).append(" where isDelete = 0 ");
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.isNotBlank(searchText)){
			query.append(" and name like ? ");
			params.add("%" + searchText + "%");
		}
		query.append(" order by createTime desc");
		return hibernateBaseDao.findPage(query.toString(), currentPage, pageSize,params);
	}

	@Override
	public List<Role> findAll() {
		StringBuilder query = new StringBuilder(" from ").append(Role.class.getName()).append(" where isDelete = 0 and validStatus = 0 order by createTime desc");
		return hibernateBaseDao.findList(query.toString());
	}

	@Override
	public List<Role> findAllOfNonGovernment() {
		
		StringBuilder query = new StringBuilder(" from ").append(Role.class.getName())
				.append(" where isDelete = 0 and validStatus = 0")
				.append(" and mark != ? and mark != ? and mark != ? and mark != ?")
				.append(" order by createTime desc");
		
		List<Object> params = new ArrayList<Object>();
		params.add(WebConstants.ROLE_KEY_ADMIN);
		params.add(WebConstants.ROLE_KEY_APP_ADMIN);
		params.add(WebConstants.ROLE_KEY_ORG_ADMIN);
		params.add(WebConstants.ROLE_KEY_USER_ADMIN);
		
		return hibernateBaseDao.findList(query.toString(), params);
	}

	@Override
	public void openOrClose(String ids, String oper) {

		int status = -1;
		if ( "open".equals(oper) ) {
			status = 0;
		} else if ( "close".equals(oper) ) {
			status = 1;
		}
		
		for ( String id : ids.split(",") ) {
			Role info = hibernateBaseDao.getById(Role.class, id);
			info.setValidStatus(status);
			hibernateBaseDao.update(info);
		}
	}

	@Override
	public boolean checkDelete(String ids, StringBuilder errInfo) {
		
		for ( String id : ids.split(",") ) {
			// 要删除的角色
			Role role = this.findById(id);
			
			// 是否被角色关系引用
			if ( !roleRelationService.findListByParams(null, null, id).isEmpty() ){
				errInfo.append(String.format("角色【%s】被角色关系引用", role.getName()));
				return false;
			}
			
			// 是否被机构上下级关系引用
			if ( !sourceAuthService.findListByParams(null, null, id, null).isEmpty() ) {
				errInfo.append(String.format("角色【%s】被资源授权引用", role.getName()));
				return false;
			}
		}
		
		return true;
	}
	@Override
	public List<Role> findByMark(String mark) {
		StringBuilder query = new StringBuilder(" from ").append(Role.class.getName()).append(" where isDelete = 0  and mark = ? order by createTime desc");
		return hibernateBaseDao.findList(query.toString(),mark);
	}

	@Override
	public List<Role> findByName(String name) {
		StringBuilder query = new StringBuilder(" from ").append(Role.class.getName()).append(" where isDelete = 0  and name = ? order by createTime desc");
		return hibernateBaseDao.findList(query.toString(),name);
	}

	@Override
	public boolean checkUnique(Role info, StringBuilder errInfo) {
		// 根据角色名称查询记录
		List<Role> listsByName = findByName(info.getName());
		// 验证唯一性：角色
		if ( !listsByName.isEmpty() && !listsByName.get(0).getUuid().equalsIgnoreCase(info.getUuid())) {
			errInfo.append(String.format("角色名称【%s】已经存在", info.getName()));
			return false;
		}
		
		//根据标识符查询记录
		List<Role> listsByMark = findByMark(info.getMark());
		
		// 验证唯一性：标识符
		if ( !listsByMark.isEmpty() && !listsByMark.get(0).getUuid().equalsIgnoreCase(info.getUuid())) {
			errInfo.append(String.format("标识符【%s】已经存在", info.getMark()));
			return false;
		}
		return true;
	}
}