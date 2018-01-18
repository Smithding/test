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

package com.tempus.gss.product.tra.api.entity.vo;
import com.tempus.gss.product.tra.api.entity.TraSaleOrderExt;

import java.io.Serializable;

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
public class OrderCreateVo implements Serializable {

	/**
	 * serialVersionUID:序列ID
	 *
	 * @since Ver 1.1
	 */

	private static final long serialVersionUID = -2713788416595533729L;

	private TraSaleOrderExt saleOrderExt;

	/**
	 * 订单创建方式. 1:前台创建 2:后台创建
	 */
	private Integer orderCreateWay;

	private String  queryKey;

	public Integer getOrderCreateWay() {

		return orderCreateWay;
	}

	public void setOrderCreateWay(Integer orderCreateWay) {

		this.orderCreateWay = orderCreateWay;
	}

	public TraSaleOrderExt getSaleOrderExt() {
		return saleOrderExt;
	}

	public void setSaleOrderExt(TraSaleOrderExt saleOrderExt) {
		this.saleOrderExt = saleOrderExt;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getQueryKey() {
		return queryKey;
	}

	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}
}

