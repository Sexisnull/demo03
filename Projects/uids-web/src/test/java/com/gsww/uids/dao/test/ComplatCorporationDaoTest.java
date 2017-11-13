package com.gsww.uids.dao.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.ComplatCorporationDao;
import com.gsww.uids.entity.ComplatCorporation;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class ComplatCorporationDaoTest {
	@Autowired
	private  ComplatCorporationDao complatCorporationDao;
	@Test
	public void findByIid(){
		int iid=7;
		ComplatCorporation corporation=complatCorporationDao.findByIid(iid);
		System.out.println(corporation.getcardreNamePic());
		assertTrue(true);
	}
	
	@Test
	public void findByLoginName(){
		String loginName="test";
		ComplatCorporation corporation=complatCorporationDao.findByLoginName(loginName);
		System.out.println(corporation);
		assertTrue(true);
	}

}