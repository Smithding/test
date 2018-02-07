package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;

public class InallsaleRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ticketno;
	
	private String aircode;
	
	private String orderid;
	
	private String supplier;
	
	private String selectType; // 打回类型  //0:销售员    //1:出票员
	
	private String errorinfo;

	public String getTicketno() {
		return ticketno;
	}

	public void setTicketno(String ticketno) {
		this.ticketno = ticketno;
	}

	public String getAircode() {
		return aircode;
	}

	public void setAircode(String aircode) {
		this.aircode = aircode;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getErrorinfo() {
		return errorinfo;
	}

	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}

}
