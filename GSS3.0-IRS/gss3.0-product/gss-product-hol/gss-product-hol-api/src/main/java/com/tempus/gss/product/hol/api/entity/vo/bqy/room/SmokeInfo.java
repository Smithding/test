package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class SmokeInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="HasRoomInNonSmokeArea")
	private String hasRoomInNonSmokeArea;//禁烟地区有可抽烟房间
	
	@JSONField(name="HasNonSmokeRoom")
	private String hasNonSmokeRoom;		//没有抽烟房间
	
	@JSONField(name="HasSmokeCleanRoom")
	private String hasSmokeCleanRoom;
	
	@JSONField(name="NoNonSmokeRoom")
	private String noNonSmokeRoom;		//所有房间可抽烟
	
	@JSONField(name="NotAllowSmoking")
	private String notAllowSmoking;		//所有房间不能抽烟

	public String getHasRoomInNonSmokeArea() {
		return hasRoomInNonSmokeArea;
	}

	public void setHasRoomInNonSmokeArea(String hasRoomInNonSmokeArea) {
		this.hasRoomInNonSmokeArea = hasRoomInNonSmokeArea;
	}

	public String getHasNonSmokeRoom() {
		return hasNonSmokeRoom;
	}

	public void setHasNonSmokeRoom(String hasNonSmokeRoom) {
		this.hasNonSmokeRoom = hasNonSmokeRoom;
	}

	public String getHasSmokeCleanRoom() {
		return hasSmokeCleanRoom;
	}

	public void setHasSmokeCleanRoom(String hasSmokeCleanRoom) {
		this.hasSmokeCleanRoom = hasSmokeCleanRoom;
	}

	public String getNoNonSmokeRoom() {
		return noNonSmokeRoom;
	}

	public void setNoNonSmokeRoom(String noNonSmokeRoom) {
		this.noNonSmokeRoom = noNonSmokeRoom;
	}

	public String getNotAllowSmoking() {
		return notAllowSmoking;
	}

	public void setNotAllowSmoking(String notAllowSmoking) {
		this.notAllowSmoking = notAllowSmoking;
	}
	
	
}
