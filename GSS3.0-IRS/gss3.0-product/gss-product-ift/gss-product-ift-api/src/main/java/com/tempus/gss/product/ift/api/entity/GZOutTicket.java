package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;

public class GZOutTicket implements Serializable{
    /**供应商*/
    private String supplier;
    /*出票日期*/
    private String ticketDate;
    /*票号*/
    private String ticketNo;
    /*PNR*/
    private String pnr;
    /*航程*/
    private String routing;
    /*航班号*/
    private String flightNo;
    /*舱位*/
    private String cabin;
    /*起飞日期*/
    private String departDate;
    /*旅客姓名*/
    private String passengerName;
    /*旅客类型*/
    private String passengerType;
    /*结算净价*/
    private Double settlePrice;
    /*税金*/
    private Double tax;
    /*Q值*/
    private Double qValue;
    /*票据类型*/
    private String ticketType;
    /*张数*/
    private Integer ticketNum;
    /*底扣点*/
    private Double endPoint;
    /*后返点*/
    private Double lastRebatePoint;
    /*加价*/
    private Double addPrice;
    /*营业部毛利*/
    private Double businessGross;
    /*毛利*/
    private Double gross;
    /*客户名称*/
    private String customer;
    /*售票员*/
    private String cusPerson;
    /*接单部门*/
    private String cusDept;
    /*出票员*/
    private String ticketPerson;
    /*出票部门*/
    private String ticketDept;
    /*底价*/
    private Double endPrice;
    /*代理费率*/
    private Double agencyFee;



    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
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

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }


    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public Integer getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(Integer ticketNum) {
        this.ticketNum = ticketNum;
    }

    public Double getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(Double settlePrice) {
        this.settlePrice = settlePrice;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getqValue() {
        return qValue;
    }

    public void setqValue(Double qValue) {
        this.qValue = qValue;
    }

    public Double getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Double endPoint) {
        this.endPoint = endPoint;
    }

    public Double getLastRebatePoint() {
        return lastRebatePoint;
    }

    public void setLastRebatePoint(Double lastRebatePoint) {
        this.lastRebatePoint = lastRebatePoint;
    }

    public Double getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(Double addPrice) {
        this.addPrice = addPrice;
    }

    public Double getBusinessGross() {
        return businessGross;
    }

    public void setBusinessGross(Double businessGross) {
        this.businessGross = businessGross;
    }

    public Double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(Double endPrice) {
        this.endPrice = endPrice;
    }

    public Double getAgencyFee() {
        return agencyFee;
    }

    public void setAgencyFee(Double agencyFee) {
        this.agencyFee = agencyFee;
    }

    public Double getGross() {
        return gross;
    }

    public void setGross(Double gross) {
        this.gross = gross;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCusPerson() {
        return cusPerson;
    }

    public void setCusPerson(String cusPerson) {
        this.cusPerson = cusPerson;
    }

    public String getCusDept() {
        return cusDept;
    }

    public void setCusDept(String cusDept) {
        this.cusDept = cusDept;
    }

    public String getTicketPerson() {
        return ticketPerson;
    }

    public void setTicketPerson(String ticketPerson) {
        this.ticketPerson = ticketPerson;
    }

    public String getTicketDept() {
        return ticketDept;
    }

    public void setTicketDept(String ticketDept) {
        this.ticketDept = ticketDept;
    }
}
