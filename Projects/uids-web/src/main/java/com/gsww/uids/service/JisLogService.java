package com.gsww.uids.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.JisLog;


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
	
	public Page<JisLog> getJisLogPage(Specification<JisLog> spec, PageRequest pageRequest);
}
