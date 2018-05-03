package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店详细信息查询
 */
public class QueryHotelInfoParam extends BaseQueryParam implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JSONField(name="HotelId")
	private Long hotelId;			//酒店id

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

}
