package com.tempus.gss.product.hol.api.entity.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 千淘酒店信息中的价格信息的价格明细实体
 * Created by luofengjie on 2017/3/24.
 */
public class QTPrice implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 价格
     */
    @JSONField(name = "Price")
    private BigDecimal price;
    /**
     * 返现
     */
    @JSONField(name = "Refund")
    private BigDecimal refund;
    /**
     * 立减
     */
    @JSONField(name = "Reduce")
    private BigDecimal reduce;
    
    /**
     * 控润后的价格
     */
    private BigDecimal profitPrice;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }

    public BigDecimal getRefund() {
        return refund;
    }

    public void setRefund(BigDecimal refund) {
        this.refund = refund;
    }

	public BigDecimal getProfitPrice() {
		return profitPrice;
	}

	public void setProfitPrice(BigDecimal profitPrice) {
		this.profitPrice = profitPrice;
	}
}
