package com.gsww.uids.gateway.job;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.gsww.uids.gateway.dao.application.ApplicationDao;
import com.gsww.uids.gateway.dao.sysview.SysViewDao;
import com.gsww.uids.gateway.dao.sysview.SysViewDetailDao;
import com.gsww.uids.gateway.httpClient.MyHttpClient;
import com.gsww.uids.gateway.util.Constants;
import com.gsww.uids.gateway.util.SpringContextHolder;

public class ImmediateSyncThread extends Thread {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static ApplicationDao applicationDao;
	private static SysViewDao sysViewDao;
	private static SysViewDetailDao sysViewDetailDao;
	private Map<String, Object> syncMap;

	static {
		applicationDao = SpringContextHolder.getBean("applicationDao");
		sysViewDao = SpringContextHolder.getBean("sysViewDao");
		sysViewDetailDao = SpringContextHolder.getBean("sysViewDetailDao");
	}

	public ImmediateSyncThread(Map<String, Object> syncMap) {
		this.syncMap = syncMap;
	}

	public void run() {
		try {
			logger.info("本次同步开始,本条记录IID：" + syncMap.get("iid"));
			System.out.println("本次同步开始,本条记录IID：" + syncMap.get("iid"));
			int iid = Integer.parseInt(syncMap.get("iid").toString()); // 本条同步记录ID
			int appid = Integer.parseInt(syncMap.get("appid").toString()); // 本条同步记录的应用ID
			Map<String, Object> appMap = applicationDao.selectAppById(appid);
			int transtype = Integer.parseInt(appMap.get("transtype").toString());
			logger.info("本次同步应用名称：" + appMap.get("name") + " 请求方式：" + transtype);

			sysViewDao.insertSysViewCurr(iid);
			logger.info("本条记录插入到当前同步数据表IID=" + iid);

			// sysViewDao.deleteSysView(iid);
			logger.info("实时同步数据表删除本条记录IID=" + iid);

			Map<String, Object> returnMap = new HashMap<String, Object>();

			// 推送信息到第三方
			if (Constants.TRANS_HTTP == transtype) {

				// TODO http方式通信
				// 获取发送报文 暂时为空
				// Map<String, String> sendMap = new HashMap<String, String>();
				// returnMap = MyHttpClient.syncToApplication((String)
				// appMap.get("appurl"), sendMap.toString());
			} else if (Constants.TRANS_WEBSERVICE == transtype) {

				// webService方式通信
				Map<String, Object> sendMap = sysViewDetailDao.findDetailById(iid);
				String sendmsg = (String) sendMap.get("sendmsg");
				returnMap = MyHttpClient.syncToApplication((String) appMap.get("appurl"), sendmsg);
			}

			// 更新当前数据表状态
			if (returnMap != null) {
				String status = (String) returnMap.get("status");
				if (HttpStatus.OK.equals(status)) {

					if (!"".equals(returnMap.get("respMsg")) && null != returnMap.get("respMsg")) {
						sysViewDetailDao.updateSysViewCurr(iid, (String) returnMap.get("respMsg"));
						JSONObject returnInfo = JSONObject.fromObject(returnMap.get("respMsg"));
						if (Constants.SYNC_SUCCESS_TURE.equals(returnInfo.getString("success"))) {

							sysViewDao.updateSysViewCurr(iid, Constants.OPTRESULT_SUCESS);
							logger.info("数据同步成功，更新状态:" + Constants.OPTRESULT_SUCESS);
						} else if (Constants.SYNC_SUCCESS_FALSE.equals(returnInfo.getString("success"))) {

							sysViewDao.updateSysViewCurr(iid, Constants.OPTRESULT_FALSE);
							logger.info("数据同步失败，更新状态:" + Constants.OPTRESULT_FALSE);
						}
					}
				} else {
					sysViewDao.updateSysViewCurr(iid, Constants.OPTRESULT_NET_BARRIER);
					logger.info("网络不通，更新状态:" + Constants.OPTRESULT_NET_BARRIER);
				}
			}

		} catch (Exception e) {
			logger.info("错误记录IID为:" + syncMap.get("iid") + " e.getMessage():" + e.getMessage());
			e.printStackTrace();
		}

	}

}
