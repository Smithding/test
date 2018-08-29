package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.product.hol.api.entity.response.tc.TcOrderStatus;

/**
 * 
 *BQY酒店订单变化回调
 *
 */
public class BQYPushOrderInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 酒店订单号
	 */
	@JSONField(name="OrderNumber")
	private Long orderNumber;

	/**
	 * 总订单号
	 */
	@JSONField(name="OrderId")
	private Long orderId;
	
	@JSONField(name="ChangeStatus")
	private Integer changeStatus;

	/**
	 * 订单类型 (2.酒店订单)
	 */
	@JSONField(name="OrderType")
	private Integer orderType;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public TcOrderStatus getChangeStatus() {
		return TcOrderStatus.bqyKey(String.valueOf(this.changeStatus));
	}

	public void setChangeStatus(Integer changeStatus) {
		this.changeStatus = changeStatus;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

}
