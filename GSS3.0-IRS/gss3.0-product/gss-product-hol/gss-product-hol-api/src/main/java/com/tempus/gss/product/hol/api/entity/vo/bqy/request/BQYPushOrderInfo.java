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

	@JSONField(name="Ordernumber")
	private Long ordernumber;
	
	@JSONField(name="OrderId")
	private Long orderId;
	
	@JSONField(name="OrderStatus")
	private Integer orderStatus;

	public Long getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public TcOrderStatus getOrderStatus() {
		return TcOrderStatus.bqyKey(String.valueOf(this.orderStatus));
		//return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
