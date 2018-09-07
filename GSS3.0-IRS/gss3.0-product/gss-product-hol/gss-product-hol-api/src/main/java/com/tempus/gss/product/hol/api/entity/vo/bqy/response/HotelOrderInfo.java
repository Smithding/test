package com.tempus.gss.product.hol.api.entity.vo.bqy.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 酒店订单详情
 *
 */
public class HotelOrderInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="HotelOrderId")
	private Long hotelOrderId;				//酒店订单ID
	
	@JSONField(name="OrderNumber")
	private Long orderNumber;				//总订单号
	
	@JSONField(name="OrderType")
	private Integer orderType;				//订单类型
	
	@JSONField(name="AddNumber")
	private Integer addNumber;				//加床数量
	
	@JSONField(name="Address")
	private String address;					//详细地址
	
	@JSONField(name="AddUnitPrice")
	private BigDecimal addUnitPrice;		//加床单价
	
	@JSONField(name="Area")
	private String area;					//面积
	
	@JSONField(name="AlipayNumber")
	private String alipayNumber;			//支付交易号
	
	@JSONField(name="ArriveDate")
	private Date arriveDate;				//到店时间
	
	@JSONField(name="BedType")
	private String bedType;					//床型
	
	@JSONField(name="BookingTime", format = "yyyy-MM-dd HH:mm:ss")
	private Date bookingTime;				//预定时间
	
	@JSONField(name="BookingUserId")
	private String bookingUserId;			//预订人
	
	@JSONField(name="BookNumber")
	private Integer bookNumber;				//预订间数
	
	@JSONField(name="BookingNotice")
	private String bookingNotice;			//预定须知
	
	@JSONField(name="Broadband")
	private String broadband;				//宽带
	
	@JSONField(name="Breakfast")
	private String breakfast;				//早餐
	
	@JSONField(name="CancelNotice")
	private String cancelNotice;			//取消政策
	
	@JSONField(name="CheckInDate", format = "yyyy-MM-dd HH:mm:ss")
	private Date checkInDate;				//入住时间
	
	@JSONField(name="CheckOutDate", format = "yyyy-MM-dd HH:mm:ss")
	private Date checkOutDate;				//离店时间
	
	@JSONField(name="CityId")
	private Long cityId;					//目的地城市Id
	
	@JSONField(name="CityName")
	private String cityName;				//目的城市Name
	
	@JSONField(name="Country")
	private String country;					//酒店所在国家
	
	@JSONField(name="DiscountIntensity")
	private BigDecimal discountIntensity;	//酒店优惠
	
	@JSONField(name="Email")
	private String email;					//订单联系人邮箱
	
	@JSONField(name="Floor")
	private String floor;					//楼层
	
	@JSONField(name="GuestName")
	private String guestName;				//入住人姓名
	
	@JSONField(name="GuestName")
	private Long hotelId;					//酒店ID
	
	@JSONField(name="HotelName")
	private String hotelName;				//酒店名称
	
	@JSONField(name="HotelProductId")
	private Long hotelProductId;			//酒店产品ID
	
	@JSONField(name="HotelRoomId")
	private Long hotelRoomId;				//房型Id
	
	@JSONField(name="HotelRoomName")
	private String hotelRoomName;			//酒店房型
	
	@JSONField(name="IntensityType")
	private Integer intensityType;			//优惠类型
	
	@JSONField(name="LeaveDate")
	private Date leaveDate;					//离店时间
	
	@JSONField(name="Mobile")
	private String mobile;					//订单联系人手机号码
	
	@JSONField(name="OrderLink")
	private String orderLink;				//订单联系人
	
	@JSONField(name="UserStatus")
	private Integer userStatus;				//用户状态
	
	@JSONField(name="PayPrice")
	private BigDecimal payPrice;			//支付金额
	
	@JSONField(name="PayStatus")
	private Integer payStatus;				//资金状态
	
	@JSONField(name="PayStatusStr")
	private String payStatusStr;			//资金状态说明
	
	@JSONField(name="ProductName")
	private String productName;				//产品名
	
	@JSONField(name="PayTime")
	private String payTime;					//支付时间
	
	@JSONField(name="PayType")
	private Integer payType;				//支付方式
	
	@JSONField(name="Province")
	private String province;				//酒店所在省份
	
	@JSONField(name="Special")
	private String special;					//特殊需求
	
	@JSONField(name="SupplierId")
	private String supplierId;				//供应商Id
	
	@JSONField(name="AgentId")
	private String agentId;					//代理人ID
	
	@JSONField(name="UnitPrice")
	private BigDecimal unitPrice;			//酒店单价
	
	@JSONField(name="RatePlanCategory")
	private String ratePlanCategory;		//酒店价格计划
	
	@JSONField(name="Remark")
	private String remark;					//备注
	
	@JSONField(name="Status")
	private Integer status;					//状态
	
	@JSONField(name="CreateTime")
	private Date createTime;				//创建时间
	
	@JSONField(name="Creator")
	private String creator;					//创建人
	
	@JSONField(name="Modifier")
	private String modifier;				//修改人
	
	@JSONField(name="DataChange_LastTime")
	private Date dataChange_LastTime;		//数据最后更新时间
	
	@JSONField(name="CancelTime")
	private String cancelTime;				//取消时间
	
	@JSONField(name="CancelPeople")
	private String cancelPeople;			//取消申请人
	
	@JSONField(name="RefundPrivatePrice")
	private BigDecimal refundPrivatePrice;	//退个人金额
	
	@JSONField(name="ReducePrice")
	private BigDecimal reducePrice;			//优惠金额
	
	@JSONField(name="GetSupplierId")
	private Long getSupplierId;				//获取子订单供应商ID

	@JSONField(name="CancelPenaltyStart")
	private String cancelPenaltyStart;		//取消时间

	public Long getHotelOrderId() {
		return hotelOrderId;
	}

	public void setHotelOrderId(Long hotelOrderId) {
		this.hotelOrderId = hotelOrderId;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getAddNumber() {
		return addNumber;
	}

	public void setAddNumber(Integer addNumber) {
		this.addNumber = addNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAddUnitPrice() {
		return addUnitPrice;
	}

	public void setAddUnitPrice(BigDecimal addUnitPrice) {
		this.addUnitPrice = addUnitPrice;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAlipayNumber() {
		return alipayNumber;
	}

	public void setAlipayNumber(String alipayNumber) {
		this.alipayNumber = alipayNumber;
	}

	public Date getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public Date getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(Date bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getBookingUserId() {
		return bookingUserId;
	}

	public void setBookingUserId(String bookingUserId) {
		this.bookingUserId = bookingUserId;
	}

	public Integer getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(Integer bookNumber) {
		this.bookNumber = bookNumber;
	}

	public String getBookingNotice() {
		return bookingNotice;
	}

	public void setBookingNotice(String bookingNotice) {
		this.bookingNotice = bookingNotice;
	}

	public String getBroadband() {
		return broadband;
	}

	public void setBroadband(String broadband) {
		this.broadband = broadband;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public String getCancelNotice() {
		return cancelNotice;
	}

	public void setCancelNotice(String cancelNotice) {
		this.cancelNotice = cancelNotice;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
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

	public BigDecimal getDiscountIntensity() {
		return discountIntensity;
	}

	public void setDiscountIntensity(BigDecimal discountIntensity) {
		this.discountIntensity = discountIntensity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
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

	public Long getHotelProductId() {
		return hotelProductId;
	}

	public void setHotelProductId(Long hotelProductId) {
		this.hotelProductId = hotelProductId;
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

	public Integer getIntensityType() {
		return intensityType;
	}

	public void setIntensityType(Integer intensityType) {
		this.intensityType = intensityType;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrderLink() {
		return orderLink;
	}

	public void setOrderLink(String orderLink) {
		this.orderLink = orderLink;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public BigDecimal getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayStatusStr() {
		return payStatusStr;
	}

	public void setPayStatusStr(String payStatusStr) {
		this.payStatusStr = payStatusStr;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getRatePlanCategory() {
		return ratePlanCategory;
	}

	public void setRatePlanCategory(String ratePlanCategory) {
		this.ratePlanCategory = ratePlanCategory;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getDataChange_LastTime() {
		return dataChange_LastTime;
	}

	public void setDataChange_LastTime(Date dataChange_LastTime) {
		this.dataChange_LastTime = dataChange_LastTime;
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelPeople() {
		return cancelPeople;
	}

	public void setCancelPeople(String cancelPeople) {
		this.cancelPeople = cancelPeople;
	}

	public BigDecimal getRefundPrivatePrice() {
		return refundPrivatePrice;
	}

	public void setRefundPrivatePrice(BigDecimal refundPrivatePrice) {
		this.refundPrivatePrice = refundPrivatePrice;
	}

	public BigDecimal getReducePrice() {
		return reducePrice;
	}

	public void setReducePrice(BigDecimal reducePrice) {
		this.reducePrice = reducePrice;
	}

	public Long getGetSupplierId() {
		return getSupplierId;
	}

	public void setGetSupplierId(Long getSupplierId) {
		this.getSupplierId = getSupplierId;
	}

	public String getCancelPenaltyStart() {
		return cancelPenaltyStart;
	}

	public void setCancelPenaltyStart(String cancelPenaltyStart) {
		this.cancelPenaltyStart = cancelPenaltyStart;
	}
}
