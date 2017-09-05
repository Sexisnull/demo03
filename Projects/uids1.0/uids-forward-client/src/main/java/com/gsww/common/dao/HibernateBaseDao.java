package com.gsww.common.dao;

import java.io.Serializable;
import java.util.List;

import com.gsww.common.entity.PageObject;

/**
 * hibernate的基础dao接口类
 * 
 * @author simplife
 *
 */
public interface HibernateBaseDao {
	/**
	 * 保存对象
	 * @param t
	 * @return 
	 */
	public <T> void save(T t);
	
	/**
	 * 更新对象
	 * @param t
	 */
	public <T> void update(T t);
	
	/**
	 * 根据id获取对象
	 * @param t
	 */
	public <T> T getById(final Class<T> entityClass, final Serializable id);
	
	/**
	 * 删除对象
	 * @param t
	 */
	public <T> void delete(T t);
	
	/**
	 * 根据对象ID删除对象
	 * @param t
	 */
	public <T> boolean deleteById(Class<T> entityClass, final Serializable id);
	
	/**
	 * 根据hql语句查询列表
	 * @param hqlString
	 * @param values
	 * @return
	 */
	public <T> List<T> findList(String hqlString);
	
	/**
	 * 根据hql语句查询列表
	 * @param hqlString
	 * @param values
	 * @return
	 */
	public <T> List<T> findList(String hqlString, Object... values);
	
	/**
	 * 根据hql语句查询列表
	 * @param hqlString
	 * @param values
	 * @return
	 */
	public <T> List<T> findList(String hqlString, List<Object> values);
	
	/**
	 * 根据hql语句对象
	 * @param hqlString
	 * @param values
	 * @return
	 */
	public <T> T getByHql(String hqlString, Object... values);
	
	/**
	 * 根据hql语句查询数据条数
	 * @param hqlString
	 * @param values
	 * @return
	 */
	public Long count(String hqlString);
	
	/**
	 * 根据hql语句查询数据条数
	 * @param hqlString
	 * @param values
	 * @return
	 */
	public Long count(String hqlString, Object... values);
	
	/**
	 * 分页查询
	 * @param hql
	 * @param currentPage
	 * @param pageSize
	 * @param values
	 * @return PageObject
	 */
	public <T> PageObject<T> findPage(String hql, int currentPage, int pageSize);
	
	/**
	 * 分页查询
	 * @param hql
	 * @param currentPage
	 * @param pageSize
	 * @param values
	 * @return PageObject
	 */
	public <T> PageObject<T> findPage(String hql, int currentPage, int pageSize, Object... values);
	
	/**
	 * 分页查询
	 * @param hql
	 * @param currentPage
	 * @param pageSize
	 * @param values
	 * @return PageObject
	 */
	public <T> PageObject<T> findPage(String hql, int currentPage, int pageSize, List<Object> values);
	
}
