package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BuyOrderDetailVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 采购票面价
	 */
	private BigDecimal buyFare;
	/**
	 * 采购税费
	 */
	private BigDecimal buyTax;
	/**
	 * 采购代理费率
	 */
	private BigDecimal buyAgencyFee;
	/**
	 * 采购后反
	 */
	private BigDecimal buyRebate;
	/**
	 * 采购附加费
	 */
	private BigDecimal buyBrokerage;
	/**
	 * 采购计奖价
	 */
	private BigDecimal buyAwardPrice;
	public BigDecimal getBuyFare() {
		return buyFare;
	}
	public void setBuyFare(BigDecimal buyFare) {
		this.buyFare = buyFare;
	}
	public BigDecimal getBuyTax() {
		return buyTax;
	}
	public void setBuyTax(BigDecimal buyTax) {
		this.buyTax = buyTax;
	}
	public BigDecimal getBuyAgencyFee() {
		return buyAgencyFee;
	}
	public void setBuyAgencyFee(BigDecimal buyAgencyFee) {
		this.buyAgencyFee = buyAgencyFee;
	}
	public BigDecimal getBuyRebate() {
		return buyRebate;
	}
	public void setBuyRebate(BigDecimal buyRebate) {
		this.buyRebate = buyRebate;
	}
	public BigDecimal getBuyBrokerage() {
		return buyBrokerage;
	}
	public void setBuyBrokerage(BigDecimal buyBrokerage) {
		this.buyBrokerage = buyBrokerage;
	}
	public BigDecimal getBuyAwardPrice() {
		return buyAwardPrice;
	}
	public void setBuyAwardPrice(BigDecimal buyAwardPrice) {
		this.buyAwardPrice = buyAwardPrice;
	}
	
	
}
