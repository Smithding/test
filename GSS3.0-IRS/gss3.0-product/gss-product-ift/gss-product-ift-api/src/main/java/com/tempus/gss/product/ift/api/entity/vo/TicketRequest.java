/*===========================================
* Copyright (C) 2016 Tempus
* All rights reserved
*
*文 件 名：BackWriteRequest.java
*版本信息： 1.0.0
*创建时间： 2016/9/2
*作 者： ning.fu
*
===========================================*/
package com.tempus.gss.product.ift.api.entity.vo;

import com.tempus.gss.product.ift.api.entity.BuyOrderDetail;

import java.io.Serializable;
import java.util.List;

/**
 * 回帖票号请求.
 */
public class TicketRequest implements Serializable{

	/**
	 * 人加航段的票号列表.
	 */
	private List<BuyOrderDetail> buyOrderDetailList;

	/**
	 * 是否换编出票.
	 */
	private Boolean changePnr;

	/**
	 * 新pnr.
	 */
	private String newPnr;

	/**
	 * 大编码.
	 */
	private String bigPnr;

	/**
	 * 出票office.
	 */
	private String office;

	/**
	 * 出票类型.
	 * BSP BOP B2B
	 */
	private String ticketType;

	public List<BuyOrderDetail> getBuyOrderDetailList() {

		return buyOrderDetailList;
	}

	public void setBuyOrderDetailList(List<BuyOrderDetail> buyOrderDetailList) {

		this.buyOrderDetailList = buyOrderDetailList;
	}

	public Boolean isChangePnr() {

		return changePnr;
	}

	public void setChangePnr(Boolean changePnr) {

		this.changePnr = changePnr;
	}

	public String getNewPnr() {

		return newPnr;
	}

	public void setNewPnr(String newPnr) {

		this.newPnr = newPnr;
	}

	public String getBigPnr() {

		return bigPnr;
	}

	public void setBigPnr(String bigPnr) {

		this.bigPnr = bigPnr;
	}

	public String getOffice() {

		return office;
	}

	public void setOffice(String office) {

		this.office = office;
	}

	public String getTicketType() {

		return ticketType;
	}

	public void setTicketType(String ticketType) {

		this.ticketType = ticketType;
	}
}
