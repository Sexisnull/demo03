package com.gsww.uids.service.impl;


import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.gsww.jup.dao.JdbcDAO;
import com.gsww.jup.util.StringHelper;
import com.gsww.uids.service.JisUserdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.gsww.uids.dao.JisUserdetailDao;
import com.gsww.uids.entity.JisUserdetail;


@Transactional
@Service("JisUserdetailService")
public class JisUserdetailServiceImpl implements JisUserdetailService {
	
	@Autowired
	private JisUserdetailDao jisUserdetailDao;
	
	@Autowired
    private JdbcDAO jdbcDAO;
	
	@Override
	public JisUserdetail findByUserid(Integer userId) throws Exception{
		
		return jisUserdetailDao.findByUserid(userId);
	}

	@Override
	public void save(JisUserdetail jisUserdetail) throws Exception{
		
		jisUserdetailDao.save(jisUserdetail);
	}

	@Override
	public void update(Integer iid, String cardId,Map<String,String> userMap) throws Exception{
		Iterator it = userMap.entrySet().iterator();
		String whereSqls = "";
		while(it.hasNext()){
			Map.Entry<String, String> map = (Entry<String, String>) it.next();
			String whereSql = map.getKey()+"='"+map.getValue()+"'";
			whereSqls += whereSql + ",";
		}
		if(StringHelper.isNotBlack(whereSqls)){
			whereSqls = whereSqls.substring(0, whereSqls.length()-1);
		}
		String updateSql = "";
		if(StringHelper.isNotBlack(cardId) && StringHelper.isNotBlack(whereSqls)){
			updateSql = "update jis_userdetail t set t.cardid = "+cardId+","+whereSqls+" where t.iid = "+iid;
			jdbcDAO.execute(updateSql);
		}else if(!StringHelper.isNotBlack(cardId) && StringHelper.isNotBlack(whereSqls)){
			updateSql = "update jis_userdetail t set "+whereSqls+" where t.iid = "+iid;
			jdbcDAO.execute(updateSql);
		}else if(StringHelper.isNotBlack(cardId) && !StringHelper.isNotBlack(whereSqls)){
			updateSql = "update jis_userdetail t set t.cardid = "+cardId+" where t.iid = "+iid;
			jdbcDAO.execute(updateSql);
		}		
	}
	
	/**
     * @param fieldName
     * @Description 动态addUserField
     * @author xiaoyy
     * @Date 2017/9/15 下午3:12
     * The word 'impossible' is not in my dictionary.
     */
    @Override
    public void addUserField(String fieldName) throws Exception{
        String sql = "";
        String sqlOut = "";
        sql = "ALTER TABLE jis_userdetail ADD " + fieldName + " varchar(255)";
        sqlOut = "ALTER TABLE jis_outsideuserdetail ADD " +
                fieldName + " varchar(255)";
        jdbcDAO.execute(sql);
        jdbcDAO.execute(sqlOut);
    }


    /**
     * @param fieldName
     * @Description 动态delete UserField
     * @author xiaoyy
     * @Date 2017/9/15 下午3:12
     * The word 'impossible' is not in my dictionary.
     */
    @Override
    public void delUserField(String fieldName) throws Exception{
        String sql = "ALTER TABLE jis_userdetail drop COLUMN " + fieldName;
        String sqlOut = "ALTER TABLE jis_outsideuserdetail drop COLUMN " + fieldName;
        jdbcDAO.execute(sql);
        jdbcDAO.execute(sqlOut);
    }
}
