package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 航司退款审核实体
 *
 * @author zhi.li
 * @create 2018-07-10 16:34
 **/
public class AirLineRefundVo implements Serializable{

    private static final long serialVersionUID = 8614035690745927185L;
    //采收实收金额
    private BigDecimal payment;
    //支付账号
    private String accountNo;
    //到账日期
    private Date arrivalTime;
    //流水账号
    private String capitalFow;

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCapitalFow() {
        return capitalFow;
    }

    public void setCapitalFow(String capitalFow) {
        this.capitalFow = capitalFow;
    }
}
