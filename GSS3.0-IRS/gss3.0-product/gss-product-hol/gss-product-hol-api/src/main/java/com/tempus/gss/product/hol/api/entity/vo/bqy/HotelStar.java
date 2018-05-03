package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店评分
 */

public class HotelStar implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JSONField(name="HotelStarRate")
	private String hotelStarRate;	//酒店星级评分
	
	@JSONField(name="HotelUserRate")
	private String hotelUserRate;	//酒店用户评级
	
	@JSONField(name="HotelCommRate")
	private String hotelCommRate;	//酒店评论评分
	
	@JSONField(name="CommSurroundingRate")
	private String commSurroundingRate;	//评论环境评分
	
	@JSONField(name="CommFacilityRate")
	private String commFacilityRate; //设施评分
	
	@JSONField(name="CommCleanRate")
	private String commCleanRate;	 //卫生评分
	
	@JSONField(name="CommServiceRate")
	private String commServiceRate;	 //服务评分

	public String getHotelStarRate() {
		return hotelStarRate;
	}

	public void setHotelStarRate(String hotelStarRate) {
		this.hotelStarRate = hotelStarRate;
	}

	public String getHotelUserRate() {
		return hotelUserRate;
	}

	public void setHotelUserRate(String hotelUserRate) {
		this.hotelUserRate = hotelUserRate;
	}

	public String getHotelCommRate() {
		return hotelCommRate;
	}

	public void setHotelCommRate(String hotelCommRate) {
		this.hotelCommRate = hotelCommRate;
	}

	public String getCommSurroundingRate() {
		return commSurroundingRate;
	}

	public void setCommSurroundingRate(String commSurroundingRate) {
		this.commSurroundingRate = commSurroundingRate;
	}

	public String getCommFacilityRate() {
		return commFacilityRate;
	}

	public void setCommFacilityRate(String commFacilityRate) {
		this.commFacilityRate = commFacilityRate;
	}

	public String getCommCleanRate() {
		return commCleanRate;
	}

	public void setCommCleanRate(String commCleanRate) {
		this.commCleanRate = commCleanRate;
	}

	public String getCommServiceRate() {
		return commServiceRate;
	}

	public void setCommServiceRate(String commServiceRate) {
		this.commServiceRate = commServiceRate;
	}
}
