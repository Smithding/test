package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;
import java.util.Date;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获取同程某一时间段内发生对应类型变更的酒店&及房型 id 列表入参
 * @author kai.yang
 *
 */
public class IncrHotelList implements Serializable{
	private static final long serialVersionUID= 1L;
	
	/**
	 * 拉取开始时间
	 */
	@JSONField(name = "StartTime")
	private String startTime;
	
	/**
	 * 拉取结束时间
	 */
	@JSONField(name = "EndTime")
	private String endTime;
	
	/**
	 * 请求类型：
	 * 1-信息变更 id 
	 * 2-价格变动 id 
	 * 3-无效增量 id 
	 * 4-有效增量 Id 
	 * 7-库存变动 Id
	 */
	@JSONField(name = "IncrementType")
	private Integer incrementType;
	/**
	 * 上一次调用的标记符号ID
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

	public Integer getIncrementType() {
		return incrementType;
	}

	public void setIncrementType(Integer incrementType) {
		this.incrementType = incrementType;
	}

	public Long getEndIncrementId() {
		return endIncrementId;
	}

	public void setEndIncrementId(Long endIncrementId) {
		this.endIncrementId = endIncrementId;
	}
	
	
}	
