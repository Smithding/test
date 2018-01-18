package com.tempus.gss.product.hol.api.entity.request;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 价格区间
 * Created by luofengjie on 2017/3/24.
 */
public class PriceFromTo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 最低价格
     */
    @JSONField(name = "PriceFrom")
    private BigDecimal priceFrom;
    /**
     * 最高价格
     */
    @JSONField(name = "PriceTo")
    private BigDecimal priceTo;
    
    @JSONField(name = "Rate")
    private BigDecimal rate;

	public BigDecimal getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}

	public BigDecimal getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
    
    
   
   }
