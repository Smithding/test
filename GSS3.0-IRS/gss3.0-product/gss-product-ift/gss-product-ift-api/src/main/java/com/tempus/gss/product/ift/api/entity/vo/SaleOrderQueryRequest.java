package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

/**
 * （B2B，B2G，B2C，呼叫中心）订单查询条件.
 */
public class SaleOrderQueryRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 预订日始.
	 */
	private String bookStartDate;
	/**
	 * 预订日止.
	 */
	private String bookEndDate;

	/**
	 * 客户编码.
	 */
	private String customerNo;

	/**
	 * 客户类型.
	 * （B2B，B2G，B2C）
	 */
	private String customerType;

	/**
	 * 支付日始.
	 */
	private String payStartDate;

	/**
	 * 支付日止.
	 */
	private String payEndDate;

	/**
	 * 乘机开始时间
	 */
	private String flyStartTime;

	/**
	 * 乘机结束时间
	 */
	private String flyEndTime;

	/**
	 * PNR
	 */
	private String pnr;

	/**
	 * 订单状态.
	 */
	private String orderStatus;

	/**
	 * 乘机人.
	 */
	private String passengerName;

	/**
	 * 交易单号.
	 */
	private String tradeNo;

	/**
	 * 航空公司.
	 */
	private String airline;

	/**
	 * 订单来源.
	 * （B2C,B2G,B2B白屏，B2B手工，呼叫中心白屏，呼叫中心黑屏）
	 */
	private String orderSource;

	/**
	 * 票号.
	 */
	private String ticketNo;

	/**
	 * 出发城市-机场三字码.
	 */
	private String depCity;

	/**
	 * 到达城市-机场三字码.
	 */
	private String arrCity;

	/**
	 * 航班号.
	 */
	private String flightNo;

	/**
	 * 操作员.
	 */
	private String operator;

	public String getBookStartDate() {

		return bookStartDate;
	}

	public void setBookStartDate(String bookStartDate) {

		this.bookStartDate = bookStartDate;
	}

	public String getBookEndDate() {

		return bookEndDate;
	}

	public void setBookEndDate(String bookEndDate) {

		this.bookEndDate = bookEndDate;
	}

	public String getCustomerNo() {

		return customerNo;
	}

	public void setCustomerNo(String customerNo) {

		this.customerNo = customerNo;
	}

	public String getCustomerType() {

		return customerType;
	}

	public void setCustomerType(String customerType) {

		this.customerType = customerType;
	}

	public String getPayStartDate() {

		return payStartDate;
	}

	public void setPayStartDate(String payStartDate) {

		this.payStartDate = payStartDate;
	}

	public String getPayEndDate() {

		return payEndDate;
	}

	public void setPayEndDate(String payEndDate) {

		this.payEndDate = payEndDate;
	}

	public String getFlyStartTime() {

		return flyStartTime;
	}

	public void setFlyStartTime(String flyStartTime) {

		this.flyStartTime = flyStartTime;
	}

	public String getFlyEndTime() {

		return flyEndTime;
	}

	public void setFlyEndTime(String flyEndTime) {

		this.flyEndTime = flyEndTime;
	}

	public String getPnr() {

		return pnr;
	}

	public void setPnr(String pnr) {

		this.pnr = pnr;
	}

	public String getOrderStatus() {

		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {

		this.orderStatus = orderStatus;
	}

	public String getPassengerName() {

		return passengerName;
	}

	public void setPassengerName(String passengerName) {

		this.passengerName = passengerName;
	}

	public String getTradeNo() {

		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {

		this.tradeNo = tradeNo;
	}

	public String getAirline() {

		return airline;
	}

	public void setAirline(String airline) {

		this.airline = airline;
	}

	public String getOrderSource() {

		return orderSource;
	}

	public void setOrderSource(String orderSource) {

		this.orderSource = orderSource;
	}

	public String getTicketNo() {

		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {

		this.ticketNo = ticketNo;
	}

	public String getDepCity() {

		return depCity;
	}

	public void setDepCity(String depCity) {

		this.depCity = depCity;
	}

	public String getArrCity() {

		return arrCity;
	}

	public void setArrCity(String arrCity) {

		this.arrCity = arrCity;
	}

	public String getFlightNo() {

		return flightNo;
	}

	public void setFlightNo(String flightNo) {

		this.flightNo = flightNo;
	}

	public String getOperator() {

		return operator;
	}

	public void setOperator(String operator) {

		this.operator = operator;
	}
}
