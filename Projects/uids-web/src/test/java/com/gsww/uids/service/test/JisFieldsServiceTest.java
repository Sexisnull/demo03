package com.gsww.uids.service.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

import com.gsww.uids.entity.JisFields;
import com.gsww.uids.service.JisFieldsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JisFieldsServiceTest {
	@Autowired
	private JisFieldsService jisFieldsService;
	@Test
	public void findByKey(){
		int iid=20;
		JisFields jis=jisFieldsService.findByKey(iid);
		System.out.println(jis.getFieldname());
		assertTrue(true);
	}
	@Test
	public void save(){
		JisFields jis=new JisFields("啦啦啦", null, null, null, null, null, null, null);
		jisFieldsService.save(jis);
		assertTrue(true);
	}
	@Test
	public void delete(){
		JisFields jis=jisFieldsService.findByKey(34);
		jisFieldsService.delete(jis);
		assertTrue(true);
	}
	@Test
	public void findAllJisFields(){
		List<JisFields> list=jisFieldsService.findAllJisFields();
		for(JisFields jis : list){
			System.out.println(jis.getShowname());
		}
		assertTrue(true);
	}
	@Test
	public void findFieldsType(){
		List<Integer> list=jisFieldsService.findFieldsType();
		System.out.println(list);
		assertTrue(true);
	}
}
