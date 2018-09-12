package com.tempus.gss.product.unp.api.entity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhangBro
 * 查询条件
 */
public class UnpOrderQueryVo implements Serializable {
    private static final long serialVersionUID = -6160032397049678376L;
    /**
     * 销售单号
     */
    private Long saleOrderNo;
    /**
     * 销售变更单号
     */
    private Long saleChangeNo;
    /**
     * 数据归属单位
     */
    private Integer owner;
    /**
     * 交易单号
     */
    private Long traNo;
    /**
     * 客户ID, 外键
     */
    private Long customerNo;
    /**
     * 客户类型, 外键
     */
    private Long customerType;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 是否变更以及变更类型 0正常;1退;2改
     */
    private Integer changeType;
    /**
     * 支付状态,1:未支付(默认); 2:支付中; 3:已支付; 4:挂账支付;
     *
     * @see com.tempus.gss.product.unp.api.entity.enums.EUnpConstant.PayStatus
     */
    private Integer payStatus;
    /**
     * 收款时间
     */
    private Date payTime;
    private String payTimeStart;
    private String payTimeEnd;
    /**
     * 状态:1待处理;2处理中;3已完成;4已取消;5变更中;
     */
    private Integer status;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date createTime;
    private String createTimeStart;
    private String createTimeEnd;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改时间
     */
    private Date modifyTime;
    private String modifyTimeStart;
    private String modifyTimeEnd;
    /**
     * 删除标志 0 无效 已删除 1 有效
     */
    private Integer valid;
    private String groupName;
    private String groupCode;
    private Long itemNo;
    private String itemName;
    private String itemCode;
    
    //-------------------------below is  from BuyOrder------------------------//
    /**
     * 采购单号
     */
    private Long buyOrderNo;
    /**
     * 采购变更单号
     */
    private Long buyChangeNo;
    /**
     * 供应商ID
     */
    private Long supplierId;
    /**
     * 供应商类型
     */
    private Long supplierType;
    /**
     * 供应商名称
     */
    private String supplierName;
    
    /**
     * 第三方业务单号
     */
    private Long thirdBusNo;
    
    public Long getItemNo() {
        return itemNo;
    }
    
    public Long getSaleChangeNo() {
        return saleChangeNo;
    }
    
    public void setSaleChangeNo(Long saleChangeNo) {
        this.saleChangeNo = saleChangeNo;
    }
    
    public Long getBuyChangeNo() {
        return buyChangeNo;
    }
    
    public void setBuyChangeNo(Long buyChangeNo) {
        this.buyChangeNo = buyChangeNo;
    }
    
    public void setItemNo(Long itemNo) {
        this.itemNo = itemNo;
    }
    
    public Long getSaleOrderNo() {
        return saleOrderNo;
    }
    
    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }
    
    public String getGroupName() {
        return groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public String getGroupCode() {
        return groupCode;
    }
    
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getItemCode() {
        return itemCode;
    }
    
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    
    public Integer getOwner() {
        return owner;
    }
    
    public void setOwner(Integer owner) {
        this.owner = owner;
    }
    
    public Long getTraNo() {
        return traNo;
    }
    
    public void setTraNo(Long traNo) {
        this.traNo = traNo;
    }
    
    public Long getCustomerNo() {
        return customerNo;
    }
    
    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }
    
    public Long getCustomerType() {
        return customerType;
    }
    
    public void setCustomerType(Long customerType) {
        this.customerType = customerType;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public Integer getChangeType() {
        return changeType;
    }
    
    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }
    
    public Integer getPayStatus() {
        return payStatus;
    }
    
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
    
    public Date getPayTime() {
        return payTime;
    }
    
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
    
    public String getPayTimeStart() {
        return payTimeStart;
    }
    
    public void setPayTimeStart(String payTimeStart) {
        this.payTimeStart = payTimeStart;
    }
    
    public String getPayTimeEnd() {
        return payTimeEnd;
    }
    
    public void setPayTimeEnd(String payTimeEnd) {
        this.payTimeEnd = payTimeEnd;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getCreator() {
        return creator;
    }
    
    public void setCreator(String creator) {
        this.creator = creator;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getCreateTimeStart() {
        return createTimeStart;
    }
    
    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }
    
    public String getCreateTimeEnd() {
        return createTimeEnd;
    }
    
    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
    
    public String getModifier() {
        return modifier;
    }
    
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
    
    public Date getModifyTime() {
        return modifyTime;
    }
    
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    
    public String getModifyTimeStart() {
        return modifyTimeStart;
    }
    
    public void setModifyTimeStart(String modifyTimeStart) {
        this.modifyTimeStart = modifyTimeStart;
    }
    
    public String getModifyTimeEnd() {
        return modifyTimeEnd;
    }
    
    public void setModifyTimeEnd(String modifyTimeEnd) {
        this.modifyTimeEnd = modifyTimeEnd;
    }
    
    public Integer getValid() {
        return valid;
    }
    
    public void setValid(Integer valid) {
        this.valid = valid;
    }
    
    public Long getBuyOrderNo() {
        return buyOrderNo;
    }
    
    public void setBuyOrderNo(Long buyOrderNo) {
        this.buyOrderNo = buyOrderNo;
    }
    
    public Long getSupplierId() {
        return supplierId;
    }
    
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    
    public Long getSupplierType() {
        return supplierType;
    }
    
    public void setSupplierType(Long supplierType) {
        this.supplierType = supplierType;
    }
    
    public String getSupplierName() {
        return supplierName;
    }
    
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
    public Long getThirdBusNo() {
        return thirdBusNo;
    }
    
    public void setThirdBusNo(Long thirdBusNo) {
        this.thirdBusNo = thirdBusNo;
    }
    
    @Override
    public String toString() {
        return "UnpOrderQueryVo:{ " + "\"saleOrderNo\":" + (saleOrderNo == null ? null : "\"" + saleOrderNo + "\"") + ",\"owner\":" + (owner == null ? null : "\"" + owner + "\"") + ",\"traNo\":" + (traNo == null ? null : "\"" + traNo + "\"") + ",\"customerNo\":" + (customerNo == null ? null : "\"" + customerNo + "\"") + ",\"customerType\":" + (customerType == null ? null : "\"" + customerType + "\"") + ",\"customerName\":" + (customerName == null ? "\"\"" : "\"" + customerName + "\"") + ",\"changeType\":" + (changeType == null ? null : "\"" + changeType + "\"") + ",\"payStatus\":" + (payStatus == null ? null : "\"" + payStatus + "\"") + ",\"payTime\":" + (payTime == null ? null : "\"" + payTime + "\"") + ",\"payTimeStart\":" + (payTimeStart == null ? "\"\"" : "\"" + payTimeStart + "\"") + ",\"payTimeEnd\":" + (payTimeEnd == null ? "\"\"" : "\"" + payTimeEnd + "\"") + ",\"status\":" + (status == null ? null : "\"" + status + "\"") + ",\"creator\":" + (creator == null ? "\"\"" : "\"" + creator + "\"") + ",\"createTime\":" + (createTime == null ? null : "\"" + createTime + "\"") + ",\"createTimeStart\":" + (createTimeStart == null ? "\"\"" : "\"" + createTimeStart + "\"") + ",\"createTimeEnd\":" + (createTimeEnd == null ? "\"\"" : "\"" + createTimeEnd + "\"") + ",\"modifier\":" + (modifier == null ? "\"\"" : "\"" + modifier + "\"") + ",\"modifyTime\":" + (modifyTime == null ? null : "\"" + modifyTime + "\"") + ",\"modifyTimeStart\":" + (modifyTimeStart == null ? "\"\"" : "\"" + modifyTimeStart + "\"") + ",\"modifyTimeEnd\":" + (modifyTimeEnd == null ? "\"\"" : "\"" + modifyTimeEnd + "\"") + ",\"valid\":" + (valid == null ? null : "\"" + valid + "\"") + ",\"buyOrderNo\":" + (buyOrderNo == null ? null : "\"" + buyOrderNo + "\"") + ",\"supplierId\":" + (supplierId == null ? null : "\"" + supplierId + "\"") + ",\"supplierType\":" + (supplierType == null ? null : "\"" + supplierType + "\"") + ",\"supplierName\":" + (supplierName == null ? "\"\"" : "\"" + supplierName + "\"") + ",\"thirdBusNo\":" + (thirdBusNo == null ? null : "\"" + thirdBusNo + "\"") + "}";
    }
}
