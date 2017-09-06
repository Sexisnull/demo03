package com.gsww.jup.service.sys.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.gsww.jup.dao.sys.SysDepartmentDao;
import com.gsww.jup.entity.sys.SysDepartment;
import com.gsww.jup.service.sys.DepartmentService;

/**
 * <p>
 * Title:部门管理接口实现类
 * </p>
 * 
 * <p>
 * Description:部门管理接口实现类
 * </p>
 * 
 * @author wangjl
 * @date 2014-7-10
 */
@Transactional
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	private SysDepartmentDao sysDepartmentDao;

	@Override
	public SysDepartment save(SysDepartment sysDepartment) throws Exception {
		return sysDepartmentDao.save(sysDepartment);

	}

	@Override
	public void delete(String id) throws Exception {
		try{
		SysDepartment sysDepartment = this.findByKey(id);
		if (sysDepartment != null){
//			sysDepartment.setDeptState("0");
//			sysDepartmentDao.save(sysDepartment);
			sysDepartmentDao.delete(sysDepartment);
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public SysDepartment findByKey(String id) throws Exception {
		return sysDepartmentDao.findOne(id);
	}
	@Override
	public SysDepartment findByKey(Integer id) throws Exception {
		return sysDepartmentDao.findByDeptId(id);
	}

	@Override
	public Page<SysDepartment> getPage(Map<String, Object> searchParams,
			int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Specification<SysDepartment> spec = buildSpecification(searchParams);
		return sysDepartmentDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = new Sort(Direction.DESC, "createTime");
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<SysDepartment> buildSpecification(
			Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<SysDepartment> spec = DynamicSpecifications
				.bySearchFilter(filters.values(), SysDepartment.class);
		return spec;
	}

	@Override
	public Page<SysDepartment> getPage(Specification<SysDepartment> spec,
			PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return sysDepartmentDao.findAll(spec, pageRequest);
	}

	@Override
	public List<SysDepartment> getAll() {
		// TODO Auto-generated method stub
		return this.sysDepartmentDao.findByDeptStateOrderByDeptSeqAsc("1");
	}

	@Override
	public SysDepartment findByCode(String code) throws Exception {
		// TODO Auto-generated method stub
		return this.sysDepartmentDao.findByDeptCode(code);
	}

	@Override
	public List<SysDepartment> checkUniqueDeptName(String deptNameInput,
			String deptCode) {
		return this.sysDepartmentDao.findByDeptNameAndLikeDeptCode(deptNameInput, deptCode);
	}

	@Override
	public List<SysDepartment> findBySetIdAndParentDeptIsNull(String setId) throws Exception {
		return sysDepartmentDao.findByParentDeptCodeIsNull();
	}

	@Override
	public List<SysDepartment> findByParentDeptCode(String parentDeptCode) throws Exception {
		return sysDepartmentDao.findByParentDeptCode(parentDeptCode);
	}

}
