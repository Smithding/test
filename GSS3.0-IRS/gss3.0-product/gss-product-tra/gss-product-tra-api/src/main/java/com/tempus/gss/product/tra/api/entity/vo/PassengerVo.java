package com.tempus.gss.product.tra.api.entity.vo;

import java.io.Serializable;

public class PassengerVo  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String passengerId;
	
	private String passengerType;//乘客类型 1成人 2儿童（必填）

	private String passengerName;//乘客姓名（必填）

	private String cardType;//证件类型 1：身份证，2：护照，3：台胞证，4：港澳通行证（必填）

	private String cardNo;//证件号（必填）

	private String seatClass;//坐席类别（必填）

	private String price;//票面价（必填）
	
	private String seatNo;
	
	private String pTicketNo;
	
	private int serviceCharge;//服务费
	
	private int insuranceCharge;//服务费
	
	private String refundPrice;//退款金额

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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

	public int getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(int serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public int getInsuranceCharge() {
		return insuranceCharge;
	}

	public void setInsuranceCharge(int insuranceCharge) {
		this.insuranceCharge = insuranceCharge;
	}

	public String getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(String refundPrice) {
		this.refundPrice = refundPrice;
	}

}
