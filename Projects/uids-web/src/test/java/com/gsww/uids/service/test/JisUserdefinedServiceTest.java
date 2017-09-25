package com.gsww.uids.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.entity.JisUserdefined;
import com.gsww.uids.service.JisUserdefinedService;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JisUserdefinedServiceTest {
	@Autowired
	private JisUserdefinedService jisUserdefinedService;
	private static Logger logger = LoggerFactory.getLogger(JisUserdefinedServiceTest.class);
	@Test
	public void findByAppidAndLoginAllName(){
		int appid=9;
		String loginAllName="jq_admin.jq.jq.sxq.gs";
		JisUserdefined define=jisUserdefinedService.findByAppidAndLoginAllName(appid, loginAllName);
		System.out.println(define.getIid());
		assertTrue(true);
	}
	@Test
	public void findByKey(){
		int iid=2;
		try {
			JisUserdefined define=jisUserdefinedService.findByKey(iid);
			System.out.println(define);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void save(){
		JisUserdefined define=new JisUserdefined();
		try {
			jisUserdefinedService.save(define);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}

}
