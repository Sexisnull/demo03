package com.gsww.jup.util;

import java.io.Serializable;

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
 * @date 2012-6-19 下午05:00:33	
 *
 * @class com.gsww.jup.util.UUIDHexGenerator
 *
 */
public class UUIDHexGenerator extends UUIDGenerator {
	
	private String sep = ""; 
	
	public String formatCode(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace( 8-formatted.length(), 8, formatted );
		return buf.toString();
	}
	
	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace( 8-formatted.length(), 8, formatted );
		return buf.toString();
	}
	
	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace( 4-formatted.length(), 4, formatted );
		return buf.toString();
	}
	
	public Serializable generate() {
		return new StringBuffer(36)
		.append( format( getIP() ) ).append(sep)
		.append( format( getJVM() ) ).append(sep)
		.append( format( getHiTime() ) ).append(sep)
		.append( format( getLoTime() ) ).append(sep)
		.append( format( getCount() ) )
		.toString();
	}

	public static void main(String[] args) {
		UUIDHexGenerator uuid=new UUIDHexGenerator();
		for(int i=0;i<100;i++){
			System.out.println(uuid.generate());
		}
	}

}
