package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 数量对象
 * @author kai.yang
 *
 */
public class CountModel implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 成人数
	 */
	@JSONField(name = "AdultCount")
	private Integer adultCount;
	/**
	 * 儿童数
	 */
	@JSONField(name = "ChildCount")
	private Integer childCount;
	/**
	 * 预定份数
	 */
	@JSONField(name = "PriceFraction")
	private Integer priceFraction;
	/**
	 * 房间总数
	 */
	@JSONField(name = "RoomCount")
	private Integer roomCount;
	/**
	 * 行程天数
	 */
	@JSONField(name = "Days")
	private Integer days;
	public Integer getAdultCount() {
		return adultCount;
	}
	public void setAdultCount(Integer adultCount) {
		this.adultCount = adultCount;
	}
	public Integer getChildCount() {
		return childCount;
	}
	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}
	public Integer getPriceFraction() {
		return priceFraction;
	}
	public void setPriceFraction(Integer priceFraction) {
		this.priceFraction = priceFraction;
	}
	public Integer getRoomCount() {
		return roomCount;
	}
	public void setRoomCount(Integer roomCount) {
		this.roomCount = roomCount;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	
}
