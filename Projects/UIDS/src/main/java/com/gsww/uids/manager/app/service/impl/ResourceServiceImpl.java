package com.gsww.uids.manager.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.gsww.uids.manager.app.entity.AppResource;
import com.gsww.uids.manager.app.entity.AppResourceAuth;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ResourceService;
import com.gsww.uids.manager.app.service.SourceAuthService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {
	
	private static final Logger logger = Logger.getLogger(ResourceServiceImpl.class);
	
	@Autowired
	private HibernateBaseDao hibernateBaseDao;
	
	@Autowired
	private SourceAuthService sourceAuthService;
	
	@Autowired
	private AccountService accountService;

	public boolean saveOrUpdate(AppResource appResource) {
		if (StringUtil.isBlank(appResource.getUuid())) {
			logger.info("资源新增！！！！！！！！！！！");
			appResource.setCreateTime(TimeHelper.getExactCurrentTime());
			this.hibernateBaseDao.save(appResource);
		} else {
			logger.info("资源编辑！！！！！！！！！！！");
			this.hibernateBaseDao.update(appResource);
		}
		return true;
	}

	public AppResource findById(String id) {
		if (StringUtil.isBlank(id)) {
			AppResource appResource = new AppResource();
			appResource.setApp(new Application());
			return appResource;
		}
		return (AppResource) this.hibernateBaseDao.getById(AppResource.class, id);
	}

	public List<AppResource> findAll() {
		StringBuilder query = new StringBuilder(" from ").append(AppResource.class.getName())
				.append(" where isDelete = 0 order by createTime desc");
		return this.hibernateBaseDao.findList(query.toString());
	}

	public String findPage(int currentPage, int pageSize, String searchName, String appUuid) {
		StringBuilder query = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		PageObject<AppResource> pageList = new PageObject<AppResource>();
		try {
			if ( (StringUtil.isBlank(appUuid)) ) {
				if ((StringUtil.isBlank(searchName)) || (searchName == "")) {
					query = new StringBuilder(" from ").append(AppResource.class.getName())
							.append(" where isDelete = 0 order by createTime desc");
					pageList = this.hibernateBaseDao.findPage(query.toString(), currentPage,pageSize, params);
				} else {
					params.add("%" + searchName + "%");
					query = new StringBuilder(" from ").append(AppResource.class.getName())
							.append(" where isDelete = 0 and name like ? order by createTime desc");
					pageList = this.hibernateBaseDao.findPage(query.toString(), currentPage,pageSize, params);
				}
			} else {
				params.add(appUuid);
				query = new StringBuilder(" from ").append(AppResource.class.getName())
						.append(" where isDelete = 0 and app.uuid = ? order by createTime desc");
				pageList = this.hibernateBaseDao.findPage(query.toString(), currentPage, pageSize,params);
			}
			if (pageList != null)
		      {
		        JsonConfig config = new JsonConfig();
		        config.registerJsonValueProcessor(Application.class,new ObjectJsonValueProcessor(new String[] { "name" }, Application.class));
		        JSONArray jsonArray = JSONArray.fromObject(pageList, config);
		        return new JsonUtils().datagridJson(jsonArray);
		      }
		} catch (Exception e) {
			logger.info("资源查询失败！" + e);
		}
		return null;
	}

	public boolean saveOrUpdateSource(AppResource appResource) {
		try {
			if (StringUtil.isBlank(appResource.getUuid())) {
				logger.info("应用新增！！！！！！！！！！！");
				appResource.setCreateTime(TimeHelper.getExactCurrentTime());
				this.hibernateBaseDao.save(appResource);
			} else {
				logger.info("应用编辑！！！！！！！！！！！");
				this.hibernateBaseDao.update(appResource);
			}
		} catch (Exception e) {
			logger.info("新增或者更新异常：" + e);
		}
		return true;
	}


	@Override
	public void deleteSource(String ids) {
		
		for ( String id : ids.split(",") ) {
			AppResource appSource = this.findById(id);
			appSource.setIsDelete(1);
			hibernateBaseDao.update(appSource);
		}
		
		logger.info("逻辑删除资源成功");
	}


	@Override
	public List<AppResource> findAppResourceByApp(String appId) {
		StringBuilder query = new StringBuilder(" from ").append(AppResource.class.getName())
				.append(" where isDelete = 0 and app.uuid = ? order by createTime desc");
		return hibernateBaseDao.findList(query.toString(), appId);
	}

	@Override
	public List<AppResource> findByAccount(String accountId) {
		
		List<AppResource> resources = new ArrayList<AppResource>();
		
		Account account = accountService.findById(accountId);
		
		// 授权的资源
		List<AppResourceAuth> auths = sourceAuthService.findByAccount(accountId);
		for (AppResourceAuth auth : auths) {
			if ( this.hasResourceAccessType(account, auth.getResource()) ) {
				resources.add(auth.getResource());
			}
		}
		
		// 账号的默认资源
		List<AppResource> defaultResources = this.findAppResourceByApp(account.getApp().getUuid());
		for ( AppResource res : defaultResources ) {
			if ( this.hasResourceAccessType(account, res) ) {
				resources.add(res);
			}
		}
		
		// 去重
		removeDuplicate(resources);
		
		return resources;
	}
	
	@Override
	public List<AppResource> findShowResourcesOfAccount(String accountId) {
		
		List<AppResource> resources = new ArrayList<AppResource>();
		
		Account account = accountService.findById(accountId);
		
		// 授权的资源
		// 过滤掉系统应用下的资源和前台不显示的资源
		List<AppResourceAuth> auths = sourceAuthService.findByAccount(accountId);
		for (AppResourceAuth auth : auths) {
			if ( this.hasResourceAccessType(account, auth.getResource()) 
					&& !auth.getResource().getApp().getType().equalsIgnoreCase(Application.SYSTEM_APP_TYPE)
					&& !auth.getResource().getApp().getIsShow().equalsIgnoreCase("0") ) {
				resources.add(auth.getResource());
			}
		}
		
		// 账号的默认资源
		if ( !account.getApp().getType().equalsIgnoreCase(Application.SYSTEM_APP_TYPE) 
				&& !account.getApp().getIsShow().equalsIgnoreCase("0") ) {
			List<AppResource> defaultResources = this.findAppResourceByApp(account.getApp().getUuid());
			for ( AppResource res : defaultResources ) {
				if ( this.hasResourceAccessType(account, res) ) {
					resources.add(res);
				}
			}
		}
		
		// 去重
		removeDuplicate(resources);
		
		return resources;
	}

	@Override
	public boolean checkDelete(String ids, StringBuilder errInfo) {
		for ( String id : ids.split(",") ) {
			AppResource resource = this.findById(id);
			
			// 检查是否被资源授权引用
			List<AppResourceAuth> auths = sourceAuthService.findListByParames(null, null, null, id);
			if ( auths.size() > 0 ) {
				errInfo.append(String.format("资源【%s】被授权了，不能删除！", resource.getName()));
				return false;
			}
		}
		
		return true;
	}

	@Override
	public List<AppResource> findListByParams(String appId, String iconId, String pId) {
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder query = new StringBuilder(" from ").append(AppResource.class.getName())
				.append(" where isDelete = 0");
		
		if ( StringUtil.isNotBlank(appId) ) {
			query.append(" and app.uuid = ?");
			params.add(appId);
		}
		
		if ( StringUtil.isNotBlank(iconId) ) {
			query.append(" and sysIcon is not null and sysIcon.uuid = ?");
			params.add(iconId);
		}
		
		if ( StringUtil.isNotBlank(pId) ) {
			query.append(" and parent is not null and parent.uuid = ?");
			params.add(pId);
		}
		
		query.append(" order by createTime desc");
		
		return hibernateBaseDao.findList(query.toString(), params);
	}

	@Override
	public boolean checkUnique(AppResource appResoure, StringBuilder errInfo) {
		//根据资源的编号检查唯一性
		AppResource appSourceCode = findByParames(appResoure.getCode(),null,null);
		//根据资源名称检查唯一性
		AppResource appSourceName = findByParames(null,appResoure.getName(),null);
		//根据资源路径检查唯一性
		AppResource appSourceUrl = findByParames(null,null,appResoure.getUrl());
		
		if (appResoure.getUuid() == null || appResoure.getUuid().equals("")) {
			if (appSourceCode != null && appSourceCode.getCode().equalsIgnoreCase(appResoure.getCode())) {
					errInfo.append(String.format("资源编号【%s】已经存在", appResoure.getCode()));
					return false;
		        }
			if (appSourceName != null && appSourceName.getName().equalsIgnoreCase(appResoure.getName())) {
	            	errInfo.append(String.format("资源名称【%s】已经存在", appResoure.getName()));
	    			return false;
			}
			if (appSourceUrl != null && appSourceUrl.getUrl().equalsIgnoreCase(appResoure.getUrl())) {
	            	errInfo.append(String.format("资源路径【%s】已经存在", appResoure.getUrl()));
	    			return false;
			}
		}
		return true;
	}

	@Override
	public AppResource findByParames(String code,String name,String url) {
		AppResource appsource = new AppResource();
		StringBuilder query = new StringBuilder(" from ").append(AppResource.class.getName()).append(" where isDelete = 0");
		if (!StringUtil.isBlank(code)) {
			query.append(" and code = ?");
			appsource = hibernateBaseDao.getByHql(query.toString(),code);
		} else if (!StringUtil.isBlank(name)) {
			query.append(" and name = ?");
			appsource = hibernateBaseDao.getByHql(query.toString(),name);
		} else if (!StringUtil.isBlank(url)) {
			query.append(" and url = ?");
			appsource = hibernateBaseDao.getByHql(query.toString(),url);
		}
		return appsource;
	}

	@Override
	public AppResource findByAppAndUrl(String appId, String url) {
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder query = new StringBuilder(" from ").append(AppResource.class.getName())
				.append(" where isDelete = 0 and app.uuid = ? and url is not null and url = ?");
		
		params.add(appId);
		params.add(url);
		
		List<AppResource> resources = hibernateBaseDao.findList(query.toString(), params);
		if ( resources.isEmpty() ) {
			return null;
		} else {
			return resources.get(0);
		}
	}
	
	/**
	 * 账号的类型是否与资源的允许访问类型一致
	 * 
	 * @param account
	 * @param resource
	 * @return
	 */
	private boolean hasResourceAccessType(Account account, AppResource resource) {
		
		/** TODO 如果要求个人账号不能访问政府资源，则将来要启用注释掉的代码
		 * TODO 如果按照用户走，只要是同一个人，无论用什么账号访问，权限都共享，则不需要注释掉的代码
		 * 
		// 如果资源访问类型为空，则任意账号类型都允许访问
		if ( StringUtil.isBlank(resource.getResourceAccessType()) ) {
			return true;
		} else if ( AppResource.GOVERNMENT_RESOURCE.equalsIgnoreCase(resource.getResourceAccessType()) ) {
			// 如果资源只允许公务账号访问，则账号类型必须是公务账号才能访问
			return (AccountTypeEnum.GOVERNMENT.getNumber() == account.getType());
		} else {
			// 公众资源允许个人账号和法人账号访问
			return (AccountTypeEnum.PUBLIC.getNumber() == account.getType() 
					|| AccountTypeEnum.CORPORATE.getNumber() == account.getType());
		}
		**/
		
		return true;
	}
	
	/**
	 * 根据uuid去重
	 * 
	 * @param resources
	 */
	private List<AppResource> removeDuplicate(List<AppResource> resources) {
		
		// 将List中的元素放到Map中，利用key唯一去重
		Map<String, AppResource> resourceMap = new HashMap<String, AppResource>();
		for ( AppResource resource : resources ) {
			resourceMap.put(resource.getUuid(), resource);
		}
		
		// 清空原来的List
		resources.clear();
		
		// 将Map中去重后的结果放回List中
		resources.addAll(resourceMap.values());
		
		return resources;
	}
}
