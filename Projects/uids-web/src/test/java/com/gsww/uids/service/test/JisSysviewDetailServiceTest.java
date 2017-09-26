package com.gsww.uids.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.entity.JisSysviewDetail;
import com.gsww.uids.service.JisSysviewDetailService;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JisSysviewDetailServiceTest {
	@Autowired
	private JisSysviewDetailService jisSysviewDetailService;
	@Test
	public void findByIid(){
		int iid=5765;
		JisSysviewDetail detail=jisSysviewDetailService.findByIid(iid);
		System.out.println(detail);
		assertTrue(true);
	}
	@Test
	public void delete(){
		JisSysviewDetail detail=jisSysviewDetailService.findByIid(3242);
		if(detail!=null){
			jisSysviewDetailService.delete(detail);
		}
		assertTrue(true);
	}
}
