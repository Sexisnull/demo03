package com.gsww.uids.service.impl;

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
import com.gsww.jup.dao.sys.SysRoleMenuRelDao;
import com.gsww.jup.dao.sys.SysRoleOperRelDao;
import com.gsww.jup.entity.sys.SysResourceTree;
import com.gsww.jup.entity.sys.SysRoleMenuRel;
import com.gsww.jup.entity.sys.SysRoleOperRel;
import com.gsww.jup.util.NullHelper;
import com.gsww.jup.util.StringHelper;
import com.gsww.uids.dao.ComplatRoleDao;
import com.gsww.uids.dao.JisRoleobjectDao;
import com.gsww.uids.entity.ComplatRole;
import com.gsww.uids.entity.JisRoleobject;
import com.gsww.uids.service.ComplatRoleService;

@Transactional
@Service("complatRoleService")
public class ComplatRoleServiceImpl implements ComplatRoleService {
	@Autowired
	private ComplatRoleDao roleDao;
	@Autowired
	private JisRoleobjectDao jisRoleobjectDao;
	// 操作
	@Autowired
	private SysOperatorDao sysOperatorDao;
	// 角色菜单关系
	@Autowired
	private SysRoleMenuRelDao sysRoleMenuRelDao;
	// 菜单
	@Autowired
	private SysMenuDao sysMenuDao;
	// 角色操作
	@Autowired
	private SysRoleOperRelDao sysRoleOperRelDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Page<ComplatRole> getRolePage(Specification<ComplatRole> spec,
			PageRequest pageRequest) {
		return roleDao.findAll(spec, pageRequest);
	}

	@Override
	public void save(ComplatRole entity) throws Exception {
		roleDao.save(entity);
	}

	@Override
	public void delete(int id) throws Exception {

		roleDao.delete(id);
	}

	@Override
	public ComplatRole findByKey(int id) throws Exception {
		return roleDao.findByIid(id);
	}

	@Override
	public List<JisRoleobject> findAcctByroleId(Integer roleId) {
		return jisRoleobjectDao.findByRoleidAndType(roleId, 0);
	}

	@Override
	public List<ComplatRole> findByName(String name) {
		return roleDao.findByName(name);
	}

	@Override
	public String getAuthorizeTree(String id) throws Exception {
		// 获取资源及授权记录
		StringBuffer resAndAccreditSql = new StringBuffer();
		resAndAccreditSql
				.append("select m.menu_id,m.menu_name,m.parent_menu_id,rm.role_menu_id from SYS_MENU m ");
		resAndAccreditSql
				.append("left join sys_role_menu_rel rm on m.menu_id=rm.menu_id and rm.role_id=? where m.menu_state='1' ");
		resAndAccreditSql.append("order by m.menu_seq asc");
		System.out.println(resAndAccreditSql);
		System.out.println(id);
		List<Map<String, Object>> resAndAccreditList = jdbcTemplate
				.queryForList(resAndAccreditSql.toString(), new String[] { id });
		// 获取操作及授权记录
		StringBuffer operAndAccreditSql = new StringBuffer();
		operAndAccreditSql
				.append("select o.operator_id,o.menu_id,o.operator_name,ro.role_oper_id from sys_operator o ");
		operAndAccreditSql
				.append("left join sys_role_oper_rel ro on ro.operator_id=o.operator_id and ro.role_id=? ");
		operAndAccreditSql
				.append("where o.operator_state='1' order by o.menu_id,o.tab_index,o.operator_type,o.operator_level asc");
		System.out.println(operAndAccreditSql);
		List<Map<String, Object>> operAndAccreditList = jdbcTemplate
				.queryForList(operAndAccreditSql.toString(),
						new String[] { id });
		// 将资源及操作记录整合后放入list
		List<SysResourceTree> list = new ArrayList<SysResourceTree>();
		for (Map<String, Object> map : resAndAccreditList) {
			SysResourceTree tree = new SysResourceTree();
			tree.setId(NullHelper.convertNullToNothingnull(map.get("MENU_ID")));
			tree.setName(NullHelper.convertNullToNothingnull(map
					.get("MENU_NAME")));
			tree.setParId(NullHelper.convertNullToNothingnull(map
					.get("PARENT_MENU_ID")));
			String roleResKey = NullHelper.convertNullToNothingnull(map
					.get("ROLE_MENU_ID"));
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
			tree.setId("O_"
					+ NullHelper.convertNullToNothingnull(map
							.get("OPERATOR_ID")));
			tree.setName(NullHelper.convertNullToNothingnull(map
					.get("OPERATOR_NAME")));
			tree.setParId(NullHelper.convertNullToNothingnull(map
					.get("MENU_ID")));
			String roleOperKey = NullHelper.convertNullToNothingnull(map
					.get("ROLE_OPER_ID"));
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
		jsonStr = jsonStr.replaceAll("\"nodes\":null", "\"nodes\":\\[\\]");
		return "[" + jsonStr + "]";
	}

	// 递归创建资源授权树
	private void createTree(SysResourceTree parentNode,
			List<SysResourceTree> list) {
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
	public void saveAuthorize(String id, String keys, String types) {
		String[] key = keys.split(",");
		String[] type = types.split(",");

		// 删除该角色下的资源和操作授权
		sysRoleOperRelDao.delete(sysRoleOperRelDao.findByRoleId(id));
		sysRoleMenuRelDao.delete(sysRoleMenuRelDao.findByRoleId(id));
		// 新增该角色下的资源和操作授权
		SysRoleMenuRel sysRoleMenuRel;
		SysRoleOperRel sysRoleOper;
		for (int i = 0; i < key.length; i++) {
			if ("res".equals(type[i])) {
				sysRoleMenuRel = new SysRoleMenuRel();
				sysRoleMenuRel.setRoleId(id);
				sysRoleMenuRel.setSysMenu(sysMenuDao.findOne(key[i]));
				sysRoleMenuRelDao.save(sysRoleMenuRel);
			} else if ("oper".equals(type[i])) { // 保存角色——操作数据
				sysRoleOper = new SysRoleOperRel();
				sysRoleOper.setRoleId(id);
				sysRoleOper.setSysOperator(sysOperatorDao.findOne(key[i]
						.substring(2, key[i].length())));
				sysRoleOperRelDao.save(sysRoleOper);
			}
		}

	}

	@Override
	public List<ComplatRole> findRoleList() throws Exception {
		List<ComplatRole> list = new ArrayList<ComplatRole>();
		list = roleDao.findAll();
		return list;
	}

	/**
	 * 根据用户ID查询对应角色ID
	 */
	@Override
	public List<JisRoleobject> findByUserId(Integer userId,Integer groupId)
			throws Exception {
		String getRoleSql = "select t.roleid from jis_roleobject t where objectid = ? and type='0'";
		List<Map<String, Object>> roleRelationMap = jdbcTemplate.queryForList(getRoleSql, new Integer[] { userId });
		if(roleRelationMap ==null || roleRelationMap.size()==0){
			getRoleSql = "select t.roleid from jis_roleobject t where objectid = ? and type='2'";
			roleRelationMap = jdbcTemplate.queryForList(getRoleSql, new Integer[] { groupId });
		}
		List<JisRoleobject> roleRelationList = new ArrayList<JisRoleobject>();
		for (Map<String, Object> map : roleRelationMap) {
			JisRoleobject roleRelation = new JisRoleobject();
			String roleId = map.get("ROLEID").toString();
			if (StringHelper.isNotBlack(roleId)) {
				roleRelation.setRoleid(Integer.parseInt(roleId));
				roleRelationList.add(roleRelation);
			}
		}

		return roleRelationList;
	}
	
	/**
	 * 查询角色用户关系
	 */
	@Override
	public List<Map<String, Object>> findRoleMember(String roleId,
			String memberType, String memberName) {
		StringBuilder sql = new StringBuilder(
				"select rb.objectid objectid,rb.type type");
		if (StringHelper.isNotBlack(memberName)) {
			switch (Integer.parseInt(memberType)) {
			case 0:
				sql.append(",u.name username")
						.append(",g.name groupname")
						.append(",o.name outname")
						.append(" from jis_roleobject rb LEFT JOIN complat_user u ON rb.objectid = u.iid AND rb.type = 0 LEFT JOIN complat_group g ON rb.objectid = g.iid AND rb.type = 2 LEFT JOIN complat_outsideuser o ON rb.objectid = o.iid AND rb.type = 1 ")
						.append(" where (g.name LIKE '%" + memberName+"%' OR u.name LIKE '%" + memberName
								+ "%' OR o.name LIKE '%" + memberName + "%') AND ");
				break;
			case 1:
				sql.append(",g.name groupname")
						.append(" from jis_roleobject rb LEFT JOIN complat_group g ON rb.objectid = g.iid AND rb.type = 2")
						.append(" where (g.name LIKE '%" + memberName
								+ "%') AND ");

				break;
			case 2:
				sql.append(",u.name username")
						.append(",o.name outname")
						.append(" from jis_roleobject rb LEFT JOIN complat_user u ON rb.objectid = u.iid AND rb.type = 0 LEFT JOIN complat_outsideuser o ON rb.objectid = o.iid AND rb.type = 1")
						.append(" where (u.name LIKE '%" + memberName
								+ "%' OR o.name LIKE '%" + memberName + "%') AND ");
				break;
			default:
				break;

			}
		} else {
			switch (Integer.parseInt(memberType)) {
			case 0:
				sql.append(",u.name username")
						.append(",g.name groupname")
						.append(",o.name outname")
						.append(" from jis_roleobject rb LEFT JOIN complat_user u ON rb.objectid = u.iid AND rb.type = 0 LEFT JOIN complat_group g ON rb.objectid = g.iid AND rb.type = 2 LEFT JOIN complat_outsideuser o ON rb.objectid = o.iid AND rb.type = 1")
						.append(" where rb.type IN (0,1,2) AND ");
				break;
			case 1:
				sql.append(",g.name groupname")
						.append(" from jis_roleobject rb LEFT JOIN complat_group g ON rb.objectid = g.iid AND rb.type = 2")
						.append(" where rb.type = 2 AND ");
				break;

			case 2:
				sql.append(",u.name username")
						.append(",o.name outname")
						.append(" from jis_roleobject rb LEFT JOIN complat_user u ON rb.objectid = u.iid AND rb.type = 0 LEFT JOIN complat_outsideuser o ON rb.objectid = o.iid AND rb.type = 1")
						.append(" where rb.type IN (0,1) AND ");
			}

		}
		sql.append(" rb.roleid = " + roleId + " order by rb.iid DESC");
		return jdbcTemplate.queryForList(sql.toString());

	}
	
	@Override
	public void deleteByRoleId(String roleId) {
		String sql= "delete from jis_roleobject where type!='3' and roleid="+roleId;
		jdbcTemplate.execute(sql);
	}
	
	@Override
	public void deleteUsersByRoleId(String roleId, String[] users,
			String[] groups) {
		 
		if(users.length>0){
			String user = "('";
			for(String u : users){
				user+=u+"','";
			}
			user = user.substring(0, user.length()-2);
			user+=")";
			System.out.println(user);
			String sql="delete from jis_roleobject where roleid="+roleId +" and " +
					" objectid in "+user;
			jdbcTemplate.execute(sql);
		}
		if (groups.length>0){
			String user = "('";
			for(String u : groups){
				user+=u+"','";
			}
			user = user.substring(0, user.length()-2);
			user+=")";
			System.out.println(user);
			String sql="delete from jis_roleobject where roleid="+roleId +" and " +
					" objectid in "+user;
			jdbcTemplate.execute(sql);
		}
		
	}
}
