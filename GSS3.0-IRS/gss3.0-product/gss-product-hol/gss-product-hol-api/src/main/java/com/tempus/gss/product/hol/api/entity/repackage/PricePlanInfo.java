package com.tempus.gss.product.hol.api.entity.repackage;

import com.tempus.gss.product.hol.api.entity.response.QTPrice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 价格计划信息
 * Created by luofengjie on 2017/3/29.
 */
public class PricePlanInfo implements Serializable{
    /**
     * 策略编号（暂时做供应商名称）
     */
    private String supplierCode;
    /**
     * 价格计划编号
     */
    private String planCode;
    /**
     * 价格计划名称
     */
    private String planName;
    /**
     * 早餐
     */
    private String breakfast;
    /**
     * 暂时未使用
     */
    private String limit;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否协议产品
     */
    private Boolean agreement;
    /**
     * 1(不可控房,必须立刻支付) 0可提前控房
     */
    private String payNow;
    /**
     * 是否是特价(1是,0不是)
     */
    private String isSpeical;
    /**
     * 支付类型(0,预付,1 面付)
     */
    private Integer payType;
    /**
     * 0 api供应商 1 协议  2 自有
     */
    private Integer source;

    /**
     * 房型编号
     */
    private String roomCode;

    /**
     * 每日价格明细 Dict<日期(yyyy-MM-dd),价格明细>
     */
    private Map<String,QTPrice> prices;
    /**
     * 取消规则
     */
    private String rule;
    /**
     * 房态(>1是有房,<1是无房)
     */
    private Integer roomCount;
    /**
     * 服务费
     */
    private BigDecimal servicePrice;
    /**
     * 供货分子公司编号
     */
    private String supplierSource;
    /**
     * 价格策略编号
     */
    private String pricePolicyID;

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getAgreement() {
        return agreement;
    }

    public void setAgreement(Boolean agreement) {
        this.agreement = agreement;
    }

    public String getPayNow() {
        return payNow;
    }

    public void setPayNow(String payNow) {
        this.payNow = payNow;
    }

    public String getIsSpeical() {
        return isSpeical;
    }

    public void setIsSpeical(String isSpeical) {
        this.isSpeical = isSpeical;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public Map<String, QTPrice> getPrices() {
        return prices;
    }

    public void setPrices(Map<String, QTPrice> prices) {
        this.prices = prices;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
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

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }
}
