package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 酒店id集合
 * @author kai.yang
 *
 */
public class ResInfoList implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 酒店 Id
	 */
	@JSONField(name = "ResId")
	private Long resId;
	
	/**
	 * 城市名称（去除“市”字样，例如：“苏州”）
	 */
	@JSONField(name = "CityName")
	private String cityName;
	
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
}
