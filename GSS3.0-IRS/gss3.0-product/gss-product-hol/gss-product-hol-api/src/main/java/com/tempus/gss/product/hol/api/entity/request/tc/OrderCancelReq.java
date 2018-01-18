package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 根据同程订单号向同程发起对应订单的退款申请入参
 * @author kai.yang
 *
 */
public class OrderCancelReq implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 客户订单号
	 */
	@JSONField(name = "OrderId")
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
