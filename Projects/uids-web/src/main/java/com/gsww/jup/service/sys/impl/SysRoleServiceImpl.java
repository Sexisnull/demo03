/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.dao.sys.SysMenuDao;
import com.gsww.jup.dao.sys.SysOperatorDao;
import com.gsww.jup.dao.sys.SysRoleAcctRelDao;
import com.gsww.jup.dao.sys.SysRoleDao;
import com.gsww.jup.dao.sys.SysRoleMenuRelDao;
import com.gsww.jup.dao.sys.SysRoleOperRelDao;
import com.gsww.jup.entity.sys.SysResourceTree;
import com.gsww.jup.entity.sys.SysRole;
import com.gsww.jup.entity.sys.SysRoleAcctRel;
import com.gsww.jup.entity.sys.SysRoleMenuRel;
import com.gsww.jup.entity.sys.SysRoleOperRel;
import com.gsww.jup.service.sys.SysRoleService;
import com.gsww.jup.util.NullHelper;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2014-7-28 下午07:29:25</p>
 * <p>类描述 : 角色管理业务实现类        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">lzxij</a>
 */
@Transactional
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleOperRelDao sysRoleOperRelDao;
	@Autowired
	private SysRoleMenuRelDao sysRoleMenuRelDao;
	@Autowired
	private SysRoleAcctRelDao sysRoleAcctRelDao;
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysOperatorDao sysOperatorDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Page<SysRole> getRolePage(Specification<SysRole> spec, PageRequest pageRequest) {
		return sysRoleDao.findAll(spec, pageRequest);
	}

	@Override
	public SysRole findByKey(String roleId) throws Exception {
		return sysRoleDao.findByRoleId(roleId);
	}

	@Override
	public void save(SysRole entity) throws Exception {
		sysRoleDao.save(entity);
	}

	@Override
	public void delete(String id) throws Exception {
		sysRoleMenuRelDao.delete(sysRoleMenuRelDao.findByRoleId(id));
		sysRoleOperRelDao.delete(sysRoleOperRelDao.findByRoleId(id));
		sysRoleDao.delete(sysRoleDao.findByRoleId(id));
	}

	@Override
	public String getAuthorizeTree(String roleId) throws Exception {
		// 1.获取资源及已授权记录
		StringBuffer resAndAccreditSql = new StringBuffer();
		resAndAccreditSql.append("select m.menu_id,m.menu_name,m.parent_menu_id,rm.role_menu_id from SYS_MENU m ");
		resAndAccreditSql.append("left join sys_role_menu_rel rm on m.menu_id=rm.menu_id and rm.role_id=? where m.menu_state='1' ");
		resAndAccreditSql.append("order by m.menu_seq asc");
		System.out.println(resAndAccreditSql);
		System.out.println(roleId);
		List<Map<String, Object>> resAndAccreditList = jdbcTemplate.queryForList(resAndAccreditSql.toString(), new String[] { roleId});
		// 2.获取操作及已授权记录
		StringBuffer operAndAccreditSql = new StringBuffer();
		operAndAccreditSql.append("select o.operator_id,o.menu_id,o.operator_name,ro.role_oper_id from sys_operator o ");
		operAndAccreditSql.append("left join sys_role_oper_rel ro on ro.operator_id=o.operator_id and ro.role_id=? ");
		operAndAccreditSql.append("where o.operator_state='1' order by o.menu_id,o.tab_index,o.operator_type,o.operator_level asc");
		System.out.println(operAndAccreditSql);
		List<Map<String, Object>> operAndAccreditList = jdbcTemplate.queryForList(operAndAccreditSql.toString(), new String[] { roleId});
		// 3.将资源及操作记录整合后放入List;
		List<SysResourceTree> list = new ArrayList<SysResourceTree>();
		for (Map<String, Object> map : resAndAccreditList) {
			SysResourceTree tree = new SysResourceTree();
			tree.setId(NullHelper.convertNullToNothingnull(map.get("MENU_ID")));
			tree.setName(NullHelper.convertNullToNothingnull(map.get("MENU_NAME")));
			tree.setParId(NullHelper.convertNullToNothingnull(map.get("PARENT_MENU_ID")));
			String roleResKey = NullHelper.convertNullToNothingnull(map.get("ROLE_MENU_ID"));
			tree.setChecked("".equals(roleResKey) ? false : true);
			tree.setHoriz(false);
			tree.setCheckbox(true);
			tree.setCustomType("res");
			tree.setCheckInteractParent(true);
			tree.setCheckInteractSub(true);
			tree.setUncheckInteractParent(true);
			tree.setUncheckInteractSub(true);
			list.add(tree);
		}
		for (Map<String, Object> map : operAndAccreditList) {
			SysResourceTree tree = new SysResourceTree();
			tree.setId("O_"+NullHelper.convertNullToNothingnull(map.get("OPERATOR_ID")));
			tree.setName(NullHelper.convertNullToNothingnull(map.get("OPERATOR_NAME")));
			tree.setParId(NullHelper.convertNullToNothingnull(map.get("MENU_ID")));
			String roleOperKey = NullHelper.convertNullToNothingnull(map.get("ROLE_OPER_ID"));
			tree.setChecked("".equals(roleOperKey) ? false : true);
			tree.setHoriz(true);
			tree.setCheckbox(true);
			tree.setCustomType("oper");
			tree.setCheckInteractParent(true);
			tree.setCheckInteractSub(true);
			tree.setUncheckInteractParent(true);
			tree.setUncheckInteractSub(true);
			list.add(tree);
		}
		
		// 4.创建树根
		SysResourceTree tree = new SysResourceTree();
		tree.setId("0");
		tree.setParId("-1");
		tree.setName("系统授权");
		// 5.递归生产完整的树
		createTree(tree, list);
		// 6.生产JSON
		JSONObject jsonObject = JSONObject.fromObject(tree);
		String jsonStr = jsonObject.toString();
		jsonStr = jsonStr.replaceAll("\"nodes\":null", "\"nodes\":\\[\\]"); // "nodes":null替换为"nodes":[]
//		System.out.println("[" + jsonStr + "]");
		return "[" + jsonStr + "]";
	}

	// 递归创建资源授权树
	private void createTree(SysResourceTree parentNode, List<SysResourceTree> list) {
		List<SysResourceTree> clist = new ArrayList<SysResourceTree>();
		for (SysResourceTree tree : list) {
			if (parentNode.getId().equals(tree.getParId())) {
				SysResourceTree node = new SysResourceTree();
				node.setId(tree.getId());
				node.setParId(tree.getParId());
				node.setName(tree.getName());
				node.setChecked(tree.isChecked());
				node.setHoriz(tree.isHoriz());
				node.setIcon(tree.getIcon());
				node.setCheckbox(true);
				node.setCustomType(tree.getCustomType());
				node.setCheckInteractParent(tree.isCheckInteractParent());
				node.setCheckInteractSub(tree.isCheckInteractSub());
				node.setUncheckInteractParent(tree.isUncheckInteractParent());
				node.setUncheckInteractSub(tree.isUncheckInteractSub());

				clist.add(node);
			}
		}
		// 孩子获取下一级孩子
		if (clist.size() > 0) {
			parentNode.setNodes(clist);
			for (SysResourceTree ctree : clist) {
				createTree(ctree, list);// 递归
			}
		}
	}

	@Override
	public void saveAuthorize(String roleId,String keys,String types) {
		String[] key = keys.split(",");
		String[] type = types.split(",");

		// 删除该角色下的资源和操作授权
		sysRoleOperRelDao.delete(sysRoleOperRelDao.findByRoleId(roleId));
		sysRoleMenuRelDao.delete(sysRoleMenuRelDao.findByRoleId(roleId));
		// 新增该角色下的资源和操作授权
		SysRoleMenuRel sysRoleMenuRel;
		SysRoleOperRel sysRoleOper;
		for (int i = 0; i < key.length; i++) {
			if ("res".equals(type[i])) {
				sysRoleMenuRel = new SysRoleMenuRel();
				sysRoleMenuRel.setRoleId(roleId);
				sysRoleMenuRel.setSysMenu(sysMenuDao.findOne(key[i]));
				sysRoleMenuRelDao.save(sysRoleMenuRel);
			} else if ("oper".equals(type[i])) { // 保存角色——操作数据
				sysRoleOper = new SysRoleOperRel();
				sysRoleOper.setRoleId(roleId);
				sysRoleOper.setSysOperator(sysOperatorDao.findOne(key[i].substring(2, key[i].length())));
				sysRoleOperRelDao.save(sysRoleOper);
			}
		}
	}

	/**
	 * 查询所有角色列表
	 * @return
	 * @throws Exception
	 */
	public List<SysRole> findRoleList() throws Exception {
		List<SysRole> list = new ArrayList<SysRole>();
		list = sysRoleDao.findAll();
		return list;
	}

	@Override
	public List<SysRoleAcctRel> findAcctByroleId(String roleId) throws Exception {
		return sysRoleAcctRelDao.findByRoleId(roleId);
	}

	@Override
	public List<SysRole> findByRoleNameAndRoleState(String roleNameInput, String state) throws Exception {
		return sysRoleDao.findByRoleNameAndRoleState(roleNameInput, state);
	}

}
