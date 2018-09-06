package com.tempus.gss.product.ift.api.entity.formula;

import java.math.BigDecimal;
import java.util.List;

import com.tempus.gss.product.ift.api.entity.search.Mileage;

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
	/**
	 * 单程直减费用
	 */
	private BigDecimal oneWayPrivilege;
	/**
	 * 往返直减费用
	 */
	private BigDecimal roundTripPrivilege;
	/**
	 * 共享段奖励
	 */
	private BigDecimal shareRebate;
	/**
	 * 是否共享航班
	 */
	private Boolean isShare;
	/**
	 * 销售价格
	 */
	private BigDecimal salePrice;
	/**
	 * 里程信息
	 */
	private List<Mileage> mileage;

	public List<Mileage> getMileage() {
		return mileage;
	}

	public void setMileage(List<Mileage> mileage) {
		this.mileage = mileage;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getShareRebate() {
		return shareRebate;
	}

	public void setShareRebate(BigDecimal shareRebate) {
		this.shareRebate = shareRebate;
	}

	public Boolean getIsShare() {
		return isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

	public BigDecimal getOneWayPrivilege() {
		return oneWayPrivilege;
	}

	public void setOneWayPrivilege(BigDecimal oneWayPrivilege) {
		this.oneWayPrivilege = oneWayPrivilege;
	}

	public BigDecimal getRoundTripPrivilege() {
		return roundTripPrivilege;
	}

	public void setRoundTripPrivilege(BigDecimal roundTripPrivilege) {
		this.roundTripPrivilege = roundTripPrivilege;
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
