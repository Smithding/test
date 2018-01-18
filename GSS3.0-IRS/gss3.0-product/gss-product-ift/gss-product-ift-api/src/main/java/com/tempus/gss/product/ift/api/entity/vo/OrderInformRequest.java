package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/8/30 0030.
 */
public class OrderInformRequest implements Serializable {
    /**
     * 票号
     */
    private String ticketNo;
    /**
     * 采购票价
     */
    private BigDecimal buyFare;
    /**
     * 采购税费
     */
    private BigDecimal buyTax;
    /**
     * 采购代理费
     */
    private BigDecimal buyAgencyFee;
    /**
     * 采购后返
     */
    private BigDecimal buyRebate;

    /**
     * 采购计奖价
     */
    private BigDecimal buyAwardPrice;
    /**
     * 采购结算价
     */
    private BigDecimal buyPrice;
    /**
     * 订单编号
     */
    private Long saleOrderNo;
    /**
     * 销售备注
     */
    private String saleRemark;
    /**
     * 采购商编号（客商）
     */
    private Long supplierNo;
    /**
     * 销售明细状态 1（待核价），2（已核价），3（出票中），4（已出票）5（退票中），6（废票中），7（改签中） 8（已退），9（已废），10（已改签）.11(已取消).12（已挂起)
     */
    private String status;
    /**
     * 采购手续费
     */
    private BigDecimal buyBrokerage;
    /**
     * 业务类型 1废 2退 3改(同SaleChange的 orderChangeType 属性)
     */
    private Integer changeType;
    /**
     * 拒绝原因
     */
    private String refuseReason;
    /**
     * 销售税费
     */
    private BigDecimal saleTax;
    /**
     * 采购退票费
     */
    private BigDecimal buyRefundPrice;
    /**
     * 采购退票结算价
     */
    private BigDecimal buyFefundAccount;
    /**
     * 退票手续费
     */
    private BigDecimal saleRefundPrice;
    /**
     * 政策编号
     */
    private Long policyNo;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public BigDecimal getBuyFare() {
        return buyFare;
    }

    public void setBuyFare(BigDecimal buyFare) {
        this.buyFare = buyFare;
    }

    public BigDecimal getBuyTax() {
        return buyTax;
    }

    public void setBuyTax(BigDecimal buyTax) {
        this.buyTax = buyTax;
    }

    public BigDecimal getBuyAgencyFee() {
        return buyAgencyFee;
    }

    public void setBuyAgencyFee(BigDecimal buyAgencyFee) {
        this.buyAgencyFee = buyAgencyFee;
    }

    public BigDecimal getBuyRebate() {
        return buyRebate;
    }

    public void setBuyRebate(BigDecimal buyRebate) {
        this.buyRebate = buyRebate;
    }

    public BigDecimal getBuyAwardPrice() {
        return buyAwardPrice;
    }

    public void setBuyAwardPrice(BigDecimal buyAwardPrice) {
        this.buyAwardPrice = buyAwardPrice;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Long getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public String getSaleRemark() {
        return saleRemark;
    }

    public void setSaleRemark(String saleRemark) {
        this.saleRemark = saleRemark;
    }

    public Long getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(Long supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getBuyBrokerage() {
        return buyBrokerage;
    }

    public void setBuyBrokerage(BigDecimal buyBrokerage) {
        this.buyBrokerage = buyBrokerage;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public BigDecimal getSaleTax() {
        return saleTax;
    }

    public void setSaleTax(BigDecimal saleTax) {
        this.saleTax = saleTax;
    }

    public BigDecimal getBuyRefundPrice() {
        return buyRefundPrice;
    }

    public void setBuyRefundPrice(BigDecimal buyRefundPrice) {
        this.buyRefundPrice = buyRefundPrice;
    }

    public BigDecimal getBuyFefundAccount() {
        return buyFefundAccount;
    }

    public void setBuyFefundAccount(BigDecimal buyFefundAccount) {
        this.buyFefundAccount = buyFefundAccount;
    }

    public BigDecimal getSaleRefundPrice() {
        return saleRefundPrice;
    }

    public void setSaleRefundPrice(BigDecimal saleRefundPrice) {
        this.saleRefundPrice = saleRefundPrice;
    }

    public Long getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(Long policyNo) {
        this.policyNo = policyNo;
    }
}
