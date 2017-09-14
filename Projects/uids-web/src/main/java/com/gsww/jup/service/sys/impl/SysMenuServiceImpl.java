package com.gsww.jup.service.sys.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gsww.jup.dao.sys.SysMenuDao;
import com.gsww.jup.dao.sys.SysOperatorDao;
import com.gsww.jup.dao.sys.SysRoleMenuRelDao;
import com.gsww.jup.dao.sys.SysRoleOperRelDao;
import com.gsww.jup.entity.sys.SysMenu;
import com.gsww.jup.entity.sys.SysOperator;
import com.gsww.jup.service.sys.SysMenuService;
import com.gsww.jup.util.NullHelper;
import com.gsww.jup.util.XmlHelper;

/**
 * Created on 2014-6-21 Title:
 * <p/>
 * Description:
 * <p/>
 * Copyright: Copyright GSWW (c) 2014
 * <p/>
 * Company: wanwei.com
 * <p/>
 * 
 * @author wangcf
 * @version 1.0
 */
@Transactional
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysOperatorDao sysOperatorDao;
	@Autowired
	private SysRoleMenuRelDao sysRoleMenuRelDao;
	@Autowired
	private SysRoleOperRelDao sysRoleOperRelDao;

	@Override
	public Page<SysMenu> getMenuPage(Specification<SysMenu> spec,
			PageRequest pageRequest) throws Exception {
		// TODO Auto-generated method stub
		return sysMenuDao.findAll(spec, pageRequest);
	}

	@Override
	public void delete(SysMenu entity) throws Exception {
		sysMenuDao.delete(entity);
	}

	@Override
	public void delete(String id) throws Exception {
		sysMenuDao.delete(id);

	}

	@Override
	public SysMenu findByKey(String pk) throws Exception {
		SysMenu sysMenu = sysMenuDao.findOne(pk);
		return sysMenu;
	}
	@Override
	public List<SysMenu> findMenuByParentMenuId(String parentMenuId) throws Exception{
		List<SysMenu> list=new ArrayList<SysMenu>();
		list=sysMenuDao.findMenuByParentMenuId(parentMenuId);
		return list;
	}
	@Override
	public SysMenu save(SysMenu entity) throws Exception {
		// TODO Auto-generated method stub
		return sysMenuDao.save(entity);
	}

	@Override
	public List<SysMenu> findFirstMenu() throws Exception {
		// TODO Auto-generated method stub
		return sysMenuDao.findFirstMenu();
	}

	/**
	 * 获取菜单
	 * 
	 * @param roleKey
	 * @param systemCode
	 * @return
	 */
	public String getSysMenu(String roleIds, String setId) throws Exception {
		List<Map<String, Object>> list = null;
		if (roleIds == null || roleIds.equals(""))
			return null;
		String[] roles = roleIds.split(",");
		boolean bResult = false;
		// 判断是否是管理员角色，管理员角色不需要授权即可显示全部菜单，如果管理员也需要过滤菜单，注释以下代码
		// for (String temp : roles) {
		// if (temp.equals(Constants.ADMIN_ROLE_KEY)) {
		// bResult = true;
		// break;
		// }
		// }

		list = SysMenuList(roleIds, bResult);

		return getXml(list);
	}

	/**
	 * 获取菜单
	 * 
	 * @param roleKey
	 * @param systemCode
	 * @return
	 */
	public String getSysMenuJson(String roleIds)
			throws Exception {
		List<Map<String, Object>> list = null;
		if (roleIds == null || roleIds.equals(""))
			return null;
		String[] roles = roleIds.split(",");
		boolean bResult = false;
//		 判断是否是管理员角色，管理员角色不需要授权即可显示全部菜单，如果管理员也需要过滤菜单，注释以下代码
//		for (String temp : roles) {
//			if (temp.equals(Constants.ADMIN_ROLE_KEY)) {
//				bResult = true;
//				break;
//			}
//		}
		list = SysMenuList(roleIds, bResult);
		return getJsonNew(list);
	}

	private List<Map<String, Object>> SysMenuList(String roleIds, boolean bResult) {
		StringBuffer menuSql = new StringBuffer();
		menuSql.append("select m.menu_id,m.parent_menu_id,m.menu_name,m.menu_url,m.menu_img,m.is_treenode from sys_menu m ");
		if (!bResult) {
			menuSql.append("inner join sys_role_menu_rel rm on rm.menu_id=m.menu_id and m.menu_state='1' and rm.role_id in ("+splitRoleId(roleIds)+") ");
		}
		menuSql.append("where 1=1" + " group by m.menu_id,m.parent_menu_id,m.menu_name,m.menu_url,m.menu_img,m.is_treenode,m.menu_seq ");
		menuSql.append("order by m.menu_seq asc ");
		return jdbcTemplate.queryForList(menuSql.toString());
	}

	private String splitRoleId(String roleIds){
		String[] roles = roleIds.split(",");
		String role = "";
		for(int i=0;i<roles.length;i++){
			role += "'"+roles[i]+"',";
		}
		return role.substring(0, role.length()-1);
	}
	
	
	// 创建返回的XML
	private String getXml(List<Map<String, Object>> list) throws Exception {
		Document document = XmlHelper.createDocument();
		Element root = XmlHelper.createRootElement(document, "root");

		for (Map<String, Object> map : list) {
			if ("0".equals(NullHelper.convertNullToNothingnull(map.get(
					"parent_menu_id").toString().trim()))) {
				Element firstChild = document.createElement("menu");
				firstChild.setAttribute("id", NullHelper
						.convertNullToNothingnull(map.get("menu_id")));
				firstChild.setAttribute("name", NullHelper
						.convertNullToNothingnull(map.get("menu_name")));
				firstChild.setAttribute("icon", NullHelper
						.convertNullToNothingnull(map.get("menu_img")));
				XmlHelper.addElement(root, firstChild);
				recursiveRes(document, firstChild, list);
			}
		}
		// System.out.println(XmlHelper.toString(document));
		return XmlHelper.toString(document);
	}

	/**
	 * 获取（例如xml.json）格式的字符串
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private String getJsonNew(List<Map<String, Object>> list) throws Exception {
/*		// 去重父级菜单ID
		Map<String, String> mapCount = new HashMap<String, String>();
		Integer count = 0;
		for (Map<String, Object> map : list) {
			if (NullHelper.convertNullToNothingnull(map.get("parent_menu_id")
					.toString().trim()) != null) {
				count++;
				String value = NullHelper.convertNullToNothingnull(map.get(
						"parent_menu_id").toString().trim());
				if (!mapCount.containsValue(value)) {
					mapCount.put(count.toString(), value);
				}
			}

		}*/
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();// 一级菜单对应的list
		for (Map<String, Object> mapW : list) {
			Map<String, Object> map1 = new HashMap<String, Object>();// 每个一级菜单对应的map
			if ("0".equals(NullHelper.convertNullToNothingnull(mapW.get("parent_menu_id").toString().trim()))) {
				
				List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();// 二级菜单对应的list
				for (Map<String, Object> map : list) {
					// 二级菜单
						if (NullHelper.convertNullToNothingnull(mapW.get("menu_id").toString().trim()).equals(NullHelper.convertNullToNothingnull(map.get("parent_menu_id").toString().trim()))) {
							Map<String, String> map2 = new HashMap<String, String>();// 二级菜单对应的map
							map2.put("id", NullHelper.convertNullToNothingnull(map.get("menu_id")));
							map2.put("name", NullHelper.convertNullToNothingnull(map.get("menu_name")));
							String url = NullHelper.convertNullToNothingnull(map.get("menu_url"));
							map2.put("url", url);
							map2.put("icon", NullHelper.convertNullToNothingnull(map.get("menu_img")));
							// 二级菜单list2添加一级菜单的map2
							list2.add(map2);
						}
				}
				

				// 一级菜单
				map1.put("id", NullHelper.convertNullToNothingnull(mapW.get("parent_menu_id")));
				map1.put("id", NullHelper.convertNullToNothingnull(mapW.get("menu_id")));
				map1.put("name", NullHelper.convertNullToNothingnull(mapW.get("menu_name")));
				map1.put("icon", NullHelper.convertNullToNothingnull(mapW.get("menu_img")));
				map1.put("menuitem", list2);
				// 一级菜单list1添加一级菜单的map1
				list1.add(map1);
			}
		}
		JSONArray jsonStr = JSONArray.fromObject(list1);
		//System.out.println(jsonStr);
		return jsonStr.toString();
	}
	// 递归创建资源树
	private void recursiveRes(Document document, Element parElement,
			List<Map<String, Object>> list) {
		String currentParResKey = "";
		List<Element> clist = new ArrayList<Element>();
		for (Map<String, Object> map : list) {
			currentParResKey = NullHelper.convertNullToNothingnull(map
					.get("parent_menu_id"));
			String parElementId = parElement.getAttribute("id");
			if (parElementId.equals(currentParResKey)) {
				Element childElement = document.createElement("menuitem");
				childElement.setAttribute("id", NullHelper
						.convertNullToNothingnull(map.get("menu_id")));
				childElement.setAttribute("name", NullHelper
						.convertNullToNothingnull(map.get("menu_name")));
				String url = NullHelper.convertNullToNothingnull(map
						.get("menu_url"));
				childElement.setAttribute("url", url);
				childElement.setAttribute("icon", NullHelper
						.convertNullToNothingnull(map.get("menu_img")));
				XmlHelper.addElement(parElement, childElement);
				clist.add(childElement);
			}
		}
		// 孩子获取下一级孩子
		if (clist.size() > 0) {
			for (Element element : clist) {
				recursiveRes(document, element, list);// 递归
			}
		}
	}

	/**
	 * 查找二级菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysMenu> findSecondMenu() throws Exception {
		List<SysMenu> list = new ArrayList<SysMenu>();
		list = sysMenuDao.findSecondMenu();
		return list;
	}

	@Override
	public void deleteOper(SysMenu sysMenu) throws Exception {
		List<SysOperator> ss = sysOperatorDao.findBySysMenu(sysMenu);
		if (ss != null && ss.size() > 0) {
			for (SysOperator o : ss) {
				sysRoleOperRelDao
						.delete(sysRoleOperRelDao.findBySysOperator(o));
			}
			sysOperatorDao.delete(sysOperatorDao.findBySysMenu(sysMenu));
		}
	}

	@Override
	public String getSysMenu(String roleIds) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRoleMenu(SysMenu sysMenu) throws Exception {
		// TODO Auto-generated method stub
		sysRoleMenuRelDao.delete(sysRoleMenuRelDao.findBySysMenu(sysMenu));
	}


}
