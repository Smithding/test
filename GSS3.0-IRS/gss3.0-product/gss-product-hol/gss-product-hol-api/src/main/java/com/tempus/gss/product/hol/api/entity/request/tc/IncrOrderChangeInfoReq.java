package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 增量获取同程订单状态信息入参
 * @author kai.yang
 *
 */
public class IncrOrderChangeInfoReq implements Serializable{
	private static final long serialVersionUID= 1L;
	/**
	 * 开始时间
	 */
	@JSONField(name = "StartTime")
	private String startTime;
	/**
	 * 结束时间
	 */
	@JSONField(name = "EndTime")
	private String endTime;
	/**
	 * 最后的更新 id
	 */
	@JSONField(name = "EndIncrementId")
	private Long endIncrementId;
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getEndIncrementId() {
		return endIncrementId;
	}
	public void setEndIncrementId(Long endIncrementId) {
		this.endIncrementId = endIncrementId;
	}
	
	
}
