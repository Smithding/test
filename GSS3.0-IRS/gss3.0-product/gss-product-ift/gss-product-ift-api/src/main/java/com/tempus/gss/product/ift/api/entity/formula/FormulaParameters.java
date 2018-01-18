package com.tempus.gss.product.ift.api.entity.formula;

import java.math.BigDecimal;

/**
 * Created by 杨威 on 2016/10/18.
 */
public class FormulaParameters {

	/**
	 * 票面.
	 *
	 */
	private BigDecimal fare;
	/**
	 * 税金
	 *
	 */
	private BigDecimal tax;
	/**
	 * 手续费
	 *
	 */
	private BigDecimal brokerage;
	/**
	 * 计奖价
	 *
	 */
	private BigDecimal awardPrice;
	/**
	 * 代理费
	 *
	 */
	private BigDecimal agencyFee;
	/**
	 * 返点
	 *
	 */
	private BigDecimal saleRebate;

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

	public BigDecimal getAwardPrice() {

		return awardPrice;
	}

	public void setAwardPrice(BigDecimal awardPrice) {

		this.awardPrice = awardPrice;
	}

	public BigDecimal getAgencyFee() {

		return agencyFee;
	}

	public void setAgencyFee(BigDecimal agencyFee) {

		this.agencyFee = agencyFee;
	}

	public BigDecimal getSaleRebate() {

		return saleRebate;
	}

	public void setSaleRebate(BigDecimal saleRebate) {

		this.saleRebate = saleRebate;
	}
}
