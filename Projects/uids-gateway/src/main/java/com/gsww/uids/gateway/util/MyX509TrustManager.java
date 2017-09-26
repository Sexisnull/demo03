package com.gsww.uids.gateway.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import com.sun.net.ssl.TrustManager;

public class MyX509TrustManager implements X509TrustManager, TrustManager {

	@Override
	// 检查客户端证书
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

	}

	@Override
	// 检查服务器端证书
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

	}

	@Override
	// 返回受信任的X509证书数组
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

	

}
