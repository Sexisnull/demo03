package com.gsww.actor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gsww.uids.client.actor.StdHttpSessionBasedActor;
import com.gsww.uids.client.actor.Exception.ActorException;
import com.gsww.uids.client.oauth.Token;
import com.gsww.uids.client.user.SSOGroup;
import com.gsww.uids.client.user.SSOUser;

/**
 * 接入uids的应用必须实现的接口
 * 
 * @author taolm
 *
 */
public class CustomActor extends StdHttpSessionBasedActor {

	/**
	 * 应用中Session的登录标记
	 */
	private static final String LOGIN_FLAG = "application-login-user";
	
	/**
	 * 应用中session的token标记
	 */
	private static final String TOKEN_FLAG = "application-token";
	
	@Override
	public boolean checkLocalLogin(HttpSession session) throws ActorException {
		
		SSOUser user = (SSOUser) session.getAttribute(LOGIN_FLAG);
		return (user != null);		
	}

	@Override
	public void loadLoginUser(HttpServletRequest request, SSOUser user) throws ActorException {

		HttpSession session = request.getSession();
		session.setAttribute(LOGIN_FLAG, user);		
	}

	@Override
	public void logout(HttpSession session) throws ActorException {

		session.removeAttribute(LOGIN_FLAG);	
		session.removeAttribute(TOKEN_FLAG);
	}

	@Override
	public boolean addUser(SSOUser user, HttpServletRequest request) throws ActorException {
		// TODO 将user保存到本地数据库中
		return true;
	}

	@Override
	public boolean disableUser(SSOUser user) throws ActorException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean enableUser(SSOUser user) throws ActorException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String extractUserName(HttpServletRequest request) throws ActorException {

		// login.jsp提交的表单中用户名
		return request.getParameter("username");
	}

	@Override
	public String extractUserPwd(HttpServletRequest request) throws ActorException {
		
		// login.jsp提交的表单中密码
		return request.getParameter("password");
	}
	
	@Override
	public boolean removeUser(SSOUser user, HttpServletRequest request) throws ActorException {
		// TODO 从本地数据库中删除某个用户
		return true;
	}

	@Override
	public boolean updateUser(SSOUser user, HttpServletRequest request) throws ActorException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean userExist(SSOUser user) throws ActorException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean addGroup(SSOGroup group, HttpServletRequest req) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean updateGroup(SSOGroup group, HttpServletRequest req) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean delGroup(SSOGroup group, HttpServletRequest req) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean moveToGroup(SSOUser user, SSOGroup group) throws ActorException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeFromGroup(SSOUser user, SSOGroup group) throws ActorException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void loadToken(HttpServletRequest request, Token token) throws ActorException {
		
		if ( token != null ) {
			HttpSession session = request.getSession();
			session.setAttribute(TOKEN_FLAG, token);
		}
	}

	@Override
	public Token extractToken(HttpServletRequest request) throws ActorException {
		
		HttpSession session = request.getSession();
		return (Token) session.getAttribute(TOKEN_FLAG);
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @return
	 */
	public SSOUser extractLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (SSOUser)session.getAttribute(LOGIN_FLAG);		
	}
}
