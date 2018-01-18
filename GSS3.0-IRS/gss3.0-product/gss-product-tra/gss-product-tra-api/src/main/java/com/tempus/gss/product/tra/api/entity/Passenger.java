package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;

public class Passenger  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String passengerId;
	
	private String passengerType;//乘客类型 1成人 2儿童（必填）

	private String passengerName;//乘客姓名（必填）

	private String idType;//证件类型 1：身份证，2：护照，3：台胞证，4：港澳通行证（必填）

	private String idCard;//证件号（必填）

	private String birthday;//生日（必填）

	private int insureCount;//是否购买保险 1购买 0不购买（必填）

	private int insurePrice;//保险价格（insureCount为0时insurePrice为0）

	private String insurNo;//保险ID（insureCount为0时为""）

	private String sex;// 性别 0女 1男（必填）

	private String seatClass;//坐席类别（必填）

	private String ticketPrice;//票面价（必填）
	
	private String seatClassName;
	
	private String seatNo;
	
	private String pTicketNo;
	
	private String ticketStateCode;

	private String ticketState;

	private String insureUnitPrice;

	private String insureState;

	private String insureBillNo;
	
	private int serviceCharge;//服务费

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}

	public String getPassengerType() {
		return this.passengerType;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getPassengerName() {
		return this.passengerName;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdType() {
		return this.idType;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setInsureCount(int insureCount) {
		this.insureCount = insureCount;
	}

	public int getInsureCount() {
		return this.insureCount;
	}

	public void setInsurePrice(int insurePrice) {
		this.insurePrice = insurePrice;
	}

	public int getInsurePrice() {
		return this.insurePrice;
	}

	public void setInsurNo(String insurNo) {
		this.insurNo = insurNo;
	}

	public String getInsurNo() {
		return this.insurNo;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	public String getSeatClass() {
		return this.seatClass;
	}

	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public String getTicketPrice() {
		return this.ticketPrice;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getSeatClassName() {
		return seatClassName;
	}

	public void setSeatClassName(String seatClassName) {
		this.seatClassName = seatClassName;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public String getpTicketNo() {
		return pTicketNo;
	}

	public void setpTicketNo(String pTicketNo) {
		this.pTicketNo = pTicketNo;
	}

	public String getTicketStateCode() {
		return ticketStateCode;
	}

	public void setTicketStateCode(String ticketStateCode) {
		this.ticketStateCode = ticketStateCode;
	}

	public String getTicketState() {
		return ticketState;
	}

	public void setTicketState(String ticketState) {
		this.ticketState = ticketState;
	}

	public String getInsureUnitPrice() {
		return insureUnitPrice;
	}

	public void setInsureUnitPrice(String insureUnitPrice) {
		this.insureUnitPrice = insureUnitPrice;
	}

	public String getInsureState() {
		return insureState;
	}

	public void setInsureState(String insureState) {
		this.insureState = insureState;
	}

	public String getInsureBillNo() {
		return insureBillNo;
	}

	public void setInsureBillNo(String insureBillNo) {
		this.insureBillNo = insureBillNo;
	}

	public int getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(int serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	
}
