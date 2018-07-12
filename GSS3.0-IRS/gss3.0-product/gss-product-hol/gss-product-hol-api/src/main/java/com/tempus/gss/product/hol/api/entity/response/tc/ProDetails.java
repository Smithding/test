package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class ProDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 房型 Id
	 */
	@JSONField(name = "ProId")
	private String proId;
	
	/**
	 * 酒店房型名称（对应房型名称/票型名称）
	 */
	@JSONField(name = "ResProName")
	private String resProName;
	
	/**
	 * 酒店房型名称（对应房型名称/票型名称）
	 */
	@JSONField(name = "BedSize")
	private String bedSize;
	
	/**
	 * 是否有宽带
	 */
	@JSONField(name = "HasBroadband")
	private List<String> hasBroadband;
	
	/**
	 * 房型面积
	 */
	@JSONField(name = "RoomSize")
	private String roomSize;
	
	/**
	 * 单数楼层
	 */
	@JSONField(name = "RoomFloor")
	private String roomFloor;
	
	/**
	 * 房型设施
	 */
	@JSONField(name = "RoomFacilities")
	private List<FacilityServices> roomFacilities;
	
	/**
	 * 房型列表
	 */
	@JSONField(name = "ResProBaseInfoList")
	private List<ResProBaseInfo> resProBaseInfoList;
	/**
	 * 用来排序的均价
	 */
	private Integer proDetailConPrice;
	
	private Integer minPrice;
	
	/**
	 * 0代表下线， 1代表上线
	 */
	private Integer saleStatus = 1;
	/**
	 * 房型图片
	 */
	private String imgUrl;

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getResProName() {
		return resProName;
	}

	public void setResProName(String resProName) {
		this.resProName = resProName;
	}

	public String getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(String roomSize) {
		this.roomSize = roomSize;
	}

	public String getRoomFloor() {
		return roomFloor;
	}

	public void setRoomFloor(String roomFloor) {
		this.roomFloor = roomFloor;
	}

	public List<FacilityServices> getRoomFacilities() {
		return roomFacilities;
	}

	public void setRoomFacilities(List<FacilityServices> roomFacilities) {
		this.roomFacilities = roomFacilities;
	}

	public List<ResProBaseInfo> getResProBaseInfoList() {
		return resProBaseInfoList;
	}

	public void setResProBaseInfoList(List<ResProBaseInfo> resProBaseInfoList) {
		this.resProBaseInfoList = resProBaseInfoList;
	}

	public List<String> getHasBroadband() {
		return hasBroadband;
	}

	public void setHasBroadband(List<String> hasBroadband) {
		this.hasBroadband = hasBroadband;
	}

	public Integer getProDetailConPrice() {
		return proDetailConPrice;
	}

	public void setProDetailConPrice(Integer proDetailConPrice) {
		this.proDetailConPrice = proDetailConPrice;
	}

	public Integer getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getBedSize() {
		return bedSize;
	}

	public void setBedSize(String bedSize) {
		this.bedSize = bedSize;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}
	
}
