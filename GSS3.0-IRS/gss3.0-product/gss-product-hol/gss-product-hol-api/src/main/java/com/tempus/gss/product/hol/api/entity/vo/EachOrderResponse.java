package com.tempus.gss.product.hol.api.entity.vo;

import java.io.Serializable;

/**
 * 互采中心酒店订单请求响应实体
 */
public class EachOrderResponse implements Serializable {

	/**
	 * 订单域总订单号
	 */
	private Long transactionId;
	/**
	 * 采购订单号
	 */
	private String orderNo;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
