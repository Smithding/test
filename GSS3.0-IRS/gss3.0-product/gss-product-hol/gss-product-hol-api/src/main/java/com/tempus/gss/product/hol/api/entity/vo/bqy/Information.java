package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 商圈信息
 */
public class Information implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JSONField(name="InfoId")
	private int infoId;				//信息
	
	@JSONField(name="CityId")
	private int cityId;				//城市ID
	
	@JSONField(name="TypeId")
	private int typeId;				//关键字
	
	@JSONField(name="InfoName")
	private String infoName;		//名称
	
	@JSONField(name="Longitude")
	private BigDecimal longitude;	//经度
	
	@JSONField(name="Latitude")
	private BigDecimal latitude;	//纬度
	
	@JSONField(name="Infouid")
	private String infouid;			//UID
	
	@JSONField(name="PhoneNumber")
	private String phoneNumber;		//联系电话
	
	@JSONField(name="Postcode")
	private String postcode;		//邮编
	
	@JSONField(name="Address")
	private String address;			//地址
	
	@JSONField(name="IsAccurate")
	private Boolean isAccurate;		//是否准确（精确）
	
	@JSONField(name="Url")
	private String url;				//百度链接地址
	
	@JSONField(name="DetailUrl")
	private String detailUrl;		//百度详细链接地址
	
	@JSONField(name="Tags")
	private String tags;			//标签
	
	@JSONField(name="AreaType")
	private String areaType;	

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getInfoName() {
		return infoName;
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getInfouid() {
		return infouid;
	}

	public void setInfouid(String infouid) {
		this.infouid = infouid;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsAccurate() {
		return isAccurate;
	}

	public void setIsAccurate(Boolean isAccurate) {
		this.isAccurate = isAccurate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
