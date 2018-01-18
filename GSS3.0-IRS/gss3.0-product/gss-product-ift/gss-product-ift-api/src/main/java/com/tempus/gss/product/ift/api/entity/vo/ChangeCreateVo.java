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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.product.ift.api.entity.Leg;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.List;

/**
 * 创建改签单请求.
 */
public class ChangeCreateVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*销售单编号*/
	@JsonSerialize(using = LongSerializer.class)
	public Long saleOrderNo;
	@JsonSerialize(using = LongSerializer.class)
	private Long buyOrderNo;

	/*需要改签的乘客编号集合*/
	public List<Long> oldPassengerNoList;
	/*需要改签的航班集合*/
	public List<Long> oldLegNoList;
	/*改签后的航班集合*/
	public List<Leg> legList;
	
	//采购商编号
	@JsonSerialize(using = LongSerializer.class)
		private Long supplierTypeNo;
		
	//采购商类型
	@JsonSerialize(using = LongSerializer.class)
	private Long supplierNo;

	/*改签类型*/
	public String changeType;
	
	public BuyOrder buyOrder;
	/**
	 *改签原因
	 */
	public String changeReason;
	/**
	 * 联系人
	 */
	public String contactName;
	
	/**
	 * 联系人手机号
	 */
	public String contactPhone;
	
	//账户编号
	@JsonSerialize(using = LongSerializer.class)
	private Long accountNo;
	//交易流水号
	@JsonSerialize(using = LongSerializer.class)
	private String dealNo;
	
	//出票类型
	private String ticketType;
	
	/** 业务批次号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long businessSignNo;
	
	
	public Long getSaleOrderNo() {

		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {

		this.saleOrderNo = saleOrderNo;
	}

	public List<Long> getOldPassengerNoList() {

		return oldPassengerNoList;
	}

	public void setOldPassengerNoList(List<Long> oldPassengerNoList) {

		this.oldPassengerNoList = oldPassengerNoList;
	}

	public List<Long> getOldLegNoList() {

		return oldLegNoList;
	}

	public void setOldLegNoList(List<Long> oldLegNoList) {

		this.oldLegNoList = oldLegNoList;
	}

	public List<Leg> getLegList() {

		return legList;
	}

	public void setLegList(List<Leg> legList) {

		this.legList = legList;
	}

	public String getChangeType() {

		return changeType;
	}

	public void setChangeType(String changeType) {

		this.changeType = changeType;
	}

	
	public String getChangeReason() {
	
		return changeReason;
	}

	
	public void setChangeReason(String changeReason) {
	
		this.changeReason = changeReason;
	}

	
	public String getContactName() {
	
		return contactName;
	}

	
	public void setContactName(String contactName) {
	
		this.contactName = contactName;
	}

	
	public String getContactPhone() {
	
		return contactPhone;
	}

	
	public void setContactPhone(String contactPhone) {
	
		this.contactPhone = contactPhone;
	}

	public Long getSupplierTypeNo() {
		return supplierTypeNo;
	}

	public void setSupplierTypeNo(Long supplierTypeNo) {
		this.supplierTypeNo = supplierTypeNo;
	}

	public Long getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(Long supplierNo) {
		this.supplierNo = supplierNo;
	}

	public BuyOrder getBuyOrder() {
		return buyOrder;
	}

	public void setBuyOrder(BuyOrder buyOrder) {
		this.buyOrder = buyOrder;
	}

	public Long getBuyOrderNo() {
		return buyOrderNo;
	}

	public void setBuyOrderNo(Long buyOrderNo) {
		this.buyOrderNo = buyOrderNo;
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

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public Long getBusinessSignNo() {
		return businessSignNo;
	}

	public void setBusinessSignNo(Long businessSignNo) {
		this.businessSignNo = businessSignNo;
	}

	
	
	
}
