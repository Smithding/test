package com.tempus.gss.product.unp.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ZhangBro
 */
public class UnpSaleRefundItem implements Serializable {
    private static final long serialVersionUID = -48676560839578238L;
    /**
     * 编号
     */
    private Long itemId;
    /**
     * 销售退款单号
     */
    private Long saleRefundOrderNo;
    /**
     * 产品类型
     */
    private Integer unpType;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 是否变更以及变更类型 0正常;1退;2改
     */
    private Integer changeType;
    /**
     * 销售价, 该类商品应收总额
     */
    private BigDecimal groupAmount;
    /**
     * 状态:1待处理;2处理中;3已完成;
     */
    private Integer itemStatus;
    /**
     * 删除标志 0 无效 已删除 1 有效
     */
    private Integer valid;
    
    public Long getItemId() {
        return itemId;
    }
    
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    public Long getSaleRefundOrderNo() {
        return saleRefundOrderNo;
    }
    
    public void setSaleRefundOrderNo(Long saleRefundOrderNo) {
        this.saleRefundOrderNo = saleRefundOrderNo;
    }
    
    public Integer getUnpType() {
        return unpType;
    }
    
    public void setUnpType(Integer unpType) {
        this.unpType = unpType;
    }
    
    public Integer getNum() {
        return num;
    }
    
    public void setNum(Integer num) {
        this.num = num;
    }
    
    public Integer getChangeType() {
        return changeType;
    }
    
    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
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
    
    public Integer getValid() {
        return valid;
    }
    
    public void setValid(Integer valid) {
        this.valid = valid;
    }
}