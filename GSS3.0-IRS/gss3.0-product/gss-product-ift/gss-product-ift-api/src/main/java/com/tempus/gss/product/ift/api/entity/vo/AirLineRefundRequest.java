package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhi.li
 * @create 2018-07-10 17:49
 **/
public class AirLineRefundRequest implements Serializable{

    private static final long serialVersionUID = -6757495586427467663L;
    private Long auditSaleChangeNo;
    private Long buyChangeNo;
    private Long customerNo;
    private Long customerTypeNo;
    private List<AirLineRefundVo> refundVoList;
    private String remark;

    public Long getAuditSaleChangeNo() {
        return auditSaleChangeNo;
    }

    public void setAuditSaleChangeNo(Long auditSaleChangeNo) {
        this.auditSaleChangeNo = auditSaleChangeNo;
    }

    public List<AirLineRefundVo> getRefundVoList() {
        return refundVoList;
    }

    public void setRefundVoList(List<AirLineRefundVo> refundVoList) {
        this.refundVoList = refundVoList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }

    public Long getCustomerTypeNo() {
        return customerTypeNo;
    }

    public void setCustomerTypeNo(Long customerTypeNo) {
        this.customerTypeNo = customerTypeNo;
    }

    public Long getBuyChangeNo() {
        return buyChangeNo;
    }

    public void setBuyChangeNo(Long buyChangeNo) {
        this.buyChangeNo = buyChangeNo;
    }
}
