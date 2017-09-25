package com.gsww.uids.dao.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.JisSysviewDetailDao;
import com.gsww.uids.entity.JisSysviewDetail;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class JisSysviewDetailDaoTest {
	@Autowired
	private JisSysviewDetailDao jisSysviewDetailDao;
	@Test
	public void findByIid(){
		int iid=5765;
		JisSysviewDetail detail=jisSysviewDetailDao.findByIid(iid);
		System.out.println(detail);
		assertTrue(true);
	}

}
