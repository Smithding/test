package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class RoomPriceInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="AveragePrice")
	private AveragePrice averagePrice;
	
	@JSONField(name="RoomPriceDetail")
	private List<RoomPriceDetail> roomPriceDetail;
	
	@JSONField(name="PayType")
	private String payType;
	
	@JSONField(name="IsCanReserve")
    private String isCanReserve;
	
	@JSONField(name="IsGuarantee")
    private String isGuarantee;
	
	@JSONField(name="IsJustifyConfirm")
    private String isJustifyConfirm;
	
	@JSONField(name="RatePlanCategory")
    private String ratePlanCategory;
	
	@JSONField(name="RemainingRooms")
    private String remainingRooms;

	public AveragePrice getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(AveragePrice averagePrice) {
		this.averagePrice = averagePrice;
	}

	public List<RoomPriceDetail> getRoomPriceDetail() {
		return roomPriceDetail;
	}

	public void setRoomPriceDetail(List<RoomPriceDetail> roomPriceDetail) {
		this.roomPriceDetail = roomPriceDetail;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getIsCanReserve() {
		return isCanReserve;
	}

	public void setIsCanReserve(String isCanReserve) {
		this.isCanReserve = isCanReserve;
	}

	public String getIsGuarantee() {
		return isGuarantee;
	}

	public void setIsGuarantee(String isGuarantee) {
		this.isGuarantee = isGuarantee;
	}

	public String getIsJustifyConfirm() {
		return isJustifyConfirm;
	}

	public void setIsJustifyConfirm(String isJustifyConfirm) {
		this.isJustifyConfirm = isJustifyConfirm;
	}

	public String getRatePlanCategory() {
		return ratePlanCategory;
	}

	public void setRatePlanCategory(String ratePlanCategory) {
		this.ratePlanCategory = ratePlanCategory;
	}

	public String getRemainingRooms() {
		return remainingRooms;
	}

	public void setRemainingRooms(String remainingRooms) {
		this.remainingRooms = remainingRooms;
	}
}
