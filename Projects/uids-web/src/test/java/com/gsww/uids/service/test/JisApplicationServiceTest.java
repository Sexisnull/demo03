package com.gsww.uids.service.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.entity.JisApplication;
import com.gsww.uids.service.JisApplicationService;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JisApplicationServiceTest {
	@Autowired
	private JisApplicationService jisApplicationService;
	
	private static Logger logger = LoggerFactory.getLogger(JisApplicationServiceTest.class);
	@Test
	public void save(){
		JisApplication entity=new JisApplication();
		entity.setName("u-i-d-s");
		try {
			jisApplicationService.save(entity);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void delete(){
		try {
			JisApplication entity=jisApplicationService.findByKey(27);
			jisApplicationService.delete(entity);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
		
	}
	@Test
	public void findByKey(){
		int iid=26;
		try {
			JisApplication entity=jisApplicationService.findByKey(iid);
			System.out.println(entity.getName());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void findByMark(){
		String mark="dzzz";
		try {
			JisApplication entity=jisApplicationService.findByMark(mark);
			System.out.println(entity.getName());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void getJisApplicationList(){
		try {
			List<Map<String, Object>> list=jisApplicationService.getJisApplicationList();
			System.out.println(list);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void findAppByRoleIds(){
		String roleId="1";
		List<Map<String, Object>> list=jisApplicationService.findAppByRoleIds(roleId);
		System.out.println(list);
		assertTrue(true);
	}

}
