package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class CancelLimitInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="PolicyType")
	private String policyType;
	
	@JSONField(name="LastCancelTime")
    private String lastCancelTime;
	
	@JSONField(name="CancelPolicyInfo")
    private String cancelPolicyInfo;

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public String getLastCancelTime() {
		return lastCancelTime;
	}

	public void setLastCancelTime(String lastCancelTime) {
		this.lastCancelTime = lastCancelTime;
	}

	public String getCancelPolicyInfo() {
		return cancelPolicyInfo;
	}

	public void setCancelPolicyInfo(String cancelPolicyInfo) {
		this.cancelPolicyInfo = cancelPolicyInfo;
	}
	
}
