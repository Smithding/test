/**
 * WarnOrderRequest.java
 * com.tempus.gss.product.ift.api.entity.vo
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年9月5日 		shuo.cheng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

/**
 * ClassName:WarnOrderRequest
 * Function: 调整单提醒对外接口请求对象
 *
 * @author shuo.cheng
 * @version
 * @since Ver 1.1
 * @Date 2017年9月5日 上午11:35:56
 *
 * @see
 * 
 */
public class WarnOrderRequest implements Serializable{

	/**
	 * serialVersionUID:序列ID
	 *
	 * @since Ver 1.1
	 */

	private static final long serialVersionUID = 5579644061536002514L;

	/** 工单号 */
	private String orderid;

	/** 0未处理 1已处理 */
	private int status;

	/** 备注 */
	private String remarks;

	public String getOrderid() {

		return orderid;
	}

	public void setOrderid(String orderid) {

		this.orderid = orderid;
	}

	public int getStatus() {

		return status;
	}

	public void setStatus(int status) {

		this.status = status;
	}

	public String getRemarks() {

		return remarks;
	}

	public void setRemarks(String remarks) {

		this.remarks = remarks;
	}

}

