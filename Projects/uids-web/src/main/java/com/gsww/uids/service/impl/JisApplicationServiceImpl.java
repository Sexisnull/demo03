package com.gsww.uids.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.util.StringHelper;
//import com.gsww.uids.controller.ComplatRoleController;
import com.gsww.uids.dao.JisApplicationDao;
import com.gsww.uids.entity.JisApplication;
import com.gsww.uids.service.JisApplicationService;
/**
 * 应用模块业务实现类
 * @author Seven
 *
 */
@Transactional
@Service("JisApplicationService")
public class JisApplicationServiceImpl implements JisApplicationService {
	
	private static Logger logger = LoggerFactory.getLogger(JisApplicationServiceImpl.class);

	@Autowired
	private JisApplicationDao jisApplicationDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate ;

	@Override
	public JisApplication save(JisApplication entity) throws Exception {
		return jisApplicationDao.save(entity);
	}

	@Override
	public String delete(JisApplication entity) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(entity);  
		String logMsg=jsonObject.toString();
		jisApplicationDao.delete(entity);
		return logMsg;
	}

	@Override
	public JisApplication findByKey(Integer iid) throws Exception {
		JisApplication jisApplication=jisApplicationDao.findByIid(iid);
		return jisApplication;
	}

	@Override
	public Page<JisApplication> getApplicationPage(Specification<JisApplication> spec,
			PageRequest pageRequest) {
		return jisApplicationDao.findAll(spec, pageRequest);
	}

	@Override
	public JisApplication findByMark(String mark) throws Exception {
		JisApplication jisApplication=jisApplicationDao.findByMark(mark).get(0);
		return jisApplication;
	}

	public JisApplication queryMarkIsUsed(String mark) throws Exception {
		List<JisApplication> list = jisApplicationDao.findByMark(mark);
		if (CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<Map<String, Object>> getJisApplicationList() throws Exception {
		String sql ="select t.iid,t.name from jis_application t";
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, new Object[]{});
		return mapList;
	}
	
	@Override
	public List<JisApplication> findAll() {
		return jisApplicationDao.findAll();
	}
	
	@Override
	public List<Map<String, Object>> findAppByRoleIds(String roleIds) {
		
		if(!StringHelper.isNotBlack(roleIds)){
			return new ArrayList<Map<String,Object>>();
		}
		String r = "'";
		String[] roids = roleIds.split(",");
		for(String s : roids){
			r+=s+"','";
		}
		r = r.substring(0, r.length()-2);
		
		String selAppId = "select objectid from jis_roleobject where type=3 and roleid in ("+r+")";
		List<Integer> appIds = jdbcTemplate.queryForList(selAppId, Integer.class);
		if(appIds==null || appIds.size()==0){
			return new ArrayList<Map<String,Object>>();
		}
		String ids = "'";
		for(Integer i : appIds){
			ids+=i+"','";
		}
		ids = ids.substring(0,ids.length()-2);
		String selApp = "select iid,name,icon,userdefined,logintype from jis_application where isshow=1 and iid in ("+ids+")";
		
		return jdbcTemplate.queryForList(selApp);
	}

	@Override
	public String findURLBylogoff(int islogoff) {
	    List<JisApplication> list = null;
	    String url = "";
	    StringBuffer buffer = new StringBuffer(128);
	    try
	    {
	      list = this.jisApplicationDao.findAll();
	      if ((list != null) && (list.size() > 0)) {
	        for (JisApplication app : list) {
	          buffer.append("，" + app.getLogOffUrl());
	        }
	        if ((buffer != null) && (buffer.length() > 0))
	          url = buffer.substring(1).toString();
	      }
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      logger.error("[findURLBylogoff] error:" + e);
	      return url;
	    }
	    return url;
	}
	
	@Override
	public List<JisApplication> findByIsSyncGroupNotNullAndLoginType(Integer loginType) throws Exception{
		List<JisApplication> list = jisApplicationDao.findByIsSyncGroupNotNullAndLoginType(loginType);
		return list;
	}
}
