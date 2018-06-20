package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBrandInfo;

public class HolMidBaseInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 酒店id
	 */
	private Long resId;
	
	/**
	 * 酒店名称
	 */
	private String resName;
	
	private List<ResNameSum> resNameSum;
	
	/**
	 * 省份名称
	 */
	private String provName;
	
	/**
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 酒店全球位置（0：未知；1：大陆；2：港澳台；3：国际）
	 */
	private Integer isInter;
	
	/**
	 * 品牌信息
	 */
	private ResBrandInfo brandInfo;
	
	/**
	 * 酒店地址
	 */
	private String address;
	/**
	 * 酒店电话
	 */
	private String resPhone;
	/**
	 * 酒店介绍
	 */
	private String intro;
	
	/**
	 * 交通指南
	 */
	private String travelGuide;
	/**
	 * 酒店等级
	 */
	private String resGrade;
	
	/**
	 * 坐标类型（1-百度坐标 2-图吧坐标 3-谷歌坐标）
	 */
	private Integer latLonType;

	/**
	 * 第一个经度lon, 第二个纬度lat
	 */
	@GeoSpatialIndexed
	private Double[] resPosition;
	
	/**
	 * 国家名称（默认为中国）
	 */
	private String countryName;
	
	/**
	 * 酒店头图
	 */
	//private List<ImgInfo> imgInfoList;
	private String titleImg;
	
	/**
	 * 酒店最低价
	 */
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
	
	/**
	 * 1: TC, 2: BQY, 3: TY, 0: All, 
	 */
	private Integer resType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	
	public List<ResNameSum> getResNameSum() {
		return resNameSum;
	}

	public void setResNameSum(List<ResNameSum> resNameSum) {
		this.resNameSum = resNameSum;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
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

	public Integer getLatLonType() {
		return latLonType;
	}

	public void setLatLonType(Integer latLonType) {
		this.latLonType = latLonType;
	}

	public Double[] getResPosition() {
		return resPosition;
	}

	public void setResPosition(Double[] resPosition) {
		this.resPosition = resPosition;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
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

	public Long getBookTimes() {
		return bookTimes;
	}

	public void setBookTimes(Long bookTimes) {
		this.bookTimes = bookTimes;
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

	public Integer getResType() {
		return resType;
	}

	public void setResType(Integer resType) {
		this.resType = resType;
	}
	
	
}
