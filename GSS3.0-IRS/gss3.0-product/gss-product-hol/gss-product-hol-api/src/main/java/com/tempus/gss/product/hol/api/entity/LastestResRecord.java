package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;


public class LastestResRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 当前访问的用户id
	 */
	@JSONField(name = "UserId")
	private String userId;
	
	/**
	 * 当前时间
	 */
	@JSONField(name = "RecordDate")
	private Date recordDate;
	
	/**
	 * 酒店id
	 */
	@JSONField(name = "ResId")
	private Long resId;
	/**
	 * 酒店名称
	 */
	@JSONField(name = "ResName")
	private String resName;
	/**
	 * 酒店等级
	 */
	@JSONField(name = "ResGrade")
	private String resGrade;
	
	/**
	 * 酒店最低价
	 */
	@JSONField(name = "MinPrice")
	private Integer minPrice;
	
	/**
	 * 图片路径
	 */
	@JSONField(name = "ImageUrl")
	private String imageUrl;

	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getResGrade() {
		return resGrade;
	}

	public void setResGrade(String resGrade) {
		this.resGrade = resGrade;
	}
	
	
}
