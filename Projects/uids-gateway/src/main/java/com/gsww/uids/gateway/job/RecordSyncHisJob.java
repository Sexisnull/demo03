package com.gsww.uids.gateway.job;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gsww.uids.gateway.dao.sysview.SysViewDao;
import com.gsww.uids.gateway.util.SpringContextHolder;

public class RecordSyncHisJob implements Callable{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	//当前同步数据任务队列
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
			logger.info("定时保存历史同步数据程序启动");
			
			List<Map<String, Object>> currMapList = null; //当前同步列表
			ThreadPoolExecutor threadPool = new ThreadPoolExecutor(20, 30, 3, TimeUnit.SECONDS, taskQueue,new ThreadPoolExecutor.CallerRunsPolicy());
			
			while(true){
				//检查实时同步列表队列是否为空，若为空则查询并执行任务
				if(taskQueue.isEmpty()){
					currMapList = sysViewDao.findCurrSysViewByOptResult(1); //获取所有同步成功的数据
					if(currMapList!=null && !currMapList.isEmpty()) {
						logger.info("共读取当前同步数据:" + currMapList.size() + "条记录！");
						for (Map<String, Object> currMap : currMapList) {
							threadPool.execute(new RecordSyncHisTread(currMap));
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
