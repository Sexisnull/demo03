package com.gsww.uids.dao.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.ComplatGroupDao;
import com.gsww.uids.entity.ComplatGroup;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class ComplatGroupDaoTest {

	@Autowired
	ComplatGroupDao complatGroupDao;
	
	@Test
	public void findByPid(){
		try {
			int pid = 128;
			List<ComplatGroup> complatGroups = complatGroupDao.findByPid(pid);
			System.out.println("根据pid获取单个机构信息"+JSONArray.fromObject(complatGroups).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void findByIid(){
		try {
			int iid = 128;
			ComplatGroup complatGroup = complatGroupDao.findByIid(iid);
			System.out.println("根据iid查找机构信息"+JSONArray.fromObject(complatGroup).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void findByName(){
		try {
			String name = "林业局";
			List<ComplatGroup> complatGroups = complatGroupDao.findByName(name);
			System.out.println("根据名称查找机构信息"+JSONArray.fromObject(complatGroups).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
}
