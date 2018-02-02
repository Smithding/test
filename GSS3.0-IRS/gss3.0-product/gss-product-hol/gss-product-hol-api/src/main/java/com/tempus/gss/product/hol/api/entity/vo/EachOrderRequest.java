package com.tempus.gss.product.hol.api.entity.vo;

import com.tempus.gss.product.hol.api.entity.response.Guest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 互采中心酒店订单请求入参实体
 */
public class EachOrderRequest implements Serializable {

    /**
     * 外部销售单号
     */
    private String outNo;

    /**
     * 分子公司编号(即当前ubp节点编号)
     */
    protected String customerCode;

    /**
     * 供应UBP编号
     */
    private String supplierSource;
    /**
     * 价格策略编号
     */
    private String pricePolicyId;

    /**
     * 酒店编号
     */
    private String hotelCode;

    /**
     * 酒店名字
     */
    private String hotelName;
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
    private String ratePlanCode;
    /**
     * 入住日期(yyyy-MM-dd)
     */
    private String checkInDate;
    /**
     * 离店日期(yyyy-MM-dd)
     */
    private String checkOutDate;
    /**
     * 房间数量,要求于names的长度一致
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
     * 价格合计
     */
    private BigDecimal totalPrice;
    /**
     * 返现合计
     */
    private BigDecimal totalRefund;
    /**
     * 立减合计
     */
    private BigDecimal totalReduce;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人电话
     */
    private String contactNumber;
    /**
     * 住客信息
     */
    private List<Guest> names;

    /**
     * 旅客类型(暂时无用,统一传入0)
     */
    private String guestType;
    /**
     * 订单备注
     */
    private String remark;
    /**
     * 客户唯一码，用于解决重复创建订单
     */
    private String customerSerialNo;
    /**
     * 会员编号
     */
    private String userID;
    /**
     * 创建人编号
     */
    private String userName;
    /**
     * 联系人邮箱
     */
    private String contactEmail;

    /**
     * 订单类型 1:内 2:国际
     */
    private Integer orderType;

    /**
     * 服务费(供TMC使用)
     */
    private BigDecimal serviceFee;

    public String getOutNo() {
        return outNo;
    }

    public void setOutNo(String outNo) {
        this.outNo = outNo;
    }

    public String getSupplierSource() {
        return supplierSource;
    }

    public void setSupplierSource(String supplierSource) {
        this.supplierSource = supplierSource;
    }

    public String getPricePolicyId() {
        return pricePolicyId;
    }

    public void setPricePolicyId(String pricePolicyId) {
        this.pricePolicyId = pricePolicyId;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
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

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalRefund() {
        return totalRefund;
    }

    public void setTotalRefund(BigDecimal totalRefund) {
        this.totalRefund = totalRefund;
    }

    public BigDecimal getTotalReduce() {
        return totalReduce;
    }

    public void setTotalReduce(BigDecimal totalReduce) {
        this.totalReduce = totalReduce;
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

    public List<Guest> getNames() {
        return names;
    }

    public void setNames(List<Guest> names) {
        this.names = names;
    }

    public String getGuestType() {
        return guestType;
    }

    public void setGuestType(String guestType) {
        this.guestType = guestType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCustomerSerialNo() {
        return customerSerialNo;
    }

    public void setCustomerSerialNo(String customerSerialNo) {
        this.customerSerialNo = customerSerialNo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
