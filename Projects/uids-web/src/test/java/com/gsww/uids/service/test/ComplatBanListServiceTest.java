package com.gsww.uids.service.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.entity.ComplatBanlist;
import com.gsww.uids.service.ComplatBanListService;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ComplatBanListServiceTest {
	@Autowired
	private ComplatBanListService complatBanListService;
	
	private static Logger logger = LoggerFactory.getLogger(ComplatBanListServiceTest.class);
	@Test
	public void findComplatbanList(){
		try {
			List<ComplatBanlist> list=complatBanListService.findComplatbanList();
			System.out.println(list);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void findByIid(){
		int iid=17;
		try {
			ComplatBanlist banList=complatBanListService.findByIid(iid);
			System.out.println(banList.getLoginname());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void delete(){
		try {
			ComplatBanlist banList=complatBanListService.findByIid(70);
			System.out.println(banList.getLoginname());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}

}
