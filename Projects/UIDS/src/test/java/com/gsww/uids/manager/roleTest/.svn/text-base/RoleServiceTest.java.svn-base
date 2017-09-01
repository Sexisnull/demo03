package com.gsww.uids.manager.roleTest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsww.common.entity.PageObject;
import com.gsww.uids.base.BaseTest;
import com.gsww.uids.manager.role.entity.Role;
import com.gsww.uids.manager.role.service.RoleService;

/**
 * 角色业务接口测试类
 * @author sunbw
 *
 */

public class RoleServiceTest extends BaseTest{
		
	@Autowired
	private RoleService roleService;
	
	/**
	 * 测试saveOrUpdate方法
	 */
	@Test
	public void saveOrUpdateTest(){
		Role role = new Role();
		role.setCreateTime("1990");
		role.setDesc("单元测试");
		role.setIsDelete(0);
		role.setMark("mark");
		role.setName("单元测试");
		role.setUuid("8a929b015b132d9b015b132de4270000");//更新
	//	role.setUuid("1231312312");
		role.setValidStatus(0);
		
		roleService.saveOrUpdate(role);
		//断言可以从数据库中查出这条记录与之对比
		Role role2 = roleService.findById("8a929b015b132d9b015b132de4270000");
		Assert.assertTrue(role.getCreateTime().equals(role2.getCreateTime()));
		Assert.assertTrue(role.getDesc().equals(role2.getDesc()));
		Assert.assertTrue(role.getMark().equals(role2.getMark()));
		Assert.assertTrue(role.getName().equals(role2.getName()));
		Assert.assertTrue(role.getUuid().equals(role2.getUuid()));
		Assert.assertTrue(role.getIsDelete() == role2.getIsDelete());
		Assert.assertTrue(role.getValidStatus() == role2.getValidStatus());
	}
	
	
	/**
	 * 测试逻辑删除功能
	 * 
	 */
	@Test
	public void deleteTest(){
		//获得数据库中三条数据，做删除
		List<Role> roleList = roleService.findAll();
		Assert.assertNotNull(roleList);//此处测试了findAll功能
		String s0 = roleList.get(0).getUuid();
		String s1 = roleList.get(1).getUuid();
		String s2 = roleList.get(2).getUuid();
		String ids = s0+","+s1+","+s2;
		System.out.println(ids);
		roleService.delete(ids);
		System.out.println(roleService.findById(s0).getIsDelete());
		Assert.assertTrue(1 == roleService.findById(s0).getIsDelete());
		System.out.println(roleService.findById(s1).getIsDelete());
		Assert.assertTrue(1 == roleService.findById(s1).getIsDelete());
		System.out.println(roleService.findById(s2).getIsDelete());
		Assert.assertTrue(1 == roleService.findById(s2).getIsDelete());
		
		//还原回去数据
		Role role0 = roleService.findById(s0);
		role0.setIsDelete(0);
		roleService.saveOrUpdate(role0);
		Role role1 = roleService.findById(s1);
		role1.setIsDelete(0);
		roleService.saveOrUpdate(role1);
		Role role2 = roleService.findById(s2);
		role2.setIsDelete(0);
		roleService.saveOrUpdate(role2);
	}
	
	/**
	 * 测试分页方法
	 */
	@Test
	public void findPageTest(){
		Integer page = 1;
		Integer size = 10;
		String searchText = null;
		PageObject<Role> pageList = roleService.findPage(page, size, searchText);
		Assert.assertTrue(10 == pageList.getPageSize());
		Assert.assertTrue(1 == pageList.getCurrentPage());
	}
	
	/**
	 * 测试检查状态
	 */
	@Test
	public void checkStatusTest(){
		String ids = "";
		List<Role> rolelist = roleService.findAll();
		for(Role role : rolelist){
			ids += role.getUuid()+",";
		}
		ids.substring(0,ids.lastIndexOf(","));
		//检验所有的状态
		StringBuilder errInfo = new StringBuilder(100);
		boolean ret = roleService.checkDelete(ids, errInfo);
		
		//此处断言，因为是从数据库中查出来的数据，所以RoleRelation中存在关系，所以此处必定返回state = 0;
		Assert.assertTrue(ret);
	}
	
}
