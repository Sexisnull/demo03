package com.gsww.uids.common;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsww.common.entity.PageObject;
import com.gsww.common.util.JsonUtils;
import com.gsww.common.util.ObjectJsonValueProcessor;
import com.gsww.uids.base.BaseTest;
import com.gsww.uids.manager.role.entity.RoleRelation;
import com.gsww.uids.manager.sys.entity.Area;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.entity.UserDetail;
import com.gsww.uids.manager.user.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class JsonUtilsTest extends BaseTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void datagridJsonTest() {
		// 用户是一个复杂对象，从数据库中查找一批用户
		PageObject<User> pages = userService.findPage(null, null, null, null, 1, 10);
		Assert.assertTrue(pages.getTotalCount() > 0);
		
		// 定制复杂字段
		JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Area.class, new ObjectJsonValueProcessor(new String[] {"uuid", "name"}, Area.class));
        config.registerJsonValueProcessor(UserDetail.class, new ObjectJsonValueProcessor(new String[] {"uuid"}, UserDetail.class));
        config.registerJsonValueProcessor(RoleRelation.class, new ObjectJsonValueProcessor(new String[] {"uuid"}, RoleRelation.class));
        JSONArray jsonArray = JSONArray.fromObject(pages, config);
        String sPages = new JsonUtils().datagridJson(jsonArray);
        
        JSONObject json = JSONObject.fromObject(sPages);
		String srows = json.getString("rows");
		JSONArray jsonRows = JSONArray.fromObject(srows);
		JSONObject headJson = JSONObject.fromObject(jsonRows.get(0));
		
		// 简单字段可以获取，复杂字段也可以获取
		Assert.assertNotNull(headJson.get("name"));
		Assert.assertNotNull(headJson.get("birthArea"));
	}
}
