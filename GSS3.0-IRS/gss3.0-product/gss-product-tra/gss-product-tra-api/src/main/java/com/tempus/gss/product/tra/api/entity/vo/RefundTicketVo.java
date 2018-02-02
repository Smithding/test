package com.tempus.gss.product.tra.api.entity.vo;

import java.io.Serializable;
import java.util.List;

public class RefundTicketVo  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String msgCode;

	private String msgInfo;

	private String orderNo;

	private String outOrderNo;

	private String refundTime;

	private String refundPrice;

	private String refundType;

	private List<PassengerVo> passengers ;

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsgInfo() {
		return msgInfo;
	}

	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOutOrderNo() {
		return outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public String getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(String refundPrice) {
		this.refundPrice = refundPrice;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public List<PassengerVo> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerVo> passengers) {
		this.passengers = passengers;
	}

}
