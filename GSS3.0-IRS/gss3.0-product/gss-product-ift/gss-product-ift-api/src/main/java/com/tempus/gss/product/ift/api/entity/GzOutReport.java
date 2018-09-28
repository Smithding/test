package com.tempus.gss.product.ift.api.entity;/*
 * 广州报表
 *
 * @author fangzhuangzhan
 * @create 15:52 2018/9/17
 */

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GzOutReport implements Serializable {

    private static final long serialVersionUID = 1L;

    //SALE_ORDER_NO
    private Long saleOrderNo;
    //SALE_CHAGNE_NO
    private Long saleChangeNo;
    //SUPPLIER_NAME;供应商
    private String supplierName;
    //ISSUE_TIME出票时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date issueTime;
    //QUIT_TIME;退票时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date quitTime;
    //TICKET_NO
    private String ticketNo;
    //PNR;
    private String pnr;
    //VOYAGE 航段
    private String voyage;
    //FLIGHT_NO; 航班号
    private  String flightNo;
    //CABIN 舱位
    private String cabin;
    //DEP_TIME;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date depTime;
    //ARR_TIME
    private Date arrTime;
    //TRAVEL_TIME;旅行时间
    private String travelTime;
    //PASSENGER_NAME
    private String passengerName;
    //PASSENGER_TYPE;
    private String passengerType;
    //NET_PRICE结算净价
    private BigDecimal netPrice;
    //TAX 税费
    private BigDecimal tax;
    //QVALUE Q值
    private BigDecimal qvalue;
    //TICKET_TYPE  0正常票 1废2退3改
    private Integer ticketType;
    //TICKET_NUM 张数
    private Integer ticketNum;
    //BOTTOM_REBATE底扣点
    private BigDecimal bottomRebate;
    //BACK_REBATE后返点
    private BigDecimal backRebate;
    //PLUS_PRICE加价
    private BigDecimal plusPrice;
    //DEPT_PROFIT
    private BigDecimal deptProfit;
    //PROFIT
    private BigDecimal profit;
    //CUSTOMER_NAME
    private String customerName;
    //SALE_MAN
    private String saleMan;
    //DEPT_NAME
    private String deptName;
    //TICKET_NAME
    private String ticketName;
    //TICKET_DEPT
    private String ticketDept;
    //按日期汇总
    private String days;
    //航司
    private String airline;
    //乘客编号
    private Long passengerNo;

    public Long getPassengerNo() {
        return passengerNo;
    }

    public void setPassengerNo(Long passengerNo) {
        this.passengerNo = passengerNo;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    @Override
    public String toString() {
        return "GzOutReport{" +
                "saleOrderNo=" + saleOrderNo +
                ", saleChangeNo=" + saleChangeNo +
                ", supplierName='" + supplierName + '\'' +
                ", issueTime=" + issueTime +
                ", quitTime=" + quitTime +
                ", ticketNo='" + ticketNo + '\'' +
                ", pnr='" + pnr + '\'' +
                ", voyage='" + voyage + '\'' +
                ", flightNo='" + flightNo + '\'' +
                ", cabin='" + cabin + '\'' +
                ", depTime=" + depTime +
                ", arrTime=" + arrTime +
                ", travelTime='" + travelTime + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", passengerType='" + passengerType + '\'' +
                ", netPrice=" + netPrice +
                ", tax=" + tax +
                ", qvalue=" + qvalue +
                ", ticketType=" + ticketType +
                ", ticketNum=" + ticketNum +
                ", bottomRebate=" + bottomRebate +
                ", backRebate=" + backRebate +
                ", plusPrice=" + plusPrice +
                ", deptProfit=" + deptProfit +
                ", profit=" + profit +
                ", customerName='" + customerName + '\'' +
                ", saleMan='" + saleMan + '\'' +
                ", deptName='" + deptName + '\'' +
                ", ticketName='" + ticketName + '\'' +
                ", ticketDept='" + ticketDept + '\'' +
                '}';
    }

    public Long getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public Long getSaleChangeNo() {
        return saleChangeNo;
    }

    public void setSaleChangeNo(Long saleChangeNo) {
        this.saleChangeNo = saleChangeNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public Date getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(Date quitTime) {
        this.quitTime = quitTime;
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

    public String getVoyage() {
        return voyage;
    }

    public void setVoyage(String voyage) {
        this.voyage = voyage;
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

    public Date getDepTime() {
        return depTime;
    }

    public void setDepTime(Date depTime) {
        this.depTime = depTime;
    }

    public Date getArrTime() {
        return arrTime;
    }

    public void setArrTime(Date arrTime) {
        this.arrTime = arrTime;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerTypeStr(){
        if("ADT".equals(passengerType)){
            return "成人";
        }else if("INF".equals(passengerType)){
            return "婴儿";
        }else{
            return "儿童";
        }
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getQvalue() {
        return qvalue;
    }

    public void setQvalue(BigDecimal qvalue) {
        this.qvalue = qvalue;
    }

    public String getTicketTypeStr(){
        if(1 == ticketType){
            return "废票单";
        } else if(2 == ticketType){
            return "退票单";
        } else if(3 == ticketType){
            return "改签单";
        } else{
            return "正常单";
        }
    }

    public Integer getTicketType() {
        return ticketType;
    }

    public void setTicketType(Integer ticketType) {
        this.ticketType = ticketType;
    }

    public Integer getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(Integer ticketNum) {
        this.ticketNum = ticketNum;
    }

    public BigDecimal getBottomRebate() {
        return bottomRebate;
    }

    public void setBottomRebate(BigDecimal bottomRebate) {
        this.bottomRebate = bottomRebate;
    }

    public BigDecimal getBackRebate() {
        return backRebate;
    }

    public void setBackRebate(BigDecimal backRebate) {
        this.backRebate = backRebate;
    }

    public BigDecimal getPlusPrice() {
        return plusPrice;
    }

    public void setPlusPrice(BigDecimal plusPrice) {
        this.plusPrice = plusPrice;
    }

    public BigDecimal getDeptProfit() {
        return deptProfit;
    }

    public void setDeptProfit(BigDecimal deptProfit) {
        this.deptProfit = deptProfit;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSaleMan() {
        return saleMan;
    }

    public void setSaleMan(String saleMan) {
        this.saleMan = saleMan;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTicketDept() {
        return ticketDept;
    }

    public void setTicketDept(String ticketDept) {
        this.ticketDept = ticketDept;
    }
}
