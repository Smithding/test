package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class Location implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店 Id
	 */
	@JSONField(name = "Location")
	private String location;
	/**
	 * 酒店 Id
	 */
	@JSONField(name = "Size")
	private Integer size;
	/**
	 * 酒店 Id
	 */
	@JSONField(name = "WaterMark")
	private Integer waterMark;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getWaterMark() {
		return waterMark;
	}
	public void setWaterMark(Integer waterMark) {
		this.waterMark = waterMark;
	}
	
	

}
