package com.tempus.gss.product.ift.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 政策的舱位定义以及舱位的默认返点.
 */
public class Cabin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = LongSerializer.class)
	private Long policyCabinNo;

	@JsonSerialize(using = LongSerializer.class)
	private Long id;

	@JsonSerialize(using = LongSerializer.class)
	private Long policyNo;

	/**
	 * 舱位: A/B/C/D/E.
	 */
	private String cabin;
	private String exclusiveCabin;//排除仓位

	//<editor-fold desc="舱位的默认销售与采购返点和手续费.">
	/**
	 * 销售返点，小于等于1的2位小数，0.01表示一个点.
	 */
	private BigDecimal saleRebate;

	/**
	 * 销售单程手续费，5，表示￥5.
	 */
	private BigDecimal saleOwBrokerage;

	/**
	 * 销售往返手续费，5，表示￥5.
	 */
	private BigDecimal saleRtBrokerage;

	/**
	 * 采购返点，百分数，0.01表示一个点.
	 */
	private BigDecimal buyRebate;

	/**
	 * 采购单程手续费，5，表示￥5.
	 */
	private BigDecimal buyOwBrokerage;

	/**
	 * 采购往返手续费，5，表示￥5.
	 */
	private BigDecimal buyRtBrokerage;
	//</editor-fold>

	/**
	 * 渠道控润列表.
	 * 优先级高于默认值.
	 * 不存在对应渠道的控润时，使用默认值.
	 */
	private List<ProfitControl> profitControlList;

	private BigDecimal agencyFee;

	public Long getPolicyCabinNo() {

		return policyCabinNo;
	}

	public void setPolicyCabinNo(Long policyCabinNo) {

		this.policyCabinNo = policyCabinNo;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public Long getPolicyNo() {

		return policyNo;
	}

	public void setPolicyNo(Long policyNo) {

		this.policyNo = policyNo;
	}

	public String getCabin() {

		return cabin;
	}

	public void setCabin(String cabin) {

		this.cabin = cabin == null ? null : cabin.trim();
	}

	public BigDecimal getSaleRebate() {

		return saleRebate;
	}

	public void setSaleRebate(BigDecimal saleRebate) {

		this.saleRebate = saleRebate;
	}

	public BigDecimal getSaleOwBrokerage() {

		return saleOwBrokerage;
	}

	public void setSaleOwBrokerage(BigDecimal saleOwBrokerage) {

		this.saleOwBrokerage = saleOwBrokerage;
	}

	public BigDecimal getSaleRtBrokerage() {

		return saleRtBrokerage;
	}

	public void setSaleRtBrokerage(BigDecimal saleRtBrokerage) {

		this.saleRtBrokerage = saleRtBrokerage;
	}

	public BigDecimal getBuyRebate() {

		return buyRebate;
	}

	public void setBuyRebate(BigDecimal buyRebate) {

		this.buyRebate = buyRebate;
	}

	public BigDecimal getBuyOwBrokerage() {

		return buyOwBrokerage;
	}

	public void setBuyOwBrokerage(BigDecimal buyOwBrokerage) {

		this.buyOwBrokerage = buyOwBrokerage;
	}

	public BigDecimal getBuyRtBrokerage() {

		return buyRtBrokerage;
	}

	public void setBuyRtBrokerage(BigDecimal buyRtBrokerage) {

		this.buyRtBrokerage = buyRtBrokerage;
	}

	public List<ProfitControl> getProfitControlList() {

		return profitControlList;
	}

	public void setProfitControlList(List<ProfitControl> profitControlList) {

		this.profitControlList = profitControlList;
	}

	public BigDecimal getAgencyFee() {
		return agencyFee;
	}

	public void setAgencyFee(BigDecimal agencyFee) {
		this.agencyFee = agencyFee;
	}

	public String getExclusiveCabin() {
		return exclusiveCabin;
	}

	public void setExclusiveCabin(String exclusiveCabin) {
		this.exclusiveCabin = exclusiveCabin;
	}

	@Override
	public String toString() {
		return "Cabin{" +
				"policyCabinNo=" + policyCabinNo +
				", id=" + id +
				", policyNo=" + policyNo +
				", cabin='" + cabin + '\'' +
				", exclusiveCabin='" + exclusiveCabin + '\'' +
				", saleRebate=" + saleRebate +
				", saleOwBrokerage=" + saleOwBrokerage +
				", saleRtBrokerage=" + saleRtBrokerage +
				", buyRebate=" + buyRebate +
				", buyOwBrokerage=" + buyOwBrokerage +
				", buyRtBrokerage=" + buyRtBrokerage +
				", profitControlList=" + profitControlList +
				", agencyFee=" + agencyFee +
				'}';
	}
}
