package com.gsww.uids.sso.rpc.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsww.uids.sso.rpc.service.AuthenticateService;

/**
 * RMI服务器：注册单点登录rpc服务
 * 
 * @author taolm
 *
 */
public class RpcServer {
	
	private static final Logger logger = Logger.getLogger(RpcServer.class);
	
	/**
	 * 提供的服务
	 */
	@Autowired
	private AuthenticateService authenticateService;
	
	/**
	 * 服务器端口号
	 */
	private int port = 2007;
	
	/**
	 * 服务绑定的命名
	 */
	private String bindName = "authenticateService";
	
	/**
	 * 注册rpc服务
	 */
	public void registry() throws RemoteException {
		
		// 创建服务注册管理器
		Registry registry = LocateRegistry.createRegistry(port);
		// 将服务绑定命名
		registry.rebind(bindName, authenticateService);
		
		logger.info(String.format("统一身份认证rpc服务启动了，端口号为【%d】", port));
	}

	public AuthenticateService getAuthenticateService() {
		return authenticateService;
	}

	public void setAuthenticateService(AuthenticateService authenticateService) {
		this.authenticateService = authenticateService;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public void setPort(String port) {
		try {
			this.port = Integer.parseInt(port);
		} catch ( NumberFormatException e ) {
			logger.error(String.format("提供RPC服务的服务器端口号【%s】格式错误，必须是数字，请重新设置！", port));
		}
	}

	public String getBindName() {
		return bindName;
	}

	public void setBindName(String bindName) {
		this.bindName = bindName;
	}
}