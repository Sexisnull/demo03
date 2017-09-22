package com.gsww.uids.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.dao.ComplatZoneDao;
import com.gsww.uids.entity.ComplatZone;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml")
public class ComplatZoneDaoTest {
	@Autowired
	private ComplatZoneDao complatZoneDao;
	@Test
	public void findByIid(){
		int iid=57;
		ComplatZone zone=complatZoneDao.findByIid(iid);
		System.out.println(zone.getName());
		assertTrue(true);
	}
	@Test
	public void findByNameAndLikeCodeId(){
		String nameInput="甘肃省1";
		String codeId="1001";
		List<ComplatZone> list=complatZoneDao.findByNameAndLikeCodeId(nameInput, codeId);
		System.out.println(list.get(0));
		assertTrue(true);
	}

}
