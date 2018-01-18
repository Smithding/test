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
import com.tempus.gss.product.ift.api.entity.Leg;
import com.tempus.gss.serializer.LongSerializer;

/**
 * 创建改签单请求.
 */
public class ChangePriceRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeNo;
	@JsonSerialize(using = LongSerializer.class)
	private Long supplierNo;
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	//销售  -改签确认的时候用到
	private List<ChangePriceVo>  salePriceList;
	//adt 审核的时候用到
	private List<ChangePriceVo>  saleAdtPriceList;
	//chd
	private List<ChangePriceVo>  saleChdPriceList;
	//inf
	private List<ChangePriceVo>  saleInfPriceList;
	//采购
	private List<ChangePriceVo>  buyPriceList;

	/**
	 * 航班信息
	 */
	private List<Leg> legList;
	
	private String[] ticketNo;
	
	//账户编号
	@JsonSerialize(using = LongSerializer.class)
	private Long accountNo;
	//交易流水号
	@JsonSerialize(using = LongSerializer.class)
	private String dealNo;
	
	//出票类型
	private String ticketType;
	
	private Integer ticketChangeType;
    //采购币种
	private String currency;
	//销售币种
	private String saleCurrency;
    //采购汇率
	private BigDecimal exchangeRate;
	
	public Long getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	
	public List<ChangePriceVo> getSaleAdtPriceList() {
	
		return saleAdtPriceList;
	}
	
	public void setSaleAdtPriceList(List<ChangePriceVo> saleAdtPriceList) {
	
		this.saleAdtPriceList = saleAdtPriceList;
	}
	
	public List<ChangePriceVo> getSaleChdPriceList() {
	
		return saleChdPriceList;
	}
	
	public void setSaleChdPriceList(List<ChangePriceVo> saleChdPriceList) {
	
		this.saleChdPriceList = saleChdPriceList;
	}
	
	public List<ChangePriceVo> getSaleInfPriceList() {
	
		return saleInfPriceList;
	}
	
	public void setSaleInfPriceList(List<ChangePriceVo> saleInfPriceList) {
	
		this.saleInfPriceList = saleInfPriceList;
	}
	public List<ChangePriceVo> getBuyPriceList() {
	
		return buyPriceList;
	}
	public void setBuyPriceList(List<ChangePriceVo> buyPriceList) {
	
		this.buyPriceList = buyPriceList;
	}
	public Long getSaleChangeNo() {
	
		return saleChangeNo;
	}
	public void setSaleChangeNo(Long saleChangeNo) {
	
		this.saleChangeNo = saleChangeNo;
	}

	public List<ChangePriceVo> getSalePriceList() {
	
		return salePriceList;
	}

	public void setSalePriceList(List<ChangePriceVo> salePriceList) {
	
		this.salePriceList = salePriceList;
	}
	
	public Long getSupplierNo() {
	
		return supplierNo;
	}

	public void setSupplierNo(Long supplierNo) {
	
		this.supplierNo = supplierNo;
	}

	public Long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}

	public String getDealNo() {
		return dealNo;
	}

	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	public String getTicketType() {
		return ticketType;
	}

	public String[] getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String[] ticketNo) {
		this.ticketNo = ticketNo;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public Integer getTicketChangeType() {
		return ticketChangeType;
	}

	public void setTicketChangeType(Integer ticketChangeType) {
		this.ticketChangeType = ticketChangeType;
	}

	public List<Leg> getLegList() {
		return legList;
	}

	public void setLegList(List<Leg> legList) {
		this.legList = legList;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getSaleCurrency() {
		return saleCurrency;
	}

	public void setSaleCurrency(String saleCurrency) {
		this.saleCurrency = saleCurrency;
	}
}
