package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.product.hol.api.util.DateUtil;

/**
 * 酒店基本信息
 * @author kai.yang
 *
 */
public class ResBaseInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID同酒店id
	 */
	@JSONField(name = "Id")
	private Long id;
	
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
	 * 洲id
	 */
	@JSONField(name = "ContId")
	private String contId;
	/**
	 * 洲名称
	 */
	@JSONField(name = "ContName")
	private String contName;
	/**
	 * 省份id
	 */
	@JSONField(name = "ProvId")
	private Integer provId;
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
	 * 区/县id
	 */
	@JSONField(name = "SectionId")
	private Integer sectionId;
	/**
	 * 区/县名称
	 */
	@JSONField(name = "SectionName")
	private String sectionName;
	/**
	 * 乡镇id
	 */
	@JSONField(name = "TownId")
	private String townId;
	/**
	 * 乡镇名称
	 */
	@JSONField(name = "TownName")
	private String townName;
	/**
	 * 品牌信息
	 */
	@JSONField(name = "BrandInfo")
	private ResBrandInfo brandInfo;
	/**
	 * 酒店全球位置（0：未知；1：大陆；2：港澳台；3：国际）
	 */
	@JSONField(name = "IsInter")
	private Integer isInter;
	/**
	 * 酒店邮编
	 */
	@JSONField(name = "PostCode")
	private String postCode;
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
	 * 资源等级 id
	 */
	@JSONField(name = "ResGradeId")
	private String resGradeId;
	/**
	 * 酒店等级重新定义排序
	 */
	/*@JSONField(name = "ResGradeIdTarget")
	private String resGradeIdTarget;*/
	/**
	 * 酒店：营业时间
	 */
	@JSONField(name = "OpenTime")
	private String openTime;
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
	
	@GeoSpatialIndexed
	@JSONField(serialize = false)
	private Double[] resGpsLocation;
	/**
	 * 酒店设施
	 */
	@JSONField(name = "ResFacilities")
	private List<FacilityServices> resFacilities;
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
	 * 商圈信息集合
	 */
	@JSONField(name = "BusinessSectionInfoList")
	private List<BusinessSection> businessSectionInfoList;
	/**
	 * 装修时间
	 */
	//@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "RenovationDate")
	private String renovationDate;
	/**
	 * 开业时间
	 */
	//@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "EstablishmentDate")
	private String establishmentDate;
	/*
	@JSONField(name = "establishmentDateFormat")
	private Date establishmentDateFormat;*/
	/**
	 * 建造时间
	 */
	//@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "WhenBuilt")
	private String whenBuilt;
	/**
	 * 入 住 和 离 店 在TravelGuide 中已包含
	 */
	@JSONField(name = "ArrivalAndDeparture")
	private String arrivalAndDeparture;
	/**
	 * 取消政策说明不同类型的客房附带不同的取消预订和
	 * 预先付费政策 选择上述客房时，请参阅“客房政策”。有和没有一样
	 */
	@JSONField(name = "CancelDescription")
	private String cancelDescription;
	/**
	 * 押金/预付款说明
	 */
	@JSONField(name = "DepositAndPrepaid")
	private String depositAndPrepaid;
	/**
	 * 宠物说明
	 */
	@JSONField(name = "PetDescription")
	private String petDescription;
	/**
	 * 儿童政策说明
	 */
	@JSONField(name = "ChildDescription")
	private String childDescription;
	/**
	 * 可支持的信用卡类型
	 * （0：默认值 未知，
	 * 2^0：国内发行银联卡，
	 * 2^1：万事达 Master，
	 * 2^2：威士VISA，
	 * 2^3：运通 AMEX，
	 * 2^4：大莱 
	 * 2^5 JCB 卡
	 */
	@JSONField(name = "CreditCards")
	private Long creditCards;
	/**
	 * 具体的支持的银行卡的名称
	 */
	@JSONField(name = "CreditCardsTarget")
	private List<String> creditCardsTarget;
	/**
	 * 定位
	 */
	@JSONField(name = "Location")
	private String location;
	/**
	 * 主题标签
	 */
	@JSONField(name = "HotelThemeRelationInfo")
	private List<ResThemeRelation> hotelThemeRelationInfo;
	/**
	 * 交通信息
	 */
	@JSONField(name = "HotelTrafficInfo")
	private List<ResTrafficInfo> hotelTrafficInfo;
	/**
	 * 房型列表
	 */
	/*@JSONField(name = "ResProBaseInfoList")
	private List<ResProBaseInfo> resProBaseInfoList;*/
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
	 * 酒店平均价
	 */
	@JSONField(name = "ResCommonPrice")
	private Integer resCommonPrice;
	/**
	 * 房型对应的列表
	 *//*
	@JSONField(name = "ProMap")
	private Map<String, List<ResProBaseInfo>> proMap;*/
	/**
	 * 房型对应的列表
	 */
	@JSONField(name = "ProDetails")
	private List<ProDetails> proDetails;
	/**
	 * 当前酒店的总房间数
	 */
	@JSONField(name = "SumPros")
	private Integer sumPros;
	/**
	 * 供应商编号
	 */
	@JSONField(name = "SupplierNo")
	private String supplierNo;
	/**
	 * 数据最近更新时间
	 */
	private String latestUpdateTime;
	/**
	 * 酒店是否可售状态，默认为1可售， 0位不可售，此状态与同程无关
	 */
	private Integer saleStatus;
	
	private String imgUrl;
	
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
	public String getContId() {
		return contId;
	}
	public void setContId(String contId) {
		this.contId = contId;
	}
	public String getContName() {
		return contName;
	}
	public void setContName(String contName) {
		this.contName = contName;
	}
	public Integer getProvId() {
		return provId;
	}
	public void setProvId(Integer provId) {
		this.provId = provId;
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
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getTownId() {
		return townId;
	}
	public void setTownId(String townId) {
		this.townId = townId;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public ResBrandInfo getBrandInfo() {
		return brandInfo;
	}
	public void setBrandInfo(ResBrandInfo brandInfo) {
		this.brandInfo = brandInfo;
	}
	public Integer getIsInter() {
		return isInter;
	}
	public void setIsInter(Integer isInter) {
		this.isInter = isInter;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
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
	public String getResGradeId() {
		return resGradeId;
	}
	public void setResGradeId(String resGradeId) {
		this.resGradeId = resGradeId;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
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
	public List<FacilityServices> getResFacilities() {
		return resFacilities;
	}
	public void setResFacilities(List<FacilityServices> resFacilities) {
		this.resFacilities = resFacilities;
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
	@JSONField(deserialize=false)
	public void setShortIntro(String shortIntro) {
		this.shortIntro = shortIntro;
	}
	public List<BusinessSection> getBusinessSectionInfoList() {
		return businessSectionInfoList;
	}
	public void setBusinessSectionInfoList(List<BusinessSection> businessSectionInfoList) {
		this.businessSectionInfoList = businessSectionInfoList;
	}
	
	public String getRenovationDate() {
		return renovationDate;
	}
	public void setRenovationDate(String renovationDate) {
		renovationDate= DateUtil.stringToStrDate(renovationDate);
		this.renovationDate = renovationDate;
	}
	public String getEstablishmentDate() {
		return establishmentDate;
	}
	public void setEstablishmentDate(String establishmentDate) {
		establishmentDate= DateUtil.stringToStrDate(establishmentDate);
		this.establishmentDate = establishmentDate;
		//this.establishmentDate = DateUtil.stringToStrDate(establishmentDate);
		//this.establishmentDateFormat =DateUtil.stringToDateFormat(this.establishmentDate);
	}
	public String getWhenBuilt() {
		return whenBuilt;
	}
	public void setWhenBuilt(String whenBuilt) {
		whenBuilt= DateUtil.stringToStrDate(whenBuilt);
		this.whenBuilt = whenBuilt;
	}
	public String getArrivalAndDeparture() {
		return arrivalAndDeparture;
	}
	public void setArrivalAndDeparture(String arrivalAndDeparture) {
		this.arrivalAndDeparture = arrivalAndDeparture;
	}
	public String getCancelDescription() {
		return cancelDescription;
	}
	public void setCancelDescription(String cancelDescription) {
		this.cancelDescription = cancelDescription;
	}
	public String getDepositAndPrepaid() {
		return depositAndPrepaid;
	}
	public void setDepositAndPrepaid(String depositAndPrepaid) {
		this.depositAndPrepaid = depositAndPrepaid;
	}
	public String getPetDescription() {
		return petDescription;
	}
	public void setPetDescription(String petDescription) {
		this.petDescription = petDescription;
	}
	public String getChildDescription() {
		return childDescription;
	}
	public void setChildDescription(String childDescription) {
		this.childDescription = childDescription;
	}
	public Long getCreditCards() {
		return creditCards;
	}
	public void setCreditCards(Long creditCards) {
		this.creditCards = creditCards;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<ResThemeRelation> getHotelThemeRelationInfo() {
		return hotelThemeRelationInfo;
	}
	public void setHotelThemeRelationInfo(List<ResThemeRelation> hotelThemeRelationInfo) {
		this.hotelThemeRelationInfo = hotelThemeRelationInfo;
	}
	public List<ResTrafficInfo> getHotelTrafficInfo() {
		return hotelTrafficInfo;
	}
	public void setHotelTrafficInfo(List<ResTrafficInfo> hotelTrafficInfo) {
		this.hotelTrafficInfo = hotelTrafficInfo;
	}
	/*public Date getEstablishmentDateFormat() {
		return DateUtil.stringToDateFormat(this.establishmentDate);
	}
	public void setEstablishmentDateFormat(Date establishmentDateFormat) {
		this.establishmentDateFormat = establishmentDateFormat;
	}*/
	/*public List<ResProBaseInfo> getResProBaseInfoList() {
		return resProBaseInfoList;
	}
	public void setResProBaseInfoList(List<ResProBaseInfo> resProBaseInfoList) {
		this.resProBaseInfoList = resProBaseInfoList;
	}*/
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
	public List<ProDetails> getProDetails() {
		return proDetails;
	}
	public void setProDetails(List<ProDetails> proDetails) {
		this.proDetails = proDetails;
	}
	public Integer getSumPros() {
		return sumPros;
	}
	public void setSumPros(Integer sumPros) {
		this.sumPros = sumPros;
	}
	public List<String> getCreditCardsTarget() {
		return creditCardsTarget;
	}
	public void setCreditCardsTarget(List<String> creditCardsTarget) {
		this.creditCardsTarget = creditCardsTarget;
	}
	public Integer getResCommonPrice() {
		return resCommonPrice;
	}
	public void setResCommonPrice(Integer resCommonPrice) {
		this.resCommonPrice = resCommonPrice;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	/*public String getResGradeIdTarget() {
		return resGradeIdTarget;
	}
	public void setResGradeIdTarget(String resGradeIdTarget) {
		this.resGradeIdTarget = resGradeIdTarget;
	}*/
	public Integer getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Double[] getResGpsLocation() {
		return resGpsLocation;
	}
	public void setResGpsLocation(Double[] resGpsLocation) {
		this.resGpsLocation = resGpsLocation;
	}
	
}
