package com.tempus.gss.product.hol.api.entity.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 千淘酒店信息中的价格计划信息实体
 * Created by luofengjie on 2017/3/24.
 */
public class QTPricePlan implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 价格计划编号
     */
    @JSONField(name = "PlanCode")
    private String planCode;
    /**
     * 价格计划名称
     */
    @JSONField(name = "PlanName")
    private String planName;
    /**
     * 早餐
     */
    @JSONField(name = "Breakfast")
    private String breakfast;
    /**
     * 暂时未使用
     */
    @JSONField(name = "Limit")
    private String limit;
    /**
     * 备注
     */
    @JSONField(name = "Remark")
    private String remark;
    /**
     * 是否协议产品
     */
    @JSONField(name = "Agreement")
    private Boolean agreement;
    /**
     * 1(不可控房,必须立刻支付) 0可提前控房
     */
    @JSONField(name = "PayNow")
    private String payNow;
    /**
     * 是否是特价(1是,0不是)
     */
    @JSONField(name = "IsSpeical")
    private String isSpeical;
    /**
     * 支付类型(0,预付,1 面付)
     */
    @JSONField(name = "PayType")
    private Integer payType;
    /**
     * 0 api供应商 1 协议  2 自有
     */
    @JSONField(name = "Source")
    private Integer source;



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
}
