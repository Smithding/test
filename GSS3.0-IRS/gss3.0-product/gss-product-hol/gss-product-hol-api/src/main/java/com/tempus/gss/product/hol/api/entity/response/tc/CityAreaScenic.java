package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 根据城市名查找区域
 * @author kai.yang
 *
 */
public class CityAreaScenic implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 城市名
	 */
	@JSONField(name = "CityName")
	private String cityName;
	/**
	 * 城市区域
	 */
	@JSONField(name = "CityArea")
	private Set<String> cityArea;
	
	/**
	 * 城市景点
	 */
	@JSONField(name = "CityScenic")
	private Set<String> cityScenic;
	/**
	 * 城市的机场火车站
	 */
	@JSONField(name = "CityAirRailWay")
	private Set<String> cityAirRailWay;
	/**
	 * 城市的地铁站
	 */
	@JSONField(name = "CitySubWay")
	private Set<String> citySubWay;
	/**
	 * 城市热门商圈
	 */
	@JSONField(name = "CityBusinessSectionInfo")
	private Set<String> cityBusinessSectionInfo;
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public Set<String> getCityAirRailWay() {
		return cityAirRailWay;
	}

	public void setCityAirRailWay(Set<String> cityAirRailWay) {
		this.cityAirRailWay = cityAirRailWay;
	}

	public Set<String> getCitySubWay() {
		return citySubWay;
	}

	public void setCitySubWay(Set<String> citySubWay) {
		this.citySubWay = citySubWay;
	}

	public Set<String> getCityBusinessSectionInfo() {
		return cityBusinessSectionInfo;
	}

	public void setCityBusinessSectionInfo(Set<String> cityBusinessSectionInfo) {
		this.cityBusinessSectionInfo = cityBusinessSectionInfo;
	}

	/* public boolean equals(Object obj){
	        if(this==obj)
	            return true;
	        if(!(obj instanceof CityAreaScenic)){
	            throw new ClassCastException("类型错误");
	        }
	        //System.out.println(this+"==============="+obj);
	        CityAreaScenic p =(CityAreaScenic)obj;
	        return this.cityArea.equals(p.cityArea);
	    }*/
	
	
	
}
