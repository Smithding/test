package com.tempus.gss.product.ift.api.entity.iftVo;

import java.io.Serializable;
import java.math.BigDecimal;

public class IftRepayVo implements Serializable {

    private static final long serialVersionUID = -7186884541568324792L;

    private Long bussinessNo;

    private BigDecimal amount;

    private Integer businessType;

    @Override
    public String toString() {
        return "IftRepayVo{" +
                "bussinessNo=" + bussinessNo +
                ", amount=" + amount +
                ", businessType=" + businessType +
                '}';
    }

    public Long getBussinessNo() {
        return bussinessNo;
    }

    public void setBussinessNo(Long bussinessNo) {
        this.bussinessNo = bussinessNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }
}
