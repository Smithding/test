package com.tempus.gss.product.ift.api.entity.formula;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.tempus.gss.product.ift.api.entity.search.Mileage;
import com.tempus.tbe.entity.NucFareInfo;

/**
 * Created by 杨威 on 2016/10/18.
 */
public class FormulaParameters implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5467397082233696916L;
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
	 * 航程类型
	 */
	private Integer flightType;
	/**
	 * 里程信息
	 */
	private List<Mileage> mileage;
	/**
	 * NUC
	 */
	private List<NucFareInfo> nucFareInfos;
	  /**
     * 控润返点
     */
    private BigDecimal profitRebate;
    /**
     * 控润加价
     */
    private BigDecimal profitPrice;
    
    /**
     * 采购返点
     */
    private BigDecimal buyRebate;
    /**
     * 采购代理费
     */
    private BigDecimal buyAgencyFee;

	public BigDecimal getBuyRebate() {
		return buyRebate;
	}
	public void setBuyRebate(BigDecimal buyRebate) {
		this.buyRebate = buyRebate;
	}
	public BigDecimal getBuyAgencyFee() {
		return buyAgencyFee;
	}
	public void setBuyAgencyFee(BigDecimal buyAgencyFee) {
		this.buyAgencyFee = buyAgencyFee;
	}
	public BigDecimal getProfitPrice() {
		return profitPrice;
	}
	public void setProfitPrice(BigDecimal profitPrice) {
		this.profitPrice = profitPrice;
	}
	public BigDecimal getProfitRebate() {
		return profitRebate;
	}
	public void setProfitRebate(BigDecimal profitRebate) {
		this.profitRebate = profitRebate;
	}
	public List<NucFareInfo> getNucFareInfos() {
		return nucFareInfos;
	}
	public void setNucFareInfos(List<NucFareInfo> nucFareInfos) {
		this.nucFareInfos = nucFareInfos;
	}
	public List<Mileage> getMileage() {
		return mileage;
	}
	public Integer getFlightType() {
		return flightType;
	}

	public void setFlightType(Integer flightType) {
		this.flightType = flightType;
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
