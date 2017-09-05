package com.gsww.uids.client.actor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gsww.uids.client.actor.Exception.ActorException;
import com.gsww.uids.client.config.SSOConfig;
import com.gsww.uids.client.oauth.Token;
import com.gsww.uids.client.user.SSOGroup;
import com.gsww.uids.client.user.SSOUser;

/**
 * 基于标准HttpSession的actor接口，每个应用必须实现此接口
 * 
 * @author taolm
 *
 */
public abstract class StdHttpSessionBasedActor {
	
	/**
	 * 判断当前session是否登录
	 * 
	 * @param session
	 * @return
	 * @throws ActorException
	 */
	public abstract boolean checkLocalLogin(HttpSession session) throws ActorException;
	
	/**
	 * 加载登录的统一用户到应用的当前会话中，完成应用自己的登录逻辑（不需要再次对用户进行认证，只需要加载）
	 * 
	 * @param request
	 * @param user
	 * @throws ActorException
	 */
	public abstract void loadLoginUser(HttpServletRequest request, SSOUser user) throws ActorException;
	
	/**
	 * 完成应用自己的退出登录的逻辑
	 * 
	 * @param session
	 * @throws ActorException
	 */
	public abstract void logout(HttpSession session) throws ActorException;
	
	/**
	 * 应用同步增加用户的实现
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws ActorException
	 */
	public abstract boolean addUser(SSOUser user, HttpServletRequest request) throws ActorException;
	
	/**
	 * 应用禁用用户的实现
	 * 
	 * @param user
	 * @return
	 * @throws ActorException
	 */
	public abstract boolean disableUser(SSOUser user) throws ActorException;
	
	/**
	 * 应用启用用户的实现
	 * 
	 * @param user
	 * @return
	 * @throws ActorException
	 */
	public abstract boolean enableUser(SSOUser user) throws ActorException;
	
	/**
	 * 从应用的自有登录页面的表单中获取用户名
	 * 
	 * @param request
	 * @return
	 * @throws ActorException
	 */
	public abstract String extractUserName(HttpServletRequest request) throws ActorException;
	
	/**
	 * 从应用的自有登录页面的表单中获取密码
	 * 
	 * @param request
	 * @return
	 * @throws ActorException
	 */
	public abstract String extractUserPwd(HttpServletRequest request) throws ActorException;
	
	/**
	 * 应用同步删除用户的实现
	 * 
	 * @param user
	 * @return
	 */
	public abstract boolean removeUser(SSOUser user, HttpServletRequest request) throws ActorException;
	
	/**
	 * 应用同步更新用户的实现
	 * 
	 * @param user
	 * @return
	 */
	public abstract boolean updateUser(SSOUser user, HttpServletRequest request) throws ActorException;
	
	/**
	 * 应用判断用户是否存在的实现。返回true，表示用户存在，使得登录时不必先调用同步增加用户的方法
	 * 
	 * @param user
	 * @return
	 * @throws ActorException
	 */
	public abstract boolean userExist(SSOUser user) throws ActorException;
	
	/**
	 * 协作应用增加组织，如果协作应用的组织与IDS服务器组织结构要保持一致，则需要实现此方法
	 * 
	 * @param group
	 * @param req
	 * @return
	 */
	public abstract boolean addGroup(SSOGroup group, HttpServletRequest req);
	
	/**
	 * 协作应用更新组织，如果协作应用的组织与IDS服务器组织结构要保持一致，则需要实现此方法
	 * 
	 * @param group
	 * @param req
	 * @return
	 */
	public abstract boolean updateGroup(SSOGroup group, HttpServletRequest req);
	
	/**
	 * 协作应用删除组织，如果协作应用的组织与IDS服务器组织结构要保持一致，则需要实现此方法
	 * @param group
	 * @param req
	 * @return
	 */
	public abstract boolean delGroup(SSOGroup group, HttpServletRequest req);
	
	/**
	 * 用户移动到指定组织（分配组织）中
	 * 注意：如果协作应用中没有该用户或组织，协作应用可以先增加用户或组织，
	 * 如果参数提供的用户或组织信息不完整，则可以通过IDS Remote API获取用户和组织的详细信息
	 * 
	 * @param user
	 * @param group
	 * @return
	 * @throws ActorException
	 */
	public abstract boolean moveToGroup(SSOUser user, SSOGroup group)  throws ActorException;
	
	/**
	 * 用户从指定组织中移除
	 * 注意：如果协作应用中没有该用户或组织，协作应用可以先增加用户或组织，
	 * 如果参数提供的用户或组织信息不完整，则可以通过IDS Remote API获取用户和组织的详细信息
	 * 
	 * @param user
	 * @param group
	 * @return
	 * @throws ActorException
	 */
	public abstract boolean removeFromGroup(SSOUser user, SSOGroup group) throws ActorException;
	
	/**
	 * 将返回的token信息加载到session中
	 * 
	 * @param request
	 * @param token
	 * @throws ActorException
	 */
	public abstract void loadToken(HttpServletRequest request, Token token) throws ActorException;
	
	/**
	 * 从session中提取token信息
	 * 
	 * @param request
	 * @return
	 * @throws ActorException
	 */
	public abstract Token extractToken(HttpServletRequest request) throws ActorException;
	
	/**
	 * 从应用的自有登录页面的表单中获取登录账号所属应用的clientId
	 * 
	 * @return
	 * @throws ActorException
	 */
	public String extractClientIdOfUser() throws ActorException {
		
		// 统一身份认证下的账号
		final String UIDS_ACCOUNT_TYPE = "1";
		// 接入应用下的账号
		final String APP_ACCOUNT_TYPE = "2";
		
		// 获取单点登录账号所属应用的clientId
		SSOConfig config = SSOConfig.getInstance();
		if ( UIDS_ACCOUNT_TYPE.equalsIgnoreCase(config.getSsoLoginAccountType()) ) {
			return config.getUidsClientId();
		} else if ( APP_ACCOUNT_TYPE.equalsIgnoreCase(config.getSsoLoginAccountType()) ) {
			return config.getClientId();
		} else {
			throw new ActorException(String.format("错误的登录账号类型【%s】，请检查配置文件【%s】中【%s】是否配置正确！", 
					config.getSsoLoginAccountType(), "uids-agent.properties", "sso.login.account.type"));
		}
	}
}
