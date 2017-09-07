package com.gsww.jup.service.sys;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.jup.entity.sys.SysAccount;
import com.gsww.jup.entity.sys.SysDepartment;

/**
 * <p>
 * Title:部门管理接口
 * </p>
 * 
 * <p>
 * Description:部门管理接口
 * </p>
 * 
 * @author wangjl
 * @date 2014-7-10 
 */
public interface DepartmentService {
	/**
	 * 保存或修改对象
	 * @param userInfo
	 * @throws Exception
	 */
	public SysDepartment save(SysDepartment sysDepartment) throws Exception;
	
	
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
	public SysDepartment findByKey(String id) throws Exception;
	public SysDepartment findByKey(Integer id) throws Exception;
	public SysDepartment findByCode(String code) throws Exception;
	
	/**
	 * 分页列表
	 * @param nameId
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<SysDepartment> getPage(Map<String, Object> searchParams, int pageNumber, int pageSize,String sortType);
	
	public Page<SysDepartment> getPage(Specification<SysDepartment> spec,PageRequest pageRequest) ;
	
	List<SysDepartment> getAll();
	/**
	 * 验证部门名称的唯一性
	 * @param deptNameInput
	 * @param deptCode
	 * @return
	 */
	public List<SysDepartment> checkUniqueDeptName(String deptNameInput,String deptCode);
	/**
	 * 查询部门树根节点
	 * @param parentDeptCode
	 * @return
	 * @throws Exception
	 */
	public List<SysDepartment> findBySetIdAndParentDeptIsNull(String setId) throws Exception;
	/**
	 * 根据父级部门编码查询部门列表
	 * @return
	 * @throws Exception
	 */
	public List<SysDepartment> findByParentDeptCode(String parentDeptCode) throws Exception;

}
