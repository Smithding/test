package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店ID
 */
public class HotelId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="HotelId")
	private Long hotelId; 			//酒店Id
	
	@JSONField(name="HotelName")
	private String hotelName; 	//酒店名称

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	
}
