package com.tempus.gss.product.unp.api.entity;

import com.tempus.gss.product.unp.api.entity.enums.EUnpConstant;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ZhangBro
 */
public class UnpBuy implements Serializable {
    
    private static final long serialVersionUID = -8735072824827534721L;
    /**
     * 采购单号
     */
    private Long buyOrderNo;
    /**
     * 销售单编号
     */
    private Long saleOrderNo;
    /**
     * 数据归属单位
     */
    private Integer owner;
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
    private String name;
    /**
     * 对应销售单客户名称
     */
    private String customerName;
    /**
     * 是否变更以及变更类型 0正常;1退;2改
     */
    private Integer changeType;
    /**
     * 备注
     */
    private String remark;
    /**
     * 交易单号
     */
    private Long traNo;
    /**
     * 第三方业务单号
     */
    private String thirdBusNo;
    /**
     * 采购价, 应付总额
     */
    private BigDecimal planAmount;
    /**
     * 采购支付状态,1:未支付(默认); 2:支付中; 3:已支付; 4:挂账支付;
     *
     * @see com.tempus.gss.product.unp.api.entity.enums.EUnpConstant.PayStatus
     */
    private Integer payStatus;
    /**
     * 实付总额, 默认:0
     */
    private BigDecimal actualAmount;
    /**
     * 收款时间
     */
    private Date payTime;
    /**
     * 状态:1待处理;2处理中;3已完成;4已取消
     *
     * @see EUnpConstant.OrderStatus
     */
    private Integer status;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 采购付款账号
     */
    private String buyAccount;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 删除标志 0 无效 已删除 1 有效
     */
    private Integer valid;
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    private List<UnpBuyItem> buyItems;
    
    public Long getBuyOrderNo() {
        return buyOrderNo;
    }
    
    public void setBuyOrderNo(Long buyOrderNo) {
        this.buyOrderNo = buyOrderNo;
    }
    
    public Long getSaleOrderNo() {
        return saleOrderNo;
    }
    
    public String getBuyAccount() {
        return buyAccount;
    }
    
    public void setBuyAccount(String buyAccount) {
        this.buyAccount = buyAccount;
    }
    
    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }
    
    public Integer getOwner() {
        return owner;
    }
    
    public void setOwner(Integer owner) {
        this.owner = owner;
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
    
    public Integer getChangeType() {
        return changeType;
    }
    
    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Long getTraNo() {
        return traNo;
    }
    
    public void setTraNo(Long traNo) {
        this.traNo = traNo;
    }
    
    public String getThirdBusNo() {
        return thirdBusNo;
    }
    
    public void setThirdBusNo(String thirdBusNo) {
        this.thirdBusNo = thirdBusNo;
    }
    
    public BigDecimal getPlanAmount() {
        return planAmount;
    }
    
    public void setPlanAmount(BigDecimal planAmount) {
        this.planAmount = planAmount;
    }
    
    public Integer getPayStatus() {
        return payStatus;
    }
    
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
    
    public BigDecimal getActualAmount() {
        return actualAmount;
    }
    
    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }
    
    public Date getPayTime() {
        return payTime;
    }
    
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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
    
    public Integer getValid() {
        return valid;
    }
    
    public void setValid(Integer valid) {
        this.valid = valid;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    public List<UnpBuyItem> getBuyItems() {
        return buyItems;
    }
    
    public void setBuyItems(List<UnpBuyItem> buyItems) {
        this.buyItems = buyItems;
    }
    
    public UnpBuy(Integer initSize) {
        this.buyItems = new ArrayList<>(initSize);
    }
    
    public UnpBuy() {
    }
}