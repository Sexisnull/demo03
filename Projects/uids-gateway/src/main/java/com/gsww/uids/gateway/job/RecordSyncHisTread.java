package com.gsww.uids.gateway.job;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gsww.uids.gateway.dao.sysview.SysViewDao;
import com.gsww.uids.gateway.util.Constants;
import com.gsww.uids.gateway.util.SpringContextHolder;

public class RecordSyncHisTread extends Thread {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static SysViewDao sysViewDao;
	private Map<String, Object> currMap;

	static {
		sysViewDao = SpringContextHolder.getBean("sysViewDao");
	}

	public RecordSyncHisTread(Map<String, Object> currMap) {
		this.currMap = currMap;
	}

	public void run() {
		logger.info("本次保存历史数据开始,本条记录transcationId：" + currMap.get("transcation_id"));
		System.out.println("本次保存历史数据开始,本条记录transcationId：" + currMap.get("transcation_id"));
		int iid = Integer.parseInt(currMap.get("iid").toString()); // 本条同步记录ID
		String transcationId = currMap.get("transcation_id").toString(); // 本条同步记录ID

		sysViewDao.insertSysViewHis(iid);
		logger.info("本条记录插入到历史同步数据表，transcation_id=" + transcationId);

		sysViewDao.deleteSysViewCurr(iid);
		logger.info("当前同步数据表删除本条记录，transcation_id=" + transcationId);

		logger.info("本次保存历史数据成功！");
	}
}
