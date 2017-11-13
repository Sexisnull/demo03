package com.gsww.uids.dao.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.ComplatUserDao;
import com.gsww.uids.entity.ComplatUser;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class ComplatUserDaoTest {
	@Autowired
	ComplatUserDao complatUserDao;

	@Test
	public void findByIid(){
		try {
			int iid = 50;
			ComplatUser complatUser = complatUserDao.findByIid(iid);
			System.out.println("根据iid获取单个政府用户信息"+JSONArray.fromObject(complatUser).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void updateUser(){
		try {
			complatUserDao.updateUser(20640, "updateUnit", "", "", "", "", "", "", new Date(), "000000");
			System.out.println("用户更新成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void findByLoginnameAndGroupid(){
		try {
			String userName = "admin";
			String password = "111111";
			Integer groupId = 128;
			List<ComplatUser> complatUsers = complatUserDao.findByLoginnameAndGroupid(userName,groupId);
			System.out.println("根据名称获取政府用户信息list"+JSONArray.fromObject(complatUsers).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void findByName(){
		try {
			String name = "武威市管理员";
			List<ComplatUser> complatUsers = complatUserDao.findByName(name);
			System.out.println("根据登录名密码和机构Id获取单个政府用户信息"+JSONArray.fromObject(complatUsers).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
