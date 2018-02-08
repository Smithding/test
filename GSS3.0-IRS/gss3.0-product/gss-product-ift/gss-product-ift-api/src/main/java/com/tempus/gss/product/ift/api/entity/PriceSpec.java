package com.tempus.gss.product.ift.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 算价规则（总则）.
 */

public class PriceSpec extends RequestWithActor<PriceSpec> implements Serializable {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer owner;
    
    @JsonSerialize(using = LongSerializer.class)
    private Long priceSpecNo;
    
    /**
     * 出票航司. 可为*号或单个航司，每个航司一个总则。*号也只能一个。
     */
    private String airline;
    
    // <editor-fold desc="无奖励设置">
    /**
     * 旅客类型无奖励: All（不限）,ADT（成人），INF（婴儿），CHD（儿童）， STU（学生），SAI（海员），
     * MIG（移民），LAB（劳工），YTH（青年），SEN（老年），HOM（探亲） 其他旅客类型三字码保存时用 “/”分隔
     */
    private String noAwardType;
    
    /**
     * SOTO票无奖励.
     */
    private boolean noAwardSoto;
    
    /**
     * IT票无奖励.
     */
    private boolean noAwardIt;
    
    /**
     * OPEN票无奖励.
     */
    private boolean noAwardOpen;
    
    /**
     * 包含无奖励的舱位：A/B/C/M/N， 保存时用 “/”分隔.
     */
    private String noAwardCabin;
    
    /**
     * 包含无奖励的票价基础， 保存时用 “/”分隔.
     */
    private String noAwardFareBase;
    
    /**
     * 包含无奖励的TourCode， 保存时用 “/”分隔.
     */
    private String noAwardTourCode;
    
    /**
     * 包含无奖励的航班号， 保存时用 “/”分隔.
     */
    private String noAwardFlightNo;
    
    /**
     * 票价低于设定值无奖励.
     */
    private BigDecimal noAwardBelowFare;
    // </editor-fold>
    
    // <editor-fold desc="OD设置">
    /**
     * 去程起点类型. 1： 出票航第一个航段的起点 2：出票航自承运第一个航段起点 3：第一个跨国段的起点
     */
    private Integer goDep;
    
    /**
     * 去程终点/回程起点类型. 1： 实际行程的折返点 2： 去程/回程出票航的最远点 3： 去程主航段终点/回程主航段起点
     */
    private Integer middle;
    
    /**
     * 回程终点. 1： 出票航最后一个航段的终点 2： 出票航自承运的最后一个航段终点 3： 第一个跨国段的终点
     */
    private Integer backArr;
    // </editor-fold>
    
    /**
     * 价格选取方式. 1：仅返回数据日期最新的 2：仅返回销售价最高的 3：仅返回销售价最低的
     */
    private Integer priorityPrice;
    
    /**
     * 价格公式计算的参数. 将参数转换为json后保存.
     */
    private String formulaData;
    
    /**
     * 价格公式计算的参数对象.
     */
    private com.tempus.gss.product.ift.api.entity.formula.Formula Formula;
    
    /**
     * 删除标记
     */
    private boolean valid;
    /**
     * 操作日志
     */
    private String log;
    
    private java.util.Date createTime;
    private java.util.Date modifyTime;
    
    private String creator;
    private String modifier;
    
    // get set方法
    
    public String getLog() {
        return log;
    }
    
    public void setLog(String log) {
        this.log = log;
    }
    
    public Long getId() {
        
        return id;
    }
    
    public void setId(Long id) {
        
        this.id = id;
    }
    
    public Integer getOwner() {
        
        return owner;
    }
    
    public void setOwner(Integer owner) {
        
        this.owner = owner;
    }
    
    public Long getPriceSpecNo() {
        
        return priceSpecNo;
    }
    
    public void setPriceSpecNo(Long priceSpecNo) {
        
        this.priceSpecNo = priceSpecNo;
    }
    
    public String getAirline() {
        
        return airline;
    }
    
    public void setAirline(String airline) {
        
        this.airline = airline;
    }
    
    public String getNoAwardType() {
        
        return noAwardType;
    }
    
    public void setNoAwardType(String noAwardType) {
        
        this.noAwardType = noAwardType;
    }
    
    public boolean isNoAwardSoto() {
        
        return noAwardSoto;
    }
    
    public void setNoAwardSoto(boolean noAwardSoto) {
        
        this.noAwardSoto = noAwardSoto;
    }
    
    public boolean isNoAwardIt() {
        
        return noAwardIt;
    }
    
    public void setNoAwardIt(boolean noAwardIt) {
        
        this.noAwardIt = noAwardIt;
    }
    
    public boolean isNoAwardOpen() {
        
        return noAwardOpen;
    }
    
    public void setNoAwardOpen(boolean noAwardOpen) {
        
        this.noAwardOpen = noAwardOpen;
    }
    
    public String getNoAwardCabin() {
        
        return noAwardCabin;
    }
    
    public void setNoAwardCabin(String noAwardCabin) {
        
        this.noAwardCabin = noAwardCabin;
    }
    
    public String getNoAwardFareBase() {
        
        return noAwardFareBase;
    }
    
    public void setNoAwardFareBase(String noAwardFareBase) {
        
        this.noAwardFareBase = noAwardFareBase;
    }
    
    public String getNoAwardTourCode() {
        
        return noAwardTourCode;
    }
    
    public void setNoAwardTourCode(String noAwardTourCode) {
        
        this.noAwardTourCode = noAwardTourCode;
    }
    
    public String getNoAwardFlightNo() {
        
        return noAwardFlightNo;
    }
    
    public void setNoAwardFlightNo(String noAwardFlightNo) {
        
        this.noAwardFlightNo = noAwardFlightNo;
    }
    
    public BigDecimal getNoAwardBelowFare() {
        
        return noAwardBelowFare;
    }
    
    public void setNoAwardBelowFare(BigDecimal noAwardBelowFare) {
        
        this.noAwardBelowFare = noAwardBelowFare;
    }
    
    public Integer getGoDep() {
        
        return goDep;
    }
    
    public void setGoDep(Integer goDep) {
        
        this.goDep = goDep;
    }
    
    public Integer getMiddle() {
        
        return middle;
    }
    
    public void setMiddle(Integer middle) {
        
        this.middle = middle;
    }
    
    public Integer getBackArr() {
        
        return backArr;
    }
    
    public void setBackArr(Integer backArr) {
        
        this.backArr = backArr;
    }
    
    public Integer getPriorityPrice() {
        
        return priorityPrice;
    }
    
    public void setPriorityPrice(Integer priorityPrice) {
        
        this.priorityPrice = priorityPrice;
    }
    
    public String getFormulaData() {
        
        return formulaData;
    }
    
    public void setFormulaData(String formulaData) {
        
        this.formulaData = formulaData;
    }
    
    public com.tempus.gss.product.ift.api.entity.formula.Formula getFormula() {
        
        return Formula;
    }
    
    public void setFormula(com.tempus.gss.product.ift.api.entity.formula.Formula formula) {
        
        Formula = formula;
    }
    
    public boolean isValid() {
        
        return valid;
    }
    
    public void setValid(boolean valid) {
        
        this.valid = valid;
    }
    
    public java.util.Date getCreateTime() {
        
        return createTime;
    }
    
    public void setCreateTime(java.util.Date createTime) {
        
        this.createTime = createTime;
    }
    
    public java.util.Date getModifyTime() {
        
        return modifyTime;
    }
    
    public void setModifyTime(java.util.Date modifyTime) {
        
        this.modifyTime = modifyTime;
    }
    
    public String getCreator() {
        
        return creator;
    }
    
    public void setCreator(String creator) {
        
        this.creator = creator;
    }
    
    public String getModifier() {
        
        return modifier;
    }
    
    public void setModifier(String modifier) {
        
        this.modifier = modifier;
    }
    
    @Override
    public String toString() {
        
        return "PriceSpec [id=" + id + ", owner=" + owner + ", priceSpecNo=" + priceSpecNo + ", airline=" + airline + ", noAwardType=" + noAwardType + ", noAwardSoto=" + noAwardSoto + ", noAwardIt=" + noAwardIt + ", noAwardOpen=" + noAwardOpen + ", noAwardCabin=" + noAwardCabin + ", noAwardFareBase=" + noAwardFareBase + ", noAwardTourCode=" + noAwardTourCode + ", noAwardFlightNo=" + noAwardFlightNo + ", noAwardBelowFare=" + noAwardBelowFare + ", goDep=" + goDep + ", middle=" + middle + ", backArr=" + backArr + ", priorityPrice=" + priorityPrice + ", formulaData=" + formulaData + ", Formula=" + Formula + ", valid=" + valid + ", createTime=" + createTime + ", modifyTime=" + modifyTime + ", creator=" + creator + ", modifier=" + modifier + "]";
    }
    
}
