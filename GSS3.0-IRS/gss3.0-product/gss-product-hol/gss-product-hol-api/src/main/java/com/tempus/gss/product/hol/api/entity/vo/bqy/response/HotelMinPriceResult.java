package com.tempus.gss.product.hol.api.entity.vo.bqy.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *	酒店最低价结果
 */
public class HotelMinPriceResult implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="HotelId")
	private Long hotelId;			//酒店ID
	
	@JSONField(name="LowPrice")
	private BigDecimal lowPrice;	//最低价

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public BigDecimal getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(BigDecimal lowPrice) {
		this.lowPrice = lowPrice;
	}

}
