package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

import java.io.Serializable;
import java.util.Date;

/**
 * 酒店详细信息查询
 */
public class QueryHotelParam extends BaseQueryParam implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long HotelId;			//酒店id
	
	private String CityCode;		//城市名称
	
	private String CheckInTime;	//入住时间(2018-04-08)
	
	private String CheckOutTime;//离店时间(2018-04-09)

	public Long getHotelId() {
		return HotelId;
	}

	public void setHotelId(Long hotelId) {
		HotelId = hotelId;
	}

	public String getCityCode() {
		return CityCode;
	}

	public void setCityCode(String cityCode) {
		CityCode = cityCode;
	}

	public String getCheckInTime() {
		return CheckInTime;
	}

	public void setCheckInTime(String checkInTime) {
		CheckInTime = checkInTime;
	}

	public String getCheckOutTime() {
		return CheckOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		CheckOutTime = checkOutTime;
	}

}
