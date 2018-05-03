package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *	酒店图片 
 */
public class ImageList implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JSONField(name="TitleName")
	private String titleName;	//类型名称
	
	@JSONField(name="HotelId")
	private Integer hotelId;	//酒店ID
	
	@JSONField(name="RoomTypeId")
	private String roomTypeId;	//房间ID
	
	@JSONField(name="RoomTypeName")
	private String roomTypeName;	//房间类型名称
	
	@JSONField(name="HotelImageID")
	private String hotelImageID;	//图片ID
	
	@JSONField(name="ImageType")
	private String imageType;		//图片类型
	
	@JSONField(name="ImageUrl")
	private String imageUrl;		//图片地址

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public Integer getHotelId() {
		return hotelId;
	}

	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	public String getHotelImageID() {
		return hotelImageID;
	}

	public void setHotelImageID(String hotelImageID) {
		this.hotelImageID = hotelImageID;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
