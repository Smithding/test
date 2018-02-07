/**
 * OrderCreateVo.java
 * com.tempus.gss.product.ins.api.entity.vo
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2016年10月17日 		shuo.cheng
 *
 * Copyright (c) 2016, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ins.api.entity.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.tempus.gss.product.ins.api.entity.SaleOrderExt;

/**
 * ClassName:OrderCreateVo
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   shuo.cheng
 * @version  
 * @since    Ver 1.1
 * @Date	 2016年10月17日		下午5:05:27
 *
 * @see 	 
 *  
 */
@Alias("insOrderCreateVo")
public class OrderCreateVo implements Serializable {

	/**
	 * serialVersionUID:序列ID
	 *
	 * @since Ver 1.1
	 */

	private static final long serialVersionUID = -2713788416595533729L;

	public SaleOrderExt saleOrderExt;

	/**
	 * 订单创建方式. 1:前台创建 2:后台创建
	 */
	public Integer orderCreateWay;

	public Integer getOrderCreateWay() {

		return orderCreateWay;
	}

	public void setOrderCreateWay(Integer orderCreateWay) {

		this.orderCreateWay = orderCreateWay;
	}

	public SaleOrderExt getSaleOrderExt() {

		return saleOrderExt;
	}

	public void setSaleOrderExt(SaleOrderExt saleOrderExt) {

		this.saleOrderExt = saleOrderExt;
	}
}

