package com.gsww.uids.manager.userTest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsww.common.util.TimeHelper;
import com.gsww.uids.base.BaseTest;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.entity.UserDetail;
import com.gsww.uids.manager.user.service.UserService;

public class UserServiceTest extends BaseTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void findPageTest() {
		Assert.assertTrue(userService.findPage("陶", "360", "130", null, 1, 10).getTotalCount() > 0);
	}
	
	@Test
	public void checkDeleteTest() {
		StringBuilder errInfo = new StringBuilder(100);
		Assert.assertFalse(userService.checkDelete("402880e55b76a9af015b76aa8ad50001", errInfo));
	}
	
	@Test
	public void checkUniqueTest() {
		// 从数据库里找一个用户
		User userInDb = userService.findByIdentity("450123198502178969");
		
		// 构建一个一样的用户
		User newUser = new User();
		newUser.setBirthArea(userInDb.getBirthArea());
		newUser.setCreateTime(TimeHelper.getCurrentTime());
		newUser.setDetail(new UserDetail());
		newUser.setIdentity(userInDb.getIdentity());
		newUser.setLiveArea(userInDb.getLiveArea());
		newUser.setMobile(userInDb.getMobile());
		newUser.setName(userInDb.getName());
		newUser.setNationality(userInDb.getNationality());
		newUser.setSex(userInDb.getSex());
		
		// 检查唯一性
		StringBuilder errInfo = new StringBuilder(100);
		Assert.assertFalse(userService.checkUnique(newUser, errInfo));
	}
	
	@Test
	public void findListByParamerTest() {
		// 从数据库里找一个用户
		User userInDb = userService.findByIdentity("450123198502178969");
		
		// 根据地域查找
		Assert.assertTrue(!userService.findListByParamer(userInDb.getBirthArea().getUuid()).isEmpty());
	}
	
	@Test
	public void saveOrUpdateCompleteUser() {
		// 从数据库里找一个用户
		User userInDb = userService.findByIdentity("450123198502178969");
		String oldName = userInDb.getName();
		UserDetail detailInDb = userInDb.getDetail();
		String oldContactAddress = detailInDb.getContactAddress();
		
		// 修改数据
		userInDb.setName(oldName + "_name");
		detailInDb.setContactAddress(oldContactAddress + "_address");
		
		// 更新
		userService.saveOrUpdateCompleteUser(userInDb, detailInDb);
		
		// 测试
		User newUser = userService.findByIdentity("450123198502178969");
		Assert.assertFalse(oldName.equalsIgnoreCase(newUser.getName()));
		Assert.assertFalse(newUser.getDetail().getContactAddress().equals(oldContactAddress));
	}
}
