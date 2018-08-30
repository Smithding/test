package com.tempus.gss.product.unp.api.entity.vo;

import java.io.Serializable;
import java.util.Date;

public class SaleReportQueryVo implements Serializable {
    // 0 正常票 1 退票
    private Integer changeType;
    //产品
    private Long itemTypeNo;
    //查询起始结束时间
    private Date ticketDateStart;
    private Date ticketDateEnd;

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeTye) {
        changeType = changeType;
    }

    public Long getItemTypeNo() {
        return itemTypeNo;
    }

    public void setItemTypeNo(Long itemTypeNo) {
        this.itemTypeNo = itemTypeNo;
    }

    public Date getTicketDateStart() {
        return ticketDateStart;
    }

    public void setTicketDateStart(Date ticketDateStart) {
        this.ticketDateStart = ticketDateStart;
    }

    public Date getTicketDateEnd() {
        return ticketDateEnd;
    }

    public void setTicketDateEnd(Date ticketDateEnd) {
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
