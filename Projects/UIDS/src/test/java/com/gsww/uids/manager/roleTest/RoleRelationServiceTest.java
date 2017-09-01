package com.gsww.uids.manager.roleTest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsww.common.entity.PageObject;
import com.gsww.uids.base.BaseTest;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.role.entity.Role;
import com.gsww.uids.manager.role.entity.RoleRelation;
import com.gsww.uids.manager.role.service.RoleRelationService;
import com.gsww.uids.manager.role.service.RoleService;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.service.UserService;

/**
 * 角色关系测试类
 * @author sunbw
 *
 */
public class RoleRelationServiceTest extends BaseTest {
	
	@Autowired
	private RoleRelationService roleRelationService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 测试根据uuid查询实体对象
	 */
	@Test
	public void findById(){
		String id = "8a929b015b143359015b14339bca0000";
		RoleRelation roleRelation = roleRelationService.findById(id);
		Assert.assertTrue(id.equals(roleRelation.getUuid()));
	}
	
	/**
	 * 测试saveOrUpdate方法
	 */
	@Test
	public void saveOrUpdateTest(){
		//机构
		Organization org = organizationService.findAll().get(0);
		//角色
		Role role = roleService.findAll().get(0);
		//用户
		User user = userService.findAll().get(0);
		
		RoleRelation roleRelation = new RoleRelation();
		roleRelation.setCreateTime("2017");
		roleRelation.setOrg(org);
		roleRelation.setRole(role);
		roleRelation.setUser(user);
		roleRelation.setUuid("8a929b015b143359015b14339bca0000");
		roleRelationService.saveOrUpdate(roleRelation);
		//断言可以从数据库中查处这条记录与之对比
		RoleRelation RoleRelation2 = roleRelationService.findById("8a929b015b143359015b14339bca0000");
		Assert.assertTrue(roleRelation.getCreateTime().equals(RoleRelation2.getCreateTime()));
		Assert.assertTrue(roleRelation.getOrg().getUuid().equals(RoleRelation2.getOrg().getUuid()));
		Assert.assertTrue(roleRelation.getRole().getUuid().equals(RoleRelation2.getRole().getUuid()));
		Assert.assertTrue(roleRelation.getUser().getUuid().equals(RoleRelation2.getUser().getUuid()));
	}
	
	/**
	 * 测试查找某个用户的所有角色分页
	 */
	@Test
	public void findPageTest(){
		Integer page = 1;
		Integer size = 10;
		String userId = "1";
		PageObject<RoleRelation> pageList = roleRelationService.findPage(RoleRelation.USER_RELATION_TYPE, userId,page, size);
		Assert.assertTrue(10 == pageList.getPageSize());
		Assert.assertTrue(1 == pageList.getCurrentPage());
	}
	
	
	/**
	 * 测试检查唯一性
	 */
	@Test
	public void checkRoleRelationUniqueTest(){
		RoleRelation roleRelation = roleRelationService.findById("8a929b015b143359015b14339bca0000");
		StringBuilder errInfo = new StringBuilder("测试唯一性");
		Boolean flag = roleRelationService.checkUnique(roleRelation, errInfo);
		Assert.assertTrue(flag == true);
	}
	
	/***
	 * 测试检查能否批量删除：如果有一个不能删除，则返回false
	 */
//	@Test
	public void checkDeleteTest(){
		
	}
	
	/**
	 * 测试查找**用户在**机构下的**角色的用户角色记录
	 * 
	 */
	@Test
	public void findUserRelationTest(){
		String orgId = "0";
		String roleId = "8a929b015b132d9b015b132de4270000";
		String userId = "1";
		List<RoleRelation> roleRelation = roleRelationService.findListByParams(RoleRelation.USER_RELATION_TYPE, userId, orgId, roleId);
		Assert.assertTrue(roleRelation.size() > 0);	
	}
	
	/**
	 * 测试根据不同参数，查询list
	 */
	@Test
	public void findListByParames(){
		List<RoleRelation> roleRelationList = roleRelationService.findListByParams(RoleRelation.USER_RELATION_TYPE, null, null, null);
		//RoleRelation表中有数据，此处断言roleRelationList不为空
		Assert.assertNotNull(roleRelationList);
		List<RoleRelation> roleRelationList1 = roleRelationService.findListByParams(RoleRelation.USER_RELATION_TYPE, "3", null, null);
		//RoleRelation表中userId = "3"时，没有数据，因此此处断言，roleRelationList1必定位空
		Assert.assertTrue(roleRelationList1.size() == 0);
	}
}
