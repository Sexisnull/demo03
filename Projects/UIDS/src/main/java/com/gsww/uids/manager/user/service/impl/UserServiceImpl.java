package com.gsww.uids.manager.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.entity.PageObject;
import com.gsww.common.enums.UserTypeEnum;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeUtil;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.role.entity.RoleRelation;
import com.gsww.uids.manager.role.service.RoleRelationService;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.entity.UserDetail;
import com.gsww.uids.manager.user.service.UserService;
import com.gsww.uids.system.service.AbstractCommonService;

/**
 * 用户的业务层实现
 * 
 * @author taolm
 *
 */
@Service
public class UserServiceImpl extends AbstractCommonService implements UserService {
	
	@Autowired
	private HibernateBaseDao hibernateBaseDao;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private RoleRelationService roleRelationService;
	
	@Override
	@Transactional
	public boolean saveOrUpdate(User info) {
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
	public void delete(String ids) {
		for (String id : ids.split(",")) {
			User info = hibernateBaseDao.getById(User.class, id);
			info.setDel(1);
			hibernateBaseDao.update(info);
		}
	}
	
	@Override
	public User findById(String id) {
		if (StringUtil.isBlank(id)) {
			return new User();
		}
		return hibernateBaseDao.getById(User.class, id);
	}

	@Override
	public PageObject<User> findPage(int type, String name, String identity, String mobile, String corporateName, Integer currentPage, Integer pageSize) {

		List<Object> params = new ArrayList<Object>();
		
		StringBuilder query = new StringBuilder(" from ").append(User.class.getName())
				.append(" where del = 0 and type = ?");
		params.add(type);
		
		if ( StringUtil.isNotBlank(name) ) {
			StringBuilder newName = new StringBuilder(name);
			query.append(" and ").append(generateSearchSql("name", newName));
			params.add("%" + newName + "%");
		}
		
		if ( StringUtil.isNotBlank(identity) ) {
			StringBuilder newIdentity = new StringBuilder(identity);
			query.append(" and ").append(generateSearchSql("identity", newIdentity));
			params.add("%" + newIdentity + "%");		
		}
		
		if ( StringUtil.isNotBlank(mobile) ) {
			StringBuilder newMobile = new StringBuilder(mobile);
			query.append(" and ").append(generateSearchSql("mobile", newMobile));
			params.add("%" + newMobile + "%");
		}
		
		if ( StringUtil.isNotBlank(corporateName) ) {
			StringBuilder newCorporateName = new StringBuilder(corporateName);
			query.append(" and ").append(generateSearchSql("corporateName", newCorporateName));
			params.add("%" + newCorporateName + "%");
		}
		
		query.append(" order by createTime desc");
		
		return hibernateBaseDao.findPage(query.toString(), currentPage, pageSize, params);
	}
	
	@Override
	public boolean checkDelete(String ids, StringBuilder errInfo) {
		
		for (String id : ids.split(",") ) {
			// 用户
			User user = this.findById(id);
			
			// 检查是否有账号引用本用户记录
			if ( !accountService.findByUser(id).isEmpty()) {
				errInfo.append(String.format("用户%s被账号使用，不能删除！", user.getName()));
				return false;
			}
			
			// 检查是否有用户角色引用本用户记录
			List<RoleRelation> relations = roleRelationService.findListByParams(id, null, null);
			if ( !relations.isEmpty() ) {
				errInfo.append(String.format("用户%s被角色关系使用，不能删除！", user.getName()));
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public User findNaturalUserByIdentity(String identity) {
		StringBuilder query = new StringBuilder(" from ").append(User.class.getName())
				.append(" where type = 1 and del = 0 and identity = ? order by createTime desc");
		List<User> users = hibernateBaseDao.findList(query.toString(), identity);
		if ( users.isEmpty() ) {
			return null;
		} else {
			return users.get(0);
		}
	}
	
	@Override
	public User findNaturalUserByMobile(String mobile) {
		StringBuilder query = new StringBuilder(" from ").append(User.class.getName())
				.append(" where type = 1 and del = 0 and mobile = ? order by createTime desc");
		List<User> users = hibernateBaseDao.findList(query.toString(), mobile);
		if ( users.isEmpty() ) {
			return null;
		} else {
			return users.get(0);
		}
	}
	
	@Override
	public User findCorporateUserByCreditCode(String creDitCode) {
		StringBuilder query = new StringBuilder(" from ").append(User.class.getName())
				.append(" where type = 2 and creditCode = ?");
		return hibernateBaseDao.getByHql(query.toString(), creDitCode);
	}

	@Override
	public User findCorporateUserByOrgCode(String orgCode) {
		StringBuilder query = new StringBuilder(" from ").append(User.class.getName())
				.append(" where type = 2 and orgCode = ?");
		return hibernateBaseDao.getByHql(query.toString(), orgCode);
	}

	@Override
	public User findCorporateUserByCorporteName(String corporateName) {
		StringBuilder query = new StringBuilder(" from ").append(User.class.getName())
				.append(" where type = 2 and corporateName = ?");
		return hibernateBaseDao.getByHql(query.toString(), corporateName);
	}

	@Override
	public boolean checkUnique(User info, StringBuilder errInfo) {
		// 自然人
		if ( info.getType() == UserTypeEnum.NATURAL.getNumber() ) {
			// 根据身份证号查询记录
			User user = findNaturalUserByIdentity(info.getIdentity());		
			// 验证唯一性：身份证
			if ( user != null && !user.getUuid().equalsIgnoreCase(info.getUuid()) ) {
				errInfo.append(String.format("身份证号【%s】已经存在", info.getIdentity()));
				return false;
			}
			
			// 根据手机号查询记录
			user = findNaturalUserByMobile(info.getMobile());
			// 验证唯一性：手机号
			if ( user != null && !user.getUuid().equalsIgnoreCase(info.getUuid()) ) {
				errInfo.append(String.format("手机号【%s】已经存在", info.getMobile()));
				return false;
			}
		} else {  // 法人
			User user = findCorporateUserByCorporteName(info.getCorporateName());
			if ( user != null && !user.getUuid().equalsIgnoreCase(info.getUuid()) ) {
				errInfo.append(String.format("企业/机构名称【%s】已经存在", info.getCorporateName()));
				return false;
			}
			
			user = findCorporateUserByOrgCode(info.getOrgCode());
			if ( user != null && !user.getUuid().equalsIgnoreCase(info.getUuid()) ) {
				errInfo.append(String.format("组织机构代码【%s】已经存在", info.getOrgCode()));
				return false;
			}
			
			// 企业法人
			if ( User.CORPORATE_TYPE.equalsIgnoreCase(info.getCorporateType()) ) {
				user = findCorporateUserByCreditCode(info.getCreditCode());
				if ( user != null && !user.getUuid().equalsIgnoreCase(info.getUuid()) ) {
					errInfo.append(String.format("企业工商注册号/统一社会信用代码【%s】已经存在", info.getCreditCode()));
					return false;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public List<User> findAll() {
		StringBuilder query = new StringBuilder(" from ").append(User.class.getName()).append(" where del = 0 order by createTime desc");
		return hibernateBaseDao.findList(query.toString());
	}

	@Override
	public List<User> findListByParamer(String areaId, String orgId) {
		
		StringBuilder query = new StringBuilder(" from ").append(User.class.getName()).append(" where type = 1 and del = 0 ");
		List<Object> params = new ArrayList<Object>();
		
		if ( StringUtil.isNotBlank(areaId) ){
			query.append(" and birthArea.uuid = ? or liveArea.uuid = ?");
			params.add(areaId);
			params.add(areaId);
		}
		
		if ( StringUtil.isNotBlank(orgId) ) {
			query.append(" and org is not null and org.uuid = ?");
			params.add(orgId);
		}

		return hibernateBaseDao.findList(query.toString(), params);
	}
	
	@Override
	@Transactional
	public boolean saveOrUpdateCompleteUser(User user, UserDetail detail) {
		if ( StringUtil.isBlank(user.getUuid()) ) {
			// 先保存详细信息
			hibernateBaseDao.save(detail);
			
			// 再保存基本信息
			user.setCreateTime(TimeUtil.getExactCurrentTime());
			user.setDetail(detail);
			hibernateBaseDao.save(user);
		} else {
			hibernateBaseDao.update(detail);
			hibernateBaseDao.update(user);
		}
		
		return true;
	}
	
	@Override
	public User getCurrentLoginUser() {
		
		// 获取当前登录账号
		Subject subject = SecurityUtils.getSubject();
		if ( !subject.isAuthenticated() ) {
			return null;
		}
		Session session = subject.getSession();
		String accountId = (String) session.getAttribute(WebConstants.ONLINE_ACCOUNT_ID);
		if ( StringUtil.isBlank(accountId) ) {
			return null;
		}
		Account account = accountService.findById(accountId);
		
		// 返回用户
		if ( account == null ) {
			return null;
		}
		return account.getUser();
	}

	@Override
	public PageObject<User> addMaskToSeceretInfo(PageObject<User> pages) {
		
		// 清空缓存中的对象，防止保存进数据库
		hibernateBaseDao.clear();
		
		List<User> users = pages.getDataList();
		for ( User user : users ) {
			// 给手机号增加掩码
			String mobileWithMask = addMaskToMobile(user.getMobile());
			user.setMobile(mobileWithMask);
			
			// 给身份证号增加掩码
			String identityWithMask = addMaskToIdentity(user.getIdentity());
			user.setIdentity(identityWithMask);
		}
		
		return pages;
	}
}