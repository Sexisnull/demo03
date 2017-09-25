package com.gsww.uids.constant;

import com.hanweb.common.util.SpringUtil;

public class JisSettings
{
  private String sysName = "统一身份认证系统";

  private String sysUrl = "";

  private String copyRight = "";

  private String isRegister = "";

  private String registerType = "";

  private String isFindPwd = "";

  private String pwdLevel = "1";

  private String isLoginfail = "";

  private int loginError = 3;

  private int banTimes = 15;

  private String emailSmtp = "";

  private String emailPort = "";

  private String emailBox = "";

  private String emailPassword = "";

  private String emailSender = "";

  private String emailTitle = "";

  private String emailContent = "";

  private String emailFindPassTitle = "";

  private String emailFindPassContent = "";

  private String syncTime = "";

  private String clearLogTime = "";

  private String ticketEffectiveTime = "";

  private String tokenEffectiveTime = "";

  private int perSessionTime = 30;

  private int corSessionTime = 30;

  private String perGotoUrl = "";

  private String corGotoUrl = "";

  private String netType = "1";

  private String enableSynTask = "1";

  private String enableBackupsTask = "1";

  private String realNameAuth = "1";

  private String realNameAuthUrl = "";

  private String appId = "";

  private String appName = "";

  private String appAcc = "";

  private String appPwd = "";

  private String importantLevel = "";

  private String isSendAgain = "";

  private String isLose = "";

  private String isUpstream = "";

  private String urlRoot = "";

  private String validityPeriod = "";

  private String dynamicPwdMessageContent = "";

  private String businessIdForGettingDynamicPwd = "";

  private String businessNameForGettingDynamicPwd = "";

  private String businessIdForRegestingPer = "";

  private String businessNameForRegestingPer = "";

  private String registPerMessageContent = "";

  private String businessIdForRecovingPwd = "";

  private String businessNameForRecovingPwd = "";

  private String recovingPwdContent = "";

  private String businessIdForRegestingCor = "";

  private String businessNameForRegestingCor = "";

  private String registCorMessageContent = "";

  private String enableCorRealNameAuth = "0";

  private String corRequestCod = "";

  private String corRealUsername = "";

  private String corRealPassword = "";

  private String corExchangeTokenUrl = "";

  private String corCompareRealNameUrl = "";

  private String perCompareRealNameUrl = "";

  private String corDetailRealNameUrl = "";

  private String govRequestCod = "";

  private String govRealUsername = "";

  private String govRealPassword = "";

  private String govCompareRealNameUrl = "";
  private Integer verify_mode;
  private String enableGovRealNameAuth = "";

  private String govExchangeTokenUrl = "";

  private String modifyPassTime = "0";

  public String getModifyPassTime()
  {
    return this.modifyPassTime;
  }

  public void setModifyPassTime(String modifyPassTime) {
    this.modifyPassTime = modifyPassTime;
  }

  public String getBusinessIdForRegestingCor() {
    return this.businessIdForRegestingCor;
  }

  public void setBusinessIdForRegestingCor(String businessIdForRegestingCor) {
    this.businessIdForRegestingCor = businessIdForRegestingCor;
  }

  public String getBusinessNameForRegestingCor() {
    return this.businessNameForRegestingCor;
  }

  public void setBusinessNameForRegestingCor(String businessNameForRegestingCor) {
    this.businessNameForRegestingCor = businessNameForRegestingCor;
  }

  public String getRegistCorMessageContent() {
    return this.registCorMessageContent;
  }

  public void setRegistCorMessageContent(String registCorMessageContent) {
    this.registCorMessageContent = registCorMessageContent;
  }

  public String getBusinessIdForRecovingPwd() {
    return this.businessIdForRecovingPwd;
  }

  public void setBusinessIdForRecovingPwd(String businessIdForRecovingPwd) {
    this.businessIdForRecovingPwd = businessIdForRecovingPwd;
  }

  public String getBusinessNameForRecovingPwd() {
    return this.businessNameForRecovingPwd;
  }

  public void setBusinessNameForRecovingPwd(String businessNameForRecovingPwd) {
    this.businessNameForRecovingPwd = businessNameForRecovingPwd;
  }

  public String getRecovingPwdContent() {
    return this.recovingPwdContent;
  }

  public void setRecovingPwdContent(String recovingPwdContent) {
    this.recovingPwdContent = recovingPwdContent;
  }

  public String getRegistPerMessageContent() {
    return this.registPerMessageContent;
  }

  public void setRegistPerMessageContent(String registPerMessageContent) {
    this.registPerMessageContent = registPerMessageContent;
  }

  public String getBusinessIdForRegestingPer() {
    return this.businessIdForRegestingPer;
  }

  public void setBusinessIdForRegestingPer(String businessIdForRegestingPer) {
    this.businessIdForRegestingPer = businessIdForRegestingPer;
  }

  public String getBusinessNameForRegestingPer() {
    return this.businessNameForRegestingPer;
  }

  public void setBusinessNameForRegestingPer(String businessNameForRegestingPer) {
    this.businessNameForRegestingPer = businessNameForRegestingPer;
  }

  public String getBusinessNameForGettingDynamicPwd() {
    return this.businessNameForGettingDynamicPwd;
  }

  public void setBusinessNameForGettingDynamicPwd(String businessNameForGettingDynamicPwd)
  {
    this.businessNameForGettingDynamicPwd = businessNameForGettingDynamicPwd;
  }

  public String getBusinessIdForGettingDynamicPwd() {
    return this.businessIdForGettingDynamicPwd;
  }

  public void setBusinessIdForGettingDynamicPwd(String businessIdForGettingDynamicPwd)
  {
    this.businessIdForGettingDynamicPwd = businessIdForGettingDynamicPwd;
  }

  public String getAppId() {
    return this.appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getAppName() {
    return this.appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getAppAcc() {
    return this.appAcc;
  }

  public void setAppAcc(String appAcc) {
    this.appAcc = appAcc;
  }

  public String getAppPwd() {
    return this.appPwd;
  }

  public void setAppPwd(String appPwd) {
    this.appPwd = appPwd;
  }

  public String getImportantLevel() {
    return this.importantLevel;
  }

  public void setImportantLevel(String importantLevel) {
    this.importantLevel = importantLevel;
  }

  public String getIsSendAgain() {
    return this.isSendAgain;
  }

  public void setIsSendAgain(String isSendAgain) {
    this.isSendAgain = isSendAgain;
  }

  public String getIsLose() {
    return this.isLose;
  }

  public void setIsLose(String isLose) {
    this.isLose = isLose;
  }

  public String getIsUpstream() {
    return this.isUpstream;
  }

  public void setIsUpstream(String isUpstream) {
    this.isUpstream = isUpstream;
  }

  public String getUrlRoot() {
    return this.urlRoot;
  }

  public void setUrlRoot(String urlRoot) {
    this.urlRoot = urlRoot;
  }

  public String getValidityPeriod() {
    return this.validityPeriod;
  }

  public void setValidityPeriod(String validityPeriod) {
    this.validityPeriod = validityPeriod;
  }

  public String getDynamicPwdMessageContent() {
    return this.dynamicPwdMessageContent;
  }

  public void setDynamicPwdMessageContent(String dynamicPwdMessageContent) {
    this.dynamicPwdMessageContent = dynamicPwdMessageContent;
  }

  public static JisSettings getSettings()
  {
    return (JisSettings)SpringUtil.getBean(JisSettings.class);
  }

  public String getSysName() {
    return this.sysName;
  }

  public void setSysName(String sysName) {
    this.sysName = sysName;
  }

  public String getSysUrl() {
    return this.sysUrl;
  }

  public void setSysUrl(String sysUrl) {
    this.sysUrl = sysUrl;
  }

  public String getCopyRight() {
    return this.copyRight;
  }

  public void setCopyRight(String copyRight) {
    this.copyRight = copyRight;
  }

  public String getIsRegister() {
    return this.isRegister;
  }

  public void setIsRegister(String isRegister) {
    this.isRegister = isRegister;
  }

  public String getRegisterType() {
    return this.registerType;
  }

  public void setRegisterType(String registerType) {
    this.registerType = registerType;
  }

  public String getIsFindPwd() {
    return this.isFindPwd;
  }

  public void setIsFindPwd(String isFindPwd) {
    this.isFindPwd = isFindPwd;
  }

  public String getPwdLevel() {
    return this.pwdLevel;
  }

  public void setPwdLevel(String pwdLevel) {
    this.pwdLevel = pwdLevel;
  }

  public String getIsLoginfail() {
    return this.isLoginfail;
  }

  public void setIsLoginfail(String isLoginfail) {
    this.isLoginfail = isLoginfail;
  }

  public int getLoginError() {
    return this.loginError;
  }

  public void setLoginError(int loginError) {
    this.loginError = loginError;
  }

  public int getBanTimes() {
    return this.banTimes;
  }

  public void setBanTimes(int banTimes) {
    this.banTimes = banTimes;
  }

  public String getEmailSmtp() {
    return this.emailSmtp;
  }

  public void setEmailSmtp(String emailSmtp) {
    this.emailSmtp = emailSmtp;
  }

  public String getEmailBox() {
    return this.emailBox;
  }

  public void setEmailBox(String emailBox) {
    this.emailBox = emailBox;
  }

  public String getEmailPassword() {
    return this.emailPassword;
  }

  public void setEmailPassword(String emailPassword) {
    this.emailPassword = emailPassword;
  }

  public String getEmailSender() {
    return this.emailSender;
  }

  public void setEmailSender(String emailSender) {
    this.emailSender = emailSender;
  }

  public String getEmailTitle() {
    return this.emailTitle;
  }

  public void setEmailTitle(String emailTitle) {
    this.emailTitle = emailTitle;
  }

  public String getEmailContent() {
    return this.emailContent;
  }

  public void setEmailContent(String emailContent) {
    this.emailContent = emailContent;
  }

  public String getEmailFindPassTitle() {
    return this.emailFindPassTitle;
  }

  public void setEmailFindPassTitle(String emailFindPassTitle) {
    this.emailFindPassTitle = emailFindPassTitle;
  }

  public String getEmailFindPassContent() {
    return this.emailFindPassContent;
  }

  public void setEmailFindPassContent(String emailFindPassContent) {
    this.emailFindPassContent = emailFindPassContent;
  }

  public String getSyncTime() {
    return this.syncTime;
  }

  public void setSyncTime(String syncTime) {
    this.syncTime = syncTime;
  }

  public String getClearLogTime() {
    return this.clearLogTime;
  }

  public void setClearLogTime(String clearLogTime) {
    this.clearLogTime = clearLogTime;
  }

  public String getEmailPort() {
    return this.emailPort;
  }

  public void setEmailPort(String emailPort) {
    this.emailPort = emailPort;
  }

  public String getTicketEffectiveTime() {
    return this.ticketEffectiveTime;
  }

  public void setTicketEffectiveTime(String ticketEffectiveTime) {
    this.ticketEffectiveTime = ticketEffectiveTime;
  }

  public String getTokenEffectiveTime() {
    return this.tokenEffectiveTime;
  }

  public void setTokenEffectiveTime(String tokenEffectiveTime) {
    this.tokenEffectiveTime = tokenEffectiveTime;
  }

  public int getPerSessionTime() {
    return this.perSessionTime;
  }

  public void setPerSessionTime(int perSessionTime) {
    this.perSessionTime = perSessionTime;
  }

  public int getCorSessionTime() {
    return this.corSessionTime;
  }

  public void setCorSessionTime(int corSessionTime) {
    this.corSessionTime = corSessionTime;
  }

  public String getPerGotoUrl() {
    return this.perGotoUrl;
  }

  public void setPerGotoUrl(String perGotoUrl) {
    this.perGotoUrl = perGotoUrl;
  }

  public String getCorGotoUrl() {
    return this.corGotoUrl;
  }

  public void setCorGotoUrl(String corGotoUrl) {
    this.corGotoUrl = corGotoUrl;
  }

  public String getNetType() {
    return this.netType;
  }

  public void setNetType(String netType) {
    this.netType = netType;
  }

  public String getEnableSynTask() {
    return this.enableSynTask;
  }

  public void setEnableSynTask(String enableSynTask) {
    this.enableSynTask = enableSynTask;
  }

  public String getEnableBackupsTask() {
    return this.enableBackupsTask;
  }

  public void setEnableBackupsTask(String enableBackupsTask) {
    this.enableBackupsTask = enableBackupsTask;
  }

  public String getRealNameAuth() {
    return this.realNameAuth;
  }

  public void setRealNameAuth(String realNameAuth) {
    this.realNameAuth = realNameAuth;
  }

  public String getRealNameAuthUrl() {
    return this.realNameAuthUrl;
  }

  public void setRealNameAuthUrl(String realNameAuthUrl) {
    this.realNameAuthUrl = realNameAuthUrl;
  }

  public String getEnableCorRealNameAuth() {
    return this.enableCorRealNameAuth;
  }

  public void setEnableCorRealNameAuth(String enableCorRealNameAuth) {
    this.enableCorRealNameAuth = enableCorRealNameAuth;
  }

  public String getCorRequestCod() {
    return this.corRequestCod;
  }

  public void setCorRequestCod(String corRequestCod) {
    this.corRequestCod = corRequestCod;
  }

  public String getCorRealUsername() {
    return this.corRealUsername;
  }

  public void setCorRealUsername(String corRealUsername) {
    this.corRealUsername = corRealUsername;
  }

  public String getCorRealPassword() {
    return this.corRealPassword;
  }

  public void setCorRealPassword(String corRealPassword) {
    this.corRealPassword = corRealPassword;
  }

  public String getCorExchangeTokenUrl() {
    return this.corExchangeTokenUrl;
  }

  public void setCorExchangeTokenUrl(String corExchangeTokenUrl) {
    this.corExchangeTokenUrl = corExchangeTokenUrl;
  }

  public String getCorCompareRealNameUrl() {
    return this.corCompareRealNameUrl;
  }

  public void setCorCompareRealNameUrl(String corCompareRealNameUrl) {
    this.corCompareRealNameUrl = corCompareRealNameUrl;
  }

  public String getPerCompareRealNameUrl() {
    return this.perCompareRealNameUrl;
  }

  public void setPerCompareRealNameUrl(String perCompareRealNameUrl) {
    this.perCompareRealNameUrl = perCompareRealNameUrl;
  }

  public String getCorDetailRealNameUrl() {
    return this.corDetailRealNameUrl;
  }

  public void setCorDetailRealNameUrl(String corDetailRealNameUrl) {
    this.corDetailRealNameUrl = corDetailRealNameUrl;
  }

  public String getGovCompareRealNameUrl() {
    return this.govCompareRealNameUrl;
  }

  public void setGovCompareRealNameUrl(String govCompareRealNameUrl) {
    this.govCompareRealNameUrl = govCompareRealNameUrl;
  }

  public Integer getVerify_mode() {
    return this.verify_mode;
  }

  public void setVerify_mode(Integer verify_mode) {
    this.verify_mode = verify_mode;
  }

  public String getGovRequestCod() {
    return this.govRequestCod;
  }

  public void setGovRequestCod(String govRequestCod) {
    this.govRequestCod = govRequestCod;
  }

  public String getGovRealUsername() {
    return this.govRealUsername;
  }

  public void setGovRealUsername(String govRealUsername) {
    this.govRealUsername = govRealUsername;
  }

  public String getGovRealPassword() {
    return this.govRealPassword;
  }

  public void setGovRealPassword(String govRealPassword) {
    this.govRealPassword = govRealPassword;
  }

  public String getEnableGovRealNameAuth() {
    return this.enableGovRealNameAuth;
  }

  public void setEnableGovRealNameAuth(String enableGovRealNameAuth) {
    this.enableGovRealNameAuth = enableGovRealNameAuth;
  }

  public String getGovExchangeTokenUrl() {
    return this.govExchangeTokenUrl;
  }

  public void setGovExchangeTokenUrl(String govExchangeTokenUrl) {
    this.govExchangeTokenUrl = govExchangeTokenUrl;
  }
}