package com.gsww.uids.common;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsww.common.entity.PageObject;
import com.gsww.uids.base.BaseTest;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PageObjectTest extends BaseTest {
	
	@Autowired
	private UserService userService;

	@Test
	public void toJSONStringTest() {
		// 用户是一个复杂对象，从数据库中查找一批用户
		PageObject<User> pages = userService.findPage(null, null, null, null, 1, 10);
		Assert.assertTrue(pages.getTotalCount() > 0);
		
		String sPages = pages.toJSONString();
		JSONObject json = JSONObject.fromObject(sPages);
		String srows = json.getString("rows");
		JSONArray jsonArray = JSONArray.fromObject(srows);
		JSONObject headJson = JSONObject.fromObject(jsonArray.get(0));
		
		// 简单字段可以获取，复杂字段获取不到
		Assert.assertNotNull(headJson.get("name"));
		Assert.assertNull(headJson.get("birthArea"));
	}
}
