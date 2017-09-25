package com.gsww.uids.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.JisSysviewHistoryDao;
import com.gsww.uids.entity.JisSysviewHistory;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class JisSysviewHistoryDaoTest {
	
	@Autowired
	private JisSysviewHistoryDao jisSysviewHistoryDao;
	@Test
	public void findByIid(){
		int iid=1;
		JisSysviewHistory history=jisSysviewHistoryDao.findByIid(iid);
		System.out.println(history.getObjectname());
		assertTrue(true);
	}
	@Test
	public void findAll(){
		List<JisSysviewHistory> list=jisSysviewHistoryDao.findAll();
		System.out.println(list.size());
		assertTrue(true);
	}
}
