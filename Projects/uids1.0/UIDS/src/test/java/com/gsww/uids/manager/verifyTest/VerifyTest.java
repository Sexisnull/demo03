package com.gsww.uids.manager.verifyTest;

import org.junit.Assert;
import org.junit.Test;

import com.gsww.common.util.VerifyUtil;
import com.gsww.uids.base.BaseTest;

/**
 * 实名认证测试
 * @author simplife
 *
 */
public class VerifyTest extends BaseTest {

	@Test
	public void test(){
		Assert.assertTrue(VerifyUtil.getInstance().smrz("620502199102085330", "杨伟"));
	}
}
