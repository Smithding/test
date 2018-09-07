package com.tempus.gss.product.unp.api.entity;

import com.tempus.gss.product.unp.api.entity.enums.EUnpConstant;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ZhangBro
 */
public class UnpBuyItem implements Serializable {
    
    /**
     * 编号
     */
    private Long itemId;
    /**
     * 销售单号
     */
    private Long buyOrderNo;
    /**
     * 产品类型
     */
    private Long unpType;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 是否变更以及变更类型 0正常;1退;2改
     *
     * @see EUnpConstant.ChangeType
     */
    private Integer changeType;
    /**
     * 采购总价, 该类商品应付总额
     */
    private BigDecimal groupAmount;
    /**
     * 状态:1待处理;2处理中;3已完成;4已取消
     */
    private Integer itemStatus;
    /**
     * 优惠规则
     */
    private Long policy;
    /**
     * 删除标志 0 无效 已删除 1 有效
     */
    private Integer valid;
    
    private static final long serialVersionUID = 5304640655455648250L;
    
    public Long getItemId() {
        return itemId;
    }
    
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    public Long getBuyOrderNo() {
        return buyOrderNo;
    }
    
    public void setBuyOrderNo(Long buyOrderNo) {
        this.buyOrderNo = buyOrderNo;
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
    
    public Long getPolicy() {
        return policy;
    }
    
    public void setPolicy(Long policy) {
        this.policy = policy;
    }
    
    public Integer getValid() {
        return valid;
    }
    
    public void setValid(Integer valid) {
        this.valid = valid;
    }
}