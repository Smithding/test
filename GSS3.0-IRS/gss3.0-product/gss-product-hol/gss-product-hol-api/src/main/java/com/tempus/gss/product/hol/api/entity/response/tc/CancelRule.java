package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;


import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.product.hol.api.util.DateUtil;
/**
 * TC取消规则
 * @author kai.yang
 *
 */
public class CancelRule implements Serializable{
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
	 * 罚金百分比（默认-1）
	 */
	@JSONField(name = "ForfeitPercent")
	private Integer forfeitPercent;
	/**
	 * 外币罚金
	 */
	@JSONField(name = "OtherCurrencyForfeitAmount")
	private Integer otherCurrencyForfeitAmount;
	
	public String getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(String cancelTime) {
		cancelTime = DateUtil.stringToStrDetailDate(cancelTime);
		this.cancelTime = cancelTime;
	}
	public Integer getForfeitAmount() {
		return forfeitAmount;
	}
	public void setForfeitAmount(Integer forfeitAmount) {
		this.forfeitAmount = forfeitAmount;
	}
	public Integer getForfeitPercent() {
		return forfeitPercent;
	}
	public void setForfeitPercent(Integer forfeitPercent) {
		this.forfeitPercent = forfeitPercent;
	}
	public Integer getOtherCurrencyForfeitAmount() {
		return otherCurrencyForfeitAmount;
	}
	public void setOtherCurrencyForfeitAmount(Integer otherCurrencyForfeitAmount) {
		this.otherCurrencyForfeitAmount = otherCurrencyForfeitAmount;
	}
	
	
	
}
