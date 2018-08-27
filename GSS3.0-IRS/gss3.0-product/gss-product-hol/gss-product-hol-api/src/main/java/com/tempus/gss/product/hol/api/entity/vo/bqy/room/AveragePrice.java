package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

public class AveragePrice implements Serializable {

	private static final long serialVersionUID = 1L;

	@JSONField(name="Amount")
	private String amount;

	@JSONField(name="CNYAmount")
    private String CNYAmount;

	@JSONField(name = "Averageprice")
	private BigDecimal averageprice;	//挂牌价

	@JSONField(name = "SettleFee")
	private BigDecimal settleFee;		//结算价

	@JSONField(name = "Currency")
	private String currency;			//币种

	public BigDecimal getSettleFee() {
		return settleFee;
	}

	public void setSettleFee(BigDecimal settleFee) {
		this.settleFee = settleFee;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCNYAmount() {
		return CNYAmount;
	}

	public void setCNYAmount(String CNYAmount) {
		this.CNYAmount = CNYAmount;
	}

	public BigDecimal getAverageprice() {
		return averageprice;
	}

	public void setAverageprice(BigDecimal averageprice) {
		this.averageprice = averageprice;
	}
}
