package com.tempus.gss.product.hol.api.entity.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.product.hol.api.entity.request.BaseRequest;

public class GetOrderStatusReq extends BaseRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单编号
	 */
	@JSONField(name = "OrderNo")
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
