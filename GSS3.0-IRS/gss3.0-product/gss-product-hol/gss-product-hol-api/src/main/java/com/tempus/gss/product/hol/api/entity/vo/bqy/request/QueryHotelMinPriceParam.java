package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelId;

/**
 * 酒店最低价查询
 */
public class QueryHotelMinPriceParam extends BaseQueryParam {

	private static final long serialVersionUID = 1L;

	@JSONField(name="HotelIdLists")
	private List<HotelId> hotelIdLists;			//酒店id集合
	
	@JSONField(name="CheckInTime")
	private String checkInTime;					//入住时间
	
	@JSONField(name="DayNum")
	private int dayNum;							//入住天数

	public List<HotelId> getHotelIdLists() {
		return hotelIdLists;
	}

	public void setHotelIdLists(List<HotelId> hotelIdLists) {
		this.hotelIdLists = hotelIdLists;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public int getDayNum() {
		return dayNum;
	}

	public void setDayNum(int dayNum) {
		this.dayNum = dayNum;
	}
	
}
