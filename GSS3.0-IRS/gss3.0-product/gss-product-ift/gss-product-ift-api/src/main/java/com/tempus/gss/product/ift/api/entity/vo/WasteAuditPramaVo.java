package com.tempus.gss.product.ift.api.entity.vo;

/*
 * 销售废票审核参数
 * @author fangzhuangzhan
 * @create 8:58 2018/8/22
 */

import java.io.Serializable;

public class WasteAuditPramaVo implements Serializable{

    private static final long serialVersionUID = 1L;
    private Long saleChangeNo;
    private PassengerRefundPriceVo passengerRefundPriceVo;
    private String changeRemark;

    @Override
    public String toString() {
        return "WasteAuditPramaVo{" +
                "saleChangeNo=" + saleChangeNo +
                ", passengerRefundPriceVo=" + passengerRefundPriceVo +
                ", changeRemark='" + changeRemark + '\'' +
                '}';
    }

    public Long getSaleChangeNo() {
        return saleChangeNo;
    }

    public void setSaleChangeNo(Long saleChangeNo) {
        this.saleChangeNo = saleChangeNo;
    }

    public PassengerRefundPriceVo getPassengerRefundPriceVo() {
        return passengerRefundPriceVo;
    }

    public void setPassengerRefundPriceVo(PassengerRefundPriceVo passengerRefundPriceVo) {
        this.passengerRefundPriceVo = passengerRefundPriceVo;
    }

    public String getChangeRemark() {
        return changeRemark;
    }

    public void setChangeRemark(String changeRemark) {
        this.changeRemark = changeRemark;
    }
}
