package com.gsww.jup.dao.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.SysDepartment;

public interface SysDepartmentDao extends
		PagingAndSortingRepository<SysDepartment, String>,
		JpaSpecificationExecutor<SysDepartment> {

	public SysDepartment findByDeptId(Integer deptId);
	public SysDepartment findByDeptCode(String deptCode);
	public List<SysDepartment> findByDeptStateOrderByDeptSeqAsc(String deptState);
	public List<SysDepartment> findByParentDeptCode(String parentId,String deptState);
	/**
	 * 验证部门名称的唯一性
	 * @param deptNameInput
	 * @param deptCode
	 * @return
	 */
	@Query("select t from SysDepartment t where  t.deptName =?1  and t.deptCode like %?2")
	public List<SysDepartment> findByDeptNameAndLikeDeptCode(String deptNameInput, String deptCode);
	@Query("select t from SysDepartment t where  t.deptName =?1  and t.deptCode=?2")
	public List<SysDepartment> findByDeptNameAndEQDeptCode(String deptNameInput, String deptCode);
	/**
	 * 根据父级部门编码查询部门列表
	 * @param parentDeptCode
	 * @return
	 */
	public List<SysDepartment> findByParentDeptCode(String parentDeptCode);
	/**
	 * 查询部门树的根节点
	 * @return
	 */
	public List<SysDepartment> findByParentDeptCodeIsNull();
}
