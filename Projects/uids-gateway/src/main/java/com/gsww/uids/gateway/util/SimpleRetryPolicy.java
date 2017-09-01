package com.gsww.uids.gateway.util;

import org.mule.api.retry.RetryPolicy;
import org.mule.retry.PolicyStatus;
public class SimpleRetryPolicy implements RetryPolicy {
	public PolicyStatus applyPolicy(Throwable throwable) {
	try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		throw new RuntimeException(e);
	}
		return PolicyStatus.policyOk();
	}
}