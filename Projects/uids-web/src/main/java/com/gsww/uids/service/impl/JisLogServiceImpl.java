package com.gsww.uids.service.impl;

import java.util.List;
import java.util.Map;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.ServiceException;
import com.gsww.uids.dao.JisLogDao;
import com.gsww.uids.entity.JisLog;
import com.gsww.uids.service.JisLogService;
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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(JisLog log) {
		
		if(log!=null){
			jisLogDao.save(log);
		}
		
	}

}
