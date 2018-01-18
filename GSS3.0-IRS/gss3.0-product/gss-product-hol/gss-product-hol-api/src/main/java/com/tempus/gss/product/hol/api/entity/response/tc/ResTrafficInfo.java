package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TC酒店交通信息
 * @author kai.yang
 *
 */
public class ResTrafficInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店名称
	 */
	@JSONField(name = "ResId")
	private Integer resId;
	/**
	 * 开始地点
	 */
	@JSONField(name = "StartLocation")
	private String startLocation;
	/**
	 * 距离
	 */
	@JSONField(name = "Distance")
	private Float distance;
	/**
	 * 怎么到达
	 */
	@JSONField(name = "HowToArrive")
	private String howToArrive;
	/**
	 * 起始地点的详细说明
	 */
	@JSONField(name = "Description")
	private String description;
	/**
	 * 经度（ 目前未使用，默认为 0.00000000000000000000 ）
	 */
	@JSONField(name = "TrafficLon")
	private Float trafficLon;
	/**
	 * 纬度（ 目前未使用，默认为 0.00000000000000000000 ）
	 */
	@JSONField(name = "TrafficLat")
	private Float trafficLat;
	public Integer getResId() {
		return resId;
	}
	public void setResId(Integer resId) {
		this.resId = resId;
	}
	public String getStartLocation() {
		return startLocation;
	}
	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}
	public Float getDistance() {
		return distance;
	}
	public void setDistance(Float distance) {
		this.distance = distance;
	}
	public String getHowToArrive() {
		return howToArrive;
	}
	public void setHowToArrive(String howToArrive) {
		this.howToArrive = howToArrive;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Float getTrafficLon() {
		return trafficLon;
	}
	public void setTrafficLon(Float trafficLon) {
		this.trafficLon = trafficLon;
	}
	public Float getTrafficLat() {
		return trafficLat;
	}
	public void setTrafficLat(Float trafficLat) {
		this.trafficLat = trafficLat;
	}
	
}
