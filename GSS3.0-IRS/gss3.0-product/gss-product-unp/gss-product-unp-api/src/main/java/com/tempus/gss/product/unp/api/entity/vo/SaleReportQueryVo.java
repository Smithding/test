package com.tempus.gss.product.unp.api.entity.vo;

import java.io.Serializable;
import java.util.Date;

public class SaleReportQueryVo implements Serializable {
    // 0 正常票 1 退票
    private Integer changeType;
    //产品
    private Long itemTypeNo;
    //查询起始结束时间
    private String ticketDateStart;
    private String ticketDateEnd;

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public Long getItemTypeNo() {

        return itemTypeNo;
    }

    public void setItemTypeNo(Long itemTypeNo) {
        this.itemTypeNo = itemTypeNo;
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

    @Override
    public String toString() {
        return "SaleReportQueryVo{" +
                "changeType=" + changeType +
                ", itemTypeNo=" + itemTypeNo +
                ", ticketDateStart=" + ticketDateStart +
                ", ticketDateEnd=" + ticketDateEnd +
                '}';
    }
}
