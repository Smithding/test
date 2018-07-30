/*===========================================
* Copyright (C) 2016 Tempus
* All rights reserved
*
*文 件 名：ChangeCreateVo.java
*版本信息： 1.0.0
*创建时间： 2016/9/10
*作 者： ning.fu
*
===========================================*/
package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * 创建改签单请求.
 */
public class ChangePriceVo implements Serializable{

	private static final long serialVersionUID = 1L;
	/*销售单编号*/
	@JsonSerialize(using = LongSerializer.class)
	public Long saleOrderNo;
	@JsonSerialize(using = LongSerializer.class)
	public Long saleChangeNo;
	@JsonSerialize(using = LongSerializer.class)
	public Long passengerNo;
	@JsonSerialize(using = LongSerializer.class)
	private Long changePriceNo;
	
	/* 乘客类型*/
	private String passengerType;
	/*订单类型*/
	/*业务类型 1废 2退 3改(同SaleChange的 orderChangeType 属性)*/
	private String orderType;
	/*改签差价*/
	private BigDecimal salePrice;
	/*改签税*/
	private BigDecimal saleTax;
	/*手续费*/
	private BigDecimal saleBrokerage;
	/*其他手续费*/
	private BigDecimal saleRest;
	/*改签结算价*/
	private BigDecimal countPrice;
	
	/*改签差价*/
	private BigDecimal buyPrice;
	/*改签税*/
	private BigDecimal buyTax;
	/*手续费*/
	private BigDecimal buyBrokerage;
	/*其他手续费*/
	private BigDecimal buyRest;
	/*改签结算价*/
	private BigDecimal buyCountPrice;
	/*航程编号*/
	private List<Long> legNoList;
	/*票号  可废弃*/
	private List<String> ticketNoList;
	/*明细编号*/
	private List<Long> saleOrderDetailNoList;

	private BigDecimal buyAgencyFee;
	private BigDecimal buyRebate;
	private BigDecimal saleAgencyFee;
	private BigDecimal saleRebate;
	/*票号*/
	private String ticketNo;
	//采购币种
	private String buyCurrency;
	//采购汇率
	private BigDecimal buyExchangeRate;
	//原单销售结算价和销售改签差价之和
	private BigDecimal allSalePrice;
	//原单采购结算价和采购改签差价之和
	private BigDecimal allBuyPrice;
	/**毛利*/
	private BigDecimal profit;

	public BigDecimal getAllSalePrice() {
		return allSalePrice;
	}

	public void setAllSalePrice(BigDecimal allSalePrice) {
		this.allSalePrice = allSalePrice;
	}

	public BigDecimal getAllBuyPrice() {
		return allBuyPrice;
	}

	public void setAllBuyPrice(BigDecimal allBuyPrice) {
		this.allBuyPrice = allBuyPrice;
	}

	public String getBuyCurrency() {
		return buyCurrency;
	}

	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	public BigDecimal getBuyExchangeRate() {
		return buyExchangeRate;
	}

	public void setBuyExchangeRate(BigDecimal buyExchangeRate) {
		this.buyExchangeRate = buyExchangeRate;
	}

	public Long getSaleOrderNo() {
	
		return saleOrderNo;
	}
	
	public void setSaleOrderNo(Long saleOrderNo) {
	
		this.saleOrderNo = saleOrderNo;
	}
	
	public Long getSaleChangeNo() {
	
		return saleChangeNo;
	}
	
	public void setSaleChangeNo(Long saleChangeNo) {
	
		this.saleChangeNo = saleChangeNo;
	}
	
	public Long getPassengerNo() {
	
		return passengerNo;
	}
	
	public void setPassengerNo(Long passengerNo) {
	
		this.passengerNo = passengerNo;
	}
	
	public BigDecimal getSalePrice() {
	
		return salePrice;
	}
	
	public void setSalePrice(BigDecimal salePrice) {
	
		this.salePrice = salePrice;
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
	
	public BigDecimal getSaleRest() {
	
		return saleRest;
	}
	
	public void setSaleRest(BigDecimal saleRest) {
	
		this.saleRest = saleRest;
	}
	
	public BigDecimal getCountPrice() {
	
		return countPrice;
	}
	
	public void setCountPrice(BigDecimal countPrice) {
	
		this.countPrice = countPrice;
	}

	public String getPassengerType() {
	
		return passengerType;
	}

	public void setPassengerType(String passengerType) {
	
		this.passengerType = passengerType;
	}
	
	public String getOrderType() {
	
		return orderType;
	}

	public void setOrderType(String orderType) {
	
		this.orderType = orderType;
	}

	public List<Long> getLegNoList() {
	
		return legNoList;
	}

	public void setLegNoList(List<Long> legNoList) {
	
		this.legNoList = legNoList;
	}

	public List<String> getTicketNoList() {
	
		return ticketNoList;
	}
	
	public void setTicketNoList(List<String> ticketNoList) {
	
		this.ticketNoList = ticketNoList;
	}

	public List<Long> getSaleOrderDetailNoList() {
	
		return saleOrderDetailNoList;
	}

	public void setSaleOrderDetailNoList(List<Long> saleOrderDetailNoList) {
	
		this.saleOrderDetailNoList = saleOrderDetailNoList;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
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

	public BigDecimal getBuyRest() {
		return buyRest;
	}

	public void setBuyRest(BigDecimal buyRest) {
		this.buyRest = buyRest;
	}

	public BigDecimal getBuyCountPrice() {
		return buyCountPrice;
	}

	public void setBuyCountPrice(BigDecimal buyCountPrice) {
		this.buyCountPrice = buyCountPrice;
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

	public Long getChangePriceNo() {
		return changePriceNo;
	}

	public void setChangePriceNo(Long changePriceNo) {
		this.changePriceNo = changePriceNo;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
}
