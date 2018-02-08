package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;
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
	private String StartTime;
	/**
	 * 结束时间
	 */
	private String EndTime;
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	
	
	
	
}
