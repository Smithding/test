package com.tempus.gss.product.hol.api.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单详情
 * @author Administrator
 *
 */
public class BdOrder implements Serializable{
	private static final long serialVersionUID = 1L;
	

	private String cityName;
	
	private BigDecimal returnAmount;
	
	/**
	 * 立减金额
	 */
	private String reduce;
	
	/**
	 * 返现
	 */
	private String refund;
	
	/**
	 * 订单编号
	 */
	private String orderNumber;

	/**
	 * 旅客证件
	 */
	private String cardNo;
	
	/**
	 * 客户编号
	 */
	private String customerCode;
	
	/**
	 * 酒店编号
	 */
	private String hotelCode;
	
	/**
	 * 策略编号
	 */
	private String supplierCode;
	
	/**
	 * 房型编号
	 */
	private String roomCode;
	
	/**
	 * 价格计划编号
	 */
	private String planCode;
	
	/**
	 * 入住日期
	 */
	private String checkInDate;
	
	/**
	 * 离店日期
	 */
	private String checkOutDate;
	
	/**
	 * 房间数量
	 */
	private Integer roomCount;
	
	/**
	 * 最早到店时间(hh:mm)
	 */
	private String arriveEarlyTime;
	
	/**
	 * 最迟到店时间(hh:mm)
	 */
	private String arriveLastTime;
	
	/**
	 * 合计价格
	 */
	private String price;
	
	/**
	 * 联系人姓名
	 */
	private String contactName;
	
	/**
	 * 联系人电话
	 */
	private String contactNumber;
	
	/**
	 * 旅客类型
	 */
	private String guestType;
	
	/**
	 * 旅客姓名(name1,name2)
	 */
	private String travelName;
	
	/**
	 * 旅客电话(tel1,tel2)
	 */
	private String telephone;
	
	/**
	 * 订单状态
	 */
	private String status;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 服务费
	 */
	private String servicePrice;
	
	/**
	 * 酒店名称
	 */
	private String hotelName;
	
	/**
	 * 房型名称
	 */
	private String roomName;
	
	/**
	 * 价格计划名称
	 */
	private String planName;
	
	/**
	 * 取消规则
	 */
	private String cancelRule;
	
	private String payNow;
	
	private String isSpecial;
	
	private String creatId;
	
	private String createName;
	
	private String createDate;
	
	private String dealDate;
	
	/**
	 * 早餐
	 */
	private String breakfast;
	
	private String outRemark;
	
	/**
	 * 支付类型 (0预付 1面付)
	 */
	private String payType;
	
	private String supplierOrderNumber;
	
	private String contolRoom;
	
	private String internetInfo;
	
	private String accountNumber;
	
	private String bedType;
	
	private String costCenter;
	
	private String payCenter;
	
	private String projectCode;
	
	private String forSelf;
	
	private String source;
	
	private String settleWay;
	
	/**
	 * 联系人邮箱
	 */
	private String contactEmail;
	
	/**
	 * 酒店地址
	 */
	private String hotelAddress;


	private String returnProcessDate;

	public String createId;
	
	/**
	 * 价格详情
	 */
	private List<BdOrderPriceDetail> priceDetails;
	
	/**
	 * 入住人
	 */
	private List<Guest> guests;
	/**
	 * 订单内部信息
	 */
	private BdOrderInn orderInn;
	
	/**
	 * 订单内部信息
	 */
	private  List<BdOrderLog> logs;
	

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Integer getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(Integer roomCount) {
		this.roomCount = roomCount;
	}

	public String getArriveEarlyTime() {
		return arriveEarlyTime;
	}

	public void setArriveEarlyTime(String arriveEarlyTime) {
		this.arriveEarlyTime = arriveEarlyTime;
	}

	public String getArriveLastTime() {
		return arriveLastTime;
	}

	public void setArriveLastTime(String arriveLastTime) {
		this.arriveLastTime = arriveLastTime;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getGuestType() {
		return guestType;
	}

	public void setGuestType(String guestType) {
		this.guestType = guestType;
	}

	public String getTravelName() {
		return travelName;
	}

	public void setTravelName(String travelName) {
		this.travelName = travelName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(String servicePrice) {
		this.servicePrice = servicePrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReduce() {
		return reduce;
	}

	public void setReduce(String reduce) {
		this.reduce = reduce;
	}

	public String getRefund() {
		return refund;
	}

	public void setRefund(String refund) {
		this.refund = refund;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getCancelRule() {
		return cancelRule;
	}

	public void setCancelRule(String cancelRule) {
		this.cancelRule = cancelRule;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getHotelAddress() {
		return hotelAddress;
	}

	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}

	public List<BdOrderPriceDetail> getPriceDetails() {
		return priceDetails;
	}

	public void setPriceDetails(List<BdOrderPriceDetail> priceDetails) {
		this.priceDetails = priceDetails;
	}

	public List<Guest> getGuests() {
		return guests;
	}

	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}

	public BdOrderInn getOrderInn() {
		return orderInn;
	}

	public void setOrderInn(BdOrderInn orderInn) {
		this.orderInn = orderInn;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public String getPayNow() {
		return payNow;
	}

	public void setPayNow(String payNow) {
		this.payNow = payNow;
	}

	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}

	public String getCreatId() {
		return creatId;
	}

	public void setCreatId(String creatId) {
		this.creatId = creatId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getOutRemark() {
		return outRemark;
	}

	public void setOutRemark(String outRemark) {
		this.outRemark = outRemark;
	}

	public String getSupplierOrderNumber() {
		return supplierOrderNumber;
	}

	public void setSupplierOrderNumber(String supplierOrderNumber) {
		this.supplierOrderNumber = supplierOrderNumber;
	}

	public String getContolRoom() {
		return contolRoom;
	}

	public void setContolRoom(String contolRoom) {
		this.contolRoom = contolRoom;
	}

	public String getInternetInfo() {
		return internetInfo;
	}

	public void setInternetInfo(String internetInfo) {
		this.internetInfo = internetInfo;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getPayCenter() {
		return payCenter;
	}

	public void setPayCenter(String payCenter) {
		this.payCenter = payCenter;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getForSelf() {
		return forSelf;
	}

	public void setForSelf(String forSelf) {
		this.forSelf = forSelf;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<BdOrderLog> getLogs() {
		return logs;
	}

	public void setLogs(List<BdOrderLog> logs) {
		this.logs = logs;
	}

	public String getReturnProcessDate() {
		return returnProcessDate;
	}

	public void setReturnProcessDate(String returnProcessDate) {
		this.returnProcessDate = returnProcessDate;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDealDate() {
		return dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String getSettleWay() {
		return settleWay;
	}

	public void setSettleWay(String settleWay) {
		this.settleWay = settleWay;
	}
}
