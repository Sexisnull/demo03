package com.gsww.uids.manager.account.service.impl;

import org.springframework.stereotype.Service;

import com.gsww.common.util.SmsUtil;
import com.gsww.uids.manager.account.service.AccountHomeService;
/**
 * 账号主页的业务层实现
 * @author jinwei
 *
 */

@Service
public class AccountHomeServiceImpl implements AccountHomeService {

	@Override
	public boolean checkPhoneNumber(String phoneNumber, String code , String mark) {
		// 验证验证码
		try {
			if (!SmsUtil.getInstance().checkCode(mark, phoneNumber, code)) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
