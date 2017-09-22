package com.gsww.uids.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.ComplatOutsideuserDao;
import com.gsww.uids.entity.ComplatOutsideuser;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class ComplatOutsideuserDaoTest {
	@Autowired
	private ComplatOutsideuserDao complatOutsideuserDao;
	@Test
	public void findByIid(){
		int iid=12;
		ComplatOutsideuser user=complatOutsideuserDao.findByIid(iid);
		System.out.println(user.getLoginName());
		assertTrue(true);
	}
	@Test
	public void findAll(){
		List<ComplatOutsideuser> list=complatOutsideuserDao.findAll();
		System.out.println(list.get(0).getDegree());
		assertTrue(true);
	}
	@Test
	public void updateOutsideuser(){
		int iid=13;
		complatOutsideuserDao.updateOutsideuser(iid);
		assertTrue(true);
	}
	@Test
	public void findByLoginName(){
		String logName="zcl1234";
		ComplatOutsideuser list=complatOutsideuserDao.findByLoginName(logName);
		System.out.println(list.getEmail());
		assertTrue(true);
		
	}
	
}
