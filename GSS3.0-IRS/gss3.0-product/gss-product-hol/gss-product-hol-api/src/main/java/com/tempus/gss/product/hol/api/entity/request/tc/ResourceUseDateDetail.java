package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 创建订单时酒店使用明细
 * @author kai.yang
 *
 */
public class ResourceUseDateDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店使用日期
	 */
	@JSONField(name = "UseDate")
	private String useDate;
	/**
	 * 酒店在该日期的卖价
	 */
	@JSONField(name = "CheckPrice")
	private BigDecimal checkPrice;
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public BigDecimal getCheckPrice() {
		return checkPrice;
	}
	public void setCheckPrice(BigDecimal checkPrice) {
		this.checkPrice = checkPrice;
	}
	
}
