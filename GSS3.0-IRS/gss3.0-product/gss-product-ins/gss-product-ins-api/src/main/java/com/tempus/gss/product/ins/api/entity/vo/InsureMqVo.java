package com.tempus.gss.product.ins.api.entity.vo;

import com.tempus.gss.vo.Agent;

public class InsureMqVo {
    /**
     * 交易单号
     */
    private long transactionNo;
    /**
     * 交易单号
     */
    private long saleOrderNo;
    /**
     * 交易单号
     */
    private Agent agent;

    public long getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(long transactionNo) {
        this.transactionNo = transactionNo;
    }

    public long getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
