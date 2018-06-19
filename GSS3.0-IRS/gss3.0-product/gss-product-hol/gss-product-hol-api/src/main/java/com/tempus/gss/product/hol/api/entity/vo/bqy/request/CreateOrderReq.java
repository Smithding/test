package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

public class CreateOrderReq implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="BrandId")
	private Long brandId;	
	
	@JSONField(name="EnterpriseUserId")
	private Long enterpriseUserId;
	
	@JSONField(name="IntensityType")
	private Integer intensityType;
	
	@JSONField(name="IsInstantConfirmTxt")
	private String isInstantConfirmTxt;
	
	@JSONField(name="BookingUserId")
	private String bookingUserId;
	
	@JSONField(name="ChannelType")
	private Integer channelType;
	
	@JSONField(name="OrderLink")
	private String OrderLink;
	
	@JSONField(name="Mobile")
	private String mobile;
	
	@JSONField(name="Email")
	private String email;
	
	@JSONField(name="RuleId")
	private Integer ruleId;
	
	@JSONField(name="OrderAttribute")
	private Integer orderAttribute;
	
	@JSONField(name="BillInfo")
	private String billInfo;
	
	@JSONField(name="AuditStatus")
	private Integer auditStatus;
	
	@JSONField(name="Addressee")
	private String addressee;
	
	@JSONField(name="Phone")
	private String phone; 
	
	@JSONField(name="PolicyId")
	private Integer policyId;
	
	@JSONField(name="PriceType")
	private Integer priceType;		//金钱类型
	
	@JSONField(name="AgentId")
	private Long agentId;			//Int64	代理人Id
	
	@JSONField(name="Token")
	private String token;			//string	请求Token
	
	@JSONField(name="SupplierId")
	private String supplierId;		//string	供应商ID
	
	@JSONField(name="ProductId")
	private String productId;		//String	产品ID
	
	@JSONField(name="ProductName")
	private String productName;		//String	产品名
	
	@JSONField(name="CheckInTime")
	private String checkInTime;		//DateTime	入住时间
	
	@JSONField(name="CheckOutTime")
	private String checkOutTime;	//DateTime	离店时间
	
	@JSONField(name="LateArrivalTime")
	private String lateArrivalTime;	//string	最晚到店时间
	
	@JSONField(name="Area")
	private String area;			//String	面积
	
	@JSONField(name="Broadband")
	private String broadband;		//String	宽带
	
	@JSONField(name="Floor")
	private String floor;			//String	楼层
	
	@JSONField(name="UnitPrice")
	private BigDecimal unitPrice;	//Decimal	酒店单价
	
	@JSONField(name="Passengers")
	private String passengers;		//String	入住人，以“,”拼接
	
	@JSONField(name="BookNumber")
	private int bookNumber;			//int	预订间数
	
	@JSONField(name="RatePlanCategory")
	private String ratePlanCategory;//String	预订检查类型,501,502
	
	@JSONField(name="CancelPenaltyStart")
	private String cancelPenaltyStart;	//String	Start属性：取消开始时间
	
	@JSONField(name="HotelId")
	private Long hotelId;			//long	酒店ID
	
	@JSONField(name="HotelName")
	private String hotelName;		//String	酒店名称
	
	@JSONField(name="HotelRoomId")
	private Long hotelRoomId;		//long	房型Id
	
	@JSONField(name="HotelRoomName")
	private String hotelRoomName;	//String	酒店房型
	
	@JSONField(name="Breakfast")
	private String breakfast;		//String	早餐
	
	@JSONField(name="AddUnitPrice")
	private BigDecimal addUnitPrice;//Decimal	加床单价
	
	@JSONField(name="Address")
	private String address;			//String	酒店地址
	
	@JSONField(name="CityId")
	private Integer cityId;			//int	酒店所属城市Id
	
	@JSONField(name="CityName")
	private String cityName;		//String	酒店所属城市Name
	
	@JSONField(name="Country")
	private String country;			//String	酒店所在国家
	
	@JSONField(name="Province")
	private String province;		//String	酒店所在省份
	
	@JSONField(name="Remark")
	private String remark;			//String	备注
	
	@JSONField(name="DiscountIntensity")
	private Integer discountIntensity;//int	优惠类型
	
	@JSONField(name="BedType")
	private String bedType;			//String	床型
	
	@JSONField(name="HotelMobile")
	private String hotelMobile;		//string	酒店联系电话
	
	@JSONField(name="CancelNotice")
	private String cancelNotice;	//string	取消政策
	
	@JSONField(name="IsInstantConfirm")
	private Boolean isInstantConfirm;//bool	是否即时确认
	
	@JSONField(name="Source")
	private String source;			//string	订单来源

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getLateArrivalTime() {
		return lateArrivalTime;
	}

	public void setLateArrivalTime(String lateArrivalTime) {
		this.lateArrivalTime = lateArrivalTime;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getBroadband() {
		return broadband;
	}

	public void setBroadband(String broadband) {
		this.broadband = broadband;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getPassengers() {
		return passengers;
	}

	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}

	public int getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(int bookNumber) {
		this.bookNumber = bookNumber;
	}

	public String getRatePlanCategory() {
		return ratePlanCategory;
	}

	public void setRatePlanCategory(String ratePlanCategory) {
		this.ratePlanCategory = ratePlanCategory;
	}

	public String getCancelPenaltyStart() {
		return cancelPenaltyStart;
	}

	public void setCancelPenaltyStart(String cancelPenaltyStart) {
		this.cancelPenaltyStart = cancelPenaltyStart;
	}

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Long getHotelRoomId() {
		return hotelRoomId;
	}

	public void setHotelRoomId(Long hotelRoomId) {
		this.hotelRoomId = hotelRoomId;
	}

	public String getHotelRoomName() {
		return hotelRoomName;
	}

	public void setHotelRoomName(String hotelRoomName) {
		this.hotelRoomName = hotelRoomName;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public BigDecimal getAddUnitPrice() {
		return addUnitPrice;
	}

	public void setAddUnitPrice(BigDecimal addUnitPrice) {
		this.addUnitPrice = addUnitPrice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDiscountIntensity() {
		return discountIntensity;
	}

	public void setDiscountIntensity(Integer discountIntensity) {
		this.discountIntensity = discountIntensity;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getHotelMobile() {
		return hotelMobile;
	}

	public void setHotelMobile(String hotelMobile) {
		this.hotelMobile = hotelMobile;
	}

	public String getCancelNotice() {
		return cancelNotice;
	}

	public void setCancelNotice(String cancelNotice) {
		this.cancelNotice = cancelNotice;
	}

	public Boolean getIsInstantConfirm() {
		return isInstantConfirm;
	}

	public void setIsInstantConfirm(Boolean isInstantConfirm) {
		this.isInstantConfirm = isInstantConfirm;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getEnterpriseUserId() {
		return enterpriseUserId;
	}

	public void setEnterpriseUserId(Long enterpriseUserId) {
		this.enterpriseUserId = enterpriseUserId;
	}

	public Integer getIntensityType() {
		return intensityType;
	}

	public void setIntensityType(Integer intensityType) {
		this.intensityType = intensityType;
	}

	public String getIsInstantConfirmTxt() {
		return isInstantConfirmTxt;
	}

	public void setIsInstantConfirmTxt(String isInstantConfirmTxt) {
		this.isInstantConfirmTxt = isInstantConfirmTxt;
	}

	public String getBookingUserId() {
		return bookingUserId;
	}

	public void setBookingUserId(String bookingUserId) {
		this.bookingUserId = bookingUserId;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public String getOrderLink() {
		return OrderLink;
	}

	public void setOrderLink(String orderLink) {
		OrderLink = orderLink;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getOrderAttribute() {
		return orderAttribute;
	}

	public void setOrderAttribute(Integer orderAttribute) {
		this.orderAttribute = orderAttribute;
	}

	public String getBillInfo() {
		return billInfo;
	}

	public void setBillInfo(String billInfo) {
		this.billInfo = billInfo;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}
	
}
