package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * Created by 杨威 on 2016/10/18.
 */
public class PassengerTypePricesTotalVo implements Serializable {
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

	@JsonSerialize(using = LongSerializer.class)
	private long priceNo;

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


	public BigDecimal getFavorable() {

		return favorable;
	}

	public void setFavorable(BigDecimal favorable) {

		this.favorable = favorable;
	}

	public long getPriceNo() {
		return priceNo;
	}

	public void setPriceNo(long priceNo) {
		this.priceNo = priceNo;
	}
}
