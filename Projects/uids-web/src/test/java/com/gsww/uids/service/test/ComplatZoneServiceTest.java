package com.gsww.uids.service.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.entity.ComplatZone;
import com.gsww.uids.service.ComplatZoneService;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ComplatZoneServiceTest {
	@Autowired
	private ComplatZoneService complatZoneService;
	@Test
	public void getAll(){
		List<ComplatZone> list=	complatZoneService.getAll();
		System.out.println(list);
		assertTrue(true);
	}
	@Test
	public void fingByKey(){
		int iid=1;
		ComplatZone zone=complatZoneService.fingByKey(iid);
		System.out.println(zone.getName());
		assertTrue(true);
	}
	@Test
	public void save(){
		ComplatZone zone=new ComplatZone();
		zone.setName("武威市1");
		complatZoneService.save(zone);
		assertTrue(true);
	}
	@Test
	public void delete(){
		ComplatZone zone=complatZoneService.fingByKey(62);
		if(zone!=null){
		complatZoneService.delete(zone);
		}
		assertTrue(true);
	}
	@Test
	public void checkUniqueDeptName(){
		String nameInput="甘肃省1";
		String codeId="100";
		//String codeId="1001";
		List<ComplatZone> list=complatZoneService.checkUniqueDeptName(nameInput, codeId);
		System.out.println(list);
		assertTrue(true);
	}
	

}
