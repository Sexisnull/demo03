package com.gsww.uids.service.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.jup.controller.BaseController;
import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.service.ComplatOutsideuserService;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ComplatOutsideuserServiceTest extends BaseController{
	@Autowired
	private ComplatOutsideuserService complatOutSideUserService;
	@Test
	public void getOutsideUserPage(){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Specification<ComplatOutsideuser> spec=super.toSpecification(searchParams, ComplatOutsideuser.class);
		PageRequest pageRequest=new PageRequest(0, 10);
		Page<ComplatOutsideuser> pageComplatRole=complatOutSideUserService.getOutsideUserPage(spec, pageRequest);
		System.out.println(pageComplatRole);
		assertTrue(true);
	}
	@Test
	public void findByKey(){
		int iid=1;
		ComplatOutsideuser user=complatOutSideUserService.findByKey(iid);
		System.out.println(user.getLoginName());
		assertTrue(true);
	}
	@Test
	public void save(){
		ComplatOutsideuser user=new ComplatOutsideuser();
		user.setLoginName("uids");
		complatOutSideUserService.save(user);
		assertTrue(true);
	}
	@Test
	public void delete(){
		ComplatOutsideuser user=complatOutSideUserService.findByKey(10);
		if(user!=null){
			complatOutSideUserService.delete(user);
		}
		assertTrue(true);
	}
	@Test
	public void findAll(){
		List<ComplatOutsideuser> list=complatOutSideUserService.findAll();
		for(ComplatOutsideuser user : list){
			System.out.println(user.getIid());
		}
	}
	@Test
	public void findByNameOrPinYin(){
		String keyword="刘";
		List<Map<String, Object>> list=complatOutSideUserService.findByNameOrPinYin(keyword);
		System.out.println(list);
	}

}
