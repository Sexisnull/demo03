package com.gsww.uids.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.gsww.uids.dao.ComplatZoneDao;
import com.gsww.uids.entity.ComplatZone;
import com.gsww.uids.service.ComplatZoneService;

/**
 * Title: ComplatZoneServiceImpl.java Description: 区域管理ServiceImpl
 * 
 * @author yangxia
 * @created 2017年9月15日 下午2:16:08
 */
@Transactional
@Service("complatZoneService")
public class ComplatZoneServiceImpl implements ComplatZoneService {
	@Autowired
	private ComplatZoneDao complatZoneDao;

	@Override
	public Page<ComplatZone> getPage(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<ComplatZone> spec = buildSpecification(searchParams);
		return complatZoneDao.findAll(spec, pageRequest);
	}

	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = new Sort(Direction.DESC, "iid");
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	private Specification<ComplatZone> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<ComplatZone> spec = DynamicSpecifications.bySearchFilter(filters.values(), ComplatZone.class);
		return spec;
	}

	@Override
	public Page<ComplatZone> getPage(Specification<ComplatZone> spec, PageRequest pageRequest) {
		return complatZoneDao.findAll(spec, pageRequest);
	}

	@Override
	public List<ComplatZone> getAll() {
		List<ComplatZone> complatZoneList = new ArrayList<ComplatZone>();
		Iterable<ComplatZone> complatZoneIterables = complatZoneDao.findAll();
		Iterator<ComplatZone> complatZoneIterable = complatZoneIterables.iterator();
		while (complatZoneIterable.hasNext()) {
			ComplatZone complatZone = (ComplatZone) complatZoneIterable.next();
			complatZoneList.add(complatZone);
		}
		return complatZoneList;
	}

	@Override
	public ComplatZone fingByKey(Integer iid) {
		return complatZoneDao.findByIid(iid);
	}

	@Override
	public void save(ComplatZone complatZone) {
		complatZoneDao.save(complatZone);
	}

	@Override
	public void delete(ComplatZone complatZone) {
		complatZoneDao.delete(complatZone);
	}

	@Override
	public List<ComplatZone> checkUniqueDeptName(String nameInput, String codeId) {
		return complatZoneDao.findByNameAndLikeCodeId(nameInput, codeId);
	}
	
	@Override
	public List<ComplatZone> findChildByIid(int int1) {
		return complatZoneDao.findChildByPid(int1);
	}
	
	@Override
	public List<ComplatZone> findByPid(Integer pid) throws Exception {
		List<ComplatZone> list=new ArrayList<ComplatZone>();
		list=complatZoneDao.findByPid(pid);
		return list;	
	}
	
	@Override
	public ComplatZone findByIid(Integer iid) {
		ComplatZone complatZone=complatZoneDao.findByIid(iid);
		return complatZone;
	}
	@Override
	public List<ComplatZone> findAllByIid(Integer iid) throws Exception {
		List<ComplatZone> list=new ArrayList<ComplatZone>();
		list=complatZoneDao.findAllByIid(iid);
		return list;
	}
	
	@Override
	public boolean checkToIid(Integer iid) throws Exception {
		if(complatZoneDao.findByIid(iid) != null){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public ComplatZone findByCodeId(String codeId) throws Exception {
		ComplatZone complatZone = complatZoneDao.findByCodeId(codeId);
		return complatZone;
	}
}
