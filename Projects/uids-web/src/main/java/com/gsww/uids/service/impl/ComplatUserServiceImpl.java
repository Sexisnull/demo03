package com.gsww.uids.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.dao.JdbcDAO;
import com.gsww.uids.dao.ComplatUserDao;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.service.ComplatUserService;


@Transactional
@Service("complatUserService")
public class ComplatUserServiceImpl implements ComplatUserService{

    @Autowired
    private ComplatUserDao complatUserDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private JdbcDAO jdbcDAO;

    /**
     * 查询用户列表
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Page<ComplatUser> getComplatUserPage(Specification<ComplatUser> spec,PageRequest pageRequest) {
        return complatUserDao.findAll(spec, pageRequest);
    }




    @Override
    public ComplatUser findByKey(Integer iid){
    	ComplatUser complatUser = complatUserDao.findByIid(iid);
        return complatUser;
    }


    @Override
    public void save(ComplatUser complatUser) {

        complatUserDao.save(complatUser);
    }

    @Override
    public void delete(ComplatUser complatUser) throws Exception {

        complatUserDao.delete(complatUser);
    }




    @Override
    public List<ComplatUser> findByUserAllName(String loginallname) {
        List<ComplatUser> list=new ArrayList<ComplatUser>();
        list=complatUserDao.findByName(loginallname);
        return list;
    }


    /**
     * 用户设置保存
     * @author yaoxi
     */
    @Override
    public void updateUser(Integer iid,String name, String headShip, String phone,
                           String mobile, String fax, String email, String qq,
                           Date modifyTime,String pwd) throws Exception {

        complatUserDao.updateUser(iid,name,headShip,phone,mobile,fax,email,qq,modifyTime,pwd);
    }



    @Override
    public List<Map<String,Object>> findByGroupIds(String id) {
        if(StringHelper.isEmpty(id)){
            return null;
        }
        String [] ids = id.split(",");
        String groupIds="";
        for(String s :ids){
            groupIds+=s+",";
        }
        groupIds = groupIds.substring(0,groupIds.length()-1);
        String sql = "SELECT a.iid, a.name as ANAME,a.loginname,a.loginallname, a.pwd, a.groupid, b.name AS groupname, a.headship, a.mobile, a.mobile, a.fax, a.email, a.qq, a.msn, a.address, a.orderid,a.modifyPassTime, b.codeid, b.name, (SELECT name FROM complat_group WHERE iid = b.pid) pargroupname  FROM complat_user a, complat_group b  WHERE a.groupid = b.iid  AND a.groupid IN ("+groupIds+") AND a.opersign <>3 ORDER BY a.orderid DESC,a.iid DESC ";

        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> findByNameOrPinYin(String keyword) {
        String sql = "SELECT iid, name, loginname FROM complat_user" +
                " WHERE name LIKE '%"+keyword+"%' OR pinyin LIKE '%"+keyword+"%' ";
        return jdbcTemplate.queryForList(sql);
    }


    @Override
    public ComplatUser findByLoginname(String loginname) {
    	ComplatUser complatUser = complatUserDao.findByLoginname(loginname);
        return complatUser;
    }


    @Override
    public List<ComplatUser> findByGroupid(Integer groupid) {
        return complatUserDao.findByGroupid(groupid);
    }

    @Override
    public List<Map<String,Object>> synchronizeData(Integer userId) {
        String sql = "select a.iid userId,a.loginname,b.codeid,c.iid appId from complat_user a,complat_group b,jis_application c where a.groupid = b.iid and a.iid = '"+userId+"' and c.issyncgroup in ('0','1') and c.logintype = '0'";
        return jdbcTemplate.queryForList(sql);
    }



    //手机号
    @Override
	public ComplatUser findByMobile(String mobile) {
	    return this.complatUserDao.findByMobile(mobile);
	}


    //登录名和机构名的唯一性校验
    public List<Map<String,Object>> findByLoginnameAndgroupid(String loginname,int groupId){
    	String findSql = "select loginname from complat_user where loginname='"+loginname+"' and groupid= '"+groupId+"'";
    	List<Map<String,Object>> list=jdbcTemplate.queryForList(findSql);
		return list;
    }




	@Override
	public void updateUserPassTime(Integer iid, String name, String headShip,
			String phone, String mobile, String fax, String email, String qq,
			Date modifyTime, String pwd, Date modifyPassTime) throws Exception {
		complatUserDao.updateUserPassTime(iid,name,headShip,phone,mobile,fax,email,qq,modifyTime,pwd,modifyPassTime);
	}
}
