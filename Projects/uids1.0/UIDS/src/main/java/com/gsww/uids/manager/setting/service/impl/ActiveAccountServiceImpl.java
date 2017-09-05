package com.gsww.uids.manager.setting.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsww.common.entity.PageObject;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.setting.entity.ActiveAccount;
import com.gsww.uids.manager.setting.service.ActiveAccountService;

/**
 * 在线用户业务层实现
 * 
 * @author taolm
 *
 */
@Service
public class ActiveAccountServiceImpl implements ActiveAccountService {
	
	@Autowired
    private SessionDAO sessionDAO;
	
	@Autowired
	private AccountService accountService;
	
	@Override
	public PageObject<ActiveAccount> findPage(Integer currentPage, Integer pageSize) {
		
		// 获取所有在线账号
		List<ActiveAccount> onlineAccounts = this.getAllActiveAccounts();
		
		// 设置分页参数
		PageObject<ActiveAccount> page = new PageObject<ActiveAccount>();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
        page.setTotalCount(onlineAccounts.size());
        
        // 获取分页中的数据列表
        List<ActiveAccount> itemList = null;
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = currentPage * pageSize;
        if ( (fromIndex + 1) > page.getTotalCount() ) {
        	itemList = new ArrayList<ActiveAccount>();
        } else {        	
        	if ( toIndex > page.getTotalCount() ) {
        		toIndex = page.getTotalCount();
        	}
        	itemList = onlineAccounts.subList(fromIndex, toIndex);
        }
        page.setDataList(itemList);
        
        return page;
	}

	/**
	 * 获取所有在线账号
	 * 
	 * @return
	 */
	private List<ActiveAccount> getAllActiveAccounts() {
		
		List<ActiveAccount> onlineAccounts = new ArrayList<ActiveAccount>();
		
		// 获取所有活动session，从而获取在线账号信息
		Collection<Session> sessions =  sessionDAO.getActiveSessions();
		for ( Session session : sessions ) {
			String accountId = (String) session.getAttribute(WebConstants.ONLINE_ACCOUNT_ID);
			if ( StringUtil.isBlank(accountId) ) {
				continue ;
			}
			Account account = accountService.findById(accountId);
			String loginTime = (String) session.getAttribute(WebConstants.LOGIN_TIME);
			
			ActiveAccount  onlineAccount = new ActiveAccount();
			onlineAccount.setAccountName(account.getName());
			onlineAccount.setAppName(account.getApp().getName());
			// TODO ip地址如何获取？
			onlineAccount.setIp(session.getHost());
			onlineAccount.setLoginTime(loginTime);
			onlineAccount.setSessionId(session.getId().toString());
			
			onlineAccounts.add(onlineAccount);
		}
		
		return onlineAccounts;
	}
}
