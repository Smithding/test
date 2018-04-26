package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/16.
 */
public class SeparatedOrderVo implements Serializable {
    /**
     * 销售订单编号
     */
    private Long saleOrderNO;
    /**
     * 销售订单编号(分单的查询条件中的订单编号为 saleOrderNo)
     */
    private Long saleOrderNo;
    /**
     * PNR
     */
    private Long pnr;
    /**
     * 排序字段
     */
    private String sort;

    private String type;

    private String status;

    private String owner;

    public Long getSaleOrderNo() {
        return saleOrderNO;
    }

    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNO = saleOrderNo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getSaleOrderNO() {
        return saleOrderNO;
    }

    public void setSaleOrderNO(Long saleOrderNO) {
        this.saleOrderNO = saleOrderNO;
    }

    public Long getPnr() {
        return pnr;
    }

    public void setPnr(Long pnr) {
        this.pnr = pnr;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
