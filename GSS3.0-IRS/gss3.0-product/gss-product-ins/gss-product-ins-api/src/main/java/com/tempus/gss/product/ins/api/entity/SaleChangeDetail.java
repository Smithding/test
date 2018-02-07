/**
 * SaleChangeDetail.java
 * com.tempus.gss.product.ins.api.entity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2016年11月18日 		shuo.cheng
 *
 * Copyright (c) 2016, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ins.api.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * ClassName:SaleChangeDetail
 * Function: 销售变更明细
 *
 * @author   shuo.cheng
 * @version  
 * @since    Ver 1.1
 * @Date	 2016年11月18日		下午3:59:37
 *
 * @see 	 
 *  
 */
@Alias("insSaleChangeDetail")
public class SaleChangeDetail implements Serializable {

	/**
	 * serialVersionUID:序列ID
	 *
	 * @since Ver 1.1
	 */

	private static final long serialVersionUID = -4856532873298553307L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 订单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	/**
	 * 销售变更明细编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeDetailNo;

	/**
	 * 数据归属 数据归属
	 */
	private Integer owner;

	/**
	 * 销售变更单号.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeNo;

	/**
	 * 采购变更单号.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long buyChangeNo;

	/**
	 * 销售单明细编号.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderDetailNo;

	/**
	 * 销售单明细.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private SaleOrderDetail saleOrderDetail;

	/**
	 * 销售变更明细状态 1.未退保 2.已退保 3.已取消
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 创建者
	 */
	private String creator;

	/**
	 * 操作人 默认为：sys
	 */
	private String modifier;

	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Boolean valid;

	public Long getSaleOrderNo() {

		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {

		this.saleOrderNo = saleOrderNo;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public Date getCreateTime() {

		return createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	public Date getModifyTime() {

		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {

		this.modifyTime = modifyTime;
	}

	public String getCreator() {

		return creator;
	}

	public void setCreator(String creator) {

		this.creator = creator;
	}

	public String getModifier() {

		return modifier;
	}

	public void setModifier(String modifier) {

		this.modifier = modifier;
	}

	public Boolean getValid() {

		return valid;
	}

	public void setValid(Boolean valid) {

		this.valid = valid;
	}

	public Long getSaleChangeDetailNo() {

		return saleChangeDetailNo;
	}

	public void setSaleChangeDetailNo(Long saleChangeDetailNo) {

		this.saleChangeDetailNo = saleChangeDetailNo;
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

	public Long getSaleOrderDetailNo() {

		return saleOrderDetailNo;
	}

	public void setSaleOrderDetailNo(Long saleOrderDetailNo) {

		this.saleOrderDetailNo = saleOrderDetailNo;
	}

	public SaleOrderDetail getSaleOrderDetail() {

		return saleOrderDetail;
	}

	public void setSaleOrderDetail(SaleOrderDetail saleOrderDetail) {

		this.saleOrderDetail = saleOrderDetail;
	}

}

