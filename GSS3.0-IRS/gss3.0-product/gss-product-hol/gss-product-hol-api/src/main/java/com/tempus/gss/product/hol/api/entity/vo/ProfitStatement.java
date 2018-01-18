package com.tempus.gss.product.hol.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tempus.gss.product.hol.api.entity.ProfitPrice;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitStatement implements Serializable{
	/** SVUID */
	private static final long serialVersionUID = 1L;

	private Integer mode;

	private List<ProfitPrice> rules;

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}
	
	public List<ProfitPrice> getRules() {
		return rules;
	}

	public void setRules(List<ProfitPrice> rules) {
		this.rules = rules;
	}



	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Rule implements Serializable {

		/** SVUID */
		private static final long serialVersionUID = 1L;

		/** 区间范围 */
		private BigDecimal profitFrom;

		/** 区间范围 */
		private BigDecimal profitTo;

		/** 返点数 */
		private BigDecimal rate;

		private Integer profitModel;
		/**
		 * 判断值是否处于区间内
		 *
		 * @param number 指定值
		 */
		public boolean isBetween(Integer number) {
			return number >= profitFrom.intValue() && number <= profitTo.intValue();
		}
		public BigDecimal getProfitFrom() {
			return profitFrom;
		}
		public void setProfitFrom(BigDecimal profitFrom) {
			this.profitFrom = profitFrom;
		}
		public BigDecimal getProfitTo() {
			return profitTo;
		}
		public void setProfitTo(BigDecimal profitTo) {
			this.profitTo = profitTo;
		}
		public BigDecimal getRate() {
			return rate;
		}
		public void setRate(BigDecimal rate) {
			this.rate = rate;
		}
		public Integer getProfitModel() {
			return profitModel;
		}
		public void setProfitModel(Integer profitModel) {
			this.profitModel = profitModel;
		}
	}
}
