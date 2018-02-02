package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/3/9.
 */
public class ReportVo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    /**
     * 供应商类型
     */
    private String supplierType;
    /**
     * 退废类型
     * */
    private String refundType;
    /**
     * 票类型
     */
    private String type;
    /**
     * 销售单号
     */
    private Long saleOrderNo;
    /**
     * 航程
     */
    private String leg;
    /**
     * 销售手续费
     */
    private BigDecimal saleBrokerage;
    /**
     * 实退款
     */
    private BigDecimal factRefundAccount;
    /**
     * 供应商退款
     */
    private BigDecimal buyRefundAccount;
    /**
     * 退票员
     */
    private String refunder;
    /**
     * 退票原因
     */
    private String reason;
    /**
     * 张数
     */
    private Integer ticketNum;
    
    /**
     * 汇率
     */
    private BigDecimal exchange;
    /**
     * 售价
     */
    private BigDecimal salePrice;
    /**
     * 退票费
     */
    private BigDecimal refundPrice;
    /**
     * 净结算价
     */
    private BigDecimal settlePrice;
    /**
     * 结算到账状态,1.未结算 2.已结算 3.已审
     */
    private String settleStatus;
    /**
     * 税费
     */
    private BigDecimal tax;
    /**
     * 采购退款
     */
    private BigDecimal buyRefundPrice;
    /**
     * 营业部毛利
     */
    private BigDecimal depGrossProfit;
    /**
     * 毛利
     */
    private BigDecimal grossProfit;
    /**
     * 售票员
     */
    private String saler;
    /**
     * 接单部门
     */
    private String dep;
    /**
     * 接单部门
     */
    private Integer depId;
    /**
     * 接单部门
     */
    private Integer ticketDepId;
    /**
     * 操作部门（出票，退票等）
     */
    private String ticketDep;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 所属公司
     */
    private String customerCompany;
    /**
     * 结算方式
     */
    private String settleWay;
    /**
     * 会员号
     */
    private String customerNo;
    /**
     * 订单来源渠道
     */
    private String sourceChannel;
    /**
     * 备注
     */
    private String remark;
    /**
     * 票号
     */
    public String ticketNo;
    
    /**
     * 航司
     */
    public String airLine;
    
    /**
     * 票面价
     */
    public BigDecimal saleFare;
    
    /**
     * 税费
     */
    public BigDecimal saleTax;
    
    /**
     * 手续费(代理费)
     */
    public BigDecimal saleAgencyFee;
    
    /**
     * 航线
     */
    public String airRoute;
    
    /**
     * 航班
     */
    public String flightNo;
    
    /**
     * 舱位
     */
    public String cabin;
    
    /**
     * 乘机时间
     */
    public String depTime;
    
    /**
     * pnr
     */
    public String pnr;
    
    /**
     * 联系人
     */
    public String contactName;
    
    /**
     * 乘机人姓名
     */
    public String passengerName;
    
    /**
     * 供应商
     */
    public String supplierName;
    
    /**
     * 出票时间
     */
    public String issueTime;
    
    /**
     * 查询开始时间
     */
    
    private String startDate;
    /**
     * 查询结束时间
     */
    private String endDate;
    /**
     * 查询时间类别
     */
    private Integer dateType;
    /**
     * 是否模糊查询  1、是  0、否（出票人、退票人)
     */
    private Integer exactlyQuery;

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public Integer getExactlyQuery() {
        return exactlyQuery;
    }
    
    public void setExactlyQuery(Integer exactlyQuery) {
        this.exactlyQuery = exactlyQuery;
    }
    
    public Integer getDepId() {
        return depId;
    }
    
    public void setDepId(Integer depId) {
        this.depId = depId;
    }
    
    public Integer getTicketDepId() {
        return ticketDepId;
    }
    
    public void setTicketDepId(Integer ticketDepId) {
        this.ticketDepId = ticketDepId;
    }
    
    public String getStartDate() {
        return startDate;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public Integer getDateType() {
        return dateType;
    }
    
    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }
    
    public String getSupplierType() {
        return supplierType;
    }
    
    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }
    
    public Long getSaleOrderNo() {
        return saleOrderNo;
    }
    
    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }
    
    public String getLeg() {
        return leg;
    }
    
    public void setLeg(String leg) {
        this.leg = leg;
    }
    
    public Integer getTicketNum() {
        return this.ticketNum;
    }
    
    public void setTicketNum(Integer ticketNum) {
        this.ticketNum = ticketNum;
    }
    
    public void setTicketNum(String ticketNo) {
        this.ticketNum = getTicketNum(ticketNo);
    }
    
    public void setTicketNum() {
        this.ticketNum = getTicketNum(this.ticketNo);
    }
    
    public BigDecimal getExchange() {
        return exchange;
    }
    
    public void setExchange(BigDecimal exchange) {
        this.exchange = exchange;
    }
    
    public BigDecimal getSaleBrokerage() {
        return saleBrokerage;
    }
    
    public void setSaleBrokerage(BigDecimal saleBrokerage) {
        this.saleBrokerage = saleBrokerage;
    }
    
    public BigDecimal getFactRefundAccount() {
        return factRefundAccount;
    }
    
    public void setFactRefundAccount(BigDecimal factRefundAccount) {
        this.factRefundAccount = factRefundAccount;
    }
    
    public BigDecimal getBuyRefundAccount() {
        return buyRefundAccount;
    }
    
    public void setBuyRefundAccount(BigDecimal buyRefundAccount) {
        this.buyRefundAccount = buyRefundAccount;
    }
    
    public String getRefunder() {
        return refunder;
    }
    
    public void setRefunder(String refunder) {
        this.refunder = refunder;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public BigDecimal getSalePrice() {
        return salePrice;
    }
    
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
    
    public BigDecimal getRefundPrice() {
        return refundPrice;
    }
    
    public void setRefundPrice(BigDecimal refundPrice) {
        this.refundPrice = refundPrice;
    }
    
    public BigDecimal getSettlePrice() {
        return settlePrice;
    }
    
    public void setSettlePrice(BigDecimal settlePrice) {
        this.settlePrice = settlePrice;
    }
    
    public String getSettleStatus() {
        return settleStatus;
    }
    
    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus;
    }
    
    public BigDecimal getTax() {
        return tax;
    }
    
    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
    
    public BigDecimal getBuyRefundPrice() {
        return buyRefundPrice;
    }
    
    public void setBuyRefundPrice(BigDecimal buyRefundPrice) {
        this.buyRefundPrice = buyRefundPrice;
    }
    
    public BigDecimal getDepGrossProfit() {
        return depGrossProfit;
    }
    
    public void setDepGrossProfit(BigDecimal depGrossProfit) {
        this.depGrossProfit = depGrossProfit;
    }
    
    public BigDecimal getGrossProfit() {
        return grossProfit;
    }
    
    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }
    
    public String getSaler() {
        return saler;
    }
    
    public void setSaler(String saler) {
        this.saler = saler;
    }
    
    public String getDep() {
        return dep;
    }
    
    public void setDep(String dep) {
        this.dep = dep;
    }
    
    public String getTicketDep() {
        return ticketDep;
    }
    
    public void setTicketDep(String ticketDep) {
        this.ticketDep = ticketDep;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerCompany() {
        return customerCompany;
    }
    
    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }
    
    public String getSettleWay() {
        return settleWay;
    }
    
    public void setSettleWay(String settleWay) {
        this.settleWay = settleWay;
    }
    
    public String getCustomerNo() {
        return customerNo;
    }
    
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
    
    public String getSourceChannel() {
        return sourceChannel;
    }
    
    public void setSourceChannel(String sourceChannel) {
        this.sourceChannel = sourceChannel;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getTicketNo() {
        
        return ticketNo;
    }
    
    public void setTicketNo(String ticketNo) {
        
        this.ticketNo = ticketNo;
        this.ticketNum = getTicketNum(ticketNo);
    }
    
    public String getAirLine() {
        
        return airLine;
    }
    
    public void setAirLine(String airLine) {
        
        this.airLine = airLine;
    }
    
    public BigDecimal getSaleFare() {
        
        return saleFare;
    }
    
    public void setSaleFare(BigDecimal saleFare) {
        
        this.saleFare = saleFare;
    }
    
    public BigDecimal getSaleTax() {
        
        return saleTax;
    }
    
    public void setSaleTax(BigDecimal saleTax) {
        
        this.saleTax = saleTax;
    }
    
    public BigDecimal getSaleAgencyFee() {
        
        return saleAgencyFee;
    }
    
    public void setSaleAgencyFee(BigDecimal saleAgencyFee) {
        
        this.saleAgencyFee = saleAgencyFee;
    }
    
    public String getAirRoute() {
        
        return airRoute;
    }
    
    public void setAirRoute(String airRoute) {
        
        this.airRoute = airRoute;
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
    
    public String getDepTime() {
        
        return depTime;
    }
    
    public void setDepTime(String depTime) {
        
        this.depTime = depTime;
    }
    
    public String getPnr() {
        
        return pnr;
    }
    
    public void setPnr(String pnr) {
        
        this.pnr = pnr;
    }
    
    public String getContactName() {
        
        return contactName;
    }
    
    public void setContactName(String contactName) {
        
        this.contactName = contactName;
    }
    
    public String getPassengerName() {
        
        return passengerName;
    }
    
    public void setPassengerName(String passengerName) {
        
        this.passengerName = passengerName;
    }
    
    public String getSupplierName() {
        
        return supplierName;
    }
    
    public void setSupplierName(String supplierName) {
        
        this.supplierName = supplierName;
    }
    
    public String getIssueTime() {
        return issueTime;
    }
    
    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    private int getTicketNum(String ticketNos) {
        if (null != ticketNos && !"".equals(ticketNos)) {
            String[] split1 = ticketNos.split(",");
            if (split1.length <= 1) {
                //中文逗号
                String[] split2 = ticketNos.split("，");
                if (split2.length <= 1) {
                    return 1;
                } else {
                    return split2.length;
                }
            } else {
                return split1.length;
            }
        } else {
            return 0;
        }
    }
}
