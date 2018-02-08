/*===========================================
* Copyright (C) 2016 Tempus
* All rights reserved
*
*文 件 名：OrderCreateRequest.java
*版本信息： 1.0.0
*创建时间： 2016/9/2
*作 者： ning.fu
*
===========================================*/
package com.tempus.gss.product.ift.api.entity.vo;

import com.tempus.gss.product.ift.api.entity.Leg;
import com.tempus.gss.product.ift.api.entity.Passenger;
import com.tempus.gss.product.ift.api.entity.SaleOrderExt;

import java.io.Serializable;
import java.util.List;

/**
 * 通过白屏查询创建订单请求对象.
 */
public class OrderCreateRequest implements Serializable {

	/**
	 * 订单扩展信息.
	 */
	private SaleOrderExt saleOrderExt;

	/**
	 * 航程列表.
	 */
	private List<Leg> legList;

	/**
	 * 乘客列表.
	 */
	private List<Passenger> passengerList;

	/**
	 * 白屏查询结果.
	 */
	private FlightQueryRequest flightQueryRequest;
}
