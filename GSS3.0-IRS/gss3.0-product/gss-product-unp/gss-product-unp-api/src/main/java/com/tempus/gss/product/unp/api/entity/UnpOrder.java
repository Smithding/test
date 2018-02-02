/**
 * UnpOrder.java
 * com.tempus.gss.product.unp.api.entity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年3月10日 		niepeng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.unp.api.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tempus.gss.order.entity.SaleOrder;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * ClassName:UnpOrder
 * Function: 通用产品订单表
 *
 * @author   niepeng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年3月10日		上午8:48:39
 *
 * @see 	 
 *  
 */
@JsonInclude(Include.NON_NULL)
@Alias("unpOrder")
@TableName("UNP_ORDER")
public class UnpOrder implements Serializable {


	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 编号 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 数据归属单位 */
	private Integer owner;

	/** 销售单号 */
	private Long orderNo;

	/** 备注 */
	private String remark;

	/** 交易单号 */
	private Long tradeNo;

	/** 批次号 */
	private Long batchNo;

	/** 销售渠道ID, 外键 */
	private Long channelId;

	/** 渠道类型, 外键 */
	private Long channelType;

	/** 销售价, 应收总额 */
	private BigDecimal salePrice;

	/** 支付状态, 1:未支付(默认); 2:部分支付; 3:已支付; 4:部分退款; 5:已退款 */
	private Integer payStatus;

	/** 实收总额, 默认:0 */
	private BigDecimal payAmount;

	/** 收款时间 */
	private Date payTime;

	/** 采购单号 */
	private Long buyOrderNo;
	
	/** 采购渠道id */
	private Long buyChannelId;

	/** 采购渠道类型 */
	private Long buyChannelType;

	/** 采购价, 应付总额 */
	private BigDecimal buyPrice;

	/** 采购支付状态, 1:未支付(默认); 2:部分支付; 3:已支付; 4:部分退款; 5:已退款 */
	private Integer buyPayStatus;

	/** 实付总额, 默认:0 */
	private BigDecimal buyPayAmount;

	/** 付款时间 */
	private Date buyPayTime;

	/** 状态:1待处理;2处理中;3已完成;4已取消 */
	private Integer status;

	/** 创建者 */
	private String creator;

	/** 创建时间 */
	private Date createTime;

	/** 修改人 */
	private String modifier;

	/** 修改时间 */
	private Date modifyTime;

	/** 删除标志 0 无效 已删除 1 有效 */
	private Boolean valid;
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
	
	@TableField(exist = false)
	private SaleOrder saleOrder;
	
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
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOwner() {
		return this.owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public Long getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(Long tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Long getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

	public Long getChannelId() {
		return this.channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Long getChannelType() {
		return this.channelType;
	}

	public void setChannelType(Long channelType) {
		this.channelType = channelType;
	}

	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public BigDecimal getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Long getBuyChannelId() {
		return this.buyChannelId;
	}

	public void setBuyChannelId(Long buyChannelId) {
		this.buyChannelId = buyChannelId;
	}

	public Long getBuyChannelType() {
		return this.buyChannelType;
	}

	public void setBuyChannelType(Long buyChannelType) {
		this.buyChannelType = buyChannelType;
	}

	public BigDecimal getBuyPrice() {
		return this.buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Integer getBuyPayStatus() {
		return this.buyPayStatus;
	}

	public void setBuyPayStatus(Integer buyPayStatus) {
		this.buyPayStatus = buyPayStatus;
	}

	public BigDecimal getBuyPayAmount() {
		return this.buyPayAmount;
	}

	public void setBuyPayAmount(BigDecimal buyPayAmount) {
		this.buyPayAmount = buyPayAmount;
	}

	public Date getBuyPayTime() {
		return this.buyPayTime;
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
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Boolean getValid() {
		return this.valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	
	public Long getBuyOrderNo() {
	
		return buyOrderNo;
	}

	
	public void setBuyOrderNo(Long buyOrderNo) {
	
		this.buyOrderNo = buyOrderNo;
	}

	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}
	
	
}