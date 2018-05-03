package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class HotelList implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<HotelInfoListEntity> hotelList;
	
	@JSONField(name="Allcount")
	private int allcount;
	
	@JSONField(name="HotelCount")
	private int hotelCount;

	public List<HotelInfoListEntity> getHotelList() {
		return hotelList;
	}

	public void setHotelList(List<HotelInfoListEntity> hotelList) {
		this.hotelList = hotelList;
	}

	public int getAllcount() {
		return allcount;
	}

	public void setAllcount(int allcount) {
		this.allcount = allcount;
	}

	public int getHotelCount() {
		return hotelCount;
	}

	public void setHotelCount(int hotelCount) {
		this.hotelCount = hotelCount;
	}
	
}
