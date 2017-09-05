package com.gsww.uids.common;

import org.junit.Assert;
import org.junit.Test;

import com.gsww.common.util.HttpUtil;
import com.gsww.uids.base.BaseTest;

public class HttpUtilsTest extends BaseTest {

	@Test
	public void getIpAddressTest() {
		
		this.request.setRequestURI("/gsjis/login.uids");
		String ip = HttpUtil.getIpAddress(this.request);
		Assert.assertTrue(ip.equalsIgnoreCase("127.0.0.1"));
	}
}