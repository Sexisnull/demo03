package com.gsww.uids.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertTrue;
import com.gsww.uids.entity.JisDatacall;
import com.gsww.uids.service.JisDatacallService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JisDatacallServiceTest {
	@Autowired
	private JisDatacallService jisDatacallService;
	
	private static Logger logger = LoggerFactory.getLogger(JisDatacallServiceTest.class);
	@Test
	public void save(){
		JisDatacall call=new JisDatacall();
		call.setContent("内容");
		try {
			jisDatacallService.save(call);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void delete(){
		try {
			JisDatacall call=jisDatacallService.findByKey(10);
			jisDatacallService.delete(call);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	} 
	@Test
	public void findByKey(){
		int iid=9;
		try {
			JisDatacall call=jisDatacallService.findByKey(iid);
			System.out.println(call.getContent());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
}
