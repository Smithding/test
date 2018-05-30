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
	 * 酒店名字
	 */
	@JSONField(name = "ResName")
	private String resName;
	
	/**
	 * 城市名称（去除“市”字样，例如：“苏州”）
	 */
	@JSONField(name = "CityName")
	private String cityName;
	/**
	 * 酒店是否有效, 0为有效, 1为无效
	 */
	@JSONField(name = "Status")
	private String status;
	
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
