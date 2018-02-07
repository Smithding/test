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
     * PNR
     */
    private Long pnr;
    /**
     * 排序字段
     */
    private String sort;

    private String type;

    private String status;

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
