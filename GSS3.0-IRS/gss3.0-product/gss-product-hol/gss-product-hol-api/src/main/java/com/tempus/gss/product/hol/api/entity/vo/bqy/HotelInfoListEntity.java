package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店信息
 */
public class HotelInfoListEntity extends BaseHoelInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JSONField(name="CommentCount")
	private int commentCount;		//评论数
	
	@JSONField(name="Distance")
	private BigDecimal distance;	//距离公里
	
	@JSONField(name="HotelStarLicence")
	private double hotelStarLicence;	//酒店星级	
	
	@JSONField(name="HotelStarName")
	private String hotelStarName;		//星级名称
	
	@JSONField(name="BulitTime")
	private String bulitTime;			//建造时间
	
	@JSONField(name="CtripCommRate")
	private double ctripCommRate;		//用户评论评分			

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public double getHotelStarLicence() {
		return hotelStarLicence;
	}

	public void setHotelStarLicence(double hotelStarLicence) {
		this.hotelStarLicence = hotelStarLicence;
	}

	public String getHotelStarName() {
		return hotelStarName;
	}

	public void setHotelStarName(String hotelStarName) {
		this.hotelStarName = hotelStarName;
	}

	public String getBulitTime() {
		return bulitTime;
	}

	public void setBulitTime(String bulitTime) {
		this.bulitTime = bulitTime;
	}

	public double getCtripCommRate() {
		return ctripCommRate;
	}

	public void setCtripCommRate(double ctripCommRate) {
		this.ctripCommRate = ctripCommRate;
	}
	
}
