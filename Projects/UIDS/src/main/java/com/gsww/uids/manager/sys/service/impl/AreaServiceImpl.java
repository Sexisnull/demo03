package com.gsww.uids.manager.sys.service.impl;

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
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.sys.entity.Area;
import com.gsww.uids.manager.sys.service.AreaService;
import com.gsww.uids.manager.user.service.UserService;

/**
 * 区域业务层实现
 * 
 * @author taolm
 *
 */
@Service
public class AreaServiceImpl implements AreaService {

	private static final Logger logger = Logger.getLogger(AreaServiceImpl.class);
	
	@Autowired
	private HibernateBaseDao hibernateBaseDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Override
	@Transactional
	public boolean saveOrUpdate(Area area) {
		if (StringUtil.isBlank(area.getUuid())) {
			area.setCreateTime(TimeHelper.getExactCurrentTime());
			hibernateBaseDao.save(area);
			logger.info("保存成功");
		} else {
			hibernateBaseDao.update(area);
			logger.info("更新成功");
		}
		return true;
	}

	@Override
	@Transactional
	public void delete(String ids) {

		for ( String id : ids.split(",") ) {
			// 删除区域
			Area info = hibernateBaseDao.getById(Area.class, id);
			info.setIsDelete(1);
			hibernateBaseDao.update(info);
			
			// 修改父节点的状态
			Area parent = info.getParent();
			if ( parent != null ) {
				List<Area> children = this.findChild(parent.getUuid());
				if ( children.isEmpty() ) {
					parent.setParentOrChild(0);
					hibernateBaseDao.update(parent);
				}
			}
		}
		
		logger.info("逻辑删除区域成功");
	}

	@Override
	public Area findById(String id) {
		if (StringUtil.isBlank(id)) {
			return new Area();
		}
		return hibernateBaseDao.getById(Area.class, id);
	}

	@Override
	public PageObject<Area> findPage(Integer currentPage, Integer pageSize,String id,String searchText) {
		StringBuilder query1 = new StringBuilder(" from ").append(Area.class.getName()).append(" where isDelete = 0 ");
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.isNotBlank(id)){
			query1.append("and parent.uuid = ? ");
			params.add(id);
		}
		if(StringUtil.isNotBlank(searchText)){
			query1.append("and name like ? ");
			params.add("%"+searchText+"%");
		}
		query1.append("order by createTime desc");
		return hibernateBaseDao.findPage(query1.toString(), currentPage, pageSize, params);
	}

	@Override
	public List<Area> findAll() {
		StringBuilder query = new StringBuilder(" from ").append(Area.class.getName()).append(" where isDelete = 0 and validStatus = 0 order by createTime desc");
		return hibernateBaseDao.findList(query.toString());
	}

	@Override
	public List<Area>  findChild(String parentId){
		StringBuilder query = new StringBuilder(" from ").append(Area.class.getName()).append(" where isDelete = 0 and validStatus = 0 ");
		query.append(" and parent.uuid = ? ");
		query.append(" order by createTime desc");
		return hibernateBaseDao.findList(query.toString(), parentId);
	}	
	
	@Override
	public List<Area> findAllByLevel(int level) {
		StringBuilder query = new StringBuilder(" from ").append(Area.class.getName())
				.append(" where isDelete = 0 and validStatus = 0 and  level LIKE  ? order by createTime desc");
		return hibernateBaseDao.findList(query.toString(), "%"+level+"%");
	}
	
	@Override
	public Area generateAreaOfLevel(int level) {
		
		Area retArea = new Area();
		retArea.setLevel(level + "");
		// TODO 子等级
		
		// 填充上级节点
		Area cur = retArea;
		for (int i=level-1; i>0; i--) {
			Area parent = new Area();
			parent.setLevel(i + "");
			// TODO 子等级
			
			cur.setParent(parent);
			
			// 向上移动
			cur = parent;
		}
		
		return retArea;
	}
	
	@Override
	@Transactional
	public void openOrClose(String ids, String oper) {
		
		int status = -1;
		if ( "open".equals(oper) ) {
			status = 0;
		} else if ( "close".equals(oper) ) {
			status = 1;
		}
		
		for ( String id : ids.split(",") ) {
			Area info = hibernateBaseDao.getById(Area.class, id);
			info.setValidStatus(status);
			hibernateBaseDao.update(info);
		}
	}

	@Override
	public List<Area> findNextList(String parentId) {
		StringBuilder query1 = new StringBuilder(" from ").append(Area.class.getName()).append(" where isDelete = 0 ");
		query1.append(" and parent.uuid = ? ");
		query1.append(" order by createTime desc");
		return hibernateBaseDao.findList(query1.toString(), parentId);
	}

	@Override 
	public Area getRootOrganization() {
		StringBuilder query = new StringBuilder(" from ").append(Area.class.getName())
				.append(" where isDelete = 0 and parent is null");

		List<Area> rootArea = hibernateBaseDao.findList(query.toString());
		if ( rootArea.isEmpty() ) {
			return new Area();
		} else {
			return rootArea.get(0);
		}
	}
	
	@Override
	public boolean checkDelete(String ids, StringBuilder errInfo) {
		
		for ( String id : ids.split(",") ) {
			// 要删除的区域
			Area area = this.findById(id);
			
			// 是否存在下级区域
			if ( !this.findChild(id).isEmpty()) {
				errInfo.append(String.format("区域【%s】下存在下级区域", area.getName()));
				return false;
			}
			
			// 是否被用户引用
			if ( !userService.findListByParamer(id, null).isEmpty() ){
				errInfo.append(String.format("区域【%s】被用户引用", area.getName()));
				return false;
			}
			
			// 是否被机构上下级关系引用
			if ( !organizationService.findListByParams(id, null).isEmpty() ) {
				errInfo.append(String.format("区域【%s】被机构引用", area.getName()));
				return false;
			}
		}
		
		return true;
	}

	@Override
	public List<Area> findByCode(String code) {
		StringBuilder query = new StringBuilder(" from ").append(Area.class.getName()).append(" where isDelete = 0  and code = ? order by createTime desc");
		return hibernateBaseDao.findList(query.toString(),code);
	}

	@Override
	public List<Area> findByName(String name) {
		StringBuilder query = new StringBuilder(" from ").append(Area.class.getName()).append(" where isDelete = 0  and name = ? order by createTime desc");
		return hibernateBaseDao.findList(query.toString(),name);
	}

	@Override
	public boolean checkUnique(Area info, StringBuilder errInfo) {
		// 根据区域名称查询记录
		List<Area> listsByName = findByName(info.getName());		
		// 验证唯一性：角色
		if ( !listsByName.isEmpty() && !listsByName.get(0).getUuid().equalsIgnoreCase(info.getUuid())) {
			errInfo.append(String.format("区域名称【%s】已经存在", info.getName()));
			return false;
		}
		
		//根据标识符查询记录
		List<Area> listsByMark = findByCode(info.getCode());		
		// 验证唯一性：标识符
		if ( !listsByMark.isEmpty() && !listsByMark.get(0).getUuid().equalsIgnoreCase(info.getUuid())) {
			errInfo.append(String.format("区域编码【%s】已经存在", info.getCode()));
			return false;
		}
		return true;
	}
}
