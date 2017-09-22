package com.gsww.uids.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.ComplatCorporation;


/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2017-09-07 下午14:35:48</p>
 * <p>类描述 : 法人管理模块接口        </p>
 *
 *
 * @version 3.0.0
 * @author <a href=" ">yaoxi</a>
 */
public interface ComplatCorporationService {

	/**
	 * 
	 * 方法描述 : 分页描述
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	Page<ComplatCorporation> getCorporationPage(Specification<ComplatCorporation> spec, PageRequest pageRequest) throws Exception;
	
	/**
	 * 根据主键查询法人信息
	 */
	ComplatCorporation findByKey(Integer iid) throws Exception;
	
	/**
	 * 保存
	 */
	void save(ComplatCorporation corporation) throws Exception;
	
	/**
	 * 修改
	 */
	void updateCorporation(Integer iid) throws Exception;

	public ComplatCorporation findByLoginName(String loginName);

	boolean updatePwd(String loginName, String md5encode);
	
	/**
     * @discription   验证loginName实体是否存在 
     * @param loginName
     * @return
	 */
	public ComplatCorporation findByLoginNameIsUsed(String loginName);
}
