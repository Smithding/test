package com.tempus.gss.product.ift.api.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
public class Ift_cabin implements Serializable{
    @Id
    private Long id;
    /**
     * 政策编号
     */
    private Long policyNo;
    /**
     * 政策舱位编号
     */
    private Long policyCabinNo;
    /**
     * 舱位 舱位，多个舱位用/分隔
     */
    private String cabin;
    /**
     * 销售返点
     */
    private Double saleRebate;
    /**
     * 销售单程手续费
     */
    private Double saleOwBrokerage;
    /**
     * 销售往返手续费
     */
    private Double saleRtBrokerage;
    /**
     * 采购返点
     */
    private Double buyRebate       ;
    /**
     * 采购单程手续费
     */
    private Double buyOwBrokerage ;
    /**
     * 采购往返手续费
     */
    private Double buyRtBrokerage ;
    /**
     * 舱位等级
     */
    private String cabinGrade      ;
    /**
     * 票价
     */
    private Double price            ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(Long policyNo) {
        this.policyNo = policyNo;
    }

    public Long getPolicyCabinNo() {
        return policyCabinNo;
    }

    public void setPolicyCabinNo(Long policyCabinNo) {
        this.policyCabinNo = policyCabinNo;
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public Double getSaleRebate() {
        return saleRebate;
    }

    public void setSaleRebate(Double saleRebate) {
        this.saleRebate = saleRebate;
    }

    public Double getSaleOwBrokerage() {
        return saleOwBrokerage;
    }

    public void setSaleOwBrokerage(Double saleOwBrokerage) {
        this.saleOwBrokerage = saleOwBrokerage;
    }

    public Double getSaleRtBrokerage() {
        return saleRtBrokerage;
    }

    public void setSaleRtBrokerage(Double saleRtBrokerage) {
        this.saleRtBrokerage = saleRtBrokerage;
    }

    public Double getBuyRebate() {
        return buyRebate;
    }

    public void setBuyRebate(Double buyRebate) {
        this.buyRebate = buyRebate;
    }

    public Double getBuyOwBrokerage() {
        return buyOwBrokerage;
    }

    public void setBuyOwBrokerage(Double buyOwBrokerage) {
        this.buyOwBrokerage = buyOwBrokerage;
    }

    public Double getBuyRtBrokerage() {
        return buyRtBrokerage;
    }

    public void setBuyRtBrokerage(Double buyRtBrokerage) {
        this.buyRtBrokerage = buyRtBrokerage;
    }

    public String getCabinGrade() {
        return cabinGrade;
    }

    public void setCabinGrade(String cabinGrade) {
        this.cabinGrade = cabinGrade;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}