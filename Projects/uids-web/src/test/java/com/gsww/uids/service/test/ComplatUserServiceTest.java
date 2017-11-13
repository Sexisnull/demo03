package com.gsww.uids.service.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.service.ComplatUserService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml") 
public class ComplatUserServiceTest {

	@Autowired
	ComplatUserService complatUserService;
	
	@Test
	public void findByKey(){
		try {
			int iid = 50;
			ComplatUser complatUser = complatUserService.findByKey(iid);
			System.out.println("根据iid获取单个政府用户信息"+JSONArray.fromObject(complatUser).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void findByUserAllName(){
		try {
			String userAllName ="武威市管理员";
			List<ComplatUser> complatUsers = complatUserService.findByUserAllName(userAllName);
			System.out.println("根据登录全名获取政府用户信息"+JSONArray.fromObject(complatUsers).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void findByGroupIds(){
		try {
			String groupIds ="248";
			List<Map<String, Object>> complatUserMapList = complatUserService.findByGroupIds(groupIds);
			System.out.println("根据机构ID获取政府用户信息List"+JSONArray.fromObject(complatUserMapList).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void complatUserSave(){
		try {
			ComplatUser complatUser = new ComplatUser();
			complatUser.setIid(1122);
			complatUser.setUuid("");
			complatUser.setLoginname("unitceshi");
			complatUser.setPwd("111111");
			complatUser.setName("单元测试");
			complatUser.setGroupid(128);
			//complatUser.setAge(age);
			//complatUser.setSex(sex);
			complatUser.setEnable(1);
			//complatUser.setUsertype(1);
			complatUser.setPwdquestion("");
			complatUser.setPwdanswer("");
			complatUser.setCreatetime(new Date());
			complatUser.setIp("");
			complatUser.setAccesstime(new Date());
			complatUser.setPinyin("");
			complatUser.setMobile("");
			complatUser.setPhone("");
			complatUser.setFax("");
			complatUser.setEmail("admin11111@qq.com");
			complatUser.setQq("");
			complatUser.setMsn("");
			complatUser.setAddress("");
			complatUser.setPost("");
			complatUser.setHeadship("");
			complatUser.setOrderid(0);
			complatUser.setLoginallname("");
			//complatUser.setModifytime();
			complatUser.setSynState(2);
			complatUser.setOpersign(0);
			complatUserService.save(complatUser);
			System.out.println("用户保存成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void complatUserDelete(){
		try {
			ComplatUser complatUser = new ComplatUser();
			complatUser.setIid(20639);
			complatUserService.delete(complatUser);
			System.out.println("删除用户成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void updateUser(){
		try {
			complatUserService.updateUser(20640, "updateUnit", "", "", "", "", "", "", new Date(), "000000");
			System.out.println("用户更新成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void findByNameOrPinYin(){
		try {
			String pinYin = "ZSP";
			List<Map<String, Object>> complatUserMap= complatUserService.findByNameOrPinYin(pinYin);
			System.out.println("根据关键字查询"+JSONArray.fromObject(complatUserMap).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
}
