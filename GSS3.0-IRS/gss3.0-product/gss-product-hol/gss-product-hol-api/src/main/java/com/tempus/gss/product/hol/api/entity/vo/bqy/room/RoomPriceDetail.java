package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class RoomPriceDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@JSONField(name="Price")
	private Price price;
	
	@JSONField(name="Cost")
	private String cost;
	
	@JSONField(name="EffectDate")
    private String effectDate;
	
	@JSONField(name="Breakfast")
    private String breakfast;
	
	@JSONField(name="RoomStatus")
    private String roomStatus;
	
	@JSONField(name="GuaranteeCode")
    private String guaranteeCode;

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public String getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}

	public String getGuaranteeCode() {
		return guaranteeCode;
	}

	public void setGuaranteeCode(String guaranteeCode) {
		this.guaranteeCode = guaranteeCode;
	}
	
}
