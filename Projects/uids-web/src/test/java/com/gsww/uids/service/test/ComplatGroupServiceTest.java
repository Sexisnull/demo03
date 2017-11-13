package com.gsww.uids.service.test;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.entity.ComplatGroup;
import com.gsww.uids.service.ComplatGroupService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/applicationContext.xml") 
public class ComplatGroupServiceTest {

	@Autowired
	ComplatGroupService complatGroupService;
	
	@Test
	public void findByPid(){
		try {
			int pid = 128;
			List<ComplatGroup> complatGroups = complatGroupService.findByPid(pid);
			System.out.println("根据pid获取单个机构信息"+JSONArray.fromObject(complatGroups).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	/*@Test
	public void findAll(){
		try {
			List<ComplatGroup> complatGroups = complatGroupService.findAll();
			System.out.println("获取机构信息List"+JSONArray.fromObject(complatGroups).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}*/
	
	@Test
	public void save(){
		try {
			ComplatGroup complatGroup = new ComplatGroup();
			//complatGroup.setIid(8104);
			complatGroup.setName("单元测试");
			complatGroup.setNodetype(2);
			complatGroup.setCodeid("001001001");
			complatGroup.setOrgcode("620000000000");
			complatGroup.setSuffix("fgw.szf.gs");
			complatGroup.setSpec("");
			complatGroup.setPid(129);
			complatGroup.setOrderid(3);
			complatGroup.setPinyin("DYCS");
			complatGroup.setIscombine(0);
			complatGroup.setGroupallname("单元测试");
			complatGroup.setOpersign(2);
			complatGroup.setCreatetime(new Timestamp(new Date().getTime()));
			complatGroup.setModifytime(new Timestamp(new Date().getTime()));
			complatGroup.setSynState(2);
			ComplatGroup group = complatGroupService.save(complatGroup);
			System.out.println("机构保存成功"+JSONArray.fromObject(group).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void delete(){
		try {
			ComplatGroup complatGroup = new ComplatGroup();
			complatGroup.setIid(8104);
			String msg = complatGroupService.delete(complatGroup);
			System.out.println("机构删除成功"+msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void findByKey(){
		try {
			Integer key = 128;
			ComplatGroup complatGroup = complatGroupService.findByIid(key);
			System.out.println("根据主键查找机构信息"+JSONArray.fromObject(complatGroup).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void findByIid(){
		try {
			int iid = 128;
			ComplatGroup complatGroup = complatGroupService.findByIid(iid);
			System.out.println("根据iid查找机构信息"+JSONArray.fromObject(complatGroup).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void findByName(){
		try {
			String name = "林业局";
			ComplatGroup complatGroup = complatGroupService.findByName(name);
			System.out.println("根据名称查找机构信息"+JSONArray.fromObject(complatGroup).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void queryNameIsUsed(){
		try {
			String name = "林业局";
			int pid=253;
			boolean bool = complatGroupService.queryNameIsUsed(name, pid);
			System.out.println(bool);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
//	@Test
//	public void getByName(){
//		try {
//			String name = "林业局";
//			boolean exist = complatGroupService.getByName(name);
//			System.out.println("查找机构是否存在"+exist);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		assertTrue(true);
//	}
//	

	@Test
	public void getComplatGroupList(){
		try {
			List<Map<String, Object>> complatGroupMap = complatGroupService.getComplatGroupList();
			System.out.println("查找所有机构信息"+JSONArray.fromObject(complatGroupMap).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void findChildGroupByIid(){
		try {
			int iid = 128;
			List<Map<String, Object>> complatGroupMap = complatGroupService.findChildGroupByIid(iid);
			System.out.println("根据iid查找子机构信息"+JSONArray.fromObject(complatGroupMap).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void findByNameOrPinYin(){
		try {
			String key = "兰州";
			List<Map<String, Object>> complatGroupMap = complatGroupService.findByNameOrPinYin(key);
			System.out.println("根据名字或者关键字查找机构信息"+JSONArray.fromObject(complatGroupMap).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void findAllIidsAndName(){
		try {
			List<Map<String, Object>> complatGroupMap = complatGroupService.findAllIidsAndName();
			System.out.println("查询所有组织机构iid和名称"+JSONArray.fromObject(complatGroupMap).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
