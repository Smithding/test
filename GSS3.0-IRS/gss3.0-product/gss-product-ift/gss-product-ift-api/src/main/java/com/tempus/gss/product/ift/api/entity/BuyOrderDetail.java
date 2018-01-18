package com.tempus.gss.product.ift.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购明细.
 */
public class BuyOrderDetail implements Serializable {

	/**
	 * 采购明细编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long buyOrderDetailNo;

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
	 * 采购单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long buyOrderNo;

	/**
	 * 销售单明细编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderDetailNo;

	/**
	 * PNR编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long pnrNo;

	/**
	 * 采购的Pnr。
	 */
	private Pnr pnr;

	/**
	 * 销售明细.
	 */
	private SaleOrderDetail saleOrderDetail;

	/**
	 * 票价
	 */
	private BigDecimal fare;

	/**
	 * 手续费
	 */
	private BigDecimal brokerage;

	/**
	 * 税费
	 */
	private BigDecimal tax;

	/**
	 * 代理费
	 */
	private BigDecimal agencyFee;

	/**
	 * 后返
	 */
	private BigDecimal rebate;

	/**
	 * 计奖价
	 */
	private BigDecimal awardPrice;

	/**
	 * 出票时间
	 */
	private Date ticketTime;

	/**
	 * 票号
	 */
	private String ticketNo;

	/**
	 * 操作人
	 */
	private String modifier;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Byte valid;

	/**
	 * 启用状态  1（已出票），3（已取消），4（已退），5（已废），6（已改签）.
	 */
	private String status;

	private static final long serialVersionUID = 1L;

	public Long getBuyOrderDetailNo() {

		return buyOrderDetailNo;
	}

	public void setBuyOrderDetailNo(Long buyOrderDetailNo) {

		this.buyOrderDetailNo = buyOrderDetailNo;
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

	public Long getBuyOrderNo() {

		return buyOrderNo;
	}

	public void setBuyOrderNo(Long buyOrderNo) {

		this.buyOrderNo = buyOrderNo;
	}

	public Long getSaleOrderDetailNo() {

		return saleOrderDetailNo;
	}

	public void setSaleOrderDetailNo(Long saleOrderDetailNo) {

		this.saleOrderDetailNo = saleOrderDetailNo;
	}

	public Long getPnrNo() {

		return pnrNo;
	}

	public void setPnrNo(Long pnrNo) {

		this.pnrNo = pnrNo;
	}

	public BigDecimal getFare() {

		return fare;
	}

	public void setFare(BigDecimal fare) {

		this.fare = fare;
	}

	public BigDecimal getBrokerage() {

		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {

		this.brokerage = brokerage;
	}

	public BigDecimal getTax() {

		return tax;
	}

	public void setTax(BigDecimal tax) {

		this.tax = tax;
	}

	public BigDecimal getAgencyFee() {

		return agencyFee;
	}

	public void setAgencyFee(BigDecimal agencyFee) {

		this.agencyFee = agencyFee;
	}

	public BigDecimal getRebate() {

		return rebate;
	}

	public void setRebate(BigDecimal rebate) {

		this.rebate = rebate;
	}

	public BigDecimal getAwardPrice() {

		return awardPrice;
	}

	public void setAwardPrice(BigDecimal awardPrice) {

		this.awardPrice = awardPrice;
	}

	public Date getTicketTime() {

		return ticketTime;
	}

	public void setTicketTime(Date ticketTime) {

		this.ticketTime = ticketTime;
	}

	public String getTicketNo() {

		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {

		this.ticketNo = ticketNo;
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

	public Date getCreateTime() {

		return createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
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

	public Pnr getPnr() {

		return pnr;
	}

	public void setPnr(Pnr pnr) {

		this.pnr = pnr;
	}

	public SaleOrderDetail getSaleOrderDetail() {

		return saleOrderDetail;
	}

	public void setSaleOrderDetail(SaleOrderDetail saleOrderDetail) {

		this.saleOrderDetail = saleOrderDetail;
	}

	public String getCreator() {

		return creator;
	}

	public void setCreator(String creator) {

		this.creator = creator;
	}
}
