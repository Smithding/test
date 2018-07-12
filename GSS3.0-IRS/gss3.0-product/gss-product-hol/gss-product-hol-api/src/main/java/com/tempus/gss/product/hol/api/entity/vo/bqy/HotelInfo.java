package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店信息
 */
public class HotelInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;					//主键ID,等同于酒店ID
	
	private String provinceCode;     		//省份编号
	
	private String provinceName;     	//省份名称
	
	@JSONField(name="CityCode")             
	private String cityCode;                //酒店城市ID
	                                        
	@JSONField(name="CityName")             
	private String cityName;                //城市名
	
	private String countyCode;				//县区编号
	
	private String countyName;			//县区名称
	
	private List<ImageList> hotelImageList; //酒店图片集合

	@JSONField(name="HotelId")
	private long hotelId;					//酒店ID
	                                        
	@JSONField(name="HotelName")            
	private String hotelName;               //酒店中文名
	                                        
	@JSONField(name="HotelEngName")         
	private String hotelEngName;            //酒店中文名
	                                        
	@JSONField(name="Address")              
	private String address;                 //酒店地址
	                                        
	@JSONField(name="Latitude")             
	private BigDecimal latitude;            //纬度
	                                        
	@JSONField(name="Longitude")            
	private BigDecimal longitude;           //经度
	                                        
	@JSONField(name="RoadCross")            
	private String roadCross;               //就近地址
	                                        
	@JSONField(name="HotelStar")            
	private String hotelStar;               //酒店星级
	                                        
	@JSONField(name="HotelBrandId")         
	private int hotelBrandId;               //酒店品牌ID
	                                        
	@JSONField(name="HotelBrandName")       
	private String hotelBrandName;          //酒店品牌名称
	                                        
	@JSONField(name="mobile")               
	private String Mobile;                  //酒店联系电话
	                                        
	@JSONField(name="WhenBuilt")            
	private String whenBuilt;               //建造时间
	
	@JSONField(name="LastUpdated")
	private String lastUpdated;				//最后修改时间
	
	@JSONField(name="RoomQuantity")         
	private int roomQuantity;               //房间数量
	                                        
	@JSONField(name="FloorHight")           
	private String floorHight;              //楼高
	                                        
	@JSONField(name="Description")          
	private String description;             //酒店描述
	                                        
	@JSONField(name="AgencyAccount")
	private String agencyAccount;			//代理人
	
	@JSONField(name="Areaid")               
	private String areaid;                  //地区id
	
	@JSONField(name="UpdateAdminID")
	private String updateAdminID;			//修改人ID
	                                        
	@JSONField(name="IsRecommend")
	private int isRecommend;				//是否首推酒店（1、首推酒店）
	
	@JSONField(name="EnterpriseUserId")
	private String enterpriseUserId;		//代理ID
	
	@JSONField(name="Sourcehotelcode")
	private String sourcehotelcode;			//酒店数据源id
	                                        
	@JSONField(name="HotelNamePY")          
	private String hotelNamePY;             //酒店拼音
	                                        
	@JSONField(name="SourceHotelCode")      
	private String sourceHotelCode;         //酒店ID
	                                        
	@JSONField(name="SourceID")             
	private int sourceID;                //来源渠道ID
	
	@JSONField(name="CheckInTime")          
	private String checkInTime;             //入住时间
	                                        
	@JSONField(name="CheckOutTime")         
	private String checkOutTime;            //离店时间
	
	@JSONField(name="ServiceCode")
	private String serviceCode;				//设施服务
	
	@JSONField(name="LowPrice")
	private BigDecimal lowPrice;			//房型最低价
	
	private String supplierNo;				//供应商编号
	
	private String latestUpdateTime;		//数据最近更新时间
	
	private Integer saleStatus;				//酒店是否可售状态，默认为1可售， 0位不可售
	
	/**
	 * 坐标类型
	 */
	@JSONField(name="CoordinatesType")
	private Integer coordinatesType;
	/**
	 * 酒店头图
	 */
	@JSONField(name="ImgURL")
	private String imgURL;
	
	//图片类型
	@JSONField(name="ImgType")
	private Integer imgType;

	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelEngName() {
		return hotelEngName;
	}

	public void setHotelEngName(String hotelEngName) {
		this.hotelEngName = hotelEngName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getRoadCross() {
		return roadCross;
	}

	public void setRoadCross(String roadCross) {
		this.roadCross = roadCross;
	}

	public String getHotelStar() {
		return hotelStar;
	}

	public void setHotelStar(String hotelStar) {
		this.hotelStar = hotelStar;
	}

	public int getHotelBrandId() {
		return hotelBrandId;
	}

	public void setHotelBrandId(int hotelBrandId) {
		this.hotelBrandId = hotelBrandId;
	}

	public String getHotelBrandName() {
		return hotelBrandName;
	}

	public void setHotelBrandName(String hotelBrandName) {
		this.hotelBrandName = hotelBrandName;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getWhenBuilt() {
		return whenBuilt;
	}

	public void setWhenBuilt(String whenBuilt) {
		this.whenBuilt = whenBuilt;
	}

	public String getFloorHight() {
		return floorHight;
	}

	public void setFloorHight(String floorHight) {
		this.floorHight = floorHight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getHotelNamePY() {
		return hotelNamePY;
	}

	public void setHotelNamePY(String hotelNamePY) {
		this.hotelNamePY = hotelNamePY;
	}

	public String getSourceHotelCode() {
		return sourceHotelCode;
	}

	public void setSourceHotelCode(String sourceHotelCode) {
		this.sourceHotelCode = sourceHotelCode;
	}

	public int getSourceID() {
		return sourceID;
	}

	public void setSourceID(int sourceID) {
		this.sourceID = sourceID;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public List<ImageList> getHotelImageList() {
		return hotelImageList;
	}

	public void setHotelImageList(List<ImageList> hotelImageList) {
		this.hotelImageList = hotelImageList;
	}

	public int getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(int isRecommend) {
		this.isRecommend = isRecommend;
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

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getAgencyAccount() {
		return agencyAccount;
	}

	public void setAgencyAccount(String agencyAccount) {
		this.agencyAccount = agencyAccount;
	}

	public String getUpdateAdminID() {
		return updateAdminID;
	}

	public void setUpdateAdminID(String updateAdminID) {
		this.updateAdminID = updateAdminID;
	}

	public String getEnterpriseUserId() {
		return enterpriseUserId;
	}

	public void setEnterpriseUserId(String enterpriseUserId) {
		this.enterpriseUserId = enterpriseUserId;
	}

	public String getSourcehotelcode() {
		return sourcehotelcode;
	}

	public void setSourcehotelcode(String sourcehotelcode) {
		this.sourcehotelcode = sourcehotelcode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public BigDecimal getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(BigDecimal lowPrice) {
		this.lowPrice = lowPrice;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public Integer getCoordinatesType() {
		return coordinatesType;
	}

	public void setCoordinatesType(Integer coordinatesType) {
		this.coordinatesType = coordinatesType;
	}

	public int getRoomQuantity() {
		return roomQuantity;
	}

	public void setRoomQuantity(int roomQuantity) {
		this.roomQuantity = roomQuantity;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public Integer getImgType() {
		return imgType;
	}

	public void setImgType(Integer imgType) {
		this.imgType = imgType;
	}
	
}
