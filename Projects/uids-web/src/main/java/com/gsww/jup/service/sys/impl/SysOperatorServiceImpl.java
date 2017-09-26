package com.gsww.jup.service.sys.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gsww.jup.dao.sys.SysOperatorDao;
import com.gsww.jup.entity.sys.SysOperator;
import com.gsww.jup.service.sys.SysOperatorService;
import java.util.*;
@Transactional
@Service("sysOperatorService")
public class SysOperatorServiceImpl implements SysOperatorService{
	@Autowired
	private SysOperatorDao sysOperatorDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 删除操作
	 * @param entity
	 * @throws Exception
	 */
	@Override
	public void delete(SysOperator entity) throws Exception {
		sysOperatorDao.delete(entity);
	}
	/**
	 * 根据主键查找对象
	 * @param operatorId
	 * @throws Exception
	 */
	@Override
	public SysOperator findByOperatorId(String operatorId) throws Exception {
		return sysOperatorDao.findByOperatorId(operatorId);
	}
	/**
	 * 分页查询操作
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	@Override
	public Page<SysOperator> getSysOperatorPage(Specification<SysOperator> spec, PageRequest pageRequest) {		
		return sysOperatorDao.findAll(spec, pageRequest);
	}
	/**
	 * 保存操作
	 * @param entity
	 * @throws Exception
	 */
	@Override
	public void save(SysOperator entity) throws Exception {
		sysOperatorDao.save(entity);
	}
	/**
	 * 根据主键启用对象
	 * @param operatorId
	 * @throws Exception
	 */
	@Override
	public void start(String operatorId) throws Exception {
		sysOperatorDao.updateState("1", operatorId);
	}
	/**
	 * 根据主键停用对象
	 * @param operatorId
	 * @throws Exception
	 */
	@Override
	public void stop(String operatorId) throws Exception {
		sysOperatorDao.updateState("0", operatorId);
	}


	@Override
	public List<Map<String, Object>> getOptionByPageName(String roleIds, String menuId, String operatorType, String tabIndex) throws Exception {
		String sql = "select o.operator_name,o.operator_url,o.operator_image from SYS_OPERATOR o inner join (select ro.operator_id from " +
		" SYS_ROLE_OPER_REL ro where ro.role_id in ("+splitRoleId(roleIds)+") group by ro.operator_id) roa on " +
		" o.operator_id=roa.operator_id and o.menu_id = '"+menuId+"' and o.operator_state='1' and o.operator_type= '"+operatorType+"' " +
		" and o.tab_index= '"+tabIndex+"' order by o.operator_level asc";
		
		return jdbcTemplate.queryForList(sql);
	}
	private String splitRoleId(String roleIds){
		String[] roles = roleIds.split(",");
		String role = "";
		for(int i=0;i<roles.length;i++){
			role += "'"+roles[i]+"',";
		}
		return role.substring(0, role.length()-1);
	}
	@Override
	public List<SysOperator> findList(Object object) {
		return null;
	}
}
