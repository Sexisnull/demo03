package com.gsww.uids.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

import com.gsww.uids.entity.JisParameter;
import com.gsww.uids.service.JisParameterService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JisParameterServiceTest {
	@Autowired
	private JisParameterService jisParameterService;
	
	private static Logger logger = LoggerFactory.getLogger(JisParameterServiceTest.class);
	@Test
	public void save(){
		JisParameter entity=new JisParameter();
		try {
			jisParameterService.save(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public  void findByKey(){
		int iid=1;
		try {
			JisParameter parameter=jisParameterService.findByKey(iid);
			System.out.println(parameter.getSysName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	
}
