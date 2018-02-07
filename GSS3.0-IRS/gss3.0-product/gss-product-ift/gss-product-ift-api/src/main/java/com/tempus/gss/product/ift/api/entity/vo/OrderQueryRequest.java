/*===========================================
* Copyright (C) 2016 Tempus
* All rights reserved
*
*文 件 名：OrderQueryRequest.java
*版本信息： 1.0.0
*创建时间： 2016/9/2
*作 者： ning.fu
*
===========================================*/
package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

/**
 * 订单查询请求.
 */
public class OrderQueryRequest implements Serializable {

	/**
	 * 用户编号.
	 */
	private long customerNo;

	/**
	 * 控润渠道类型，目前可用范围：301（分销商），302（集团客户）,303（散客会员）,306（体内销售）.
	 */
	private Long customerTypeNo;

	/**
	 * pnr.
	 */
	private String pnr;

	/**
	 * 交易单编号.
	 */
	private String tradeNo;

	/**
	 * 销售订单编号.
	 */
	private String saleOrderNo;

	/**
	 * 乘客.
	 */
	private String passenger;

	/**
	 * 票号.
	 */
	private String ticketNo;

	/**
	 * 预定开始时间
	 */
	private String bookStartTime;

	/**
	 * 预定结束时间
	 */
	private String bookEndTime;

	/**
	 * 乘机开始时间
	 */
	private String flyStartTime;

	/**
	 * 乘机结束时间
	 */
	private String flyEndTime;

	/**
	 * 支付状态.
	 * 0:全部
	 */
	private int payStatus;

	/**
	 * 状态.
	 * 0:全部
	 */
	private int status;
}
