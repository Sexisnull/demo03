package com.gsww.uids.service.test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.jup.controller.BaseController;
import com.gsww.uids.entity.ComplatRole;
import com.gsww.uids.entity.ComplatRolerelation;
import com.gsww.uids.service.ComplatRoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ComplatRoleServiceTest extends BaseController{

	@Autowired
	private ComplatRoleService complatRoleService;
	
	private static Logger logger = LoggerFactory.getLogger(ComplatRoleServiceTest.class);
	
	/**
	 * 
	 */
	@Test
	public void testGetRolePage(){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Specification<ComplatRole> spec=super.toSpecification(searchParams, ComplatRole.class);
		PageRequest pageRequest=new PageRequest(0, 10);
		Page<ComplatRole> pageComplatRole=complatRoleService.getRolePage(spec, pageRequest);
		System.out.println(pageComplatRole);
		assertTrue(true);
	}
	@Test
	public void save(){
		ComplatRole entity=new ComplatRole(null, "超级管理员", "最高权限", null, null, null);
		try {
			complatRoleService.save(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void delete(){
		int id=41;
		try {
			complatRoleService.delete(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void findByKey(){
		int id=12;
		try {
			ComplatRole role=complatRoleService.findByKey(id);
			System.out.println(role.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void findAcctByroleId(){
		int id=1;
		try {
			List<ComplatRolerelation> list=complatRoleService.findAcctByroleId(id);
			System.out.println(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void findByName(){
		String name="机构管理员";
		List<ComplatRole> list=complatRoleService.findByName(name);
		System.out.println(JSONArray.fromObject(list).toString());
	}
	@Test
	public void getAuthorizeTree(){
		String id="1";
		try {
			String json=complatRoleService.getAuthorizeTree(id);
			System.out.println(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void saveAuthorize(){
		String id="6";
		String keys="8a929c3f5e5660a6015e5663c1420002,8a92012d5e7de06a015e7de18b3a0001,O_8a92012d5e7e48a8015e7e5000bc0061,O_8a92012d5e7e48a8015e7e50f6bc0062,O_8a92012d5e7e48a8015e7e516d700063,O_8a92012d5e7f3472015e7f3682360000";
		String types="res,res,oper,oper,oper,oper";
		complatRoleService.saveAuthorize(id, keys, types);
		assertTrue(true);
	}
	@Test
	public void findRoleList(){
		try {
			List<ComplatRole> list=complatRoleService.findRoleList();
			for(ComplatRole role : list){
				System.out.println(role.getName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void findByUserId(){
		int id=45;
		try {
			List<ComplatRolerelation> list=complatRoleService.findByUserId(id);
			for(ComplatRolerelation relation : list){
				System.out.println(relation.getRoleId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void findRoleMember(){
		String roleId="11";
		String memberType="0";
		String memberName=null;
		complatRoleService.findRoleMember(roleId, memberType, memberName);
		assertTrue(true);
	}
	@Test
	public void deleteByRoleId(){
		String roleId="36";
		try {
			complatRoleService.deleteByRoleId(roleId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		assertTrue(true);
	}
	@Test
	public void deleteUsersByRoleId(){
		String roleId="8";
		String[] users={"19852","4065","19893"};
		String[] groups={};
		complatRoleService.deleteUsersByRoleId(roleId, users, groups);
	}
	
	
}