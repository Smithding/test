package com.tempus.gss.product.ift.api.entity;

import com.tempus.gss.order.entity.BuyOrder;

import java.io.Serializable;

/**
 * 
 */
public class QueryByPnr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SaleOrderExt saleOrderExt;
	
	private BuyOrder buyOrder;

	private BuyOrderExt buyOrderExt;

	public BuyOrder getBuyOrder() {
		return buyOrder;
	}

	public void setBuyOrder(BuyOrder buyOrder) {
		this.buyOrder = buyOrder;
	}

	public SaleOrderExt getSaleOrderExt() {
		return saleOrderExt;
	}

	public void setSaleOrderExt(SaleOrderExt saleOrderExt) {
		this.saleOrderExt = saleOrderExt;
	}

	public BuyOrderExt getBuyOrderExt() {
		return buyOrderExt;
	}

	public void setBuyOrderExt(BuyOrderExt buyOrderExt) {
		this.buyOrderExt = buyOrderExt;
	}
}