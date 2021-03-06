package com.tempus.gss.product.ift.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.serializer.LongSerializer;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BuyOrderExt implements Serializable {


	private static final long serialVersionUID = -2447333037554441472L;
	/**
	 * 采购订单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long buyOrderNo;

	/*
	* 采购单
	* */
	private BuyOrder buyOrder;

	/**
	 * Id
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long id;

	/**
	 * 数据归属单位
	 */
	private Integer owner;

	/**
	 * 出票office
	 */
	private String airline;

	/**
	 * 出票类型
	 */
	private String ticketType;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 操作人
	 */
	private String modifier;

	/**
	 * 修改日期
	 */
	private Date modifyTime;

	/**
	 * 删除标志  0 无效 已删除 1 有效
	 */
	private Byte valid;

	/**
	 * 启用状态 1：启用，2：停用
	 */
	private String status;

	/**
	 * 采购使用的政策编号.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long policyNo;

	/**
	 * 采购使用的总则编号.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long priceSpecNo;

	/**
	 * pnr编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long pnrNo;
	
	/**
	 * 采购商编号（客商）
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long supplierNo;
	
	/**
	 * 采购商类型（客商）
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long supplierTypeNo;
	/**
	 * 采购的Pnr.
	 */
	private Pnr importPnr;

	/**
	 * 采购的详情列表.
	 */
	private List<BuyOrderDetail> buyOrderDetailList;

	/**
	 * 销售订单编号
	 */
	@Transient
	private Long saleOrderNo;
	/**
	 * 改签单订单编号
	 */
	@Transient
	private Long changeOrderNo;
	/**
	 * office
	 */
	private String office;
	
	private String  buyRemarke;
	//BUY_LOCKER
	private Long buyLocker;

	public Long getBuyLocker() {
		return buyLocker;
	}

	public void setBuyLocker(Long buyLocker) {
		this.buyLocker = buyLocker;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("BuyOrderExt{");
		sb.append("buyOrderNo=").append(buyOrderNo);
		sb.append(", buyOrder=").append(buyOrder);
		sb.append(", id=").append(id);
		sb.append(", owner=").append(owner);
		sb.append(", airline='").append(airline).append('\'');
		sb.append(", ticketType='").append(ticketType).append('\'');
		sb.append(", creator='").append(creator).append('\'');
		sb.append(", createTime=").append(createTime);
		sb.append(", modifier='").append(modifier).append('\'');
		sb.append(", modifyTime=").append(modifyTime);
		sb.append(", valid=").append(valid);
		sb.append(", status='").append(status).append('\'');
		sb.append(", policyNo=").append(policyNo);
		sb.append(", priceSpecNo=").append(priceSpecNo);
		sb.append(", pnrNo=").append(pnrNo);
		sb.append(", supplierNo=").append(supplierNo);
		sb.append(", supplierTypeNo=").append(supplierTypeNo);
		sb.append(", importPnr=").append(importPnr);
		sb.append(", buyOrderDetailList=").append(buyOrderDetailList);
		sb.append(", saleOrderNo=").append(saleOrderNo);
		sb.append(", changeOrderNo=").append(changeOrderNo);
		sb.append(", office='").append(office).append('\'');
		sb.append(", buyRemarke='").append(buyRemarke).append('\'');
		sb.append('}');
		return sb.toString();
	}

	public Long getBuyOrderNo() {

		return buyOrderNo;
	}
	
	public String getOffice() {
		return office;
	}
	
	public void setOffice(String office) {
		this.office = office;
	}
	
	public void setBuyOrderNo(Long buyOrderNo) {

		this.buyOrderNo = buyOrderNo;
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

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getTicketType() {

		return ticketType;
	}

	public void setTicketType(String ticketType) {

		this.ticketType = ticketType;
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

	public Byte getValid() {

		return valid;
	}

	public void setValid(Byte valid) {

		this.valid = valid;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public Pnr getImportPnr() {

		return importPnr;
	}

	public void setImportPnr(Pnr importPnr) {

		this.importPnr = importPnr;
	}

	public List<BuyOrderDetail> getBuyOrderDetailList() {

		return buyOrderDetailList;
	}

	public void setBuyOrderDetailList(List<BuyOrderDetail> buyOrderDetailList) {

		this.buyOrderDetailList = buyOrderDetailList;
	}

	public BuyOrder getBuyOrder() {

		return buyOrder;
	}

	public void setBuyOrder(BuyOrder buyOrder) {

		this.buyOrder = buyOrder;
	}

	public Long getPolicyNo() {

		return policyNo;
	}

	public void setPolicyNo(Long policyNo) {

		this.policyNo = policyNo;
	}

	public Long getPriceSpecNo() {

		return priceSpecNo;
	}

	public void setPriceSpecNo(Long priceSpecNo) {

		this.priceSpecNo = priceSpecNo;
	}

	public Long getPnrNo() {

		return pnrNo;
	}

	public void setPnrNo(Long pnrNo) {

		this.pnrNo = pnrNo;
	}

	public Long getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(Long supplierNo) {
		this.supplierNo = supplierNo;
	}

	public Long getSupplierTypeNo() {
		return supplierTypeNo;
	}

	public void setSupplierTypeNo(Long supplierTypeNo) {
		this.supplierTypeNo = supplierTypeNo;
	}

	public Long getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	public Long getChangeOrderNo() {
		return changeOrderNo;
	}

	public void setChangeOrderNo(Long changeOrderNo) {
		this.changeOrderNo = changeOrderNo;
	}

	public String getBuyRemarke() {
		return buyRemarke;
	}

	public void setBuyRemarke(String buyRemarke) {
		this.buyRemarke = buyRemarke;
	}

}