package com.gsww.uids.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.JisApplicationDao;
import com.gsww.uids.entity.JisApplication;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class JisApplicationDaoTest {
	@Autowired
	private JisApplicationDao jisApplicationDao;
	@Test
	public void findByIid(){
		int iid=3;
		JisApplication app=jisApplicationDao.findByIid(iid);
		System.out.println(app.getName());
		assertTrue(true);
	}
	@Test
	public void findByMark(){
		String mark="jact";
		List<JisApplication> list=jisApplicationDao.findByMark(mark);
		System.out.println(list.get(0).getName());
		assertTrue(true);
	}
	@Test
	public void findByGroupId(){
		int groupId=210;
		List<JisApplication>list=jisApplicationDao.findByGroupId(groupId);
		System.out.println(list.get(0).getName());
		assertTrue(true);
	}
	@Test
	public void findAll(){
		List<JisApplication> list=jisApplicationDao.findAll();
		System.out.println(list.size());
	}

}
