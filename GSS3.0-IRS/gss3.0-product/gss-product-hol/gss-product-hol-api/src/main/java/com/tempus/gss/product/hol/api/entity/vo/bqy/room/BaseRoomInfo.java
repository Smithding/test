package com.tempus.gss.product.hol.api.entity.vo.bqy.room;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 房间基本信息
 */
public class BaseRoomInfo implements Serializable {

	private static final long serialVersionUID = 1L;
		
	@JSONField(name="SmokeInfo")
	private SmokeInfo smokeInfo;				//抽烟房型
	
	@JSONField(name="bedTypeInfo")
	private List<BedTypeInfo> bedTypeInfo;	//床型集合
	
	@JSONField(name="RoomTypeID")
	private String roomTypeID;					//房型ID
	
	@JSONField(name="RoomName")
	private String roomName;					//房间名称
	
	@JSONField(name="Person")
	private String person;						//入住人数
	
	@JSONField(name="AreaRange")
	private String areaRange;					//地区范围
	
	@JSONField(name="FloorRange")
	private String floorRange;					//楼层范围
	
	@JSONField(name="HasWindow")
	private String hasWindow;					//电脑房
	
	@JSONField(name="AddBedFee")
	private String addBedFee;					//续费价格
	
	@JSONField(name="ServiceNames")
	private String serviceNames;				//服务名称

	public SmokeInfo getSmokeInfo() {
		return smokeInfo;
	}

	public void setSmokeInfo(SmokeInfo smokeInfo) {
		this.smokeInfo = smokeInfo;
	}
	
	public List<BedTypeInfo> getBedTypeInfo() {
		return bedTypeInfo;
	}

	public void setBedTypeInfo(List<BedTypeInfo> bedTypeInfo) {
		this.bedTypeInfo = bedTypeInfo;
	}

	public String getRoomTypeID() {
		return roomTypeID;
	}

	public void setRoomTypeID(String roomTypeID) {
		this.roomTypeID = roomTypeID;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getAreaRange() {
		return areaRange;
	}

	public void setAreaRange(String areaRange) {
		this.areaRange = areaRange;
	}

	public String getFloorRange() {
		return floorRange;
	}

	public void setFloorRange(String floorRange) {
		this.floorRange = floorRange;
	}

	public String getHasWindow() {
		return hasWindow;
	}

	public void setHasWindow(String hasWindow) {
		this.hasWindow = hasWindow;
	}

	public String getAddBedFee() {
		return addBedFee;
	}

	public void setAddBedFee(String addBedFee) {
		this.addBedFee = addBedFee;
	}

	public String getServiceNames() {
		return serviceNames;
	}

	public void setServiceNames(String serviceNames) {
		this.serviceNames = serviceNames;
	}
	
}
