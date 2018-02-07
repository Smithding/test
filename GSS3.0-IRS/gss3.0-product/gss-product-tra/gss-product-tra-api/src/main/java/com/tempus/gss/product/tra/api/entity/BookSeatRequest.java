package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;
import java.util.List;

import com.tempus.gss.product.tra.api.entity.vo.PassengerVo;

public class BookSeatRequest  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String msgCode;

	private String msgInfo;

	private String orderNo;

	private String outOrderNo;

	private String fromStationCode;

	private String fromStation;

	private String toStationCode;

	private String toStation;

	private String departureTime;

	private String arrivalTime;

	private String trainNo;

	private String ticketNo;

	private String orderAmount;

	private String isChangedOrder;		//是否改签单 0：正常订单 1：改签单

	private String originalOrderNo;

	private String changedType;	  		//改签类型 0：未改签； 1：无差价； 2：高改低（原票价高于改签后票价）； 3：低改高（原票价低于改签后票价）

	private String serviceCharge;	

	private String changePriceDiff;

	private List<PassengerVo> passengers;

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsgCode() {
		return this.msgCode;
	}

	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}

	public String getMsgInfo() {
		return this.msgInfo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}

	public String getOutOrderNo() {
		return this.outOrderNo;
	}

	public void setFromStationCode(String fromStationCode) {
		this.fromStationCode = fromStationCode;
	}

	public String getFromStationCode() {
		return this.fromStationCode;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getFromStation() {
		return this.fromStation;
	}

	public void setToStationCode(String toStationCode) {
		this.toStationCode = toStationCode;
	}

	public String getToStationCode() {
		return this.toStationCode;
	}

	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	public String getToStation() {
		return this.toStation;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getDepartureTime() {
		return this.departureTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getArrivalTime() {
		return this.arrivalTime;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getTrainNo() {
		return this.trainNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getTicketNo() {
		return this.ticketNo;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderAmount() {
		return this.orderAmount;
	}

	public void setIsChangedOrder(String isChangedOrder) {
		this.isChangedOrder = isChangedOrder;
	}

	public String getIsChangedOrder() {
		return this.isChangedOrder;
	}

	public void setOriginalOrderNo(String originalOrderNo) {
		this.originalOrderNo = originalOrderNo;
	}

	public String getOriginalOrderNo() {
		return this.originalOrderNo;
	}

	public void setChangedType(String changedType) {
		this.changedType = changedType;
	}

	public String getChangedType() {
		return this.changedType;
	}

	public void setServiceCharge(String serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getServiceCharge() {
		return this.serviceCharge;
	}

	public void setChangePriceDiff(String changePriceDiff) {
		this.changePriceDiff = changePriceDiff;
	}

	public String getChangePriceDiff() {
		return this.changePriceDiff;
	}

	public List<PassengerVo> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerVo> passengers) {
		this.passengers = passengers;
	}

}
