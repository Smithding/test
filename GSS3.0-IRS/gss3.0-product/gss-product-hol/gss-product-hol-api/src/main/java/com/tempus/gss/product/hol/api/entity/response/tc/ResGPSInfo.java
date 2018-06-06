package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TC酒店坐标信息
 * @author kai.yang
 *
 */
public class ResGPSInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 坐标类型（1-百度坐标 ）
	 */
	@JSONField(name = "Type")
	private Integer type;
	/**
	 * 经度
	 */
	@JSONField(name = "Lon")
	private String lon;
	/**
	 * 纬度
	 */
	@JSONField(name = "Lat")
	private String lat;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	
	
	
	
}
