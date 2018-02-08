package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;

public class BookTicketsResponse  implements Serializable {
	private String msgCode;

	private String msgInfo;

	private String orderNo;

	private String outOrderNo;
	
	private String Message;
	
	

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

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

}
