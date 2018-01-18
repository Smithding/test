package com.tempus.gss.product.ift.api.entity.formula;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 运价计算公式.
 * 当未配上有效政策时，将返回默认政策.
 * 保存时转化为json字符串.
 */
public class NoMatchFormulaPara implements Serializable{

	/**
	 * 代理费率，小于等于1的2位小数，0.01表示一个点.
	 */
	private BigDecimal agencyFee;

	/**
	 * 手续费，5，表示￥5.
	 */
	private BigDecimal brokerage;

	/***
	 * 有效政策与默认政策组合时，手续费参与销售价计算.
	 * true（允许），false（不允许）.
	 */
	private boolean combinationPolicy;

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

	public boolean isCombinationPolicy() {

		return combinationPolicy;
	}

	public void setCombinationPolicy(boolean combinationPolicy) {

		this.combinationPolicy = combinationPolicy;
	}

	/***
	 * 默认政策计算公式
	 *
	 */
	public BigDecimal formulaMethod(BigDecimal fare, BigDecimal tax, boolean iscombination,Integer legType) {

		if(null==agencyFee){
			agencyFee=new BigDecimal(0);
		}
		if(null==brokerage){
			brokerage=new BigDecimal(0);
		}
		BigDecimal bage=brokerage;
		if ((!combinationPolicy) && iscombination) {
			return fare.multiply(new BigDecimal(1).subtract(agencyFee.divide(new BigDecimal(100)))).add(tax);
		} else {
			if(legType>1){
				if(brokerage.compareTo(new BigDecimal(0))>0){
					bage=brokerage;
					bage=bage.divide(new BigDecimal(2));
				}
			}
			return fare.multiply(new BigDecimal(1).subtract(agencyFee.divide(new BigDecimal(100)))).add(tax).add(bage);
		}
	}
}
