package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class BaseHoelInfo {
	
	@JSONField(name="HotelInfoID")
	private String hotelInfoID;		//自增ID   1
	
	@JSONField(name="SourceHotelCode")
	private String sourceHotelCode;	//酒店ID		1
	
	@JSONField(name="HotelName")
	private String hotelName;		//酒店中文名	1
	
	@JSONField(name="HotelNameEN")
	private String hotelNameEN;		//酒店英文名称	1
	
	@JSONField(name="CityCode")
	private String cityCode;		//酒店城市ID	1
	
	@JSONField(name="ThreeCode")
	private String threeCode;		//酒店城市三字码		1
	
	@JSONField(name="RoadCross")
	private String roadCross;		//就近地址		1
	
	@JSONField(name="BrandID")
	private String brandID;			//酒店品牌ID	1
	
	@JSONField(name="AddressLine")
	private String addressLine;		//酒店地址		1
	
	@JSONField(name="Latitude")
	private BigDecimal latitude;	//纬度		1
	
	@JSONField(name="Longitude")
	private BigDecimal longitude;	//经度		1
	
	@JSONField(name="ServiceCode")
	private String serviceCode;		//服务Code	1
	
	@JSONField(name="ServiceName")
	private String serviceName;		//设施服务Code	 1
	
	@JSONField(name="HotelStarRate")
	private Double hotelStarRate;		//酒店评分等级	1
	
	@JSONField(name="FirstImg")
	private String firstImg;			//酒店头图		1
	
	@JSONField(name="imageList")
	private List<ImageList> imageList;	//酒店图片集合	1
	
	@JSONField(name="ThemeCategoryCode")
	private String themeCategoryCode;	//主题Code	1
	
	@JSONField(name="ThemeCategoryName")
	private String themeCategoryName;	//特色主题名称	1
	
	@JSONField(name="Description")
	private String description;			//酒店描述		1
	
	@JSONField(name="CheckInTime")
	private String checkInTime;			//入住时间		1
	
	@JSONField(name="CheckOutTime")
	private String checkOutTime;		//离店时间		1
	
	@JSONField(name="Roomquantity")
	private int roomquantity;			//房间数量		1
	
	@JSONField(name="MultimediaDescription")
	private String multimediaDescription;	//描述信息列表	1
		
	@JSONField(name="IdCardEngName")
	private String idCardEngName;		//可用信用卡英文名	1
	
	@JSONField(name="IdCardChnName")
	private String idCardChnName;		//可用信用卡中文名	1
	
	@JSONField(name="BrandName")
	private String brandName;			//品牌名			1
	
	@JSONField(name="ZoneID")
	private int zoneID;					//商圈ID			1
	
	@JSONField(name="ZoneName")
	private String zoneName;			//商圈名称			1
	
	@JSONField(name="MinPrice")
	private double minPrice;			//最低价			1
	
	@JSONField(name="ParentServicesName")
	private String parentServicesName;	//设施服务父级名称 	1

	public String getHotelInfoID() {
		return hotelInfoID;
	}

	public void setHotelInfoID(String hotelInfoID) {
		this.hotelInfoID = hotelInfoID;
	}

	public String getSourceHotelCode() {
		return sourceHotelCode;
	}

	public void setSourceHotelCode(String sourceHotelCode) {
		this.sourceHotelCode = sourceHotelCode;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelNameEN() {
		return hotelNameEN;
	}

	public void setHotelNameEN(String hotelNameEN) {
		this.hotelNameEN = hotelNameEN;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getThreeCode() {
		return threeCode;
	}

	public void setThreeCode(String threeCode) {
		this.threeCode = threeCode;
	}

	public String getRoadCross() {
		return roadCross;
	}

	public void setRoadCross(String roadCross) {
		this.roadCross = roadCross;
	}

	public String getBrandID() {
		return brandID;
	}

	public void setBrandID(String brandID) {
		this.brandID = brandID;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public double getHotelStarRate() {
		return hotelStarRate;
	}

	public void setHotelStarRate(Double hotelStarRate) {
		this.hotelStarRate = hotelStarRate;
	}

	public String getFirstImg() {
		return firstImg;
	}

	public void setFirstImg(String firstImg) {
		this.firstImg = firstImg;
	}

	public List<ImageList> getImageList() {
		return imageList;
	}

	public void setImageList(List<ImageList> imageList) {
		this.imageList = imageList;
	}

	public String getThemeCategoryCode() {
		return themeCategoryCode;
	}

	public void setThemeCategoryCode(String themeCategoryCode) {
		this.themeCategoryCode = themeCategoryCode;
	}

	public String getThemeCategoryName() {
		return themeCategoryName;
	}

	public void setThemeCategoryName(String themeCategoryName) {
		this.themeCategoryName = themeCategoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public int getRoomquantity() {
		return roomquantity;
	}

	public void setRoomquantity(int roomquantity) {
		this.roomquantity = roomquantity;
	}

	public String getMultimediaDescription() {
		return multimediaDescription;
	}

	public void setMultimediaDescription(String multimediaDescription) {
		this.multimediaDescription = multimediaDescription;
	}

	public String getIdCardEngName() {
		return idCardEngName;
	}

	public void setIdCardEngName(String idCardEngName) {
		this.idCardEngName = idCardEngName;
	}

	public String getIdCardChnName() {
		return idCardChnName;
	}

	public void setIdCardChnName(String idCardChnName) {
		this.idCardChnName = idCardChnName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public int getZoneID() {
		return zoneID;
	}

	public void setZoneID(int zoneID) {
		this.zoneID = zoneID;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public String getParentServicesName() {
		return parentServicesName;
	}

	public void setParentServicesName(String parentServicesName) {
		this.parentServicesName = parentServicesName;
	}
	
}
