package com.gsww.uids.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.JisDatacallDao;
import com.gsww.uids.entity.JisDatacall;
import com.gsww.uids.service.JisDatacallService;
/**
 * 数据调用业务实现类
 * @author Seven
 *
 */
@Transactional
@Service("JisDatacallService")
public class JisDatacallServiceImpl implements JisDatacallService {

	@Autowired
	private JisDatacallDao jisDatacallDao;

	@Override
	public JisDatacall save(JisDatacall entity) throws Exception {
		return jisDatacallDao.save(entity);
	}

	@Override
	public String delete(JisDatacall entity) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(entity);  
		String logMsg=jsonObject.toString();
		jisDatacallDao.delete(entity);
		return logMsg;
	}

	@Override
	public JisDatacall findByKey(Integer iid) throws Exception {
		JisDatacall jisDatacall=jisDatacallDao.findByIid(iid);
		return jisDatacall;
	}

	@Override
	public Page<JisDatacall> getDatacallPage(Specification<JisDatacall> spec,
			PageRequest pageRequest) {
		return jisDatacallDao.findAll(spec, pageRequest);
	}

	@Override
	public JisDatacall findByRemark(String remark) throws Exception {
		JisDatacall jisDatacall=jisDatacallDao.findByRemark(remark).get(0);
		return jisDatacall;
	}

	public JisDatacall queryRemarkIsUsed(String remark) throws Exception {
		List<JisDatacall> list = jisDatacallDao.findByRemark(remark);
		if (CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
}
