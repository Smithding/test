package com.tempus.gss.product.ift.api.entity.vo;

import com.tempus.gss.product.ift.api.entity.Leg;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/7/31.
 */
public class IftReportRefundVo implements Serializable {
    /**
     * pnr
     */
    private String pnr;
    /**
     * 销售部门
     */
    private String saleDept;
    /**
     * 退废单号
     */
    private Long saleChangeNo;
    /**
     * 机票状态 退 废
     */
    private Integer orderChangeType;
    /**
     * 国际机票 国内机票
     */
    private Integer goodsType;
    /**
     * 退款原因
     */
    private String refundReason;
    /**
     * 票号
     */
    private String ticketNo;
    /**
     * 乘机人
     */
    private String fltName;
    /**
     * 起飞时间
     */
    private Date depTime;
    /**
     * 票价
     */
    private BigDecimal salePrice;
    /**
     * 税费
     */
    private BigDecimal saleTax;
    /**
     * 手续费
     */
    private BigDecimal saleRefundPrice;
    /**
     * 实收金额
     */
    private BigDecimal saleAccount;
    /**
     * 应退款
     */
    private BigDecimal saleRefundAccount;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 订票员编号
     */
    private String intTicketNo;
    /**
     * 订票员名字
     */
    private String intTicketName;
    /**
     * 出票员编号
     */
    private String outTicketNo;
    /**
     * 出票名字
     */
    private String outTicketName;
    /**
     * 出票时间
     */
    private Date issueTime;
    /**
     * 退票时间
     */
    private Date refundTicketTime;
    /**
     * 退票员编号
     */
    private String refundTicketNo;
    /**
     * 退票员名字
     */
    private String refundTicketName;
    /**
     * 退款时间
     */
    private Date refundPriceTime;
    /**
     * 退款金额
     */
    private BigDecimal saleAccountPrice;
    /**
     * 退款方式
     */
    private String refundPayName;
    /**
     * 退款账户
     */
    private String refundAccount;
    /**
     * 备注
     */
    private String changeRemark;
    /**
     * 实收票款
     */
    private BigDecimal collectTicketPrice;
    /**
     * 退款部门
     */
    private String refundPriceDept;
    /**
     * 销售单号
     */
    private Long saleOrderNo;
    /**
     * 航程集合
     */
    private List<Leg> legList;

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getSaleDept() {
        return saleDept;
    }

    public void setSaleDept(String saleDept) {
        this.saleDept = saleDept;
    }

    public Long getSaleChangeNo() {
        return saleChangeNo;
    }

    public void setSaleChangeNo(Long saleChangeNo) {
        this.saleChangeNo = saleChangeNo;
    }

    public Integer getOrderChangeType() {
        return orderChangeType;
    }

    public void setOrderChangeType(Integer orderChangeType) {
        this.orderChangeType = orderChangeType;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getFltName() {
        return fltName;
    }

    public void setFltName(String fltName) {
        this.fltName = fltName;
    }

    public Date getDepTime() {
        return depTime;
    }

    public void setDepTime(Date depTime) {
        this.depTime = depTime;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getSaleTax() {
        return saleTax;
    }

    public void setSaleTax(BigDecimal saleTax) {
        this.saleTax = saleTax;
    }

    public BigDecimal getSaleRefundPrice() {
        return saleRefundPrice;
    }

    public void setSaleRefundPrice(BigDecimal saleRefundPrice) {
        this.saleRefundPrice = saleRefundPrice;
    }

    public BigDecimal getSaleAccount() {
        return saleAccount;
    }

    public void setSaleAccount(BigDecimal saleAccount) {
        this.saleAccount = saleAccount;
    }

    public BigDecimal getSaleRefundAccount() {
        return saleRefundAccount;
    }

    public void setSaleRefundAccount(BigDecimal saleRefundAccount) {
        this.saleRefundAccount = saleRefundAccount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIntTicketNo() {
        return intTicketNo;
    }

    public void setIntTicketNo(String intTicketNo) {
        this.intTicketNo = intTicketNo;
    }

    public String getIntTicketName() {
        return intTicketName;
    }

    public void setIntTicketName(String intTicketName) {
        this.intTicketName = intTicketName;
    }

    public String getOutTicketNo() {
        return outTicketNo;
    }

    public void setOutTicketNo(String outTicketNo) {
        this.outTicketNo = outTicketNo;
    }

    public String getOutTicketName() {
        return outTicketName;
    }

    public void setOutTicketName(String outTicketName) {
        this.outTicketName = outTicketName;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public Date getRefundTicketTime() {
        return refundTicketTime;
    }

    public void setRefundTicketTime(Date refundTicketTime) {
        this.refundTicketTime = refundTicketTime;
    }

    public String getRefundTicketNo() {
        return refundTicketNo;
    }

    public void setRefundTicketNo(String refundTicketNo) {
        this.refundTicketNo = refundTicketNo;
    }

    public String getRefundTicketName() {
        return refundTicketName;
    }

    public void setRefundTicketName(String refundTicketName) {
        this.refundTicketName = refundTicketName;
    }

    public Date getRefundPriceTime() {
        return refundPriceTime;
    }

    public void setRefundPriceTime(Date refundPriceTime) {
        this.refundPriceTime = refundPriceTime;
    }

    public BigDecimal getSaleAccountPrice() {
        return saleAccountPrice;
    }

    public void setSaleAccountPrice(BigDecimal saleAccountPrice) {
        this.saleAccountPrice = saleAccountPrice;
    }

    public String getRefundPayName() {
        return refundPayName;
    }

    public void setRefundPayName(String refundPayName) {
        this.refundPayName = refundPayName;
    }

    public String getRefundAccount() {
        return refundAccount;
    }

    public void setRefundAccount(String refundAccount) {
        this.refundAccount = refundAccount;
    }

    public String getChangeRemark() {
        return changeRemark;
    }

    public void setChangeRemark(String changeRemark) {
        this.changeRemark = changeRemark;
    }

    public BigDecimal getCollectTicketPrice() {
        return collectTicketPrice;
    }

    public void setCollectTicketPrice(BigDecimal collectTicketPrice) {
        this.collectTicketPrice = collectTicketPrice;
    }

    public String getRefundPriceDept() {
        return refundPriceDept;
    }

    public void setRefundPriceDept(String refundPriceDept) {
        this.refundPriceDept = refundPriceDept;
    }

    public Long getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public List<Leg> getLegList() {
        return legList;
    }

    public void setLegList(List<Leg> legList) {
        this.legList = legList;
    }
}
