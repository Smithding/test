package com.tempus.gss.product.ift.api.entity.vo;

public class QueryGZOutReportVo {
    //航司
    private String air;
    //票号
    private String ticketNo;
    //PNR
    private String pnr;
    //舱位
    private String cabin;
    //出票日期起
    private String beginTicketDate;
    //出票日期止
    private String overTicketDate;
    //起飞日期起
    private String beginFlyDate;
    //起飞日期止
    private String overFlyDate;
    //出发城市
    private String depPlace;
    //到达城市
    private String arrPlace;
    //供应商
    private String supplier;
    //客户名称
    private String customer;

    public String getAir() {
        return air;
    }

    public void setAir(String air) {
        this.air = air;
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

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public String getBeginTicketDate() {
        return beginTicketDate;
    }

    public void setBeginTicketDate(String beginTicketDate) {
        this.beginTicketDate = beginTicketDate;
    }

    public String getOverTicketDate() {
        return overTicketDate;
    }

    public void setOverTicketDate(String overTicketDate) {
        this.overTicketDate = overTicketDate;
    }

    public String getBeginFlyDate() {
        return beginFlyDate;
    }

    public void setBeginFlyDate(String beginFlyDate) {
        this.beginFlyDate = beginFlyDate;
    }

    public String getOverFlyDate() {
        return overFlyDate;
    }

    public void setOverFlyDate(String overFlyDate) {
        this.overFlyDate = overFlyDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public QueryGZOutReportVo() {
    }

    public QueryGZOutReportVo(String air, String ticketNo, String pnr, String cabin, String beginTicketDate, String overTicketDate, String beginFlyDate, String overFlyDate, String depPlace, String arrPlace, String supplier, String customer) {
        this.air = air;
        this.ticketNo = ticketNo;
        this.pnr = pnr;
        this.cabin = cabin;
        this.beginTicketDate = beginTicketDate;
        this.overTicketDate = overTicketDate;
        this.beginFlyDate = beginFlyDate;
        this.overFlyDate = overFlyDate;
        this.depPlace = depPlace;
        this.arrPlace = arrPlace;
        this.supplier = supplier;
        this.customer = customer;
    }

    public String getDepPlace() {
        return depPlace;
    }

    public void setDepPlace(String depPlace) {
        this.depPlace = depPlace;
    }

    public String getArrPlace() {
        return arrPlace;
    }

    public void setArrPlace(String arrPlace) {
        this.arrPlace = arrPlace;
    }
}
