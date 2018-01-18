package com.tempus.gss.product.hol.api.entity.response;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店图片
 * @author Administrator
 *
 */
public class BdHotelPicture implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 图片类别     1 - 餐厅 (Restaurant) 2 - 休闲 (Recreation Facilities)
	 * 3 - 会议室 (Meeting/Conference) 5 - 外观 (Exterior) 6 - 大堂/接待台 (Lobby/ Reception)
     * 8 - 客房 (Guest Room) 10 - 其他 (Other Facilities) 11 - 公共区域 (Public Area)
     * 12 - 周边景点 (Nearby Attractions)
	 */
	@JSONField(name = "PicType")
	private String picType;
	
	/**
	 * 图片标题 如果以酒店房型编号开头，那就是这个房型的图片
	 */
	@JSONField(name = "Title")
	private String title;
	
	/**
	 * 图片地址
	 */
	@JSONField(name = "Url")
	private String url;

	public String getPicType() {
		return picType;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
