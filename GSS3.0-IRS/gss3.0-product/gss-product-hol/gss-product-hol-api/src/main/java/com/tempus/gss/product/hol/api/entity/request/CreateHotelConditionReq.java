package com.tempus.gss.product.hol.api.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.product.hol.api.entity.request.BaseRequest;
import com.tempus.gss.product.hol.api.entity.response.Guest;

/**
 * 创建酒店订单实体
 *
 * @author Administrator
 */

/**
 * @author Administrator
 */
public class CreateHotelConditionReq extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 供应UBP编号
     */
    @JSONField(name = "SupplierSource")
    private String supplierSource;
    /**
     * 价格策略编号
     */
    @JSONField(name = "PricePolicyID")
    private String pricePolicyId;

    /**
     * 酒店编号
     */
    @JSONField(name = "HotelCode")
    private String hotelCode;

    /**
     * 酒店名字
     */
    @JSONField(serialize = false)
    private String hotelName;
    /**
     * 策略编号
     */
    @JSONField(name = "SupplierCode")
    private String supplierCode;
    /**
     * 房型编号
     */
    @JSONField(name = "RoomCode")
    private String roomCode;
    /**
     * 价格计划编号
     */
    @JSONField(name = "RatePlanCode")
    private String ratePlanCode;
    /**
     * 入住日期(yyyy-MM-dd)
     */
    @JSONField(name = "CheckinDate")
    private String checkInDate;
    /**
     * 离店日期(yyyy-MM-dd)
     */
    @JSONField(name = "CheckoutDate")
    private String checkOutDate;
    /**
     * 房间数量,要求于names的长度一致
     */
    @JSONField(name = "RoomCount")
    private Integer roomCount;

    /**
     * 最早到店时间(hh:mm)
     */
    @JSONField(name = "ArriveEarlyTime")
    private String arriveEarlyTime;
    /**
     * 最迟到店时间(hh:mm)
     */
    @JSONField(name = "ArriveLastTime")
    private String arriveLastTime;
    /**
     * 价格合计
     */
    @JSONField(name = "TotalPrice")
    private BigDecimal totalPrice;
    /**
     * 返现合计
     */
    @JSONField(name = "TotalRefund")
    private BigDecimal totalRefund;
    /**
     * 立减合计
     */
    @JSONField(name = "TotalReduce")
    private BigDecimal totalReduce;
    /**
     * 联系人姓名
     */
    @JSONField(name = "ContactName")
    private String contactName;
    /**
     * 联系人电话
     */
    @JSONField(name = "ContactNumber")
    private String contactNumber;
    /**
     * 住客信息
     */
    @JSONField(name = "Names")
    private List<Guest> names;

    /**
     * 旅客类型(暂时无用,统一传入0)
     */
    @JSONField(name = "GuestType")
    private String guestType;
    /**
     * 订单备注
     */
    @JSONField(name = "Remark")
    private String remark;
    /**
     * 客户唯一码，用于解决重复创建订单
     */
    @JSONField(name = "CustomerSerialNo")
    private String customerSerialNo;
    /**
     * 会员编号
     */
    @JSONField(name = "UserID")
    private String userID;
    /**
     * 创建人编号
     */
    @JSONField(name = "UserName")
    private String userName;
    /**
     * 联系人邮箱
     */
    @JSONField(name = "ContactEmail")
    private String contactEmail;

    /**
     * 订单类型 1:内 2:国际
     */
    @JSONField(serialize = false)
    private Integer orderType;
    /**
     * 采购单实体
     */
    @JSONField(serialize = false)
    private BuyOrder buyOrder;
    /**
     * 销售单实体
     */
    @JSONField(serialize = false)
    private SaleOrder saleOrder;
    /**
     * 服务费(供TMC使用)
     */
    @JSONField(serialize = false)
    private BigDecimal serviceFee;
    /**
     * 渠道（默认赋值feiren）
     */
    @JSONField(name = "Channel")
    private String channel;


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

    public BuyOrder getBuyOrder() {
        return buyOrder;
    }

    public void setBuyOrder(BuyOrder buyOrder) {
        this.buyOrder = buyOrder;
    }

    public SaleOrder getSaleOrder() {
        return saleOrder;
    }

    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
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

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
