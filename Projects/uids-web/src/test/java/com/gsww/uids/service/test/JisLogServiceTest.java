package com.gsww.uids.service.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.entity.JisLog;
import com.gsww.uids.service.JisLogService;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JisLogServiceTest {
	@Autowired
	private JisLogService jisLogService;
	
	private static Logger logger = LoggerFactory.getLogger(JisLogServiceTest.class);
	@Test
	public void findLogList(){
		try {
			List<JisLog> list=jisLogService.findLogList();
			System.out.println(list);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void findBySpec(){
		String spec="qxjxxy登录系统";
		try {
			List<JisLog> list=jisLogService.findBySpec(spec);
			System.out.println(list.get(0).getIid());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void save(){
		JisLog log=new JisLog();
		log.setSpec("描述");;
		jisLogService.save(log);
		assertTrue(true);
	}

}
