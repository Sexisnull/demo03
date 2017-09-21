package com.gsww.uids.service.test;


import static org.junit.Assert.*;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.entity.JisSysview;
import com.gsww.uids.service.JisSysviewService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml") 
public class JisSysviewServiceTest {

	@Autowired
	JisSysviewService jisSysviewService;
	
	@Test
	public void getSysviewByIid(){
		JisSysview jisSysview = new JisSysview();
		try {
			int iid = 170513;
			jisSysview = jisSysviewService.findByIid(iid);
			System.out.println("获取单个业务信息"+JSONArray.fromObject(jisSysview).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void save(){
		try {
			JisSysview jisSysview = new JisSysview();
			jisSysview.setIid(12345);
			jisSysview.setObjectid("128");
			jisSysview.setObjectname("甘肃省");
			jisSysview.setState("C");
			jisSysview.setResult("TG");
			jisSysview.setOptresult(2);
			jisSysview.setSynctime("2015-11-10 09:49:13");
			jisSysview.setAppid(1);
			jisSysview.setCodeid("001");
			jisSysview.setOperatetype("修改机构");
			jisSysview.setTimes(1);
			jisSysview.setErrorspec("");
			jisSysviewService.save(jisSysview);
			System.out.println("保存同步信息");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

}
