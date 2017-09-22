package com.gsww.uids.service.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.entity.JisSysviewCurrent;
import com.gsww.uids.service.JisSysviewCurrentService;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JisSysviewCurrentServiceTest {
	@Autowired
	private JisSysviewCurrentService jisSysviewCurrentService;
	
	private static Logger logger = LoggerFactory.getLogger(JisSysviewCurrentServiceTest.class);
	
	@Test
	public void findJisCurList(){
		try {
			List<JisSysviewCurrent> list=jisSysviewCurrentService.findJisCurList();
			System.out.println(list);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void findByIid(){
		int id=4059;
		try {
			JisSysviewCurrent jis=jisSysviewCurrentService.findByIid(id);
			System.out.println(jis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void delete(){
		try {
			JisSysviewCurrent entity=jisSysviewCurrentService.findByIid(4060);
			jisSysviewCurrentService.delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
