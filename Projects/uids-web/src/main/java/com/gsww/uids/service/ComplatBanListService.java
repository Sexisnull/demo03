package com.gsww.uids.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.ComplatBanlist;
import com.gsww.uids.entity.ComplatUser;

public interface ComplatBanListService {
	public Page<ComplatBanlist> getComplatBanPage(Specification<ComplatBanlist> spec,PageRequest pageRequest);
	
		
	public List<ComplatBanlist> findComplatbanList() throws Exception;
	
	public void delete(ComplatBanlist entity) throws Exception;
	
	public ComplatBanlist findByIid(Integer iid)throws Exception;
	
	/**
	 * 检查登陆错误次数
	 * @param user
	 * @param ipAddr
	 * @param userType
	 * @return
	 */
	public ComplatBanlist checkLoginTimes(String loginName, String ipAddr, Integer userType,String groupId);


	public void removeById(Integer iid);

	/**
	 * 保存
	 * @param banList
	 */
	public void save(ComplatBanlist banList);

}
