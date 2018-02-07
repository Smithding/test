package com.tempus.gss.product.ift.api.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.List;

/**
 * 白屏查询请求.
 */
public class FlightQueryRequest implements Serializable {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 航司.
     * 可以为空,表示不限.
     * 多个用 “/”分隔。
     */
    private String airline;
    
    /**
     * 舱位等级.
     * 可为空，Y:经济舱，C：公务舱，F：头等舱。
     */
    private String grade;
    
    /**
     * 航程类型：1:单程; 2:往返.
     * 目前仅支持单程和往返.
     */
    private Integer legType;
    
    /**
     * 起点机场.
     */
    private String depAirport;
    /**
     * 出发航班
     */
    private String depAirline;
    /**
     * 回程航班
     */
    private String returnAirline;
    /**
     * 终点机场.
     */
    private String arrAirport;
    
    /**
     * 起飞日期.
     * 格式为：2016-09-09
     */
    private String depDate;
    
    /**
     * 回程日期.
     * 格式为：2016-09-09
     */
    private String returnDate;
    
    /**
     * 成人数量;
     */
    private Integer adultCount;
    
    /**
     * 儿童数量;
     */
    private Integer childCount;
    
    /**
     * 婴儿数量;
     */
    private Integer infantCount;
    
    /**
     * 是否仅限直飞.
     */
    private boolean onlyDirect;
    
    /**
     * 数据来源(b2c/b2b/cc)
     */
    private String customerType;
    
    /**
     * 客户编号
     */
    @JsonSerialize(using = LongSerializer.class)
    protected Long customerNo;
    
    private List<String> transfers;
    
    /**
     * 更多参数   json类型字符串
     */
    private String json;
    
    // <editor-fold desc="getters and setters">
    public String getDepAirline() {
        return depAirline;
    }
    
    public void setDepAirline(String depAirline) {
        this.depAirline = depAirline;
    }
    
    public String getReturnAirline() {
        return returnAirline;
    }
    
    public void setReturnAirline(String returnAirline) {
        this.returnAirline = returnAirline;
    }
    
    public String getJson() {
        return json;
    }
    
    public void setJson(String json) {
        this.json = json;
    }
    
    public String getAirline() {
        
        return airline;
    }
    
    public void setAirline(String airline) {
        
        this.airline = airline;
    }
    
    public String getGrade() {
        
        return grade;
    }
    
    public void setGrade(String grade) {
        
        this.grade = grade;
    }
    
    public Integer getLegType() {
        
        return legType;
    }
    
    public void setLegType(Integer legType) {
        
        this.legType = legType;
    }
    
    public String getDepAirport() {
        
        return depAirport;
    }
    
    public void setDepAirport(String depAirport) {
        
        this.depAirport = depAirport;
    }
    
    public String getArrAirport() {
        
        return arrAirport;
    }
    
    public void setArrAirport(String arrAirport) {
        
        this.arrAirport = arrAirport;
    }
    
    public String getDepDate() {
        
        return depDate;
    }
    
    public void setDepDate(String depDate) {
        
        this.depDate = depDate;
    }
    
    public String getReturnDate() {
        
        return returnDate;
    }
    
    public void setReturnDate(String returnDate) {
        
        this.returnDate = returnDate;
    }
    
    public Integer getAdultCount() {
        
        return adultCount;
    }
    
    public void setAdultCount(Integer adultCount) {
        
        this.adultCount = adultCount;
    }
    
    public Integer getChildCount() {
        
        return childCount;
    }
    
    public void setChildCount(Integer childCount) {
        
        this.childCount = childCount;
    }
    
    public Integer getInfantCount() {
        
        return infantCount;
    }
    
    public void setInfantCount(Integer infantCount) {
        
        this.infantCount = infantCount;
    }
    
    public boolean isOnlyDirect() {
        
        return onlyDirect;
    }
    
    public void setOnlyDirect(boolean onlyDirect) {
        
        this.onlyDirect = onlyDirect;
    }
    
    public Long getCustomerNo() {
        
        return customerNo;
    }
    
    public void setCustomerNo(Long customerNo) {
        
        this.customerNo = customerNo;
    }
    
    public String getCustomerType() {
        return customerType;
    }
    
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
    
    public List<String> getTransfers() {
        return transfers;
    }
    
    public void setTransfers(List<String> transfers) {
        this.transfers = transfers;
    }
}
//</editor-fold>