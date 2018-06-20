package com.tempus.gss.product.ift.api.entity;

import com.tempus.gss.order.entity.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 国际杂费单
 *
 * @author lizhi
 * @create 2018-06-15 18:40
 **/
public class IftExtraOrder implements Serializable{

    /**
     * 差异单号
     */
    private Long differenceOrderNo;

    /**
     * 数据归属单位
     */
    private Integer owner;
    private String log;

    /**
     * 业务批次号
     */
    private Long businessSignNo;

    /**
     * 业务批次类型 1销采 2换票 3废和退 4改签
     */
    private Integer bsignType;

    /**
     * 所属部门
     */
    private String deptCode;

    /**
     * 客户类型
     */
    private Integer customerTypeNo;

    /**
     * 客户编号
     */
    private Long customerNo;

    /**
     * 应收 支付标准
     */
    private BigDecimal receivable;

    /**
     * 收支类型 1 收，2 为支
     */
    private Integer incomeExpenseType;

    /**
     * 实收
     */
    private BigDecimal received;

    /**
     * 业务单类型 2.销售单 3.采购单 4.销售变更单 5.采购变更单
     */
    private Integer businessType;

    /**
     * 业务单号
     */
    private Long businessOrderNo;

    /**
     * 商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
     */
    private Integer goodsType;

    /**
     * 商品小类
     */
    private Integer goodsSubType;

    /**
     * 商品名称 如：PEK-CKG-SHA,航空意外险等
     */
    private String goodsName;

    /**
     * 下单时间
     */
    private String orderingTime;

    /**
     * 支付状态 1 为待支付 2 支付中 3 为已支付，4 为挂帐支付
     */
    private Integer payStatus;

    /**
     * 处理人
     */
    private String actorUser;

    /**
     * 处理时间
     */
    private Date actorTime;

    private String actorTimeStr;

    /**
     * 处理说明
     */
    private String actorDesc;

    /**
     * 差异说明
     */
    private String differenceDesc;

    /**
     * 差异类型
     */
    private Integer differenceType;

    /**
     * 差异类型
     */
    private String differenceTypeName;

    /**
     * 结算状态 1.未结算 2.已结算 3.已审核
     */
    private Integer settlementStatus;

    /**
     * 收付款单号 多个以","隔开
     */
    private String certificateNo;

    /**
     * 支付方式 多个以","隔开
     */
    private String payWay;

    /**
     * 第三方支付流水 多个以","隔开
     */
    private String thirdPayNo;

    /**
     * 第三方业务编号 多个以","隔开
     */
    private String thirdBusNo;

    /**
     * 删除标志 0 无效 已删除 1 有效
     */
    private Integer valid;

    /**
     * 票号集合 多个以","隔开
     */
    private String ticketNos;
    /**
     * pnr
     */
    private String pnr;
    /**
     * TRA_NO
     */
    private Long traNo;
    /**
     * PAY_TIME
     */
    private Date payTime;
    /**
     * PAY_TIME
     */
    private String payTimeStr;
    /**
     * PAY_NO
     */
    private String payNo;

    public Long getTraNo() {
        return traNo;
    }

    public String getActorTimeStr() {
        return actorTimeStr;
    }

    public String getDifferenceTypeName() {
        if (null != differenceType) {
            return EIncidentalType.format(differenceType).getValue();
        } else {
            return differenceTypeName;
        }
    }

    public void setDifferenceTypeName(String differenceTypeName) {
        this.differenceTypeName = differenceTypeName;
    }

    public void setActorTimeStr(String actorTimeStr) {
        this.actorTimeStr = actorTimeStr;
    }

    public void setTraNo(Long traNo) {
        this.traNo = traNo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayTimeStr() {
        return payTimeStr;
    }

    public void setPayTimeStr(String payTimeStr) {
        this.payTimeStr = payTimeStr;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Long getDifferenceOrderNo() {
        return differenceOrderNo;
    }

    public void setDifferenceOrderNo(Long differenceOrderNo) {
        this.differenceOrderNo = differenceOrderNo;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Long getBusinessSignNo() {
        return businessSignNo;
    }

    public void setBusinessSignNo(Long businessSignNo) {
        this.businessSignNo = businessSignNo;
    }

    public Integer getBsignType() {
        return bsignType;
    }

    public void setBsignType(Integer bsignType) {
        this.bsignType = bsignType;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Integer getCustomerTypeNo() {
        return customerTypeNo;
    }

    public void setCustomerTypeNo(Integer customerTypeNo) {
        this.customerTypeNo = customerTypeNo;
    }

    public Long getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }

    public BigDecimal getReceivable() {
        return receivable;
    }

    public void setReceivable(BigDecimal receivable) {
        this.receivable = receivable;
    }

    public Integer getIncomeExpenseType() {
        return incomeExpenseType;
    }

    public void setIncomeExpenseType(Integer incomeExpenseType) {
        this.incomeExpenseType = incomeExpenseType;
    }

    public BigDecimal getReceived() {
        return received;
    }

    public void setReceived(BigDecimal received) {
        this.received = received;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Long getBusinessOrderNo() {
        return businessOrderNo;
    }

    public void setBusinessOrderNo(Long businessOrderNo) {
        this.businessOrderNo = businessOrderNo;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getGoodsSubType() {
        return goodsSubType;
    }

    public void setGoodsSubType(Integer goodsSubType) {
        this.goodsSubType = goodsSubType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOrderingTime() {
        return orderingTime;
    }

    public void setOrderingTime(String orderingTime) {
        this.orderingTime = orderingTime;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getActorUser() {
        return actorUser;
    }

    public void setActorUser(String actorUser) {
        this.actorUser = actorUser;
    }

    public Date getActorTime() {
        return actorTime;
    }

    public void setActorTime(Date actorTime) {
        this.actorTime = actorTime;
    }

    public String getActorDesc() {
        return actorDesc;
    }

    public void setActorDesc(String actorDesc) {
        this.actorDesc = actorDesc;
    }

    public String getDifferenceDesc() {
        return differenceDesc;
    }

    public void setDifferenceDesc(String differenceDesc) {
        this.differenceDesc = differenceDesc;
    }

    public Integer getDifferenceType() {
        return differenceType;
    }

    public void setDifferenceType(Integer differenceType) {
        this.differenceType = differenceType;
    }

    public Integer getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(Integer settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getThirdPayNo() {
        return thirdPayNo;
    }

    public void setThirdPayNo(String thirdPayNo) {
        this.thirdPayNo = thirdPayNo;
    }

    public String getThirdBusNo() {
        return thirdBusNo;
    }

    public void setThirdBusNo(String thirdBusNo) {
        this.thirdBusNo = thirdBusNo;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getTicketNos() {
        return ticketNos;
    }

    public void setTicketNos(String ticketNos) {
        this.ticketNos = ticketNos;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
}
