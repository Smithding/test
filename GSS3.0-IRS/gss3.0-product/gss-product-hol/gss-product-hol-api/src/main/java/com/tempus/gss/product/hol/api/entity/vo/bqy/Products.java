package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 产品售卖规则
 */
public class Products implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JSONField(name="SaleType")
	private String saleType;		//售卖类型
	
	@JSONField(name="BreakFastCount")
	private String breakFastCount;	//早餐份数
	
	@JSONField(name="CancelRule")
	private String cancelRule;		//取消规则
	
	@JSONField(name="Price")
	private String price;			//价格
	
	@JSONField(name="SourceID")
	private String sourceID;		//来源渠道ID
	
	@JSONField(name="productID")
	private String productID;		//产品ID
	
	@JSONField(name="BalanceStock")
	private String balanceStock;	//
	
	@JSONField(name="OrderType")
	private String orderType;		//订单类型

}
