package com.gsww.uids.client.config;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import com.gsww.uids.client.actor.StdHttpSessionBasedActor;
import com.gsww.uids.sso.rpc.service.AuthenticateService;
import com.gsww.uids.util.PropertiesUtil;

/**
 * 单点登录相关的配置参数
 * 
 * @author taolm
 *
 */
public class SSOConfig {
	
	/**
	 * 配置文件路径
	 */
	private static final String UIDS_AGENT_CONFIG_FILE_PATH = "uids-agent.properties";
	
	/**
	 * 实例
	 */
	private static SSOConfig instance;

	/**
	*  统一身份认证服务器
	*/
	private String serverHost;
	
	/**
	* 统一身份认证服务器为身份认证开放的远程服务端口
	*/
	private int serverPort;
	
	/**
	 * 统一身份认证远程服务名称
	 */
	private String authenticateService;
	
	/**
	* 应用实现StdHttpSessionBasedActor接口的实现类
	*/
	private String coAppActorClassName;
	
	
	/**
	* 退出uri（相对地址）
	*/
	private String logoutUri;
	
	/**
	* 刷新token的uri（相对地址）
	*/
	private String refreshTokenUri;
	
	/**
	* 登录时，页面中用户名密码表单提交到的uri（相对地址）
	*/
	private String loginActionUri;
	
	/**
	 * 由个人中心开始的单点登录，即接受code的uri
	 */
	private String serverLoginUri;
	
	/**
	* 登录成功后跳转的uri（匿名用相对路径，非匿名用绝对路径）
	*/
	private String loginSuccessUrl;
	
	/**
	* 跳转到登录成功页面的方式
	*/
	private String loginSuccessGotoType;
	
	/**
	* 登录失败后跳转的uri（绝对地址）
	*/
	private String loginFailUrl;
	
	/**
	* 跳转到登录失败页面的方式
	*/
	private String loginFailGotoType;
	
	/**
	 * 统一身份认证应用的clientId
	 */
	private String uidsClientId;
	
	/**
	 * 单点登录时使用的账号类型：1-统一身份认证账号；2-本应用下的账号
	 */
	private String ssoLoginAccountType;
	
	/**
	* clientId
	*/
	private String clientId;
	
	/**
	* clientSecret
	*/
	private String clientSecret;
	
	/**
	 * 数据传送过程的加密方式
	 */
	private String deliverDataEncrtyType;

	/**
	 * 数据传送过程的加密秘钥
	 */
	private String deliverDataEncryptSecret;

	/**
	 * 数据传送过程的加密salt
	 */
	private String deliverDataEncryptSalt;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// public methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 私有构造函数
	 */
	private SSOConfig() {
	
	}
	
	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static synchronized SSOConfig getInstance() {
		if ( instance == null ) {
			instance = new SSOConfig();
			instance.init();
		}
		
		return instance;
	}
	
	/**
	 * 获得应用actor
	 * 
	 * @throws Exception
	 */
	public StdHttpSessionBasedActor getActor() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		Class<?> actorClass = Class.forName(coAppActorClassName);
		return ((StdHttpSessionBasedActor) actorClass.newInstance());		
	}
	
	/**
	 * 获得远程服务接口
	 * 
	 * @return
	 */
	public AuthenticateService getRemoteAuthenticateService() throws Exception {
		
		// 获取服务注册管理器
		Registry registry  = LocateRegistry.getRegistry(this.serverHost, this.serverPort); 
		
		// 根据命名获取服务  
		return ((AuthenticateService) registry.lookup(this.authenticateService));  
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// help methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 读取配置文件，初始化属性
	 */
	private void init() {
		
		Properties settings = PropertiesUtil.readProperties(UIDS_AGENT_CONFIG_FILE_PATH);
		
		serverHost = settings.getProperty("idm.server.host");
		this.setServerPort(settings.getProperty("idm.server.port"));
		authenticateService = settings.getProperty("idm.server.authenticateService");
		coAppActorClassName = settings.getProperty("coAppActor.className");
		logoutUri = settings.getProperty("logout.uri");
		refreshTokenUri = settings.getProperty("refreshToken.uri");
		loginActionUri = settings.getProperty("loginAction.uri");
		serverLoginUri = settings.getProperty("serverSingleLogin.uri");
		loginSuccessUrl = settings.getProperty("afterLoginOk.gotoUrl");
		loginSuccessGotoType = settings.getProperty("afterLoginOk.gotoType");
		loginFailUrl = settings.getProperty("afterLoginFail.gotoUrl");
		loginFailGotoType = settings.getProperty("afterLoginFail.gotoType");
		uidsClientId = settings.getProperty("uids.clientId");
		ssoLoginAccountType = settings.getProperty("sso.login.account.type");
		clientId = settings.getProperty("clientId");
		clientSecret = settings.getProperty("clientSecret");
		deliverDataEncrtyType = settings.getProperty("data.deliver.encrypt.type");
		deliverDataEncryptSecret = settings.getProperty("data.deliver.encrypt.secret");
		deliverDataEncryptSalt = settings.getProperty("data.deliver.encrypt.salt");
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// getter and setter
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	public void setServerPort(String serverPort) {
		this.serverPort = Integer.parseInt(serverPort);
	}

	public String getAuthenticateService() {
		return authenticateService;
	}

	public void setAuthenticateService(String authenticateService) {
		this.authenticateService = authenticateService;
	}

	public String getCoAppActorClassName() {
		return coAppActorClassName;
	}

	public void setCoAppActorClassName(String coAppActorClassName) {
		this.coAppActorClassName = coAppActorClassName;
	}

	public String getLogoutUri() {
		return logoutUri;
	}

	public void setLogoutUri(String logoutUri) {
		this.logoutUri = logoutUri;
	}

	public String getRefreshTokenUri() {
		return refreshTokenUri;
	}

	public void setRefreshTokenUri(String refreshTokenUri) {
		this.refreshTokenUri = refreshTokenUri;
	}

	public String getLoginActionUri() {
		return loginActionUri;
	}

	public void setLoginActionUri(String loginActionUri) {
		this.loginActionUri = loginActionUri;
	}

	public String getServerLoginUri() {
		return serverLoginUri;
	}

	public void setServerLoginUri(String serverLoginUri) {
		this.serverLoginUri = serverLoginUri;
	}

	public String getLoginSuccessUrl() {
		return loginSuccessUrl;
	}

	public void setLoginSuccessUrl(String loginSuccessUrl) {
		this.loginSuccessUrl = loginSuccessUrl;
	}

	public String getLoginSuccessGotoType() {
		return loginSuccessGotoType;
	}

	public void setLoginSuccessGotoType(String loginSuccessGotoType) {
		this.loginSuccessGotoType = loginSuccessGotoType;
	}

	public String getLoginFailUrl() {
		return loginFailUrl;
	}

	public void setLoginFailUrl(String loginFailUrl) {
		this.loginFailUrl = loginFailUrl;
	}

	public String getLoginFailGotoType() {
		return loginFailGotoType;
	}

	public void setLoginFailGotoType(String loginFailGotoType) {
		this.loginFailGotoType = loginFailGotoType;
	}

	public String getUidsClientId() {
		return uidsClientId;
	}

	public void setUidsClientId(String uidsClientId) {
		this.uidsClientId = uidsClientId;
	}

	public String getSsoLoginAccountType() {
		return ssoLoginAccountType;
	}

	public void setSsoLoginAccountType(String ssoLoginAccountType) {
		this.ssoLoginAccountType = ssoLoginAccountType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getDeliverDataEncrtyType() {
		return deliverDataEncrtyType;
	}

	public void setDeliverDataEncrtyType(String deliverDataEncrtyType) {
		this.deliverDataEncrtyType = deliverDataEncrtyType;
	}

	public String getDeliverDataEncryptSecret() {
		return deliverDataEncryptSecret;
	}

	public void setDeliverDataEncryptSecret(String deliverDataEncryptSecret) {
		this.deliverDataEncryptSecret = deliverDataEncryptSecret;
	}

	public String getDeliverDataEncryptSalt() {
		return deliverDataEncryptSalt;
	}

	public void setDeliverDataEncryptSalt(String deliverDataEncryptSalt) {
		this.deliverDataEncryptSalt = deliverDataEncryptSalt;
	}
}
