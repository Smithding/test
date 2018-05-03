package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 政策信息
 */
public class Policy implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JSONField(name="PloicyName")
	private String ploicyName;		//政策名
	
	@JSONField(name="PolicyText")
	private String policyText;		//政策信息

	public String getPloicyName() {
		return ploicyName;
	}

	public void setPloicyName(String ploicyName) {
		this.ploicyName = ploicyName;
	}

	public String getPolicyText() {
		return policyText;
	}

	public void setPolicyText(String policyText) {
		this.policyText = policyText;
	}
}
