package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店房型设施服务
 */
public class QueryHotelRoomFacilityParam extends BaseQueryParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="HotelId")
	private String hotelId;		//酒店ID

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	
}
