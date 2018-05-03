package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;
import java.util.Set;

/**
 * 城市信息
 */
public class CityDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private String province;     					    //省份编号
	
	private String provinceName;     				    //省份名称
	
	private String city;							    //城市编号
	
	private String cityName;						    //城市名称
	
//	private List<HotelBrand> hotelBrandList; 		    //酒店品牌
//	
//	private List<HotelFacility> hotelFacilityList;	    //设施服务
//	
//	private List<InfoShowlist> cityBusinessSectionInfo;	//商圈
	
	private Set<String> hotelBrands; 		    		//酒店品牌
	
	private Set<String> hotelFacilitys;	    			//设施服务
	
	private Set<String> cityBusinessSectionInfo;		//商圈
	
	private Set<String> cityArea;					    //城市区域
	
	private Set<String> cityScenic;					    //城市景点
	
	private Set<String> cityAirRailWay;				    	//城市的机场/火车站
	
//	private Set<String> cityRailWay;				    //城市的火车站
	
	private Set<String> citySubWay;					    //城市的地铁站

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Set<String> getHotelBrands() {
		return hotelBrands;
	}

	public void setHotelBrands(Set<String> hotelBrands) {
		this.hotelBrands = hotelBrands;
	}

	public Set<String> getHotelFacilitys() {
		return hotelFacilitys;
	}

	public void setHotelFacilitys(Set<String> hotelFacilitys) {
		this.hotelFacilitys = hotelFacilitys;
	}

	public Set<String> getCityBusinessSectionInfo() {
		return cityBusinessSectionInfo;
	}

	public void setCityBusinessSectionInfo(Set<String> cityBusinessSectionInfo) {
		this.cityBusinessSectionInfo = cityBusinessSectionInfo;
	}

	public Set<String> getCityArea() {
		return cityArea;
	}

	public void setCityArea(Set<String> cityArea) {
		this.cityArea = cityArea;
	}

	public Set<String> getCityScenic() {
		return cityScenic;
	}

	public void setCityScenic(Set<String> cityScenic) {
		this.cityScenic = cityScenic;
	}

	public Set<String> getCitySubWay() {
		return citySubWay;
	}

	public void setCitySubWay(Set<String> citySubWay) {
		this.citySubWay = citySubWay;
	}

	public Set<String> getCityAirRailWay() {
		return cityAirRailWay;
	}

	public void setCityAirRailWay(Set<String> cityAirRailWay) {
		this.cityAirRailWay = cityAirRailWay;
	}
	
}
