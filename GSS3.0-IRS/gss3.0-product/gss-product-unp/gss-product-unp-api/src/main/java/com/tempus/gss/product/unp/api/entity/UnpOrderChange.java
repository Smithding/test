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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.order.entity.SaleChange;
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
@Alias("unpOrderChange")
@TableName("UNP_ORDER_CHANGE")
public class UnpOrderChange implements Serializable {


	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 编号 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 数据归属单位 */
	private Integer owner;

	/** 退票销售单号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long changeOrderNo;

	/** 订单号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long orderNo;

	/** 退票采购单号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long buyChangeNo;

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

	/** 删除标志 0 无效 已删除 1 有效 */
	private Boolean valid;
	
	/**销售退款金额*/
	private BigDecimal saleRefundPrice;
	
	private SaleChange saleChange;

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

	public Long getChangeOrderNo() {
		return changeOrderNo;
	}

	public void setChangeOrderNo(Long changeOrderNo) {
		this.changeOrderNo = changeOrderNo;
	}




	public Long getBuyChangeNo() {
		return buyChangeNo;
	}

	public void setBuyChangeNo(Long buyChangeNo) {
		this.buyChangeNo = buyChangeNo;
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

	public SaleChange getSaleChange() {
		return saleChange;
	}

	public void setSaleChange(SaleChange saleChange) {
		this.saleChange = saleChange;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getSaleRefundPrice() {
		return saleRefundPrice;
	}

	public void setSaleRefundPrice(BigDecimal saleRefundPrice) {
		this.saleRefundPrice = saleRefundPrice;
	}

	
	
}