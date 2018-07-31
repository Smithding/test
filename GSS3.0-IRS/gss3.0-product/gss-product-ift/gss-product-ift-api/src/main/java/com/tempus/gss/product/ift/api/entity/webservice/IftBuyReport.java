package com.tempus.gss.product.ift.api.entity.webservice;

import java.io.Serializable;

public class IftBuyReport implements Serializable {
    //    os_buyorder
    private String saleOrderNo;//销售单号
    private String buyOrderNo;//采购单号
    private String buyer;//采购员
    private String buyTime;//采购时间

    //  os_buyorder_ext
    private String supplierType;//采购商类型
    private String supplierName;//采购商名称

    //  ift_buyorder_ext
    private String airline;//出票航司
    private String ticketType;//出票类型
    private String remark;//备注

    //    ift_sale_order_detail
    private String ticketNo;//票号
    private String legNos;//同一个乘客的多个legNo

    //  ift_saleorder_ext


    //    ift_pnr 和ift_sale_order_ext的pnr_no关联
    private String pnr;

    //    ift_passenger
    private String passengerName;//乘客
    private String currency;//采购货币
    private Double rate;//采购汇率
    private Double fare;//采购票价
    private Double tax;//采购税
    private Double brokerage;//手续费  数据库没有服务费


    //    ift_leg
    private String flightNo;//航班
    private String cabin;//舱位
    private String routing;//航线

    //    os_saleorder_ext
    private String customType;//客户类型
    private String customName;//客户姓名

    //    os_saleorder
    private String customNo;//客户编号
    private String saler;//订票人  ordering_login_name
    private String saleTime;//ordering_time 下单时间

    //    sm_dept和sm_user的dept_id关联
    private String dept;//出票部门

    //    sm_user 和os_buyorder的modifier关联
    private String modifier;//出票人


    //    sm_owner_info 和owner关联
    private String owner;//分公司名

//


    /*
    乘机日期 ：多航段，往返程
    返利 率 ：  每种乘客类型不一致，返利金额也无法计算
    机票状态：
    支付方式：

     */

    public String getCustomType() {
        return customType;
    }

    public void setCustomType(String customType) {
        this.customType = customType;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomNo() {
        return customNo;
    }

    public void setCustomNo(String customNo) {
        this.customNo = customNo;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSaler() {
        return saler;
    }

    public void setSaler(String saler) {
        this.saler = saler;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(Double brokerage) {
        this.brokerage = brokerage;
    }

    public String getLegNos() {
        return legNos;
    }

    public void setLegNos(String legNos) {
        this.legNos = legNos;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(String saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public String getBuyOrderNo() {
        return buyOrderNo;
    }

    public void setBuyOrderNo(String buyOrderNo) {
        this.buyOrderNo = buyOrderNo;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
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
}
