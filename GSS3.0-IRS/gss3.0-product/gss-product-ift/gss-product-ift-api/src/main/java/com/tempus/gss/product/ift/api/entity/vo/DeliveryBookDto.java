package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

/**
 * 通用的配送信息预定时实体
 * @author hongqiaoxin
 *
 */
public class DeliveryBookDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 是否需要配送
	 */
	private Boolean delivery;
	
	/**
	 * 配送信息
	 */
	private SaveDeliveryOrderParam sdop;

	public Boolean getDelivery() {
		return delivery;
	}

	public void setDelivery(Boolean delivery) {
		this.delivery = delivery;
	}

	public SaveDeliveryOrderParam getSdop() {
		return sdop;
	}

	public void setSdop(SaveDeliveryOrderParam sdop) {
		this.sdop = sdop;
	}
	
	

}
