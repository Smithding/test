package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * Created by 杨威 on 2016/10/18.
 */
public class PassengerTypePricesTotal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 乘客类型.
	 * ADT，CHD，INF
	 */
	private String passengerType;

	/**
	 * 票面价.
	 */
	private BigDecimal fare;
	/**
	 * 售价.
	 */
	private BigDecimal salePrice;

	/**
	 * 税费.
	 */
	private BigDecimal tax;


	/**
	 * 优惠.=票面价+税费-售价
	 */
	private BigDecimal favorable;

	/**
	 * 返点
	 *
	 */
	private BigDecimal saleRebate;

	/**
	 * 是否记奖
	 */
	private Boolean isAward;

	/**
	 * 运价计算横式
	 */
	private String fareLinear;
	/**
	 * 运价代码
	 */
	private String fareBasis;

	/**
	 * 代理费率，小于等于1的2位小数，0.01表示一个点.
	 */
	private BigDecimal agencyFee;

	/**
	 * 手续费，5，表示￥5.
	 */
	private BigDecimal brokerage;
	/**
	 * 计奖价.
	 */
	private BigDecimal awardPrice;

	/**
	 * 控润加价.
	 */
	private BigDecimal addPrice;

	/**
	 * Q值.
	 */
	private BigDecimal qvalue;
	/**
	 * S值.
	 */
	private BigDecimal svalue;

	/**
	 * 税费集合.
	 */
	private HashMap<String,BigDecimal> taxs;



	@JsonSerialize(using = LongSerializer.class)
	private long priceNo;

	@JsonSerialize(using = LongSerializer.class)
	private long filePriceNo;

	public String getPassengerType() {

		return passengerType;
	}

	public void setPassengerType(String passengerType) {

		this.passengerType = passengerType;
	}

	public BigDecimal getFare() {

		return fare;
	}

	public void setFare(BigDecimal fare) {

		this.fare = fare;
	}

	public BigDecimal getSalePrice() {

		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {

		this.salePrice = salePrice;
	}

	public BigDecimal getTax() {

		return tax;
	}

	public void setTax(BigDecimal tax) {

		this.tax = tax;
	}

	public Boolean getAward() {

		return isAward;
	}

	public void setAward(Boolean award) {

		isAward = award;
	}

	public BigDecimal getFavorable() {

		return favorable;
	}

	public void setFavorable(BigDecimal favorable) {

		this.favorable = favorable;
	}

	public BigDecimal getSaleRebate() {

		return saleRebate;
	}

	public void setSaleRebate(BigDecimal saleRebate) {

		this.saleRebate = saleRebate;
	}

	public String getFareLinear() {

		return fareLinear;
	}

	public void setFareLinear(String fareLinear) {

		this.fareLinear = fareLinear;
	}

	public String getFareBasis() {
		return fareBasis;
	}

	public void setFareBasis(String fareBasis) {
		this.fareBasis = fareBasis;
	}

	public BigDecimal getAgencyFee() {
		return agencyFee;
	}

	public void setAgencyFee(BigDecimal agencyFee) {
		this.agencyFee = agencyFee;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public long getPriceNo() {
		return priceNo;
	}

	public void setPriceNo(long priceNo) {
		this.priceNo = priceNo;
	}

	public BigDecimal getAwardPrice() {
		return awardPrice;
	}

	public void setAwardPrice(BigDecimal awardPrice) {
		this.awardPrice = awardPrice;
	}

	public BigDecimal getAddPrice() {
		return addPrice;
	}

	public void setAddPrice(BigDecimal addPrice) {
		this.addPrice = addPrice;
	}

	public long getFilePriceNo() {
		return filePriceNo;
	}

	public void setFilePriceNo(long filePriceNo) {
		this.filePriceNo = filePriceNo;
	}

	public BigDecimal getQvalue() {
		return qvalue;
	}

	public void setQvalue(BigDecimal qvalue) {
		this.qvalue = qvalue;
	}

	public BigDecimal getSvalue() {
		return svalue;
	}

	public void setSvalue(BigDecimal svalue) {
		this.svalue = svalue;
	}

	public HashMap<String, BigDecimal> getTaxs() {
		return taxs;
	}

	public void setTaxs(HashMap<String, BigDecimal> taxs) {
		this.taxs = taxs;
	}
}
