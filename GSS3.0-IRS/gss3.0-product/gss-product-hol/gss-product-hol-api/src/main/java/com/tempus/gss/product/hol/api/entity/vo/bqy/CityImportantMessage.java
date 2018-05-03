package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 城市重要信息
 */
public class CityImportantMessage implements Serializable {
	
	@JSONField(name="StartDate")
	private String startDate;
	
	@JSONField(name="EndDate")
	private String endDate;
	
	@JSONField(name="MessageContent")
	private String messageContent;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
}
