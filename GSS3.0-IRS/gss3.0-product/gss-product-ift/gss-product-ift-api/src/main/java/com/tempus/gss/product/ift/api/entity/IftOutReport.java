package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IftOutReport implements Serializable{
    //供应商
    private String supplierName;
    //类型
    private String type;
    //出票日期
    private String ticketDate;
    //工单号
    private String saleOrderNo;
    //票号
    private String ticketNo;
    //航程
    private String routing;
    //航班号
    private String flightNo;
    //舱位
    private String cabin;
    //起飞日期
    private String depDate;
    //张数
    private String ticketNum;
    //汇率
    private BigDecimal rate;
    //实价
    private BigDecimal truePrice;
    //结算净价
    private BigDecimal settlePrice;
    //税金
    private BigDecimal tax;
    //净价合计
    private BigDecimal totalPrice;
    //毛利
    private BigDecimal gross;
    //售票员
    private String salePerson;
    //出票部门
    private String saleDept;
    //出票员
    private String ticketPerson;
    //月结方
    private String monthSettle;
    //所属公司
    private String company;
    //结算方式
    private String settleMethod;
    //旅客姓名
    private String passengerName;
    //性别
    private String gender;
    //订单来源
    private String source;
    //备注
    private String remark;
    //改签类型
    private String changeType;

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getTicketPerson() {
        return ticketPerson;
    }

    public void setTicketPerson(String ticketPerson) {
        this.ticketPerson = ticketPerson;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }

    public String getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(String saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getTruePrice() {
        return truePrice;
    }

    public void setTruePrice(BigDecimal truePrice) {
        this.truePrice = truePrice;
    }

    public BigDecimal getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(BigDecimal settlePrice) {
        this.settlePrice = settlePrice;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getGross() {
        return gross;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public String getSalePerson() {
        return salePerson;
    }

    public void setSalePerson(String salePerson) {
        this.salePerson = salePerson;
    }

    public String getSaleDept() {
        return saleDept;
    }

    public void setSaleDept(String saleDept) {
        this.saleDept = saleDept;
    }

    public String getMonthSettle() {
        return monthSettle;
    }

    public void setMonthSettle(String monthSettle) {
        this.monthSettle = monthSettle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSettleMethod() {
        return settleMethod;
    }

    public void setSettleMethod(String settleMethod) {
        this.settleMethod = settleMethod;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
