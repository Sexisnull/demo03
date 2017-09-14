package com.gsww.uids.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.gsww.uids.entity.CountUser;



/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2017-09-12 下午14:30:23</p>
 * <p>类描述 :   在线用户模块service层    </p>
 *
 *
 * @version 3.0.0
 * @author <a href=" ">yaoxi</a>
 */
public interface JisLogService {
	
	public Page<Map<String,String>> getJisLogPage(int pageNumber,int pageSize,List<List<String>> searchCodition) throws Exception;

}
