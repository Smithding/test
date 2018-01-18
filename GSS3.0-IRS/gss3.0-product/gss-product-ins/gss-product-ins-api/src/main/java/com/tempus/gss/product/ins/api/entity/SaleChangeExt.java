/**
 * SaleChangeExt.java
 * com.tempus.gss.product.ins.api.entity
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2016年11月18日 		shuo.cheng
 * <p>
 * Copyright (c) 2016, TNT All Rights Reserved.
 */

package com.tempus.gss.product.ins.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.order.entity.BuyChange;
import com.tempus.gss.order.entity.SaleChange;
import com.tempus.gss.serializer.LongSerializer;

/**
 * ClassName:SaleChangeExt
 * Function: 销售变更扩展单
 *
 * @author shuo.cheng
 * @version
 * @since Ver 1.1
 * @Date 2016年11月18日        下午3:36:33
 *
 * @see
 *
 */
@Alias("insSaleChangeExt")
public class SaleChangeExt implements Serializable {

	/**
	 * serialVersionUID:序列ID
	 *
	 * @since Ver 1.1
	 */

	private static final long serialVersionUID = -7417146502653454757L;

	/**
	 * 销售单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	/**
	 * 销售变更扩展单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeExtNo;
	/**
	 * 销售变更单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeNo;

	/**
	 * 编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long id;

	/**
	 * 数据归属单位
	 */
	private Integer owner;

	/**
	 * 销售变更单
	 */
	private SaleChange saleChange;

	/**
	 * 采购变更单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long buyChangeNo;
	/**
	 * 采购改签单
	 */
	private BuyChange buyChange;

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

	/**
	 * 保险子订单集合
	 */
	private List<SaleChangeDetail> saleChangeDetailList;

	/**
	 * 废退方式：1：自愿、2：非自愿
	 */
	private Integer refundWay;

	/**
	 * 启用状态 未投保（1），已投保（2），已取消（3）
	 */
	private String status;

	/**
	 * 业务类型 1退保 2退款
	 */
	private Integer changeType;
	/**
	 * 退款日期
	 */
	private  Date refundDate;
	/**
	 * 应付金额
	 */
    private BigDecimal rightRefund;
	/**
	 * 实付金额
	 */
    private BigDecimal actualRefund;
	/**
	 * 被保人编号
	 */
    private Long insuredNo;
    public Long getInsuredNo() {
		return insuredNo;
	}

	public void setInsuredNo(Long insuredNo) {
		this.insuredNo = insuredNo;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public BigDecimal getRightRefund() {
		return rightRefund;
	}

	public void setRightRefund(BigDecimal rightRefund) {
		this.rightRefund = rightRefund;
	}

	public BigDecimal getActualRefund() {
		return actualRefund;
	}

	public void setActualRefund(BigDecimal actualRefund) {
		this.actualRefund = actualRefund;
	}

	public Integer getChangeType() {

		return changeType;
	}

	public void setChangeType(Integer changeType) {

		this.changeType = changeType;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public Integer getRefundWay() {

		return refundWay;
	}

	public void setRefundWay(Integer refundWay) {

		this.refundWay = refundWay;
	}

	public Long getSaleOrderNo() {

		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {

		this.saleOrderNo = saleOrderNo;
	}

	public Long getSaleChangeExtNo() {

		return saleChangeExtNo;
	}

	public void setSaleChangeExtNo(Long saleChangeExtNo) {

		this.saleChangeExtNo = saleChangeExtNo;
	}

	public Long getSaleChangeNo() {

		return saleChangeNo;
	}

	public void setSaleChangeNo(Long saleChangeNo) {

		this.saleChangeNo = saleChangeNo;
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

	public SaleChange getSaleChange() {

		return saleChange;
	}

	public void setSaleChange(SaleChange saleChange) {

		this.saleChange = saleChange;
	}

	public Long getBuyChangeNo() {

		return buyChangeNo;
	}

	public void setBuyChangeNo(Long buyChangeNo) {

		this.buyChangeNo = buyChangeNo;
	}

	public BuyChange getBuyChange() {

		return buyChange;
	}

	public void setBuyChange(BuyChange buyChange) {

		this.buyChange = buyChange;
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

	public List<SaleChangeDetail> getSaleChangeDetailList() {

		return saleChangeDetailList;
	}

	public void setSaleChangeDetailList(List<SaleChangeDetail> saleChangeDetailList) {

		this.saleChangeDetailList = saleChangeDetailList;
	}

}

