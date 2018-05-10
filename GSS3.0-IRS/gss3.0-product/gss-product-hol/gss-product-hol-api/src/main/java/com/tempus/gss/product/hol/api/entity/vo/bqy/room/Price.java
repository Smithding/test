package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class Price implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="Amount")
	private String amount;
	
	@JSONField(name="Currency")
    private String currency;
	
	@JSONField(name="CNYAmount")
    private String CNYAmount;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCNYAmount() {
		return CNYAmount;
	}

	public void setCNYAmount(String cNYAmount) {
		CNYAmount = cNYAmount;
	}
	
}
