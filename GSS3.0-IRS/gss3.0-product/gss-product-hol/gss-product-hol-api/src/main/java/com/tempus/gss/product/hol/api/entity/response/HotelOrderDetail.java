package com.tempus.gss.product.hol.api.entity.response;

import com.tempus.gss.order.entity.SaleOrder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 酒店订单详情
 * Created by luofengjie on 2017/3/25.
 */
public class HotelOrderDetail implements Serializable{

    /**
     * 销售单编号
     */
    private Long saleOrderNo;
    /**
     * 下单时间
     */
    private Date createTime;

    /**
     * 千淘酒店订单编号
     */
    private String orderNumber;

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
     * 服务费
     */
    private String servicePrice;

    /**
     * 备注
     */
    private String remark;

    /**
     * 立减金额
     */
    private String reduce;

    /**
     * 返现
     */
    private String refound;

    /**
     * 旅客证件
     */
    private String cardNo;

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

    /**
     * 早餐
     */
    private String breakfast;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

    /**
     * 酒店地址
     */
    private String hotelAddress;

    /**
     * 价格详情
     */
    private List<BdOrderPriceDetail> priceDetails;

    /**
     * 入住人
     */
    private List<Guest> Guests;
    /**
     * 订单内部信息
     */
    private BdOrderInn bdOrderInn;
    /**
     * 支付类型 (0预付 1面付)
     */
    private String payType;
    /**
     * 销售单信息
     */
    private SaleOrder saleOrder;


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

    public String getRefound() {
        return refound;
    }

    public void setRefound(String refound) {
        this.refound = refound;
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
        return Guests;
    }

    public void setGuests(List<Guest> guests) {
        Guests = guests;
    }

    public Long getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public BdOrderInn getBdOrderInn() {
        return bdOrderInn;
    }

    public void setBdOrderInn(BdOrderInn bdOrderInn) {
        this.bdOrderInn = bdOrderInn;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public SaleOrder getSaleOrder() {
        return saleOrder;
    }

    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
    }

    public String getArriveLastTime() {
        return arriveLastTime;
    }

    public void setArriveLastTime(String arriveLastTime) {
        this.arriveLastTime = arriveLastTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
