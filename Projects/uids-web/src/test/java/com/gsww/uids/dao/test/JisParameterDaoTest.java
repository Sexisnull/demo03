package com.gsww.uids.dao.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.JisParameterDao;
import com.gsww.uids.entity.JisParameter;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class JisParameterDaoTest {
	@Autowired
	private JisParameterDao jisParameterDao;
	@Test
	public void findByIid(){
		int iid=1;
		JisParameter jis=jisParameterDao.findByIid(iid);
		System.out.println(jis.getSysUrl());
		assertTrue(true);
	}
}
