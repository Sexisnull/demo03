
package com.gsww.jup.util;

import java.net.InetAddress;
/**
 * 
 * <p>
 * TODO(描述这个类的作用)
 * </p>
 * @company 中国电信甘肃万维公司 
 *
 * @project jup-util
 *
 * @version V2.0.0
 *
 * @author 
 *
 * @date 2012-6-19 下午05:00:49	
 *
 * @class com.gsww.jup.util.UUIDGenerator
 *
 */

public abstract class UUIDGenerator {
	
	private static final int IP;
	static {
		int ipadd;
		try {
			ipadd = BytesHelper.toInt( InetAddress.getLocalHost().getAddress() );
		}
		catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}
	private static short counter = (short) 0;
	private static final int JVM = (int) ( System.currentTimeMillis() >>> 8 );
	
	public UUIDGenerator() {
	}
	
	/**
	 * Unique across JVMs on this machine (unless they load this class
	 * in the same quater second - very unlikely)
	 */
	protected int getJVM() {
		return JVM;
	}
	
	/**
	 * Unique in a millisecond for this JVM instance (unless there
	 * are > Short.MAX_VALUE instances created in a millisecond)
	 */
	protected short getCount() {
		synchronized(UUIDGenerator.class) {
			if (counter<0) counter=0;
			return counter++;
		}
	}
	
	/**
	 * Unique in a local network
	 */
	protected int getIP() {
		return IP;
	}
	
	/**
	 * Unique down to millisecond
	 */
	protected short getHiTime() {
		return (short) ( System.currentTimeMillis() >>> 32 );
	}
	protected int getLoTime() {
		return (int) System.currentTimeMillis();
	}
	
	
}





