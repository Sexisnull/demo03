package com.gsww.uids.service;

import java.util.Map;

import com.gsww.uids.entity.JisUserdetail;

/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2017-09-07 下午14:30:23</p>
 * <p>类描述 :   用户模块service层    </p>
 *
 *
 * @version 3.0.0
 * @author <a href=" ">yaoxi</a>
 */
public interface JisUserdetailService {
	
	JisUserdetail findByUserid(Integer userId) throws Exception;
	
	void save(JisUserdetail jisUserdetail) throws Exception;
	
	void update(Integer iid,String cardId,Map<String,String> userMap) throws Exception;
	
    //动态addUserField
    public void addUserField(String fieldName) throws Exception;

    //动态delete UserField
    public void delUserField(String fieldName) throws Exception;

}