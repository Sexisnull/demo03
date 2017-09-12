package com.gsww.uids.gateway.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gsww.uids.gateway.dao.application.ApplicationDao;
import com.gsww.uids.gateway.dao.sysview.SysViewDao;
import com.gsww.uids.gateway.httpClient.MyHttpClient;
import com.gsww.uids.gateway.util.SpringContextHolder;

/**
 * Description：实时同步列表 JOB
 * @author zhl
 * @version v2.0
 * */
public class ImmediateSyncJob implements Callable{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final int TRANS_HTTP = 0;
	private static final int TRANS_WEBSERVICE = 1;
	
	//同步列表任务队列
	private ArrayBlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<Runnable>(20);
	
	private SysViewDao sysViewDao = SpringContextHolder.getBean("sysViewDao");
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		this.bizDeal();
		return null;
	}
	@SuppressWarnings("static-access")
	public void bizDeal(){
		try {
			logger.info("定时同步列表程序启动");
			
			List<Map<String, Object>> syncMapList = null; //实时同步列表
			ThreadPoolExecutor threadPool = new ThreadPoolExecutor(20, 30, 3, TimeUnit.SECONDS, taskQueue,new ThreadPoolExecutor.CallerRunsPolicy());
			
			while(true){
				//检查实时同步列表队列是否为空，若为空则查询并执行任务
				if(taskQueue.isEmpty()){
					syncMapList = sysViewDao.selectSysView();
					if(syncMapList!=null && !syncMapList.isEmpty()) {
						logger.info("当前共读取实时同步列表:" + syncMapList.size() + "条记录！");
						for (Map<String, Object> syncMap : syncMapList) {
							threadPool.execute(new ImmediateSyncThread(syncMap));
						}
					}
				}
				
				Thread.currentThread().sleep(2000);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			bizDeal();
		}
	}
}
