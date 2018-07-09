package com.tempus.gss.product.ift.api.entity;

import com.tempus.tbe.entity.AvailableJourney;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 杨威 on 2016/10/13.
 */
public class QueryIBEDetail implements Serializable {
    
    /**
     * 出票航司.
     */
    private String ticketAirline;
    
    /**
     * 是否直飞.
     */
    private boolean isDirect;
    /**
     * 航程类型：1:单程; 2:往返.
     * 目前仅支持单程和往返.
     */
    private Integer legType;
    
    /**
     * 去程起飞时间.
     */
    private Date goDepTime;
    
    /**
     * 去程到达时间.
     */
    private Date goArrTime;
    
    /**
     * 去程起点机场.
     */
    private String goDepAirport;
    
    /**
     * 去程终点机场.
     */
    private String goArrAirport;
    /**
     * 回程起飞时间.
     */
    private Date backDepTime;
    
    /**
     * 回程到达时间.
     */
    private Date backArrTime;
    
    /**
     * 回程起点机场.
     */
    private String backDepAirport;
    
    /**
     * 回程终点机场.
     */
    private String backArrAirport;
    
    /**
     * 时长
     */
    private String goDuration;
    
    /**
     * 时长
     */
    private String backDuration;
    
    /**
     * 总则编号.
     */
    private long priceSpecNo;
    
    /**
     * 数据来源(b2c/b2b/cc)
     */
    private String customerType;
    
    private String goDepOD;
    private String goArrOD;
    private String backDepOD;
    private String backArrOD;

    private String pnr;

    /**
     * 退改签规则
     */
    private String fareRule;
    
    /**
     * 航线仓位价格总和.
     */
    private List<CabinsPricesTotals> cabinsPricesTotalses;
    
    /**
     * 航段具体信息.
     */
    private List<PnrPassenger> pnrPassengers;
    
    /**
     * 乘机人信息.
     */
    private List<Flight> flights;
    
    /**
     * 查询更多舱位呃参数信息，从搜索航班功能中保存下来的
     */
    private AvailableJourney availableJourney;
    /**
     * 更多舱位的json格式参数
     */
    private String availableJourneyJson;

    /**各个乘客类型数量统计*/
    private Map<String,Integer> passengerTypeCount;

    public String getFareRule() {
        return fareRule;
    }

    public void setFareRule(String fareRule) {
        this.fareRule = fareRule;
    }

    public String getAvailableJourneyJson() {
        return availableJourneyJson;
    }
    
    public void setAvailableJourneyJson(String availableJourneyJson) {
        this.availableJourneyJson = availableJourneyJson;
    }
    
    public AvailableJourney getAvailableJourney() {
        return availableJourney;
    }
    
    public void setAvailableJourney(AvailableJourney availableJourney) {
        this.availableJourney = availableJourney;
    }
    
    public String getTicketAirline() {
        
        return ticketAirline;
    }
    
    public void setTicketAirline(String ticketAirline) {
        
        this.ticketAirline = ticketAirline;
    }
    
    public boolean isDirect() {
        
        return isDirect;
    }
    
    public void setDirect(boolean direct) {
        
        isDirect = direct;
    }
    
    public Date getGoDepTime() {
        
        return goDepTime;
    }
    
    public void setGoDepTime(Date goDepTime) {
        
        this.goDepTime = goDepTime;
    }
    
    public Date getGoArrTime() {
        
        return goArrTime;
    }
    
    public void setGoArrTime(Date goArrTime) {
        
        this.goArrTime = goArrTime;
    }
    
    public String getGoDepAirport() {
        
        return goDepAirport;
    }
    
    public void setGoDepAirport(String goDepAirport) {
        
        this.goDepAirport = goDepAirport;
    }
    
    public String getGoArrAirport() {
        
        return goArrAirport;
    }
    
    public void setGoArrAirport(String goArrAirport) {
        
        this.goArrAirport = goArrAirport;
    }
    
    public Date getBackDepTime() {
        
        return backDepTime;
    }
    
    public void setBackDepTime(Date backDepTime) {
        
        this.backDepTime = backDepTime;
    }
    
    public Date getBackArrTime() {
        
        return backArrTime;
    }
    
    public void setBackArrTime(Date backArrTime) {
        
        this.backArrTime = backArrTime;
    }
    
    public String getBackDepAirport() {
        
        return backDepAirport;
    }
    
    public void setBackDepAirport(String backDepAirport) {
        
        this.backDepAirport = backDepAirport;
    }
    
    public String getBackArrAirport() {
        
        return backArrAirport;
    }
    
    public void setBackArrAirport(String backArrAirport) {
        
        this.backArrAirport = backArrAirport;
    }
    
    public List<CabinsPricesTotals> getCabinsPricesTotalses() {
        
        return cabinsPricesTotalses;
    }
    
    public void setCabinsPricesTotalses(List<CabinsPricesTotals> cabinsPricesTotalses) {
        
        this.cabinsPricesTotalses = cabinsPricesTotalses;
    }
    
    public List<Flight> getFlights() {
        
        return flights;
    }
    
    public void setFlights(List<Flight> flights) {
        
        this.flights = flights;
    }
    
    public long getPriceSpecNo() {
        
        return priceSpecNo;
    }
    
    public void setPriceSpecNo(long priceSpecNo) {
        
        this.priceSpecNo = priceSpecNo;
    }
    
    public Integer getLegType() {
        
        return legType;
    }
    
    public void setLegType(Integer legType) {
        
        this.legType = legType;
    }
    
    public String getCustomerType() {
        return customerType;
    }
    
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
    
    public String getGoDuration() {
        return goDuration;
    }
    
    public void setGoDuration(String goDuration) {
        this.goDuration = goDuration;
    }
    
    public String getBackDuration() {
        return backDuration;
    }
    
    public void setBackDuration(String backDuration) {
        this.backDuration = backDuration;
    }
    
    public String getGoDepOD() {
        return goDepOD;
    }
    
    public void setGoDepOD(String goDepOD) {
        this.goDepOD = goDepOD;
    }
    
    public String getGoArrOD() {
        return goArrOD;
    }
    
    public void setGoArrOD(String goArrOD) {
        this.goArrOD = goArrOD;
    }
    
    public String getBackDepOD() {
        return backDepOD;
    }
    
    public void setBackDepOD(String backDepOD) {
        this.backDepOD = backDepOD;
    }
    
    public String getBackArrOD() {
        return backArrOD;
    }
    
    public void setBackArrOD(String backArrOD) {
        this.backArrOD = backArrOD;
    }
    
    public List<PnrPassenger> getPnrPassengers() {
        return pnrPassengers;
    }
    
    public void setPnrPassengers(List<PnrPassenger> pnrPassengers) {
        this.pnrPassengers = pnrPassengers;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public Map<String, Integer> getPassengerTypeCount() {
        Map<String, Integer> passengerTypeMap = new HashMap<>();
        if(this.pnrPassengers!=null){
            for(PnrPassenger passengerType:this.pnrPassengers){
                Integer num = passengerTypeMap.get(passengerType.getPassengerstype());
                if(num==null){
                    passengerTypeMap.put(passengerType.getPassengerstype(),1);
                }else{
                    passengerTypeMap.put(passengerType.getPassengerstype(),num+1);
                }
            }
        }
        return passengerTypeMap;
    }

    public void setPassengerTypeCount(Map<String,Integer> passengerTypeCount) {
        this.passengerTypeCount = passengerTypeCount;
    }
}
