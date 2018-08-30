package com.tempus.gss.product.unp.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/*销售报表返回实体类
*
* */
public class UnpSaleReportVo implements Serializable{
    //销售单号
    private Long saleOrderNo;
    //客户名称
    private  String customerName;
    //供应商名称
    private String supplierName;
    //订单来源
    private  String source;
    //产品名称
    private String productName;
    //产品状态
    private Integer itemStuts;
    //销售公司
    private String companyName;
    //部门名称
    private String deptName;
    //交易日期
    private Date createTime;
    //应收金额
    private BigDecimal salePlanAmount;
    //实收金额
    private  BigDecimal saleActualAmount;
    //采购应付
    private BigDecimal buyPlanAmount;
    //其他json数据信息
    private String additionalInfo;

    public Long getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public BigDecimal getSalePlanAmount() {
        return salePlanAmount;
    }

    public void setSalePlanAmount(BigDecimal salePlanAmount) {
        this.salePlanAmount = salePlanAmount;
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

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "UnpSaleReportVo{" +
                "saleOrderNo=" + saleOrderNo +
                ", customerName='" + customerName + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", source='" + source + '\'' +
                ", productName='" + productName + '\'' +
                ", itemStuts=" + itemStuts +
                ", companyName='" + companyName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", createTime=" + createTime +
                ", salePlanAmount=" + salePlanAmount +
                ", saleActualAmount=" + saleActualAmount +
                ", buyPlanAmount=" + buyPlanAmount +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
