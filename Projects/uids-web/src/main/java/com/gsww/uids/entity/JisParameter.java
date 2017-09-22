package com.gsww.uids.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 系统参数实体类
 * @author Seven
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "JIS_PARAMETER")
public class JisParameter implements java.io.Serializable{

	private Integer iid;                //主键id
	private String sysName;             //系统名称
	private String sysUrl;              //系统地址
	private String copyRight;           //版权信息
	private String isLoginfail;         //限制登录次数
	private Integer loginError;         //连续错误次数
	private Integer banTimes;           //错误限制时长
	private String pwdLevel;            //后台密码强度等级
	private String netType;             //系统网络类型
	private String modifyPassTime;      //密码定时修改时间（月）
	
	private String ticketEffectiveTime; //票据有效时间
	private String tokenEffectiveTime;  //令牌有效时间
	private Integer perSessionTime;     //前台个人用户登录超时时间
	private Integer corSessionTime;     //前台法人用户登录超时时间
	
	private String emailSmtp;           //服务器
	private String emailPort;           //端口
	private String emailBox;            //账号
	private String emailPassword;       //密码
	private String emailSender;         //寄件方名称
	private String emailTitle;          //注册邮件标题
	private String emailContent;        //注册邮件内容
	private String emailFindPassTitle;  //找回密码标题
	private String emailFindPassContent;//找回密码内容
	
	private String enableSynTask;       //同步线程开启
	private String syncTime;            //同步线程时间
	private String clearLogTime;        //日志保留天数
	
	private String appId;               //appId
	private String appName;             //appName
	private String appAcc;              //appAcc
	private String appPwd;              //appPwd
	private String importantLevel;      //importantLevel
	private String isSendAgain;         //isSendAgain
	private String isLose;              //isLose
	private String isUpstream;          //isUpstream
	private String urlRoot;             //urlRoot
	
	private String businessIdForRegestingPer;         //个人注册短信业务Id
	private String businessNameForRegestingPer;       //个人注册短信业务名称
	private String registPerMessageContent;           //个人注册时的短信内容
	private String businessIdForRegestingCor;         //法人注册短信业务Id
	private String businessNameForRegestingCor;       //法人注册短信业务名称
	private String registCorMessageContent;           //法人注册时的短信内容
	private String businessIdForRecovingPwd;          //找回密码业务Id
	private String businessNameForRecovingPwd;        //找回密码业务名称
	private String recovingPwdContent;                //找回密码时的短信内容
	private String businessIdForGettingDynamicPwd;    //获取动态登录密码业务Id
	private String businessNameForGettingDynamicPwd;  //获取动态登录密码业务名称
	private String validityPeriod;                    //动态登录密码有效期
	private String dynamicPwdMessageContent;          //获取动态登录密码时的短信内容
	
	private String enableCorRealNameAuth;    //实名认证开启
	private String corRequestCod;            //法人认证接口参数requestcod
	private String corRealUsername;          //法人认证接口参数username
	private String corRealPassword;          //法人认证接口参数password
	private String corExchangeTokenUrl;      //法人认证换取token地址
	private String corCompareRealNameUrl;    //法人认证实名比对地址
	private String perCompareRealNameUrl;    //个人认证实名比对地址
	private String corDetailRealNameUrl;     //获取法人详细信息地址
	
	private String enableGovRealNameAuth;     //实名认证开启
	private Integer verify_mode;              //请选择认证模式
	private String govRequestCod;             //政府用户认证接口参数requestcod
	private String govRealUsername;           //政府用户认证接口参数username
	private String govRealPassword;           //政府用户认证接口参数password
	private String govExchangeTokenUrl;       //政府用户认证换取token地址
	private String govCompareRealNameUrl;     //政府用户认证实名比对地址
	
	public JisParameter() {
		super();
	}

	public JisParameter(Integer iid, String sysName, String sysUrl,
			String copyRight, String isLoginfail, Integer loginError,
			Integer banTimes, String pwdLevel, String netType,
			String modifyPassTime, String ticketEffectiveTime,
			String tokenEffectiveTime, Integer perSessionTime,
			Integer corSessionTime, String emailSmtp, String emailPort,
			String emailBox, String emailPassword, String emailSender,
			String emailTitle, String emailContent, String emailFindPassTitle,
			String emailFindPassContent, String enableSynTask, String syncTime,
			String clearLogTime, String appId, String appName, String appAcc,
			String appPwd, String importantLevel, String isSendAgain,
			String isLose, String isUpstream, String urlRoot,
			String businessIdForRegestingPer,
			String businessNameForRegestingPer, String registPerMessageContent,
			String businessIdForRegestingCor,
			String businessNameForRegestingCor, String registCorMessageContent,
			String businessIdForRecovingPwd, String businessNameForRecovingPwd,
			String recovingPwdContent, String businessIdForGettingDynamicPwd,
			String businessNameForGettingDynamicPwd, String validityPeriod,
			String dynamicPwdMessageContent, String enableCorRealNameAuth,
			String corRequestCod, String corRealUsername,
			String corRealPassword, String corExchangeTokenUrl,
			String corCompareRealNameUrl, String perCompareRealNameUrl,
			String corDetailRealNameUrl, String enableGovRealNameAuth,
			Integer verify_mode, String govRequestCod, String govRealUsername,
			String govRealPassword, String govExchangeTokenUrl,
			String govCompareRealNameUrl) {
		super();
		this.iid = iid;
		this.sysName = sysName;
		this.sysUrl = sysUrl;
		this.copyRight = copyRight;
		this.isLoginfail = isLoginfail;
		this.loginError = loginError;
		this.banTimes = banTimes;
		this.pwdLevel = pwdLevel;
		this.netType = netType;
		this.modifyPassTime = modifyPassTime;
		this.ticketEffectiveTime = ticketEffectiveTime;
		this.tokenEffectiveTime = tokenEffectiveTime;
		this.perSessionTime = perSessionTime;
		this.corSessionTime = corSessionTime;
		this.emailSmtp = emailSmtp;
		this.emailPort = emailPort;
		this.emailBox = emailBox;
		this.emailPassword = emailPassword;
		this.emailSender = emailSender;
		this.emailTitle = emailTitle;
		this.emailContent = emailContent;
		this.emailFindPassTitle = emailFindPassTitle;
		this.emailFindPassContent = emailFindPassContent;
		this.enableSynTask = enableSynTask;
		this.syncTime = syncTime;
		this.clearLogTime = clearLogTime;
		this.appId = appId;
		this.appName = appName;
		this.appAcc = appAcc;
		this.appPwd = appPwd;
		this.importantLevel = importantLevel;
		this.isSendAgain = isSendAgain;
		this.isLose = isLose;
		this.isUpstream = isUpstream;
		this.urlRoot = urlRoot;
		this.businessIdForRegestingPer = businessIdForRegestingPer;
		this.businessNameForRegestingPer = businessNameForRegestingPer;
		this.registPerMessageContent = registPerMessageContent;
		this.businessIdForRegestingCor = businessIdForRegestingCor;
		this.businessNameForRegestingCor = businessNameForRegestingCor;
		this.registCorMessageContent = registCorMessageContent;
		this.businessIdForRecovingPwd = businessIdForRecovingPwd;
		this.businessNameForRecovingPwd = businessNameForRecovingPwd;
		this.recovingPwdContent = recovingPwdContent;
		this.businessIdForGettingDynamicPwd = businessIdForGettingDynamicPwd;
		this.businessNameForGettingDynamicPwd = businessNameForGettingDynamicPwd;
		this.validityPeriod = validityPeriod;
		this.dynamicPwdMessageContent = dynamicPwdMessageContent;
		this.enableCorRealNameAuth = enableCorRealNameAuth;
		this.corRequestCod = corRequestCod;
		this.corRealUsername = corRealUsername;
		this.corRealPassword = corRealPassword;
		this.corExchangeTokenUrl = corExchangeTokenUrl;
		this.corCompareRealNameUrl = corCompareRealNameUrl;
		this.perCompareRealNameUrl = perCompareRealNameUrl;
		this.corDetailRealNameUrl = corDetailRealNameUrl;
		this.enableGovRealNameAuth = enableGovRealNameAuth;
		this.verify_mode = verify_mode;
		this.govRequestCod = govRequestCod;
		this.govRealUsername = govRealUsername;
		this.govRealPassword = govRealPassword;
		this.govExchangeTokenUrl = govExchangeTokenUrl;
		this.govCompareRealNameUrl = govCompareRealNameUrl;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IID", unique = true, nullable = false)
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	@Column(name = "SYSNAME")
	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	@Column(name = "SYSURL")
	public String getSysUrl() {
		return sysUrl;
	}

	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}

	@Column(name = "COPYRIGHT")
	public String getCopyRight() {
		return copyRight;
	}

	public void setCopyRight(String copyRight) {
		this.copyRight = copyRight;
	}

	@Column(name = "ISLOGINFAIL")
	public String getIsLoginfail() {
		return isLoginfail;
	}

	public void setIsLoginfail(String isLoginfail) {
		this.isLoginfail = isLoginfail;
	}

	@Column(name = "LOGINERROR")
	public Integer getLoginError() {
		return loginError;
	}

	public void setLoginError(Integer loginError) {
		this.loginError = loginError;
	}

	@Column(name = "BANTIMES")
	public Integer getBanTimes() {
		return banTimes;
	}

	public void setBanTimes(Integer banTimes) {
		this.banTimes = banTimes;
	}

	@Column(name = "PWDLEVEL")
	public String getPwdLevel() {
		return pwdLevel;
	}

	public void setPwdLevel(String pwdLevel) {
		this.pwdLevel = pwdLevel;
	}

	@Column(name = "NETTYPE")
	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	@Column(name = "MODIFYPASSTIME")
	public String getModifyPassTime() {
		return modifyPassTime;
	}

	public void setModifyPassTime(String modifyPassTime) {
		this.modifyPassTime = modifyPassTime;
	}

	@Column(name = "TICKETEFFECTIVETIME")
	public String getTicketEffectiveTime() {
		return ticketEffectiveTime;
	}

	public void setTicketEffectiveTime(String ticketEffectiveTime) {
		this.ticketEffectiveTime = ticketEffectiveTime;
	}

	@Column(name = "TOKENEFFECTIVETIME")
	public String getTokenEffectiveTime() {
		return tokenEffectiveTime;
	}

	public void setTokenEffectiveTime(String tokenEffectiveTime) {
		this.tokenEffectiveTime = tokenEffectiveTime;
	}

	@Column(name = "PERSESSIONTIME")
	public Integer getPerSessionTime() {
		return perSessionTime;
	}

	public void setPerSessionTime(Integer perSessionTime) {
		this.perSessionTime = perSessionTime;
	}

	@Column(name = "CORSESSIONTIME")
	public Integer getCorSessionTime() {
		return corSessionTime;
	}

	public void setCorSessionTime(Integer corSessionTime) {
		this.corSessionTime = corSessionTime;
	}

	@Column(name = "EMAILSMTP")
	public String getEmailSmtp() {
		return emailSmtp;
	}

	public void setEmailSmtp(String emailSmtp) {
		this.emailSmtp = emailSmtp;
	}

	@Column(name = "EMAILPORT")
	public String getEmailPort() {
		return emailPort;
	}

	public void setEmailPort(String emailPort) {
		this.emailPort = emailPort;
	}

	@Column(name = "EMAILBOX")
	public String getEmailBox() {
		return emailBox;
	}

	public void setEmailBox(String emailBox) {
		this.emailBox = emailBox;
	}

	@Column(name = "EMAILPASSWORD")
	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	@Column(name = "EMAILSENDER")
	public String getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(String emailSender) {
		this.emailSender = emailSender;
	}

	@Column(name = "EMAILTITLE")
	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	@Column(name = "EMAILCONTENT")
	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	@Column(name = "EMAILFINDPASSTITLE")
	public String getEmailFindPassTitle() {
		return emailFindPassTitle;
	}

	public void setEmailFindPassTitle(String emailFindPassTitle) {
		this.emailFindPassTitle = emailFindPassTitle;
	}

	@Column(name = "EMAILFINDPASSCONTENT")
	public String getEmailFindPassContent() {
		return emailFindPassContent;
	}

	public void setEmailFindPassContent(String emailFindPassContent) {
		this.emailFindPassContent = emailFindPassContent;
	}

	@Column(name = "ENABLESYNTASK")
	public String getEnableSynTask() {
		return enableSynTask;
	}

	public void setEnableSynTask(String enableSynTask) {
		this.enableSynTask = enableSynTask;
	}

	@Column(name = "SYNCTIME")
	public String getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(String syncTime) {
		this.syncTime = syncTime;
	}

	@Column(name = "CLEARLOGTIME")
	public String getClearLogTime() {
		return clearLogTime;
	}

	public void setClearLogTime(String clearLogTime) {
		this.clearLogTime = clearLogTime;
	}

	@Column(name = "APPID")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name = "APPNAME")
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Column(name = "APPACC")
	public String getAppAcc() {
		return appAcc;
	}

	public void setAppAcc(String appAcc) {
		this.appAcc = appAcc;
	}

	@Column(name = "APPPWD")
	public String getAppPwd() {
		return appPwd;
	}

	public void setAppPwd(String appPwd) {
		this.appPwd = appPwd;
	}

	@Column(name = "IMPORTANTLEVEL")
	public String getImportantLevel() {
		return importantLevel;
	}

	public void setImportantLevel(String importantLevel) {
		this.importantLevel = importantLevel;
	}

	@Column(name = "ISSENDAGAIN")
	public String getIsSendAgain() {
		return isSendAgain;
	}

	public void setIsSendAgain(String isSendAgain) {
		this.isSendAgain = isSendAgain;
	}

	@Column(name = "ISLOSE")
	public String getIsLose() {
		return isLose;
	}

	public void setIsLose(String isLose) {
		this.isLose = isLose;
	}

	@Column(name = "ISUPSTREAM")
	public String getIsUpstream() {
		return isUpstream;
	}

	public void setIsUpstream(String isUpstream) {
		this.isUpstream = isUpstream;
	}

	@Column(name = "URLROOT")
	public String getUrlRoot() {
		return urlRoot;
	}

	public void setUrlRoot(String urlRoot) {
		this.urlRoot = urlRoot;
	}

	@Column(name = "BUSINESSIDFORREGESTINGPER")
	public String getBusinessIdForRegestingPer() {
		return businessIdForRegestingPer;
	}

	public void setBusinessIdForRegestingPer(String businessIdForRegestingPer) {
		this.businessIdForRegestingPer = businessIdForRegestingPer;
	}

	@Column(name = "BUSINESSNAMEFORREGESTINGPER")
	public String getBusinessNameForRegestingPer() {
		return businessNameForRegestingPer;
	}

	public void setBusinessNameForRegestingPer(String businessNameForRegestingPer) {
		this.businessNameForRegestingPer = businessNameForRegestingPer;
	}

	@Column(name = "REGISTPERMESSAGECONTENT")
	public String getRegistPerMessageContent() {
		return registPerMessageContent;
	}

	public void setRegistPerMessageContent(String registPerMessageContent) {
		this.registPerMessageContent = registPerMessageContent;
	}

	@Column(name = "BUSINESSIDFORREGESTINGCOR")
	public String getBusinessIdForRegestingCor() {
		return businessIdForRegestingCor;
	}

	public void setBusinessIdForRegestingCor(String businessIdForRegestingCor) {
		this.businessIdForRegestingCor = businessIdForRegestingCor;
	}

	@Column(name = "BUSINESSNAMEFORREGESTINGCOR")
	public String getBusinessNameForRegestingCor() {
		return businessNameForRegestingCor;
	}

	public void setBusinessNameForRegestingCor(String businessNameForRegestingCor) {
		this.businessNameForRegestingCor = businessNameForRegestingCor;
	}

	@Column(name = "REGISTCORMESSAGECONTENT")
	public String getRegistCorMessageContent() {
		return registCorMessageContent;
	}

	public void setRegistCorMessageContent(String registCorMessageContent) {
		this.registCorMessageContent = registCorMessageContent;
	}

	@Column(name = "BUSINESSIDFORRECOVINGPWD")
	public String getBusinessIdForRecovingPwd() {
		return businessIdForRecovingPwd;
	}

	public void setBusinessIdForRecovingPwd(String businessIdForRecovingPwd) {
		this.businessIdForRecovingPwd = businessIdForRecovingPwd;
	}

	@Column(name = "BUSINESSNAMEFORRECOVINGPWD")
	public String getBusinessNameForRecovingPwd() {
		return businessNameForRecovingPwd;
	}

	public void setBusinessNameForRecovingPwd(String businessNameForRecovingPwd) {
		this.businessNameForRecovingPwd = businessNameForRecovingPwd;
	}

	@Column(name = "RECOVINGPWDCONTENT")
	public String getRecovingPwdContent() {
		return recovingPwdContent;
	}

	public void setRecovingPwdContent(String recovingPwdContent) {
		this.recovingPwdContent = recovingPwdContent;
	}

	@Column(name = "BUSINESSIDFORGETTINGDYNAMICPWD")
	public String getBusinessIdForGettingDynamicPwd() {
		return businessIdForGettingDynamicPwd;
	}

	public void setBusinessIdForGettingDynamicPwd(
			String businessIdForGettingDynamicPwd) {
		this.businessIdForGettingDynamicPwd = businessIdForGettingDynamicPwd;
	}

	@Column(name = "BUSINESSNAMEFORGETTINGDYNAMICPWD")
	public String getBusinessNameForGettingDynamicPwd() {
		return businessNameForGettingDynamicPwd;
	}

	public void setBusinessNameForGettingDynamicPwd(
			String businessNameForGettingDynamicPwd) {
		this.businessNameForGettingDynamicPwd = businessNameForGettingDynamicPwd;
	}

	@Column(name = "VALIDITYPERIOD")
	public String getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	@Column(name = "DYNAMICPWDMESSAGECONTENT")
	public String getDynamicPwdMessageContent() {
		return dynamicPwdMessageContent;
	}

	public void setDynamicPwdMessageContent(String dynamicPwdMessageContent) {
		this.dynamicPwdMessageContent = dynamicPwdMessageContent;
	}

	@Column(name = "ENABLECORREALNAMEAUTH")
	public String getEnableCorRealNameAuth() {
		return enableCorRealNameAuth;
	}

	public void setEnableCorRealNameAuth(String enableCorRealNameAuth) {
		this.enableCorRealNameAuth = enableCorRealNameAuth;
	}

	@Column(name = "CORREQUESTCOD")
	public String getCorRequestCod() {
		return corRequestCod;
	}

	public void setCorRequestCod(String corRequestCod) {
		this.corRequestCod = corRequestCod;
	}

	@Column(name = "CORREALUSERNAME")
	public String getCorRealUsername() {
		return corRealUsername;
	}

	public void setCorRealUsername(String corRealUsername) {
		this.corRealUsername = corRealUsername;
	}

	@Column(name = "CORREALPASSWORD")
	public String getCorRealPassword() {
		return corRealPassword;
	}

	public void setCorRealPassword(String corRealPassword) {
		this.corRealPassword = corRealPassword;
	}

	@Column(name = "COREXCHANGETOKENURL")
	public String getCorExchangeTokenUrl() {
		return corExchangeTokenUrl;
	}

	public void setCorExchangeTokenUrl(String corExchangeTokenUrl) {
		this.corExchangeTokenUrl = corExchangeTokenUrl;
	}

	@Column(name = "CORCOMPAREREALNAMEURL")
	public String getCorCompareRealNameUrl() {
		return corCompareRealNameUrl;
	}

	public void setCorCompareRealNameUrl(String corCompareRealNameUrl) {
		this.corCompareRealNameUrl = corCompareRealNameUrl;
	}

	@Column(name = "PERCOMPAREREALNAMEURL")
	public String getPerCompareRealNameUrl() {
		return perCompareRealNameUrl;
	}

	public void setPerCompareRealNameUrl(String perCompareRealNameUrl) {
		this.perCompareRealNameUrl = perCompareRealNameUrl;
	}

	@Column(name = "CORDETAILREALNAMEURL")
	public String getCorDetailRealNameUrl() {
		return corDetailRealNameUrl;
	}

	public void setCorDetailRealNameUrl(String corDetailRealNameUrl) {
		this.corDetailRealNameUrl = corDetailRealNameUrl;
	}

	@Column(name = "ENABLEGOVREALNAMEAUTH")
	public String getEnableGovRealNameAuth() {
		return enableGovRealNameAuth;
	}

	public void setEnableGovRealNameAuth(String enableGovRealNameAuth) {
		this.enableGovRealNameAuth = enableGovRealNameAuth;
	}

	@Column(name = "VERIFY_MODE")
	public Integer getVerify_mode() {
		return verify_mode;
	}

	public void setVerify_mode(Integer verify_mode) {
		this.verify_mode = verify_mode;
	}

	@Column(name = "GOVREQUESTCOD")
	public String getGovRequestCod() {
		return govRequestCod;
	}

	public void setGovRequestCod(String govRequestCod) {
		this.govRequestCod = govRequestCod;
	}

	@Column(name = "GOVREALUSERNAME")
	public String getGovRealUsername() {
		return govRealUsername;
	}

	public void setGovRealUsername(String govRealUsername) {
		this.govRealUsername = govRealUsername;
	}

	@Column(name = "GOVREALPASSWORD")
	public String getGovRealPassword() {
		return govRealPassword;
	}

	public void setGovRealPassword(String govRealPassword) {
		this.govRealPassword = govRealPassword;
	}

	@Column(name = "GOVEXCHANGETOKENURL")
	public String getGovExchangeTokenUrl() {
		return govExchangeTokenUrl;
	}

	public void setGovExchangeTokenUrl(String govExchangeTokenUrl) {
		this.govExchangeTokenUrl = govExchangeTokenUrl;
	}

	@Column(name = "GOVCOMPAREREALNAMEURL")
	public String getGovCompareRealNameUrl() {
		return govCompareRealNameUrl;
	}

	public void setGovCompareRealNameUrl(String govCompareRealNameUrl) {
		this.govCompareRealNameUrl = govCompareRealNameUrl;
	}
}
