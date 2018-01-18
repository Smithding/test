package com.tempus.gss.product.hol.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 *
 * 酒店控润价格
 *
 */
public class ProfitPriceVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 区间开始值 */
	private BigDecimal priceFrom;

	/** 区间结束值 */
	private BigDecimal priceTo;

	/** 变动值 */
	private BigDecimal rate;

	/** 备注,描述 */
	private String remark;
	
	/** 控润模式 1.控点 2.控现 */
	private Integer profitMode;
	
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getProfitMode() {
		return profitMode;
	}

	public void setProfitMode(Integer profitMode) {
		this.profitMode = profitMode;
	}
}
