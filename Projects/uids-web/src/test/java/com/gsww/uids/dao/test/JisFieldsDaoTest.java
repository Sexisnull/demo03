package com.gsww.uids.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.JisFieldsDao;
import com.gsww.uids.entity.JisFields;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class JisFieldsDaoTest {
	@Autowired
	private JisFieldsDao jisFieldsDao;
	@Test
	public void findByIid(){
		int iid=61;
		JisFields jis=jisFieldsDao.findByIid(iid);
		System.out.println(jis.getShowname());
		assertTrue(true);
	}
	@Test
	public void findFieldsType(){
		List<Integer> list=jisFieldsDao.findFieldsType();
		System.out.println(list);
	}

}
