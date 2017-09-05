package com.gsww.uids.manager.sys.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gsww.common.entity.PageObject;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.sys.entity.BanLog;

/**
 * 封停业务层接口
 * 
 * @author sunbw
 *
 */
@Service
public interface BanService {
	
	/**
	 * 保存、修改
	 * @param info
	 * @return
	 */
	public boolean saveOrUpdate(BanLog info);
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	public void delete(String ids);
	
	/**
	 * 获取分页列表
	 * @return
	 */
	public PageObject<BanLog> findPage(Integer currentPage, Integer pageSize,String searchText);
	
	/**
	 * 根据账号名查询封停记录
	 * @param account
	 * @return
	 */
	public BanLog findByAccountName(String accountName);
	
	/**
	 * 记录封停日志
	 * @param account
	 * @param request
	 */
	public void writeBanLog(Account account, HttpServletRequest request);
	
}
