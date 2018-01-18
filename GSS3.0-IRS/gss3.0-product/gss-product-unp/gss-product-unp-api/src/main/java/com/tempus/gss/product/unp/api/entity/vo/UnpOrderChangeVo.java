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

package com.tempus.gss.product.unp.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;


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
@Alias("unpOrderChangeVo")
public class UnpOrderChangeVo implements Serializable {


	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 数据归属单位 */
	private Integer owner;

	/** 退票销售单号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long changeOrderNo;

	/** 订单号 */
	@JsonSerialize(using = LongSerializer.class)
	private String orderNo;

	/** 启用状态 未处理（1），已处理（2），已取消（3） */
	private Integer status;

	/** 创建者 */
	private String creator;

	/** 创建时间 */
	private Date createTime;

	/** 修改人 */
	private String modifier;

	/** 修改时间 */
	private Date modifyTime;
	
	/**销售退款金额*/
	private BigDecimal saleRefundPrice;
	/** 删除标志 0 无效 已删除 1 有效 */
	private Boolean valid;
	/** 销售渠道ID, 外键 */
	private Long channelId;
	private Date createStartTime;
	
	private Date createEndTime;
	
	private Integer saleAccountType;//客户付款方式
	private Long saleAccount;//客户付款账号
	private Integer buyAccountType;//供应商收款方式
	private Long buyAccount;//供应商收款账号
	private Long capitalAccount;//收/付款账号(自己)
	private String payAmountTransactionNo;//付款流水号
	
	/** 实收总额, 默认:0 */
	private BigDecimal payAmount;
	
	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public Long getChangeOrderNo() {
		return changeOrderNo;
	}

	public void setChangeOrderNo(Long changeOrderNo) {
		this.changeOrderNo = changeOrderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public Date getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	public Integer getSaleAccountType() {
		return saleAccountType;
	}

	public void setSaleAccountType(Integer saleAccountType) {
		this.saleAccountType = saleAccountType;
	}

	public Long getSaleAccount() {
		return saleAccount;
	}

	public void setSaleAccount(Long saleAccount) {
		this.saleAccount = saleAccount;
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

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public BigDecimal getSaleRefundPrice() {
		return saleRefundPrice;
	}

	public void setSaleRefundPrice(BigDecimal saleRefundPrice) {
		this.saleRefundPrice = saleRefundPrice;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	
	

	
}