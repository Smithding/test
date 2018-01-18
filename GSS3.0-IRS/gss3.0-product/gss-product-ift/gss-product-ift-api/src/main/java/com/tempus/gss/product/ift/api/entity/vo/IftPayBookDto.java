package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

import com.tempus.gss.product.ift.api.entity.vo.OrderCreateVo;

/**
 * 国际机票预定dto
 * @author hongqiaoxin
 *
 */
public class IftPayBookDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**预定的类型  1：白屏   2：PNR*/
	private Integer opBookType;
	
	private OrderCreateVo order;
	
//	private SaveDeliveryOrderParam sdop;
//	
//	/**是否报销  0：不需要  1：需要*/
//	private Integer isDelivery;

	public OrderCreateVo getOrder() {
		return order;
	}

	public void setOrder(OrderCreateVo order) {
		this.order = order;
	}

//	public SaveDeliveryOrderParam getSdop() {
//		return sdop;
//	}
//
//	public void setSdop(SaveDeliveryOrderParam sdop) {
//		this.sdop = sdop;
//	}
//
//	public Integer getIsDelivery() {
//		return isDelivery;
//	}
//
//	public void setIsDelivery(Integer isDelivery) {
//		this.isDelivery = isDelivery;
//	}

	public Integer getOpBookType() {
		return opBookType;
	}

	public void setOpBookType(Integer opBookType) {
		this.opBookType = opBookType;
	}
	
	
}
