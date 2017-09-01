package com.gsww.uids.gateway.util;

import org.mule.api.retry.RetryPolicy;
import org.mule.retry.policies.AbstractPolicyTemplate;;

public class SimpleRetryPolicyTemplate extends AbstractPolicyTemplate {
	public RetryPolicy createRetryInstance() {
		return new SimpleRetryPolicy();
	}
}