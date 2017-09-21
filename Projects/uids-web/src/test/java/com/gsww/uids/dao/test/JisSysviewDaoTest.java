package com.gsww.uids.dao.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.JisSysviewDao;
import com.gsww.uids.entity.JisSysview;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class JisSysviewDaoTest {

	@Autowired
	private JisSysviewDao jisSysviewDao;
	
	@Test
	public void findByIid(){
		JisSysview jisSysview = new JisSysview();
		try {
			int iid = 170513;
			jisSysview = jisSysviewDao.findByIid(iid);
			System.out.println("获取单个同步信息"+JSONArray.fromObject(jisSysview).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void findAll(){
		List<JisSysview> jisSysviews = null;
		try {
		    jisSysviews = jisSysviewDao.findAll();
		    System.out.println("获取所有实时同步信息"+JSONArray.fromObject(jisSysviews).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
