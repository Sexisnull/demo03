package com.gsww.uids.dao.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.JisUserdetailDao;
import com.gsww.uids.entity.JisUserdetail;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class JisUserdetailDaoTest {
	@Autowired
	private JisUserdetailDao jisUserdetailDao;
	@Test
	public void findByUserid(){
		int userId =10831;
		JisUserdetail detail=jisUserdetailDao.findByUserid(userId);
		System.out.println(detail.getIid());
		assertTrue(true);
	}
	

}
