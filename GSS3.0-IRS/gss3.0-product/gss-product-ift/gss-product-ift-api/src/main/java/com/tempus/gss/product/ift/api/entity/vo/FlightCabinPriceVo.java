package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 报价内容.
 */
public class FlightCabinPriceVo implements Serializable {

	/**
	 * 乘客类型.
	 * ADT，CHD，INF
	 */
	private String passengerType;

	/**
	 * 是否大客户价格.
	 */
	private boolean isVipPrice;

	/**
	 * 大客户号.
	 */
	private String vipCode;

	/**
	 * 票价.
	 */
	private BigDecimal fare;

	/**
	 * 税费.
	 */
	private BigDecimal tax;

	/**
	 * 改签规则.
	 */
	private String changeRule;

	/**
	 * 退票规则.
	 */
	private String refundRule;
	/**
	 * 是否计奖
	 */
	private boolean isAward;
	/**
	 * 仓位
	 */
	private String cabin;

	/**
	 * 返点
	 *
	 */
	private String saleRebate;

	/**
	 * 运价基础代码
	 */

	private String fareBasisCode;


	public String getPassengerType() {

		return passengerType;
	}

	public void setPassengerType(String passengerType) {

		this.passengerType = passengerType;
	}

	public boolean isVipPrice() {

		return isVipPrice;
	}

	public void setVipPrice(boolean vipPrice) {

		isVipPrice = vipPrice;
	}

	public String getVipCode() {

		return vipCode;
	}

	public void setVipCode(String vipCode) {

		this.vipCode = vipCode;
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

	public String getChangeRule() {

		return changeRule;
	}

	public void setChangeRule(String changeRule) {

		this.changeRule = changeRule;
	}

	public String getRefundRule() {

		return refundRule;
	}

	public void setRefundRule(String refundRule) {

		this.refundRule = refundRule;
	}

	public boolean isAward() {

		return isAward;
	}

	public void setAward(boolean award) {

		isAward = award;
	}

	public String getCabin() {

		return cabin;
	}

	public void setCabin(String cabin) {

		this.cabin = cabin;
	}


	public String getSaleRebate() {

		return saleRebate;
	}

	public void setSaleRebate(String saleRebate) {

		this.saleRebate = saleRebate;
	}

	public String getFareBasisCode() {
		return fareBasisCode;
	}

	public void setFareBasisCode(String fareBasisCode) {
		this.fareBasisCode = fareBasisCode;
	}
}
