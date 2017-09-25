package com.gsww.uids.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.ComplatBanListDao;
import com.gsww.uids.entity.ComplatBanlist;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class ComplatBanListDaoTest {
	@Autowired
	private ComplatBanListDao complatBanListDao;
	@Test
	public void findByIid(){
		int iid =17;
		ComplatBanlist banList=complatBanListDao.findByIid(iid);
		System.out.println(banList.getLoginname());
		assertTrue(true);
	}
	@Test
	public void findAll(){
		List<ComplatBanlist> list=complatBanListDao.findAll();
		for(ComplatBanlist banList : list){
			System.out.println(banList.getLoginname());
		}
		assertTrue(true);
	}

}
