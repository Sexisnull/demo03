package com.gsww.jup.sysview.test;


import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.service.JisSysviewService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml") 
public class JisSysviewServiceTest {

	@Autowired
	JisSysviewService jisSysviewService;
	
	@Test
	public void testBusinessInfoByOpuserId(){
		assertTrue(true);
	}
	
	@Test
	public void getSysviewByIid(){
		try {
			/*String businessId= "8a9297115576bc9b015576bc9b110000";
			BusinessInfo businessInfo = businessInfoService.getBusinesstById(businessId);
			System.out.println("获取单个业务信息"+JSONArray.fromObject(businessInfo).toString());*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

}
