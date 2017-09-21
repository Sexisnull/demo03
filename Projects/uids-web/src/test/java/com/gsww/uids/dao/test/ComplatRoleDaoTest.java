package com.gsww.uids.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.ComplatRoleDao;
import com.gsww.uids.entity.ComplatRole;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class ComplatRoleDaoTest {
	@Autowired
	private ComplatRoleDao complatRoleDao;
	@Test
	public void findByIid(){
		int iid=1;
		ComplatRole role=complatRoleDao.findByIid(iid);
		System.out.println(role.getName());
		assertTrue(true);
	}
	@Test
	public void findAll(){
		List<ComplatRole> list=complatRoleDao.findAll();
		System.out.println(list.get(1).getName());
		assertTrue(true);
	}
	@Test
	public void findByName(){
		String name="内容管理系统";
		List<ComplatRole> role=complatRoleDao.findByName(name);
		System.out.println(role.get(0).getName());
	}

}
