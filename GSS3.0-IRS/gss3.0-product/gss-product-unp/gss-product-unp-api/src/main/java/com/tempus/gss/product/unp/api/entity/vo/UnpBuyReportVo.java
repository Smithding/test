package com.tempus.gss.product.unp.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售报表返回实体类
 */
public class UnpBuyReportVo implements Serializable {
    
    private static final long serialVersionUID = -3614158055221813075L;
    /**
     * 销售单号
     */
    private Long saleOrderNo;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 订单来源
     */
    private String source;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品状态
     */
    private Integer itemStuts;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 交易日期
     */
    private Date createTime;
    /**
     * 实收金额
     */
    private BigDecimal saleActualAmount;
    /**
     * 采购应付
     */
    private BigDecimal buyPlanAmount;
    /*
    * 采购实付
    * */
    private BigDecimal buyActualAmount;
    /*
    *第三方业务号
    */
    private Long thirdBusNo;
    /**
     * 其他json数据信息
     */
    private String additionalInfo;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getItemStuts() {
        return itemStuts;
    }

    public void setItemStuts(Integer itemStuts) {
        this.itemStuts = itemStuts;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getSaleActualAmount() {
        return saleActualAmount;
    }

    public void setSaleActualAmount(BigDecimal saleActualAmount) {
        this.saleActualAmount = saleActualAmount;
    }

    public BigDecimal getBuyPlanAmount() {
        return buyPlanAmount;
    }

    public void setBuyPlanAmount(BigDecimal buyPlanAmount) {
        this.buyPlanAmount = buyPlanAmount;
    }

    public BigDecimal getBuyActualAmount() {
        return buyActualAmount;
    }

    public void setBuyActualAmount(BigDecimal buyActualAmount) {
        this.buyActualAmount = buyActualAmount;
    }

    public Long getThirdBusNo() {
        return thirdBusNo;
    }

    public void setThirdBusNo(Long thirdBusNo) {
        this.thirdBusNo = thirdBusNo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "UnpBuyReportVo{" +
                "saleOrderNo=" + saleOrderNo +
                ", supplierName='" + supplierName + '\'' +
                ", source='" + source + '\'' +
                ", productName='" + productName + '\'' +
                ", itemStuts=" + itemStuts +
                ", deptName='" + deptName + '\'' +
                ", createTime=" + createTime +
                ", saleActualAmount=" + saleActualAmount +
                ", buyPlanAmount=" + buyPlanAmount +
                ", buyActualAmount=" + buyActualAmount +
                ", thirdBusNo=" + thirdBusNo +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
