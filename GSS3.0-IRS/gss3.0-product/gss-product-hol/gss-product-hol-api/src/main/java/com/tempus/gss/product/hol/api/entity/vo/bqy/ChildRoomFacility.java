package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class ChildRoomFacility implements Serializable {

	private static final long serialVersionUID = 1L;

	@JSONField(name="FacilityId")
	private int facilityId;			//房型设施服务ID
	
	@JSONField(name="FacilityName")
	private String facilityName;	//房型设施服务Name

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

}
