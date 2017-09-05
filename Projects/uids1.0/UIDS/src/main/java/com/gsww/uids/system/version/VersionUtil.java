package com.gsww.uids.system.version;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一身份认证版本记录
 * 
 * @author sunbw 
 * @author taolm 修改
 */
public class VersionUtil {
	/**
	 * 所有版本信息
	 */
	private List<VersionInfo> vlist = new ArrayList<>();
	
	/**
	 * 对象
	 */
	private static VersionUtil instance = null;
	
	/**
	 * 私有构造函数
	 */
	private VersionUtil(){
		VersionInfo defaultVersion = new VersionInfo(1, "v1.0", "发布1.0正式版版本", "2017-03-30");
		addVersion(defaultVersion);
	} 
	
	/**
	 * 单例模式
	 */
	public static synchronized VersionUtil getInstance() {
		if ( instance == null ) {
			instance = new VersionUtil();
		}
		
		return instance;
	}

	/**
	 * 获得最新版本
	 * 
	 * @return
	 */
	public VersionInfo getLastVersion(){
		if ( vlist.isEmpty() ) {
			return null;
		} else {
			return vlist.get(vlist.size()-1);
		}
	}
	
	/**
	 * 增加一个版本信息
	 * 
	 * @param info
	 */
	public synchronized void addVersion(VersionInfo info){
		// TODO 应该先排序
		vlist.add(info);
	}
}
