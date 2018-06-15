package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店预订请求参数
 */
public class BookOrderParam extends QueryHotelInfoParam implements Serializable{

	private static final long serialVersionUID = 1L;

	@JSONField(name="CheckInTime")
	private String checkInTime;			//入住时间
	
	@JSONField(name="CheckOutTime")
	private String checkOutTime;		//离店时间
	
	@JSONField(name="RatePlanCode")
	private String ratePlanCode;		//计划ID
	
	@JSONField(name="RatePlanCategory")
	private String ratePlanCategory;	//预付房型可定检查需增加此字段
	
	@JSONField(name="quantity")
	private int quantity;			//房间数量
	
	@JSONField(name="GuestCount")
	private int guestCount;			//入住人数
	
	@JSONField(name="lateArrivalTime")
	private String lateArrivalTime;		//最晚到店时间

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public String getRatePlanCode() {
		return ratePlanCode;
	}

	public void setRatePlanCode(String ratePlanCode) {
		this.ratePlanCode = ratePlanCode;
	}

	public String getRatePlanCategory() {
		return ratePlanCategory;
	}

	public void setRatePlanCategory(String ratePlanCategory) {
		this.ratePlanCategory = ratePlanCategory;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getLateArrivalTime() {
		return lateArrivalTime;
	}

	public void setLateArrivalTime(String lateArrivalTime) {
		this.lateArrivalTime = lateArrivalTime;
	}

	public int getGuestCount() {
		return guestCount;
	}

	public void setGuestCount(int guestCount) {
		this.guestCount = guestCount;
	}
	
}
