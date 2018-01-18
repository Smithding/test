/*===========================================
* Copyright (C) 2016 Tempus
* All rights reserved
*
*文 件 名：DemandCancelRequest.java
*版本信息： 1.0.0
*创建时间： 2016/9/2
*作 者： ning.fu
*
===========================================*/
package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

/**
 * DemandCancelRequest.java.
 */
public class DemandCancelRequest implements Serializable {

	/**
	 * 需求单编号.
	 */
	private String demandNo;

	public String getDemandNo() {

		return demandNo;
	}

	public void setDemandNo(String demandNo) {

		this.demandNo = demandNo;
	}
}
