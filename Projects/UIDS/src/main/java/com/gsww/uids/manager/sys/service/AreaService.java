package com.gsww.uids.manager.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gsww.common.entity.PageObject;
import com.gsww.uids.manager.sys.entity.Area;

/**
 * 区域业务层接口
 * 
 * @author sunbw
 *
 */
@Service
public interface AreaService {
	
	/**
	 * 保存、修改
	 * @param info
	 * @return
	 */
	public boolean saveOrUpdate(Area area);
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	public void delete(String ids);
	
	/**
	 * 查询全部列表
	 */
	public List<Area> findAll();
	
	/**
	 * 查找
	 * 
	 * @param id
	 * @return
	 */
	public Area findById(String id);
	
	/**
	 * 根据参数不同获取pagelist
	 * @param page 页数
	 * @param size 大小
	 * @param parentId 父亲Id
	 * @param searchText 
	 * @return
	 */
	public PageObject<Area> findPage(Integer page, Integer size, String parentId,String searchText);
	
	/**
	 * 对外接口，公共接口
	 * 根据父区域id查找区域列表
	 * @param parentId
	 * @return
	 */
	public List<Area>  findChild(String parentId);
	
	/**
	 * 私有，只有和区域有关的类能用
	 * 根据父区域id查找区域列表
	 * @param parentId
	 * @return
	 */
	public List<Area> findNextList(String parentId);
	/**
	 * 查找某个等级的所有区域
	 * 
	 * @param level
	 * @return
	 */
	List<Area> findAllByLevel(int level);
	
	/**
	 * 产生某个等级的区域：父节点不为空
	 * 
	 * @param level
	 * @return
	 */
	Area generateAreaOfLevel(int level);
	
	/**
	 * 打开或者关闭有效状态
	 * @param ids
	 * @return
	 */
	public void openOrClose(String ids, String oper);
	
	/**
	 * 获取区域的根节点
	 * 
	 * @return
	 */
	public Area getRootOrganization();
	
	/**
	 * 检查能否批量删除：如果有一个不能删除，则返回false
	 * 
	 * @param ids
	 * @param errInfo
	 * @return
	 */
	boolean checkDelete(String ids, StringBuilder errInfo);
	
	/**
	 * 根据区域编码查找
	 * @param code 区域编码
	 * @return
	 */
	public List<Area> findByCode(String code);
	
	/**
	 * 根据区域名称查找
	 * @param name
	 * @return
	 */
	public List<Area> findByName(String name); 
	
	/**
	 * 检查记录的唯一性
	 * 
	 * @param info
	 * @param errInfo
	 * @return
	 */
	public boolean checkUnique(Area info, StringBuilder errInfo);
}
