package com.tempus.gss.product.hol.api.entity.vo.bqy.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 订单创建结果返回
 *
 */
public class CreateOrderRespone implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="OrderNumber")
	private Long orderNumber;		// 订单号
	
	@JSONField(name="PayPrice")
	private BigDecimal payPrice;	// 支付价格
	
	@JSONField(name="PayStatusStr")
	private String payStatusStr;	// 订单状态

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public BigDecimal getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}

	public String getPayStatusStr() {
		return payStatusStr;
	}

	public void setPayStatusStr(String payStatusStr) {
		this.payStatusStr = payStatusStr;
	}
}
