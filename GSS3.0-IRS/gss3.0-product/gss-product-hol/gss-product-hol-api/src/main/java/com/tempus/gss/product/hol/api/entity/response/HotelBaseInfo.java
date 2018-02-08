package com.tempus.gss.product.hol.api.entity.response;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店基本资料
 * @author Administrator
 *
 */
public class HotelBaseInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 热点编号
	 */
	@JSONField(name = "HotelCode")
	private String hotelCode;
	/**
	 * 客户编号
	 */
	@JSONField(name = "CityCode")
	private String cityCode;
	/**
	 * 酒店编号
	 */
	@JSONField(name = "CnName")
	private String cnName;
	/**
	 * 策略编号
	 */
	@JSONField(name = "EnName")
	private String enName;
	/**
	 * 开业时间
	 */
	@JSONField(name = "OpeningTime")
	private String openingTime;
	/**
	 * 装修时间
	 */
	@JSONField(name = "FixTime")
	private String fixTime;
	/**
	 * 星级编号(1,2,3,4,5)
	 */
	@JSONField(name = "StarCode")
	private String starCode;
	/**
	 * 
	 */
	@JSONField(name = "Email")
	private String email;
	/**
	 * 
	 */
	@JSONField(name = "PhoneNum")
	private String phoneNum;
	/**
	 * 
	 */
	@JSONField(name = "FaxNum")
	private String faxNum;
	/**
	 * 纬度
	 */
	@JSONField(name = "Latitude")
	private String latitude;
	/**
	 * 精度
	 */
	@JSONField(name = "Longitude")
	private String longitude;
	/**
	 * 地址
	 */
	@JSONField(name = "AddrCN")
	private String addrCN;
	/**
	 * 右边
	 */
	@JSONField(name = "PostNumber")
	private String postNumber;
	/**
	 * 品牌
	 */
	@JSONField(name = "BrandCode")
	private String brandCode;
	/**
	 * 图片
	 */
	@JSONField(name = "MainPicture")
	private String mainPicture;
	/**
	 * 简述
	 */
	@JSONField(name = "ShortDescription")
	private String shortDescription;
	/**
	 * 描述
	 */
	@JSONField(name = "Description")
	private String description;
	/**
	 * 
	 */
	@JSONField(name = "TrafficInformation")
	private String trafficInformation;
	/**
	 * 
	 */
	@JSONField(name = "HotelFacility")
	private String hotelFacility;
	/**
	 * 
	 */
	@JSONField(name = "HotelService")
	private String hotelService;
	/**
	 * 
	 */
	@JSONField(name = "RoomNumber")
	private String roomNumber;
	/**
	 * 
	 */
	@JSONField(name = "Category")
	private String category;
	
	public String getHotelCode() {
		return hotelCode;
	}
	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getOpeningTime() {
		return openingTime;
	}
	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}
	public String getFixTime() {
		return fixTime;
	}
	public void setFixTime(String fixTime) {
		this.fixTime = fixTime;
	}
	public String getStarCode() {
		return starCode;
	}
	public void setStarCode(String starCode) {
		this.starCode = starCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getFaxNum() {
		return faxNum;
	}
	public void setFaxNum(String faxNum) {
		this.faxNum = faxNum;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getAddrCN() {
		return addrCN;
	}
	public void setAddrCN(String addrCN) {
		this.addrCN = addrCN;
	}
	public String getPostNumber() {
		return postNumber;
	}
	public void setPostNumber(String postNumber) {
		this.postNumber = postNumber;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getMainPicture() {
		return mainPicture;
	}
	public void setMainPicture(String mainPicture) {
		this.mainPicture = mainPicture;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTrafficInformation() {
		return trafficInformation;
	}
	public void setTrafficInformation(String trafficInformation) {
		this.trafficInformation = trafficInformation;
	}
	public String getHotelFacility() {
		return hotelFacility;
	}
	public void setHotelFacility(String hotelFacility) {
		this.hotelFacility = hotelFacility;
	}
	public String getHotelService() {
		return hotelService;
	}
	public void setHotelService(String hotelService) {
		this.hotelService = hotelService;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
