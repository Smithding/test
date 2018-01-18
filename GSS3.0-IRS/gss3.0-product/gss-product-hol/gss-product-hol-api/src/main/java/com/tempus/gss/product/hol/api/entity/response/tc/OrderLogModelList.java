package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 分销商订单操作日志
 *
 */
public class OrderLogModelList implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JSONField(name = "OrderLogInfo")
	private List<OrderLogModel> orderLogInfo;

	public List<OrderLogModel> getOrderLogInfo() {
		return orderLogInfo;
	}

	public void setOrderLogInfo(List<OrderLogModel> orderLogInfo) {
		this.orderLogInfo = orderLogInfo;
	}
	
}	
