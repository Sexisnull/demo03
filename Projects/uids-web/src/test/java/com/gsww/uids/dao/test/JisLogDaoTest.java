package com.gsww.uids.dao.test;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertTrue;
import com.gsww.uids.dao.JisLogDao;
import com.gsww.uids.entity.JisLog;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class JisLogDaoTest {
	@Autowired
	private JisLogDao jisLogDao;
	@Test
	public void findAll(){
		List<JisLog> list=jisLogDao.findAll();
		System.out.println(list.size());
		assertTrue(true);
	}
	@Test
	public void findBySpec(){
		String spec="webservice接口调用登录系统";
		List<JisLog> list=jisLogDao.findBySpec(spec);
		System.out.println(list.size());
		assertTrue(true);
	}

}
