package com.tempus.gss.product.hol.api.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HotelShowOrder implements Serializable {
	/**
	 * 销售单号
	 */
private Long saleOrderNo;
/**
 * 客户类型
 */
private String nameType;
/**
 * 客户卡号
 */
private Long customerNo;
/**
 * 客户名称
 */
private String customerName;
/**
 * 预订时间
 */
private String creator;

private String createOrderTime;
/**
 * 酒店名称
 */
private String hotelName;
/**
 * 房型
 */
private String proName;
/**
 * 客人姓名
 */
private String guestName;
/**
 * 到店时间
 */
private Date arrivalDate;
/**
 * 离店时间
 */
private Date departureDate;
/**
 * 实际到店时间
 */
private Date factArriveTime;
/**
 * 实际离店时间
 */
private Date factLeaveTime;
/**
 * 预订房间数量
 */
private Integer bookCount;
/**
 * 实际房间预订数量
 */
private Integer factProCount;
/**
 * 总价
 */
private BigDecimal totalPrice;
/**
 * 晚数
 */
private Integer nightCount;
/**
 * 实际总价
 */
private Integer factTotalPrice;
/**
 * 返佣总计
 */
private Integer totalRefund;
/**
 * 供应商号
 */
private Long supplierNo;
/**
 * 订单状态
 */
private String orderStatus;
/**
 *外购单号
 */
private Long hotelOrderNo;
/**
 * 同城状态
 */
private String tcOrderStatus;
/**
 * 供应商状态
 */
private String supplier;
/**
 * 地址
 * @return
 */
private String address;

/**
 * 实际晚数
 */
private Integer factNightCount;
public String getCreator() {
	return creator;
}
public void setCreator(String creator) {
	this.creator = creator;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public Long getSaleOrderNo() {
	return saleOrderNo;
}
public void setSaleOrderNo(Long saleOrderNo) {
	this.saleOrderNo = saleOrderNo;
}
public String getNameType() {
	return nameType;
}
public void setNameType(String nameType) {
	this.nameType = nameType;
}
public Long getCustomerNo() {
	return customerNo;
}
public void setCustomerNo(Long customerNo) {
	this.customerNo = customerNo;
}
public String getCustomerName() {
	return customerName;
}
public void setCustomerName(String customerName) {
	this.customerName = customerName;
}
public String getCreateOrderTime() {
	return createOrderTime;
}
public void setCreateOrderTime(String createOrderTime) {
	this.createOrderTime = createOrderTime;
}
public String getHotelName() {
	return hotelName;
}
public void setHotelName(String hotelName) {
	this.hotelName = hotelName;
}
public String getProName() {
	return proName;
}
public void setProName(String proName) {
	this.proName = proName;
}
public String getGuestName() {
	return guestName;
}
public void setGuestName(String guestName) {
	this.guestName = guestName;
}
public Date getArrivalDate() {
	return arrivalDate;
}
public void setArrivalDate(Date arrivalDate) {
	this.arrivalDate = arrivalDate;
}
public Date getDepartureDate() {
	return departureDate;
}
public void setDepartureDate(Date departureDate) {
	this.departureDate = departureDate;
}
public Date getFactArriveTime() {
	return factArriveTime;
}
public void setFactArriveTime(Date factArriveTime) {
	this.factArriveTime = factArriveTime;
}
public Date getFactLeaveTime() {
	return factLeaveTime;
}
public void setFactLeaveTime(Date factLeaveTime) {
	this.factLeaveTime = factLeaveTime;
}
public Integer getBookCount() {
	return bookCount;
}
public void setBookCount(Integer bookCount) {
	this.bookCount = bookCount;
}
public Integer getFactProCount() {
	return factProCount;
}
public void setFactProCount(Integer factProCount) {
	this.factProCount = factProCount;
}
public BigDecimal getTotalPrice() {
	return totalPrice;
}
public void setTotalPrice(BigDecimal totalPrice) {
	this.totalPrice = totalPrice;
}
public Integer getNightCount() {
	return nightCount;
}
public void setNightCount(Integer nightCount) {
	this.nightCount = nightCount;
}
public Integer getFactTotalPrice() {
	return factTotalPrice;
}
public void setFactTotalPrice(Integer factTotalPrice) {
	this.factTotalPrice = factTotalPrice;
}
public Integer getTotalRefund() {
	return totalRefund;
}
public void setTotalRefund(Integer totalRefund) {
	this.totalRefund = totalRefund;
}
public String getOrderStatus() {
	return orderStatus;
}
public void setOrderStatus(String orderStatus) {
	this.orderStatus = orderStatus;
}
public Long getHotelOrderNo() {
	return hotelOrderNo;
}
public void setHotelOrderNo(Long hotelOrderNo) {
	this.hotelOrderNo = hotelOrderNo;
}
public String getSupplier() {
	return supplier;
}
public void setSupplier(String supplier) {
	this.supplier = supplier;
}
public Long getSupplierNo() {
	return supplierNo;
}
public void setSupplierNo(Long supplierNo) {
	this.supplierNo = supplierNo;
}

public String getTcOrderStatus() {
	return tcOrderStatus;
}
public void setTcOrderStatus(String tcOrderStatus) {
	this.tcOrderStatus = tcOrderStatus;
}
public Integer getFactNightCount() {
	return factNightCount;
}
public void setFactNightCount(Integer factNightCount) {
	this.factNightCount = factNightCount;
}

}
