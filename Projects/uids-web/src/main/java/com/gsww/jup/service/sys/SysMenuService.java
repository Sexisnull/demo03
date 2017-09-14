package com.gsww.jup.service.sys;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.jup.entity.sys.SysMenu;
import com.gsww.jup.entity.sys.SysOperator;

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
public interface SysMenuService {
		/**
		 * 获取菜单
		 * @param roleKey
		 * @param systemCode
		 * @return
		 */
		public String getSysMenu(String roleIds) throws Exception ;
		/**
		 * 获取菜单  Json字符串格式的
		 * @param roleKey
		 * @param systemCode
		 * @return
		 */
		public String getSysMenuJson(String roleIds) throws Exception ;
		/**
		 * 获取菜单分页
		 * @param searchParams
		 * @param pageNumber
		 * @param pageSize
		 * @param sortType
		 * @return
		 */
		public Page<SysMenu> getMenuPage(Specification<SysMenu> spec, PageRequest pageRequest) throws Exception ;
		/**
		 * 删除单个对象
		 * @param SysMenu
		 * @throws Exception
		 */
		public void delete(SysMenu entity) throws Exception;
		
		/**
		 * 保存或修改对象
		 * @param SysMenu
		 * @throws Exception
		 */
		public SysMenu save(SysMenu entity) throws Exception;
		
		/**
		 * 根据主键删除对象
		 * @param id
		 * @throws Exception
		 */
		public void delete(String id) throws Exception;
		
		/**
		 * 根据主键查找对象
		 * @param pk
		 * @throws Exception
		 */
		public SysMenu findByKey(String pk) throws Exception;
		
		
		public List<SysMenu> findMenuByParentMenuId(String parentMenuId) throws Exception;
		/**
		 * 查找第一级菜单
		 * @return
		 * @throws Exception
		 */
		public List<SysMenu> findFirstMenu() throws Exception;
		/**
		 * 查找二级菜单
		 * @return
		 * @throws Exception
		 */
		public List<SysMenu> findSecondMenu() throws Exception;


		/**
		 * 方法描述 :根据菜单ID删除操作
		 * @param mId
		 */
		public void deleteOper(SysMenu sysMenu) throws Exception;

		/**
		 * 方法描述 : 删除菜单角色关系
		 * @param sysMenu
		 * @throws Exception
		 */
		public void deleteRoleMenu(SysMenu sysMenu)throws Exception;

}
