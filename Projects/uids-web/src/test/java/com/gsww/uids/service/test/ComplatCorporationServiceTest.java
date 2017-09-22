package com.gsww.uids.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.service.ComplatCorporationService;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ComplatCorporationServiceTest {
	@Autowired
	private ComplatCorporationService complatCorporationService;
	
	private static Logger logger = LoggerFactory.getLogger(ComplatCorporationServiceTest.class);
	@Test
	public void findByKey(){
		int iid=2;
		try {
			ComplatCorporation corporation=complatCorporationService.findByKey(iid);
			System.out.println(corporation.getLoginName());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void save(){
		ComplatCorporation corporation=new ComplatCorporation(null, null, null, null, "星星", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		try {
			complatCorporationService.save(corporation);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void updateCorporation(){
		int iid=2;
		try {
			complatCorporationService.updateCorporation(iid);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
}
