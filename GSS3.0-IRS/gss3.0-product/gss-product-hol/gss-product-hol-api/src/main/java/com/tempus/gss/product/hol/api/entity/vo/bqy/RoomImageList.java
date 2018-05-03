package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店房型图片
 */
public class RoomImageList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="ImageId")
	private int imageId;		//图片ID
	
	@JSONField(name="RoomTypeId")
	private String roomTypeId;  //房型类型
	
	@JSONField(name="ImgUrl")
	private String imgUrl;		//图片地址

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
