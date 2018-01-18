package com.tempus.gss.product.tra.api.entity.vo;

import java.io.Serializable;

public class NotifyOvertimeOrderVo  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String msgCode;

	private String msgInfo;

	private String orderNo;

	private String outOrderNo;

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
	
}
