package com.gsww.uids.gateway.job;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gsww.uids.gateway.dao.sysview.SysViewDao;
import com.gsww.uids.gateway.util.Contants;
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
		logger.info("本次保存历史数据开始,本条记录IID：" + currMap.get("iid"));
		System.out.println("本次保存历史数据开始,本条记录IID：" + currMap.get("iid"));
		int iid = Integer.parseInt(currMap.get("iid").toString()); // 本条同步记录ID

		sysViewDao.insertSysViewHis(iid);
		logger.info("本条记录插入到历史同步数据表，IID=" + iid);

		sysViewDao.deleteSysViewCurr(iid);
		logger.info("当前同步数据表删除本条记录，IID=" + iid);

		logger.info("本次保存历史数据成功！");
	}
}
