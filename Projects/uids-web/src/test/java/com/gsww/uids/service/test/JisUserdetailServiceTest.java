package com.gsww.uids.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.entity.JisUserdetail;
import com.gsww.uids.service.JisUserdetailService;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JisUserdetailServiceTest {

	@Autowired
	private JisUserdetailService jisUserdetailService;
	private static Logger logger = LoggerFactory.getLogger(JisUserdetailServiceTest.class);
	@Test
	public void findByUserid(){
		int userId=1;
		try {
			JisUserdetail detail=jisUserdetailService.findByUserid(userId);
			System.out.println(detail.getCardid());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void save(){
		JisUserdetail detail=new JisUserdetail();
		detail.setComptel("detail");
		try {
			jisUserdetailService.save(detail);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
}
