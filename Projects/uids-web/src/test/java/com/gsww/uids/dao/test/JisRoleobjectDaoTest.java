package com.gsww.uids.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.JisRoleobjectDao;
import com.gsww.uids.entity.JisRoleobject;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class JisRoleobjectDaoTest {
	
	@Autowired
	private JisRoleobjectDao jisRoleobjectDao;
	@Test
	public void findByRoleidAndType(){
		Integer roleId=7;
		Integer type=0;
		List<JisRoleobject> list=jisRoleobjectDao.findByRoleidAndType(roleId, type);
		System.out.println(list.size());
		assertTrue(true);
	}
}
