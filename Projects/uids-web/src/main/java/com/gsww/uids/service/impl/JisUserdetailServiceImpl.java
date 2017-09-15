package com.gsww.uids.service.impl;

import com.gsww.jup.dao.JdbcDAO;
import com.gsww.uids.service.JisUserdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * JisUserdetailServiceImpl
 * com.gsww.uids.service.impl
 *
 * @author xiaoyy
 * @Date 2017-09-15 下午2:50
 * The word 'impossible' is not in my dictionary.
 */
@Transactional
@Service("jisUserdetailService")
public class JisUserdetailServiceImpl implements JisUserdetailService{

    @Autowired
    private JdbcDAO jdbcDAO;


    /**
     * @param fieldName
     * @Description 动态addUserField
     * @author Xander
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
     * @author Xander
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
