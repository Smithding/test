package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;

/**
 * @author ZhangBro
 * 查询条件
 */
public class IftOutReportVO implements Serializable {
    
    private static final long serialVersionUID = -6393082115652051244L;
    /**
     * 精确查找  在sql条件中不使用like关键字
     */
    private Boolean exactQuery;
    /**
     * 供应商
     */
    private String supplierName;
    /**
     * 类型
     */
    private String type;
    /**
     * 出票日期
     */
    private String ticketDateStart;
    private String ticketDateEnd;
    /**
     * 工单号
     */
    private String saleOrderNo;
    /**
     * 票号
     */
    private String ticketNo;
    /**
     * 航程
     */
    private String routing;
    /**
     * 航班号
     */
    private String flightNo;
    /**
     * 舱位
     */
    private String cabin;
    /**
     * 起飞日期
     */
    private String depDateStart;
    private String depDateEnd;
    /**
     * 售票员
     */
    private String salePerson;
    /**
     * 出票部门
     */
    private String saleDept;
    /**
     * 出票员
     */
    private String ticketPerson;
    /**
     * 所属公司
     */
    private String company;
    /**
     * 旅客姓名
     */
    private String passengerName;
    /**
     * 订单来源
     */
    private String source;
    /**
     * 改签类型
     */
    private Integer changeType;
    
    public Integer getChangeType() {
        return changeType;
    }
    
    public boolean isExactQuery() {
        return exactQuery;
    }
    
    public void setExactQuery(boolean exactQuery) {
        this.exactQuery = exactQuery;
    }
    
    public void setChangeType(Integer changeType) {
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
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getPassengerName() {
        return passengerName;
    }
    
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
}
