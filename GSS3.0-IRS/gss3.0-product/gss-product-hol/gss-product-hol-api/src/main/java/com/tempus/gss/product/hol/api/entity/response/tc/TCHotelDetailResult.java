package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TC酒店商品详情接口
 * @author kai.yang
 *
 */
public class TCHotelDetailResult implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店基本信息
	 */
	@JSONField(name = "ResBaseInfos")
	private List<ResBaseInfo> resBaseInfos;  
	/**
	 * 酒店房型信息
	 */
	@JSONField(name = "ResProBaseInfos")
	private List<ResProBaseInfo> resProBaseInfos; 
	/**
	 * 酒店图片集合
	 */
	@JSONField(name = "ResImages")
	private List<ImgInfo> resImages;
	public List<ResBaseInfo> getResBaseInfos() {
		return resBaseInfos;
	}
	public void setResBaseInfos(List<ResBaseInfo> resBaseInfos) {
		this.resBaseInfos = resBaseInfos;
	}
	public List<ResProBaseInfo> getResProBaseInfos() {
		return resProBaseInfos;
	}
	public void setResProBaseInfos(List<ResProBaseInfo> resProBaseInfos) {
		this.resProBaseInfos = resProBaseInfos;
	}
	public List<ImgInfo> getResImages() {
		return resImages;
	}
	public void setResImages(List<ImgInfo> resImages) {
		this.resImages = resImages;
	} 
	
	
	
}
