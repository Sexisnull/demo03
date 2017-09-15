package com.gsww.uids.service;

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
	
	JisUserdetail findByUserid(Integer userId);
	
	void save(JisUserdetail jisUserdetail);
	
	void update(Integer iid,String cardId);
}
