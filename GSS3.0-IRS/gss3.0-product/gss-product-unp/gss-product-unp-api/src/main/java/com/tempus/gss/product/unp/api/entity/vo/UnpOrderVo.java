/**
 * UnpOrderVo.java
 * com.tempus.gss.product.unp.api.entity.vo
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2017年3月10日 		niepeng
 * <p>
 * Copyright (c) 2017, TNT All Rights Reserved.
 */

package com.tempus.gss.product.unp.api.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.SaleOrder;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName:UnpOrderVo
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author niepeng
 * @Date 2017年3月10日        上午8:52:37
 * @see
 * @since Ver 1.1
 */
@JsonInclude(Include.NON_NULL)
@Alias("unpOrderVo")
public class UnpOrderVo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 编号
     */
    private Long id;
    
    /**
     * 数据归属单位
     */
    private Integer owner;
    
    /**
     * 销售单号
     */
    private Long orderNo;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 交易单号
     */
    private Long tradeNo;
    
    /**
     * 批次号
     */
    private Long batchNo;
    
    /**
     * 销售渠道ID, 外键
     */
    private Long channelId;
    
    /**
     * 渠道类型, 外键
     */
    private Long channelType;
    
    /**
     * 销售价, 应收总额
     */
    private BigDecimal salePrice;
    
    /**
     * 支付状态, 1:未支付(默认); 2:部分支付; 3:已支付; 4:部分退款; 5:已退款
     */
    private Integer payStatus;
    
    /**
     * 实收总额, 默认:0
     */
    private BigDecimal payAmount;
    
    /**
     * 收款时间
     */
    private Date payTime;
    
    /**
     * 采购单号
     */
    private Long buyOrderNo;
    
    /**
     * 采购渠道id
     */
    private Long buyChannelId;
    
    /**
     * 采购渠道类型
     */
    private Long buyChannelType;
    
    /**
     * 采购价, 应付总额
     */
    private BigDecimal buyPrice;
    
    /**
     * 采购支付状态, 1:未支付(默认); 2:部分支付; 3:已支付; 4:部分退款; 5:已退款
     */
    private Integer buyPayStatus;
    
    /**
     * 实付总额, 默认:0
     */
    private BigDecimal buyPayAmount;
    
    /**
     * 付款时间
     */
    private Date buyPayTime;
    
    /**
     * 状态:1待处理;2处理中;3已完成;4已取消
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
    private Boolean valid;
    
    /**
     * 销售单
     */
    private SaleOrder saleOrder;
    
    /**
     * 采购单
     */
    private BuyOrder buyOrder;
    /**
     * 线下交易流水号
     */
    private Long offlinePayNo;
    /**
     * 线下交易付款号
     */
    private String offlinePayer;
    /**
     * 线下交易收款号
     */
    private String offlinePayee;
    /**
     * 线下交易备注信息
     */
    private String offlinePayRemark;
    private Date createDateStart;
    private Date createDateEnd;
    private Integer saleAccountType;//客户付款方式
    private Long saleAccount;//客户付款账号
    private Integer buyAccountType;//供应商收款方式
    private Long buyAccount;//供应商收款账号
    private Long capitalAccount;//收/付款账号(自己)
    private String payAmountTransactionNo;//付款流水号
    
    public Long getOfflinePayNo() {
        return offlinePayNo;
    }
    
    public void setOfflinePayNo(Long offlinePayNo) {
        this.offlinePayNo = offlinePayNo;
    }
    
    public String getOfflinePayer() {
        return offlinePayer;
    }
    
    public void setOfflinePayer(String offlinePayer) {
        this.offlinePayer = offlinePayer;
    }
    
    public String getOfflinePayee() {
        return offlinePayee;
    }
    
    public void setOfflinePayee(String offlinePayee) {
        this.offlinePayee = offlinePayee;
    }
    
    public String getOfflinePayRemark() {
        return offlinePayRemark;
    }
    
    public void setOfflinePayRemark(String offlinePayRemark) {
        this.offlinePayRemark = offlinePayRemark;
    }
    
    public Long getId() {
        
        return id;
    }
    
    public void setId(Long id) {
        
        this.id = id;
    }
    
    public Integer getOwner() {
        
        return owner;
    }
    
    public void setOwner(Integer owner) {
        
        this.owner = owner;
    }
    
    public Long getOrderNo() {
        
        return orderNo;
    }
    
    public void setOrderNo(Long orderNo) {
        
        this.orderNo = orderNo;
    }
    
    public String getRemark() {
        
        return remark;
    }
    
    public void setRemark(String remark) {
        
        this.remark = remark;
    }
    
    public Long getTradeNo() {
        
        return tradeNo;
    }
    
    public void setTradeNo(Long tradeNo) {
        
        this.tradeNo = tradeNo;
    }
    
    public Long getBatchNo() {
        
        return batchNo;
    }
    
    public void setBatchNo(Long batchNo) {
        
        this.batchNo = batchNo;
    }
    
    public Long getChannelId() {
        
        return channelId;
    }
    
    public void setChannelId(Long channelId) {
        
        this.channelId = channelId;
    }
    
    public Long getChannelType() {
        
        return channelType;
    }
    
    public void setChannelType(Long channelType) {
        
        this.channelType = channelType;
    }
    
    public BigDecimal getSalePrice() {
        
        return salePrice;
    }
    
    public void setSalePrice(BigDecimal salePrice) {
        
        this.salePrice = salePrice;
    }
    
    public Integer getPayStatus() {
        
        return payStatus;
    }
    
    public void setPayStatus(Integer payStatus) {
        
        this.payStatus = payStatus;
    }
    
    public BigDecimal getPayAmount() {
        
        return payAmount;
    }
    
    public void setPayAmount(BigDecimal payAmount) {
        
        this.payAmount = payAmount;
    }
    
    public Date getPayTime() {
        
        return payTime;
    }
    
    public void setPayTime(Date payTime) {
        
        this.payTime = payTime;
    }
    
    public Long getBuyChannelId() {
        
        return buyChannelId;
    }
    
    public void setBuyChannelId(Long buyChannelId) {
        
        this.buyChannelId = buyChannelId;
    }
    
    public Long getBuyChannelType() {
        
        return buyChannelType;
    }
    
    public void setBuyChannelType(Long buyChannelType) {
        
        this.buyChannelType = buyChannelType;
    }
    
    public BigDecimal getBuyPrice() {
        
        return buyPrice;
    }
    
    public void setBuyPrice(BigDecimal buyPrice) {
        
        this.buyPrice = buyPrice;
    }
    
    public Integer getBuyPayStatus() {
        
        return buyPayStatus;
    }
    
    public void setBuyPayStatus(Integer buyPayStatus) {
        
        this.buyPayStatus = buyPayStatus;
    }
    
    public BigDecimal getBuyPayAmount() {
        
        return buyPayAmount;
    }
    
    public void setBuyPayAmount(BigDecimal buyPayAmount) {
        
        this.buyPayAmount = buyPayAmount;
    }
    
    public Date getBuyPayTime() {
        
        return buyPayTime;
    }
    
    public void setBuyPayTime(Date buyPayTime) {
        
        this.buyPayTime = buyPayTime;
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
    
    public Boolean getValid() {
        
        return valid;
    }
    
    public void setValid(Boolean valid) {
        
        this.valid = valid;
    }
    
    public SaleOrder getSaleOrder() {
        
        return saleOrder;
    }
    
    public void setSaleOrder(SaleOrder saleOrder) {
        
        this.saleOrder = saleOrder;
    }
    
    public BuyOrder getBuyOrder() {
        
        return buyOrder;
    }
    
    public void setBuyOrder(BuyOrder buyOrder) {
        
        this.buyOrder = buyOrder;
    }
    
    public Date getCreateDateStart() {
        
        return createDateStart;
    }
    
    public void setCreateDateStart(Date createDateStart) {
        
        this.createDateStart = createDateStart;
    }
    
    public Date getCreateDateEnd() {
        
        return createDateEnd;
    }
    
    public void setCreateDateEnd(Date createDateEnd) {
        
        this.createDateEnd = createDateEnd;
    }
    
    public Long getBuyOrderNo() {
        
        return buyOrderNo;
    }
    
    public void setBuyOrderNo(Long buyOrderNo) {
        
        this.buyOrderNo = buyOrderNo;
    }
    
    public Long getSaleAccount() {
        
        return saleAccount;
    }
    
    public void setSaleAccount(Long saleAccount) {
        
        this.saleAccount = saleAccount;
    }
    
    public Long getCapitalAccount() {
        
        return capitalAccount;
    }
    
    public void setCapitalAccount(Long capitalAccount) {
        
        this.capitalAccount = capitalAccount;
    }
    
    public String getPayAmountTransactionNo() {
        
        return payAmountTransactionNo;
    }
    
    public void setPayAmountTransactionNo(String payAmountTransactionNo) {
        
        this.payAmountTransactionNo = payAmountTransactionNo;
    }
    
    public Integer getSaleAccountType() {
        
        return saleAccountType;
    }
    
    public void setSaleAccountType(Integer saleAccountType) {
        
        this.saleAccountType = saleAccountType;
    }
    
    public Integer getBuyAccountType() {
        
        return buyAccountType;
    }
    
    public void setBuyAccountType(Integer buyAccountType) {
        
        this.buyAccountType = buyAccountType;
    }
    
    public Long getBuyAccount() {
        
        return buyAccount;
    }
    
    public void setBuyAccount(Long buyAccount) {
        
        this.buyAccount = buyAccount;
    }
}

