package com.tempus.gss.product.hol.api.entity.vo;

import java.io.Serializable;

public class QueryFinancialReportVo implements Serializable {
    //订单号
    private String saleOrderNo;
    //订单状态
    private String orderStatus;
    //供应商订单号
    private String supplierOrderNo;
    //支付方式
    private Integer payWay;
    //预定人
    private String reservePerson;
    //入住人
    private String checkinPerson;
    //入住城市
    private String checkinCity;
    //酒店名称
    private String hotelName;
    //开始预定日期
    private String minReserveDate;
    //结束预定日期
    private  String maxReserveDate;
    //开始入住日期
    private String checkinDate;
    //结束入住日期
    private String checkoutDate;
    //开始离店日期
    private String depDateBegin;
    //结束离店日期
    private String depDateOver;


    public String getDepDateBegin() {
        return depDateBegin;
    }

    public void setDepDateBegin(String depDateBegin) {
        this.depDateBegin = depDateBegin;
    }

    public String getDepDateOver() {
        return depDateOver;
    }

    public void setDepDateOver(String depDateOver) {
        this.depDateOver = depDateOver;
    }

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

    public String getReservePerson() {
        return reservePerson;
    }

    public void setReservePerson(String reservePerson) {
        this.reservePerson = reservePerson;
    }

    public String getCheckinPerson() {
        return checkinPerson;
    }

    public void setCheckinPerson(String checkinPerson) {
        this.checkinPerson = checkinPerson;
    }

    public String getCheckinCity() {
        return checkinCity;
    }

    public void setCheckinCity(String checkinCity) {
        this.checkinCity = checkinCity;
    }

    public String getMinReserveDate() {
        return minReserveDate;
    }

    public void setMinReserveDate(String minReserveDate) {
        this.minReserveDate = minReserveDate;
    }

    public String getMaxReserveDate() {
        return maxReserveDate;
    }

    public void setMaxReserveDate(String maxReserveDate) {
        this.maxReserveDate = maxReserveDate;
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

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public QueryFinancialReportVo(String saleOrderNo, String orderStatus, String supplierOrderNo, Integer payWay, String reservePerson, String checkinPerson, String checkinCity, String hotelName, String minReserveDate, String maxReserveDate, String checkinDate, String checkoutDate, String depDateBegin, String depDateOver) {
        this.saleOrderNo = saleOrderNo;
        this.orderStatus = orderStatus;
        this.supplierOrderNo = supplierOrderNo;
        this.payWay = payWay;
        this.reservePerson = reservePerson;
        this.checkinPerson = checkinPerson;
        this.checkinCity = checkinCity;
        this.hotelName = hotelName;
        this.minReserveDate = minReserveDate;
        this.maxReserveDate = maxReserveDate;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.depDateBegin = depDateBegin;
        this.depDateOver = depDateOver;
    }

    public QueryFinancialReportVo() {
    }
}
