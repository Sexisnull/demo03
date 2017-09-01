package com.gsww.uids.gateway.util;
public class Dbid {
	public static String getID(){
		UUIDHexGenerator uuidHex=new UUIDHexGenerator();
		return uuidHex.generate().toString();
		 
	} 
}
