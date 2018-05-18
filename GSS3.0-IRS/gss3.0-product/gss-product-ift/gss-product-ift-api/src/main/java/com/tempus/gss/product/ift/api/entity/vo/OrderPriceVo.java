package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderPriceVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 乘客类型 1：ADT:2：CHD，3：INF
	 */
	private String passengerType;

	/**
	 * 订单类型 1.销售单 2.采购单
	 */
	private String orderType;
	
	/**
	 * 采购票价
	 */
	private BigDecimal buyFare;

	/**
	 * 采购税费
	 */
	private BigDecimal buyTax;

	/**
	 * 采购手续费
	 */
	private BigDecimal buyBrokerage;

	/**
	 * 采购代理费
	 */
	private BigDecimal buyAgencyFee;

	/**
	 * 采购后返
	 */
	private BigDecimal buyRebate;

	/**
	 * 采购计奖价
	 */
	private BigDecimal buyAwardPrice;

	/**
	 * 采购价结算价.
	 */
	private BigDecimal buyPrice;

	/**
	 * 销售票价
	 */
	private BigDecimal saleFare;

	/**
	 * 销售税费
	 */
	private BigDecimal saleTax;

	/**
	 * 销售手续费
	 */
	private BigDecimal saleBrokerage;

	/**
	 * 销售代理费
	 */
	private BigDecimal saleAgencyFee;

	/**
	 * 销售后返
	 */
	private BigDecimal saleRebate;

	/**
	 * 销售计奖价
	 */
	private BigDecimal saleAwardPrice;

	/**
	 * 销售价结算价.
	 */
	private BigDecimal salePrice;
	
	/**
	 * 服务费
	 */
	private BigDecimal serviceCharge;
	
	/**
	 * 解挂计算使用销售价结算价.
	 */
	private BigDecimal saleJgPrice;
	/**
	 * 营业部门毛利
	 */
	private BigDecimal deptProfit;
	/**
	 *	毛利
	 */
	private BigDecimal profit;

	
	public String getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}

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

	

	public BigDecimal getBuyBrokerage() {
		return buyBrokerage;
	}

	public void setBuyBrokerage(BigDecimal buyBrokerage) {
		this.buyBrokerage = buyBrokerage;
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

	public BigDecimal getBuyAwardPrice() {
		return buyAwardPrice;
	}

	public void setBuyAwardPrice(BigDecimal buyAwardPrice) {
		this.buyAwardPrice = buyAwardPrice;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public BigDecimal getSaleFare() {
		return saleFare;
	}

	public void setSaleFare(BigDecimal saleFare) {
		this.saleFare = saleFare;
	}

	public BigDecimal getSaleTax() {
		return saleTax;
	}

	public void setSaleTax(BigDecimal saleTax) {
		this.saleTax = saleTax;
	}

	public BigDecimal getSaleBrokerage() {
		return saleBrokerage;
	}

	public void setSaleBrokerage(BigDecimal saleBrokerage) {
		this.saleBrokerage = saleBrokerage;
	}

	public BigDecimal getSaleAgencyFee() {
		return saleAgencyFee;
	}

	public void setSaleAgencyFee(BigDecimal saleAgencyFee) {
		this.saleAgencyFee = saleAgencyFee;
	}

	public BigDecimal getSaleRebate() {
		return saleRebate;
	}

	public void setSaleRebate(BigDecimal saleRebate) {
		this.saleRebate = saleRebate;
	}

	public BigDecimal getSaleAwardPrice() {
		return saleAwardPrice;
	}

	public void setSaleAwardPrice(BigDecimal saleAwardPrice) {
		this.saleAwardPrice = saleAwardPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public String getOrderType() {

		return orderType;
	}

	public void setOrderType(String orderType) {

		this.orderType = orderType;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public BigDecimal getSaleJgPrice() {
		return saleJgPrice;
	}

	public void setSaleJgPrice(BigDecimal saleJgPrice) {
		this.saleJgPrice = saleJgPrice;
	}

	public BigDecimal getDeptProfit() {
		return deptProfit;
	}

	public void setDeptProfit(BigDecimal deptProfit) {
		this.deptProfit = deptProfit;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
}
