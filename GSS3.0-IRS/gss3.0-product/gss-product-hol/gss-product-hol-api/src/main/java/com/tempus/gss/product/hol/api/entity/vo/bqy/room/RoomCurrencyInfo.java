package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class RoomCurrencyInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="Currency")
	private String currency;
	
	@JSONField(name="CurrencyName")
    private String currencyName;
	
	@JSONField(name="Exchange")
    private String exchange;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
}	
