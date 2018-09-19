package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.reflect.Array.newInstance;

/**
 * @author fangzhuangzhan
 * 查询条件
 */
public class GzOutReportParams implements Serializable {
    
    private static final long serialVersionUID = 1L;
    /**
     * 航司
     */
    private String airLine;
    /**
     * 票证(三字码)
     */
    private String ticketNoThr;
    /**
     * 票号
     */
    private String ticketNo;
    /**
     * pnr小编码
     */
    private String pnr;
    /**
     * 出票日期
     */
    private String ticketDateStart;
    private String ticketDateEnd;
    /**
     * 旅行日期
     */
    private String depDateStart;
    private String depDateEnd;
    /**
     * 始发地
     */
    private String depAirport;
    /**
     * 目的地
     */
    private String arrAirport;
    /**
     * 供应商
     */
    private String supplierName;
    /**
     * 采购商
     */
    private String customerName;
    /**
     * 舱位
     */
    private List<String> cabins;

    /**
     * 汇总项
     */
    private String orderByType;

    /**
     * 行程类型 1.去程  2.返程
     */
    private Integer goBack;

    public Integer getGoBack() {
        return goBack;
    }

    public void setGoBack(Integer goBack) {
        this.goBack = goBack;
    }

    @Override
    public String toString() {
        return "GzOutReportParams{" +
                "airLine='" + airLine + '\'' +
                ", ticketNoThr='" + ticketNoThr + '\'' +
                ", ticketNo='" + ticketNo + '\'' +
                ", pnr='" + pnr + '\'' +
                ", ticketDateStart='" + ticketDateStart + '\'' +
                ", ticketDateEnd='" + ticketDateEnd + '\'' +
                ", depDateStart='" + depDateStart + '\'' +
                ", depDateEnd='" + depDateEnd + '\'' +
                ", depAirport='" + depAirport + '\'' +
                ", arrAirport='" + arrAirport + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", cabins=" + cabins +
                ", orderByType='" + orderByType + '\'' +
                ", goBack=" + goBack +
                '}';
    }

    public String getOrderByType() {
        return orderByType;
    }

    public void setOrderByType(String orderByType) {
        this.orderByType = orderByType;
    }

    public String getAirLine() {
        return airLine;
    }

    public void setAirLine(String airLine) {
        this.airLine = airLine;
    }

    public String getTicketNoThr() {
        return ticketNoThr;
    }

    public void setTicketNoThr(String ticketNoThr) {
        this.ticketNoThr = ticketNoThr;
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

    public String getTicketDateStart() {
        return ticketDateStart;
    }

    public void setTicketDateStart(String ticketDateStart) {
        this.ticketDateStart = ticketDateStart;
    }

    public String getTicketDateEnd() {
        return ticketDateEnd;
    }

    public void setTicketDateEnd(String ticketDateEnd) {
        this.ticketDateEnd = ticketDateEnd;
    }

    public String getDepDateStart() {
        return depDateStart;
    }

    public void setDepDateStart(String depDateStart) {
        this.depDateStart = depDateStart;
    }

    public String getDepDateEnd() {
        return depDateEnd;
    }

    public void setDepDateEnd(String depDateEnd) {
        this.depDateEnd = depDateEnd;
    }

    public String getDepAirport() {
        return depAirport;
    }

    public void setDepAirport(String depAirport) {
        this.depAirport = depAirport;
    }

    public String getArrAirport() {
        return arrAirport;
    }

    public void setArrAirport(String arrAirport) {
        this.arrAirport = arrAirport;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<String> getCabins() {
        return cabins;
    }

    public void setCabins(List<String> cabins) {
        this.cabins = cabins;
    }
}

