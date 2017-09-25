package com.gsww.uids.service.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.entity.JisRoleobject;
import com.gsww.uids.service.JisRoleobjectService;

import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JisRoleobjectServiceTest {
	@Autowired
	private JisRoleobjectService jisRoleobjectService;
	@Test
	public void findByRoleIdAndType(){
		int roleId=7;
		int type=3;
		List<JisRoleobject> list=jisRoleobjectService.findByRoleIdAndType(roleId, type);
		System.out.println(list);
		assertTrue(true);
	}
	@Test
	public void modifyRoleApps(){
		String app="3";
		boolean bool=jisRoleobjectService.modifyRoleApps(null, app);
		System.out.println(bool);
	}
	@Test
	public void findObjectSize(){
		int role=3;
		int objectid=240;
		int type=0;
		int num=jisRoleobjectService.findObjectSize(role, objectid, type);
		System.out.println(num);
	}
	@Test
	public void add(){
		JisRoleobject roleObject = new JisRoleobject(7, 2, 1);
		jisRoleobjectService.add(roleObject.getRoleid(), roleObject.getObjectid(), roleObject.getType());
	}

}
