package com.tempus.gss.product.ift.api.entity.vo;

import com.tempus.gss.product.ift.api.entity.Policy;
import com.tempus.gss.product.common.entity.RequestWithActor;

/**
 * 创建政策的请求参数.
 */
public class PolicyCreateRequest extends RequestWithActor<Policy> {

	/**
	 * 政策
	 */
	private Policy policy;

	public Policy getPolicy() {

		return policy;
	}

	public void setPolicy(Policy policy) {

		this.policy = policy;
	}
}
