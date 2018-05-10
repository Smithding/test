package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class ReserveTimeLimitInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@JSONField(name="LastReserveTime")
	private String lastReserveTime;

	public String getLastReserveTime() {
		return lastReserveTime;
	}

	public void setLastReserveTime(String lastReserveTime) {
		this.lastReserveTime = lastReserveTime;
	}
	
}
