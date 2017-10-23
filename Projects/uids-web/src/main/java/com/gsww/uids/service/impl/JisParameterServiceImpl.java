package com.gsww.uids.service.impl;

import java.net.URI;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.constant.JisSettings;
import com.gsww.uids.dao.JisParameterDao;
import com.gsww.uids.entity.JisParameter;
import com.gsww.uids.service.JisParameterService;
import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.Properties;
/**
 * 系统参数业务实现类
 * @author Seven
 *
 */
@Transactional
@Service("JisParameterService")
public class JisParameterServiceImpl implements JisParameterService {

	@Autowired
	private JisParameterDao jisParameterDao ;

	@Override
	public JisParameter save(JisParameter entity) throws Exception {
		saveToBeanAndProperties(entity);
		String emailPassword = entity.getEmailPassword();
	      if ("".equals(emailPassword)) {
	        emailPassword = JisSettings.getSettings().getEmailPassword();
	        entity.setEmailPassword(emailPassword);
	    }
		return jisParameterDao.save(entity);
	}

	@Override
	public Page<JisParameter> getParameterPage(Specification<JisParameter> spec,
			PageRequest pageRequest) {
		return jisParameterDao.findAll(spec, pageRequest);
	}

	@Override
	public JisParameter findByKey(Integer iid) throws Exception {
		JisParameter jisParameter=jisParameterDao.findByIid(iid);
		return jisParameter;
	}
	
	/**
	 * 保存对象到配置文件中，并且刷新jisSetting对象
	 * @param entity
	 */
	private  void saveToBeanAndProperties(JisParameter entity) {
		try
	    {
	      String emailPassword = entity.getEmailPassword();
	      if ("".equals(emailPassword)) {
	        emailPassword = JisSettings.getSettings().getEmailPassword();
	      }

	      JisSettings.getSettings().setSysName(entity.getSysName());
	      JisSettings.getSettings().setSysUrl(entity.getSysUrl());
	      JisSettings.getSettings().setCopyRight(entity.getCopyRight());
	      JisSettings.getSettings().setNetType(entity.getNetType());
	      JisSettings.getSettings().setModifyPassTime(entity.getModifyPassTime());

	      JisSettings.getSettings().setIsRegister(entity.getIsRegister());
	      JisSettings.getSettings().setRegisterType(entity.getRegisterType());
	      JisSettings.getSettings().setIsFindPpd(entity.getIsFindPwd());
	      JisSettings.getSettings().setPpdLevel(entity.getPwdLevel());
	      JisSettings.getSettings().setIsLoginfail(entity.getIsLoginfail());
	      JisSettings.getSettings().setLoginError(entity.getLoginError());
	      JisSettings.getSettings().setBanTimes(entity.getBanTimes());
	      JisSettings.getSettings().setPerSessionTime(entity.getPerSessionTime());
	      JisSettings.getSettings().setCorSessionTime(entity.getCorSessionTime());

	      JisSettings.getSettings().setEmailSmtp(entity.getEmailSmtp());
	      JisSettings.getSettings().setEmailPort(entity.getEmailPort());
	      JisSettings.getSettings().setEmailBox(entity.getEmailBox());
	      JisSettings.getSettings().setEmailPassword(emailPassword);
	      JisSettings.getSettings().setEmailSender(entity.getEmailSender());
	      JisSettings.getSettings().setEmailTitle(entity.getEmailTitle());
	      JisSettings.getSettings().setEmailContent(entity.getEmailContent());
	      JisSettings.getSettings().setEmailFindPassTitle(entity.getEmailFindPassTitle());
	      JisSettings.getSettings().setEmailFindPassContent(entity.getEmailFindPassContent());

	      JisSettings.getSettings().setSyncTime(entity.getSyncTime());
	      JisSettings.getSettings().setClearLogTime(entity.getClearLogTime());
	      JisSettings.getSettings().setEnableSynTask(entity.getEnableSynTask());
	      JisSettings.getSettings().setRealNameAuth(entity.getRealNameAuth());
	      JisSettings.getSettings().setRealNameAuthUrl(entity.getRealNameAuthUrl());

	      JisSettings.getSettings().setAppId(entity.getAppId());
	      JisSettings.getSettings().setAppName(entity.getAppName());
	      JisSettings.getSettings().setAppAcc(entity.getAppAcc());
	      JisSettings.getSettings().setAppPpd(entity.getAppPwd());
	      JisSettings.getSettings().setImportantLevel(entity.getImportantLevel());
	      JisSettings.getSettings().setIsSendAgain(entity.getIsSendAgain());
	      JisSettings.getSettings().setIsLose(entity.getIsLose());
	      JisSettings.getSettings().setIsUpstream(entity.getIsUpstream());
	      JisSettings.getSettings().setUrlRoot(entity.getUrlRoot());
	      JisSettings.getSettings().setValidityPeriod(entity.getValidityPeriod());
	      JisSettings.getSettings().setDynamicPpdMessageContent(entity.getDynamicPwdMessageContent());

	      JisSettings.getSettings().setBusinessIdForGettingDynamicPpd(entity.getBusinessIdForGettingDynamicPwd());
	      JisSettings.getSettings().setBusinessNameForGettingDynamicPpd(entity.getBusinessNameForGettingDynamicPwd());
	      JisSettings.getSettings().setBusinessIdForRegestingPer(entity.getBusinessIdForRegestingPer());
	      JisSettings.getSettings().setBusinessNameForRegestingPer(entity.getBusinessNameForRegestingPer());
	      JisSettings.getSettings().setRegistPerMessageContent(entity.getRegistPerMessageContent());
	      JisSettings.getSettings().setBusinessIdForRecovingPpd(entity.getBusinessIdForRecovingPwd());
	      JisSettings.getSettings().setBusinessNameForRecovingPpd(entity.getBusinessNameForRecovingPwd());
	      JisSettings.getSettings().setRecovingPpdContent(entity.getRecovingPwdContent());
	      JisSettings.getSettings().setBusinessIdForRegestingCor(entity.getBusinessIdForRegestingCor());
	      JisSettings.getSettings().setBusinessNameForRegestingCor(entity.getBusinessNameForRegestingCor());
	      JisSettings.getSettings().setRegistCorMessageContent(entity.getRegistCorMessageContent());

	      JisSettings.getSettings().setEnableCorRealNameAuth(entity.getEnableCorRealNameAuth());
	      JisSettings.getSettings().setCorRequestCod(entity.getCorRequestCod());
	      JisSettings.getSettings().setCorRealUsername(entity.getCorRealUsername());
	      JisSettings.getSettings().setCorRealPassword(entity.getCorRealPassword());
	      JisSettings.getSettings().setCorExchangeTokenUrl(entity.getCorExchangeTokenUrl());
	      JisSettings.getSettings().setCorCompareRealNameUrl(entity.getCorCompareRealNameUrl());
	      JisSettings.getSettings().setPerCompareRealNameUrl(entity.getPerCompareRealNameUrl());

	      JisSettings.getSettings().setGovRequestCod(entity.getGovRequestCod());
	      JisSettings.getSettings().setGovRealPassword(entity.getGovRealPassword());
	      JisSettings.getSettings().setGovRealUsername(entity.getGovRealUsername());
	      JisSettings.getSettings().setGovExchangeTokenUrl(entity.getGovExchangeTokenUrl());
	      JisSettings.getSettings().setGovCompareRealNameUrl(entity.getGovCompareRealNameUrl());
	      JisSettings.getSettings().setVerify_mode(entity.getVerify_mode());
	      JisSettings.getSettings().setEnableGovRealNameAuth(entity.getEnableGovRealNameAuth());
	      
	      URL uri = this.getClass().getClassLoader().getResource("jis.properties");
	      Properties properties = new Properties(uri.getPath());

	      properties.setProperty("sysName", JisSettings.getSettings().getSysName());
	      properties.setProperty("sysUrl", JisSettings.getSettings().getSysUrl());
	      properties.setProperty("copyRight", JisSettings.getSettings().getCopyRight());
	      properties.setProperty("netType", JisSettings.getSettings().getNetType());
	      properties.setProperty("modifyPassTime", JisSettings.getSettings().getModifyPassTime());

	      properties.setProperty("isRegister", JisSettings.getSettings().getIsRegister());
	      properties.setProperty("registerType", JisSettings.getSettings().getRegisterType());
	      properties.setProperty("isFindPwd", JisSettings.getSettings().getIsFindPpd());
	      properties.setProperty("pwdLevel", JisSettings.getSettings().getPpdLevel());
	      properties.setProperty("isLoginfail", JisSettings.getSettings().getIsLoginfail());
	      properties.setProperty("loginError", JisSettings.getSettings().getLoginError()+"");
	      properties.setProperty("banTimes", JisSettings.getSettings().getBanTimes()+"");
	      properties.setProperty("perSessionTime", JisSettings.getSettings().getPerSessionTime()+"");
	      properties.setProperty("corSessionTime", JisSettings.getSettings().getCorSessionTime()+"");

	      properties.setProperty("ticketEffectiveTime", JisSettings.getSettings().getTicketEffectiveTime());
	      properties.setProperty("tokenEffectiveTime", JisSettings.getSettings().getTokenEffectiveTime());

	      properties.setProperty("emailSmtp", JisSettings.getSettings().getEmailSmtp());
	      properties.setProperty("emailPort", JisSettings.getSettings().getEmailPort());
	      properties.setProperty("emailBox", JisSettings.getSettings().getEmailBox());
	      properties.setProperty("emailPassword", JisSettings.getSettings().getEmailPassword());
	      properties.setProperty("emailSender", JisSettings.getSettings().getEmailSender());
	      properties.setProperty("emailTitle", JisSettings.getSettings().getEmailTitle());
	      properties.setProperty("emailContent", JisSettings.getSettings().getEmailContent());
	      properties.setProperty("emailFindPassTitle", 
	        JisSettings.getSettings().getEmailFindPassTitle());
	      properties.setProperty("emailFindPassContent", 
	        JisSettings.getSettings().getEmailFindPassContent());

	      properties.setProperty("syncTime", JisSettings.getSettings().getSyncTime());
	      properties.setProperty("clearLogTime", JisSettings.getSettings().getClearLogTime());
	      properties.setProperty("enableSynTask", JisSettings.getSettings().getEnableSynTask());
	      properties.setProperty("realNameAuth", JisSettings.getSettings().getRealNameAuth());
	      properties.setProperty("realNameAuthUrl", JisSettings.getSettings().getRealNameAuthUrl());

	      properties.setProperty("appId", JisSettings.getSettings().getAppId());
	      properties.setProperty("appName", JisSettings.getSettings().getAppName());
	      properties.setProperty("appAcc", JisSettings.getSettings().getAppAcc());
	      properties.setProperty("appPwd", JisSettings.getSettings().getAppPpd());
	      properties.setProperty("importantLevel", JisSettings.getSettings().getImportantLevel());
	      properties.setProperty("isSendAgain", JisSettings.getSettings().getIsSendAgain());
	      properties.setProperty("isLose", JisSettings.getSettings().getIsLose());
	      properties.setProperty("isUpstream", JisSettings.getSettings().getIsUpstream());
	      properties.setProperty("urlRoot", JisSettings.getSettings().getUrlRoot());
	      properties.setProperty("validityPeriod", JisSettings.getSettings().getValidityPeriod());
	      properties.setProperty("dynamicPwdMessageContent", JisSettings.getSettings().getDynamicPpdMessageContent());

	      properties.setProperty("businessIdForGettingDynamicPwd", JisSettings.getSettings().getBusinessIdForGettingDynamicPpd());
	      properties.setProperty("businessNameForGettingDynamicPwd", JisSettings.getSettings().getBusinessNameForGettingDynamicPpd());
	      properties.setProperty("businessIdForRegestingPer", JisSettings.getSettings().getBusinessIdForRegestingPer());
	      properties.setProperty("businessNameForRegestingPer", JisSettings.getSettings().getBusinessNameForRegestingPer());
	      properties.setProperty("registPerMessageContent", JisSettings.getSettings().getRegistPerMessageContent());
	      properties.setProperty("businessIdForRecovingPwd", JisSettings.getSettings().getBusinessIdForRecovingPpd());
	      properties.setProperty("businessNameForRecovingPwd", JisSettings.getSettings().getBusinessNameForRecovingPpd());
	      properties.setProperty("recovingPwdContent", JisSettings.getSettings().getRecovingPpdContent());
	      properties.setProperty("businessIdForRegestingCor", JisSettings.getSettings().getBusinessIdForRegestingCor());
	      properties.setProperty("businessNameForRegestingCor", JisSettings.getSettings().getBusinessIdForRegestingCor());
	      properties.setProperty("registCorMessageContent", JisSettings.getSettings().getRegistCorMessageContent());

	      properties.setProperty("enableCorRealNameAuth", JisSettings.getSettings().getEnableCorRealNameAuth());
	      properties.setProperty("corRequestCod", JisSettings.getSettings().getCorRequestCod());
	      properties.setProperty("corRealPassword", JisSettings.getSettings().getCorRealPassword());
	      properties.setProperty("corRealPassword", JisSettings.getSettings().getCorRealPassword());
	      properties.setProperty("corExchangeTokenUrl", JisSettings.getSettings().getCorExchangeTokenUrl());
	      properties.setProperty("corCompareRealNameUrl", JisSettings.getSettings().getCorCompareRealNameUrl());

	      properties.setProperty("perCompareRealNameUrl", JisSettings.getSettings().getPerCompareRealNameUrl());

	      properties.setProperty("govRequestCod", JisSettings.getSettings().getGovRequestCod());
	      properties.setProperty("govRealUsername", JisSettings.getSettings().getGovRealUsername());
	      properties.setProperty("govRealPassword", JisSettings.getSettings().getGovRealPassword());
	      properties.setProperty("govCompareRealNameUrl", JisSettings.getSettings().getGovCompareRealNameUrl());
	      properties.setProperty("verify_mode", JisSettings.getSettings().getVerify_mode()+"");
	      properties.setProperty("enableGovRealNameAuth", JisSettings.getSettings().getEnableGovRealNameAuth());

	      properties.save();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
	}
}
