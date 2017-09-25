package com.gsww.uids.dao.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.JisUserdefinedDao;
import com.gsww.uids.entity.JisUserdefined;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class JisUserdefinedDaoTest {
	@Autowired
	private JisUserdefinedDao jisUserdefinedDao;
	@Test
	public void findByAppidAndLoginallname(){
		int appid=9;
		String loginAllName="jq_admin.jq.jq.sxq.gs";
		JisUserdefined jis=jisUserdefinedDao.findByAppidAndLoginallname(appid, loginAllName);
		System.out.println(jis.getLoginname());
		assertTrue(true);
	}
	@Test
	public void findByIid(){
		int iid =4;
		JisUserdefined jis=jisUserdefinedDao.findByIid(iid);
		System.out.println(jis.getLoginname());
		assertTrue(true);
	}

}
