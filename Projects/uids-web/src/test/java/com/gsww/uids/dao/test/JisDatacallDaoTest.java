package com.gsww.uids.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.JisDatacallDao;
import com.gsww.uids.entity.JisDatacall;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class JisDatacallDaoTest {
	@Autowired
	private JisDatacallDao jisDatacallDao;
	@Test
	public void findByIid(){
		int iid=1;
		JisDatacall call=jisDatacallDao.findByIid(iid);
		System.out.println(call.getResName());
		assertTrue(true);
	}
	@Test
	public void findByRemark(){
		String remark="XXkk";
		List<JisDatacall> list=jisDatacallDao.findByRemark(remark);
		System.out.println(list.get(0).getResName());
		assertTrue(true);
	}
	

}
