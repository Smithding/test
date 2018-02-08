package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获取支持的信用卡信息入参
 * @author kai.yang
 *
 */
public class CardSupportReq implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 类型 1：代表国内酒店
	 */
	@JSONField(name = "OrderType")
	private Integer orderType;

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	
	
	
	
}
