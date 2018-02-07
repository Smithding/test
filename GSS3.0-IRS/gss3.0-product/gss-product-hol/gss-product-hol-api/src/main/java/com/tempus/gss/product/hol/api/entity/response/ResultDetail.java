package com.tempus.gss.product.hol.api.entity.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 千淘酒店信息中的价格信息实体
 * Created by luofengjie on 2017/3/24.
 */
public class ResultDetail implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 房型编号
     */
    @JSONField(name = "RoomCode")
    private String roomCode;
    /**
     * 价格计划编号
     */
    @JSONField(name = "PlanCode")
    private String planCode;
    /**
     * 每日价格明细 Dict<日期(yyyy-MM-dd),价格明细>
     */
    @JSONField(name = "Prices")
    private Map<String,QTPrice> prices;
    /**
     * 取消规则
     */
    @JSONField(name = "Rule")
    private String rule;
    /**
     * 房态(>0是有房,<0是无房)
     */
    @JSONField(name = "RoomCount")
    private Integer roomCount;
    /**
     * 服务费
     */
    @JSONField(name = "ServicePrice")
    private BigDecimal servicePrice;
    /**
     * 供货分子公司编号（即实际请求千淘酒店的采购ubp编号）
     */
    @JSONField(name = "SupplierSource")
    private String supplierSource;
    /**
     * 价格策略编号
     */
    @JSONField(name = "PricePolicyID")
    private String pricePolicyID;


    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
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
}
