package com.gsww.uids.gateway.job;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gsww.uids.gateway.dao.application.ApplicationDao;
import com.gsww.uids.gateway.dao.sysview.SysViewDao;
import com.gsww.uids.gateway.httpClient.MyHttpClient;
import com.gsww.uids.gateway.util.Contants;
import com.gsww.uids.gateway.util.SpringContextHolder;

public class ImmediateSyncThread extends Thread{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static ApplicationDao applicationDao;
	private static SysViewDao sysViewDao;
	private Map<String, Object> syncMap;
	
	static {
		applicationDao = SpringContextHolder.getBean("applicationDao");
		sysViewDao = SpringContextHolder.getBean("sysViewDao");
	}
	
	public ImmediateSyncThread(Map<String, Object> syncMap) {
		this.syncMap = syncMap;
	}
	
	public void run() {
		try {
			logger.info("本次同步开始,本条记录IID：" + syncMap.get("iid"));
			System.out.println("本次同步开始,本条记录IID：" + syncMap.get("iid"));
			int iid = (int) syncMap.get("iid"); //本条同步记录ID
			int appid = (int) syncMap.get("appid"); //本条同步记录的应用ID
			Map<String,Object> appMap = applicationDao.selectAppById(appid);
			int transtype = (int) appMap.get("transtype");
			logger.info("应用名称："+appMap.get("name")+" 请求方式："+transtype);
			
			sysViewDao.insertSysViewCurr(iid);
			logger.info("插入到当前同步数据表，IID="+iid);
			
			//sysViewDao.deleteSysView(iid);
			logger.info("删除实时同步数据表，IID="+iid);
			
			Map<String,Object> returnMap = new HashMap<String, Object>();
			
			//推送信息到第三方，分为Http和webservice
			if(Contants.TRANS_HTTP == transtype){
				// TODO http方式通信
				//Map<String, String> sendMap = new HashMap<String, String>();//TODO 获取发送报文 暂时为空
				//returnMap = MyHttpClient.syncToApplication((String) appMap.get("appurl"), sendMap.toString());
			}else if(Contants.TRANS_WEBSERVICE== transtype){
				//webService方式通信
				String sendStr = "";//获取发送的数据
				//returnMap = MyHttpClient.syncToApplication((String) appMap.get("appurl"), sendStr);
			}
			 
			//更新当前表状态
			if(returnMap!=null){
				String status =  (String) returnMap.get("status");
				if("200".equals(status)){
					if(!"".equals(returnMap.get("respMsg")) && null != returnMap.get("respMsg")){
						JSONObject returnInfo = JSONObject.fromObject(returnMap.get("respMsg"));
						if(Contants.SYNC_SUCCESS_TURE.equals(returnInfo.getString("success"))){
							
							sysViewDao.updateSysViewCurr(iid, Contants.OPTRESULT_SUCESS);
							logger.info("数据同步成功，更新状态:"+Contants.OPTRESULT_SUCESS);
						}else if(Contants.SYNC_SUCCESS_FALSE.equals(returnInfo.getString("success"))){
							
							sysViewDao.updateSysViewCurr(iid, Contants.OPTRESULT_FALSE);
							logger.info("数据同步失败，更新状态:"+Contants.OPTRESULT_FALSE);
						}
					}
				}else{
					sysViewDao.updateSysViewCurr(iid, Contants.OPTRESULT_NET_BARRIER);
					logger.info("网络不通，更新状态:"+Contants.OPTRESULT_NET_BARRIER);
				}
			}
			
		} catch (Exception e) {
			logger.info("错误数据IID为:"+syncMap.get("iid")+"e.getMessage():"+e.getMessage());
			e.printStackTrace();
		}
		
	}
}
