package com.tempus.gss.product.ift.api.entity.iftVo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 销售营销，表人+航段的组合.
 */
public class IftSaleOrderDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 销售明细编号 销售明细编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderDetailNo;
	/**
	 * 销售订单编号 订单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	/**
	 * 乘客编号 乘客编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long passengerNo;
	/**
	 * 乘客
	 */
	private IftApiPassenger passenger;
	/**
	 * 航程编号 航程编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long legNo;
	/**
	 * 航程
	 */
	private IftLeg leg;
	/**
	 * 票号
	 */
	private String ticketNo;
	/**
	 * 票价
	 */
	private BigDecimal fare;
	/**
	 * 税费
	 */
	private BigDecimal tax;
	/**
	 * 手续费
	 */
	private BigDecimal brokerage;
	/**
	 * 代理费
	 */
	private BigDecimal agencyFee;
	/**
	 * 后返
	 */
	private BigDecimal rebate;
	/**
	 * 舱位: A/B/C/D/E.
	 */
	private String cabin;
	/**
	 * 计奖价
	 */
	private BigDecimal awardPrice;
	/**
	 * 父航段序号 从01开始
	 */
	private String parentSection;
	/**
	 * 子航段序号 从01开始 保存格式为 复航段序号+子航段序号，即：0101.
	 */
	private String childSection;
	/**
	 * 是否改签新增的明细。
	 * true：表示改签新增信息。
	 * false：表示原始信息。
	 */
	private Boolean isChange;
	
	/**
	 * 销售明细状态 1（待核价），2（已核价），3（出票中），4（已出票）5（退票中），6（废票中），7（改签中） 8（已退），9（已废），10（已改签）.11(已取消)
	 */
	private String status;

	public Long getSaleOrderDetailNo() {

		return saleOrderDetailNo;
	}

	public void setSaleOrderDetailNo(Long saleOrderDetailNo) {

		this.saleOrderDetailNo = saleOrderDetailNo;
	}

	public Long getSaleOrderNo() {

		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {

		this.saleOrderNo = saleOrderNo;
	}

	public Long getPassengerNo() {

		return passengerNo;
	}

	public void setPassengerNo(Long passengerNo) {

		this.passengerNo = passengerNo;
	}

	public Long getLegNo() {

		return legNo;
	}

	public void setLegNo(Long legNo) {

		this.legNo = legNo;
	}

	public String getTicketNo() {

		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {

		this.ticketNo = ticketNo;
	}

	public Boolean getChange() {

		return isChange;
	}

	public BigDecimal getFare() {

		return fare;
	}

	public void setFare(BigDecimal fare) {

		this.fare = fare;
	}

	public BigDecimal getTax() {

		return tax;
	}

	public void setTax(BigDecimal tax) {

		this.tax = tax;
	}

	public BigDecimal getBrokerage() {

		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {

		this.brokerage = brokerage;
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

	public String getParentSection() {

		return parentSection;
	}

	public void setParentSection(String parentSection) {

		this.parentSection = parentSection;
	}

	public String getChildSection() {

		return childSection;
	}

	public void setChildSection(String childSection) {

		this.childSection = childSection;
	}

	public Boolean getIsChange() {

		return isChange;
	}

	public void setIsChange(Boolean isChange) {

		this.isChange = isChange;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public IftApiPassenger getPassenger() {

		return passenger;
	}

	public void setPassenger(IftApiPassenger passenger) {

		this.passenger = passenger;
	}
	
	public String getCabin() {
	
		return cabin;
	}

	public void setCabin(String cabin) {
	
		this.cabin = cabin;
	}

	public IftLeg getLeg() {
		return leg;
	}

	public void setLeg(IftLeg leg) {
		this.leg = leg;
	}
	
	
}