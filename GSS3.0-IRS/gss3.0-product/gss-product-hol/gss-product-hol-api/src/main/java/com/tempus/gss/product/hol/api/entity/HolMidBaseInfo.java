package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBrandInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResGPSInfo;

public class HolMidBaseInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID同酒店id
	 */
	@JSONField(name = "Id")
	private String id;
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
	 * 省份名称
	 */
	@JSONField(name = "ProvName")
	private String provName;
	/**
	 * 城市id
	 */
	@JSONField(name = "CityId")
	private Integer cityId;
	/**
	 * 城市名称
	 */
	@JSONField(name = "CityName")
	private String cityName;
	
	/**
	 * 酒店全球位置（0：未知；1：大陆；2：港澳台；3：国际）
	 */
	@JSONField(name = "IsInter")
	private Integer isInter;
	
	/**
	 * 品牌信息
	 */
	@JSONField(name = "BrandInfo")
	private ResBrandInfo brandInfo;
	
	/**
	 * 酒店地址
	 */
	@JSONField(name = "Address")
	private String address;
	/**
	 * 酒店电话
	 */
	@JSONField(name = "ResPhone")
	private String resPhone;
	/**
	 * 酒店介绍（html 字符串）
	 */
	@JSONField(name = "Intro")
	private String intro;
	
	/**
	 * 预订须知（html 字符串）
	 */
	@JSONField(name = "TravelGuide")
	private String travelGuide;
	/**
	 * 酒店等级
	 */
	@JSONField(name = "ResGrade")
	private String resGrade;
	
	/**
	 * 交通指南（html 字符串）
	 */
	@JSONField(name = "TrafficGuid")
	private String trafficGuid;
	/**
	 * 酒店坐标信息
	 */
	@JSONField(name = "ResGPS")
	private List<ResGPSInfo> resGPS;
	
	/**
	 * 国家
	 */
	@JSONField(name = "CountryId")
	private Integer countryId;
	/**
	 * 国家名称（默认为中国）
	 */
	@JSONField(name = "CountryName")
	private String countryName;
	/**
	 * 一句话介绍
	 */
	@JSONField(name = "ShortIntro")
	private String shortIntro;
	
	/**
	 * 图片列表
	 */
	@JSONField(name = "ImgInfoList")
	private List<ImgInfo> imgInfoList;
	
	/**
	 * 酒店最低价
	 */
	@JSONField(name = "MinPrice")
	private Integer minPrice;
	
	/**
	 * 供应商编号
	 */
	private String supplierNo;
	/**
	 * 数据最近更新时间
	 */
	private String latestUpdateTime;
	/**
	 * 酒店是否可售状态，默认为1可售， 0位不可售，此状态与同程无关
	 */
	private Integer saleStatus;
	/**
	 * 酒店预定次数
	 */
	private Long bookTimes;
	
	/**
	 * 预定备注(预留字段)
	 */
	private String bookRemark;
	
	/**
	 * 预定信息(预留字段)
	 */
	private String bookInfo;
	
	private List<Object> bookItems;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getIsInter() {
		return isInter;
	}

	public void setIsInter(Integer isInter) {
		this.isInter = isInter;
	}

	public ResBrandInfo getBrandInfo() {
		return brandInfo;
	}

	public void setBrandInfo(ResBrandInfo brandInfo) {
		this.brandInfo = brandInfo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getResPhone() {
		return resPhone;
	}

	public void setResPhone(String resPhone) {
		this.resPhone = resPhone;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getTravelGuide() {
		return travelGuide;
	}

	public void setTravelGuide(String travelGuide) {
		this.travelGuide = travelGuide;
	}

	public String getResGrade() {
		return resGrade;
	}

	public void setResGrade(String resGrade) {
		this.resGrade = resGrade;
	}

	public String getTrafficGuid() {
		return trafficGuid;
	}

	public void setTrafficGuid(String trafficGuid) {
		this.trafficGuid = trafficGuid;
	}

	public List<ResGPSInfo> getResGPS() {
		return resGPS;
	}

	public void setResGPS(List<ResGPSInfo> resGPS) {
		this.resGPS = resGPS;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getShortIntro() {
		return shortIntro;
	}

	public void setShortIntro(String shortIntro) {
		this.shortIntro = shortIntro;
	}

	public List<ImgInfo> getImgInfoList() {
		return imgInfoList;
	}

	public void setImgInfoList(List<ImgInfo> imgInfoList) {
		this.imgInfoList = imgInfoList;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getLatestUpdateTime() {
		return latestUpdateTime;
	}

	public void setLatestUpdateTime(String latestUpdateTime) {
		this.latestUpdateTime = latestUpdateTime;
	}

	public Integer getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getBookRemark() {
		return bookRemark;
	}

	public void setBookRemark(String bookRemark) {
		this.bookRemark = bookRemark;
	}

	public String getBookInfo() {
		return bookInfo;
	}

	public void setBookInfo(String bookInfo) {
		this.bookInfo = bookInfo;
	}

	public Long getBookTimes() {
		return bookTimes;
	}

	public void setBookTimes(Long bookTimes) {
		this.bookTimes = bookTimes;
	}

	public List<Object> getBookItems() {
		return bookItems;
	}

	public void setBookItems(List<Object> bookItems) {
		this.bookItems = bookItems;
	}
	
}
