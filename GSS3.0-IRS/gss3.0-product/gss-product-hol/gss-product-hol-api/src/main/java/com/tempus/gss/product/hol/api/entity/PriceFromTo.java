package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

import com.baomidou.mybatisplus.annotations.TableField;

/**
 *
 * 酒店控润价格
 *
 */
public class PriceFromTo implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 区间开始值 */
	@JSONField(name = "PriceFrom")
	private BigDecimal priceFrom;

	/** 区间结束值 */
	@JSONField(name = "PriceTo")
	private BigDecimal priceTo;

	/** 变动值 */
	@JSONField(name = "Rate")
	private BigDecimal rate;

	public BigDecimal getPriceFrom() {
		return this.priceFrom;
	}

	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}

	public BigDecimal getPriceTo() {
		return this.priceTo;
	}

	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
}
