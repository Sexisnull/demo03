package com.gsww.uids.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.JisSysviewCurrentDao;
import com.gsww.uids.entity.JisSysviewCurrent;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class JisSysviewCurrentDaoTest {

	@Autowired
	private JisSysviewCurrentDao jisSysviewCurrentDao;
	@Test
	public void findByIid(){
		int iid=12;
		JisSysviewCurrent jis=jisSysviewCurrentDao.findByIid(iid);
		System.out.println(jis.getObjectname());
		assertTrue(true);
	}
	@Test
	public void findAll(){
		List<JisSysviewCurrent> list=jisSysviewCurrentDao.findAll();
		System.out.println(list.size());
		assertTrue(true);
				
	}
}
