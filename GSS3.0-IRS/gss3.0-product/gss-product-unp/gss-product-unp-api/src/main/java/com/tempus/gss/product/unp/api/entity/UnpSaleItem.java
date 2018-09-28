package com.tempus.gss.product.unp.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author ZhangBro
 */
public class UnpSaleItem implements Serializable {
    /**
     * 编号
     */
    private Long itemId;
    /**
     * 销售单号
     */
    private Long saleOrderNo;
    /**
     * 产品类型
     */
    private Long unpType;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 销售价, 该类商品应收总额
     */
    private BigDecimal groupAmount;
    /**
     * 状态:1待处理;2处理中;3已完成;4已取消
     */
    private Integer itemStatus;
    /**
     * 是否变更以及变更类型 0正常;1退;2改
     */
    private Integer changeType;
    /**
     * 优惠规则
     */
    private Integer policy;
    /**
     * 各个类型展示的字段,动态变化 标准JSON
     */
    private String additionalInfo;
    /**
     * 删除标志 0 无效 已删除 1 有效
     */
    private Integer valid;
    /*
    * 小类名称
    * */
    private String productName;
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private static final long serialVersionUID = 7248601471160558477L;
    
    
    public Long getItemId() {
        return itemId;
    }
    
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    public Long getSaleOrderNo() {
        return saleOrderNo;
    }
    
    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }
    
    public Long getUnpType() {
        return unpType;
    }
    
    public void setUnpType(Long unpType) {
        this.unpType = unpType;
    }
    
    public Integer getNum() {
        return num;
    }
    
    public void setNum(Integer num) {
        this.num = num;
    }
    
    public BigDecimal getGroupAmount() {
        return groupAmount;
    }
    
    public void setGroupAmount(BigDecimal groupAmount) {
        this.groupAmount = groupAmount;
    }
    
    public Integer getItemStatus() {
        return itemStatus;
    }
    
    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
    }
    
    public Integer getChangeType() {
        return changeType;
    }
    
    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }
    
    public Integer getPolicy() {
        return policy;
    }
    
    public void setPolicy(Integer policy) {
        this.policy = policy;
    }
    
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    
    public Integer getValid() {
        return valid;
    }
    
    public void setValid(Integer valid) {
        this.valid = valid;
    }
}