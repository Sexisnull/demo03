package com.gsww.uids.manager.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.entity.PageObject;
import com.gsww.common.enums.AccountTypeEnum;
import com.gsww.common.enums.UserTypeEnum;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeUtil;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.entity.AccountErrorTemp;
import com.gsww.uids.manager.account.entity.AccountMergeTemp;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.entity.UserDetail;
import com.gsww.uids.manager.user.service.UserService;
import com.gsww.uids.system.service.AbstractCommonService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 账号业务层实现
 * 
 * @author taolm
 *
 */
@Service
public class AccountServiceImpl extends AbstractCommonService implements AccountService {
	
	
	@Autowired
	private HibernateBaseDao hibernateBaseDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Override
	@Transactional
	public boolean saveOrUpdate(Account info) {
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
		Account info = null;
		for (String id : ids.split(",")) {
			info = hibernateBaseDao.getById(Account.class, id);
			info.setDel(1);
			hibernateBaseDao.update(info);
		}
	}
	
	@Override
	public Account findById(String id) {
		if (StringUtil.isBlank(id)) {
			return new Account();
		}
		return hibernateBaseDao.getById(Account.class, id);
	}

	@Override
	public PageObject<Account> findPage(int type, String name, String identity, String corporateName, String accountName, Integer currentPage, Integer pageSize) {

		List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder(" from ").append(Account.class.getName()).append(" where del = 0 and type = ?");
		params.add(type);
		
		// name、identity、corporateName在绑定的用户信息中
		if ( StringUtil.isNotBlank(name) || StringUtil.isNotBlank(identity)
			|| (type == AccountTypeEnum.CORPORATE.getNumber() && StringUtil.isNotBlank(corporateName)) ) {
			query.append(" and user is not null");
			
			if ( StringUtil.isNotBlank(name) ) {
				StringBuilder nameObj = new StringBuilder(name);
				query.append(" and ").append(generateSearchSql("user.name", nameObj));
				params.add("%" + nameObj + "%");
			}
			
			if ( StringUtil.isNotBlank(identity) ) {
				StringBuilder identityObj = new StringBuilder(identity);
				query.append(" and ").append(generateSearchSql("user.identity", identityObj));
				params.add("%" + identityObj + "%");
			}
			
			if ( type == AccountTypeEnum.CORPORATE.getNumber() && StringUtil.isNotBlank(corporateName) ) {
				StringBuilder corporateNameObj = new StringBuilder(corporateName);
				query.append(" and ").append(generateSearchSql("user.corporateName", corporateNameObj));				
				params.add("%" + corporateNameObj + "%");
			}
		}
		
		// 账号名：各种类型账号都有账号名
		if ( StringUtil.isNotBlank(accountName) ) {
			StringBuilder accountNameObj = new StringBuilder(accountName);
			query.append(" and ").append(generateSearchSql("name", accountNameObj));				
			params.add("%" + accountNameObj + "%");
		}		
		query.append(" order by createTime desc");
		
		return hibernateBaseDao.findPage(query.toString(), currentPage, pageSize, params);
	}
	
	@Override
	public boolean checkDelete(String ids, StringBuilder errInfo) {		
		return true;
	}
	
	@Override
	public List<Account> findByAppAndAccountName(String appId, String accountName) {
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder query = new StringBuilder(" from ").append(Account.class.getName())
				.append(" where del = 0 ");
		
		if (StringUtil.isNotBlank(appId)) {
			query.append(" and app.uuid = ? ");
			params.add(appId);
		}
		
		if (StringUtil.isNotBlank(accountName)) {
			query.append(" and name = ? ");
			params.add(accountName);
		}
		
		return hibernateBaseDao.findList(query.toString(), params);
	}
	
	@Override
	public Account findByClientIdAndAccountName(String clientId, String accountName) {
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder query = new StringBuilder(" from ").append(Account.class.getName())
				.append(" where del = 0 and app.clientId = ? and name = ?");
		
		params.add(clientId);
		params.add(accountName);
		
		List<Account> accounts = hibernateBaseDao.findList(query.toString(), params);
		if ( accounts.isEmpty() ) {
			return null;
		} else {
			return accounts.get(0);
		}
	}
	
	@Override
	public boolean checkUnique(Account info, StringBuilder errInfo) {
		
		// 查找应用下的账号名记录
		List<Account> accounts = findByAppAndAccountName(info.getApp().getUuid(), info.getName());
		
		// 唯一性检查：某应用下的账号名应当是唯一的
		if ( !accounts.isEmpty() && !accounts.get(0).getUuid().equalsIgnoreCase(info.getUuid()) ) {
			errInfo.append(String.format("应用【%s】下的账号名%s已存在", info.getApp().getName(), info.getName()));
			return false;
		}
			
		return true;
	}
	
	@Override
	public List<Account> findByUser(String userId) {
		StringBuilder query = new StringBuilder(" from ").append(Account.class.getName())
				.append(" where del = 0 and user is not null and user.uuid = ? order by createTime desc");
		return hibernateBaseDao.findList(query.toString(), userId);
	}
	
	@Override
	@Transactional
	public boolean bindNewUser(String accountId, User newUser, UserDetail newDetail) {
		// 保存新增用户
		userService.saveOrUpdateCompleteUser(newUser, newDetail);
		
		// 绑定用户
		Account account = findById(accountId);
		account.setUser(newUser);
		saveOrUpdate(account);
		
		return true;
	}
	
	@Override
	@Transactional
	public boolean bindExistNaturalUser(String accountId, String userIdentity) {
		// 查找用户
		User user = userService.findNaturalUserByIdentity(userIdentity);
		if ( user == null ) {
			return false;
		}
		
		// 绑定账号
		Account account = findById(accountId);
		account.setUser(user);
		saveOrUpdate(account);
		
		return true;
	}

	@Override
	public boolean bindExistCorporateUser(String accountId, String orgCode) {
		// 查找用户
		User user = userService.findCorporateUserByOrgCode(orgCode);
		if ( user == null ) {
			return false;
		}
		
		// 绑定账号
		Account account = findById(accountId);
		account.setUser(user);
		saveOrUpdate(account);
		
		return true;
	}

	@Override
	public List<Account> findListByParams(String userId, String appId) {
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder(" from ").append(Account.class.getName())
				.append(" where del = 0 ");
		
		if ( StringUtil.isNotBlank(userId) ) {
			query.append(" and user is not null and user.uuid = ?");
			params.add(userId);
		}
		
		if ( StringUtil.isNotBlank(appId) ) {
			query.append(" and app.uuid = ?");
			params.add(appId);
		}
		
		query.append(" order by createTime desc");
		
		return hibernateBaseDao.findList(query.toString(), params);
	}
	
	@Override
	public void mergeAfterUpdate(String tableinfo) {
		JSONArray jsonArr = JSONArray.fromObject(tableinfo);
		for(int i = 0; i < jsonArr.size(); i++){
			JSONObject jsonObject = jsonArr.getJSONObject(i);
			AccountMergeTemp amt = (AccountMergeTemp) JSONObject.toBean(jsonObject, AccountMergeTemp.class);
			Account account = new Account();
			account.setApp(applicationService.findById(amt.getAppId()));
			account.setCreateTime(amt.getCreateTime());
			account.setDel(amt.getDel());
			account.setName(amt.getName());
			account.setNickname(amt.getNickname());
			account.setPassword(amt.getPassword());
			account.setStatus(amt.getStatus());
			account.setType(amt.getType());
			account.setUuid(amt.getId());
			saveOrUpdate(account);//更新到数据库中
		}
		deleteErrorTemp();//清空错误账号信息
		deleteMergeTemp();//清空合并的账号信息
	}

	@Override
	@Transactional
	public boolean saveOrUpdate(AccountErrorTemp info) {
		if (StringUtil.isBlank(info.getUuid())) {
			hibernateBaseDao.save(info);
		} else {
			hibernateBaseDao.update(info);
		}
		return true;
	}

	@Override
	@Transactional
	public boolean saveOrUpdate(AccountMergeTemp info) {
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
		String sql = " delete  from UIDS_ACCOUNT_ERROR_TEMP ";
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery(sql).executeUpdate();
	}

	@Override
	@Transactional
	public void deleteMergeTemp() {
		String sql = " delete  from UIDS_ACCOUNT_MERGE_TEMP ";
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery(sql).executeUpdate();
	}

	@Override
	public List<AccountErrorTemp> findErrAll() {
		StringBuilder query = new StringBuilder(" from ").append(AccountErrorTemp.class.getName());
		return hibernateBaseDao.findList(query.toString());
	}

	@Override
	public List<AccountMergeTemp> findMergeAll() {
		StringBuilder query = new StringBuilder(" from ").append(AccountMergeTemp.class.getName());
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
	public boolean validate(String appId, String accountName, String password) {
		// 验证appId和accountName
		List<Account> accounts = this.findByAppAndAccountName(appId, accountName);
		if ( accounts.isEmpty() ) {
			return false;
		}
		Account account = accounts.get(0);
		
		// 将密码加密
		Application app = applicationService.findById(appId);
		SimpleHash simpleHash = new SimpleHash(app.getAccountEncryptType(), password, app.getAccountEncryptSalt(),
				app.getAccountEncryptIterations());
		String inputEncryptPassword = simpleHash.toString();
		
		// 验证密码
		return account.getPassword().equals(inputEncryptPassword);	
	}

	@Override
	public boolean isAccountTypeCoincidentWithUserType(int accountType, int userType) {
		if ( accountType == AccountTypeEnum.CORPORATE.getNumber() ) {
			return userType == UserTypeEnum.CORPORATE.getNumber();
		} else {
			return userType == UserTypeEnum.NATURAL.getNumber();
		}
	}

	@Override
	public Account findMainAccount(User user, Account loginAccount, Application loginApp) {
		
		// 选择主账号的策略：优先顺序：
		// 1、先选择单点登录应用下的账号，如果应用下有多个账号，则选择最新的一个；
		// 2、选择统一身份认证下的账号；如果统一身份认证下有多个账号，则选择最新的一个；
		// 3、选择登录的账号
		
		// 查找单点登录应用下的账号
		StringBuilder query = new StringBuilder(" from ").append(Account.class.getName())
				.append(" where del = 0 and user is not null and user.uuid = ? and app.uuid = ? order by createTime desc");
		List<Account> accounts = hibernateBaseDao.findList(query.toString(), user.getUuid(), loginApp.getUuid());		
		if ( !accounts.isEmpty() ) {
			return accounts.get(0);
		}
		
		// 查找统一身份认证系统下的账号
		query = new StringBuilder(" from ").append(Account.class.getName())
				.append(" where del = 0 and user is not null and user.uuid = ? and app.type = ? order by createTime desc");
		accounts = hibernateBaseDao.findList(query.toString(), user.getUuid(), Application.SYSTEM_APP_TYPE);		
		if ( !accounts.isEmpty() ) {
			return accounts.get(0);
		}
		
		// 返回登录的账号
		return loginAccount;
	}

	@Override
	public PageObject<Account> addMaskToSeceretInfo(PageObject<Account> pages) {
		
		// 清空缓存中的对象，防止保存进数据库
		hibernateBaseDao.clear();
				
		List<Account> accounts = pages.getDataList();
		for ( Account account : accounts ) {
			// 给账号中的手机号增加掩码
			String phoneWithMask = addMaskToMobile(account.getPhone());
			account.setPhone(phoneWithMask);
			
			// 给用户中的手机号和身份证号增加掩码
			User user = account.getUser();
			if ( user != null ) {
				// 给手机号增加掩码
				String mobileWithMask = addMaskToMobile(user.getMobile());
				user.setMobile(mobileWithMask);
				
				// 给身份证号增加掩码
				String identityWithMask = addMaskToIdentity(user.getIdentity());
				user.setIdentity(identityWithMask);
			}
		}
		
		return pages;
	}
}
