package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Array.newInstance;

/**
 * @author ZhangBro
 * 查询条件
 */
public class IFTIssueReportParams implements Serializable {
    
    private static final long serialVersionUID = -6393082115652051244L;
    /**
     * 精确查找  在sql条件中不使用like关键字
     */
    private String exactQuery;
    /**
     * 供应商
     */
    private String supplierName;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 出票日期
     */
    private String ticketDateStart;
    private String ticketDateEnd;
    /**
     * 单号
     */
    private String saleOrderNo;
    /**
     * 票号
     */
    private String ticketNo;
    /**
     * 航程
     */
    private String routing;
    /**
     * 航班号
     */
    private String flightNo;
    /**
     * 舱位
     */
    private String[] cabins;
    /**
     * 起飞日期
     */
    private String depDateStart;
    private String depDateEnd;
    /**
     * 售票员
     */
    private String salePerson;
    /**
     * 出票部门
     */
    private String[] saleDept;
    /**
     * 出票员
     */
    private String ticketPerson;
    /**
     * 所属公司
     */
    private String company;
    /**
     * 旅客姓名
     */
    private String passengerName;
    /**
     * 姓
     */
    private String surname;
    /**
     * 名
     */
    private String name;
    
    /**
     * 订单来源
     */
    private String[] source;
    /**
     * 多状态查询条件
     */
    private Integer[] orderStatusArray;
    
    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer[] getOrderStatusArray() {
        return orderStatusArray;
    }
    
    public void setOrderStatusArray(Integer[] orderStatusArray) {
        this.orderStatusArray = filterNull(orderStatusArray, Integer.class);
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public String getExactQuery() {
        return exactQuery;
    }
    
    public void setExactQuery(String exactQuery) {
        this.exactQuery = exactQuery;
    }
    
    public String getTicketPerson() {
        return ticketPerson;
    }
    
    public void setTicketPerson(String ticketPerson) {
        this.ticketPerson = ticketPerson;
    }
    
    public String getTicketNo() {
        return ticketNo;
    }
    
    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }
    
    public String getSupplierName() {
        return supplierName;
    }
    
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
    public String getSaleOrderNo() {
        return saleOrderNo;
    }
    
    public void setSaleOrderNo(String saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }
    
    public String getRouting() {
        return routing;
    }
    
    public void setRouting(String routing) {
        this.routing = routing;
    }
    
    public String getFlightNo() {
        return flightNo;
    }
    
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }
    
    public String getTicketDateStart() {
        return ticketDateStart;
    }
    
    public void setTicketDateStart(String ticketDateStart) {
        this.ticketDateStart = ticketDateStart;
    }
    
    public String getTicketDateEnd() {
        return ticketDateEnd;
    }
    
    public void setTicketDateEnd(String ticketDateEnd) {
        this.ticketDateEnd = ticketDateEnd;
    }
    
    public String getDepDateStart() {
        return depDateStart;
    }
    
    public void setDepDateStart(String depDateStart) {
        this.depDateStart = depDateStart;
    }
    
    public String getDepDateEnd() {
        return depDateEnd;
    }
    
    public void setDepDateEnd(String depDateEnd) {
        this.depDateEnd = depDateEnd;
    }
    
    public String getSalePerson() {
        return salePerson;
    }
    
    public void setSalePerson(String salePerson) {
        this.salePerson = salePerson;
    }
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getPassengerName() {
        return passengerName;
    }
    
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    
    public Integer getType() {
        return type;
    }
    
    public String[] getCabins() {
        return cabins;
    }
    
    public void setCabins(String[] cabins) {
        
        this.cabins = filterNull(cabins, String.class);
    }
    
    public String[] getSaleDept() {
        return saleDept;
    }
    
    public void setSaleDept(String[] saleDept) {
        this.saleDept = filterNull(saleDept, String.class);
    }
    
    public String[] getSource() {
        return source;
    }
    
    public void setSource(String[] source) {
        this.source = filterNull(source, String.class);
    }
    
    private <T> T[] filterNull(T[] source, Class<T> type) {
        if (null == source || source.length <= 0) {
            return null;
        } else {
            List<T> list = new ArrayList<T>();
            T[] result;
            
            boolean notNull;
            for (T i : source) {
                notNull = false;
                if (i != null) {
                    if (i instanceof String) {
                        if (!"".equals(i)) {
                            notNull = true;
                        }
                    }
                    if (notNull) {
                        list.add(i);
                    }
                    
                }
            }
            if (list.size() > 0) {
                result = (T[]) newInstance(type, list.size());
                for (int i = 0; i < list.size(); i++) {
                    result[i] = list.get(i);
                }
                return result;
            } else {
                return null;
            }
        }
    }
}

