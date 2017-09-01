package com.gsww.common.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.entity.PageObject;

/**
 * hibernate操作表的dao公共类
 * 
 * @author simplife
 *
 */
@Repository("hibernateBaseDao")
@SuppressWarnings("unchecked")
@Transactional
public class HibernateBaseDaoImpl implements HibernateBaseDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public <T> void save(T t) {
		getSession().save(t);
	}

	@Override
	public <T> void update(T t) {
		t = (T) getSession().merge(t);
		getSession().update(t);
	}

	@Override
	public <T> T getById(Class<T> entityClass, Serializable id) {
		return (T) getSession().get(entityClass, id);
	}

	@Override
	public <T> void delete(T t) {
		getSession().delete(t);
	}

	@Override
	public <T> boolean deleteById(Class<T> entityClass, Serializable id) {
		T t = getById(entityClass, id);
		if (null == t) {
			return false;
		}
		delete(t);
		return true;
	}

	@Override
	public <T> List<T> findList(String hqlString) {
		return findList(hqlString, null, null);
	}
	
	@Override
	public <T> List<T> findList(String hqlString, Object... values) {
		Query query = this.getSession().createQuery(hqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
            	if (values[i] != null) {
            		query.setParameter(i, values[i]);
            	}
            }
        }
        return query.list();
	}
	
	@Override
	public <T> List<T> findList(String hqlString, List<Object> values) {
		if (null == values) {
			return findList(hqlString);
		}
		Object[] params = new Object[values.size()];
		for (int i = 0; i < values.size(); i++) {
			params[i] = values.get(i);
		}
		return findList(hqlString, params);
	}
	
	@Override
	public <T> T getByHql(String hqlString, Object... values) {
		List<T> list = findList(hqlString, values);
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Long count(String hqlString) {
		return (long) findList(hqlString).size();
	}
	
	@Override
	public Long count(String hqlString, Object... values) {
		return (long) findList(hqlString, values).size();
	}

	@Override
	public <T> PageObject<T> findPage(String hql, int currentPage, int pageSize) {
		return findPage(hql, currentPage, pageSize, null, null);
	}
	
	@Override
	public <T> PageObject<T> findPage(String hql, int currentPage, int pageSize, Object... values) {
		PageObject<T> PageObject = new PageObject<T>();
        Query query = this.getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
            	if (values[i] != null) {
            		query.setParameter(i, values[i]);
            	}
            }
        }
        PageObject.setCurrentPage(currentPage);
        PageObject.setPageSize(pageSize);
        Long count = count(hql, values);
        PageObject.setTotalCount(count.intValue());
        
        List<T> itemList = query.setFirstResult((currentPage - 1) * pageSize).setMaxResults(pageSize).list();
        if (itemList == null) {
            itemList = new ArrayList<T>();
        }
        PageObject.setDataList(itemList);
        return PageObject;
	}
	
	@Override
	public <T> PageObject<T> findPage(String hql, int currentPage, int pageSize, List<Object> values) {
		if (null == values) {
			return findPage(hql, currentPage, pageSize);
		}
		Object[] params = new Object[values.size()];
		for (int i = 0; i < values.size(); i++) {
			params[i] = values.get(i);
		}
		return findPage(hql, currentPage, pageSize, params);
	}

	@Override
	public void executeUpdate(String sql) {
		getSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public void evict(Object obj) {
		sessionFactory.getCurrentSession().evict(obj);;
	}

	@Override
	public void clear() {
		sessionFactory.getCurrentSession().clear();
	}

	/**
	 * 获取session
	 * @return
	 */
	private Session getSession() {
    	return sessionFactory.getCurrentSession();
    }
}
