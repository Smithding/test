package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;

public class IFTIssueReport implements Serializable {
    
    private static final long serialVersionUID = -6393082115652051244L;
    /**
     * saleDept 销售部门
     */
    private String saleDept;
    
    /**
     * customerName 客户全称
     */
    private String customerName;
    
    /**
     * customerNo 客户编号
     */
    private String customerNo;
    
    /**
     * airline 承运人
     */
    private String airline;
    
    /**
     * ticketNo 票号
     */
    private String ticketNo;
    
    /**
     * routing 航程
     */
    private String routing;
    
    /**
     * flightNo 航班
     */
    private String flightNo;
    
    /**
     * cabin 舱位
     */
    private String cabin;
    
    /**
     * ticketDate 出票日期
     */
    private String ticketDate;
    
    /**
     * depDate 乘机日期
     */
    private String depDate;
    
    /**
     * farePrice 票价
     */
    private String farePrice;
    
    /**
     * tax 税费
     */
    private String tax;
    
    /**
     * saleRebate 返点
     */
    private String saleRebate;
    /**
     * rebatePrice 返利
     */
    private String rebatePrice;
    
    /**
     * salePrice 应收金额
     */
    private String salePrice;
    
    /**
     * serviceCharge 服务费
     */
    private String serviceCharge;
    
    /**
     * passengerName 乘机人
     */
    private String passengerName;
    
    /**
     * pnr PNR
     */
    private String pnr;
    
    /**
     * ticketType 机票状态
     */
    private String ticketType;
    
    /**
     * salePerson 订票人
     */
    private String salePerson;
    
    /**
     * ticketPerson 出票人
     */
    private String ticketPerson;
    
    /**
     * supplierName 出票渠道
     */
    private String supplierName;
    
    /**
     * saleOrderNo 订单号
     */
    private String saleOrderNo;
    
    /**
     * payWay 支付方式
     */
    private String payWay;
    
    /**
     * payNo 支付流水号
     */
    private String payNo;
    
    /**
     * itineraryNo 行程单号
     */
    private String itineraryNo;
    
    /**
     * settlePrice 结算金额
     */
    private String settlePrice;
    
    /**
     * exchangeRate 汇率
     */
    private String exchangeRate;
    
    /**
     * currency 币种
     */
    private String currency;
    
    /**
     * company 所属公司
     */
    private String company;
    
    /**
     * source 订单来源
     */
    private String source;
    
    //todo   （机票）返利金额           保险	    保单号	 类型	   全价金额Y	   服务费
    
    public String getSaleDept() {
        return saleDept;
    }
    
    public void setSaleDept(String saleDept) {
        this.saleDept = saleDept;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerNo() {
        return customerNo;
    }
    
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
    
    public String getAirline() {
        return airline;
    }
    
    public void setAirline(String airline) {
        this.airline = airline;
    }
    
    public String getTicketNo() {
        return ticketNo;
    }
    
    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
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
    
    public String getTicketDate() {
        return ticketDate;
    }
    
    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }
    
    public String getDepDate() {
        return depDate;
    }
    
    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }
    
    public String getFarePrice() {
        return farePrice;
    }
    
    public void setFarePrice(String farePrice) {
        this.farePrice = farePrice;
    }
    
    public String getTax() {
        return tax;
    }
    
    public void setTax(String tax) {
        this.tax = tax;
    }
    
    public String getSaleRebate() {
        return saleRebate;
    }
    
    public void setSaleRebate(String saleRebate) {
        this.saleRebate = saleRebate;
    }
    
    public String getSalePrice() {
        return salePrice;
    }
    
    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }
    
    public String getServiceCharge() {
        return serviceCharge;
    }
    
    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }
    
    public String getPassengerName() {
        return passengerName;
    }
    
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    
    public String getPnr() {
        return pnr;
    }
    
    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
    
    public String getTicketType() {
        return ticketType;
    }
    
    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
    
    public String getSalePerson() {
        return salePerson;
    }
    
    public void setSalePerson(String salePerson) {
        this.salePerson = salePerson;
    }
    
    public String getTicketPerson() {
        return ticketPerson;
    }
    
    public void setTicketPerson(String ticketPerson) {
        this.ticketPerson = ticketPerson;
    }
    
    public String getSupplierName() {
        return supplierName;
    }
    
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
    public String getSaleOrderNo() {
        return saleOrderNo;
    }
    
    public void setSaleOrderNo(String saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }
    
    public String getPayWay() {
        return payWay;
    }
    
    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
    
    public String getPayNo() {
        return payNo;
    }
    
    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }
    
    public String getItineraryNo() {
        return itineraryNo;
    }
    
    public void setItineraryNo(String itineraryNo) {
        this.itineraryNo = itineraryNo;
    }
    
    public String getSettlePrice() {
        return settlePrice;
    }
    
    public void setSettlePrice(String settlePrice) {
        this.settlePrice = settlePrice;
    }
    
    public String getExchangeRate() {
        return exchangeRate;
    }
    
    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    
    public String getRebatePrice() {
        return rebatePrice;
    }
    
    public void setRebatePrice(String rebatePrice) {
        this.rebatePrice = rebatePrice;
    }
}
