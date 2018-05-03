package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;
import java.util.List;

/**
 * 城市搜索信息返回结果集类
 */
public class CityInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<HotelBrand> hotelbrand;	    //酒店品牌
	
	private List<HotelFacility>	hotelFacility;	//设施服务
	
	private List<InfoShowlist> zoneInfo;	    //商圈

	public List<HotelBrand> getHotelbrand() {
		return hotelbrand;
	}

	public void setHotelbrand(List<HotelBrand> hotelbrand) {
		this.hotelbrand = hotelbrand;
	}

	public List<HotelFacility> getHotelFacility() {
		return hotelFacility;
	}

	public void setHotelFacility(List<HotelFacility> hotelFacility) {
		this.hotelFacility = hotelFacility;
	}

	public List<InfoShowlist> getZoneInfo() {
		return zoneInfo;
	}

	public void setZoneInfo(List<InfoShowlist> zoneInfo) {
		this.zoneInfo = zoneInfo;
	}
	
}	

