package com.tempus.gss.product.ift.api.entity.iftVo;


import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

public class IftSaleChangeExtVo implements Serializable{

	private static final long serialVersionUID = 1L;

	
	/**
	 * 废退该签单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeNo;

	
	/**
	 * 销售单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	
	/**
	 * 支付状态
	 */
	private Integer[] payStatus;
	/**
	 * 起点机场-出发地
	 */
	private String depAirport;

	/**
	 * 终点机场-目的地
	 */
	private String arrAirport;
	
	/**
	 * 乘客名字
	 */
	private String pgerName;

	/**
	 * 出票类型
	 */
	private String ticketType;

	/**
	 *业务类型 0（1废 2退） 1废 2退 3改
	 */
	private Integer changeType;
	
	/**
	 * 废退方式：1：自愿、2：非自愿  0:全部(包括1 2 )
	 */
	private Integer refundWay;


	/**
	 * 子状态  1.未审核、2.已审核、10 已完成、 11已取消   集合可多个状态进行查询
	 * @return
	 */
	private Integer[] childStatus;

	/**
	 * 改签类型
	 */
	private Integer ticketChangeType;
	
	
	private int curPage;// 当前页

	private int rowNum;// 每页多少行

	public Long getSaleChangeNo() {
		return saleChangeNo;
	}

	public void setSaleChangeNo(Long saleChangeNo) {
		this.saleChangeNo = saleChangeNo;
	}


	public Long getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	

	public Integer[] getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer[] payStatus) {
		this.payStatus = payStatus;
	}

	public String getDepAirport() {
		return depAirport;
	}

	public void setDepAirport(String depAirport) {
		this.depAirport = depAirport;
	}

	public String getArrAirport() {
		return arrAirport;
	}

	public void setArrAirport(String arrAirport) {
		this.arrAirport = arrAirport;
	}

	public String getPgerName() {
		return pgerName;
	}

	public void setPgerName(String pgerName) {
		this.pgerName = pgerName;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public Integer getChangeType() {
		return changeType;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}

	public Integer getRefundWay() {
		return refundWay;
	}

	public void setRefundWay(Integer refundWay) {
		this.refundWay = refundWay;
	}

	public Integer[] getChildStatus() {
		return childStatus;
	}

	public void setChildStatus(Integer[] childStatus) {
		this.childStatus = childStatus;
	}

	public Integer getTicketChangeType() {
		return ticketChangeType;
	}

	public void setTicketChangeType(Integer ticketChangeType) {
		this.ticketChangeType = ticketChangeType;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
	
	
}
