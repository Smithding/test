package com.tempus.gss.product.hol.api.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

;

/**
 * 订单内部信息
 * Created by luofengjie on 2017/3/31.
 */
public class BdOrderInn implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    private String orderNumber;

    private int innStatus;


    /**
     * 供应商订单编号
     */
    private String supplierOrderNumber;

    private String remark;

    /**
     * 成本
     */
    private BigDecimal cost;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 客户名称
     */
    private String customerName;

    private String lastUpdateDate;


    private String accountStep;


    private int isManual;


    private int isCheck;


    private String payTypeCode;


    private String payTypeName;


    private String hotelConfirmNo;


    private BigDecimal middleServicePrice;


    private BigDecimal middlePrice;

    private String customerSource;


    private String channel;


    private String customerSourceName;


    private String supplierSource;


    private String pricePolicyID;


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSupplierOrderNumber() {
        return supplierOrderNumber;
    }

    public void setSupplierOrderNumber(String supplierOrderNumber) {
        this.supplierOrderNumber = supplierOrderNumber;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
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

    public int getInnStatus() {
        return innStatus;
    }

    public void setInnStatus(int innStatus) {
        this.innStatus = innStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getAccountStep() {
        return accountStep;
    }

    public void setAccountStep(String accountStep) {
        this.accountStep = accountStep;
    }

    public int getIsManual() {
        return isManual;
    }

    public void setIsManual(int isManual) {
        this.isManual = isManual;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public String getPayTypeCode() {
        return payTypeCode;
    }

    public void setPayTypeCode(String payTypeCode) {
        this.payTypeCode = payTypeCode;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public String getHotelConfirmNo() {
        return hotelConfirmNo;
    }

    public void setHotelConfirmNo(String hotelConfirmNo) {
        this.hotelConfirmNo = hotelConfirmNo;
    }

    public BigDecimal getMiddleServicePrice() {
        return middleServicePrice;
    }

    public void setMiddleServicePrice(BigDecimal middleServicePrice) {
        this.middleServicePrice = middleServicePrice;
    }

    public BigDecimal getMiddlePrice() {
        return middlePrice;
    }

    public void setMiddlePrice(BigDecimal middlePrice) {
        this.middlePrice = middlePrice;
    }

    public String getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(String customerSource) {
        this.customerSource = customerSource;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCustomerSourceName() {
        return customerSourceName;
    }

    public void setCustomerSourceName(String customerSourceName) {
        this.customerSourceName = customerSourceName;
    }

    public String getSupplierSource() {
        return supplierSource;
    }

    public void setSupplierSource(String supplierSource) {
        this.supplierSource = supplierSource;
    }

    public String getPricePolicyID() {
        return pricePolicyID;
    }

    public void setPricePolicyID(String pricePolicyID) {
        this.pricePolicyID = pricePolicyID;
    }
}
