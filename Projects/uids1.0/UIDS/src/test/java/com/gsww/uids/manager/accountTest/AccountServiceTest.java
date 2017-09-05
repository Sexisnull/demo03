package com.gsww.uids.manager.accountTest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsww.common.entity.DegreeEnum;
import com.gsww.common.entity.NationalityEnum;
import com.gsww.common.entity.PageObject;
import com.gsww.common.enums.AccountTypeEnum;
import com.gsww.common.util.TimeHelper;
import com.gsww.uids.base.BaseTest;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.entity.AccountDetail;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.sys.entity.Area;
import com.gsww.uids.manager.sys.service.AreaService;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.entity.UserDetail;
import com.gsww.uids.manager.user.service.UserService;

public class AccountServiceTest extends BaseTest {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationService orgService;
	
	@Test
	public void findPageTest() {
		
		// 为了简单，假设数据库中已经存在一些账号记录		
		// 1、搜索法人账号
		PageObject<Account> pages = accountService.findPage(AccountTypeEnum.CORPORATE.getNumber(), null, "万维", "622301198910012198", "甘肃万维", "ftest", 1, 10);
		Assert.assertTrue(pages.getTotalCount() == 1);

		// 2、搜索公务账号
		pages = accountService.findPage(AccountTypeEnum.GOVERNMENT.getNumber(), "ff8080815afdfee8015afe113cba0000", null, null, null, "gov", 1, 10);
		Assert.assertTrue(pages.getTotalCount() == 1);
		
		// 3、搜索个人账号
		pages = accountService.findPage(AccountTypeEnum.PUBLIC.getNumber(), null, "陶", "360121", null, "admin", 1, 10);
		Assert.assertTrue(pages.getTotalCount() == 1);
	}
	
	@Test
	public void isAdminTest() {
		Assert.assertTrue(accountService.isAdmin("402880e55b76a9af015b76aaf4500002"));
	}
	
	@Test
	public void checkUniqueTest() {
		// 1、尝试插入一个已经存在的账号
		// 查找一个记录
		Account accountInDb = accountService.findById("402880e55b76a9af015b76aaf4500002");
		// 构造一个和数据库中记录相同的记录
		Account newAccount = new Account();
		newAccount.setApp(accountInDb.getApp());
		newAccount.setBase64Pass(accountInDb.getBase64Pass());
		newAccount.setCreateTime(TimeHelper.getCurrentTime());
		newAccount.setName(accountInDb.getName());
		newAccount.setNickname(accountInDb.getNickname());
		newAccount.setPassword(accountInDb.getPassword());
		newAccount.setType(accountInDb.getType());
		newAccount.setUser(accountInDb.getUser());
		StringBuilder errInfo = new StringBuilder(100);
		Assert.assertTrue(!accountService.checkUnique(newAccount, errInfo));
		
		// 2、尝试插入一个新的不存在的记录
		Account newAccount2 = new Account();
		newAccount2.setApp(accountInDb.getApp());
		newAccount2.setBase64Pass(accountInDb.getBase64Pass());
		newAccount2.setCreateTime(TimeHelper.getCurrentTime());
		newAccount2.setName("uniqueAccount");
		newAccount2.setNickname("unique_account");
		newAccount2.setPassword(accountInDb.getPassword());
		newAccount2.setType(accountInDb.getType());
		newAccount2.setUser(accountInDb.getUser());		
		Assert.assertTrue(accountService.checkUnique(newAccount2, errInfo));
	}
	
	@Test
	public void bindNewUserTest() {
		// 找到一个没有绑定用户的账号
		Account account = accountService.findById("8a929bca5b8a1ac0015b8a33cbc6000e");
		Assert.assertNull(account.getUser());
		
		// 创建一个新用户
		User user = new User();
		Area area = areaService.findByName("城关区").get(0);
		user.setBirthArea(area);
		user.setCreateTime(TimeHelper.getCurrentTime());
		user.setIdentity("622420198909083746");
		user.setLiveArea(area);
		user.setMobile("18120342689");
		user.setName("阿尔高");
		user.setNationality(NationalityEnum.HAN.getNumber());
		user.setSex(1);
		UserDetail detail = new UserDetail();
		detail.setAge("28");
		detail.setDegree(DegreeEnum.ACADEMY.getNumber());
		user.setDetail(detail);
		
		// 插入之前，保证用户不存在
		StringBuilder errInfo = new StringBuilder(100);
		Assert.assertTrue(userService.checkUnique(user, errInfo));
		
		// 绑定新用户
		accountService.bindNewUser(account.getUuid(), user, detail);
		
		// 测试
		Assert.assertNotNull(userService.findByIdentity("622420198909083746"));
		Account accountAfterBind = accountService.findById("8a929bca5b8a1ac0015b8a33cbc6000e");
		Assert.assertNotNull(accountAfterBind.getUser());
		
		// 恢复数据
		accountAfterBind.setUser(null);
		accountService.saveOrUpdate(accountAfterBind);
	}
	
	@Test
	public void bindExistUserTest() {
		// 找到一个没有绑定用户的账号
		Account account = accountService.findById("8a929bca5b8a1ac0015b8a33cbc6000e");
		Assert.assertNull(account.getUser());
		
		// 绑定一个已经存在的用户
		Assert.assertNotNull(userService.findByIdentity("450123198502178969"));
		accountService.bindExistUser(account.getUuid(), "450123198502178969");
		
		// 测试
		Account accountAfterBind = accountService.findById("8a929bca5b8a1ac0015b8a33cbc6000e");
		Assert.assertNotNull(accountAfterBind.getUser());
		
		// 恢复数据
		accountAfterBind.setUser(null);
		accountService.saveOrUpdate(accountAfterBind);
	}
	
	@Test
	public void saveOrUpdateCompleteAccountTest() {
		// 找到一个公务账号
		Account account = accountService.findById("8a929bca5b80cb86015b80d1ce830002");
		AccountDetail detail = account.getAccountDetail();
		String oldNickName = account.getNickname();
		Organization oldOrganization = detail.getOrg();
		
		// 修改
		account.setNickname("newNickName");
		detail.setOrg(orgService.findByShortName("瓜州县"));
		accountService.saveOrUpdateCompleteAccount(account, detail);
		
		// 测试
		Account acccountAfterUpdate = accountService.findById("8a929bca5b80cb86015b80d1ce830002");
		Assert.assertFalse(oldNickName.equalsIgnoreCase(acccountAfterUpdate.getNickname()));
		Assert.assertFalse(oldOrganization == acccountAfterUpdate.getAccountDetail().getOrg());
	}
	
	@Test
	public void findListByParamsTest() {
		Assert.assertTrue(!accountService.findListByParams("402880e55b76a9af015b76aa8ad50001", "8a929b915b22683d015b227211a90012", null).isEmpty());
	}
	
	@Test
	public void findCorporateByAppAndOrgCodeTest() {
		Assert.assertTrue(!accountService.findCorporateByAppAndOrgCode("8a929b915b22683d015b227211a90012", "asdf12313").isEmpty());
	}
	
	@Test
	public void findCorporateByAppAndCreditCodeTest() {
		Assert.assertTrue(!accountService.findCorporateByAppAndCreditCode("8a929b915b22683d015b227211a90012", "121212121123123").isEmpty());
	}
}
