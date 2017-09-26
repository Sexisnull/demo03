package com.gsww.uids.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.Constants;
import com.gsww.jup.ServiceException;
import com.gsww.uids.dao.JisLogDao;
import com.gsww.uids.entity.JisLog;
import com.gsww.uids.service.JisLogService;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * 公司名称 : 中国电信甘肃万维公司
 * </p>
 * <p>
 * 项目名称 : uids
 * </p>
 * <p>
 * 创建时间 : 2017年9月13日10:25:59
 * </p>
 * <p>
 * 类描述 : 系统日志service层接口
 * </p>
 * 
 * 
 * @version 1.0.0
 * @author <a href=" ">zcc</a>
 */
@Transactional
@Service("jisLogService")
public class JisLogServiceImpl implements JisLogService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private JisLogDao jisLogDao;

	@Override
	public Page<Map<String, String>> getJisLogPage(int pageNumber,
			int pageSize, List<List<String>> searchCodition) throws Exception {
		try {
			String logListSql = this.getJisLogSql(searchCodition);
			int startNo = (pageNumber - 1) * pageSize;
			int endNo = startNo + pageSize;
			Integer totalCount = this.queryForCount(logListSql);
			List logList = this.searchForList(logListSql, startNo, endNo);
			Page<Map<String, String>> page = new PageImpl<Map<String, String>>(
					logList, new PageRequest(pageNumber - 1, pageSize),
					totalCount);
			return page;
		} catch (Exception exception) {
			throw new ServiceException("获取在线用户出错！"
					+ exception.fillInStackTrace());
		}
	}

	/**
	 * 拼装查询在线用户sql
	 * 
	 * @param searchCodition
	 * @return
	 */
	public String getJisLogSql(List<List<String>> searchCodition) {
		try {

			StringBuffer querySql = new StringBuffer();
			querySql.append("select @rownum:=@rownum+1 AS rownum,us.name as username,gr.name as groupname,log.ip,"
					+ " date_format(log.operatetime,'%Y-%c-%d %H:%i:%s') as operatetime from (select @rownum:=0)r, jis_log log ");
			querySql.append("left join complat_user us on log.userid = us.iid ");
			querySql.append("left join complat_group gr on us.groupid = gr.iid ");
			querySql.append("order by log.operatetime desc ");
			return querySql.toString();
		} catch (Exception exception) {
			throw new ServiceException("拼装查询sql时出错！"
					+ exception.fillInStackTrace());
		}
	}

	/**
	 * 
	 * 统计总个数
	 */
	public Integer queryForCount(String querySql) {
		Integer count = 0;
		try {
			String countSql = "select count(*) from ( " + querySql + ")ss";
			count = jdbcTemplate.queryForObject(countSql, Integer.class);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 获取列表
	 * 
	 * @param sql
	 * @param startNo
	 * @param endNo
	 * @return
	 */
	public List<Map<String, Object>> searchForList(String sql, int startNo,
			int endNo) {
		try {
			if (startNo == 0 && endNo == 0) {
				sql = sql + "";
			} else if (startNo == 1) {
				sql = "select * from (" + sql + ")ss where rownum < " + endNo;
			} else {
				sql = "select * from (select row_.*,rownum rownum_ from ("
						+ sql + ") row_ where rownum <= " + endNo
						+ " ) count where rownum_>" + startNo;
			}

			return jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			throw new ServiceException("根据sql语句查询时出错！" + e.fillInStackTrace());
		}
	}

	@Override
	public void logInsert(JisLog jisLog) {

	}

	@Override
	public List<JisLog> findLogList() throws Exception {
		List<JisLog> list = new ArrayList<JisLog>();
		list = jisLogDao.findAll();
		return list;
	}

	@Override
	public Page<JisLog> getJisLogPage(Specification<JisLog> spec,
			PageRequest pageRequest) {
		return jisLogDao.findAll(spec, pageRequest);
	}

	@Override
	public List<JisLog> findBySpec(String spec) throws Exception {
		return null;
	}

	@Override
	public void save(JisLog log) {
		
		if(log!=null){
			jisLogDao.save(log);
		}
		
	}
	@Override
	public void save(String loginName, String loginIp, String desc,
			Integer moduleName, Integer operatorType) {
		JisLog log = new JisLog();
		log.setUserId(loginName);
		log.setIp(loginIp);
		log.setOperateTime(new Date());
		log.setSpec(desc);
		log.setModuleName(moduleName);
		log.setOperateType(operatorType);
		jisLogDao.save(log);
		
	}

	@Override
	public boolean add(Integer operatetype, Integer modulename, String spec) {
		 String userid = "";
		    if ((operatetype.intValue() == 12) && (modulename.intValue() == 12)) {
		      userid = "外网用户注册";
		      String tempspec = getOprSpec(modulename, operatetype, spec, userid);
		      JisLog log = new JisLog();
		      log.setUserId(userid);
		      log.setIp(ControllerUtil.getIp());
		      log.setModuleName(modulename);
		      log.setOperateType(operatetype);
		      log.setOperateTime(new Date());
		      log.setSpec(tempspec);
		      boolean res = this.jisLogDao.save(log) !=null;
		      return res;
		    }
		    
		    // TODO 统一注册判断当前登录用户
		   /* if (getCurrentLoginName() != null) {
		      userid = getCurrentLoginName();
		    }
		    else {
		      userid = "接口调用";
		    }*/

		    if (StringUtil.isNotEmpty(userid)) {
		      String tempspec = getOprSpec(modulename, operatetype, spec, userid);
		      JisLog log = new JisLog();
		      log.setUserId(userid);
		      log.setIp(ControllerUtil.getIp());
		      log.setModuleName(modulename);
		      log.setOperateType(operatetype);
		      log.setOperateTime(new Date());
		      log.setSpec(tempspec);
		      boolean res = this.jisLogDao.save(log) !=null;
		      return res;
		    }
		return false;
	}
	
	private String getOprSpec(Integer moduleId, Integer oprtypeId, String spec, String username)
	  {
	    String oprSpec = "";
	    String operatetype = getArrName(Constants.OPR_ARRAY, oprtypeId);
	    String modulename = getArrName(Constants.MOD_ARRAY, moduleId);
	    if ((StringUtil.isNotEmpty(operatetype)) && (StringUtil.isNotEmpty(modulename))) {
	      if (StringUtil.isEmpty(spec)) {
	        oprSpec = username + operatetype + modulename;
	      }
	      else if (moduleId.intValue() == 8)
	        oprSpec = username + operatetype + "系统";
	      else {
	        oprSpec = username + operatetype + modulename + "【" + spec + "】";
	      }
	    }

	    return oprSpec;
	  }

	/*public String getCurrentLoginName()
	  {
	    String loginName = "";

	    AbstractUser user = UserSessionInfo.getFrontCurrentUserInfo();
	    if (user == null) {
	      return "webservice接口调用";
	    }

	    loginName = user.getLoginName();

	    return loginName;
	  }*/
	
	public String getArrName(String[][] str, Integer id)
	  {
	    for (int i = 0; i < str.length; i++) {
	      if (id.intValue() == NumberUtil.getInt(str[i][0])) {
	        return str[i][1];
	      }
	    }
	    return "";
	  }
}
