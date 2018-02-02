package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.product.ift.api.entity.Passenger;
import com.tempus.gss.serializer.LongSerializer;

public class PassengerListVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = LongSerializer.class)
	private Long supplierNo;
	//出票类型
	private String ticketType;
	//新pnr
	private String pnr;
	//pnr编号
	@JsonSerialize(using = LongSerializer.class)
	private Long pnrNo;
	//大编码
	private String bigPnr;
	
	private List<Passenger> rows;
	//确认出单使用到
	private List<PassengerVo> pVoList;
	//账户编号
	@JsonSerialize(using = LongSerializer.class)
	private Long accountNo;
	//交易流水号
	@JsonSerialize(using = LongSerializer.class)
	private String dealNo;

	private String buyRemarke;

	public List<Passenger> getRows() {
		return rows;
	}
	public void setRows(List<Passenger> rows) {
		this.rows = rows;
	}
	public List<PassengerVo> getpVoList() {
	
		return pVoList;
	}
	public void setpVoList(List<PassengerVo> pVoList) {
	
		this.pVoList = pVoList;
	}
	
	public Long getSupplierNo() {
	
		return supplierNo;
	}
	
	public void setSupplierNo(Long supplierNo) {
	
		this.supplierNo = supplierNo;
	}

	public String getTicketType() {

		return ticketType;
	}

	public void setTicketType(String ticketType) {

		this.ticketType = ticketType;
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
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public String getBigPnr() {
		return bigPnr;
	}
	public void setBigPnr(String bigPnr) {
		this.bigPnr = bigPnr;
	}
	public Long getPnrNo() {
		return pnrNo;
	}
	public void setPnrNo(Long pnrNo) {
		this.pnrNo = pnrNo;
	}

	public String getBuyRemarke() {
		return buyRemarke;
	}

	public void setBuyRemarke(String buyRemarke) {
		this.buyRemarke = buyRemarke;
	}
}
