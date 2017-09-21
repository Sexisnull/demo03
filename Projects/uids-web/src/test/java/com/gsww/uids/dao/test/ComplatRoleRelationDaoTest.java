package com.gsww.uids.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.ComplatRoleRelationDao;
import com.gsww.uids.entity.ComplatRolerelation;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class ComplatRoleRelationDaoTest {
	@Autowired
	private ComplatRoleRelationDao complatRoleRelationDao;
	/*@Test
	public void findByUserId(){
		int userId=269;
		List<ComplatRolerelation> list=complatRoleRelationDao.findByUserId(userId);
		System.out.println(list.get(0).getIid());
		assertTrue(true);
	}*/
	/*@Test
	public void findByRoleId(){
		int roleId=7;
		List<ComplatRolerelation> list=complatRoleRelationDao.findByRoleId(roleId);
		System.out.println(list.get(0).getUserId());
		
	}*/

}
