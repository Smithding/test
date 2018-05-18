package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class CancelRulesStrictInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 取消日期
	 */
	@JSONField(name = "CancelTime")
	private String cancelTime;
	/**
	 * 罚金
	 */
	@JSONField(name = "ForfeitAmount")
	private Integer forfeitAmount;
	/**
	 * 外币罚金
	 */
	@JSONField(name = "OtherCurrencyForfeitAmount")
	private Integer otherCurrencyForfeitAmount;
	/**
	 * 取消类型（0：限时取消， 1：免费取消， 2：不可取消）
	 */
	@JSONField(name = "CancelType")
	private Integer cancelType;
	public String getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}
	public Integer getForfeitAmount() {
		return forfeitAmount;
	}
	public void setForfeitAmount(Integer forfeitAmount) {
		this.forfeitAmount = forfeitAmount;
	}
	public Integer getOtherCurrencyForfeitAmount() {
		return otherCurrencyForfeitAmount;
	}
	public void setOtherCurrencyForfeitAmount(Integer otherCurrencyForfeitAmount) {
		this.otherCurrencyForfeitAmount = otherCurrencyForfeitAmount;
	}
	public Integer getCancelType() {
		return cancelType;
	}
	public void setCancelType(Integer cancelType) {
		this.cancelType = cancelType;
	}
	
}
