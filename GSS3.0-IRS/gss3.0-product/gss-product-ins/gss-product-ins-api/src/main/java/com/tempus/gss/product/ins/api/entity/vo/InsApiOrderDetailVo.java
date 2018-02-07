/**
 * SaleOrderVo.java
 * com.tempus.gss.product.ins.api.entity.vo
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2016年10月19日 		shuo.cheng
 *
 * Copyright (c) 2016, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ins.api.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.product.ins.api.entity.Insurance;
import com.tempus.gss.product.ins.api.entity.SaleOrderDetail;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ClassName:InsApiOrderDetailVo
 *
 * @author   Fengjie.luo
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年02月20日		上午10:40:37
 *
 * @see 	 
 *  
 */
public class InsApiOrderDetailVo extends InsApiBase implements Serializable {
	/**
	 * 订单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	public Long getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}
}

