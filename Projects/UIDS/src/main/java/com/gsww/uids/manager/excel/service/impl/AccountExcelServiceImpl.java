package com.gsww.uids.manager.excel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.excel.service.AccountExcelService;

@Service
public class AccountExcelServiceImpl implements AccountExcelService{
	
	@Autowired
	private HibernateBaseDao hibernateBaseDao;

	@Override
	public List<Account> findAll() {
		StringBuilder query = new StringBuilder(" from ").append(Account.class.getName());
		return hibernateBaseDao.findList(query.toString());
	}

}
