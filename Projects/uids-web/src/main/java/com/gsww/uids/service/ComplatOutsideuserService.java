package com.gsww.uids.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.ComplatOutsideuser;

/**
 * Title: OutsideUserService.java Description: 个人用户Service层
 * 
 * @author yangxia
 * @created 2017年9月8日 上午10:41:40
 */
public interface ComplatOutsideuserService {

	/**
	 * @discription 分页描述
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<ComplatOutsideuser> getOutsideUserPage(Specification<ComplatOutsideuser> spec, PageRequest pageRequest);

	/**
	 * @discription 根据iid查询个人用户信息
	 * @param iid
	 * @return
	 */
	ComplatOutsideuser findByKey(Integer iid);

	/**
	 * @discription 保存个人用户信息
	 * @param iid
	 * @return
	 */
	void save(ComplatOutsideuser outsideUser);

	/**
	 * @discription 删除个人用户信息
	 * @param iid
	 * @return
	 */
	void delete(ComplatOutsideuser outsideUser);

	/**
     * @discription  查询全部个人用户实体  
     * @return
	 */
	public List<ComplatOutsideuser> findAll();
	
	/**
	 * 关键字查询
	 * @param keyword
	 * @return
	 */
	public List<Map<String, Object>> findByNameOrPinYin(String keyword);

	public List<ComplatOutsideuser> findByMobile(String cellPhoneNum);

	public List<ComplatOutsideuser> findByIdCard(String IdCard);

	public ComplatOutsideuser findByLoginName(String userName);

	public ComplatOutsideuser checkUserLogin(String userName, String password, String ip);

	public ComplatOutsideuser checkUserLogin(HttpSession session, String loginName, String password, String ip);

	public boolean updateLoginIpAndLoginTime(ComplatOutsideuser user);

	public boolean updatePwd(int iid, String pwd);

	/**
     * @discription   逻辑删除 
     * @param iid
	 */
	void delete(Integer iid);
	
	/**
     * @discription   验证loginName实体是否存在 
     * @param loginName
     * @return
	 */
	public Integer findByLoginNameIsUsed(String loginName);
	
	/**
	 * 统一注册插入个人用户
	 * @param outsideUser
	 * @return
	 */
	boolean insert(ComplatOutsideuser outsideUser);
	
	boolean modifyAuthing(ComplatOutsideuser outsideUser);
	
	Integer findByMobileIsUsed(String mobile);
	
	Integer findByIdCardIsUsed(String papersNumber);
}
