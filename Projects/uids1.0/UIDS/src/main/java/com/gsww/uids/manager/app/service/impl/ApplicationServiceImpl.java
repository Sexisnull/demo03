package com.gsww.uids.manager.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.entity.PageObject;
import com.gsww.common.util.JsonUtils;
import com.gsww.common.util.ObjectJsonValueProcessor;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.app.entity.AppIcon;
import com.gsww.uids.manager.app.entity.AppResource;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.app.service.ResourceService;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.system.service.AbstractCommonService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Service
public class ApplicationServiceImpl extends AbstractCommonService implements ApplicationService {
	
	private static final Logger logger = Logger.getLogger(ApplicationServiceImpl.class);
	
	@Autowired
	private HibernateBaseDao hibernateBaseDao;

	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private AccountService accountService;

	@Override
	public Application findById(String id) {
		if (StringUtil.isBlank(id)) {
			return new Application();
		}
		return (Application) this.hibernateBaseDao.getById(Application.class, id);
	}

	@Override
	public List<Application> findAll() {
		StringBuilder query = new StringBuilder(" from ").append(Application.class.getName())
				.append(" where isDelete = 0 order by createTime desc");
		return this.hibernateBaseDao.findList(query.toString());
	}
	
	@Override
	public List<Application> findShowAll() {
		StringBuilder query = new StringBuilder(" from ").append(Application.class.getName())
				.append(" where isDelete = 0 and isShow = ? order by createTime desc");
		return this.hibernateBaseDao.findList(query.toString(),"1");
	}

	@Override
	public String findPage(int currentPage, int pageSize, String name, String groupId) {
		
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder query = new StringBuilder(" from ").append(Application.class.getName())
				.append(" where isDelete = 0");
		
		if ( StringUtil.isNotBlank(name) ) {
			StringBuilder nameObj = new StringBuilder(name.trim());
			query.append(" and ").append(generateSearchSql("name", nameObj));				
			params.add("%" + nameObj + "%");
		} 
		
		if ( StringUtil.isNotBlank(groupId) && !organizationService.isRoot(groupId) ) {
			query.append(" and organization.uuid = ?");
			params.add(groupId);
		}
		
		query.append(" order by createTime desc");
		PageObject<Application> pageList = hibernateBaseDao.findPage(query.toString(), currentPage, pageSize, params);
		
		try {
			 if (pageList != null)
		      {
		        JsonConfig config = new JsonConfig();
		        config.registerJsonValueProcessor(Organization.class,new ObjectJsonValueProcessor(new String[] { "shortName" }, Organization.class));
		        config.registerJsonValueProcessor(AppIcon.class,new ObjectJsonValueProcessor(new String[] { "name" }, AppIcon.class));
		        config.registerJsonValueProcessor(AppResource.class,new ObjectJsonValueProcessor(new String[] { "name","uuid" }, AppResource.class));
		        JSONArray jsonArray = JSONArray.fromObject(pageList, config);
		        return new JsonUtils().datagridJson(jsonArray);
		      }
		} catch (Exception e) {
			logger.info("应用列表查询错误！" + e);
		}
		return null;
	}

	@Override
	@Transactional
	public boolean saveOrUpdate(Application application) {
		try {
			if (StringUtil.isBlank(application.getUuid())) {
				logger.info("应用新增！！！！！！！！！！！");
				application.setCreateTime(TimeHelper.getExactCurrentTime());
				this.hibernateBaseDao.save(application);
			} else {
				logger.info("应用编辑！！！！！！！！！！！");
				this.hibernateBaseDao.update(application);
			}
		} catch (Exception e) {
			logger.info("新增或者更新异常：" + e);
		}
		return true;
	}

	@Override
	@Transactional
	public void deleteApp(String ids) {
		
		for ( String id : ids.split(",") ) {
			Application application = this.findById(id);
			application.setIsDelete(1);
			hibernateBaseDao.update(application);
		}
		
		logger.info("逻辑删除应用成功");
	}

	@Override
	@Transactional
	public Application findByClientId(String clientId) {
		StringBuilder query = new StringBuilder(" from ").append(Application.class.getName())
				.append(" where isDelete = 0 and clientId = ?");
		List<Application> apps = this.hibernateBaseDao.findList(query.toString(), clientId);
		if (apps.isEmpty()) {
			return null;
		}
		return apps.get(0);
	}

	@Override
	public boolean checkClientAndKey(String clientId, String clientKey) {
		
		if ( StringUtil.isBlank(clientId) || StringUtil.isBlank(clientKey) ) {
			return false;
		}
		
		StringBuilder query = new StringBuilder(" from ").append(Application.class.getName())
				.append(" where isDelete = 0 and clientId = ? and clientKey = ?");
		
		List<Application> apps = this.hibernateBaseDao.findList(query.toString(), clientId, clientKey);
		return !apps.isEmpty();
	}

	@Override
	public List<Application> findByName(String appName) {
		StringBuilder query = new StringBuilder(" from ").append(Application.class.getName())
				.append(" where isDelete = 0 and name = ? ");
		return this.hibernateBaseDao.findList(query.toString(), new Object[] { appName });
	}
	
	@Override
	public boolean checkDelete(String ids, StringBuilder errInfo) {
		for ( String id : ids.split(",") ) {
			Application app = this.findById(id);
			
			// 检查是否有账号引用
			List<Account> accounts = accountService.findListByParams(null, id);
			if ( accounts.size() > 0 ) {
				errInfo.append(String.format("应用【%s】被账号使用，不能删除！", app.getName()));
				return false;
			}
			
			// 检查是否有资源引用
			List<AppResource> resources = resourceService.findListByParams(id, null, null);
			if ( resources.size() > 0 ) {
				errInfo.append(String.format("应用【%s】被资源使用，不能删除！", app.getName()));
				return false;
			}
		}
		
		return true;
	}

	@Override
	public List<Application> findListByParams(String orgId, String iconId) {
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder query = new StringBuilder(" from ").append(Application.class.getName())
				.append(" where isDelete = 0");
		
		if ( StringUtil.isNotBlank(orgId) ) {
			query.append(" and organization.uuid = ?");
			params.add(orgId);
		}
		
		if ( StringUtil.isNotBlank(iconId) ) {
			query.append(" and sysIcon is not null and sysIcon.uuid = ?");
			params.add(iconId);
		}
		
		query.append(" order by createTime desc");

		return hibernateBaseDao.findList(query.toString(), params);
	}

	@Override
	public boolean checkUnique(Application app, StringBuilder errInfo) {
		// 检查应用唯一性:名称唯一性了，那么这个list里面也就一个对象app
	      List<Application>  applications = findByName(app.getName());	
		// 验证唯一性：应用名称
        if (applications.size() > 0) {
        	if (!applications.get(0).getUuid().equalsIgnoreCase(app.getUuid())) {
        		errInfo.append(String.format("应用名称【%s】已经存在", app.getName()));
            	return false;
        	}
        }	      
			
	    // 验证唯一性：应用标识
		Application appMark   = findByMark(app.getMark());
		if ( appMark != null && !appMark.getUuid().equalsIgnoreCase(app.getUuid()) ) {
			  errInfo.append(String.format("应用标识【%s】已经存在", app.getMark()));
			  return false;
			}
		return true;
	}

	@Override
	public Application findByMark(String mark) {
		StringBuilder query = new StringBuilder(" from ").append(Application.class.getName()).append(" where isDelete = 0 and mark = ? order by createTime desc");
		Application app  = hibernateBaseDao.getByHql(query.toString(), mark);
		return app;
	}

	@Override
	public  List<Application> findByAppType(String type) {
		StringBuilder query = new StringBuilder(" from ").append(Application.class.getName()).append(" where isDelete = 0 and type = ? order by createTime desc");
		List<Application> apps  = hibernateBaseDao.findList(query.toString(), type);
		return apps;
	}

	@Override
	public List<Application> findCommonApplications() {

		StringBuilder query = new StringBuilder(" from ").append(Application.class.getName())
				.append(" where isDelete = 0 and type = ? order by createTime desc");
		
		return hibernateBaseDao.findList(query.toString(), Application.COMMON_APP_TYPE);
	}

	@Override
	public Application findSystemApplication() {
		
		StringBuilder query = new StringBuilder(" from ").append(Application.class.getName())
				.append(" where isDelete = 0 and type = ?");
		
		List<Application> apps = hibernateBaseDao.findList(query.toString(), Application.SYSTEM_APP_TYPE);
		if ( apps.isEmpty() ) {
			return null;
		} else {
			return apps.get(0);
		}
	}

}
