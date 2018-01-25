package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;

public class GZRefundTicket implements Serializable{
    /**供应商*/
    private String supplier;
    /*退票日期*/
    private String refundDate;
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
    /*旅客类型*/
    private String passengerType;
    /*结算净价*/
    private Double settlePrice;
    /*税金*/
    private Double tax;
    /*张数*/
    private Integer ticketNum;
    /*客户名称*/
    private String customer;

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
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

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
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

    public Integer getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(Integer ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
