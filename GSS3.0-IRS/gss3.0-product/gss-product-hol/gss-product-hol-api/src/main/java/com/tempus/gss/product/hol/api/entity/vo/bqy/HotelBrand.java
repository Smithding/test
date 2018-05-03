package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店品牌
 */
public class HotelBrand implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JSONField(name = "HotelBrandType")
	private String hotelBrandType;		//平台
	
	@JSONField(name = "Sort")
	private int sort;				//排序
	
	@JSONField(name = "HotelBrandName")
	private String hotelBrandName;		//酒店品牌名称
	
	@JSONField(name = "HotelBrandId")
	private Integer hotelBrandId;		//酒店品牌ID

	public String getHotelBrandType() {
		return hotelBrandType;
	}

	public void setHotelBrandType(String hotelBrandType) {
		this.hotelBrandType = hotelBrandType;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getHotelBrandName() {
		return hotelBrandName;
	}

	public void setHotelBrandName(String hotelBrandName) {
		this.hotelBrandName = hotelBrandName;
	}

	public Integer getHotelBrandId() {
		return hotelBrandId;
	}

	public void setHotelBrandId(Integer hotelBrandId) {
		this.hotelBrandId = hotelBrandId;
	}
	
}
