package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class HotelFinancialReport implements Serializable{
    private static final long serialVersionUID = 1L;

    //订单号
    private String saleOrderNo;
    //订单状态
    private String orderStatus;
    //供应商订单号
    private String supplierOrderNo;
    //支付方式
    private Integer payWay;
    //支付方式-String
    private String payWayString;
    //预定人
    private String reservcePerson;
    //入住人
    private String checkinPerson;
    //供应商名称
    private String supplierName;
    //入住城市
    private String city;
    //入住酒店
    private String hotelName;
    //房型
    private String roomType;
    //入住房号
    private String roomNo;
    //预定日期
    private String reserveDate;
    //实际入住日期
    private String checkinDate;
    //实际离店日期
    private String checkoutDate;
    //入住房间数
    private Integer roomNum;
    //入住晚数
    private Integer nightNum;
    //间夜数
    private Integer roomNightNum;
    //销售单价String
    private String salePrices;
    //销售单价
    private BigDecimal salePrice;
    //结算单价
    private BigDecimal settlePrice;
    //销售总价
    private BigDecimal saleTotalPrice;
    //结算总价
    private BigDecimal settleTotalPrice;
    //上游佣金率
    private BigDecimal upCommission;
    //应收佣金
    private BigDecimal receivableCommission;
    //下游佣金率
    private BigDecimal downCommission;
    //返佣金额
    private BigDecimal rebatePrice;
    //毛利
    private BigDecimal gross;
    //供应商结算方式
    private String supplierSettleType="月结";
    //订单来源
    private String orderSource;
    //订房员
    private String creator;
    //银行名称
    private String bankName;
    //银行卡号
    private String bankCardNo;
    //开户名称
    private String openAccountName;
    //开户省份
    private String openAccountProvince;
    //开户城市
    private String openAccountCity;
    //开户地址
    private String openAccountAddress;




    public String getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(String saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSupplierOrderNo() {
        return supplierOrderNo;
    }

    public void setSupplierOrderNo(String supplierOrderNo) {
        this.supplierOrderNo = supplierOrderNo;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public String getPayWayString() {
        return payWayString;
    }

    public void setPayWayString(String payWayString) {
        this.payWayString = payWayString;
    }

    public String getReservcePerson() {
        return reservcePerson;
    }

    public void setReservcePerson(String reservcePerson) {
        this.reservcePerson = reservcePerson;
    }

    public String getCheckinPerson() {
        return checkinPerson;
    }

    public void setCheckinPerson(String checkinPerson) {
        this.checkinPerson = checkinPerson;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Integer getNightNum() {
        return nightNum;
    }

    public void setNightNum(Integer nightNum) {
        this.nightNum = nightNum;
    }

    public Integer getRoomNightNum() {
        return roomNightNum;
    }

    public void setRoomNightNum(Integer roomNightNum) {
        this.roomNightNum = roomNightNum;
    }

    public String getSalePrices() {
        return salePrices;
    }

    public void setSalePrices(String salePrices) {
        this.salePrices = salePrices;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(BigDecimal settlePrice) {
        this.settlePrice = settlePrice;
    }

    public BigDecimal getSaleTotalPrice() {
        return saleTotalPrice;
    }

    public void setSaleTotalPrice(BigDecimal saleTotalPrice) {
        this.saleTotalPrice = saleTotalPrice;
    }

    public BigDecimal getSettleTotalPrice() {
        return settleTotalPrice;
    }

    public void setSettleTotalPrice(BigDecimal settleTotalPrice) {
        this.settleTotalPrice = settleTotalPrice;
    }

    public BigDecimal getUpCommission() {
        return upCommission;
    }

    public void setUpCommission(BigDecimal upCommission) {
        this.upCommission = upCommission;
    }

    public BigDecimal getReceivableCommission() {
        return receivableCommission;
    }

    public void setReceivableCommission(BigDecimal receivableCommission) {
        this.receivableCommission = receivableCommission;
    }

    public BigDecimal getDownCommission() {
        return downCommission;
    }

    public void setDownCommission(BigDecimal downCommission) {
        this.downCommission = downCommission;
    }

    public BigDecimal getRebatePrice() {
        return rebatePrice;
    }

    public void setRebatePrice(BigDecimal rebatePrice) {
        this.rebatePrice = rebatePrice;
    }

    public BigDecimal getGross() {
        return gross;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public String getSupplierSettleType() {
        return supplierSettleType;
    }

    public void setSupplierSettleType(String supplierSettleType) {
        this.supplierSettleType = supplierSettleType;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getOpenAccountName() {
        return openAccountName;
    }

    public void setOpenAccountName(String openAccountName) {
        this.openAccountName = openAccountName;
    }

    public String getOpenAccountProvince() {
        return openAccountProvince;
    }

    public void setOpenAccountProvince(String openAccountProvince) {
        this.openAccountProvince = openAccountProvince;
    }

    public String getOpenAccountCity() {
        return openAccountCity;
    }

    public void setOpenAccountCity(String openAccountCity) {
        this.openAccountCity = openAccountCity;
    }

    public String getOpenAccountAddress() {
        return openAccountAddress;
    }

    public void setOpenAccountAddress(String openAccountAddress) {
        this.openAccountAddress = openAccountAddress;
    }
}
