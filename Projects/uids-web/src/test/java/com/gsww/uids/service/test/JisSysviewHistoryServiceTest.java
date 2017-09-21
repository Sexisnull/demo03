package com.gsww.uids.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.entity.JisSysviewHistory;
import com.gsww.uids.service.JisSysviewHistoryService;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JisSysviewHistoryServiceTest {
	@Autowired
	private JisSysviewHistoryService jisSysviewHistoryService;
	private static Logger logger = LoggerFactory.getLogger(JisSysviewHistoryServiceTest.class);
	@Test
	public void findByIid(){
		int iid=1;
		try {
			JisSysviewHistory history=jisSysviewHistoryService.findByIid(iid);
			System.out.println(history.getObjectname());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
}
