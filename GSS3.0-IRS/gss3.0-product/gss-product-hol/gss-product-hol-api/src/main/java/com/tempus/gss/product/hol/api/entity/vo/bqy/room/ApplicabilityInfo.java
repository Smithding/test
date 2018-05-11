package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class ApplicabilityInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="Applicability")
	private String applicability;
	
	@JSONField(name="OtherDescription")
    private String otherDescription;

	public String getApplicability() {
		return applicability;
	}

	public void setApplicability(String applicability) {
		this.applicability = applicability;
	}

	public String getOtherDescription() {
		return otherDescription;
	}

	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}
}
