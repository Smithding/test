package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/16.
 */
public class SeparatedOrder implements Serializable {

    private Long id;
    /**
     * 销售单号
     */
    private Long saleOrderNo;
    /**
     * PNR编号
     */
    private Long pnr;
    /**
     * 部门名称
     */
    private String depName;
    /**
     * 当前操作人名称
     */
    private String modifierName;
    /**
     * 分销机构或公司名称
     */
    private String compName;
    /**
     * 预订人
     */
    private String customerName;
    //订单分配人
    private Long locker;
    //订单状态
    private String orderStatus;
    //销售单废退改编号
    private Long saleChangeNo;
    //ticketsenderType
    private String ticketsenderType;

    public String getTicketsenderType() {
        return ticketsenderType;
    }

    public void setTicketsenderType(String ticketsenderType) {
        this.ticketsenderType = ticketsenderType;
    }

    public Long getSaleChangeNo() {
        return saleChangeNo;
    }

    public void setSaleChangeNo(Long saleChangeNo) {
        this.saleChangeNo = saleChangeNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public Long getPnr() {
        return pnr;
    }

    public void setPnr(Long pnr) {
        this.pnr = pnr;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getLocker() {
        return locker;
    }

    public void setLocker(Long locker) {
        this.locker = locker;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
