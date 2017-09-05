package com.gsww.uids.manager.excel.service;

import java.util.List;

import com.gsww.uids.manager.account.entity.Account;

public interface AccountExcelService {
	
	/**
	 * 查询所有账号信息
	 * @return
	 */
	List<Account> findAll();

}
