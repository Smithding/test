package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

import java.io.Serializable;
import java.util.Date;

/**
 * 酒店列表查询参数
 */
public class QueryHotelListParam extends BaseQueryParam implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String AreaID;		//地区id
	
	private String City;		//城市名称
	
	private Date CheckInDate;	//入住时间(2018-04-08)
	
	private Date CheckOutDate;//离店时间(2018-04-09)
	
	private String BrandID;		//品牌ID
	
	private String ZoneCode;	//商圈ID
	
	private String TrainStation;//火车站
	
	private String Airport;		//机场
	
	private String MetroStation;//地铁站
	
	private String ViewSpot;	//景点
	
	private String FacilityID;	//设施服务ID
	
	private int Minprice;	//起始价
	
	private int Maxprice;	//最大价格
	
	private String HotelPrice;	//酒店价格
	
	private String StarType;	//星级名称
	
	private String SortType;	//排序方式 1 升序, 2降序
	
	private int PageIndex;	//页面下标
	
	private int PageCount;	//分页数
	
	private int IsConfirm;	//立即确认
	
	private int Isadvance;	//预付优惠
	
	private int Isdiscount;	//促销
	
	private String Keyword;		//关键字
	
	private String HotelItude;	//酒店经纬度   (经度|纬度,经度|纬度,)
	
	private Double Distance;	//距离公里

	public String getAreaID() {
		return AreaID;
	}

	public void setAreaID(String areaID) {
		AreaID = areaID;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public Date getCheckInDate() {
		return CheckInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		CheckInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return CheckOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		CheckOutDate = checkOutDate;
	}

	public String getBrandID() {
		return BrandID;
	}

	public void setBrandID(String brandID) {
		BrandID = brandID;
	}

	public String getZoneCode() {
		return ZoneCode;
	}

	public void setZoneCode(String zoneCode) {
		ZoneCode = zoneCode;
	}

	public String getTrainStation() {
		return TrainStation;
	}

	public void setTrainStation(String trainStation) {
		TrainStation = trainStation;
	}

	public String getAirport() {
		return Airport;
	}

	public void setAirport(String airport) {
		Airport = airport;
	}

	public String getMetroStation() {
		return MetroStation;
	}

	public void setMetroStation(String metroStation) {
		MetroStation = metroStation;
	}

	public String getViewSpot() {
		return ViewSpot;
	}

	public void setViewSpot(String viewSpot) {
		ViewSpot = viewSpot;
	}

	public String getFacilityID() {
		return FacilityID;
	}

	public void setFacilityID(String facilityID) {
		FacilityID = facilityID;
	}

	public int getMinprice() {
		return Minprice;
	}

	public void setMinprice(int minprice) {
		Minprice = minprice;
	}

	public int getMaxprice() {
		return Maxprice;
	}

	public void setMaxprice(int maxprice) {
		Maxprice = maxprice;
	}

	public String getHotelPrice() {
		return HotelPrice;
	}

	public void setHotelPrice(String hotelPrice) {
		HotelPrice = hotelPrice;
	}

	public String getStarType() {
		return StarType;
	}

	public void setStarType(String starType) {
		StarType = starType;
	}

	public String getSortType() {
		return SortType;
	}

	public void setSortType(String sortType) {
		SortType = sortType;
	}

	public int getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(int pageIndex) {
		PageIndex = pageIndex;
	}

	public int getPageCount() {
		return PageCount;
	}

	public void setPageCount(int pageCount) {
		PageCount = pageCount;
	}

	public int getIsConfirm() {
		return IsConfirm;
	}

	public void setIsConfirm(int isConfirm) {
		IsConfirm = isConfirm;
	}

	public int getIsadvance() {
		return Isadvance;
	}

	public void setIsadvance(int isadvance) {
		Isadvance = isadvance;
	}

	public int getIsdiscount() {
		return Isdiscount;
	}

	public void setIsdiscount(int isdiscount) {
		Isdiscount = isdiscount;
	}

	public String getKeyword() {
		return Keyword;
	}

	public void setKeyword(String keyword) {
		Keyword = keyword;
	}

	public String getHotelItude() {
		return HotelItude;
	}

	public void setHotelItude(String hotelItude) {
		HotelItude = hotelItude;
	}

	public Double getDistance() {
		return Distance;
	}

	public void setDistance(Double distance) {
		Distance = distance;
	}
	
}
