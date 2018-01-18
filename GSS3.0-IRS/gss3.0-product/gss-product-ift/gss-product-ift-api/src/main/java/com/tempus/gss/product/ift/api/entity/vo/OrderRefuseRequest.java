/*===========================================
* Copyright (C) 2016 Tempus
* All rights reserved
*
*文 件 名：OrderRefuseRequest.java
*版本信息： 1.0.0
*创建时间： 2016/9/2
*作 者： ning.fu
*
===========================================*/
package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * 拒单请求.
 */
public class OrderRefuseRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 销售单编号.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	/**
	 * 拒单备注.
	 */
	private String remark;

	public Long getSaleOrderNo() {

		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {

		this.saleOrderNo = saleOrderNo;
	}

	public String getRemark() {

		return remark;
	}

	public void setRemark(String remark) {

		this.remark = remark;
	}
}
