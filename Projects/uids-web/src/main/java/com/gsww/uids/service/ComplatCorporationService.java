package com.gsww.uids.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.ComplatCorporation;
import com.gsww.uids.util.exception.OperationException;


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
	
	ComplatCorporation checkUserLogin(String userName, String password, String ip);

	void updateLoginIpAndLoginTime(ComplatCorporation corporation);

	/**
	 * 根据工商注册查询法人用户
	 * @param regNum
	 * @return
	 */
	public ComplatCorporation findByRegNum(String regNum);
	
	/**
	 * 根据组织机构编码查询法人用户列表
	 * @param inputByGuest
	 * @return
	 */
	public ComplatCorporation findByOrgName(String inputByGuest);
	
	/**
	 * 多个字段匹配查找用户
	 * @param inputByGuest
	 * @return
	 */
	public ComplatCorporation findByManyWay(String inputByGuest);

	ComplatCorporation findByLoginNameIsUsed(String loginName);

	boolean updateIsUpload(int intValue, int i);

	boolean modify(ComplatCorporation corporation);

	/**
	 * 统一注册
	 * @param corporation
	 * @return
	 * @throws OperationException
	 */
	boolean add(ComplatCorporation corporation) throws OperationException;
}
