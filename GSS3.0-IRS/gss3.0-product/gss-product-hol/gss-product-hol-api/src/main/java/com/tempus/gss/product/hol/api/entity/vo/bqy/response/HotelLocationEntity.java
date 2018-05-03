package com.tempus.gss.product.hol.api.entity.vo.bqy.response;

import java.io.Serializable;
import java.util.List;

import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelBrand;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelFacility;
import com.tempus.gss.product.hol.api.entity.vo.bqy.InfoShowlist;

public class HotelLocationEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<HotelBrand> hotelbrand;	    //酒店品牌
	
	private List<HotelFacility>	hotelFacility;	//设施服务
	
	private List<InfoShowlist> infolist;	    //商圈

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

	public List<InfoShowlist> getInfolist() {
		return infolist;
	}

	public void setInfolist(List<InfoShowlist> infolist) {
		this.infolist = infolist;
	}
	
}
