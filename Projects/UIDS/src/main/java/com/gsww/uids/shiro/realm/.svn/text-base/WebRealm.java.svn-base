package com.gsww.uids.shiro.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsww.common.util.StringUtil;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.role.entity.RoleRelation;
import com.gsww.uids.manager.role.service.RoleRelationService;

public class WebRealm extends AuthorizingRealm {
	
	// 访问前台的权限：只要成功登录，就有此权限
	public static final String 	ACCOUNT_PERMISSION = "account_permission";

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private RoleRelationService roleRelationService;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		
		// 从token中获取用户名：解析为两部分：appId + accountName
		String principal = (String) authcToken.getPrincipal();		
		String appId = principal.substring(0, 32);
		String accountName = principal.substring(32);
		
		// 查找账号
		List<Account> accounts = accountService.findByAppAndAccountName(appId, accountName);		
		if ( accounts.isEmpty() ) {
			throw new AuthenticationException("账号或密码有误, 请重新输入");
		}
		Account account = accounts.get(0);
		
		// 设置CredentialsMatcher的加密方式
		setHashAlgorithm(account.getApp());
		ByteSource salt = null;
		if ( StringUtil.isNotBlank(account.getApp().getAccountEncryptSalt()) ) {
			salt = new SimpleByteSource(account.getApp().getAccountEncryptSalt());
		}
		
		// 验证账号是否封停
		if ( account.getStatus() == Account.FROZEN_STATUS ) {
			throw new AuthenticationException("账号已经封停");
		}
		
		// 返回数据库中的用于匹配的正确身份信息	
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				(String) authcToken.getPrincipal(), //用户名
				account.getPassword(), //密码
				salt, // salt
                getName()  //realm name
        );
        return authenticationInfo;
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 返回授权信息
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		// 登录了，就有访问前台的权限
		simpleAuthorizationInfo.addStringPermission(ACCOUNT_PERMISSION);
		
		// 从token中获取用户名：解析为两部分：appId + accountName
		String principal = (String) principals.getPrimaryPrincipal();
		String appId = principal.substring(0, 32);
		String accountName = principal.substring(32);
		List<Account> accounts = accountService.findByAppAndAccountName(appId, accountName);
		if ( accounts.isEmpty() ) {
			throw new AuthorizationException("账号不存在，或者修改了账号信息，请重新登录！");
		}
		Account account = accounts.get(0);
		
		//用户的所有角色
		if (account.getUser() != null) {
			List<RoleRelation> rrs = roleRelationService.findListByParams(account.getUser().getUuid(), null, null);
			for (RoleRelation rr : rrs) {
				simpleAuthorizationInfo.addStringPermission(rr.getRole().getMark());
			}
		}
		return simpleAuthorizationInfo;
	}
	
	/**
	 * 根据账号所属应用的加密配置，设置验证身份信息时的密码加密hash算法
	 * 
	 * @param app
	 */
	private void setHashAlgorithm(Application app) {

		CredentialsMatcher matcher = this.getCredentialsMatcher();
		if ( matcher instanceof HashedCredentialsMatcher ) {
			HashedCredentialsMatcher hashMatcher = (HashedCredentialsMatcher) matcher;
			hashMatcher.setHashAlgorithmName(app.getAccountEncryptType());
			hashMatcher.setHashIterations(app.getAccountEncryptIterations());
			hashMatcher.setStoredCredentialsHexEncoded(true);
			
			if ( StringUtil.isNotBlank(app.getAccountEncryptSalt()) ) {
				hashMatcher.setHashSalted(true);
			}
		}
	}
}
