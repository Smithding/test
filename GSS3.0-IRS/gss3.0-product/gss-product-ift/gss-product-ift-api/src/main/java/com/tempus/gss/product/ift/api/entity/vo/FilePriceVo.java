package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
public class FilePriceVo implements Serializable {

    private  String id;
    /**
     * 出票航司
     */
    private String airline;

    /**
     * 生效日期
     */
    private String effectStartDate;

    /**
     * 去程起点
     */
    private String goStart;

    /**
     * 去/回折返点
     */
    private String goEnd;

    /**
     * 回程终点
     */
    private String backEnd;

    /**
     * 投放分销商
     */
    private String distributor;

    /**
     * 产品商
     */
    private String productor;

    /**
     * 产品编号
     */
    private String productNo;

    /**
     * 销售配置
     */
    private String saleConfig;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 政策id
     */
    private Long policyNo;

    /**
     * 删除标志 false标识无效的数据 true代表有效
     */
    private Integer valid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getEffectStartDate() {
        return effectStartDate;
    }

    public void setEffectStartDate(String effectStartDate) {
        this.effectStartDate = effectStartDate;
    }

    public String getGoStart() {
        return goStart;
    }

    public void setGoStart(String goStart) {
        this.goStart = goStart;
    }

    public String getGoEnd() {
        return goEnd;
    }

    public void setGoEnd(String goEnd) {
        this.goEnd = goEnd;
    }

    public String getBackEnd() {
        return backEnd;
    }

    public void setBackEnd(String backEnd) {
        this.backEnd = backEnd;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getSaleConfig() {
        return saleConfig;
    }

    public void setSaleConfig(String saleConfig) {
        this.saleConfig = saleConfig;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(Long policyNo) {
        this.policyNo = policyNo;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }
}
